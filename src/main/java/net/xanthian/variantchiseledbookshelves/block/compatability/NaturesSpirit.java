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

public class NaturesSpirit {

    public static Map<Identifier, Block> NS_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block NS_ASPEN_CHISELED_BOOKSHELF;
    public static Block NS_CYPRESS_CHISELED_BOOKSHELF;
    public static Block NS_FIR_CHISELED_BOOKSHELF;
    public static Block NS_JOSHUA_CHISELED_BOOKSHELF;
    public static Block NS_MAPLE_CHISELED_BOOKSHELF;
    public static Block NS_OLIVE_CHISELED_BOOKSHELF;
    public static Block NS_REDWOOD_CHISELED_BOOKSHELF;
    public static Block NS_SUGI_CHISELED_BOOKSHELF;
    public static Block NS_WILLOW_CHISELED_BOOKSHELF;
    public static Block NS_WISTERIA_CHISELED_BOOKSHELF;

    public static Block NS_CEDAR_CHISELED_BOOKSHELF;
    public static Block NS_COCONUT_CHISELED_BOOKSHELF;
    public static Block NS_GHAF_CHISELED_BOOKSHELF;
    public static Block NS_LARCH_CHISELED_BOOKSHELF;
    public static Block NS_PALO_VERDE_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        NS_ASPEN_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_aspen_chiseled_bookshelf");
        NS_CYPRESS_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_cypress_chiseled_bookshelf");
        NS_FIR_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_fir_chiseled_bookshelf");
        NS_JOSHUA_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_joshua_chiseled_bookshelf");
        NS_MAPLE_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_maple_chiseled_bookshelf");
        NS_OLIVE_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_olive_chiseled_bookshelf");
        NS_REDWOOD_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_redwood_chiseled_bookshelf");
        NS_SUGI_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_sugi_chiseled_bookshelf");
        NS_WILLOW_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_willow_chiseled_bookshelf");
        NS_WISTERIA_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_wisteria_chiseled_bookshelf");

        NS_CEDAR_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_cedar_chiseled_bookshelf");
        NS_COCONUT_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_coconut_chiseled_bookshelf");
        NS_GHAF_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_ghaf_chiseled_bookshelf");
        NS_LARCH_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_larch_chiseled_bookshelf");
        NS_PALO_VERDE_CHISELED_BOOKSHELF = registerChiseledBookshelf("ns_palo_verde_chiseled_bookshelf");
    }

    private static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        NS_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    private static Block registerChiseledBookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}