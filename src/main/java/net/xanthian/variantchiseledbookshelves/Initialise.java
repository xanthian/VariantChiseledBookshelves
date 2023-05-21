package net.xanthian.variantchiseledbookshelves;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.bookshelves.ChiseledBookshelves;

import java.util.Comparator;

public class Initialise implements ModInitializer {

    public static final String MOD_ID = "variantchiseledbookshelves";

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP,
            new Identifier(MOD_ID, "variantchiseledbookshelves"));

    @Override
    public void onInitialize() {
        // Custom Item Group
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.literal("Variant Chiseled Bookshelves"))
                .icon(() -> new ItemStack(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF))
                .entries((context, entries) -> {
                    entries.addAll(Registries.ITEM.getIds().stream()
                            .filter(identifier -> identifier.getNamespace().matches(MOD_ID))
                            .sorted(Comparator.comparing(Identifier::getPath))
                            .map(Registries.ITEM::get)
                            .map(ItemStack::new)
                            .filter(input -> !input.isEmpty())
                            .toList());
                })
                .build());

        //Bookshelf Registration
        ChiseledBookshelves.registerVanillaChiseledBookshelves();
    }
}