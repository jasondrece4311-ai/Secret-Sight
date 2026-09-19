package com.example.model

data class LevelResult(
    val levelId: Int,
    val totalFound: Int,
    val totalObjects: Int,
    val mistakes: Int,
    val maxMistakes: Int,
    val timeSeconds: Int,
    val hintsUsed: Int,
    val stars: Int,
    val score: Int
)

object ScoreCalculator {
    fun calculate(
        levelId: Int,
        totalFound: Int,
        mistakes: Int,
        maxMistakes: Int,
        timeSeconds: Int,
        hintsUsed: Int,
        starThreshold: StarThreshold
    ): LevelResult {
        val baseScore = totalFound * 1000
        val mistakePenalty = mistakes * 120
        val hintPenalty = hintsUsed * 200
        val timeBonus = (3000 - (timeSeconds * 10)).coerceAtLeast(0)
        val finalScore = (baseScore + timeBonus - mistakePenalty - hintPenalty).coerceAtLeast(100)

        val stars = when {
            totalFound < 5 -> 0
            mistakes <= starThreshold.maxMistakesFor3Stars &&
                    timeSeconds <= starThreshold.maxSecondsFor3Stars &&
                    hintsUsed <= 1 -> 3
            mistakes <= starThreshold.maxMistakesFor2Stars -> 2
            else -> 1
        }

        return LevelResult(
            levelId = levelId,
            totalFound = totalFound,
            totalObjects = 5,
            mistakes = mistakes,
            maxMistakes = maxMistakes,
            timeSeconds = timeSeconds,
            hintsUsed = hintsUsed,
            stars = stars,
            score = finalScore
        )
    }
}
