package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;

import java.util.concurrent.CompletableFuture;

import static net.xanthian.variantchiseledbookshelves.Initialise.MOD_ID;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final TagKey<Block> CHISELEDBOOKSHELVES = TagKey.of(Registries.BLOCK.getKey(), new Identifier(MOD_ID,"chiseledbookshelves"));
    private static final TagKey<Block> MINEABLE_AXE = TagKey.of(Registries.BLOCK.getKey(), new Identifier("minecraft:mineable/axe"));

    @Override
    protected void configure (RegistryWrapper.WrapperLookup arg){

        getOrCreateTagBuilder(CHISELEDBOOKSHELVES)
                .add(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF)
                .add(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF);

        getOrCreateTagBuilder(MINEABLE_AXE)
                .addTag(CHISELEDBOOKSHELVES);

    }
}
