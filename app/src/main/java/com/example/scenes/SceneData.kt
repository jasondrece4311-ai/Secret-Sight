package com.example.scenes

import androidx.compose.ui.geometry.Offset
import com.example.model.SceneCategory
import com.example.model.SceneDefinition
import com.example.model.SceneRect
import com.example.model.SceneTarget

object SceneData {
    // SCENE 1: Enchanted Forest
    val scene1 = SceneDefinition(
        id = 1,
        title = "Enchanted Forest",
        subtitle = "Trees, mossy leaves, mushrooms & ancient rocks",
        category = SceneCategory.NATURE,
        ambientAudioKey = "forest",
        targets = listOf(
            SceneTarget(
                id = "s1_frog",
                name = "Tree Frog",
                clue = "Camouflaged along the emerald moss leaves",
                hitBox = SceneRect(210f, 550f, 320f, 660f),
                hintLocation = Offset(265f, 605f)
            ),
            SceneTarget(
                id = "s1_snake",
                name = "Vine Snake",
                clue = "Curled gracefully along the twisted birch branch",
                hitBox = SceneRect(810f, 210f, 940f, 330f),
                hintLocation = Offset(875f, 270f)
            ),
            SceneTarget(
                id = "s1_butterfly",
                name = "Forest Butterfly",
                clue = "Blending into the purple woodland orchid petals",
                hitBox = SceneRect(480f, 340f, 590f, 440f),
                hintLocation = Offset(535f, 390f)
            ),
            SceneTarget(
                id = "s1_key",
                name = "Ornate Brass Key",
                clue = "Embedded into the carved bark of the great oak",
                hitBox = SceneRect(120f, 290f, 230f, 400f),
                hintLocation = Offset(175f, 345f)
            ),
            SceneTarget(
                id = "s1_coin",
                name = "Ancient Bronze Coin",
                clue = "Resting quietly among the smooth riverbed stones",
                hitBox = SceneRect(700f, 710f, 810f, 820f),
                hintLocation = Offset(755f, 765f)
            )
        )
    )

    // SCENE 2: Grandmother's Garden
    val scene2 = SceneDefinition(
        id = 2,
        title = "Grandmother's Garden",
        subtitle = "Flowers, tools, terracotta pots & rustic fence",
        category = SceneCategory.COZY,
        ambientAudioKey = "garden",
        targets = listOf(
            SceneTarget(
                id = "s2_mantis",
                name = "Praying Mantis",
                clue = "Perched along the climbing green rose stem",
                hitBox = SceneRect(310f, 320f, 420f, 430f),
                hintLocation = Offset(365f, 375f)
            ),
            SceneTarget(
                id = "s2_hummingbird",
                name = "Hummingbird",
                clue = "Hovering near crimson blossoms by the trellis",
                hitBox = SceneRect(780f, 230f, 890f, 340f),
                hintLocation = Offset(835f, 285f)
            ),
            SceneTarget(
                id = "s2_seed_packet",
                name = "Seed Packet",
                clue = "Tucked against the wooden cedar fence slats",
                hitBox = SceneRect(150f, 480f, 260f, 580f),
                hintLocation = Offset(205f, 530f)
            ),
            SceneTarget(
                id = "s2_snail",
                name = "Garden Snail",
                clue = "Slowly gliding along the terracotta flowerpot rim",
                hitBox = SceneRect(530f, 620f, 640f, 720f),
                hintLocation = Offset(585f, 670f)
            ),
            SceneTarget(
                id = "s2_trowel",
                name = "Garden Trowel",
                clue = "Hanging subtly beside the wooden potting bench",
                hitBox = SceneRect(890f, 540f, 1000f, 650f),
                hintLocation = Offset(945f, 595f)
            )
        )
    )

    // SCENE 3: Messy Garage (Contains "Jason Drece" on cabinet/toolbox)
    val scene3 = SceneDefinition(
        id = 3,
        title = "Messy Garage",
        subtitle = "Tools, boxes, bicycles & workshop equipment",
        category = SceneCategory.COZY,
        ambientAudioKey = "garage",
        targets = listOf(
            SceneTarget(
                id = "s3_screwdriver",
                name = "Precision Screwdriver",
                clue = "Aligned with the dark spokes of the vintage bicycle",
                hitBox = SceneRect(180f, 450f, 290f, 560f),
                hintLocation = Offset(235f, 505f)
            ),
            SceneTarget(
                id = "s3_measuring_tape",
                name = "Yellow Measuring Tape",
                clue = "Blending into the packing tape on the cardboard box",
                hitBox = SceneRect(720f, 600f, 830f, 700f),
                hintLocation = Offset(775f, 650f)
            ),
            SceneTarget(
                id = "s3_padlock",
                name = "Brass Padlock",
                clue = "Fastened beside chains on the heavy tool chest",
                hitBox = SceneRect(490f, 490f, 600f, 590f),
                hintLocation = Offset(545f, 540f)
            ),
            SceneTarget(
                id = "s3_oil_can",
                name = "Vintage Oil Can",
                clue = "Nestled in shadows beneath the steel workbench shelf",
                hitBox = SceneRect(350f, 680f, 460f, 780f),
                hintLocation = Offset(405f, 730f)
            ),
            SceneTarget(
                id = "s3_pencil",
                name = "Carpenter's Pencil",
                clue = "Resting along the bevel of the wooden ruler",
                hitBox = SceneRect(840f, 380f, 950f, 480f),
                hintLocation = Offset(895f, 430f)
            )
        )
    )
}
