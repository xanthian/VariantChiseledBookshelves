package net.xanthian.variantchiseledbookshelves.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;

import java.util.ArrayList;
import java.util.Comparator;

public final class ModCreativeTab {
    private static final Identifier BACKGROUND = Identifier.ofVanilla(
            "textures/gui/container/creative_inventory/tab_variantchiseledbookshelves.png");

    private ModCreativeTab() {
    }

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Initialise.MOD_ID, "variantchiseledbookshelves"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.variantchiseledbookshelves"))
                        
                        
                        .icon(() -> Registries.ITEM.getOrEmpty(Identifier.of(Initialise.MOD_ID, "mangrove_chiseled_bookshelf"))
                                .orElse(Items.CHISELED_BOOKSHELF).getDefaultStack())
                        .entries((context, entries) -> {
                            var tables = new ArrayList<ItemStack>();
                            tables.add(Items.CHISELED_BOOKSHELF.getDefaultStack());
                            DynamicShelves.variants().forEach(variant -> tables.add(new ItemStack(variant.block())));
                            tables.sort(Comparator.comparing((ItemStack stack) -> stack.getName().getString(),
                                            String.CASE_INSENSITIVE_ORDER)
                                    .thenComparing(stack -> Registries.ITEM.getId(stack.getItem())));
                            tables.forEach(entries::add);
                        })
                        .build());
    }
}
