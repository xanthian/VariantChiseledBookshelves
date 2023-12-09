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

public class Bewitchment {

    public static Map<Identifier, Block> BW_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block BW_CYPRESS_CHISELED_BOOKSHELF;
    public static Block BW_DRAGONS_BLOOD_CHISELED_BOOKSHELF;
    public static Block BW_ELDER_CHISELED_BOOKSHELF;
    public static Block BW_JUNIPER_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        BW_CYPRESS_CHISELED_BOOKSHELF = registerChiseledBookshelf("bw_cypress_chiseled_bookshelf");
        BW_DRAGONS_BLOOD_CHISELED_BOOKSHELF = registerChiseledBookshelf("bw_dragons_blood_chiseled_bookshelf");
        BW_ELDER_CHISELED_BOOKSHELF = registerChiseledBookshelf("bw_elder_chiseled_bookshelf");
        BW_JUNIPER_CHISELED_BOOKSHELF = registerChiseledBookshelf("bw_juniper_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        BW_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}