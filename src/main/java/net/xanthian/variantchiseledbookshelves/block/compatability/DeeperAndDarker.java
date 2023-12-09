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

public class DeeperAndDarker {

    public static Map<Identifier, Block> DAD_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block DAD_ECHO_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        DAD_ECHO_CHISELED_BOOKSHELF = registerChiseledBookshelf("dad_echo_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        DAD_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}