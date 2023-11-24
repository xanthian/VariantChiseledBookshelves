package net.xanthian.variantchiseledbookshelves.util;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;

import static net.xanthian.variantchiseledbookshelves.Initialise.isModVersion;

public class ModRegistries {

    public static void registerFuelandFlammable() {
        registerFuel();
    }

    private static void registerFuel() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        // Copy of Blocks.CHISELED_BOOKSHELF from net.minecraft.block.entity.AbstractFurnaceBlockEntity.createFuelTimeMap
        registry.add(Vanilla.ACACIA_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.BAMBOO_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.BIRCH_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.CHERRY_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.DARK_OAK_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.JUNGLE_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.MANGROVE_CHISELED_BOOKSHELF, 300);
        registry.add(Vanilla.SPRUCE_CHISELED_BOOKSHELF, 300);

        if (FabricLoader.getInstance().isModLoaded("ad_astra")) {
            for (Block block : AdAstra.AA_CHISELED_BOOKSHELVES.values()) {
                registry.add(block, 300);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("beachparty")) {
            registry.add(BeachParty.LDBP_PALM_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("betterarcheology")) {
            registry.add(BetterArcheology.BA_ROTTEN_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("bewitchment")) {
            for (Block block : Bewitchment.BW_CHISELED_BOOKSHELVES.values()) {
                registry.add(block, 300);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("biomemakeover")) {
            for (Block block : BiomeMakeover.BM_CHISELED_BOOKSHELVES.values()) {
                registry.add(block, 300);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("deeperdarker")) {
            registry.add(DeeperAndDarker.DAD_ECHO_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("eldritch_end")) {
            registry.add(EldritchEnd.EE_PRIMORDIAL_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("minecells")) {
            registry.add(MineCells.MC_PUTRID_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("natures_spirit")) {
            for (Block block : NaturesSpirit.NS_CHISELED_BOOKSHELVES.values()) {
                registry.add(block, 300);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("promenade")) {
            registry.add(Promenade.PROM_MAPLE_CHISELED_BOOKSHELF, 300);
            registry.add(Promenade.PROM_PALM_CHISELED_BOOKSHELF, 300);
            registry.add(Promenade.PROM_SAKURA_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("regions_unexplored")) {
            registry.add(RegionsUnexplored.RU_ALPHA_OAK_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_BAOBAB_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_BLACK_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_BLACKWOOD_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_BLUE_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_BROWN_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_CYAN_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_CYPRESS_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_EUCALYPTUS_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_GRAY_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_GREEN_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_JOSHUA_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_LARCH_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_LIGHT_BLUE_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_LIGHT_GRAY_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_LIME_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_MAGENTA_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_MAPLE_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_MAUVE_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_ORANGE_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_PALM_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_PINE_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_PINK_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_PURPLE_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_REDWOOD_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_RED_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_WHITE_PAINTED_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_WILLOW_CHISELED_BOOKSHELF, 300);
            registry.add(RegionsUnexplored.RU_YELLOW_PAINTED_CHISELED_BOOKSHELF, 300);
            if (isModVersion("regions_unexplored", "0.4")) {
                registry.add(RegionsUnexplored.RU_CHERRY_CHISELED_BOOKSHELF, 300);
                registry.add(RegionsUnexplored.RU_SCULKWOOD_CHISELED_BOOKSHELF, 300);
            } else {
                registry.add(RegionsUnexplored.RU_KAPOK_CHISELED_BOOKSHELF, 300);
                registry.add(RegionsUnexplored.RU_MAGNOLIA_CHISELED_BOOKSHELF, 300);
                registry.add(RegionsUnexplored.RU_SOCOTRA_CHISELED_BOOKSHELF, 300);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("snifferplus")) {
            registry.add(SnifferPlus.SP_STONE_PINE_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("techreborn")) {
            registry.add(TechReborn.TR_RUBBER_CHISELED_BOOKSHELF, 300);
        }
        if (FabricLoader.getInstance().isModLoaded("vinery")) {
            registry.add(Vinery.LDV_CHERRY_CHISELED_BOOKSHELF, 300);
        }
    }
}