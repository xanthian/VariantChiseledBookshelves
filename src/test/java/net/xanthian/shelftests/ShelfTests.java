package net.xanthian.shelftests;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.resource.ResourceType;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import net.xanthian.variantchiseledbookshelves.resource.GeneratedShelfPack;
import net.xanthian.variantchiseledbookshelves.client.ShelfTexture;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.awt.image.BufferedImage;

public class ShelfTests implements ModInitializer, FabricGameTest {
    public void onInitialize() {
        add("fixture_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
        add("missing_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
        add("odd_boards", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
        add("half_board", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));
        add("fireproof_planks", new Block(AbstractBlock.Settings.create().strength(1.5f)));
        add("fireproof_slab", new SlabBlock(AbstractBlock.Settings.create().strength(1.5f)));
        net.fabricmc.fabric.api.registry.FuelRegistry.INSTANCE.add(Registries.ITEM.get(Identifier.of("shelf_tests", "fixture_planks")), 100);
        net.fabricmc.fabric.api.registry.FuelRegistry.INSTANCE.add(Registries.ITEM.get(Identifier.of("shelf_tests", "fireproof_planks")), 300);
        add("fixture_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));
    }
    private static void add(String name, Block block) {
        Identifier id = Identifier.of("shelf_tests", name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
    }
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void discoveryResourcesAndInventory(TestContext context) throws Exception {
        var tab = Registries.ITEM_GROUP.get(Identifier.of("variantchiseledbookshelves", "variantchiseledbookshelves"));
        context.assertTrue(tab.getTexture().equals(Identifier.ofVanilla("textures/gui/container/creative_inventory/tab_variantchiseledbookshelves.png")), "Creative tab uses its original artwork");
        context.assertTrue(!tab.shouldRenderName(), "Artwork supplies the creative tab heading");
        var variants = DynamicShelves.variants();
        context.assertTrue(variants.size() == 13, "Ten vanilla woods and three fixtures must register");
        context.assertTrue(variants.stream().noneMatch(v -> v.planks().getPath().equals("missing_planks")), "Plank without a slab must be skipped");
        var fixture = variants.stream().filter(v -> v.planks().getPath().equals("fixture_planks")).findFirst().orElseThrow();
        context.assertTrue(fixture.slab().equals(Identifier.of("shelf_tests", "fixture_slab")), "Use the matching slab");
        context.assertTrue(variants.stream().anyMatch(v -> v.planks().getPath().equals("odd_boards") && v.slab().getPath().equals("half_board")), "Nested tags and slab recipes support unconventional names");
        var assets = new GeneratedShelfPack(ResourceType.CLIENT_RESOURCES, variants);
        var data = new GeneratedShelfPack(ResourceType.SERVER_DATA, variants);
        for (var variant : variants) {
            context.assertTrue(BlockEntityType.CHISELED_BOOKSHELF.supports(variant.block().getDefaultState()), "Vanilla block entity must support variant");
            try (var in = assets.open(ResourceType.CLIENT_RESOURCES, variant.id().withPrefixedPath("blockstates/").withSuffixedPath(".json")).get()) {
                var json = JsonParser.parseReader(new InputStreamReader(in)).getAsJsonObject();
                context.assertTrue(json.getAsJsonArray("multipart").size() == 52, "All facings and book slot states must have models");
            }
            context.assertTrue(context.getWorld().getServer().getRecipeManager().get(variant.id()).isPresent(), "Recipe must load: " + variant.id());
            context.assertTrue(data.open(ResourceType.SERVER_DATA, variant.id().withPrefixedPath("loot_table/blocks/").withSuffixedPath(".json")) != null, "Self drop must exist");
            BlockPos pos = new BlockPos(1, 1, 1);
            context.setBlockState(pos, variant.block());
            var entity = (ChiseledBookshelfBlockEntity) context.getBlockEntity(pos);
            context.assertTrue(entity != null && entity.size() == 6, "Six inventory slots must exist");
            entity.setStack(0, new ItemStack(Items.BOOK));
            context.assertTrue(entity.getStack(0).isOf(Items.BOOK), "Books must be stored");
            context.assertTrue(context.getBlockState(pos).get(ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.getFirst()), "Inserting book updates occupied state");
        }
        context.complete();
    }
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void animatedTexturesPreserveBooks(TestContext context) {
        var empty = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        var occupied = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        var plank = new BufferedImage(32,64,BufferedImage.TYPE_INT_ARGB);
        for(int y=0;y<16;y++) for(int x=0;x<16;x++) { empty.setRGB(x,y,0xff987643); occupied.setRGB(x,y,0xff987643); }
        occupied.setRGB(2,2,0xff1256ab);
        for(int y=0;y<64;y++) for(int x=0;x<32;x++) plank.setRGB(x,y,y<32?0xffaabbcc:0xff998877);
        var result = ShelfTexture.compose(occupied,empty,plank,32,32);
        context.assertTrue(result.getWidth()==32 && result.getHeight()==64,"Animation dimensions retained");
        context.assertTrue(result.getRGB(4,4)==0xff1256ab && result.getRGB(4,36)==0xff1256ab,"Book pixels survive each frame");
        context.assertTrue(result.getRGB(0,0)!=result.getRGB(0,32),"Wood follows animated source");
        boolean rejected=false;
        try { ShelfTexture.compose(occupied,empty,plank,17,32); } catch(IllegalArgumentException expected) { rejected=true; }
        context.assertTrue(rejected,"Invalid frame dimensions rejected");
        context.complete();
    }
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void vanillaAssetsAndSlotTemplates(TestContext context) throws Exception {
        try (var zip = new java.util.zip.ZipFile(System.getProperty("shelf.assetJar"))) {
            String prefix = "assets/minecraft/";
            String[] faces = {"top", "side", "empty", "occupied"};
            for (String face : faces) {
                var template = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/chiseled_bookshelf_" + face + ".png")));
                var empty = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/chiseled_bookshelf_" + (face.equals("occupied") ? "empty" : face) + ".png")));
                for (String wood : new String[]{"acacia","bamboo","birch","cherry","crimson","dark_oak","jungle","mangrove","spruce","warped"}) {
                    var plank = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/" + wood + "_planks.png")));
                    var output = ShelfTexture.compose(template,empty,plank,plank.getWidth(),plank.getHeight());
                    int changed=0;
                    for(int y=0;y<16;y++) for(int x=0;x<16;x++) {
                        if(template.getRGB(x,y)!=empty.getRGB(x,y)) context.assertTrue(output.getRGB(x,y)==template.getRGB(x,y),"Book decoration must be preserved");
                        if(output.getRGB(x,y)!=template.getRGB(x,y)) changed++;
                    }
                    context.assertTrue(changed>0,"Each wood face must be recoloured: " + wood + " " + face);
                }
            }
            for(String slot : new String[]{"top_left","top_mid","top_right","bottom_left","bottom_mid","bottom_right"})
                context.assertTrue(zip.getEntry(prefix+"models/block/template_chiseled_bookshelf_slot_"+slot+".json")!=null,"Slot parent exists");
        }
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void creativeLayoutCommand(TestContext context) throws Exception {
        var world = context.getWorld();
        var player = context.createMockCreativeServerPlayerInWorld();
        var origin = context.getAbsolutePos(new BlockPos(0, 12, 0));
        player.refreshPositionAndAngles(origin.getX(), origin.getY(), origin.getZ(), 0, 0);
        var source = player.getCommandSource().withLevel(0).withSilent();
        int total = DynamicShelves.variants().size() + 1;
        var start = origin.south(2);
        for (int index = 0; index < total; index++) {
            var pos = start.east(index % 12 * 2).south(index / 12 * 3);
            world.getChunk(pos);
            for (int y = 0; y < 3; y++) world.setBlockState(pos.up(y), Blocks.AIR.getDefaultState());
        }
        world.setBlockState(start.up(), Blocks.STONE.getDefaultState());
        var dispatcher = world.getServer().getCommandManager().getDispatcher();
        int placed = dispatcher.execute("variantchiseledbookshelves layout", source);
        context.assertTrue(placed == total - 1, "Non-operator creative players can place all clear displays");
        context.assertTrue(world.getBlockState(start).isAir() && world.getBlockState(start.up()).isOf(Blocks.STONE), "Blocked column is left untouched");
        for (int index = 1; index < total; index++) {
            var pos = start.east(index % 12 * 2).south(index / 12 * 3);
            var state = world.getBlockState(pos);
            context.assertTrue(state.getBlock() instanceof ChiseledBookshelfBlock, "Display contains a functioning bookshelf");
            context.assertTrue(state.get(net.minecraft.state.property.Properties.HORIZONTAL_FACING) == net.minecraft.util.math.Direction.NORTH, "Display faces the player");
            context.assertTrue(world.getBlockState(pos.up(2)).getBlock() instanceof SlabBlock, "Display includes matching slab");
            context.assertTrue(!world.isAir(pos.up()), "Display includes source plank");
        }
        context.assertTrue(dispatcher.execute("variantchiseledbookshelves lineup", source) == 0, "Lineup alias skips occupied displays");
        var survival = new net.minecraft.server.network.ServerPlayerEntity(world.getServer(), world,
                new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "survival-test"),
                net.minecraft.network.packet.c2s.common.SyncedClientOptions.createDefault());
        boolean denied = false;
        try { dispatcher.execute("variantchiseledbookshelves layout", survival.getCommandSource().withLevel(0).withSilent()); }
        catch (com.mojang.brigadier.exceptions.CommandSyntaxException expected) { denied = true; }
        context.assertTrue(denied, "Survival players cannot use the layout command");
        denied = false;
        try { dispatcher.execute("variantchiseledbookshelves layout", world.getServer().getCommandSource().withSilent()); }
        catch (com.mojang.brigadier.exceptions.CommandSyntaxException expected) { denied = true; }
        context.assertTrue(denied, "Console cannot place a player layout");
        context.complete();
    }

}
