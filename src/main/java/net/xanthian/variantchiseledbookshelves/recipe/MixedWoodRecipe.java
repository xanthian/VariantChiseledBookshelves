package net.xanthian.variantchiseledbookshelves.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;

/** Mixed wood uses the vanilla output; uniform matching ingredients use their ordinary recipe. */
public final class MixedWoodRecipe extends SpecialCraftingRecipe {
    public static final Identifier ID = Identifier.of(Initialise.MOD_ID, "mixed_wood_chiseled_bookshelf");
    public static final Identifier PLANKS_ID = Identifier.of(Initialise.MOD_ID, "bookshelf_planks");
    public static final Identifier SLABS_ID = Identifier.of(Initialise.MOD_ID, "bookshelf_slabs");
    private static final TagKey<Item> PLANKS = TagKey.of(RegistryKeys.ITEM, PLANKS_ID);
    private static final TagKey<Item> SLABS = TagKey.of(RegistryKeys.ITEM, SLABS_ID);
    private static final RecipeSerializer<MixedWoodRecipe> SERIALIZER = new SpecialRecipeSerializer<>(MixedWoodRecipe::new);

    private MixedWoodRecipe(CraftingRecipeCategory category) { super(category); }

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, ID, SERIALIZER);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.getWidth() != 3 || input.getHeight() != 3 || input.getStackCount() != 9) return false;
        Item planks = input.getStackInSlot(0).getItem();
        Item slab = input.getStackInSlot(3).getItem();
        boolean mixed = false;
        for (int slot = 0; slot < 9; slot++) {
            boolean slabSlot = slot >= 3 && slot <= 5;
            ItemStack stack = input.getStackInSlot(slot);
            if (!stack.isIn(slabSlot ? SLABS : PLANKS)) return false;
            mixed |= !stack.isOf(slabSlot ? slab : planks);
        }
        if (mixed) return true;
        if (planks == Items.OAK_PLANKS && slab == Items.OAK_SLAB) return false;
        // Uniform planks with another wood's slabs also count as mixed wood.
        return DynamicShelves.variants().stream().noneMatch(variant ->
                Registries.ITEM.get(variant.ingredient()) == planks && Registries.ITEM.get(variant.slab()) == slab);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) { return getResult(registries); }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registries) { return Items.CHISELED_BOOKSHELF.getDefaultStack(); }

    @Override
    public boolean fits(int width, int height) { return width >= 3 && height >= 3; }

    @Override
    public RecipeSerializer<?> getSerializer() { return SERIALIZER; }
}
