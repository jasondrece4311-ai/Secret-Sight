package com.example.audio

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

object SoundSynthesizer {
    const val SAMPLE_RATE = 22050

    fun generateShortClick(): ShortArray {
        val duration = 0.05f // 50ms
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            val freq = 600f * exp(-t * 30f)
            val envelope = exp(-t * 40f)
            val wave = sin(2f * PI.toFloat() * freq * t)
            buffer[i] = (wave * envelope * Short.MAX_VALUE * 0.4f).toInt().toShort()
        }
        return buffer
    }

    fun generateFoundChime(): ShortArray {
        // Ascending harmonic chime: E5 (659Hz), G#5 (830Hz), B5 (987Hz), E6 (1318Hz)
        val duration = 0.65f
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)
        val freqs = floatArrayOf(659.25f, 830.61f, 987.77f, 1318.51f)
        val noteOffset = 0.09f // 90ms between note starts

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            var sampleSum = 0f

            for (n in freqs.indices) {
                val noteStart = n * noteOffset
                if (t >= noteStart) {
                    val noteT = t - noteStart
                    val env = exp(-noteT * 6.5f)
                    // Fundamental + subtle 2nd harmonic
                    val fundamental = sin(2f * PI.toFloat() * freqs[n] * noteT)
                    val harmonic = 0.3f * sin(4f * PI.toFloat() * freqs[n] * noteT)
                    sampleSum += (fundamental + harmonic) * env * 0.3f
                }
            }
            buffer[i] = (sampleSum.coerceIn(-1f, 1f) * Short.MAX_VALUE * 0.6f).toInt().toShort()
        }
        return buffer
    }

    fun generateMistakeBoop(): ShortArray {
        // Soft subtle woodblock boop: 240Hz down to 180Hz
        val duration = 0.18f
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            val freq = 240f - (t / duration) * 60f
            val env = exp(-t * 22f)
            val wave = sin(2f * PI.toFloat() * freq * t)
            buffer[i] = (wave * env * Short.MAX_VALUE * 0.35f).toInt().toShort()
        }
        return buffer
    }

    fun generateHintShimmer(): ShortArray {
        // Shimmering harp sweep
        val duration = 0.75f
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)
        val freqs = floatArrayOf(523.25f, 659.25f, 783.99f, 1046.50f, 1318.51f)

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            var sum = 0f
            for (n in freqs.indices) {
                val start = n * 0.07f
                if (t >= start) {
                    val noteT = t - start
                    val env = exp(-noteT * 7f)
                    val s = sin(2f * PI.toFloat() * freqs[n] * noteT)
                    sum += s * env * 0.22f
                }
            }
            buffer[i] = (sum.coerceIn(-1f, 1f) * Short.MAX_VALUE * 0.5f).toInt().toShort()
        }
        return buffer
    }

    fun generateLevelCompleteFanfare(): ShortArray {
        val duration = 1.6f
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)
        // Major 9th chord progression: Cmaj9 -> Fmaj9
        val chord1 = floatArrayOf(261.63f, 329.63f, 392.00f, 493.88f, 587.33f)
        val chord2 = floatArrayOf(349.23f, 440.00f, 523.25f, 659.25f, 783.99f)

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            var sum = 0f
            if (t < 0.75f) {
                val env = exp(-t * 2.2f)
                for (f in chord1) {
                    sum += sin(2f * PI.toFloat() * f * t) * 0.16f * env
                }
            } else {
                val t2 = t - 0.7f
                val env = exp(-t2 * 1.6f)
                for (f in chord2) {
                    sum += sin(2f * PI.toFloat() * f * t2) * 0.18f * env
                }
            }
            buffer[i] = (sum.coerceIn(-1f, 1f) * Short.MAX_VALUE * 0.65f).toInt().toShort()
        }
        return buffer
    }

    /**
     * Generates a smooth, seamlessly loopable ambient soundscape for a given theme.
     * Duration: 5.0 seconds loopable buffer (compact in memory: ~220KB per loop).
     */
    fun generateAmbientLoop(ambientKey: String): ShortArray {
        val duration = 5.0f
        val totalSamples = (SAMPLE_RATE * duration).toInt()
        val buffer = ShortArray(totalSamples)

        // Select scale and base frequencies according to scene atmosphere
        val (baseFreqs, warmth, noiseFactor) = when (ambientKey) {
            "forest" -> Triple(floatArrayOf(174.61f, 261.63f, 349.23f, 440.0f), 0.7f, 0.08f) // F major calm
            "garden" -> Triple(floatArrayOf(196.00f, 246.94f, 293.66f, 392.0f), 0.8f, 0.04f) // G major warm
            "camp" -> Triple(floatArrayOf(164.81f, 220.00f, 329.63f, 440.0f), 0.75f, 0.09f) // E minor campfire
            "ruins" -> Triple(floatArrayOf(146.83f, 220.00f, 293.66f, 369.99f), 0.6f, 0.07f) // D suspended
            "living_room" -> Triple(floatArrayOf(220.00f, 277.18f, 329.63f, 440.0f), 0.85f, 0.02f) // A major cozy
            "kitchen" -> Triple(floatArrayOf(261.63f, 329.63f, 392.00f, 523.25f), 0.8f, 0.03f) // C major cheerful
            "bedroom" -> Triple(floatArrayOf(174.61f, 220.00f, 261.63f, 349.23f), 0.9f, 0.02f) // F major lullaby
            "garage" -> Triple(floatArrayOf(155.56f, 233.08f, 311.13f, 466.16f), 0.7f, 0.05f) // Eb lo-fi workshop
            "beach" -> Triple(floatArrayOf(130.81f, 196.00f, 261.63f, 392.0f), 0.65f, 0.14f) // Ocean swell
            "harbor" -> Triple(floatArrayOf(146.83f, 220.00f, 293.66f, 440.0f), 0.7f, 0.08f) // Nautical breeze
            "coral_reef" -> Triple(floatArrayOf(196.00f, 261.63f, 329.63f, 493.88f), 0.6f, 0.06f) // Underwater pads
            "pirate_island" -> Triple(floatArrayOf(146.83f, 174.61f, 220.00f, 293.66f), 0.7f, 0.06f) // D minor adventure
            "city" -> Triple(floatArrayOf(174.61f, 261.63f, 329.63f, 392.00f), 0.75f, 0.07f) // Urban ambience
            "market" -> Triple(floatArrayOf(220.00f, 293.66f, 369.99f, 440.00f), 0.8f, 0.05f) // Light acoustic
            "train_station" -> Triple(floatArrayOf(164.81f, 246.94f, 329.63f, 392.00f), 0.75f, 0.06f) // Nostalgic
            "night_city" -> Triple(floatArrayOf(130.81f, 174.61f, 261.63f, 349.23f), 0.6f, 0.04f) // Atmospheric electronic
            "castle" -> Triple(floatArrayOf(146.83f, 220.00f, 293.66f, 440.00f), 0.8f, 0.03f) // Medieval strings
            "wizard_workshop" -> Triple(floatArrayOf(220.00f, 277.18f, 349.23f, 440.00f), 0.65f, 0.04f) // Magical
            "dragon_cave" -> Triple(floatArrayOf(110.00f, 164.81f, 220.00f, 329.63f), 0.7f, 0.08f) // Deep fantasy
            "space_station" -> Triple(floatArrayOf(130.81f, 196.00f, 293.66f, 440.00f), 0.55f, 0.04f) // Futuristic
            else -> Triple(floatArrayOf(174.61f, 220.00f, 261.63f, 349.23f), 0.75f, 0.05f) // Title / generic
        }

        var noiseState = 0.5f

        for (i in 0 until totalSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            // Low frequency envelope modulation (LFO) for breathing sensation (0.2 Hz = 5s cycle)
            val lfo = 0.8f + 0.2f * sin(2f * PI.toFloat() * 0.2f * t)
            var sample = 0f

            for ((idx, f) in baseFreqs.withIndex()) {
                val detune = 1f + 0.002f * sin(2f * PI.toFloat() * (0.1f + idx * 0.05f) * t)
                val freq = f * detune
                // Soft sine wave with gentle second harmonic
                val s1 = sin(2f * PI.toFloat() * freq * t)
                val s2 = 0.25f * sin(4f * PI.toFloat() * freq * t)
                sample += (s1 + s2) * (0.2f / baseFreqs.size)
            }

            // Subtle gentle filtered breath / pink noise
            noiseState = 0.95f * noiseState + 0.05f * ((Math.random().toFloat() * 2f) - 1f)
            sample = (sample * warmth) + (noiseState * noiseFactor)
            sample *= lfo

            // Seamless boundary windowing to eliminate any loop clicks
            val window = if (i < 500) {
                i / 500f
            } else if (i > totalSamples - 500) {
                (totalSamples - i) / 500f
            } else {
                1f
            }

            buffer[i] = (sample.coerceIn(-1f, 1f) * window * Short.MAX_VALUE * 0.35f).toInt().toShort()
        }
        return buffer
    }
}
