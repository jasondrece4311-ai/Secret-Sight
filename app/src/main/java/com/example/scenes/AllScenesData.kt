package com.example.scenes

import androidx.compose.ui.geometry.Offset
import com.example.model.SceneCategory
import com.example.model.SceneDefinition
import com.example.model.SceneRect
import com.example.model.SceneTarget

object AllScenesData {

    // Scene 4: Jungle Ruins
    val scene4 = SceneDefinition(
        id = 4,
        title = "Jungle Ruins",
        subtitle = "Ancient stone pillars, wild vines & tropical canopy",
        category = SceneCategory.ADVENTURE,
        ambientAudioKey = "ruins",
        targets = listOf(
            SceneTarget("s4_chameleon", "Panther Chameleon", "Camouflaged against the lichen on the central pillar", SceneRect(320f, 380f, 430f, 490f), Offset(375f, 435f)),
            SceneTarget("s4_mayan_mask", "Carved Jade Mask", "Embedded in the leafy ivy covering the stone shrine", SceneRect(680f, 220f, 790f, 330f), Offset(735f, 275f)),
            SceneTarget("s4_vine_lizard", "Emerald Skink", "Basking on a fallen tropical monstera branch", SceneRect(180f, 620f, 290f, 730f), Offset(235f, 675f)),
            SceneTarget("s4_golden_idol", "Miniature Golden Idol", "Hidden among the roots of the strangler fig tree", SceneRect(850f, 600f, 960f, 710f), Offset(905f, 655f)),
            SceneTarget("s4_stone_dagger", "Obsidian Dagger", "Resting in the cleft of the stepped ruin staircase", SceneRect(500f, 700f, 610f, 810f), Offset(555f, 755f))
        )
    )

    // Scene 5: Cozy Living Room
    val scene5 = SceneDefinition(
        id = 5,
        title = "Cozy Living Room",
        subtitle = "Fireplace, bookshelf, Persian rug & comfy armchair",
        category = SceneCategory.COZY,
        ambientAudioKey = "living_room",
        targets = listOf(
            SceneTarget("s5_reading_glasses", "Reading Glasses", "Folded quietly along the edge of the mahogany mantel", SceneRect(440f, 260f, 550f, 360f), Offset(495f, 310f)),
            SceneTarget("s5_brass_bell", "Brass Hearth Bell", "Standing beside the stacked birch firewood logs", SceneRect(240f, 580f, 350f, 680f), Offset(295f, 630f)),
            SceneTarget("s5_pocket_watch", "Pocket Watch", "Tucked into the woven fringe of the patterned armchair", SceneRect(720f, 480f, 830f, 580f), Offset(775f, 530f)),
            SceneTarget("s5_porcelain_bird", "Porcelain Finch", "Resting amongst vintage hardcovers on the shelf", SceneRect(880f, 180f, 990f, 280f), Offset(935f, 230f)),
            SceneTarget("s5_silver_thimble", "Silver Thimble", "Blended into the floral motif of the Persian carpet", SceneRect(520f, 720f, 630f, 820f), Offset(575f, 770f))
        )
    )

    // Scene 6: Old World Kitchen
    val scene6 = SceneDefinition(
        id = 6,
        title = "Old World Kitchen",
        subtitle = "Pantry shelves, copper pans, dried herbs & butcher block",
        category = SceneCategory.COZY,
        ambientAudioKey = "kitchen",
        targets = listOf(
            SceneTarget("s6_nutmeg_grater", "Nutmeg Grater", "Hanging flush with the copper cookware rack", SceneRect(310f, 200f, 410f, 300f), Offset(360f, 250f)),
            SceneTarget("s6_ceramic_egg", "Ceramic Nesting Egg", "Placed among the farm eggs inside the wicker basket", SceneRect(740f, 460f, 850f, 560f), Offset(795f, 510f)),
            SceneTarget("s6_rosemary_twine", "Herb Scissors", "Camouflaged behind hanging bundles of dried lavender", SceneRect(160f, 280f, 270f, 390f), Offset(215f, 335f)),
            SceneTarget("s6_cookie_press", "Brass Cookie Stamp", "Resting along the grain of the oak butcher block", SceneRect(510f, 540f, 620f, 640f), Offset(565f, 590f)),
            SceneTarget("s6_carved_spoon", "Carved Wooden Spoon", "Tucked alongside the antique stoneware spice crocks", SceneRect(890f, 320f, 1000f, 420f), Offset(945f, 370f))
        )
    )

