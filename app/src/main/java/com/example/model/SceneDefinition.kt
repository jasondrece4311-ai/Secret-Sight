package com.example.model

enum class SceneCategory {
    NATURE,
    COZY,
    ADVENTURE,
    MYSTERY,
    URBAN,
    FANTASY,
    SCI_FI
}

data class StarThreshold(
    val maxMistakesFor3Stars: Int,
    val maxSecondsFor3Stars: Int,
    val maxMistakesFor2Stars: Int
)

data class SceneDefinition(
    val id: Int,
    val title: String,
    val subtitle: String,
    val category: SceneCategory,
    val ambientAudioKey: String,
    val targets: List<SceneTarget>,
    val maxMistakes: Int = when (id) {
        in 1..5 -> 10
        in 6..12 -> 7
        in 13..17 -> 5
        else -> 3
    },
    val starThreshold: StarThreshold = StarThreshold(
        maxMistakesFor3Stars = when (id) {
            in 1..5 -> 2
            in 6..12 -> 1
            else -> 0
        },
        maxSecondsFor3Stars = when (id) {
            in 1..5 -> 180
            in 6..12 -> 210
            else -> 240
        },
        maxMistakesFor2Stars = when (id) {
            in 1..5 -> 5
            in 6..12 -> 4
            in 13..17 -> 3
            else -> 2
        }
    )
) {
    init {
        require(targets.size == 5) { "Each scene must contain exactly 5 camouflaged targets." }
    }
}
