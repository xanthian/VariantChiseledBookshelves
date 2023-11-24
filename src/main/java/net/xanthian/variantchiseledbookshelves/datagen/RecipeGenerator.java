package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;
import net.xanthian.variantchiseledbookshelves.util.ModItemTags;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    public static void offerChiseledBookshelfRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible bookshelf, ItemConvertible plank, ItemConvertible slab) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, bookshelf)
                .input('#', plank).input('X', slab)
                .pattern("###").pattern("XXX").pattern("###")
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        offerChiseledBookshelfRecipe(exporter, Vanilla.ACACIA_CHISELED_BOOKSHELF, Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.BAMBOO_CHISELED_BOOKSHELF, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.BIRCH_CHISELED_BOOKSHELF, Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.CHERRY_CHISELED_BOOKSHELF, Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.CRIMSON_CHISELED_BOOKSHELF, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.DARK_OAK_CHISELED_BOOKSHELF, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.JUNGLE_CHISELED_BOOKSHELF, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.MANGROVE_CHISELED_BOOKSHELF, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.SPRUCE_CHISELED_BOOKSHELF, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB);
        offerChiseledBookshelfRecipe(exporter, Vanilla.WARPED_CHISELED_BOOKSHELF, Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB);

        // Ad Astra (disabled)
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("ad_astra")), AdAstra.AA_AERONOS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("adastra:aeronos_planks")), Registries.ITEM.get(new Identifier("adastra:strophar_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("ad_astra")), AdAstra.AA_GLACIAN_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("adastra:glacian_planks")), Registries.ITEM.get(new Identifier("adastra:strophar_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("ad_astra")), AdAstra.AA_STROPHAR_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("adastra:strophar_planks")), Registries.ITEM.get(new Identifier("adastra:strophar_slab")));

        // Beach Party (Lets Do)
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("beachparty")), BeachParty.LDBP_PALM_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("beachparty:palm_planks")), Registries.ITEM.get(new Identifier("beachparty:palm_slab")));

        // Better Archeology
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("betterarcheology")), BetterArcheology.BA_ROTTEN_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("betterarcheology:rotten_planks")), Registries.ITEM.get(new Identifier("betterarcheology:rotten_slab")));

        // Bewitchment
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("bewitchment")), Bewitchment.BW_CYPRESS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("bewitchment:cypress_planks")), Registries.ITEM.get(new Identifier("bewitchment:cypress_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("bewitchment")), Bewitchment.BW_DRAGONS_BLOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("bewitchment:dragons_blood_planks")), Registries.ITEM.get(new Identifier("bewitchment:dragons_blood_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("bewitchment")), Bewitchment.BW_ELDER_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("bewitchment:elder_planks")), Registries.ITEM.get(new Identifier("bewitchment:elder_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("bewitchment")), Bewitchment.BW_JUNIPER_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("bewitchment:juniper_planks")), Registries.ITEM.get(new Identifier("bewitchment:juniper_slab")));

        // Biome Makeover
        //offerChiseledBookshelfRecipe(withConditions(exporter,DefaultResourceConditions.allModsLoaded("biomemakeover")),BiomeMakeover.BM_ANCIENT_OAK_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("biomemakeover:ancient_oak_planks")), Registries.ITEM.get(new Identifier("biomemakeover:ancient_oak_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter,DefaultResourceConditions.allModsLoaded("biomemakeover")),BiomeMakeover.BM_BLIGHTED_BALSA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("biomemakeover:blighted_balsa_planks")), Registries.ITEM.get(new Identifier("biomemakeover:blighted_balsa_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter,DefaultResourceConditions.allModsLoaded("biomemakeover")),BiomeMakeover.BM_SWAMP_CYPRESS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("biomemakeover:swamp_cypress_planks")), Registries.ITEM.get(new Identifier("biomemakeover:swamp_cypress_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter,DefaultResourceConditions.allModsLoaded("biomemakeover")),BiomeMakeover.BM_WILLOW_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("biomemakeover:willow_planks")), Registries.ITEM.get(new Identifier("biomemakeover:willow_slab")));

        // Deeper & Darker (disabled)
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("deeperdarker")), DeeperAndDarker.DAD_ECHO_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("deeperanddarker:echo_planks")), Registries.ITEM.get(new Identifier("deeperanddarker:echo_slab")));

        // Eldritch End
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("eldritch_end")), EldritchEnd.EE_PRIMORDIAL_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("eldritch_end:primordial_planks")), Registries.ITEM.get(new Identifier("eldritch_end:primordial_slab")));

        // MineCells
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("minecells")), MineCells.MC_PUTRID_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("minecells:putrid_planks")), Registries.ITEM.get(new Identifier("minecells:putrid_slab")));

        // Natures Spirit (disabled)
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_ASPEN_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:aspen_planks")), Registries.ITEM.get(new Identifier("natures_spirit:aspen_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_CYPRESS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:cypress_planks")), Registries.ITEM.get(new Identifier("natures_spirit:cypress_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_FIR_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:fir_planks")), Registries.ITEM.get(new Identifier("natures_spirit:fir_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_JOSHUA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:joshua_planks")), Registries.ITEM.get(new Identifier("natures_spirit:joshua_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_MAPLE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:maple_planks")), Registries.ITEM.get(new Identifier("natures_spirit:maple_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_OLIVE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:olive_planks")), Registries.ITEM.get(new Identifier("natures_spirit:olive_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_REDWOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:redwood_planks")), Registries.ITEM.get(new Identifier("natures_spirit:redwood_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_SUGI_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:sugi_planks")), Registries.ITEM.get(new Identifier("natures_spirit:sugi_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_WILLOW_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:willow_planks")), Registries.ITEM.get(new Identifier("natures_spirit:willow_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("natures_spirit")), NaturesSpirit.NS_WISTERIA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("natures_spirit:wisteria_planks")), Registries.ITEM.get(new Identifier("natures_spirit:wisteria_slab")));

        // Promenade
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("promenade")), Promenade.PROM_DARK_AMARANTH_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("promenade:dark_amaranth_planks")), Registries.ITEM.get(new Identifier("promenade:dark_amaranth_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("promenade")), Promenade.PROM_MAPLE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("promenade:maple_planks")), Registries.ITEM.get(new Identifier("promenade:maple_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("promenade")), Promenade.PROM_PALM_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("promenade:palm_planks")), Registries.ITEM.get(new Identifier("promenade:palm_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("promenade")), Promenade.PROM_SAKURA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("promenade:sakura_planks")), Registries.ITEM.get(new Identifier("promenade:sakura_slab")));

        // Regions Unexplored
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_ALPHA_OAK_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:alpha_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:alpha_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_BAOBAB_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:baobab_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:baobab_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_BLACK_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:black_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:black_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_BLACKWOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:blackwood_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:blackwood_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_BLUE_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:blue_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:blue_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_BROWN_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:brown_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:brown_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_CYAN_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:cyan_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:cyan_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_CYPRESS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:cypress_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:cypress_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_DEAD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:dead_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:dead_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_EUCALYPTUS_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:eucalyptus_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:eucalyptus_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_GREEN_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:green_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:green_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_GRAY_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:gray_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:gray_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_JOSHUA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:joshua_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:joshua_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_LARCH_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:larch_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:larch_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_LIGHT_BLUE_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:light_blue_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:light_blue_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_LIGHT_GRAY_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:light_gray_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:light_gray_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_LIME_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:lime_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:lime_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_MAGENTA_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:magenta_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:magenta_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_MAPLE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:maple_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:maple_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_MAUVE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:mauve_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:mauve_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_ORANGE_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:orange_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:orange_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_PALM_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:palm_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:palm_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_PINE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:pine_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:pine_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_PINK_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:pink_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:pink_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_PURPLE_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:purple_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:purple_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_RED_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:red_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:red_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_REDWOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:redwood_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:redwood_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_WHITE_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:white_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:white_painted_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_WILLOW_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:willow_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:willow_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("regions_unexplored")), RegionsUnexplored.RU_YELLOW_PAINTED_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:yellow_painted_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:yellow_painted_slab")));
        //0.4.1 (disabled)
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:cherry_planks"))))), RegionsUnexplored.RU_CHERRY_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:cherry_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:cherry_slab")));
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:sculkwood_planks"))))), RegionsUnexplored.RU_SCULKWOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:sculkwood_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:sculkwood_slab")));
        //0.5.0
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:brimwood_planks"))))), RegionsUnexplored.RU_BRIMWOOD_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:brimwood_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:brimwood_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:cobalt_planks"))))), RegionsUnexplored.RU_COBALT_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:cobalt_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:cobalt_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:kapok_planks"))))), RegionsUnexplored.RU_KAPOK_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:kapok_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:kapok_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:magnolia_planks"))))), RegionsUnexplored.RU_MAGNOLIA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:magnolia_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:magnolia_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:socotra_planks"))))), RegionsUnexplored.RU_SOCOTRA_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:socotra_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:socotra_slab")));
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.and(DefaultResourceConditions.allModsLoaded("regions_unexplored"), DefaultResourceConditions.registryContains(RegistryKey.of(RegistryKeys.BLOCK, new Identifier("regions_unexplored:yellow_bioshroom_planks"))))), RegionsUnexplored.RU_YELLOW_BIOSHROOM_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("regions_unexplored:yellow_bioshroom_planks")), Registries.ITEM.get(new Identifier("regions_unexplored:yellow_bioshroom_slab")));

        // SnifferPlus (disabled)
        //offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("snifferplus")), SnifferPlus.SP_STONE_PINE_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("snifferplus:stone_pine_planks")), Registries.ITEM.get(new Identifier("snifferplus:stone_pine_slab")));

        // Tech Reborn
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("techreborn")), TechReborn.TR_RUBBER_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("techreborn:rubber_planks")), Registries.ITEM.get(new Identifier("techreborn:rubber_slab")));

        // Vinery (Lets Do)
        offerChiseledBookshelfRecipe(withConditions(exporter, DefaultResourceConditions.allModsLoaded("vinery")), Vinery.LDV_CHERRY_CHISELED_BOOKSHELF, Registries.ITEM.get(new Identifier("vinery:cherry_planks")), Registries.ITEM.get(new Identifier("vinery:cherry_slab")));

        // Uncrafting recipe
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.CHISELED_BOOKSHELF)
                .input(ModItemTags.CHISELED_BOOKSHELVES)
                .criterion("has_chiseled_bookshelf", InventoryChangedCriterion.Conditions.items(Items.CHISELED_BOOKSHELF))
                .offerTo(exporter, new Identifier("variantchiseledbookshelves", "chiseled_bookshelf"));

    }
}
