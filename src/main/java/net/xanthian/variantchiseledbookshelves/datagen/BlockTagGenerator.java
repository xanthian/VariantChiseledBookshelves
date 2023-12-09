package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.xanthian.variantchiseledbookshelves.Initialise.MOD_ID;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    private static final TagKey<Block> CHISELED_BOOKSHELVES = TagKey.of(Registries.BLOCK.getKey(), new Identifier(MOD_ID, "chiseled_bookshelves"));

    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        registerTags(Vanilla.VANILLA_CHISELED_BOOKSHELVES);
        registerTags(AdAstra.AA_CHISELED_BOOKSHELVES);
        registerTags(BeachParty.LDBP_CHISELED_BOOKSHELVES);
        registerTags(BetterArcheology.BA_CHISELED_BOOKSHELVES);
        registerTags(Bewitchment.BW_CHISELED_BOOKSHELVES);
        registerTags(BiomeMakeover.BM_CHISELED_BOOKSHELVES);
        registerTags(Blockus.BLS_CHISELED_BOOKSHELVES);
        registerTags(Botania.BOT_CHISELED_BOOKSHELVES);
        registerTags(Cinderscapes.CS_CHISELED_BOOKSHELVES);
        registerTags(DeeperAndDarker.DAD_CHISELED_BOOKSHELVES);
        registerTags(Desolation.DS_CHISELED_BOOKSHELVES);
        registerTags(EldritchEnd.EE_CHISELED_BOOKSHELVES);
        registerTags(MineCells.MC_CHISELED_BOOKSHELVES);
        registerTags(NaturesSpirit.NS_CHISELED_BOOKSHELVES);
        registerTags(Promenade.PROM_CHISELED_BOOKSHELVES);
        registerTags(RegionsUnexplored.RU_CHISELED_BOOKSHELVES);
        registerTags(SnifferPlus.SP_CHISELED_BOOKSHELVES);
        registerTags(TechReborn.TR_CHISELED_BOOKSHELVES);
        registerTags(Vinery.LDV_CHISELED_BOOKSHELVES);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .addTag(CHISELED_BOOKSHELVES);
    }

    private void registerTags(Map<Identifier, Block> blockMap) {
        for (Block block : blockMap.values()) {
            Identifier lootTableId = block.getLootTableId();
            String newPath = lootTableId.getPath().replaceFirst("blocks/", "");
            Identifier modifiedId = new Identifier(lootTableId.getNamespace(), newPath);

            getOrCreateTagBuilder(CHISELED_BOOKSHELVES)
                    .addOptional(modifiedId);
        }
    }
}