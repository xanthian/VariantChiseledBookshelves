package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;
import net.xanthian.variantchiseledbookshelves.util.ModItemTags;

import java.util.Map;
import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }


    public static void offerChiseledBookshelfRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible bookshelf, ItemConvertible plank, ItemConvertible slab) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, bookshelf)
                .input('#', plank).input('X', slab)
                .pattern("###").pattern("XXX").pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        offerChiseledBookshelfRecipe(exporter, Vanilla.ACACIA_CHISELED_BOOKSHELF, Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.BAMBOO_CHISELED_BOOKSHELF, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.BIRCH_CHISELED_BOOKSHELF, Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.CHERRY_CHISELED_BOOKSHELF, Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.CRIMSON_CHISELED_BOOKSHELF, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.DARK_OAK_CHISELED_BOOKSHELF, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.JUNGLE_CHISELED_BOOKSHELF, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.MANGROVE_CHISELED_BOOKSHELF, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.SPRUCE_CHISELED_BOOKSHELF, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.WARPED_CHISELED_BOOKSHELF, Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB);


        registerChiseledBookshelfRecipe(exporter, AdAstra.AA_CHISELED_BOOKSHELVES, "ad_astra");
        registerChiseledBookshelfRecipe(exporter, BeachParty.LDBP_CHISELED_BOOKSHELVES, "beachparty");
        registerChiseledBookshelfRecipe(exporter, BetterArcheology.BA_CHISELED_BOOKSHELVES, "betterarcheology");
        registerChiseledBookshelfRecipe(exporter, Bewitchment.BW_CHISELED_BOOKSHELVES, "bewitchment");
        registerChiseledBookshelfRecipe(exporter, BiomeMakeover.BM_CHISELED_BOOKSHELVES, "biomemakeover");
        registerChiseledBookshelfRecipe(exporter, Blockus.BLS_CHISELED_BOOKSHELVES, "blockus");
        //registerChiseledBookshelfRecipe(exporter, Botania.BOT_CHISELED_BOOKSHELVES, "botania");
        registerChiseledBookshelfRecipe(exporter, Cinderscapes.CS_CHISELED_BOOKSHELVES, "cinderscapes");
        registerChiseledBookshelfRecipe(exporter, DeeperAndDarker.DAD_CHISELED_BOOKSHELVES, "deeperdarker");
        registerChiseledBookshelfRecipe(exporter, Desolation.DS_CHISELED_BOOKSHELVES, "desolation");
        registerChiseledBookshelfRecipe(exporter, EldritchEnd.EE_CHISELED_BOOKSHELVES, "eldritch_end");
        registerChiseledBookshelfRecipe(exporter, MineCells.MC_CHISELED_BOOKSHELVES, "minecells");
        registerChiseledBookshelfRecipe(exporter, NaturesSpirit.NS_CHISELED_BOOKSHELVES, "natures_spirit");
        registerChiseledBookshelfRecipe(exporter, Promenade.PROM_CHISELED_BOOKSHELVES, "promenade");
        registerChiseledBookshelfRecipe(exporter, RegionsUnexplored.RU_CHISELED_BOOKSHELVES, "regions_unexplored");
        //registerChiseledBookshelfRecipe(exporter, SnifferPlus.SP_CHISELED_BOOKSHELVES, "snifferplus");
        registerChiseledBookshelfRecipe(exporter, TechReborn.TR_CHISELED_BOOKSHELVES, "techreborn");
        registerChiseledBookshelfRecipe(exporter, Vinery.LDV_CHISELED_BOOKSHELVES, "vinery");

        // Uncrafting recipe
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.CHISELED_BOOKSHELF)
                .input(ModItemTags.CHISELED_BOOKSHELVES)
                .criterion("has_chiseled_bookshelf", InventoryChangedCriterion.Conditions.items(Items.CHISELED_BOOKSHELF))
                .offerTo(exporter, new Identifier("variantchiseledbookshelves", "chiseled_bookshelf"));

    }

    public void registerChiseledBookshelfRecipe(Consumer<RecipeJsonProvider> exporter, Map<Identifier, Block> bookshelf, String modId) {
        registerChiseledBookshelfRecipe(exporter, bookshelf, modId, "_planks", "_slab");
    }

    public void registerChiseledBookshelfRecipe(Consumer<RecipeJsonProvider> exporter, Map<Identifier, Block> bookshelves, String modId, String plankSuffix, String slabSuffix) {
        for (Map.Entry<Identifier, Block> entry : bookshelves.entrySet()) {
            Identifier bookshelfId = entry.getKey();
            Block bookshelf = entry.getValue();
            String path = bookshelfId.getPath();
            String name = path.replace("variantchiseledbookshelves:", "").replace("_chiseled_bookshelf", "").replaceFirst("^[^_]+_", "");
            String plankPath = modId + ":" + name + plankSuffix;
            String slabPath = modId + ":" + name + slabSuffix;
            offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded(modId),
                            DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier(plankPath))))),
                    bookshelf, Registries.ITEM.get(new Identifier(plankPath)), Registries.ITEM.get(new Identifier(slabPath)));
        }
    }
}
