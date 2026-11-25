package net.xanthian.variantchiseledbookshelves;
import net.fabricmc.api.ModInitializer;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import net.xanthian.variantchiseledbookshelves.block.ShelfBehavior;
import net.xanthian.variantchiseledbookshelves.util.ModCreativeTab;
public final class Initialise implements ModInitializer {
 public static final String MOD_ID = "variantchiseledbookshelves";
 public void onInitialize() { DynamicShelves.register(); ShelfBehavior.register(); ModCreativeTab.registerItemGroup(); ShelfLineupCommand.register(); }
}