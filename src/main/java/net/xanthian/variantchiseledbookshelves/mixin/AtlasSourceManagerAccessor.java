package net.xanthian.variantchiseledbookshelves.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.client.texture.atlas.AtlasSourceManager;
import net.minecraft.client.texture.atlas.AtlasSourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes the client atlas-source type map so the client initializer can register
 * {@link net.xanthian.variantchiseledbookshelves.client.ShelfSpriteSource} before atlas JSON is decoded.
 * The generated block atlas references that registered type to build shelf textures during resource reloads.
 * This accessor is listed under the client section of the mixin configuration.
 */
@Mixin(AtlasSourceManager.class)
public interface AtlasSourceManagerAccessor {
    /** Returns the live type map, retaining vanilla and other mods' registered atlas sources. */
    @Accessor("SOURCE_TYPE_BY_ID")
    static BiMap<Identifier, AtlasSourceType> variantchiseledbookshelves$getSourceTypes() {
        // Mixin replaces this static accessor body with access to the private field.
        throw new AssertionError();
    }
}
