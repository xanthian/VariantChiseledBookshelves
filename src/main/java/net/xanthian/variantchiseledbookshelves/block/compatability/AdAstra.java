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

public class AdAstra {

    public static Map<Identifier, Block> AA_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block AA_AERONOS_CHISELED_BOOKSHELF;
    public static Block AA_GLACIAN_CHISELED_BOOKSHELF;
    public static Block AA_STROPHAR_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        AA_AERONOS_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("aa_aeronos_chiseled_bookshelf");
        AA_GLACIAN_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("aa_glacian_chiseled_bookshelf");
        AA_STROPHAR_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("aa_strophar_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        AA_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseled_Bookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}