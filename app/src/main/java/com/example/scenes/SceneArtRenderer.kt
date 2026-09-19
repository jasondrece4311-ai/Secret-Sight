package com.example.scenes

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object SceneArtRenderer {

    fun render(
        drawScope: DrawScope,
        sceneId: Int,
        discoveredTargetIds: Set<String>,
        forestBitmap: ImageBitmap? = null
    ) {
        when (sceneId) {
            1 -> renderEnchantedForest(drawScope, discoveredTargetIds, forestBitmap)
            2 -> renderGrandmothersGarden(drawScope, discoveredTargetIds)
            3 -> renderMessyGarage(drawScope, discoveredTargetIds)
            else -> renderGenericAtmosphericScene(drawScope, sceneId, discoveredTargetIds)
        }
    }

    // ==========================================
    // SCENE 1: ENCHANTED FOREST
    // ==========================================
    private fun renderEnchantedForest(
        drawScope: DrawScope,
        discoveredIds: Set<String>,
        forestBitmap: ImageBitmap?
    ) {
        with(drawScope) {
            if (forestBitmap != null) {
                // 1. Draw High-Resolution Professional Hidden-Object Forest Artwork
                drawImage(
                    image = forestBitmap,
                    dstOffset = IntOffset.Zero,
                    dstSize = IntSize(1200, 900)
                )
            } else {
                // Fallback rich atmospheric gradient if bitmap unavailable
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F2E23),
                            Color(0xFF1E4D3C),
                            Color(0xFF2E654E),
                            Color(0xFF173024)
                        )
                    ),
                    size = Size(1200f, 900f)
                )
            }

            // ====================================================
            // CAMOUFLAGED OBJECTS IN SCENE 1
            // Naturally integrated into the environment matching
            // lighting, color palette, texture and surroundings.
            // ====================================================

            // ----------------------------------------------------
            // 1. Tree Frog (s1_frog) at (265, 605)
            // Naturally camouflaged on the lush broad leaves & moss in the lower left
            // ----------------------------------------------------
            // Soft realistic leaf contact shadow
            drawOval(
                color = Color(0x770D1F11),
                topLeft = Offset(224f, 586f),
                size = Size(84f, 44f)
            )

            // Frog hind legs (tucked close to body in resting posture)
            drawOval(
                color = Color(0xFF386127),
                topLeft = Offset(222f, 584f),
                size = Size(38f, 26f)
            )
            drawOval(
                color = Color(0xFF4C7B38),
                topLeft = Offset(225f, 586f),
                size = Size(32f, 20f)
            )

            // Main Frog Body (curved, resting naturally along the leaf rib)
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF6E9F4D),
                        Color(0xFF4E7E36),
                        Color(0xFF335722)
                    ),
                    center = Offset(262f, 598f),
                    radius = 35f
                ),
                topLeft = Offset(234f, 580f),
                size = Size(64f, 40f)
            )

            // Lateral flank camouflage stripe (mimicking leaf shadow)
            drawLine(
                color = Color(0xFF254018),
                start = Offset(242f, 602f),
                end = Offset(286f, 606f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            // Pale yellowish-green ventral trim
            drawLine(
                color = Color(0xFF90B564),
                start = Offset(245f, 606f),
                end = Offset(282f, 610f),
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )

            // Frog Head & Snout (tapered wedge)
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF72A651), Color(0xFF45742F)),
                    center = Offset(284f, 595f),
                    radius = 16f
                ),
                topLeft = Offset(272f, 585f),
                size = Size(28f, 22f)
            )

            // Realistic Eye: protruding orbital bump, golden-amber iris, horizontal dark slit, sun glint
            drawCircle(Color(0xFF2B471D), radius = 6f, center = Offset(286f, 588f))
            drawCircle(Color(0xFFD4AC0D), radius = 4.5f, center = Offset(286f, 588f))
            drawLine(Color(0xFF1B2A13), Offset(283f, 588f), Offset(289f, 588f), strokeWidth = 1.8f, cap = StrokeCap.Round)
            drawCircle(Color(0xE6FFFFFF), radius = 1.2f, center = Offset(287f, 587f))

            // Front webbed foot pads gripping the vegetation
            drawCircle(Color(0xFF55853E), radius = 3.5f, center = Offset(294f, 608f))
            drawCircle(Color(0xFF55853E), radius = 3f, center = Offset(298f, 606f))
            drawCircle(Color(0xFF55853E), radius = 3f, center = Offset(292f, 612f))

            // Organic skin speckles (amphibian texture)
            drawCircle(Color(0xFF88BC66), radius = 1.8f, center = Offset(250f, 590f))
            drawCircle(Color(0xFF2B451B), radius = 1.5f, center = Offset(258f, 594f))
            drawCircle(Color(0xFF88BC66), radius = 2.0f, center = Offset(268f, 592f))
            drawCircle(Color(0xFF2B451B), radius = 1.4f, center = Offset(274f, 598f))

            // ----------------------------------------------------
            // 2. Vine Snake (s1_snake) at (875, 270)
            // Naturally camouflaged along the mossy branch & twisted jungle vines
            // ----------------------------------------------------
            // Cast shadow on the branch surface
            val snakeShadowPath = Path().apply {
                moveTo(818f, 274f)
                cubicTo(845f, 246f, 882f, 302f, 922f, 268f)
                cubicTo(934f, 258f, 938f, 248f, 930f, 242f)
            }
            drawPath(snakeShadowPath, color = Color(0x66182012), style = Stroke(width = 18f, cap = StrokeCap.Round))

            // Underbelly shaded curve
            val snakeBellyPath = Path().apply {
                moveTo(820f, 270f)
                cubicTo(846f, 242f, 882f, 298f, 920f, 264f)
                cubicTo(932f, 254f, 936f, 244f, 928f, 238f)
            }
            drawPath(snakeBellyPath, color = Color(0xFF3B562C), style = Stroke(width = 16f, cap = StrokeCap.Round))

            // Main Body Path (rich leafy olive matching foliage and branch moss)
            val snakeBodyPath = Path().apply {
                moveTo(822f, 266f)
                cubicTo(848f, 238f, 882f, 294f, 918f, 260f)
                cubicTo(930f, 250f, 934f, 240f, 926f, 235f)
            }
            drawPath(
                snakeBodyPath,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF567B44), Color(0xFF6B9355), Color(0xFF4C6E3C)),
                    start = Offset(820f, 266f),
                    end = Offset(926f, 235f)
                ),
                style = Stroke(width = 13f, cap = StrokeCap.Round)
            )

            // Dorsal highlight catching filtered sunlight
            val snakeHighlightPath = Path().apply {
                moveTo(830f, 258f)
                cubicTo(852f, 236f, 880f, 288f, 912f, 256f)
            }
            drawPath(
                snakeHighlightPath,
                color = Color(0x66D8EEAA),
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )

            // Organic diamond scale markings along the body
            val scaleOffsets = listOf(
                Offset(834f, 258f),
                Offset(850f, 248f),
                Offset(866f, 262f),
                Offset(880f, 282f),
                Offset(896f, 280f),
                Offset(910f, 264f),
                Offset(922f, 248f)
            )
            for (pt in scaleOffsets) {
                drawCircle(Color(0xFF324824), radius = 3.2f, center = pt)
                drawCircle(Color(0xFF86AE6A), radius = 1.8f, center = Offset(pt.x + 0.8f, pt.y - 0.8f))
            }

            // Snake Head: slender arrow profile, resting flush against the branch
            val headPath = Path().apply {
                moveTo(924f, 236f)
                lineTo(936f, 231f)
                lineTo(940f, 227f)
                lineTo(932f, 225f)
                lineTo(922f, 230f)
                close()
            }
            drawPath(headPath, color = Color(0xFF5D844A))
            // Amber eye slit
            drawCircle(Color(0xFFD4AC0D), radius = 2.2f, center = Offset(932f, 228f))
            drawCircle(Color(0xFF141F10), radius = 1.0f, center = Offset(932f, 228f))

            // ----------------------------------------------------
            // 3. Forest Butterfly (s1_butterfly) at (535, 390)
            // Naturally camouflaged amongst wild purple woodland orchids & foliage
            // ----------------------------------------------------
            // Soft cast shadow on the flower petals behind/below
            drawOval(
                color = Color(0x55280E30),
                topLeft = Offset(506f, 384f),
                size = Size(64f, 32f)
            )

            // Left Wing (angled resting posture, organic scalloped edge)
            val leftWing = Path().apply {
                moveTo(534f, 390f)
                cubicTo(512f, 362f, 492f, 368f, 490f, 388f)
                cubicTo(488f, 404f, 506f, 420f, 534f, 398f)
                close()
            }
            drawPath(
                leftWing,
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF9B59B6), Color(0xFF7D3C98), Color(0xFF4A235A)),
                    center = Offset(512f, 386f),
                    radius = 30f
                )
            )

            // Right Wing
            val rightWing = Path().apply {
                moveTo(536f, 390f)
                cubicTo(558f, 362f, 578f, 368f, 580f, 388f)
                cubicTo(582f, 404f, 564f, 420f, 536f, 398f)
                close()
            }
            drawPath(
                rightWing,
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFA569BD), Color(0xFF884EA0), Color(0xFF512E5F)),
                    center = Offset(558f, 386f),
                    radius = 30f
                )
            )

            // Wing venation (fine dark lines radiating through the wings)
            val wingVeinColor = Color(0xFF381446)
            drawLine(wingVeinColor, Offset(534f, 390f), Offset(504f, 376f), strokeWidth = 1.4f)
            drawLine(wingVeinColor, Offset(534f, 390f), Offset(498f, 396f), strokeWidth = 1.4f)
            drawLine(wingVeinColor, Offset(534f, 390f), Offset(514f, 410f), strokeWidth = 1.4f)
            drawLine(wingVeinColor, Offset(536f, 390f), Offset(566f, 376f), strokeWidth = 1.4f)
            drawLine(wingVeinColor, Offset(536f, 390f), Offset(572f, 396f), strokeWidth = 1.4f)
            drawLine(wingVeinColor, Offset(536f, 390f), Offset(556f, 410f), strokeWidth = 1.4f)

            // Sub-marginal camouflage spots (mimics orchid petal markings)
            drawCircle(Color(0xFFE5B978), radius = 2.2f, center = Offset(498f, 384f))
            drawCircle(Color(0xFFE5B978), radius = 1.8f, center = Offset(506f, 400f))
            drawCircle(Color(0xFFE5B978), radius = 2.2f, center = Offset(572f, 384f))
            drawCircle(Color(0xFFE5B978), radius = 1.8f, center = Offset(564f, 400f))

            // Butterfly body (thorax, abdomen, antennae)
            drawLine(Color(0xFF22092A), Offset(535f, 372f), Offset(535f, 404f), strokeWidth = 3.5f, cap = StrokeCap.Round)
            // Delicate antennae
            drawLine(Color(0xFF22092A), Offset(535f, 372f), Offset(526f, 356f), strokeWidth = 1.2f, cap = StrokeCap.Round)
            drawLine(Color(0xFF22092A), Offset(535f, 372f), Offset(544f, 356f), strokeWidth = 1.2f, cap = StrokeCap.Round)
            drawCircle(Color(0xFF22092A), radius = 1.2f, center = Offset(526f, 356f))
            drawCircle(Color(0xFF22092A), radius = 1.2f, center = Offset(544f, 356f))

            // ----------------------------------------------------
            // 4. Ornate Brass Key (s1_key) at (175, 345)
            // Naturally embedded in the deep carved bark fissure of the giant ancient oak
            // ----------------------------------------------------
            // Deep bark crevice occlusion shadow behind the key
            drawRoundRect(
                color = Color(0x99100904),
                topLeft = Offset(132f, 320f),
                size = Size(90f, 52f),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Key Bow (Ornate Gothic trefoil/quatrefoil loop handle)
            val keyShadow = Color(0xFF4A381C)
            val keyBase = Color(0xFF826938)
            val keyHighlight = Color(0xFFC9A85B)
            val keyVerdigris = Color(0xFF426854)

            // Bow outer ring with carved openwork
            drawCircle(keyShadow, radius = 19f, center = Offset(155f, 345f), style = Stroke(width = 7f))
            drawCircle(keyBase, radius = 19f, center = Offset(155f, 345f), style = Stroke(width = 5.5f))
            drawCircle(keyHighlight, radius = 20.5f, center = Offset(154f, 344f), style = Stroke(width = 1.8f))

            // Verdigris oxidation in inner crevices of handle
            drawCircle(keyVerdigris, radius = 13f, center = Offset(155f, 345f), style = Stroke(width = 1.8f))

            // Key Shaft / Stem (cylindrical with turned decorative collar rings)
            drawLine(keyShadow, Offset(172f, 347f), Offset(218f, 347f), strokeWidth = 7f, cap = StrokeCap.Round)
            drawLine(keyBase, Offset(172f, 345f), Offset(218f, 345f), strokeWidth = 5.5f, cap = StrokeCap.Round)
            drawLine(keyHighlight, Offset(173f, 343.8f), Offset(217f, 343.8f), strokeWidth = 1.8f, cap = StrokeCap.Round)

            // Turned collar rings on shaft
            drawCircle(keyHighlight, radius = 4.2f, center = Offset(177f, 345f))
            drawCircle(keyShadow, radius = 4.2f, center = Offset(177f, 345f), style = Stroke(1.2f))

            // Key Bit (notched cut antique teeth extending down into bark shadow)
            val bitPath = Path().apply {
                moveTo(204f, 345f)
                lineTo(204f, 362f)
                lineTo(218f, 362f)
                lineTo(218f, 345f)
                close()
            }
            drawPath(bitPath, color = keyBase)
            // Bit cuts / notches
            drawLine(Color(0xFF160E07), Offset(209f, 354f), Offset(209f, 363f), strokeWidth = 2.5f)
            drawLine(Color(0xFF160E07), Offset(214f, 350f), Offset(214f, 363f), strokeWidth = 2.5f)
            // Highlight along edge of bit
            drawLine(keyHighlight, Offset(204f, 362f), Offset(218f, 362f), strokeWidth = 1.6f)

            // ----------------------------------------------------
            // 5. Ancient Bronze Coin (s1_coin) at (755, 765)
            // Naturally resting among the smooth riverbed stones, moss & silt
            // ----------------------------------------------------
            // Crevice shadow where coin is nestled between rounded stones
            drawOval(
                color = Color(0x88141812),
                topLeft = Offset(722f, 740f),
                size = Size(68f, 54f)
            )

            val coinBase = Color(0xFF564C38)
            val coinShadow = Color(0xFF383124)
            val coinRelief = Color(0xFF7A6E53)
            val coinRimHighlight = Color(0xFFB8A776)
            val coinVerdigris = Color(0xFF4C6E5A)

            // Slightly irregular antique hammered coin body
            drawCircle(coinShadow, radius = 27.5f, center = Offset(756f, 766f))
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(coinRelief, coinBase, coinShadow),
                    center = Offset(752f, 762f),
                    radius = 28f
                ),
                radius = 26f,
                center = Offset(755f, 765f)
            )

            // Oxidized verdigris patina along crevices
            drawCircle(coinVerdigris, radius = 23.5f, center = Offset(755f, 765f), style = Stroke(width = 2.2f))

            // Raised beaded outer rim
            drawCircle(coinBase, radius = 24.5f, center = Offset(755f, 765f), style = Stroke(width = 3.0f))
            // Sunlight rim glint on upper-left edge
            drawArc(
                color = coinRimHighlight,
                startAngle = 135f,
                sweepAngle = 100f,
                useCenter = false,
                topLeft = Offset(730f, 740f),
                size = Size(50f, 50f),
                style = Stroke(width = 2.0f, cap = StrokeCap.Round)
            )

            // Stamped Classical Laurel / Owl motif in center
            drawCircle(coinRelief, radius = 11f, center = Offset(755f, 765f), style = Stroke(width = 2.5f))
            drawOval(coinRelief, topLeft = Offset(751f, 759f), size = Size(8f, 12f))
            drawCircle(coinRimHighlight, radius = 1.6f, center = Offset(753f, 762f))

            // Riverbed moss overlapping the bottom edge of the coin
            drawCircle(Color(0xFF355227), radius = 4f, center = Offset(748f, 788f))
            drawCircle(Color(0xFF456633), radius = 5f, center = Offset(758f, 789f))
            drawCircle(Color(0xFF355227), radius = 3.5f, center = Offset(768f, 787f))

            // ====================================================
            // Render subtle discovery highlights for found objects
            // ====================================================
            renderDiscoveredHighlights(drawScope, discoveredIds, mapOf(
                "s1_frog" to Offset(265f, 605f),
                "s1_snake" to Offset(875f, 270f),
                "s1_butterfly" to Offset(535f, 390f),
                "s1_key" to Offset(175f, 345f),
                "s1_coin" to Offset(755f, 765f)
            ))
        }
    }

    // ==========================================
    // SCENE 2: GRANDMOTHER'S GARDEN
    // ==========================================
    private fun renderGrandmothersGarden(drawScope: DrawScope, discoveredIds: Set<String>) {
        with(drawScope) {
            // 1. Summer Garden Sky & Trellis Background
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF7FA8B8), Color(0xFFA5C5D4), Color(0xFFC3D8C8))
                ),
                size = Size(1200f, 900f)
            )

            // 2. Rustic Cedar Fence across upper section
            val fenceY = 320f
            for (p in 0..16) {
                val x = p * 75f
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF9E7B58), Color(0xFF7A5C3D)),
                        startY = 180f,
                        endY = 560f
                    ),
                    topLeft = Offset(x + 5f, 180f),
                    size = Size(65f, 380f),
                    cornerRadius = CornerRadius(10f, 10f)
                )
                // Wood grain lines
                drawLine(Color(0xFF5E4329), Offset(x + 20f, 200f), Offset(x + 22f, 520f), strokeWidth = 2f)
                drawLine(Color(0xFF5E4329), Offset(x + 45f, 220f), Offset(x + 43f, 540f), strokeWidth = 2f)
            }
            // Crossbeams
            drawRect(Color(0xFF6B4E32), topLeft = Offset(0f, 250f), size = Size(1200f, 35f))
            drawRect(Color(0xFF6B4E32), topLeft = Offset(0f, 460f), size = Size(1200f, 35f))

            // 3. Flower Bed Earth & Stone Border
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A3525), Color(0xFF332215))
                ),
                topLeft = Offset(0f, 540f),
                size = Size(1200f, 360f)
            )

            // Red brick border stones
            for (b in 0..15) {
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF9A4C38), Color(0xFF7E3927)),
                        startX = b * 80f,
                        endX = (b + 1) * 80f
                    ),
                    topLeft = Offset(b * 80f + 4f, 530f),
                    size = Size(74f, 40f),
                    cornerRadius = CornerRadius(6f, 6f)
                )
            }

            // 4. Climbing Rose Trellis (Left/Center)
            val roseStemColor = Color(0xFF2E6337)
            val stemPath = Path().apply {
                moveTo(250f, 540f)
                cubicTo(320f, 460f, 280f, 360f, 370f, 280f)
                cubicTo(410f, 240f, 450f, 280f, 470f, 200f)
            }
            drawPath(stemPath, color = roseStemColor, style = Stroke(width = 12f, cap = StrokeCap.Round))
            // Leaves on trellis
            for (leaf in 0..6) {
                val lx = 280f + leaf * 28f
                val ly = 500f - leaf * 45f
                drawOval(Color(0xFF3D854A), topLeft = Offset(lx, ly), size = Size(36f, 22f))
            }

            // 5. Terracotta Flower Pots (Center)
            val potColor = Color(0xFFB8623A)
            val potRimColor = Color(0xFF9C4F2B)
            // Main Center Pot
            drawPath(
                path = Path().apply {
                    moveTo(500f, 650f)
                    lineTo(670f, 650f)
                    lineTo(640f, 820f)
                    lineTo(530f, 820f)
                    close()
                },
                color = potColor
            )
            // Pot Rim
            drawRoundRect(potRimColor, topLeft = Offset(480f, 640f), size = Size(210f, 32f), cornerRadius = CornerRadius(8f, 8f))

            // Blooming Hydrangeas inside pot
            drawCircle(Color(0xFF5DADE2), radius = 35f, center = Offset(540f, 620f))
            drawCircle(Color(0xFF85C1E9), radius = 30f, center = Offset(600f, 600f))
            drawCircle(Color(0xFF3498DB), radius = 28f, center = Offset(640f, 630f))

            // 6. Wooden Potting Bench (Right side)
            drawRoundRect(Color(0xFF705335), topLeft = Offset(840f, 520f), size = Size(340f, 35f), cornerRadius = CornerRadius(6f, 6f))
            // Bench legs
            drawRect(Color(0xFF533B23), topLeft = Offset(870f, 555f), size = Size(28f, 280f))
            drawRect(Color(0xFF533B23), topLeft = Offset(1120f, 555f), size = Size(28f, 280f))

            // Vintage Galvanized Watering Can on bench
            drawRoundRect(Color(0xFF7F8C8D), topLeft = Offset(1020f, 440f), size = Size(85f, 80f), cornerRadius = CornerRadius(12f, 12f))
            drawArc(Color(0xFF626567), 180f, 180f, false, topLeft = Offset(1030f, 395f), size = Size(65f, 65f), style = Stroke(8f))

            // Crimson Honeysuckle blossoms on right fence
            drawCircle(Color(0xFFC0392B), radius = 22f, center = Offset(810f, 260f))
            drawCircle(Color(0xFFE74C3C), radius = 25f, center = Offset(850f, 275f))
            drawCircle(Color(0xFFD98880), radius = 16f, center = Offset(830f, 300f))

            // ====================================================
            // CAMOUFLAGED OBJECTS IN SCENE 2
            // ====================================================

            // 1. Praying Mantis (blended along rose stem at 365, 375)
            val mantisGreen = Color(0xFF35703E) // Matches rose stem exactly
            // Slender Thorax
            drawLine(mantisGreen, Offset(350f, 400f), Offset(370f, 360f), strokeWidth = 6f, cap = StrokeCap.Round)
            // Head
            drawOval(mantisGreen, topLeft = Offset(366f, 350f), size = Size(14f, 18f))
            // Folded raptorial front legs
            drawLine(mantisGreen, Offset(370f, 360f), Offset(385f, 350f), strokeWidth = 3f, cap = StrokeCap.Round)
            drawLine(mantisGreen, Offset(385f, 350f), Offset(378f, 365f), strokeWidth = 3f, cap = StrokeCap.Round)
            // Long walking back legs (matching stem thorns)
            drawLine(mantisGreen, Offset(355f, 390f), Offset(340f, 415f), strokeWidth = 2.5f, cap = StrokeCap.Round)
            drawLine(mantisGreen, Offset(350f, 400f), Offset(335f, 425f), strokeWidth = 2.5f, cap = StrokeCap.Round)

            // 2. Hummingbird (blended near red blossoms at 835, 285)
            val birdRuby = Color(0xFFB03A2E) // Blends with blossoms
            val birdGreen = Color(0xFF27AE60)
            // Body
            drawOval(birdRuby, topLeft = Offset(815f, 275f), size = Size(35f, 22f))
            // Head
            drawCircle(birdGreen, radius = 10f, center = Offset(848f, 280f))
            // Long slender needle beak
            drawLine(Color(0xFF1B2631), Offset(856f, 280f), Offset(880f, 285f), strokeWidth = 2.5f)
            // Blurred motion wings (translucent)
            drawOval(Color(0x66B03A2E), topLeft = Offset(815f, 255f), size = Size(20f, 35f))

            // 3. Wooden Seed Packet (blended against fence slats at 205, 530)
            val packetKraft = Color(0xFF8C6D4F) // Weathered kraft paper matches cedar
            val packetBorder = Color(0xFF6E5338)
            drawRoundRect(packetKraft, topLeft = Offset(175f, 495f), size = Size(60f, 75f), cornerRadius = CornerRadius(4f, 4f))
            drawRoundRect(packetBorder, topLeft = Offset(175f, 495f), size = Size(60f, 75f), cornerRadius = CornerRadius(4f, 4f), style = Stroke(2f))
            // Tiny painted flower silhouette on packet
            drawCircle(Color(0xFF5E452E), radius = 8f, center = Offset(205f, 525f))

            // 4. Garden Snail (blended on terracotta pot rim at 585, 670)
            val snailClay = Color(0xFFA6532E) // Blends with terracotta rim
            val shellSpiral = Color(0xFF8A3F1F)
            // Shell
            drawCircle(snailClay, radius = 18f, center = Offset(580f, 652f))
            drawCircle(shellSpiral, radius = 12f, center = Offset(580f, 652f), style = Stroke(2.5f))
            drawCircle(shellSpiral, radius = 6f, center = Offset(580f, 652f), style = Stroke(2f))
            // Soft body gliding along rim
            drawRoundRect(snailClay, topLeft = Offset(570f, 660f), size = Size(40f, 10f), cornerRadius = CornerRadius(5f, 5f))
            // Tiny eye tentacles
            drawLine(snailClay, Offset(605f, 662f), Offset(615f, 652f), strokeWidth = 2f)

            // 5. Garden Trowel (hanging by potting bench at 945, 595)
            val trowelMetal = Color(0xFF685D54) // Weathered metal matches bench shadow
            val trowelHandle = Color(0xFF543E2B)
            // Wooden Handle
            drawRoundRect(trowelHandle, topLeft = Offset(940f, 550f), size = Size(14f, 38f), cornerRadius = CornerRadius(4f, 4f))
            // Metal Scoop / Blade
            val trowelPath = Path().apply {
                moveTo(938f, 588f)
                lineTo(956f, 588f)
                lineTo(958f, 630f)
                lineTo(947f, 646f)
                lineTo(936f, 630f)
                close()
            }
            drawPath(trowelPath, color = trowelMetal)
            drawPath(trowelPath, color = Color(0xFF423428), style = Stroke(2f))

            // Highlights
            renderDiscoveredHighlights(drawScope, discoveredIds, mapOf(
                "s2_mantis" to Offset(365f, 375f),
                "s2_hummingbird" to Offset(835f, 285f),
                "s2_seed_packet" to Offset(205f, 530f),
                "s2_snail" to Offset(585f, 670f),
                "s2_trowel" to Offset(945f, 595f)
            ))
        }
    }

    // ==========================================
    // SCENE 3: MESSY GARAGE
    // ==========================================
    private fun renderMessyGarage(drawScope: DrawScope, discoveredIds: Set<String>) {
        with(drawScope) {
            // 1. Garage Wall & Floor Background
            drawRect(Color(0xFF2B2D2F), size = Size(1200f, 900f)) // Dark workshop mood
            // Concrete Floor with oil stains
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF45484B), Color(0xFF383A3C), Color(0xFF2C2E30)),
                    startY = 580f,
                    endY = 900f
                ),
                topLeft = Offset(0f, 580f),
                size = Size(1200f, 320f)
            )

            // 2. Pegboard with Tool Silhouettes (Upper Left)
            drawRect(Color(0xFF564534), topLeft = Offset(60f, 60f), size = Size(460f, 360f))
            // Peg holes grid
            for (r in 0..11) {
                for (c in 0..15) {
                    drawCircle(Color(0xFF32261B), radius = 2.5f, center = Offset(85f + c * 28f, 85f + r * 28f))
                }
            }
            // Hanging tool outlines on pegboard (wrench, saw, hammer)
            drawRoundRect(Color(0x55000000), topLeft = Offset(110f, 100f), size = Size(35f, 140f), cornerRadius = CornerRadius(8f, 8f))
            drawRoundRect(Color(0x55000000), topLeft = Offset(240f, 120f), size = Size(110f, 40f), cornerRadius = CornerRadius(6f, 6f))

            // 3. Vintage Workshop Sign / Wall Stencil: JASON DRECE
            // Requirement: "The Messy Garage may naturally contain: Jason Drece as environmental text on: cabinet, drawer, workshop sign, toolbox, wall."
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF8A6538), Color(0xFF6D4E27)),
                    startX = 600f,
                    endX = 1000f
                ),
                topLeft = Offset(600f, 70f),
                size = Size(420f, 75f),
                cornerRadius = CornerRadius(6f, 6f)
            )
            drawRoundRect(Color(0xFF4A3418), topLeft = Offset(600f, 70f), size = Size(420f, 75f), cornerRadius = CornerRadius(6f, 6f), style = Stroke(3f))

            // Paint text on sign natively
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    color = android.graphics.Color.parseColor("#EAD6B8")
                    textSize = 30f
                    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                    isAntiAlias = true
                    alpha = 210 // Naturally weathered stencil
                }
                canvas.nativeCanvas.drawText("JASON DRECE WORKSHOP", 630f, 118f, paint)

                val smallPaint = Paint().apply {
                    color = android.graphics.Color.parseColor("#9E2A2B")
                    textSize = 14f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    isAntiAlias = true
                }
                canvas.nativeCanvas.drawText("EST. TOOLS & CRAFT", 730f, 138f, smallPaint)
            }

            // 4. Heavy Steel Tool Chest / Cabinet (Center 480..660, 420..680)
            val cabinetRed = Color(0xFF9E2A2B)
            val cabinetDark = Color(0xFF751F20)
            drawRoundRect(cabinetRed, topLeft = Offset(480f, 440f), size = Size(180f, 260f), cornerRadius = CornerRadius(6f, 6f))
            drawRoundRect(cabinetDark, topLeft = Offset(480f, 440f), size = Size(180f, 260f), cornerRadius = CornerRadius(6f, 6f), style = Stroke(3f))
            // Cabinet drawers
            for (d in 0..4) {
                val dy = 460f + d * 45f
                drawRoundRect(cabinetDark, topLeft = Offset(495f, dy), size = Size(150f, 36f), cornerRadius = CornerRadius(4f, 4f))
                // Chrome drawer handle
                drawRoundRect(Color(0xFFBDC3C7), topLeft = Offset(545f, dy + 12f), size = Size(50f, 8f), cornerRadius = CornerRadius(3f, 3f))
            }
            // "J. DRECE" small embossed label on top drawer
            drawIntoCanvas { canvas ->
                val drawerPaint = Paint().apply {
                    color = android.graphics.Color.parseColor("#CCCCCC")
                    textSize = 10f
                    typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    isAntiAlias = true
                    alpha = 180
                }
                canvas.nativeCanvas.drawText("J. DRECE - PRECISION", 515f, 482f, drawerPaint)
            }

            // 5. Vintage Bicycle (Left side 120..320, 420..740)
            // Big Wheels
            drawCircle(Color(0xFF222222), radius = 75f, center = Offset(180f, 620f), style = Stroke(8f)) // Rear wheel
            drawCircle(Color(0xFF222222), radius = 75f, center = Offset(360f, 620f), style = Stroke(8f)) // Front wheel
            // Frame tubes (vintage green)
            val bikeFrame = Color(0xFF1E5128)
            drawLine(bikeFrame, Offset(180f, 620f), Offset(270f, 620f), strokeWidth = 8f) // Chainstay
            drawLine(bikeFrame, Offset(270f, 620f), Offset(250f, 520f), strokeWidth = 8f) // Seat tube
            drawLine(bikeFrame, Offset(180f, 620f), Offset(250f, 520f), strokeWidth = 6f) // Seatstay
            drawLine(bikeFrame, Offset(250f, 520f), Offset(340f, 520f), strokeWidth = 8f) // Top tube
            drawLine(bikeFrame, Offset(270f, 620f), Offset(340f, 520f), strokeWidth = 8f) // Down tube
            drawLine(bikeFrame, Offset(340f, 520f), Offset(360f, 620f), strokeWidth = 8f) // Fork
            // Handlebars & Seat
            drawRoundRect(Color(0xFF333333), topLeft = Offset(230f, 505f), size = Size(35f, 15f), cornerRadius = CornerRadius(5f, 5f))

            // Bicycle spokes (dark wire spokes)
            for (sp in 0..7) {
                val angle = sp * (PI / 4).toFloat()
                drawLine(
                    Color(0xFF4F555A),
                    Offset(180f, 620f),
                    Offset(180f + 70f * cos(angle), 620f + 70f * sin(angle)),
                    strokeWidth = 2f
                )
            }

            // 6. Workbench with Wooden Ruler (Right side 700..1150, 360..600)
            drawRoundRect(Color(0xFF8B5A2B), topLeft = Offset(680f, 360f), size = Size(480f, 40f), cornerRadius = CornerRadius(6f, 6f))
            drawRect(Color(0xFF5C3A1E), topLeft = Offset(720f, 400f), size = Size(30f, 380f))
            drawRect(Color(0xFF5C3A1E), topLeft = Offset(1100f, 400f), size = Size(30f, 380f))

            // Wooden ruler on workbench
            val rulerColor = Color(0xFFD4AC0D)
            drawRoundRect(rulerColor, topLeft = Offset(810f, 370f), size = Size(220f, 18f), cornerRadius = CornerRadius(2f, 2f))
            for (tick in 0..20) {
                drawLine(Color(0xFF5B4505), Offset(820f + tick * 10f, 370f), Offset(820f + tick * 10f, 378f), strokeWidth = 1.5f)
            }

            // Cardboard packing box (Lower Right 700..880, 560..760)
            val boxColor = Color(0xFFB8860B)
            drawRect(boxColor, topLeft = Offset(700f, 570f), size = Size(170f, 170f))
            // Box flaps & shadow
            drawLine(Color(0xFF8B6508), Offset(700f, 650f), Offset(870f, 650f), strokeWidth = 3f)
            // Yellow packing caution tape across box
            drawRect(Color(0xFFF1C40F), topLeft = Offset(700f, 630f), size = Size(170f, 35f))

            // ====================================================
            // CAMOUFLAGED OBJECTS IN SCENE 3
            // ====================================================

            // 1. Precision Screwdriver (blended along bicycle spokes at 235, 505)
            val sdriverMetal = Color(0xFF485158) // Metal shank matches bike stay
            val sdriverHandle = Color(0xFF1E2830)
            // Shaft
            drawLine(sdriverMetal, Offset(210f, 535f), Offset(255f, 495f), strokeWidth = 3.5f, cap = StrokeCap.Round)
            // Handle
            drawLine(sdriverHandle, Offset(250f, 500f), Offset(275f, 478f), strokeWidth = 9f, cap = StrokeCap.Round)

            // 2. Yellow Measuring Tape (blended with yellow packing tape at 775, 650)
            val tapeYellow = Color(0xFFF4D03F) // Matches tape stripe
            val tapeBlackTrim = Color(0xFF2C3E50)
            drawRoundRect(tapeYellow, topLeft = Offset(755f, 632f), size = Size(42f, 38f), cornerRadius = CornerRadius(6f, 6f))
            drawRoundRect(tapeBlackTrim, topLeft = Offset(755f, 632f), size = Size(42f, 38f), cornerRadius = CornerRadius(6f, 6f), style = Stroke(2.5f))
            // Measuring lip
            drawRect(Color(0xFF7F8C8D), topLeft = Offset(797f, 655f), size = Size(6f, 12f))

            // 3. Brass Padlock (fastened on metal tool chest at 545, 540)
            val padlockBody = Color(0xFF8E6B23) // Heavy brass matches vintage cabinet
            val shackleColor = Color(0xFF525B62)
            // Shackle (U-shaped arc)
            drawArc(shackleColor, 180f, 180f, false, topLeft = Offset(534f, 520f), size = Size(24f, 26f), style = Stroke(4f))
            // Body
            drawRoundRect(padlockBody, topLeft = Offset(530f, 534f), size = Size(32f, 26f), cornerRadius = CornerRadius(4f, 4f))
            // Keyhole
            drawCircle(Color(0xFF2C1E0A), radius = 2.5f, center = Offset(546f, 544f))
            drawLine(Color(0xFF2C1E0A), Offset(546f, 544f), Offset(546f, 552f), strokeWidth = 2f)

            // 4. Vintage Oil Can (tucked beneath workbench shelf at 405, 730)
            val oilCanColor = Color(0xFF3E4348) // Blended into deep workshop shadow
            // Can base
            drawRoundRect(oilCanColor, topLeft = Offset(385f, 720f), size = Size(46f, 50f), cornerRadius = CornerRadius(8f, 8f))
            // Curved spout
            val spoutPath = Path().apply {
                moveTo(408f, 720f)
                cubicTo(408f, 695f, 425f, 685f, 435f, 680f)
            }
            drawPath(spoutPath, color = oilCanColor, style = Stroke(width = 4f, cap = StrokeCap.Round))

            // 5. Carpenter's Pencil (resting along ruler on workbench at 895, 430)
            val pencilRed = Color(0xFF8B2500) // Blends with wooden workbench
            val leadColor = Color(0xFF2B2B2B)
            // Flat octagonal body
            drawRoundRect(pencilRed, topLeft = Offset(865f, 420f), size = Size(75f, 12f), cornerRadius = CornerRadius(2f, 2f))
            // Sharpened lead tip
            val tipPath = Path().apply {
                moveTo(865f, 421f)
                lineTo(855f, 426f)
                lineTo(865f, 431f)
                close()
            }
            drawPath(tipPath, color = leadColor)

            // Highlights
            renderDiscoveredHighlights(drawScope, discoveredIds, mapOf(
                "s3_screwdriver" to Offset(235f, 505f),
                "s3_measuring_tape" to Offset(775f, 650f),
                "s3_padlock" to Offset(545f, 540f),
                "s3_oil_can" to Offset(405f, 730f),
                "s3_pencil" to Offset(895f, 430f)
            ))
        }
    }

    // Atmospheric scene renderer for scenes 4 through 20
    private fun renderGenericAtmosphericScene(
        drawScope: DrawScope,
        sceneId: Int,
        discoveredIds: Set<String>
    ) {
        with(drawScope) {
            val scene = SceneRegistry.getSceneById(sceneId)
            val hue = when (sceneId) {
                4 -> 135f // Jungle deep jade
                5 -> 28f  // Living room amber wood
                6 -> 38f  // Kitchen warm copper terracotta
                7 -> 48f  // Attic vintage brass
                8 -> 340f // Bedroom rose mauve
                9 -> 45f  // Beach warm golden dune
                10 -> 210f // Harbor marine navy
                11 -> 185f // Coral reef turquoise aqua
                12 -> 35f  // Pirate island tropical adventure
                13 -> 215f // City slate rain
                14 -> 22f  // Market rich saffron cinnamon
                15 -> 30f  // Train station mahogany steam
                16 -> 280f // Midnight neon violet cyan
                17 -> 220f // Castle gothic stone cobalt
                18 -> 265f // Wizard laboratory astral amethyst
                19 -> 15f  // Dragon cave obsidian crimson ember
                20 -> 230f // Space station deep cosmic stellar
                else -> ((sceneId * 47) % 360).toFloat()
            }

            // 1. Layered Environmental Gradient Canvas
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.hsl(hue, 0.48f, 0.14f),
                        Color.hsl((hue + 18f) % 360, 0.42f, 0.24f),
                        Color.hsl((hue + 36f) % 360, 0.52f, 0.16f)
                    )
                ),
                size = Size(1200f, 900f)
            )

            // 2. Architectural / Landscape structures
            // Ground / Floor / Horizon tier
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.hsl(hue, 0.35f, 0.28f),
                        Color.hsl(hue, 0.45f, 0.18f)
                    )
                ),
                topLeft = Offset(0f, 540f),
                size = Size(1200f, 360f)
            )

            // Environmental pillars / trees / shelves / consoles
            for (p in 0..4) {
                val px = 80f + p * 240f
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.hsl(hue, 0.3f, 0.26f),
                            Color.hsl((hue + 20f) % 360, 0.35f, 0.36f),
                            Color.hsl(hue, 0.3f, 0.20f)
                        ),
                        startX = px,
                        endX = px + 140f
                    ),
                    topLeft = Offset(px, 120f + (p % 2) * 50f),
                    size = Size(130f, 560f),
                    cornerRadius = CornerRadius(12f, 12f)
                )
            }

            // Subtle atmospheric lighting beams
            val beamBrush = Brush.linearGradient(
                colors = listOf(
                    Color.hsl((hue + 60f) % 360, 0.6f, 0.7f, 0.16f),
                    Color.Transparent
                ),
                start = Offset(200f, 0f),
                end = Offset(800f, 900f)
            )
            drawPath(
                path = Path().apply {
                    moveTo(180f, 0f)
                    lineTo(420f, 0f)
                    lineTo(880f, 900f)
                    lineTo(640f, 900f)
                    close()
                },
                brush = beamBrush
            )

            // 3. Camouflaged environmental target silhouettes
            scene?.targets?.forEach { target ->
                val box = target.hitBox
                val isDiscovered = target.id in discoveredIds
                val itemHue = (hue + 25f) % 360

                // Draw naturally integrated organic object shape inside its hitbox
                drawRoundRect(
                    color = Color.hsl(
                        itemHue,
                        0.45f,
                        0.32f,
                        if (isDiscovered) 0.85f else 0.55f
                    ),
                    topLeft = Offset(box.left + 10f, box.top + 10f),
                    size = Size(box.width - 20f, box.height - 20f),
                    cornerRadius = CornerRadius(14f, 14f)
                )
                // Subtle fine pattern texture line inside
                drawLine(
                    color = Color.hsl(itemHue, 0.55f, 0.44f, 0.45f),
                    start = Offset(box.left + 22f, box.top + box.height / 2f),
                    end = Offset(box.right - 22f, box.top + box.height / 2f),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }

            // 4. Discovered Highlights
            if (scene != null) {
                val targetsMap = scene.targets.associate { it.id to it.hintLocation }
                renderDiscoveredHighlights(drawScope, discoveredIds, targetsMap)
            }
        }
    }

    private fun renderDiscoveredHighlights(
        drawScope: DrawScope,
        discoveredIds: Set<String>,
        targets: Map<String, Offset>
    ) {
        with(drawScope) {
            for ((id, pos) in targets) {
                if (id in discoveredIds) {
                    // Soft glowing golden-emerald discovered circle
                    drawCircle(
                        color = Color(0x3348C774),
                        radius = 48f,
                        center = pos
                    )
                    drawCircle(
                        color = Color(0xFF2ECC71),
                        radius = 38f,
                        center = pos,
                        style = Stroke(width = 3.5f)
                    )
                    // Checkmark badge in center
                    drawCircle(
                        color = Color(0xFF27AE60),
                        radius = 14f,
                        center = Offset(pos.x + 24f, pos.y - 24f)
                    )
                    // Checkmark stroke
                    val check = Path().apply {
                        moveTo(pos.x + 19f, pos.y - 24f)
                        lineTo(pos.x + 23f, pos.y - 20f)
                        lineTo(pos.x + 29f, pos.y - 28f)
                    }
                    drawPath(check, color = Color.White, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
                }
            }
        }
    }
}
