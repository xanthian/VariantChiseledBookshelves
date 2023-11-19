package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;
import net.xanthian.variantchiseledbookshelves.util.ModItemTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {

    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        registerTags(Vanilla.VANILLA_CHISELED_BOOKSHELVES);
        registerTags(AdAstra.AA_CHISELED_BOOKSHELVES);
        registerTags(BeachParty.LDBP_CHISELED_BOOKSHELVES);
        registerTags(BetterArcheology.BA_CHISELED_BOOKSHELVES);
        registerTags(Bewitchment.BW_CHISELED_BOOKSHELVES);
        registerTags(DeeperAndDarker.DAD_CHISELED_BOOKSHELVES);
        registerTags(EldritchEnd.EE_CHISELED_BOOKSHELVES);
        registerTags(MineCells.MC_CHISELED_BOOKSHELVES);
        registerTags(NaturesSpirit.NS_CHISELED_BOOKSHELVES);
        registerTags(Promenade.PROM_CHISELED_BOOKSHELVES);
        registerTags(RegionsUnexplored.RU_CHISELED_BOOKSHELVES);
        registerTags(SnifferPlus.SP_CHISELED_BOOKSHELVES);
        registerTags(TechReborn.TR_CHISELED_BOOKSHELVES);
        registerTags(Vinery.LDV_CHISELED_BOOKSHELVES);

        getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD)
                .add(Item.fromBlock(Vanilla.CRIMSON_CHISELED_BOOKSHELF))
                .add(Item.fromBlock(Vanilla.WARPED_CHISELED_BOOKSHELF))
                .addOptional(new Identifier("variantchiseledbookshelves:prom_dark_amaranth_chiseled_bookshelf"))
                .addOptional(new Identifier("variantchiseledbookshelves:ru_brimwood_chiseled_bookshelf"))
                .addOptional(new Identifier("variantchiseledbookshelves:ru_cobalt_chiseled_bookshelf"))
                .addOptional(new Identifier("variantchiseledbookshelves:ru_dead_chiseled_bookshelf"))
                .addOptional(new Identifier("variantchiseledbookshelves:ru_yellow_bioshroom_chiseled_bookshelf"));

    }

    private void registerTags(Map<Identifier, Block> blockMap) {
        for (Block block : blockMap.values()) {
            Identifier lootTableId = block.getLootTableId();
            String newPath = lootTableId.getPath().replaceFirst("blocks/", "");
            Identifier modifiedId = new Identifier(lootTableId.getNamespace(), newPath);

            getOrCreateTagBuilder(ModItemTags.CHISELED_BOOKSHELVES)
                    .addOptional(modifiedId);
        }
    }
}