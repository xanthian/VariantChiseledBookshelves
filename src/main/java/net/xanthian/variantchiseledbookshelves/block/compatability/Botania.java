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

public class Botania {

    public static Map<Identifier, Block> BOT_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block BOT_DREAMWOOD_CHISELED_BOOKSHELF;
    public static Block BOT_LIVINGWOOD_CHISELED_BOOKSHELF;
    public static Block BOT_SHIMMERWOOD_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        BOT_DREAMWOOD_CHISELED_BOOKSHELF = registerChiseledBookshelf("bot_dreamwood_chiseled_bookshelf");
        BOT_LIVINGWOOD_CHISELED_BOOKSHELF = registerChiseledBookshelf("bot_livingwood_chiseled_bookshelf");
        BOT_SHIMMERWOOD_CHISELED_BOOKSHELF = registerChiseledBookshelf("bot_shimmerwood_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        BOT_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());

    }
}