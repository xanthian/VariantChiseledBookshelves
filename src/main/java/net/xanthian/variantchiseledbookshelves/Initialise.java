package net.xanthian.variantchiseledbookshelves;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
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

    public static boolean isModVersion(String modId, String ver) {
        return FabricLoader.getInstance()
                .getModContainer(modId)
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getVersion)
                .map(Version::getFriendlyString)
                .filter(version -> version.startsWith(ver))
                .isPresent();
    }

    @Override
    public void onInitialize() {

        Vanilla.registerVanillaChiseledBookshelves();

        ifModLoaded("ad_astra", AdAstra::registerChiseledBookshelves);

        ifModLoaded("beachparty", BeachParty::registerChiseledBookshelves);

        ifModLoaded("betterarcheology", BetterArcheology::registerChiseledBookshelves);

        ifModLoaded("bewitchment", Bewitchment::registerChiseledBookshelves);

        ifModLoaded("deeperdarker", DeeperAndDarker::registerChiseledBookshelves);

        ifModLoaded("eldritch_end", EldritchEnd::registerChiseledBookshelves);

        ifModLoaded("minecells", MineCells::registerChiseledBookshelves);

        ifModLoaded("natures_spirit", NaturesSpirit::registerChiseledBookshelves);

        ifModLoaded("promenade", Promenade::registerChiseledBookshelves);

        ifModLoaded("regions_unexplored", () -> {
            RegionsUnexplored.registerChiseledBookshelves();
            if (isModVersion("regions_unexplored", "0.4")) {
                RegionsUnexplored.register04ChiseledBookshelves();
            } else {
                RegionsUnexplored.register05ChiseledBookshelves();
            }
        });

        ifModLoaded("snifferplus", SnifferPlus::registerChiseledBookshelves);

        ifModLoaded("techreborn", TechReborn::registerChiseledBookshelves);

        ifModLoaded("vinery", Vinery::registerChiseledBookshelves);

        ModRegistries.registerFuelandFlammable();
        ModCreativeTab.registerItemGroup();

        // Datagen Block - disable for client run
        //SnifferPlus.registerChiseledBookshelves();
        //RegionsUnexplored.register04ChiseledBookshelves();
        //NaturesSpirit.registerChiseledBookshelves();
        //DeeperAndDarker.registerChiseledBookshelves();
        //AdAstra.registerChiseledBookshelves();

    }
}