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
                        if (FabricLoader.getInstance().isModLoaded("blockus")) {
                            for (Block block : Blockus.BLS_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("botania")) {
                            for (Block block : Botania.BOT_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("cinderscapes")) {
                            for (Block block : Cinderscapes.CS_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
                            }
                        }
                        if (FabricLoader.getInstance().isModLoaded("deeperdarker")) {
                            entries.add(DeeperAndDarker.DAD_ECHO_CHISELED_BOOKSHELF);
                        }
                        if (FabricLoader.getInstance().isModLoaded("desolation")) {
                            entries.add(Desolation.DS_CHARRED_CHISELED_BOOKSHELF);
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
                            for (Block block : RegionsUnexplored.RU_CHISELED_BOOKSHELVES.values()) {
                                entries.add(block);
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
                    .texture("variantchiseledbookshelves.png").noRenderedName().build());

    public static void registerItemGroup() {
    }
}