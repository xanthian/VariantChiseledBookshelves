package net.xanthian.variantchiseledbookshelves.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.client.texture.atlas.AtlasSourceManager;
import net.minecraft.client.texture.atlas.AtlasSourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AtlasSourceManager.class)
public interface AtlasSourceManagerAccessor {
    @Accessor("SOURCE_TYPE_BY_ID")
    static BiMap<Identifier, AtlasSourceType> variantchiseledbookshelves$getSourceTypes() {
        throw new AssertionError();
    }
}
