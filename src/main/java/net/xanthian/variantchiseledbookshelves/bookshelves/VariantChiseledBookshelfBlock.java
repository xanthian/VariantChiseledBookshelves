package net.xanthian.variantchiseledbookshelves.bookshelves;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChiseledBookshelfBlock;


public class VariantChiseledBookshelfBlock extends ChiseledBookshelfBlock {

    public VariantChiseledBookshelfBlock() {
        super(Settings.copy(Blocks.CHISELED_BOOKSHELF));
    }

}