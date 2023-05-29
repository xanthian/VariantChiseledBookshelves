package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;

import java.util.concurrent.CompletableFuture;

import static net.xanthian.variantchiseledbookshelves.Initialise.MOD_ID;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

        private static final TagKey<Item> CHISELEDBOOKSHELVES = TagKey.of(Registries.ITEM.getKey(), new Identifier(MOD_ID,"chiseledbookshelves"));
        private static final TagKey<Item> NON_FLAMMABLE_WOOD = TagKey.of(Registries.ITEM.getKey(), new Identifier("minecraft:non_flammable_wood"));

        @Override
        protected void configure (RegistryWrapper.WrapperLookup arg){

            getOrCreateTagBuilder(CHISELEDBOOKSHELVES)
                    .add(Item.fromBlock(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF));

            getOrCreateTagBuilder(NON_FLAMMABLE_WOOD)
                    .add(Item.fromBlock(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF))
                    .add(Item.fromBlock(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF));

        }
    }