    // Scene 7: Attic of Curiosities
    val scene7 = SceneDefinition(
        id = 7,
        title = "Attic of Curiosities",
        subtitle = "Vintage trunks, brass clocks, telescopes & dust sheets",
        category = SceneCategory.MYSTERY,
        ambientAudioKey = "camp",
        targets = listOf(
            SceneTarget("s7_kaleidoscope", "Brass Kaleidoscope", "Resting parallel to the brass telescope tripod legs", SceneRect(260f, 340f, 370f, 450f), Offset(315f, 395f)),
            SceneTarget("s7_music_box_key", "Music Box Winder", "Tucked into the ornate filigree of the leather steamer trunk", SceneRect(630f, 520f, 740f, 620f), Offset(685f, 570f)),
            SceneTarget("s7_tin_soldier", "Tin Toy Soldier", "Guarding the shadows behind a stack of aged sheet music", SceneRect(810f, 380f, 920f, 490f), Offset(865f, 435f)),
            SceneTarget("s7_hourglass", "Pocket Hourglass", "Blended against the pendulum case of the grandfather clock", SceneRect(460f, 210f, 570f, 320f), Offset(515f, 265f)),
            SceneTarget("s7_magnifier", "Ivory Magnifying Glass", "Reflecting subtle light upon the linen dressmaker mannequin", SceneRect(140f, 560f, 250f, 670f), Offset(195f, 615f))
        )
    )

    // Scene 8: Quiet Bedroom
    val scene8 = SceneDefinition(
        id = 8,
        title = "Quiet Bedroom",
        subtitle = "Patchwork quilt, cedar dresser & morning window light",
        category = SceneCategory.COZY,
        ambientAudioKey = "bedroom",
        targets = listOf(
            SceneTarget("s8_silk_ribbon", "Silk Ribbon Bow", "Blending into the rose-patterned floral wallpaper", SceneRect(220f, 210f, 330f, 320f), Offset(275f, 265f)),
            SceneTarget("s8_pearl_brooch", "Pearl Hairpin", "Pinned into the lace trim of the embroidered pillow", SceneRect(680f, 390f, 790f, 490f), Offset(735f, 440f)),
            SceneTarget("s8_wooden_comb", "Sandalwood Comb", "Resting across the wood inlay of the vanity dressing table", SceneRect(860f, 510f, 970f, 610f), Offset(915f, 560f)),
            SceneTarget("s8_embroidered_bookmark", "Linen Bookmark", "Slipping out of the poetry anthology on the nightstand", SceneRect(390f, 470f, 500f, 570f), Offset(445f, 520f)),
            SceneTarget("s8_velvet_slipper", "Tiny Knitted Slipper", "Tucked into the soft shadow beside the bedside woven rug", SceneRect(530f, 680f, 640f, 790f), Offset(585f, 735f))
        )
    )

    // Scene 9: Sunlit Beach
    val scene9 = SceneDefinition(
        id = 9,
        title = "Sunlit Beach",
        subtitle = "Golden dunes, tide pools, sea glass & weathered driftwood",
        category = SceneCategory.NATURE,
        ambientAudioKey = "beach",
        targets = listOf(
            SceneTarget("s9_ghost_crab", "Ghost Crab", "Perfect pale sand camouflage beside the sea oat grass", SceneRect(740f, 580f, 850f, 690f), Offset(795f, 635f)),
            SceneTarget("s9_sea_glass", "Emerald Sea Glass", "Gleaming wet among tide pool pebbles and green sea lettuce", SceneRect(340f, 640f, 450f, 750f), Offset(395f, 695f)),
            SceneTarget("s9_sand_dollar", "Sand Dollar", "Imprinted lightly into the wet shoreline sandbar", SceneRect(540f, 490f, 650f, 590f), Offset(595f, 540f)),
            SceneTarget("s9_driftwood_seahorse", "Carved Driftwood Charm", "Nestled in the crags of the sun-bleached cypress log", SceneRect(170f, 390f, 280f, 500f), Offset(225f, 445f)),
            SceneTarget("s9_nautilus", "Pearl Nautilus Shell", "Resting in the foam line where the incoming tide recedes", SceneRect(880f, 360f, 990f, 470f), Offset(935f, 415f))
        )
    )

