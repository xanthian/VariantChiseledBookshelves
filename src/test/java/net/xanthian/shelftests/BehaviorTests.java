package net.xanthian.shelftests;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.test.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;
import net.minecraft.world.event.listener.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import java.util.*;

public class BehaviorTests implements FabricGameTest {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void miningLootFuelAndPistons(TestContext context) {
        var world = context.getWorld();
        var pos = context.getAbsolutePos(new BlockPos(1, 5, 1));
        var silk = new ItemStack(Items.DIAMOND_AXE);
        silk.addEnchantment(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
        for (var variant : DynamicShelves.variants()) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setBlockState(pos, variant.block().getDefaultState());
            var state = world.getBlockState(pos);
            var entity = world.getBlockEntity(pos);
            context.assertTrue(new ItemStack(Items.DIAMOND_AXE).getMiningSpeedMultiplier(state) > new ItemStack(Items.DIAMOND_PICKAXE).getMiningSpeedMultiplier(state), "Axe mines faster: " + variant.id());
            context.assertTrue(!state.isToolRequired(), "Any tool can destroy the shelf");
            context.assertTrue(Block.getDroppedStacks(state, world, pos, entity, null, ItemStack.EMPTY).isEmpty(), "No shelf drop without a tool");
            context.assertTrue(Block.getDroppedStacks(state, world, pos, entity, null, new ItemStack(Items.DIAMOND_AXE)).isEmpty(), "No shelf drop without Silk Touch");
            var drops = Block.getDroppedStacks(state, world, pos, entity, null, silk);
            context.assertTrue(drops.size() == 1 && drops.getFirst().isOf(variant.block().asItem()), "Silk Touch drops the matching shelf");
            for (Direction direction : Direction.values()) context.assertTrue(!PistonBlock.isMovable(state, world, pos, direction, false, direction), "Pistons cannot push or pull a bookshelf");
            boolean fuel = !variant.planks().getPath().startsWith("fireproof") && !variant.planks().getPath().equals("crimson_planks")
                    && !variant.planks().getPath().equals("warped_planks") && !variant.planks().getPath().equals("odd_boards");
            Integer time = FuelRegistry.INSTANCE.get(variant.block().asItem());
            context.assertTrue(fuel ? Integer.valueOf(300).equals(time) : time == null || time == 0, "Fuel must be 300 ticks only for burnable source planks: " + variant.id() + " got " + time);
            if (!fuel && !variant.planks().getPath().equals("odd_boards")) context.assertTrue(!state.isBurnable(), "Fireproof wood remains lava-safe");
        }
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void inventorySignalsHopperEventsAndBookDrops(TestContext context) {
        var world = context.getWorld();
        var pos = context.getAbsolutePos(new BlockPos(1, 5, 1));
        int[] changes = {0};
        GameEventListener listener = new GameEventListener() {
            public PositionSource getPositionSource() { return new BlockPositionSource(pos); }
            public int getRange() { return 16; }
            public boolean listen(ServerWorld w, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d eventPos) {
                if (event.equals(GameEvent.BLOCK_CHANGE) && BlockPos.ofFloored(eventPos).equals(pos)) changes[0]++;
                return false;
            }
        };
        var dispatcher = world.getChunk(pos).getGameEventDispatcher(ChunkSectionPos.getSectionCoord(pos.getY()));
        dispatcher.addListener(listener);
        try {
            context.assertTrue(Vibrations.getFrequency(GameEvent.BLOCK_CHANGE) == 11, "Block change has vibration frequency 11");
            for (var variant : DynamicShelves.variants()) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos, variant.block().getDefaultState());
                var state = world.getBlockState(pos);
                var shelf = (ChiseledBookshelfBlockEntity) world.getBlockEntity(pos);
                context.assertTrue(state.getComparatorOutput(world, pos) == 0, "Untouched shelf outputs zero");
                for (int slot = 0; slot < 6; slot++) {
                    shelf.setStack(slot, new ItemStack(Items.BOOK));
                    context.assertTrue(world.getBlockState(pos).getComparatorOutput(world, pos) == slot + 1, "Comparator reports last inserted slot");
                    shelf.removeStack(slot, 1);
                    context.assertTrue(world.getBlockState(pos).getComparatorOutput(world, pos) == slot + 1, "Comparator remembers last removed slot");
                }
                world.setBlockState(pos.up(), Blocks.HOPPER.getDefaultState());
                var hopper = (HopperBlockEntity) world.getBlockEntity(pos.up());
                hopper.setStack(0, new ItemStack(Items.BOOK));
                int before = changes[0];
                HopperBlockEntity.serverTick(world, pos.up(), world.getBlockState(pos.up()), hopper);
                context.assertTrue(shelf.getStack(0).isOf(Items.BOOK), "Hopper inserts a book");
                context.assertTrue(changes[0] > before, "Hopper insertion emits a block_change event");
                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
                for (int slot = 1; slot < 6; slot++) shelf.setStack(slot, new ItemStack(Items.BOOK));
                var box = new Box(pos).expand(1);
                world.getEntitiesByClass(net.minecraft.entity.ItemEntity.class, box, e -> true).forEach(net.minecraft.entity.Entity::discard);
                world.breakBlock(pos, true);
                int books = world.getEntitiesByClass(net.minecraft.entity.ItemEntity.class, box, e -> e.getStack().isOf(Items.BOOK))
                        .stream().mapToInt(e -> e.getStack().getCount()).sum();
                context.assertTrue(books == 6, "Breaking drops all six contained books: " + variant.id());
            }
        } finally { dispatcher.removeListener(listener); }
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 400)
    public void observersDetectInsertionAndRemoval(TestContext context) {
        var world = context.getWorld();
        var pos = context.getAbsolutePos(new BlockPos(1, 2, 1));
        List<Block> blocks = new ArrayList<>();
        blocks.add(Blocks.CHISELED_BOOKSHELF);
        DynamicShelves.variants().forEach(v -> blocks.add(v.block()));
        boolean[] insertion = {false};
        boolean[] removal = {false};
        context.runAtEveryTick(() -> {
            long phase = context.getTick() % 24;
            if (!world.getBlockState(pos.south()).isOf(Blocks.OBSERVER)) return;
            if (world.getBlockState(pos.south()).get(ObserverBlock.POWERED)) {
                if (phase > 6 && phase < 14) insertion[0] = true;
                if (phase > 14) removal[0] = true;
            }
        });
        for (int index = 0; index < blocks.size(); index++) {
            Block shelf = blocks.get(index);
            long base = index * 24L;
            context.runAtTick(base, () -> {
                world.setBlockState(pos.south(), Blocks.AIR.getDefaultState());
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos, shelf.getDefaultState());
                world.setBlockState(pos.south(), Blocks.OBSERVER.getDefaultState().with(ObserverBlock.FACING, Direction.NORTH));
                insertion[0] = false;
                removal[0] = false;
            });
            context.runAtTick(base + 6, () -> ((ChiseledBookshelfBlockEntity) world.getBlockEntity(pos)).setStack(0, new ItemStack(Items.BOOK)));
            context.runAtTick(base + 13, () -> context.assertTrue(insertion[0], "Observer detects insertion for " + Registries.BLOCK.getId(shelf)));
            context.runAtTick(base + 14, () -> ((ChiseledBookshelfBlockEntity) world.getBlockEntity(pos)).removeStack(0, 1));
            context.runAtTick(base + 22, () -> context.assertTrue(removal[0], "Observer detects removal for " + Registries.BLOCK.getId(shelf)));
        }
        context.runAtTick(blocks.size() * 24L - 1, context::complete);
    }
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void powerOfBooksAdvancementRecognizesEveryWood(TestContext context) {
        var world = context.getWorld();
        var player = context.createMockCreativeServerPlayerInWorld();
        var advancement = world.getServer().getAdvancementLoader().get(net.minecraft.util.Identifier.ofVanilla("adventure/read_power_of_chiseled_bookshelf"));
        context.assertTrue(advancement != null, "The vanilla Power of Books advancement is loaded");
        var tracker = player.getAdvancementTracker();
        List<Block> shelves = new ArrayList<>();
        shelves.add(Blocks.CHISELED_BOOKSHELF);
        DynamicShelves.variants().forEach(v -> shelves.add(v.block()));
        var shelfPos = context.getAbsolutePos(new BlockPos(1, 2, 1));
        for (Block shelf : shelves) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                var comparatorPos = shelfPos.offset(direction.getOpposite());
                world.setBlockState(comparatorPos.down(), Blocks.STONE.getDefaultState());
                for (boolean shelfFirst : List.of(false, true)) {
                    advancement.value().criteria().keySet().forEach(criterion -> tracker.revokeCriterion(advancement, criterion));
                    world.setBlockState(shelfPos, Blocks.AIR.getDefaultState());
                    world.setBlockState(comparatorPos, Blocks.AIR.getDefaultState());
                    BlockPos placedPos = shelfFirst ? comparatorPos : shelfPos;
                    Block placedBlock = shelfFirst ? Blocks.COMPARATOR : shelf;
                    var comparatorState = Blocks.COMPARATOR.getDefaultState().with(ComparatorBlock.FACING, direction);
                    if (shelfFirst) world.setBlockState(shelfPos, shelf.getDefaultState());
                    else world.setBlockState(comparatorPos, comparatorState);
                    world.setBlockState(placedPos, shelfFirst ? comparatorState : shelf.getDefaultState());
                    context.assertTrue(world.getBlockState(comparatorPos).isOf(Blocks.COMPARATOR), "Comparator remains on its support block");
                    // Fire the same criterion that BlockItem placement calls after successful placement.
                    net.minecraft.advancement.criterion.Criteria.PLACED_BLOCK.trigger(player, placedPos, new ItemStack(placedBlock));
                    context.assertTrue(tracker.getProgress(advancement).isDone(), "Power of Books must complete for "
                            + Registries.BLOCK.getId(shelf) + ", facing " + direction + ", shelf first " + shelfFirst);
                    world.setBlockState(shelfPos, Blocks.AIR.getDefaultState());
                    world.setBlockState(comparatorPos, Blocks.AIR.getDefaultState());
                }
                advancement.value().criteria().keySet().forEach(criterion -> tracker.revokeCriterion(advancement, criterion));
                world.setBlockState(shelfPos, shelf.getDefaultState());
                world.setBlockState(comparatorPos, Blocks.COMPARATOR.getDefaultState().with(ComparatorBlock.FACING, direction.getOpposite()));
                net.minecraft.advancement.criterion.Criteria.PLACED_BLOCK.trigger(player, comparatorPos, new ItemStack(Items.COMPARATOR));
                context.assertTrue(!tracker.getProgress(advancement).isDone(), "A comparator facing away from the shelf must not complete the advancement");
                world.setBlockState(shelfPos, Blocks.AIR.getDefaultState());
                world.setBlockState(comparatorPos, Blocks.AIR.getDefaultState());
            }
        }
        advancement.value().criteria().keySet().forEach(criterion -> tracker.revokeCriterion(advancement, criterion));
        world.setBlockState(shelfPos, Blocks.OAK_PLANKS.getDefaultState());
        world.setBlockState(shelfPos.south(), Blocks.COMPARATOR.getDefaultState().with(ComparatorBlock.FACING, Direction.NORTH));
        net.minecraft.advancement.criterion.Criteria.PLACED_BLOCK.trigger(player, shelfPos.south(), new ItemStack(Items.COMPARATOR));
        context.assertTrue(!tracker.getProgress(advancement).isDone(), "Reading a different block must not complete Power of Books");
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void mixedWoodCraftingKeepsUniformVariants(TestContext context) {
        var world = context.getWorld();
        var recipes = world.getServer().getRecipeManager();
        var mixedId = net.xanthian.variantchiseledbookshelves.recipe.MixedWoodRecipe.ID;
        context.assertTrue(recipes.get(mixedId).isPresent(), "Mixed wood recipe is loaded");
        var oak = bookshelfGrid(Items.OAK_PLANKS, Items.OAK_SLAB);
        assertBookshelfCraft(context, oak, Items.CHISELED_BOOKSHELF, false);
        for (var variant : DynamicShelves.variants()) {
            Item planks = Registries.ITEM.get(variant.ingredient());
            Item slab = Registries.ITEM.get(variant.slab());
            assertBookshelfCraft(context, bookshelfGrid(planks, slab), variant.block().asItem(), false);
            assertBookshelfCraft(context, bookshelfGrid(planks, Items.OAK_SLAB), Items.CHISELED_BOOKSHELF, true);
            assertBookshelfCraft(context, bookshelfGrid(Items.OAK_PLANKS, slab), Items.CHISELED_BOOKSHELF, true);
            var mixedPlanks = new ArrayList<ItemStack>(oak.getStacks());
            mixedPlanks.set(0, new ItemStack(planks));
            assertBookshelfCraft(context, net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, mixedPlanks), Items.CHISELED_BOOKSHELF, true);
            var mixedSlabs = new ArrayList<ItemStack>(oak.getStacks());
            mixedSlabs.set(4, new ItemStack(slab));
            assertBookshelfCraft(context, net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, mixedSlabs), Items.CHISELED_BOOKSHELF, true);
        }
        var invalid = new ArrayList<ItemStack>(oak.getStacks());
        invalid.set(3, new ItemStack(Items.STONE_SLAB));
        context.assertTrue(recipes.getAllMatches(net.minecraft.recipe.RecipeType.CRAFTING, net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, invalid), world).isEmpty(), "Stone slabs cannot craft a bookshelf");
        invalid = new ArrayList<ItemStack>(oak.getStacks());
        invalid.set(4, ItemStack.EMPTY);
        context.assertTrue(recipes.getAllMatches(net.minecraft.recipe.RecipeType.CRAFTING, net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, invalid), world).isEmpty(), "Incomplete pattern cannot craft a bookshelf");
        invalid = new ArrayList<ItemStack>(oak.getStacks());
        java.util.Collections.swap(invalid, 0, 3);
        context.assertTrue(recipes.getAllMatches(net.minecraft.recipe.RecipeType.CRAFTING, net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, invalid), world).isEmpty(), "Planks belong in top and bottom rows, slabs in the middle");
        context.complete();
    }

    private static net.minecraft.recipe.input.CraftingRecipeInput bookshelfGrid(Item planks, Item slab) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int slot = 0; slot < 9; slot++) stacks.add(new ItemStack(slot >= 3 && slot <= 5 ? slab : planks));
        return net.minecraft.recipe.input.CraftingRecipeInput.create(3, 3, stacks);
    }

    private static void assertBookshelfCraft(TestContext context, net.minecraft.recipe.input.CraftingRecipeInput input, Item expected, boolean mixed) {
        var world = context.getWorld();
        var matches = world.getServer().getRecipeManager().getAllMatches(net.minecraft.recipe.RecipeType.CRAFTING, input, world);
        context.assertTrue(matches.size() == 1, "Exactly one recipe must match, avoiding recipe ordering conflicts; found " + matches.size());
        var recipe = matches.getFirst();
        var output = recipe.value().craft(input, world.getRegistryManager());
        context.assertTrue(output.isOf(expected) && output.getCount() == 1, "Recipe must produce one " + Registries.ITEM.getId(expected));
        context.assertTrue(recipe.id().equals(net.xanthian.variantchiseledbookshelves.recipe.MixedWoodRecipe.ID) == mixed,
                "Matching wood keeps its normal recipe; mixed wood uses the vanilla fallback");
    }

}
