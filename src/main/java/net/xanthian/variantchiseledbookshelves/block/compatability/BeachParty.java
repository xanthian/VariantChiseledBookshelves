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

public class BeachParty {

    public static Map<Identifier, Block> LDBP_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block LDBP_PALM_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        LDBP_PALM_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ldbp_palm_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        LDBP_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseled_Bookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}