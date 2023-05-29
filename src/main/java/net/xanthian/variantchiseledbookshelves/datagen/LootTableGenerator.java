package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;

public class LootTableGenerator extends FabricBlockLootTableProvider {
    public LootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDropWithSilkTouch(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF);
        addDropWithSilkTouch(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF);
    }
}