    // Scene 10: Historic Harbor
    val scene10 = SceneDefinition(
        id = 10,
        title = "Historic Harbor",
        subtitle = "Tangled hemp ropes, wooden piers, anchors & fishing skiffs",
        category = SceneCategory.ADVENTURE,
        ambientAudioKey = "harbor",
        targets = listOf(
            SceneTarget("s10_marlin_spike", "Sailor's Marlinspike", "Tucked into the braided coil of thick mooring line", SceneRect(280f, 520f, 390f, 630f), Offset(335f, 575f)),
            SceneTarget("s10_brass_compass", "Pocket Mariner's Compass", "Resting in the tackle drawer of the harbormaster's skiff", SceneRect(650f, 420f, 760f, 530f), Offset(705f, 475f)),
            SceneTarget("s10_glass_float", "Green Glass Fishing Float", "Tangled among dried seaweed inside the nylon lobster trap", SceneRect(810f, 580f, 920f, 690f), Offset(865f, 635f)),
            SceneTarget("s10_pewter_shilling", "Pewter Harbor Token", "Wedged into the weathered crevice of the oak bollard post", SceneRect(480f, 310f, 590f, 420f), Offset(535f, 365f)),
            SceneTarget("s10_brass_telescope", "Collapsible Spyglass", "Resting along the gunwale timber of the dory boat", SceneRect(150f, 360f, 260f, 470f), Offset(205f, 415f))
        )
    )

    // Scene 11: Coral Reef
    val scene11 = SceneDefinition(
        id = 11,
        title = "Coral Reef",
        subtitle = "Azure currents, sea fans, brain coral & tropical schools",
        category = SceneCategory.NATURE,
        ambientAudioKey = "coral_reef",
        targets = listOf(
            SceneTarget("s11_seahorse", "Pygmy Seahorse", "Holding tightly to the pink sea fan gorgonian branch", SceneRect(350f, 270f, 460f, 380f), Offset(405f, 325f)),
            SceneTarget("s11_stonefish", "Stonefish", "Blended indistinguishably against the volcanic seafloor rock", SceneRect(620f, 680f, 730f, 790f), Offset(675f, 735f)),
            SceneTarget("s11_cowrie", "Tiger Cowrie Shell", "Tucked into the shady hollow beneath the brain coral", SceneRect(780f, 450f, 890f, 560f), Offset(835f, 505f)),
            SceneTarget("s11_pipefish", "Banded Pipefish", "Hovering vertically among gently swaying green kelp fronds", SceneRect(190f, 430f, 300f, 540f), Offset(245f, 485f)),
            SceneTarget("s11_sunken_ring", "Golden Sailor's Band", "Encrusted with pink coralline algae on the coral ledge", SceneRect(510f, 540f, 620f, 650f), Offset(565f, 595f))
        )
    )

    // Scene 12: Pirate Island Hideout
    val scene12 = SceneDefinition(
        id = 12,
        title = "Pirate Island Hideout",
        subtitle = "Hidden sea cave, treasure chests, rum barrels & skull rock",
        category = SceneCategory.ADVENTURE,
        ambientAudioKey = "pirate_island",
        targets = listOf(
            SceneTarget("s12_doubloon", "Gold Doubloon", "Gleaming softly under the brim of the pirate tricorn hat", SceneRect(420f, 380f, 530f, 490f), Offset(475f, 435f)),
            SceneTarget("s12_flintlock_powder", "Ivory Powder Horn", "Hung against the weathered iron straps of the rum barrel", SceneRect(720f, 510f, 830f, 620f), Offset(775f, 565f)),
            SceneTarget("s12_carved_pipe", "Meerschaum Pipe", "Resting in the sandy skull eye socket in the limestone wall", SceneRect(850f, 220f, 960f, 330f), Offset(905f, 275f)),
            SceneTarget("s12_skeleton_key", "Iron Strongbox Key", "Lying among rusty cutlass blades in the weapons rack", SceneRect(240f, 580f, 350f, 690f), Offset(295f, 635f)),
            SceneTarget("s12_wax_sealed_map", "Parchment Island Chart", "Rolled tight and tucked between cannonballs in the crate", SceneRect(560f, 670f, 670f, 780f), Offset(615f, 725f))
        )
    )

