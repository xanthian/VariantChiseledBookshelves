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

public class BiomeMakeover {

    public static Map<Identifier, Block> BM_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block BM_ANCIENT_OAK_CHISELED_BOOKSHELF;
    public static Block BM_BLIGHTED_BALSA_CHISELED_BOOKSHELF;
    public static Block BM_SWAMP_CYPRESS_CHISELED_BOOKSHELF;
    public static Block BM_WILLOW_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        BM_ANCIENT_OAK_CHISELED_BOOKSHELF = registerChiseledBookshelf("bm_ancient_oak_chiseled_bookshelf");
        BM_BLIGHTED_BALSA_CHISELED_BOOKSHELF = registerChiseledBookshelf("bm_blighted_balsa_chiseled_bookshelf");
        BM_SWAMP_CYPRESS_CHISELED_BOOKSHELF = registerChiseledBookshelf("bm_swamp_cypress_chiseled_bookshelf");
        BM_WILLOW_CHISELED_BOOKSHELF = registerChiseledBookshelf("bm_willow_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        BM_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}