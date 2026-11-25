package net.xanthian.variantchiseledbookshelves.client;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import net.xanthian.variantchiseledbookshelves.block.VariantShelfBlock;
import net.xanthian.variantchiseledbookshelves.resource.GeneratedShelfPack;

import java.util.IdentityHashMap;
import java.util.Map;

/** Identifies the plank provider, rather than the mod owning the generated shelf's registry ID. */
public final class SourceModTooltip {
    private SourceModTooltip() {
    }

    public static void register() {
        // Resolve lazily: the client entrypoint runs before the shelves are registered.
        Map<Item, Text> sourceNames = new IdentityHashMap<>();
        ItemTooltipCallback.EVENT.register((stack, context, flags, lines) -> {
            Item item = stack.getItem();
            if (item != Items.CHISELED_BOOKSHELF
                    && !(item instanceof BlockItem blockItem && blockItem.getBlock() instanceof VariantShelfBlock)) {
                return;
            }
            Text sourceName = sourceNames.computeIfAbsent(item, key -> {
                String namespace = key == Items.CHISELED_BOOKSHELF ? "minecraft" : DynamicShelves.variants().stream()
                        .filter(variant -> variant.block().asItem() == key)
                        .map(variant -> variant.planks().getNamespace()).findFirst().orElse(null);
                if (namespace == null) {
                    return null;
                }
                String name = FabricLoader.getInstance().getModContainer(namespace)
                        .map(mod -> mod.getMetadata().getName()).orElse(namespace);
                return Text.literal(name).formatted(Formatting.GRAY, Formatting.ITALIC)
                        .styled(style -> style.withFont(GeneratedShelfPack.SOURCE_MOD_FONT_ID));
            });
            if (sourceName != null) {
                lines.add(Math.min(1, lines.size()), sourceName.copy());
            }
        });
    }
}
