package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

enum class SoundEffectType {
    CLICK,
    FOUND,
    MISTAKE,
    HINT,
    LEVEL_COMPLETE
}

class GameAudioManager(context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    private val scope = CoroutineScope(Dispatchers.Default)

    @Volatile var musicVolume: Float = 0.20f
        private set
    @Volatile var effectsVolume: Float = 0.80f
        private set
    @Volatile var isMusicMuted: Boolean = false
        private set
    @Volatile var isEffectsMuted: Boolean = false
        private set

    private var currentAmbientKey: String? = null
    private var ambientTrack: AudioTrack? = null
    private var ambientLoopJob: Job? = null
    private var isPlayingAmbient: Boolean = false
    private var hasAudioFocus: Boolean = false
    private var focusRequest: AudioFocusRequest? = null

    // Cache pre-generated sound effect buffers
    private val sfxBuffers = ConcurrentHashMap<SoundEffectType, ShortArray>()
    private val ambientCache = ConcurrentHashMap<String, ShortArray>()

    private val focusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                hasAudioFocus = false
                pauseAmbient()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT,
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                hasAudioFocus = false
                pauseAmbient()
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                hasAudioFocus = true
                resumeAmbient()
            }
        }
    }

    init {
        // Pre-generate short SFX buffers asynchronously to keep UI responsive
        scope.launch {
            sfxBuffers[SoundEffectType.CLICK] = SoundSynthesizer.generateShortClick()
            sfxBuffers[SoundEffectType.FOUND] = SoundSynthesizer.generateFoundChime()
            sfxBuffers[SoundEffectType.MISTAKE] = SoundSynthesizer.generateMistakeBoop()
            sfxBuffers[SoundEffectType.HINT] = SoundSynthesizer.generateHintShimmer()
            sfxBuffers[SoundEffectType.LEVEL_COMPLETE] = SoundSynthesizer.generateLevelCompleteFanfare()
        }
    }

    private fun requestAudioFocus(): Boolean {
        if (audioManager == null) return true
        if (hasAudioFocus) return true

        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val playbackAttrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttrs)
                .setAcceptsDelayedFocusGain(false)
                .setOnAudioFocusChangeListener(focusChangeListener)
                .build()
            focusRequest = request
            audioManager.requestAudioFocus(request)
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                focusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }
        hasAudioFocus = (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
        return hasAudioFocus
    }

    private fun abandonAudioFocus() {
        if (!hasAudioFocus) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        } else {
            @Suppress("DEPRECATION")
            audioManager?.abandonAudioFocus(focusChangeListener)
        }
        hasAudioFocus = false
    }

    fun playEffect(type: SoundEffectType) {
        if (isEffectsMuted || effectsVolume <= 0.01f) return

        scope.launch {
            val buffer = sfxBuffers[type] ?: when (type) {
                SoundEffectType.CLICK -> SoundSynthesizer.generateShortClick()
                SoundEffectType.FOUND -> SoundSynthesizer.generateFoundChime()
                SoundEffectType.MISTAKE -> SoundSynthesizer.generateMistakeBoop()
                SoundEffectType.HINT -> SoundSynthesizer.generateHintShimmer()
                SoundEffectType.LEVEL_COMPLETE -> SoundSynthesizer.generateLevelCompleteFanfare()
            }.also { sfxBuffers[type] = it }

            playSfxBuffer(buffer, effectsVolume)
        }
    }

    private fun playSfxBuffer(buffer: ShortArray, volume: Float) {
        try {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val format = AudioFormat.Builder()
                .setSampleRate(SoundSynthesizer.SAMPLE_RATE)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

            val bufferSize = buffer.size * 2
            val track = AudioTrack.Builder()
                .setAudioAttributes(attributes)
                .setAudioFormat(format)
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.setVolume(volume.coerceIn(0f, 1f))
            track.write(buffer, 0, buffer.size)
            track.play()

            // Release after playback finished
            track.setNotificationMarkerPosition(buffer.size)
            track.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
                override fun onPeriodicNotification(p0: AudioTrack?) {}
                override fun onMarkerReached(p0: AudioTrack?) {
                    try {
                        track.stop()
                        track.release()
                    } catch (_: Exception) {}
                }
            })
        } catch (_: Exception) {}
    }

    fun playAmbient(ambientKey: String) {
        if (currentAmbientKey == ambientKey && isPlayingAmbient) return
        currentAmbientKey = ambientKey

        stopAmbient()

        if (isMusicMuted || musicVolume <= 0.01f) return
        if (!requestAudioFocus()) return

        isPlayingAmbient = true
        ambientLoopJob = scope.launch {
            try {
                val buffer = ambientCache.getOrPut(ambientKey) {
                    SoundSynthesizer.generateAmbientLoop(ambientKey)
                }

                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
                val format = AudioFormat.Builder()
                    .setSampleRate(SoundSynthesizer.SAMPLE_RATE)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .build()

                val bufferSizeBytes = buffer.size * 2
                val track = AudioTrack.Builder()
                    .setAudioAttributes(attributes)
                    .setAudioFormat(format)
                    .setBufferSizeInBytes(bufferSizeBytes)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                ambientTrack = track
                val actualVolume = if (isMusicMuted) 0f else musicVolume.coerceIn(0f, 1f)
                track.setVolume(actualVolume)
                track.write(buffer, 0, buffer.size)
                track.setLoopPoints(0, buffer.size, -1) // infinite loop
                track.play()

                while (isActive && isPlayingAmbient) {
                    kotlinx.coroutines.delay(500)
                }
            } catch (_: Exception) {
            }
        }
    }

    fun pauseAmbient() {
        try {
            ambientTrack?.let {
                if (it.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    it.pause()
                }
            }
        } catch (_: Exception) {}
    }

    fun resumeAmbient() {
        if (isMusicMuted || musicVolume <= 0.01f) return
        requestAudioFocus()
        try {
            ambientTrack?.let {
                if (it.playState == AudioTrack.PLAYSTATE_PAUSED) {
                    it.play()
                } else if (it.playState == AudioTrack.PLAYSTATE_STOPPED) {
                    currentAmbientKey?.let { key -> playAmbient(key) }
                }
            } ?: run {
                currentAmbientKey?.let { key -> playAmbient(key) }
            }
        } catch (_: Exception) {}
    }

    fun stopAmbient() {
        isPlayingAmbient = false
        ambientLoopJob?.cancel()
        ambientLoopJob = null
        try {
            ambientTrack?.let {
                if (it.state == AudioTrack.STATE_INITIALIZED) {
                    it.stop()
                    it.release()
                }
            }
        } catch (_: Exception) {}
        ambientTrack = null
    }

    fun setMusicVolume(volume: Float) {
        musicVolume = volume.coerceIn(0f, 1f)
        val actual = if (isMusicMuted) 0f else musicVolume
        try {
            ambientTrack?.setVolume(actual)
        } catch (_: Exception) {}
    }

    fun setEffectsVolume(volume: Float) {
        effectsVolume = volume.coerceIn(0f, 1f)
    }

    fun setMusicMuted(muted: Boolean) {
        isMusicMuted = muted
        val actual = if (muted) 0f else musicVolume
        try {
            ambientTrack?.setVolume(actual)
            if (!muted && isPlayingAmbient && ambientTrack?.playState != AudioTrack.PLAYSTATE_PLAYING) {
                resumeAmbient()
            }
        } catch (_: Exception) {}
    }

    fun setEffectsMuted(muted: Boolean) {
        isEffectsMuted = muted
    }

    fun release() {
        stopAmbient()
        abandonAudioFocus()
        sfxBuffers.clear()
        ambientCache.clear()
    }
}
