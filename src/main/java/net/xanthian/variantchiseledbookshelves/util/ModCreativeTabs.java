package net.xanthian.variantchiseledbookshelves.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;


public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Initialise.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VCB_TAB = CREATIVE_MODE_TABS.register("variantvanillablocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Vanilla.MANGROVE_CHISELED_BOOKSHELF.get()))
                    .title(Component.literal("Variant Chiseled Bookshelves"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Vanilla.ACACIA_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.BAMBOO_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.BIRCH_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.CHERRY_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.CRIMSON_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.DARK_OAK_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.JUNGLE_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.MANGROVE_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Blocks.CHISELED_BOOKSHELF); // Oak
                        pOutput.accept(Vanilla.SPRUCE_CHISELED_BOOKSHELF.get());
                        pOutput.accept(Vanilla.WARPED_CHISELED_BOOKSHELF.get());
                    })
                    .build());
}