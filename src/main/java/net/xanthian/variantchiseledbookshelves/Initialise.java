package net.xanthian.variantchiseledbookshelves;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;
import net.xanthian.variantchiseledbookshelves.util.ModCreativeTab;
import net.xanthian.variantchiseledbookshelves.util.ModRegistries;

public class Initialise implements ModInitializer {

    public static final String MOD_ID = "variantchiseledbookshelves";

    public static void ifModLoaded(String modId, Runnable runnable) {
        if (FabricLoader.getInstance().isModLoaded(modId)) {
            runnable.run();
        }
    }

    @Override
    public void onInitialize() {

        Vanilla.registerVanillaChiseledBookshelves();

        ifModLoaded("ad_astra", AdAstra::registerChiseledBookshelves);

        ifModLoaded("beachparty", BeachParty::registerChiseledBookshelves);

        ifModLoaded("betterarcheology", BetterArcheology::registerChiseledBookshelves);

        ifModLoaded("bewitchment", Bewitchment::registerChiseledBookshelves);

        ifModLoaded("biomemakeover", BiomeMakeover::registerChiseledBookshelves);

        ifModLoaded("blockus", Blockus::registerChiseledBookshelves);

        ifModLoaded("botania", Botania::registerChiseledBookshelves);

        ifModLoaded("cinderscapes", Cinderscapes::registerChiseledBookshelves);

        ifModLoaded("deeperdarker", DeeperAndDarker::registerChiseledBookshelves);

        ifModLoaded("desolation", Desolation::registerChiseledBookshelves);

        ifModLoaded("eldritch_end", EldritchEnd::registerChiseledBookshelves);

        ifModLoaded("minecells", MineCells::registerChiseledBookshelves);

        ifModLoaded("natures_spirit", NaturesSpirit::registerChiseledBookshelves);

        ifModLoaded("promenade", Promenade::registerChiseledBookshelves);

        ifModLoaded("regions_unexplored", RegionsUnexplored::registerChiseledBookshelves);

        ifModLoaded("snifferplus", SnifferPlus::registerChiseledBookshelves);

        ifModLoaded("techreborn", TechReborn::registerChiseledBookshelves);

        ifModLoaded("vinery", Vinery::registerChiseledBookshelves);

        ModRegistries.registerFuelandFlammable();
        ModCreativeTab.registerItemGroup();

        // Datagen Block - disable for client run
        //SnifferPlus.registerChiseledBookshelves();
        //Botania.registerChiseledBookshelves();


    }
}