    // Scene 13: Rainy City Street
    val scene13 = SceneDefinition(
        id = 13,
        title = "Rainy City Street",
        subtitle = "Cobblestone boulevard, yellow taxis, umbrellas & warm café",
        category = SceneCategory.URBAN,
        ambientAudioKey = "city",
        targets = listOf(
            SceneTarget("s13_dropped_token", "Subway Transit Token", "Glittering beneath the rain puddle reflection on asphalt", SceneRect(310f, 690f, 420f, 800f), Offset(365f, 745f)),
            SceneTarget("s13_brass_door_knocker", "Fox Head Knocker", "Mounted seamlessly onto the brownstone entry portal", SceneRect(760f, 340f, 870f, 450f), Offset(815f, 395f)),
            SceneTarget("s13_yellow_sparrow", "Urban Finch", "Sheltering under the scalloped awning of the bakery", SceneRect(520f, 240f, 630f, 350f), Offset(575f, 295f)),
            SceneTarget("s13_antique_newspaper", "Folded Daily Chronicle", "Tucked behind the wrought-iron café patio chair", SceneRect(170f, 530f, 280f, 640f), Offset(225f, 585f)),
            SceneTarget("s13_cat_silhouette", "Sleeping Alley Cat", "Curled camouflaged atop the warm iron heating grate", SceneRect(860f, 590f, 970f, 700f), Offset(915f, 645f))
        )
    )

    // Scene 14: Artisan Market
    val scene14 = SceneDefinition(
        id = 14,
        title = "Artisan Market",
        subtitle = "Burlap spice mounds, woven kilims, glazed pots & lanterns",
        category = SceneCategory.URBAN,
        ambientAudioKey = "market",
        targets = listOf(
            SceneTarget("s14_saffron_vial", "Glass Saffron Vial", "Nestled among vivid turmeric and paprika mounds", SceneRect(440f, 540f, 550f, 650f), Offset(495f, 595f)),
            SceneTarget("s14_filigree_earring", "Silver Filigree Hoop", "Hooked into the geometric weave of the hanging tapestry", SceneRect(730f, 260f, 840f, 370f), Offset(785f, 315f)),
            SceneTarget("s14_carved_scarab", "Lapis Lazuli Scarab", "Resting in a ceramic dish of polished river agates", SceneRect(260f, 420f, 370f, 530f), Offset(315f, 475f)),
            SceneTarget("s14_brass_bell_charm", "Camel Bell Charm", "Tied into the hemp tassel of the leather merchant pack", SceneRect(850f, 490f, 960f, 600f), Offset(905f, 545f)),
            SceneTarget("s14_clay_oil_lamp", "Terracotta Aladdin Lamp", "Resting on the brick ledge behind brass lanterns", SceneRect(580f, 330f, 690f, 440f), Offset(635f, 385f))
        )
    )

