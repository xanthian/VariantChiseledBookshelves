package net.xanthian.variantchiseledbookshelves.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

/**
 * Allows dynamically registered shelves to use the vanilla chiseled bookshelf block entity type.
 * During registration, DynamicShelves copies that type's supported-block set, adds the new variant,
 * and replaces the set. Existing supported blocks are retained, including entries added by other mods.
 * This runs on both the client and dedicated server so block entity validation agrees on both sides.
 */
@Mixin(BlockEntityType.class)
public interface BlockEntityTypeAccessor {
    /** Reads the supported blocks so registration can preserve all existing entries. */
    @Accessor("blocks")
    Set<Block> variantchiseledbookshelves$getBlocks();

    /**
     * Replaces the supported-block set with the extended copy prepared by DynamicShelves.
     * Mutable permits writing the normally final field; replacing the set also supports immutable sets.
     *
     * @param blocks the complete supported-block set, including the newly registered variant
     */
    @Mutable
    @Accessor("blocks")
    void variantchiseledbookshelves$setBlocks(Set<Block> blocks);
}
