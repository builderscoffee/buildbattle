package eu.builderscoffee.expresso.utils.blocks;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BlockData {

    STONE(1, 0, BlockCategory.CONSTRUCT),
    GRANITE(1, 1, BlockCategory.CONSTRUCT),
    POLISHED_GRANITE(1, 2, BlockCategory.CONSTRUCT),
    DIORITE(1, 3, BlockCategory.CONSTRUCT),
    POLISHED_DIORITE(1, 4, BlockCategory.CONSTRUCT),
    ANDESITE(1, 5, BlockCategory.CONSTRUCT),
    POLISHED_ANDESITE(1, 6, BlockCategory.CONSTRUCT),
    GRASS(2, 0, BlockCategory.CONSTRUCT),
    DIRT(3, 0, BlockCategory.CONSTRUCT),
    COARSE_DIRT(3, 1, BlockCategory.CONSTRUCT),
    PODZOL(3, 2, BlockCategory.CONSTRUCT),
    COBBLE(4, 0, BlockCategory.CONSTRUCT),
    OAK_WOOD_PLANK(5, 0, BlockCategory.CONSTRUCT),
    SPRUCE_WOOD_PLANK(5, 1, BlockCategory.CONSTRUCT),
    BIRCH_WOOD_PLANK(5, 2, BlockCategory.CONSTRUCT),
    JUNGLE_WOOD_PLANK(5, 3, BlockCategory.CONSTRUCT),
    ACACIA_WOOD_PLANK(5, 4, BlockCategory.CONSTRUCT),
    DARK_OAK_WOOD_PLANK(5, 5, BlockCategory.CONSTRUCT),
    OAK_SAPLING(6, 0, BlockCategory.SAPLING),
    SPRUCE_SAPLING(6, 1, BlockCategory.SAPLING),
    BIRCH_SAPLING(6, 2, BlockCategory.SAPLING),
    JUNGLE_SAPLING(6, 3, BlockCategory.SAPLING),
    ACACIA_SAPLING(6, 4, BlockCategory.SAPLING),
    DARK_OAK_SAPLING(6, 5, BlockCategory.SAPLING),
    BEDROCK(7, 0, BlockCategory.CONSTRUCT),
    SAND(12, 0, BlockCategory.CONSTRUCT),
    RED_SAND(12, 1, BlockCategory.CONSTRUCT),
    GRAVEL(13, 0, BlockCategory.CONSTRUCT),
    GOLD_ORE(14, 0, BlockCategory.CONSTRUCT),
    IRON_ORE(15, 0, BlockCategory.CONSTRUCT),
    COAL_ORE(16, 0, BlockCategory.CONSTRUCT),
    OAK_WOOD(17, 0, BlockCategory.LOG),
    SPRUCE_WOOD(17, 1, BlockCategory.LOG),
    BIRCH_WOOD(17, 2, BlockCategory.LOG),
    JUNGLE_WOOD(17, 3, BlockCategory.LOG),
    OAK_LEAVES(18, 0, BlockCategory.CONSTRUCT),
    SPRUCE_LEAVES(18, 1, BlockCategory.CONSTRUCT),
    BIRCH_LEAVES(18, 2, BlockCategory.CONSTRUCT),
    JUNGLE_LEAVES(18, 2, BlockCategory.CONSTRUCT),
    SPONGE(19, 0, BlockCategory.CONSTRUCT),
    WET_SPONGE(19, 1, BlockCategory.CONSTRUCT),
    GLASS(20, 0, BlockCategory.CONSTRUCT),
    LAPIS_LAZULI_ORE(21, 0, BlockCategory.CONSTRUCT),
    LAPIS_LAZULI_BLOCK(22, 0, BlockCategory.CONSTRUCT),
    DISPENSER(23, 0, BlockCategory.MISCELLANEOUS),
    SANDSTONE(24, 0, BlockCategory.CONSTRUCT),
    CHISELED_SANDSTONE(24, 1, BlockCategory.CONSTRUCT),
    SMOOTH_SANDSTONE(24, 2, BlockCategory.CONSTRUCT),
    NOTE_BLOCK(25, 0, BlockCategory.CONSTRUCT),
    //BED(26, 0, BlockCategory.CONSTRUCT),
    STICKY_PISTON(29, 0, BlockCategory.MISCELLANEOUS),
    COBWEB(30, 0, BlockCategory.PLANT),
    DEAD_SHRUB(31, 0, BlockCategory.PLANT),
    TALLGRASS(31, 1, BlockCategory.PLANT),
    FERN(31, 2, BlockCategory.PLANT),
    DEAD_BUSH(31, 2, BlockCategory.PLANT),
    PISTON(33, 0, BlockCategory.MISCELLANEOUS),
    WHITE_WOOL(35, 0, BlockCategory.CONSTRUCT),
    ORANGE_WOOL(35, 1, BlockCategory.CONSTRUCT),
    MAGENTA_WOOL(35, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_WOOL(35, 3, BlockCategory.CONSTRUCT),
    YELLOW_WOOL(35, 4, BlockCategory.CONSTRUCT),
    LIME_WOOL(35, 5, BlockCategory.CONSTRUCT),
    PINK_WOOL(35, 6, BlockCategory.CONSTRUCT),
    GRAY_WOOL(35, 7, BlockCategory.CONSTRUCT),
    LIGT_GRAY_WOOL(35, 7, BlockCategory.CONSTRUCT),
    CYAN_GRAY_WOOL(35, 8, BlockCategory.CONSTRUCT),
    PURPLE_WOOL(35, 9, BlockCategory.CONSTRUCT),
    BLUE_WOOL(35, 10, BlockCategory.CONSTRUCT),
    BROWN_WOOL(35, 11, BlockCategory.CONSTRUCT),
    GREEN_WOOL(35, 13, BlockCategory.CONSTRUCT),
    RED_WOOL(35, 14, BlockCategory.CONSTRUCT),
    BLACK_WOOL(35, 15, BlockCategory.CONSTRUCT),
    DANDELION(37, 0, BlockCategory.PLANT),
    POPPY(38, 0, BlockCategory.PLANT),
    BLUE_ORCHID(38, 1, BlockCategory.PLANT),
    ALLIUM(38, 2, BlockCategory.PLANT),
    AZURE_BLUET(38, 3, BlockCategory.PLANT),
    RED_TULIP(38, 4, BlockCategory.PLANT),
    ORANGE_TULIP(38, 5, BlockCategory.PLANT),
    WHITE_TULIP(38, 6, BlockCategory.PLANT),
    PINK_TULIP(38, 7, BlockCategory.PLANT),
    OXEYE_DAISY(38, 8, BlockCategory.PLANT),
    BROWN_MUSHROOM(39, 0, BlockCategory.PLANT),
    RED_MUSHROOM(40, 0, BlockCategory.PLANT),
    GOLD_BLOCK(41, 0, BlockCategory.PLANT),
    IRON_BLOCK(42, 0, BlockCategory.PLANT),
    /*
    DOUBLE_STONE_SLAB(43, 0, BlockCategory.CONSTRUCT),
    DOUBLE_STANDSTONE_SLAB(43, 1, BlockCategory.CONSTRUCT),
    DOUBLE_WOODEN_SLAB(43, 2, BlockCategory.CONSTRUCT),
    DOUBLE_COBBLESTONE_SLAB(43, 3, BlockCategory.CONSTRUCT),
    DOUBLE_BRICK_SLAB(43, 4, BlockCategory.CONSTRUCT),
    DOUBLE_STONE_BRICK_SLAB(43, 5, BlockCategory.CONSTRUCT),
    DOUBLE_NETHER_BRICK_SLAB(43, 6, BlockCategory.CONSTRUCT),
    DOUBLE_QUARTZ_SLAB(43, 7, BlockCategory.CONSTRUCT),
    */
    STONE_SLAB(44, 0, BlockCategory.SLAB),
    SANDSTONE_SLAB(44, 1, BlockCategory.SLAB),
    WOODEN_SLAB(44, 2, BlockCategory.SLAB),
    COBBLESTONE_SLAB(44, 3, BlockCategory.SLAB),
    BRICK_SLAB(44, 4, BlockCategory.SLAB),
    STONE_BRICK_SLAB(44, 5, BlockCategory.SLAB),
    NETHER_BRICK_SLAB(44, 6, BlockCategory.SLAB),
    QUARTZ_SLAB(44, 7, BlockCategory.SLAB),
    BRICKS(45, 0, BlockCategory.CONSTRUCT),
    TNT(46, 0, BlockCategory.CONSTRUCT),
    BOOKSHELF(47, 0, BlockCategory.CONSTRUCT),
    MOSS_STONE(48, 0, BlockCategory.CONSTRUCT),
    OBSIDIAN(49, 0, BlockCategory.CONSTRUCT),
    //TORCH(50,0,)
    MONSTER_SPAWNER(52, 0, BlockCategory.CONSTRUCT),
    OAK_WOOD_STAIRS(53, 0, BlockCategory.CONSTRUCT),
    DIAMOND_ORE(56, 0, BlockCategory.CONSTRUCT),
    DIAMOND_BLOCK(57, 0, BlockCategory.CONSTRUCT),
    CRAFTING_TABLE(58, 0, BlockCategory.CONSTRUCT),
    //WHEAT_CROPS(59, 0, BlockCategory.CONSTRUCT),
    FARMLAND(60, 0, BlockCategory.CONSTRUCT),
    FURNACE(61, 0, BlockCategory.MISCELLANEOUS),
    //BURNING_FURNACE(62, 0, BlockCategory.MISCELLANEOUS),
    OAK_DOOR_BLOCK(64, 0, BlockCategory.DOOR),
    RAIL(66, 0, BlockCategory.CONSTRUCT),
    COBBELSTONE_STAIRS(67, 0, BlockCategory.STAIRS),
    STONE_PRESSURE_PLATE(70, 0, BlockCategory.PLATE),
    IRON_DOOR_BLOCK(71, 0, BlockCategory.DOOR),
    WOODEN_PRESSURE_PLATE(72, 0, BlockCategory.PLATE),
    REDSTONE_ORE(73, 0, BlockCategory.CONSTRUCT),
    //GLOWING_REDSTONE_ORE(74, 0, BlockCategory.CONSTRUCT),
    STONE_BUTTON(77, 0, BlockCategory.MISCELLANEOUS),
    SNOW(78, 0, BlockCategory.CONSTRUCT),
    ICE(79, 0, BlockCategory.CONSTRUCT),
    SNOW_BLOCK(80, 0, BlockCategory.CONSTRUCT),
    CACTUS(81, 0, BlockCategory.CONSTRUCT),
    CLAY(82, 0, BlockCategory.CONSTRUCT),
    SUGAR_CANNES(83, 0, BlockCategory.PLANT),
    JUKEBOX(84, 0, BlockCategory.CONSTRUCT),
    OAK_FENCE(85, 0, BlockCategory.CONSTRUCT),
    PUMPKIN(86, 0, BlockCategory.MISCELLANEOUS),
    NETHERRACK(87, 0, BlockCategory.CONSTRUCT),
    SOUL_SAND(88, 0, BlockCategory.CONSTRUCT),
    GLOWSTONE(89, 0, BlockCategory.CONSTRUCT),
    //NETHER_PORTAL(90, 0, BlockCategory.CONSTRUCT),
    JACK_O_LANTERN(91, 0, BlockCategory.MISCELLANEOUS),
    //CAKE_BLOCK(92, 0, BlockCategory.MISCELLANEOUS),
    WHITE_STAINED_GLASS(95, 0, BlockCategory.CONSTRUCT),
    ORANGE_STAINED_GLASS(95, 1, BlockCategory.CONSTRUCT),
    MAGENTA_STAINED_GLASS(95, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_STAINED_GLASS(95, 3, BlockCategory.CONSTRUCT),
    YELLOW_STAINED_GLASS(95, 4, BlockCategory.CONSTRUCT),
    LIME_STAINED_GLASS(95, 5, BlockCategory.CONSTRUCT),
    PINK_STAINED_GLASS(95, 6, BlockCategory.CONSTRUCT),
    GRAY_STAINED_GLASS(95, 7, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_STAINED_GLASS(95, 8, BlockCategory.CONSTRUCT),
    CYAN_STAINED_GLASS(95, 9, BlockCategory.CONSTRUCT),
    PURPLE_STAINED_GLASS(95, 10, BlockCategory.CONSTRUCT),
    BLUE_STAINED_GLASS(95, 11, BlockCategory.CONSTRUCT),
    BROWN_STAINED_GLASS(95, 12, BlockCategory.CONSTRUCT),
    GREEN_STAINED_GLASS(95, 13, BlockCategory.CONSTRUCT),
    RED_STAINED_GLASS(95, 14, BlockCategory.CONSTRUCT),
    BLACK_STAINED_GLASS(95, 15, BlockCategory.CONSTRUCT),
    WOODEN_TRAPDOOR(96, 0, BlockCategory.DOOR),
    STONE_MONSTER_EGG(97, 0, BlockCategory.CONSTRUCT),
    COBBELSTONE_MONSTER_EGG(97, 1, BlockCategory.CONSTRUCT),
    STONE_BRICK_MONSTER_EGG(97, 2, BlockCategory.CONSTRUCT),
    MOSSY_STONE_BRICK_MONSTER_EGG(97, 3, BlockCategory.CONSTRUCT),
    CRACKED_STONE_BRICK_MONSTER_EGG(97, 4, BlockCategory.CONSTRUCT),
    CHISELED_STONE_BRICK_MONSTER_EGG(97, 5, BlockCategory.CONSTRUCT),
    STONE_BRICK(98, 0, BlockCategory.CONSTRUCT),
    MOSSY_STONE_BRICK(98, 1, BlockCategory.CONSTRUCT),
    CRACKED_STONE_BRICK(98, 2, BlockCategory.CONSTRUCT),
    CHISELED_STONE_BRICK(98, 3, BlockCategory.CONSTRUCT),
    BROWN_MUSHROOM_BLOCK(99, 0, BlockCategory.CONSTRUCT),
    RED_MUSHROOM_BLOCK(100, 0, BlockCategory.CONSTRUCT),
    IRON_BARS(101, 0, BlockCategory.CONSTRUCT),
    GLASS_PANE(102, 0, BlockCategory.CONSTRUCT),
    MELON_BLOCK(103, 0, BlockCategory.CONSTRUCT),
    //PUMPKIN_STEM(104, 0, BlockCategory.PLANT),
    //MELON_STEM(105, 0, BlockCategory.PLANT),
    OAK_FENCE_GATE(107, 0, BlockCategory.CONSTRUCT),
    BRICK_STAIRS(108, 0, BlockCategory.STAIRS),
    STONE_BRICK_STAIRS(109, 0, BlockCategory.STAIRS),
    MYCELIUM(110, 0, BlockCategory.CONSTRUCT),
    LILY_PAD(111, 0, BlockCategory.PLANT),
    NETHER_BRICK(112, 0, BlockCategory.CONSTRUCT),
    NETHER_BRICK_FENCE(113, 0, BlockCategory.CONSTRUCT),
    NETHER_BRICK_STAIRS(114, 0, BlockCategory.STAIRS),
    NETHER_WART(115, 0, BlockCategory.PLANT),
    ENCHANTEMENT_TABLE(116, 0, BlockCategory.CONSTRUCT),
    BREWING_STAND(117, 0, BlockCategory.CONSTRUCT),
    CAULDRON(118, 0, BlockCategory.CONSTRUCT),
    END_PORTAL(119, 0, BlockCategory.MISCELLANEOUS),
    END_PORTAL_FRAME(120, 0, BlockCategory.CONSTRUCT),
    END_STONE(121, 0, BlockCategory.CONSTRUCT),
    DRAGON_EGG(122, 0, BlockCategory.CONSTRUCT),
    REDSTONE_LAMP_INACTIVE(123, 0, BlockCategory.CONSTRUCT),
    REDSTONE_LAMP_ACTIVE(124, 0, BlockCategory.CONSTRUCT),
    /*DOUBLE_OAK_WOOD_SLAB(125, 0, BlockCategory.CONSTRUCT),
    DOUBLE_SPRUCE_WOOD_SLAB(125, 1, BlockCategory.CONSTRUCT),
    DOUBLE_BIRCH_WOOD_SLAB(125, 2, BlockCategory.CONSTRUCT),
    DOUBLE_JUNGLE_WOOD_SLAB(125, 4, BlockCategory.CONSTRUCT),
    DOUBLE_ACACIA_WOOD_SLAB(125, 4, BlockCategory.CONSTRUCT),
    DOUBLE_DARK_OAK_WOOD_SLAB(125, 5, BlockCategory.CONSTRUCT),
    */
    OAK_WOOD_SLAB(126, 0, BlockCategory.SLAB),
    SPRUCE_WOOD_SLAB(126, 1, BlockCategory.SLAB),
    BIRCH_WOOD_SLAB(126, 2, BlockCategory.SLAB),
    JUNGLE_WOOD_SLAB(126, 3, BlockCategory.SLAB),
    ACACIA_WOOD_SLAB(126, 4, BlockCategory.SLAB),
    DARK_OAK_WOOD_SLAB(126, 5, BlockCategory.SLAB),
    //COCOA(127, 0, BlockCategory.PLANT),
    SANDSTONE_STAIRS(128, 0, BlockCategory.STAIRS),
    EMERALD_ORE(129, 0, BlockCategory.CONSTRUCT),
    EMERALD_BLOCK(133, 0, BlockCategory.CONSTRUCT),
    SPRUCE_WOOD_STAIRS(134, 0, BlockCategory.STAIRS),
    BIRCH_WOOD_STAIRS(135, 0, BlockCategory.STAIRS),
    JUNGLE_WOOD_STAIRS(136, 0, BlockCategory.STAIRS),
    COMMAND_BLOCK(137, 0, BlockCategory.CONSTRUCT),
    BEACON(138, 0, BlockCategory.CONSTRUCT),
    COBBELSTONE_WALL(139, 0, BlockCategory.CONSTRUCT),
    MOSSY_COBBLESTONE_WALL(139, 1, BlockCategory.CONSTRUCT),
    //FLOWER_POT(140, 0, BlockCategory.CONSTRUCT), // TODO Correct ?
    CARROTS(141, 0, BlockCategory.PLANT),
    //POTATOES(142, 0, BlockCategory.PLANT),
    WOODEN_BUTTON(143, 0, BlockCategory.MISCELLANEOUS),
    //MOB_HEAD(144, 0, BlockCategory.MISCELLANEOUS),
    ANVIL(145, 0, BlockCategory.CONSTRUCT),
    GOLD_PLATE(147, 0, BlockCategory.PLATE),
    IRON_PLATE(148, 0, BlockCategory.PLATE),
    REDSTONE_COMPARATOR_INACTIVE(149, 0, BlockCategory.PLATE),
    //REDSTONE_COMPARATOR_ACTIVE(150, 0, BlockCategory.PLATE),
    DAYLIGHTSENSOR(151, 0, BlockCategory.CONSTRUCT),
    REDSTONE_BLOCK(152, 0, BlockCategory.CONSTRUCT),
    NETHER_QUARTZ_ORE(153, 0, BlockCategory.CONSTRUCT),
    HOPPER(154, 0, BlockCategory.CONSTRUCT),
    QUARTZ_BLOCK(155, 0, BlockCategory.CONSTRUCT),
    CHISELED_QUARTZ_BLOCK(155, 1, BlockCategory.CONSTRUCT),
    PILLAR_QUARTZ_BLOCK(155, 2, BlockCategory.CONSTRUCT),
    QUARTZ_STAIRS(156, 0, BlockCategory.STAIRS),
    DROPPER(158, 0, BlockCategory.MISCELLANEOUS),
    WHITE_HARDENED_CLAY(159, 0, BlockCategory.CONSTRUCT),
    ORANGE_HARDENED_CLAY(159, 1, BlockCategory.CONSTRUCT),
    MAGENTA_HARDENED_CLAY(159, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_HARDNED_CLAY(159, 3, BlockCategory.CONSTRUCT),
    YELLOW_HARDNED_CLAY(159, 4, BlockCategory.CONSTRUCT),
    LIME_HARDNED_CLAY(159, 5, BlockCategory.CONSTRUCT),
    PINK_HARDNED_CLAY(159, 6, BlockCategory.CONSTRUCT),
    GRAY_HARDNED_CLAY(159, 7, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_HARDNED_CLAY(159, 8, BlockCategory.CONSTRUCT),
    CYAN_HARDNED_CLAY(159, 9, BlockCategory.CONSTRUCT),
    PURPLE_HARDENED_CLAY(159, 10, BlockCategory.CONSTRUCT),
    BLUE_HARDNED_CLAY(159, 11, BlockCategory.CONSTRUCT),
    BROWN_HARDNED_CLAY(159, 12, BlockCategory.CONSTRUCT),
    GREEN_HARDNED_CLAY(159, 13, BlockCategory.CONSTRUCT),
    RED_HARDNED_CLAY(159, 14, BlockCategory.CONSTRUCT),
    BLACK_HARDNED_CLAY(159, 15, BlockCategory.CONSTRUCT),
    WHITE_STAINED_GLASS_PANE(160, 0, BlockCategory.CONSTRUCT),
    ORANGE_STAINED_GLASS_PANE(160, 1, BlockCategory.CONSTRUCT),
    MAGENTA_STAINED_GLASS_PANE(160, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_STAINED_GLASS_PANE(160, 3, BlockCategory.CONSTRUCT),
    YELLOW_STAINED_GLASS_PANE(160, 4, BlockCategory.CONSTRUCT),
    LIME_STAINED_GLASS_PANE(160, 5, BlockCategory.CONSTRUCT),
    PINK_BLUE_STAINED_GLASS_PANE(160, 6, BlockCategory.CONSTRUCT),
    GRAY_BLUE_STAINED_GLASS_PANE(160, 7, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_BLUE_STAINED_GLASS_PANE(160, 8, BlockCategory.CONSTRUCT),
    CYAN_STAINED_GLASS_PANE(160, 9, BlockCategory.CONSTRUCT),
    PURPLE_STAINED_GLASS_PANE(160, 10, BlockCategory.CONSTRUCT),
    BLUE_STAINED_GLASS_PANE(160, 11, BlockCategory.CONSTRUCT),
    BROWN_STAINED_GLASS_PANE(160, 12, BlockCategory.CONSTRUCT),
    GREEN_STAINED_GLASS_PANE(160, 13, BlockCategory.CONSTRUCT),
    RED_STAINED_GLASS_PANE(160, 14, BlockCategory.CONSTRUCT),
    BLACK_STAINED_GLASS_PANE(160, 6, BlockCategory.CONSTRUCT),
    ACACIA_LEAVES(161, 0, BlockCategory.CONSTRUCT),
    DARK_OAK_LEAVES(161, 1, BlockCategory.CONSTRUCT),
    ACACIA_WOOD(162, 0, BlockCategory.CONSTRUCT),
    DARK_OAK_WOOD(162, 1, BlockCategory.CONSTRUCT),
    ACACIA_WOOD_STAIRS(163, 0, BlockCategory.STAIRS),
    DARK_OAK_STAIRS(164, 0, BlockCategory.STAIRS),
    SLIME_BLOCK(165, 0, BlockCategory.CONSTRUCT),
    BARRIER(166, 0, BlockCategory.CONSTRUCT),
    IRON_TRAPDOOR(167, 0, BlockCategory.DOOR),
    PRISMARINE(168, 0, BlockCategory.CONSTRUCT),
    PRISMARINE_BRICKS(168, 1, BlockCategory.CONSTRUCT),
    DARK_PRISMARINE(168, 2, BlockCategory.CONSTRUCT),
    SEA_LANTERN(169, 0, BlockCategory.CONSTRUCT),
    HAY_BALE(170, 0, BlockCategory.CONSTRUCT),
    WHITE_CARPET(171, 0, BlockCategory.CONSTRUCT),
    ORANGE_CARPET(171, 1, BlockCategory.CONSTRUCT),
    MAGENTA_CARPET(171, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_CARPET(171, 3, BlockCategory.CONSTRUCT),
    YELLOW_CARPET(171, 4, BlockCategory.CONSTRUCT),
    LIME_CARPET(171, 5, BlockCategory.CONSTRUCT),
    PINK_CARPET(171, 6, BlockCategory.CONSTRUCT),
    GRAY_CARPET(171, 7, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_CARPET(171, 8, BlockCategory.CONSTRUCT),
    CYAN_CARPET(171, 9, BlockCategory.CONSTRUCT),
    PURPLE_CARPET(171, 10, BlockCategory.CONSTRUCT),
    BLUE_CARPET(171, 11, BlockCategory.CONSTRUCT),
    BROWN_CARPET(171, 12, BlockCategory.CONSTRUCT),
    GREEN_CARPET(171, 13, BlockCategory.CONSTRUCT),
    RED_CARPET(171, 14, BlockCategory.CONSTRUCT),
    BLACK_CARPET(171, 15, BlockCategory.CONSTRUCT),
    HARDENED_CLAY(172, 0, BlockCategory.CONSTRUCT),
    BLOCK_OF_COAL(173, 0, BlockCategory.CONSTRUCT),
    PACKED_ICE(174, 0, BlockCategory.CONSTRUCT),
    SUNFLOWER(175, 0, BlockCategory.PLANT),
    LILAC(175, 1, BlockCategory.PLANT),
    DOUBLE_TALLGRASS(175, 1, BlockCategory.PLANT),
    LARGE_FERM(175, 3, BlockCategory.PLANT),
    ROSE_BUSH(175, 4, BlockCategory.PLANT),
    PEONY(175, 5, BlockCategory.PLANT),
    STANDING_BANNER(176, 0, BlockCategory.CONSTRUCT),
    WALL_BANNER(177, 0, BlockCategory.CONSTRUCT),
    DAYLIGHT_SENSOR(178, 0, BlockCategory.CONSTRUCT),
    RED_SANDSTONE(179, 0, BlockCategory.CONSTRUCT),
    CHISELED_RED_SANDSTONE(179, 1, BlockCategory.CONSTRUCT),
    SMOOTH_RED_SANDSTONE(179, 2, BlockCategory.CONSTRUCT),
    RED_SANDSTONE_STAIRS(180, 0, BlockCategory.STAIRS),
    RED_SANDSTONE_SLAB(182, 0, BlockCategory.SLAB),
    SPRICE_FENCE_GATE(183, 0, BlockCategory.CONSTRUCT),
    BIRCH_FENCE_GATE(184, 0, BlockCategory.CONSTRUCT),
    JUNGLE_FENCE_GATE(185, 0, BlockCategory.CONSTRUCT),
    DARK_OAK_FENCE_GATE(186, 0, BlockCategory.CONSTRUCT),
    ACACIA_FENCE_GATE(187, 0, BlockCategory.CONSTRUCT),
    SPRUCE_FENCE(188, 0, BlockCategory.CONSTRUCT),
    BIRCH_FENCE(189, 0, BlockCategory.CONSTRUCT),
    JUNGLE_FENCE(190, 0, BlockCategory.CONSTRUCT),
    DARK_OAK_FENCE(191, 0, BlockCategory.CONSTRUCT),
    ACACIA_FENCE(192, 0, BlockCategory.CONSTRUCT),
    END_ROD(198, 0, BlockCategory.CONSTRUCT),
    CHORUS_PLANT(199, 0, BlockCategory.CONSTRUCT),
    CHORUS_FLOWER(200, 0, BlockCategory.CONSTRUCT),
    PURPUR_BLOCK(201, 0, BlockCategory.CONSTRUCT),
    PURPUR_PILAR(202, 0, BlockCategory.CONSTRUCT),
    PURPUR_STAIRS(203, 0, BlockCategory.STAIRS),
    PURPUR_SLAB(204, 0, BlockCategory.SLAB),
    END_STONE_BRICK(206, 0, BlockCategory.CONSTRUCT),
    GRASS_PATH(208, 0, BlockCategory.CONSTRUCT),
    END_GATEWAY(209, 0, BlockCategory.CONSTRUCT),
    REPEATING_COMMAND_BLOCK(210, 0, BlockCategory.CONSTRUCT),
    CHAIN_COMMAND_BLOCK(211, 0, BlockCategory.CONSTRUCT),
    FROSTED_ICE(212, 0, BlockCategory.CONSTRUCT),
    MAGMA_BLOCK(213, 0, BlockCategory.CONSTRUCT),
    NETHER_WART_BLOCK(214, 0, BlockCategory.CONSTRUCT),
    RED_NETHER_BRICK(215, 0, BlockCategory.CONSTRUCT),
    BONE_BLOCK(216, 0, BlockCategory.CONSTRUCT),
    STRUCTURE_VOID(217, 0, BlockCategory.CONSTRUCT),
    OBSERVER(218, 0, BlockCategory.CONSTRUCT),
    WHITE_SHULKER_BOX(219, 0, BlockCategory.CONSTRUCT),
    ORANGE_SHULKER_BOX(220, 0, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_SHULKER_BOX(222, 0, BlockCategory.CONSTRUCT),
    YELLOW_SHULKER_BOX(223, 0, BlockCategory.CONSTRUCT),
    LIME_SHULKER_BOX(224, 0, BlockCategory.CONSTRUCT),
    PINK_SHULKER_BOX(225, 0, BlockCategory.CONSTRUCT),
    GRAY_SHULKER_BOX(226, 0, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_SHULKER_BOX(227, 0, BlockCategory.CONSTRUCT),
    CYAN_SHULKER_BOX(228, 0, BlockCategory.CONSTRUCT),
    PURPLE_SHULKER_BOX(229, 0, BlockCategory.CONSTRUCT),
    BLUE_SHULKER_BOX(230, 0, BlockCategory.CONSTRUCT),
    BROWN_SHULKER_BOX(231, 0, BlockCategory.CONSTRUCT),
    GREEN_SHULKER_BOX(232, 0, BlockCategory.CONSTRUCT),
    RED_SHULKER_BOX(233, 0, BlockCategory.CONSTRUCT),
    BLACK_SHULKER_BOX(234, 0, BlockCategory.CONSTRUCT),
    WHITE_GLAZED_TERRACOTTA(235, 0, BlockCategory.CONSTRUCT),
    ORANGE_GLAZED_TERRACOTTA(236, 0, BlockCategory.CONSTRUCT),
    MAGENTA_GLAZED_TERRACOTTA(237, 0, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_GLAZED_TERRACOTTA(238, 0, BlockCategory.CONSTRUCT),
    YELLOW_GLAZED_TERRACOTTA(239, 0, BlockCategory.CONSTRUCT),
    LIME_GLAZED_TERRACOTTA(240, 0, BlockCategory.CONSTRUCT),
    PINK_GLAZED_TERRACOTTA(241, 0, BlockCategory.CONSTRUCT),
    GRAY_GLAZED_TERRCOTTA(242, 0, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_GLAZED_TERRCOTTA(243, 0, BlockCategory.CONSTRUCT),
    CYAN_GLAZED_TERRCOTTA(244, 0, BlockCategory.CONSTRUCT),
    PURPLE_GLAZED_TERRCOTTA(245, 0, BlockCategory.CONSTRUCT),
    BLUE_GLAZED_TERRCOTTA(246, 0, BlockCategory.CONSTRUCT),
    BROWN_GLAZED_TERRCOTTA(247, 0, BlockCategory.CONSTRUCT),
    GREEN_GLAZED_TERRCOTTA(248, 0, BlockCategory.CONSTRUCT),
    RED_GLAZED_TERRCOTTA(249, 0, BlockCategory.CONSTRUCT),
    BLACK_GLAZED_TERRCOTTA(250, 0, BlockCategory.CONSTRUCT),
    WHITE_CONCRETE(251, 0, BlockCategory.CONSTRUCT),
    ORANGE_CONCRETE(251, 1, BlockCategory.CONSTRUCT),
    MAGENTA_CONCRETE(251, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_CONCRETE(251, 3, BlockCategory.CONSTRUCT),
    YELLOW_CONCRETE(251, 4, BlockCategory.CONSTRUCT),
    LIME_CONCRETE(251, 5, BlockCategory.CONSTRUCT),
    PINK_CONCRETE(251, 6, BlockCategory.CONSTRUCT),
    GRAY_CONCRETE(251, 7, BlockCategory.CONSTRUCT),
    LIGHT_GREY_CONCRETE(251, 8, BlockCategory.CONSTRUCT),
    CYAN_CONCRETE(251, 9, BlockCategory.CONSTRUCT),
    PURPLE_CONCRETE(251, 10, BlockCategory.CONSTRUCT),
    BLUE_CONCRETE(251, 11, BlockCategory.CONSTRUCT),
    BROWN_CONCRETE(251, 12, BlockCategory.CONSTRUCT),
    GREEN_CONCRETE(251, 13, BlockCategory.CONSTRUCT),
    RED_CONCRETE(251, 14, BlockCategory.CONSTRUCT),
    BLACK_CONCRETE(251, 15, BlockCategory.CONSTRUCT),
    WHITE_CONCRETE_POWDER(252, 0, BlockCategory.CONSTRUCT),
    ORANGE_CONCRETE_POWDER(252, 1, BlockCategory.CONSTRUCT),
    MAGENTA_CONCRETE_POWDER(252, 2, BlockCategory.CONSTRUCT),
    LIGHT_BLUE_POWDER(252, 3, BlockCategory.CONSTRUCT),
    YELLOW_CONCRETE_POWDER(252, 4, BlockCategory.CONSTRUCT),
    LIME_CONCRETE_POWDER(252, 5, BlockCategory.CONSTRUCT),
    PINK_CONCRETE_POWDER(252, 6, BlockCategory.CONSTRUCT),
    GRAY_CONCRETE_POWDER(252, 7, BlockCategory.CONSTRUCT),
    LIGHT_GRAY_CONCRETE_POWDER(252, 8, BlockCategory.CONSTRUCT),
    CYAN_CONCRETE_POWDER(252, 9, BlockCategory.CONSTRUCT),
    PURPLE_CONCRETE_POWDER(252, 10, BlockCategory.CONSTRUCT),
    BLUE_CONCRETE_POWDER(252, 11, BlockCategory.CONSTRUCT),
    BROWN_CONCRETE_POWDER(252, 12, BlockCategory.CONSTRUCT),
    GREEN_CONCRETE_POWDER(252, 13, BlockCategory.CONSTRUCT),
    RED_CONCRETE_POWDER(252, 14, BlockCategory.CONSTRUCT),
    BLACK_CONCRETE_POWDER(252, 15, BlockCategory.CONSTRUCT),
    STRUCTURE_BLOCK(255, 0, BlockCategory.CONSTRUCT),
    OAK_DOOR(324, 0, BlockCategory.DOOR),
    SIGN(323, 0, BlockCategory.MISCELLANEOUS),
    IRON_DOOR(330, 0, BlockCategory.DOOR),
    REDSTONE(331, 0, BlockCategory.PLATE),
    CAKE(354, 0, BlockCategory.CONSTRUCT),
    BED(355, 0, BlockCategory.CONSTRUCT),
    REDSTONE_REPEATER(356, 0, BlockCategory.PLATE),
    ITEM_FRAME(389, 0, BlockCategory.MISCELLANEOUS),
    FLOWER_POT(390, 0, BlockCategory.PLANT),
    MOB_HEAD_SKELETON(397, 0, BlockCategory.CONSTRUCT),
    MOB_HEAD_WHITHER_SKELETON(397, 1, BlockCategory.CONSTRUCT),
    MOB_HEAD_ZOMBIE(397, 2, BlockCategory.CONSTRUCT),
    MOB_HEAD_HUMAN(397, 3, BlockCategory.CONSTRUCT),
    MOB_HEAD_CREEPER(397, 4, BlockCategory.CONSTRUCT),
    MOB_HEAD_DRAGON(397, 5, BlockCategory.CONSTRUCT),
    REDSTONE_COMPARATOR(404, 0, BlockCategory.PLATE),
    ARMOR_STAND(416, 0, BlockCategory.MISCELLANEOUS),
    END_CRYSTAL(426, 0, BlockCategory.CONSTRUCT),
    SPRUCE_DOOR(427, 0, BlockCategory.DOOR),
    BIRCH_DOOR(428, 0, BlockCategory.DOOR),
    JUNGLE_DOOR(429, 0, BlockCategory.DOOR),
    ACACIA_DOOR(430, 0, BlockCategory.DOOR),
    DARK_OAK_DOOR(431, 0, BlockCategory.DOOR);


    @Getter
    public int id, shortId;
    @Getter
    public BlockCategory blockCategory;

    /***
     * Constructor BlockData
     * @param id - L'id d'un bloc
     * @param shortId - La data d'un bloc
     * @param blockCategory - Sa catégorie
     */
    BlockData(int id, int shortId, BlockCategory blockCategory) {
        this.id = id;
        this.shortId = shortId;
        this.blockCategory = blockCategory;
    }

    /***
     * Checker si une BlockData est présente dans l'énumération
     * @param id
     * @return
     */
    public static boolean isValidBlockData(int id) {
        return Arrays.stream(BlockData.values())
                .anyMatch(blockData -> Objects.equals(blockData.getId(), id));
    }

    /***
     * Retourne une BlockData par rapport à sont id
     * @param id - L'id du block
     * @return
     */
    public static BlockData getBlockDataById(int id) {
        return Arrays.stream(BlockData.values())
                .filter(blockData -> Objects.equals(blockData.getId(), id))
                .findFirst()
                .get();
    }

    /***
     * Retourne une BlockData par sont id et sont shortId
     * @param id - L'id du block
     * @param shortId - Le shortId du block
     * @return
     */
    public static BlockData getBlockDataByIdAndShort(int id, int shortId) {
        return Arrays.stream(BlockData.values())
                .filter(blockData -> Objects.equals(blockData.getId(), id))
                .filter(blockData -> Objects.equals(blockData.getShortId(), shortId))
                .findFirst()
                .get();
    }

    /***
     * Retourne une liste des blocs dans une catégorie précisée
     *
     * @param category - La catégorie du blockData
     * @return
     */
    public static List<BlockData> blockDataCategory(BlockCategory category) {
        return Stream.of(BlockData.values())
                .filter(blockData -> blockData.blockCategory.equals(category))
                .collect(Collectors.toList());
    }

    public enum BlockCategory {
        CONSTRUCT,
        SAPLING,
        PLANT,
        SLAB,
        DOOR,
        PLATE,
        // Spécial - Direction par rapport à l'endroit posé
        STAIRS,
        LOG,
        // Spécial - Direction par rapport aux regards
        MISCELLANEOUS
    }
}
