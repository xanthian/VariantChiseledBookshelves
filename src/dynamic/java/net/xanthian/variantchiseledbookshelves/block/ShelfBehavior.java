package net.xanthian.variantchiseledbookshelves.block;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;

import java.util.IdentityHashMap;
import java.util.Map;

/** Refreshes fire and furnace behavior after vanilla and Fabric have applied tags. */
public final class ShelfBehavior {
    private static final FlammableBlockRegistry FIRE = FlammableBlockRegistry.getDefaultInstance();
    private static final FuelRegistry FUEL = FuelRegistry.INSTANCE;

    private ShelfBehavior() {
    }

    public static void register() {
        Identifier phase = Identifier.of(Initialise.MOD_ID, "inherit_plank_behavior");
        CommonLifecycleEvents.TAGS_LOADED.addPhaseOrdering(Event.DEFAULT_PHASE, phase);
        CommonLifecycleEvents.TAGS_LOADED.register(phase, (registries, client) -> refresh());
    }

    private static void refresh() {
        Map<Item, Integer> fuelTimes = new IdentityHashMap<>();
        for (var variant : DynamicShelves.variants()) {
            var plankBlock = Registries.BLOCK.get(variant.planks());
            try {
                FlammableBlockRegistry.Entry entry = FIRE.get(plankBlock);
                if (entry == null) {
                    FIRE.remove(variant.block());
                } else {
                    FIRE.add(variant.block(), entry);
                }
            } catch (RuntimeException exception) {
                LogUtils.getLogger().warn("Could not inherit fire behavior from {}", variant.planks(), exception);
                FIRE.remove(variant.block());
            }
            fuelTimes.put(variant.block().asItem(), fuelTime(variant));
        }
        // Writes invalidate Fabric's fuel cache, so read all of the source times first.
        fuelTimes.forEach((item, time) -> {
            if (time == null || time <= 0) {
                FUEL.remove(item);
            } else {
                FUEL.add(item, time);
            }
        });
    }

    private static Integer fuelTime(DynamicShelves.Variant variant) {
        try {
            Item source = Registries.ITEM.get(variant.ingredient());
            if (!variant.nonFlammable() && !Registries.ITEM.getEntry(source).isIn(ItemTags.NON_FLAMMABLE_WOOD)) {
                return FUEL.get(source);
            }
        } catch (RuntimeException exception) {
            LogUtils.getLogger().warn("Could not inherit fuel behavior from {}", variant.ingredient(), exception);
        }
        return null;
    }
}
