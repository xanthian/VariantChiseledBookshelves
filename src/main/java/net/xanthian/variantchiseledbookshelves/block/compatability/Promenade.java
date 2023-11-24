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

public class Promenade {

    public static Map<Identifier, Block> PROM_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block PROM_DARK_AMARANTH_CHISELED_BOOKSHELF;
    public static Block PROM_MAPLE_CHISELED_BOOKSHELF;
    public static Block PROM_PALM_CHISELED_BOOKSHELF;
    public static Block PROM_SAKURA_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        PROM_DARK_AMARANTH_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("prom_dark_amaranth_chiseled_bookshelf");
        PROM_MAPLE_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("prom_maple_chiseled_bookshelf");
        PROM_PALM_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("prom_palm_chiseled_bookshelf");
        PROM_SAKURA_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("prom_sakura_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        PROM_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseled_Bookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}