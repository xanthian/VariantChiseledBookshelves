package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF)
                .input('#', Blocks.ACACIA_PLANKS)
                .input('X', Blocks.ACACIA_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF)
                .input('#', Blocks.BAMBOO_PLANKS)
                .input('X', Blocks.BAMBOO_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF)
                .input('#', Blocks.BIRCH_PLANKS)
                .input('X', Blocks.BIRCH_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF)
                .input('#', Blocks.CHERRY_PLANKS)
                .input('X', Blocks.CHERRY_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF)
                .input('#', Blocks.DARK_OAK_PLANKS)
                .input('X', Blocks.DARK_OAK_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF)
                .input('#', Blocks.JUNGLE_PLANKS)
                .input('X', Blocks.JUNGLE_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF)
                .input('#', Blocks.MANGROVE_PLANKS)
                .input('X', Blocks.MANGROVE_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF)
                .input('#', Blocks.SPRUCE_PLANKS)
                .input('X', Blocks.SPRUCE_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF)
                .input('#', Blocks.CRIMSON_PLANKS)
                .input('X', Blocks.CRIMSON_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF)
                .input('#', Blocks.WARPED_PLANKS)
                .input('X', Blocks.WARPED_SLAB)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF)));
    }
}
