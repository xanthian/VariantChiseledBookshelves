package net.xanthian.variantchiseledbookshelves.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;

import static net.xanthian.variantchiseledbookshelves.Initialise.isModVersion;


public class ModCreativeTab {

    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Initialise.MOD_ID, "variantchiseledbookshelves"),

            FabricItemGroup.builder()
                    .displayName(Text.literal("Variant Chiseled Bookshelves"))
                    .icon(() -> new ItemStack(Vanilla.MANGROVE_CHISELED_BOOKSHELF))
                    .entries((context, entries) -> {


                        entries.add(Vanilla.ACACIA_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.BAMBOO_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.BIRCH_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.CHERRY_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.CRIMSON_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.DARK_OAK_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.JUNGLE_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.MANGROVE_CHISELED_BOOKSHELF);
                        entries.add(Blocks.CHISELED_BOOKSHELF); // Oak
                        entries.add(Vanilla.SPRUCE_CHISELED_BOOKSHELF);
                        entries.add(Vanilla.WARPED_CHISELED_BOOKSHELF);

                        if (FabricLoader.getInstance().isModLoaded("ad_astra")) {
                            for (Block block : AdAstra.AA_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("beachparty")) {
                            entries.add(BeachParty.LDBP_PALM_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("betterarcheology")) {
                            entries.add(BetterArcheology.BA_ROTTEN_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("bewitchment")) {
                            for (Block block : Bewitchment.BW_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("biomemakeover")) {
                            for (Block block : BiomeMakeover.BM_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("deeperdarker")) {
                            entries.add(DeeperAndDarker.DAD_ECHO_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("eldritch_end")) {
                            entries.add(EldritchEnd.EE_PRIMORDIAL_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("minecells")) {
                            entries.add(MineCells.MC_PUTRID_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("natures_spirit")) {
                            for (Block block : NaturesSpirit.NS_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("promenade")) {
                            for (Block block : Promenade.PROM_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("regions_unexplored")) {
                            entries.add(RegionsUnexplored.RU_ALPHA_OAK_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_BAOBAB_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_BLACK_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_BLACKWOOD_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_BLUE_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_BROWN_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_CYAN_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_CYPRESS_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_DEAD_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_EUCALYPTUS_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_GRAY_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_GREEN_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_JOSHUA_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_LARCH_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_LIGHT_BLUE_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_LIGHT_GRAY_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_LIME_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_MAGENTA_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_MAPLE_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_MAUVE_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_ORANGE_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_PALM_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_PINE_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_PINK_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_PURPLE_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_REDWOOD_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_RED_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_WHITE_PAINTED_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_WILLOW_CHISELED_BOOKSHELF);
                            entries.add(RegionsUnexplored.RU_YELLOW_PAINTED_CHISELED_BOOKSHELF);
                            if (isModVersion("regions_unexplored", "0.4")) {
                                entries.add(RegionsUnexplored.RU_CHERRY_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_SCULKWOOD_CHISELED_BOOKSHELF);
                            } else {
                                entries.add(RegionsUnexplored.RU_BRIMWOOD_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_COBALT_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_KAPOK_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_MAGNOLIA_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_SOCOTRA_CHISELED_BOOKSHELF);
                                entries.add(RegionsUnexplored.RU_YELLOW_BIOSHROOM_CHISELED_BOOKSHELF);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("snifferplus")) {
                            entries.add(SnifferPlus.SP_STONE_PINE_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("techreborn")) {
                            entries.add(TechReborn.TR_RUBBER_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("vinery")) {
                            entries.add(Vinery.LDV_CHERRY_CHISELED_BOOKSHELF);
                        }

                    })
                    .build());

    public static void registerItemGroup() {
    }
}