package net.xanthian.variantchiseledbookshelves;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.ChiseledBookshelves;
import net.xanthian.variantchiseledbookshelves.util.ModRegistries;

public class Initialise implements ModInitializer {

    public static final String MOD_ID = "variantchiseledbookshelves";

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP,
            new Identifier(MOD_ID, "variantchiseledbookshelves"));

    @Override
    public void onInitialize() {
        // Custom Item Group
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("variantchiseledbookshleves.group.variantchiseledbookshelves"))
                .icon(() -> new ItemStack(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF))
                .entries((context, entries) -> {
                    entries.add(ChiseledBookshelves.ACACIA_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.BAMBOO_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.BIRCH_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.CHERRY_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.CRIMSON_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.DARK_OAK_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.JUNGLE_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.MANGROVE_CHISELED_BOOKSHELF);
                    entries.add(Blocks.CHISELED_BOOKSHELF); // Oak
                    entries.add(ChiseledBookshelves.SPRUCE_CHISELED_BOOKSHELF);
                    entries.add(ChiseledBookshelves.WARPED_CHISELED_BOOKSHELF);
                })
                .build());

        //Bookshelf Registration
        ChiseledBookshelves.registerVanillaChiseledBookshelves();

        ModRegistries.registerFuelandFlammable();
    }
}