    // Scene 15: Nostalgic Train Station
    val scene15 = SceneDefinition(
        id = 15,
        title = "Nostalgic Train Station",
        subtitle = "Steam engine tracks, wooden benches, brass bell & departure board",
        category = SceneCategory.URBAN,
        ambientAudioKey = "train_station",
        targets = listOf(
            SceneTarget("s15_punched_ticket", "Punched Parlor Car Ticket", "Tucked into the slats of the polished oak passenger bench", SceneRect(360f, 560f, 470f, 670f), Offset(415f, 615f)),
            SceneTarget("s15_conductor_whistle", "Silver Conductor's Whistle", "Hanging from the luggage strap on the brass porter trolley", SceneRect(740f, 460f, 850f, 570f), Offset(795f, 515f)),
            SceneTarget("s15_brass_watch_key", "Station Clock Winding Key", "Lying on the marble pedestal below the grand station clock", SceneRect(560f, 220f, 670f, 330f), Offset(615f, 275f)),
            SceneTarget("s15_leather_tag", "Monogrammed Luggage Tag", "Blended against the brown calfskin leather trunk", SceneRect(180f, 620f, 290f, 730f), Offset(235f, 675f)),
            SceneTarget("s15_railroad_lantern", "Miniature Train Lantern", "Resting in the cast iron bracket of the track signal switch", SceneRect(880f, 350f, 990f, 460f), Offset(935f, 405f))
        )
    )

    // Scene 16: Midnight Neon Alley
    val scene16 = SceneDefinition(
        id = 16,
        title = "Midnight Neon Alley",
        subtitle = "Cyberpunk rain reflections, neon tubes, steel pipes & fire escapes",
        category = SceneCategory.URBAN,
        ambientAudioKey = "night_city",
        targets = listOf(
            SceneTarget("s16_data_drive", "Holographic Memory Drive", "Blended along the luminescent cyan cables on the wall conduit", SceneRect(310f, 380f, 420f, 490f), Offset(365f, 435f)),
            SceneTarget("s16_neon_origami", "Fluorescent Crane", "Perched atop the glowing ramen shop neon sign", SceneRect(690f, 190f, 800f, 300f), Offset(745f, 245f)),
            SceneTarget("s16_cybernetic_eye", "Ocular Camera Lens", "Hidden within the ventilation grill of the industrial air duct", SceneRect(840f, 480f, 950f, 590f), Offset(895f, 535f)),
            SceneTarget("s16_electronic_keycard", "VIP Penthouse Keycard", "Floating along the magenta puddle reflections by the curb", SceneRect(490f, 700f, 600f, 810f), Offset(545f, 755f)),
            SceneTarget("s16_micro_drone", "Hover Hummingbird Drone", "Silently docked beneath the rusty steel fire escape rung", SceneRect(160f, 260f, 270f, 370f), Offset(215f, 315f))
        )
    )

    // Scene 17: Medieval Castle Hall
    val scene17 = SceneDefinition(
        id = 17,
        title = "Medieval Castle Hall",
        subtitle = "Heraldic banners, suits of armor, stone hearth & candle chandeliers",
        category = SceneCategory.FANTASY,
        ambientAudioKey = "castle",
        targets = listOf(
            SceneTarget("s17_heraldic_signet", "Royal Seal Signet Ring", "Resting on the velvet arm of the high-backed throne", SceneRect(570f, 450f, 680f, 560f), Offset(625f, 505f)),
            SceneTarget("s17_silver_dagger", "Engraved Dagger Pommel", "Tucked alongside the scabbard of the full plate armor", SceneRect(240f, 340f, 350f, 450f), Offset(295f, 395f)),
            SceneTarget("s17_carved_chess_knight", "Ebony Knight Piece", "Blended into the intricate wood grain of the banquet table", SceneRect(780f, 580f, 890f, 690f), Offset(835f, 635f)),
            SceneTarget("s17_falcon_hood", "Embroidered Falconry Hood", "Hanging beside the mounted antlers above the stone mantel", SceneRect(420f, 220f, 530f, 330f), Offset(475f, 275f)),
            SceneTarget("s17_wax_seal_stamp", "Brass Griffin Stamp", "Resting among ancient sheepskin scrolls in the chest", SceneRect(870f, 360f, 980f, 470f), Offset(925f, 415f))
        )
    )

