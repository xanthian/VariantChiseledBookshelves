package net.xanthian.variantchiseledbookshelves.block;

import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;

import java.util.Map;

import static java.util.Map.entry;

/** Registry IDs for the 1.21.1 dynamic table backport. */
public final class ShelfIds {
    private static final String TABLE_SUFFIX = "_chiseled_bookshelf";

    // These paths are already stored in worlds. Key by the full source plank ID
    // so newly discovered woods from these mods still get the standard dynamic ID.
    private static final Map<String, String> LEGACY_PATHS = Map.ofEntries(
            entry("ad_astra:aeronos_planks", "aa_aeronos_chiseled_bookshelf"),
            entry("ad_astra:glacian_planks", "aa_glacian_chiseled_bookshelf"),
            entry("ad_astra:strophar_planks", "aa_strophar_chiseled_bookshelf"),
            entry("beachparty:palm_planks", "ldbp_palm_chiseled_bookshelf"),
            entry("betterarcheology:rotten_planks", "ba_rotten_chiseled_bookshelf"),
            entry("bewitchment:cypress_planks", "bw_cypress_chiseled_bookshelf"),
            entry("bewitchment:dragons_blood_planks", "bw_dragons_blood_chiseled_bookshelf"),
            entry("bewitchment:elder_planks", "bw_elder_chiseled_bookshelf"),
            entry("bewitchment:juniper_planks", "bw_juniper_chiseled_bookshelf"),
            entry("biomemakeover:ancient_oak_planks", "bm_ancient_oak_chiseled_bookshelf"),
            entry("biomemakeover:blighted_balsa_planks", "bm_blighted_balsa_chiseled_bookshelf"),
            entry("biomemakeover:swamp_cypress_planks", "bm_swamp_cypress_chiseled_bookshelf"),
            entry("biomemakeover:willow_planks", "bm_willow_chiseled_bookshelf"),
            entry("blockus:charred_planks", "bls_charred_chiseled_bookshelf"),
            entry("blockus:raw_bamboo_planks", "bls_raw_bamboo_chiseled_bookshelf"),
            entry("blockus:white_oak_planks", "bls_white_oak_chiseled_bookshelf"),
            entry("cinderscapes:scorched_planks", "cs_scorched_chiseled_bookshelf"),
            entry("cinderscapes:umbral_planks", "cs_umbral_chiseled_bookshelf"),
            entry("deeperdarker:echo_planks", "dad_echo_chiseled_bookshelf"),
            entry("desolation:charred_planks", "ds_charred_chiseled_bookshelf"),
            entry("eldritch_end:primordial_planks", "ee_primordial_chiseled_bookshelf"),
            entry("minecells:putrid_planks", "mc_putrid_chiseled_bookshelf"),
            entry("minecraft:acacia_planks", "acacia_chiseled_bookshelf"),
            entry("minecraft:bamboo_planks", "bamboo_chiseled_bookshelf"),
            entry("minecraft:birch_planks", "birch_chiseled_bookshelf"),
            entry("minecraft:cherry_planks", "cherry_chiseled_bookshelf"),
            entry("minecraft:crimson_planks", "crimson_chiseled_bookshelf"),
            entry("minecraft:dark_oak_planks", "dark_oak_chiseled_bookshelf"),
            entry("minecraft:jungle_planks", "jungle_chiseled_bookshelf"),
            entry("minecraft:mangrove_planks", "mangrove_chiseled_bookshelf"),
            entry("minecraft:spruce_planks", "spruce_chiseled_bookshelf"),
            entry("minecraft:warped_planks", "warped_chiseled_bookshelf"),
            entry("natures_spirit:aspen_planks", "ns_aspen_chiseled_bookshelf"),
            entry("natures_spirit:cedar_planks", "ns_cedar_chiseled_bookshelf"),
            entry("natures_spirit:coconut_planks", "ns_coconut_chiseled_bookshelf"),
            entry("natures_spirit:cypress_planks", "ns_cypress_chiseled_bookshelf"),
            entry("natures_spirit:fir_planks", "ns_fir_chiseled_bookshelf"),
            entry("natures_spirit:ghaf_planks", "ns_ghaf_chiseled_bookshelf"),
            entry("natures_spirit:joshua_planks", "ns_joshua_chiseled_bookshelf"),
            entry("natures_spirit:larch_planks", "ns_larch_chiseled_bookshelf"),
            entry("natures_spirit:maple_planks", "ns_maple_chiseled_bookshelf"),
            entry("natures_spirit:olive_planks", "ns_olive_chiseled_bookshelf"),
            entry("natures_spirit:palo_verde_planks", "ns_palo_verde_chiseled_bookshelf"),
            entry("natures_spirit:redwood_planks", "ns_redwood_chiseled_bookshelf"),
            entry("natures_spirit:sugi_planks", "ns_sugi_chiseled_bookshelf"),
            entry("natures_spirit:willow_planks", "ns_willow_chiseled_bookshelf"),
            entry("natures_spirit:wisteria_planks", "ns_wisteria_chiseled_bookshelf"),
            entry("promenade:dark_amaranth_planks", "prom_dark_amaranth_chiseled_bookshelf"),
            entry("promenade:maple_planks", "prom_maple_chiseled_bookshelf"),
            entry("promenade:palm_planks", "prom_palm_chiseled_bookshelf"),
            entry("promenade:sakura_planks", "prom_sakura_chiseled_bookshelf"),
            entry("regions_unexplored:alpha_planks", "ru_alpha_chiseled_bookshelf"),
            entry("regions_unexplored:baobab_planks", "ru_baobab_chiseled_bookshelf"),
            entry("regions_unexplored:black_painted_planks", "ru_black_painted_chiseled_bookshelf"),
            entry("regions_unexplored:blackwood_planks", "ru_blackwood_chiseled_bookshelf"),
            entry("regions_unexplored:blue_bioshroom_planks", "ru_blue_bioshroom_chiseled_bookshelf"),
            entry("regions_unexplored:blue_painted_planks", "ru_blue_painted_chiseled_bookshelf"),
            entry("regions_unexplored:brimwood_planks", "ru_brimwood_chiseled_bookshelf"),
            entry("regions_unexplored:brown_painted_planks", "ru_brown_painted_chiseled_bookshelf"),
            entry("regions_unexplored:cobalt_planks", "ru_cobalt_chiseled_bookshelf"),
            entry("regions_unexplored:cyan_painted_planks", "ru_cyan_painted_chiseled_bookshelf"),
            entry("regions_unexplored:cypress_planks", "ru_cypress_chiseled_bookshelf"),
            entry("regions_unexplored:dead_planks", "ru_dead_chiseled_bookshelf"),
            entry("regions_unexplored:eucalyptus_planks", "ru_eucalyptus_chiseled_bookshelf"),
            entry("regions_unexplored:gray_painted_planks", "ru_gray_painted_chiseled_bookshelf"),
            entry("regions_unexplored:green_bioshroom_planks", "ru_green_bioshroom_chiseled_bookshelf"),
            entry("regions_unexplored:green_painted_planks", "ru_green_painted_chiseled_bookshelf"),
            entry("regions_unexplored:joshua_planks", "ru_joshua_chiseled_bookshelf"),
            entry("regions_unexplored:kapok_planks", "ru_kapok_chiseled_bookshelf"),
            entry("regions_unexplored:larch_planks", "ru_larch_chiseled_bookshelf"),
            entry("regions_unexplored:light_blue_painted_planks", "ru_light_blue_painted_chiseled_bookshelf"),
            entry("regions_unexplored:light_gray_painted_planks", "ru_light_gray_painted_chiseled_bookshelf"),
            entry("regions_unexplored:lime_painted_planks", "ru_lime_painted_chiseled_bookshelf"),
            entry("regions_unexplored:magenta_painted_planks", "ru_magenta_painted_chiseled_bookshelf"),
            entry("regions_unexplored:magnolia_planks", "ru_magnolia_chiseled_bookshelf"),
            entry("regions_unexplored:maple_planks", "ru_maple_chiseled_bookshelf"),
            entry("regions_unexplored:mauve_planks", "ru_mauve_chiseled_bookshelf"),
            entry("regions_unexplored:orange_painted_planks", "ru_orange_painted_chiseled_bookshelf"),
            entry("regions_unexplored:palm_planks", "ru_palm_chiseled_bookshelf"),
            entry("regions_unexplored:pine_planks", "ru_pine_chiseled_bookshelf"),
            entry("regions_unexplored:pink_bioshroom_planks", "ru_pink_bioshroom_chiseled_bookshelf"),
            entry("regions_unexplored:pink_painted_planks", "ru_pink_painted_chiseled_bookshelf"),
            entry("regions_unexplored:purple_painted_planks", "ru_purple_painted_chiseled_bookshelf"),
            entry("regions_unexplored:red_painted_planks", "ru_red_painted_chiseled_bookshelf"),
            entry("regions_unexplored:redwood_planks", "ru_redwood_chiseled_bookshelf"),
            entry("regions_unexplored:socotra_planks", "ru_socotra_chiseled_bookshelf"),
            entry("regions_unexplored:white_painted_planks", "ru_white_painted_chiseled_bookshelf"),
            entry("regions_unexplored:willow_planks", "ru_willow_chiseled_bookshelf"),
            entry("regions_unexplored:yellow_bioshroom_planks", "ru_yellow_bioshroom_chiseled_bookshelf"),
            entry("regions_unexplored:yellow_painted_planks", "ru_yellow_painted_chiseled_bookshelf"),
            entry("techreborn:rubber_planks", "tr_rubber_chiseled_bookshelf"),
            entry("vinery:cherry_planks", "ldv_cherry_chiseled_bookshelf")
    );
    private ShelfIds() {
    }

    /** Resolves the table ID for a source plank block, including the existing oak table. */
    public static Identifier forPlanks(Identifier planks) {
        String legacyPath = LEGACY_PATHS.get(planks.toString());
        if (legacyPath != null) {
            return Identifier.of(Initialise.MOD_ID, legacyPath);
        }

        String namespace = planks.getNamespace();
        String path = planks.getPath();
        if (namespace.equals("minecraft")) {
            if (path.equals("oak_planks")) {
                return Identifier.ofVanilla("chiseled_bookshelf");
            }
            return Identifier.of(Initialise.MOD_ID, path.replaceFirst("_planks$", "") + TABLE_SUFFIX);
        }
        return Identifier.of(Initialise.MOD_ID, "mod/" + namespace + "/" + path + TABLE_SUFFIX);
    }
}
