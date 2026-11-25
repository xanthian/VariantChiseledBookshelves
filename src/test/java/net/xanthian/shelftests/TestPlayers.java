package net.xanthian.shelftests;

import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.TestContext;
import net.minecraft.world.GameMode;

import java.util.UUID;

/** Uses a normal server player with an in-memory connection and the actual Creative game mode. */
final class TestPlayers {
    private TestPlayers() {}

    static ServerPlayerEntity creative(TestContext context) {
        var world = context.getWorld();
        var server = world.getServer();
        var client = ConnectedClientData.createDefault(new GameProfile(UUID.randomUUID(), "shelf-test-player"), false);
        var player = new ServerPlayerEntity(server, world, client.gameProfile(), client.syncedOptions());
        var connection = new ClientConnection(NetworkSide.SERVERBOUND);
        new EmbeddedChannel(connection);
        server.getPlayerManager().onPlayerConnect(connection, player, client);
        player.changeGameMode(GameMode.CREATIVE);
        return player;
    }
}