    // Scene 18: Wizard's Laboratory
    val scene18 = SceneDefinition(
        id = 18,
        title = "Wizard's Laboratory",
        subtitle = "Glowing glass alembics, grimoires, crystals & celestial globes",
        category = SceneCategory.FANTASY,
        ambientAudioKey = "wizard_workshop",
        targets = listOf(
            SceneTarget("s18_crystal_wand", "Prismatic Quartz Wand", "Aligned alongside glass distillation tubes on the stand", SceneRect(380f, 320f, 490f, 430f), Offset(435f, 375f)),
            SceneTarget("s18_alchemical_seal", "Ouroboros Bronze Medallion", "Inlaid into the center ring of the brass armillary sphere", SceneRect(740f, 240f, 850f, 350f), Offset(795f, 295f)),
            SceneTarget("s18_phoenix_feather", "Gilded Phoenix Quill", "Resting in the spine groove of the open leather grimoire", SceneRect(530f, 530f, 640f, 640f), Offset(585f, 585f)),
            SceneTarget("s18_glowing_rune_stone", "Violet Rune Stone", "Camouflaged among raw amethyst geodes on the shelf", SceneRect(860f, 460f, 970f, 570f), Offset(915f, 515f)),
            SceneTarget("s18_silver_compass", "Astrologer's Sextant", "Tucked into the shadows under the apothecary counter", SceneRect(190f, 630f, 300f, 740f), Offset(245f, 685f))
        )
    )

    // Scene 19: Ancient Dragon Cave
    val scene19 = SceneDefinition(
        id = 19,
        title = "Ancient Dragon Cave",
        subtitle = "Luminescent crystal clusters, obsidian boulders & sleeping hoard",
        category = SceneCategory.FANTASY,
        ambientAudioKey = "dragon_cave",
        targets = listOf(
            SceneTarget("s19_dragon_egg", "Scaly Obsidian Dragon Egg", "Blended seamlessly among smooth riverbed coal rocks", SceneRect(460f, 620f, 570f, 730f), Offset(515f, 675f)),
            SceneTarget("s19_elven_circlet", "Mithril Leaf Tiara", "Half-submerged in the shimmering gold coin drift", SceneRect(720f, 520f, 830f, 630f), Offset(775f, 575f)),
            SceneTarget("s19_ruby_eye", "Faceted Crimson Gem", "Gleaming inside the eye cleft of the gargantuan dragon skull", SceneRect(280f, 340f, 390f, 450f), Offset(335f, 395f)),
            SceneTarget("s19_enchanted_quiver", "Dragon-Scale Arrow", "Resting vertically against the glowing teal stalagmite", SceneRect(840f, 320f, 950f, 430f), Offset(895f, 375f)),
            SceneTarget("s19_carved_horn", "Runed Horn of Calling", "Tucked into the crevice between glowing sulfur pillars", SceneRect(160f, 540f, 270f, 650f), Offset(215f, 595f))
        )
    )

    // Scene 20: Orbital Space Station
    val scene20 = SceneDefinition(
        id = 20,
        title = "Orbital Space Station",
        subtitle = "Earth viewport, zero-g hydroponics, control consoles & airlock",
        category = SceneCategory.SCI_FI,
        ambientAudioKey = "space_station",
        targets = listOf(
            SceneTarget("s20_zero_g_pen", "Pressurized Space Pen", "Floating horizontally along the edge of the titanium console", SceneRect(410f, 480f, 520f, 590f), Offset(465f, 535f)),
            SceneTarget("s20_mission_patch", "Gold Apollo Mission Patch", "Velcroed into the insulation fabric of the airlock hatch", SceneRect(770f, 360f, 880f, 470f), Offset(825f, 415f)),
            SceneTarget("s20_hydroponic_strawberry", "Hydroponic Seedling", "Blended against the green LED growth lamps in botany bay", SceneRect(220f, 390f, 330f, 500f), Offset(275f, 445f)),
            SceneTarget("s20_hex_wrench", "Magnetic EVA Tool", "Clamped to the conduit bracket near the star observation dome", SceneRect(860f, 580f, 970f, 690f), Offset(915f, 635f)),
            SceneTarget("s20_audio_cassette", "Voyager Golden Disc Fragment", "Displayed in the shadow beside the orbital telemetry screen", SceneRect(560f, 220f, 670f, 330f), Offset(615f, 275f))
        )
    )

    val additionalScenes = listOf(
        scene4, scene5, scene6, scene7, scene8,
        scene9, scene10, scene11, scene12, scene13,
        scene14, scene15, scene16, scene17, scene18,
        scene19, scene20
    )
}
