package net.xanthian.variantchiseledbookshelves.block.compatability;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.VariantChiseledBookshelfBlock;

import java.util.Map;

public class Blockus {

    public static Map<Identifier, Block> BLS_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block BLS_CHARRED_CHISELED_BOOKSHELF;
    public static Block BLS_RAW_BAMBOO_CHISELED_BOOKSHELF;
    public static Block BLS_WHITE_OAK_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {

        BLS_CHARRED_CHISELED_BOOKSHELF = registerChiseledBookshelf("bls_charred_chiseled_bookshelf");
        BLS_RAW_BAMBOO_CHISELED_BOOKSHELF = registerChiseledBookshelf("bls_raw_bamboo_chiseled_bookshelf");
        BLS_WHITE_OAK_CHISELED_BOOKSHELF = registerChiseledBookshelf("bls_white_oak_chiseled_bookshelf");

    }

    private static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        BLS_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    private static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}