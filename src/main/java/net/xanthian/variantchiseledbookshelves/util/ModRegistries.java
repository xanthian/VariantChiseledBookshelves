package net.xanthian.variantchiseledbookshelves.util;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;

public class ModRegistries {

    public static void registerFuelandFlammable() {
        //registerFlammableBlocks(); // Not flammable
        registerFuel();
    }

    private static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        // Copy of Blocks.CHISELED_BOOKSHELF from net.minecraft.block.FireBlock.registerDefaultFlammables
        registry.add(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF, 30, 20);
        registry.add(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF, 30, 20);
    }

    private static void registerFuel() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        // Copy of Blocks.CHISELED_BOOKSHELF from net.minecraft.block.entity.AbstractFurnaceBlockEntity.createFuelTimeMap
        registry.add(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF, 300);
        registry.add(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF, 300);
    }
}