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

@Mixin(LifecycledResourceManagerImpl.class)
abstract class LifecycledResourceManagerImplMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static List<ResourcePack> variantchiseledbookshelves$addGeneratedPack(
            List<ResourcePack> packs, @Local(argsOnly = true) ResourceType type) {
        var variants = DynamicShelves.variants();
        if (variants.isEmpty()) {
            return packs;
        }
        List<ResourcePack> result = new ArrayList<>(packs);
        int insertion = 0;
        for (int index = 0; index < packs.size(); index++) {
            if (packs.get(index).getId().equals("vanilla")) {
                insertion = index + 1;
                break;
            }
        }
        result.add(insertion, new GeneratedShelfPack(type, variants));
        return result;
    }
}
