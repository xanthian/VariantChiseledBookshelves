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

public class RegionsUnexplored {

    public static Map<Identifier, Block> RU_CHISELED_BOOKSHELVES = Maps.newHashMap();

    public static Block RU_ALPHA_OAK_CHISELED_BOOKSHELF;
    public static Block RU_BAOBAB_CHISELED_BOOKSHELF;
    public static Block RU_BLACK_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_BLACKWOOD_CHISELED_BOOKSHELF;
    public static Block RU_BLUE_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_BROWN_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_CYAN_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_CYPRESS_CHISELED_BOOKSHELF;
    public static Block RU_DEAD_CHISELED_BOOKSHELF;
    public static Block RU_EUCALYPTUS_CHISELED_BOOKSHELF;
    public static Block RU_GRAY_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_GREEN_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_JOSHUA_CHISELED_BOOKSHELF;
    public static Block RU_LARCH_CHISELED_BOOKSHELF;
    public static Block RU_LIGHT_BLUE_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_LIGHT_GRAY_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_LIME_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_MAGENTA_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_MAPLE_CHISELED_BOOKSHELF;
    public static Block RU_MAUVE_CHISELED_BOOKSHELF;
    public static Block RU_ORANGE_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_PALM_CHISELED_BOOKSHELF;
    public static Block RU_PINE_CHISELED_BOOKSHELF;
    public static Block RU_PINK_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_PURPLE_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_REDWOOD_CHISELED_BOOKSHELF;
    public static Block RU_RED_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_WHITE_PAINTED_CHISELED_BOOKSHELF;
    public static Block RU_WILLOW_CHISELED_BOOKSHELF;
    public static Block RU_YELLOW_PAINTED_CHISELED_BOOKSHELF;

    public static Block RU_CHERRY_CHISELED_BOOKSHELF;
    public static Block RU_SCULKWOOD_CHISELED_BOOKSHELF;

    public static Block RU_BRIMWOOD_CHISELED_BOOKSHELF;
    public static Block RU_COBALT_CHISELED_BOOKSHELF;
    public static Block RU_KAPOK_CHISELED_BOOKSHELF;
    public static Block RU_MAGNOLIA_CHISELED_BOOKSHELF;
    public static Block RU_SOCOTRA_CHISELED_BOOKSHELF;
    public static Block RU_YELLOW_BIOSHROOM_CHISELED_BOOKSHELF;

    public static void registerChiseledBookshelves() {
        RU_ALPHA_OAK_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_alpha_oak_chiseled_bookshelf");
        RU_BAOBAB_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_baobab_chiseled_bookshelf");
        RU_BLACK_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_black_painted_chiseled_bookshelf");
        RU_BLACKWOOD_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_blackwood_chiseled_bookshelf");
        RU_BLUE_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_blue_painted_chiseled_bookshelf");
        RU_BROWN_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_brown_painted_chiseled_bookshelf");
        RU_CYAN_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_cyan_painted_chiseled_bookshelf");
        RU_CYPRESS_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_cypress_chiseled_bookshelf");
        RU_DEAD_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_dead_chiseled_bookshelf");
        RU_EUCALYPTUS_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_eucalyptus_chiseled_bookshelf");
        RU_GREEN_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_green_painted_chiseled_bookshelf");
        RU_GRAY_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_gray_painted_chiseled_bookshelf");
        RU_JOSHUA_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_joshua_chiseled_bookshelf");
        RU_LARCH_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_larch_chiseled_bookshelf");
        RU_LIGHT_BLUE_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_light_blue_painted_chiseled_bookshelf");
        RU_LIGHT_GRAY_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_light_gray_painted_chiseled_bookshelf");
        RU_LIME_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_lime_painted_chiseled_bookshelf");
        RU_MAGENTA_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_magenta_painted_chiseled_bookshelf");
        RU_MAPLE_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_maple_chiseled_bookshelf");
        RU_MAUVE_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_mauve_chiseled_bookshelf");
        RU_ORANGE_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_orange_painted_chiseled_bookshelf");
        RU_PALM_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_palm_chiseled_bookshelf");
        RU_PINE_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_pine_chiseled_bookshelf");
        RU_PINK_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_pink_painted_chiseled_bookshelf");
        RU_PURPLE_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_purple_painted_chiseled_bookshelf");
        RU_RED_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_red_painted_chiseled_bookshelf");
        RU_REDWOOD_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_redwood_chiseled_bookshelf");
        RU_WHITE_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_white_painted_chiseled_bookshelf");
        RU_WILLOW_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_willow_chiseled_bookshelf");
        RU_YELLOW_PAINTED_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_yellow_painted_chiseled_bookshelf");
    }

    public static void register04ChiseledBookshelves() {
        RU_CHERRY_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_cherry_chiseled_bookshelf");
        RU_SCULKWOOD_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_sculkwood_chiseled_bookshelf");
    }

    public static void register05ChiseledBookshelves() {
        RU_BRIMWOOD_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_brimwood_chiseled_bookshelf");
        RU_COBALT_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_cobalt_chiseled_bookshelf");
        RU_KAPOK_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_kapok_chiseled_bookshelf");
        RU_MAGNOLIA_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_magnolia_chiseled_bookshelf");
        RU_SOCOTRA_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_socotra_chiseled_bookshelf");
        RU_YELLOW_BIOSHROOM_CHISELED_BOOKSHELF = registerChiseled_Bookshelf("ru_yellow_bioshroom_chiseled_bookshelf");
    }

    public static Block register(String name, Block block) {
        Identifier identifier = new Identifier(Initialise.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);
        RU_CHISELED_BOOKSHELVES.put(identifier, block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static Block registerChiseled_Bookshelf(String name) {
        return register(name, new VariantChiseledBookshelfBlock());
    }
}