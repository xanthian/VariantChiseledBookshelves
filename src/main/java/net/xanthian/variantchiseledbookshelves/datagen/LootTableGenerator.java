package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;

import java.util.Map;

public class LootTableGenerator extends FabricBlockLootTableProvider {

    public LootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        for (Block block : Vanilla.VANILLA_CHISELED_BOOKSHELVES.values()) {
            addDropWithSilkTouch(block);
        }
        registerLootTables(AdAstra.AA_CHISELED_BOOKSHELVES, "ad_astra");
        registerLootTables(BeachParty.LDBP_CHISELED_BOOKSHELVES, "beachparty");
        registerLootTables(BetterArcheology.BA_CHISELED_BOOKSHELVES, "betterarcheology");
        registerLootTables(Bewitchment.BW_CHISELED_BOOKSHELVES, "bewitchment");
        registerLootTables(BiomeMakeover.BM_CHISELED_BOOKSHELVES, "biomemakeover");
        registerLootTables(Blockus.BLS_CHISELED_BOOKSHELVES, "blockus");
        registerLootTables(Botania.BOT_CHISELED_BOOKSHELVES, "botania");
        registerLootTables(Cinderscapes.CS_CHISELED_BOOKSHELVES, "cinderscapes");
        registerLootTables(DeeperAndDarker.DAD_CHISELED_BOOKSHELVES, "deeperdarker");
        registerLootTables(Desolation.DS_CHISELED_BOOKSHELVES, "desolation");
        registerLootTables(EldritchEnd.EE_CHISELED_BOOKSHELVES, "eldritch_end");
        registerLootTables(MineCells.MC_CHISELED_BOOKSHELVES, "minecells");
        registerLootTables(NaturesSpirit.NS_CHISELED_BOOKSHELVES, "natures_spirit");
        registerLootTables(Promenade.PROM_CHISELED_BOOKSHELVES, "promenade");
        registerLootTables(RegionsUnexplored.RU_CHISELED_BOOKSHELVES, "regions_unexplored");
        registerLootTables(SnifferPlus.SP_CHISELED_BOOKSHELVES, "snifferplus");
        registerLootTables(TechReborn.TR_CHISELED_BOOKSHELVES, "techreborn");
        registerLootTables(Vinery.LDV_CHISELED_BOOKSHELVES, "vinery");
    }

    private void registerLootTables(Map<Identifier, Block> blockMap, String modId) {
        for (Block block : blockMap.values()) {
            withConditions(DefaultResourceConditions.allModsLoaded(modId)).addDropWithSilkTouch(block);
        }
    }
}