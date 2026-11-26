package net.xanthian.variantchiseledbookshelves;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** A straight line of front-facing shelf, source plank, and sideways-facing shelf displays. */
public final class ShelfLineupCommand {
    private static final SimpleCommandExceptionType CREATIVE_REQUIRED = new SimpleCommandExceptionType(
            Text.literal("Only players in Creative mode can place a bookshelf layout."));

    private record Column(Block shelf, Block plank) {}

    private ShelfLineupCommand() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registries, environment) ->
                dispatcher.register(CommandManager.literal(Initialise.MOD_ID)
                        .requires(ShelfLineupCommand::canUse)
                        .then(CommandManager.literal("lineup").executes(context -> place(context.getSource())))
                        .then(CommandManager.literal("layout").executes(context -> place(context.getSource())))));
    }

    private static boolean canUse(ServerCommandSource source) {
        return source.getEntity() instanceof ServerPlayerEntity player && player.isCreative();
    }

    private static int place(ServerCommandSource source) throws CommandSyntaxException {
        if (!canUse(source)) throw CREATIVE_REQUIRED.create();
        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
        ServerWorld world = source.getWorld();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column(Blocks.CHISELED_BOOKSHELF, Blocks.OAK_PLANKS));
        for (var variant : DynamicShelves.variants()) {
            columns.add(new Column(variant.block(), Registries.BLOCK.get(variant.planks())));
        }
        columns.sort(Comparator.comparing(column -> column.shelf() == Blocks.CHISELED_BOOKSHELF
                ? "oak_chiseled_bookshelf" : Registries.BLOCK.getId(column.shelf()).toString()));

        Direction forward = player.getHorizontalFacing();
        Direction right = forward.rotateYCounterclockwise();
        BlockPos start = BlockPos.ofFloored(source.getPosition()).offset(forward, 2);
        int placed = 0;
        for (int index = 0; index < columns.size(); index++) {
            BlockPos pos = start.offset(right, index);
            if (!world.isInBuildLimit(pos.up(2)) || !world.getWorldBorder().contains(pos)) continue;
            world.getChunk(pos);
            if (!isClear(world, player, pos) || !isClear(world, player, pos.up()) || !isClear(world, player, pos.up(2))) continue;
            Column column = columns.get(index);
            world.setBlockState(pos, column.shelf().getDefaultState().with(Properties.HORIZONTAL_FACING, forward.getOpposite()), Block.NOTIFY_ALL);
            world.setBlockState(pos.up(), column.plank().getDefaultState(), Block.NOTIFY_ALL);
            world.setBlockState(pos.up(2), column.shelf().getDefaultState().with(Properties.HORIZONTAL_FACING, right), Block.NOTIFY_ALL);
            placed++;
        }
        int count = placed;
        source.sendFeedback(() -> Text.literal("Placed " + count + " bookshelf displays. Skipped "
                + (columns.size() - count) + " blocked or protected spaces."), false);
        return placed;
    }

    private static boolean isClear(ServerWorld world, ServerPlayerEntity player, BlockPos pos) {
        return world.isInBuildLimit(pos) && world.getWorldBorder().contains(pos) && world.getChunkManager().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)
                && world.canPlayerModifyAt(player, pos) && world.isAir(pos);
    }
}
