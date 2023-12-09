package net.xanthian.variantchiseledbookshelves.block;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;

import java.util.Map;

public class Vanilla {

    public static final VariantChiseledBookshelfBlock ACACIA_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock BAMBOO_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock BIRCH_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock CHERRY_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock CRIMSON_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock DARK_OAK_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock JUNGLE_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock MANGROVE_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock SPRUCE_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static final VariantChiseledBookshelfBlock WARPED_CHISELED_BOOKSHELF = new VariantChiseledBookshelfBlock();
    public static Map<Identifier, Block> VANILLA_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static void registerVanillaChiseledBookshelves() {
        registerChiseledBookshelfBlock("acacia_chiseled_bookshelf", ACACIA_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("bamboo_chiseled_bookshelf", BAMBOO_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("birch_chiseled_bookshelf", BIRCH_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("cherry_chiseled_bookshelf", CHERRY_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("crimson_chiseled_bookshelf", CRIMSON_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("dark_oak_chiseled_bookshelf", DARK_OAK_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("jungle_chiseled_bookshelf", JUNGLE_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("mangrove_chiseled_bookshelf", MANGROVE_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("spruce_chiseled_bookshelf", SPRUCE_CHISELED_BOOKSHELF);
        registerChiseledBookshelfBlock("warped_chiseled_bookshelf", WARPED_CHISELED_BOOKSHELF);
    }

    private static void registerChiseledBookshelfBlock(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        VANILLA_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
    }
}