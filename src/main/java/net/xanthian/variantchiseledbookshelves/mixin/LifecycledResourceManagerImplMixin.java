package net.xanthian.variantchiseledbookshelves.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import net.xanthian.variantchiseledbookshelves.resource.GeneratedShelfPack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Inserts the in-memory shelf pack into each newly constructed resource manager.
 * Client managers receive generated models, blockstates, language entries and atlas definitions;
 * server-data managers receive recipes, loot tables, tags and the extended Power of Books advancement.
 * A fresh pack is built from the registered variant catalogue for each manager, including reloads.
 */
@Mixin(LifecycledResourceManagerImpl.class)
abstract class LifecycledResourceManagerImplMixin {
    /**
     * Replaces only the constructor's pack-list argument, before the manager processes its packs.
     * The captured ResourceType argument selects client assets or server data for the generated pack.
     */
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static List<ResourcePack> variantchiseledbookshelves$addGeneratedPack(
            List<ResourcePack> packs, @Local(argsOnly = true) ResourceType type) {
        var variants = DynamicShelves.variants();
        if (variants.isEmpty()) {
            return packs;
        }
        // Copy the supplied list because callers may pass an immutable or shared list.
        List<ResourcePack> result = new ArrayList<>(packs);
        // Packs are ordered from low to high priority. If vanilla is absent, use the lowest priority.
        int insertion = 0;
        for (int index = 0; index < packs.size(); index++) {
            if (packs.get(index).getId().equals("vanilla")) {
                insertion = index + 1;
                break;
            }
        }
        // Generated defaults override vanilla, while later mod and user packs can still override them.
        result.add(insertion, new GeneratedShelfPack(type, variants));
        return result;
    }
}
