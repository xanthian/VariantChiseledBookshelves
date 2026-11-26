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
        var result = ShelfTexture.compose(occupied,empty,empty,plank,32,32);
        context.assertTrue(result.getWidth()==32 && result.getHeight()==64,"Animation dimensions retained");
        context.assertTrue(result.getRGB(4,4)==0xff1256ab && result.getRGB(4,36)==0xff1256ab,"Book pixels survive each frame");
        context.assertTrue(result.getRGB(0,0)!=result.getRGB(0,32),"Wood follows animated source");
        boolean rejected=false;
        try { ShelfTexture.compose(occupied,empty,empty,plank,17,32); } catch(IllegalArgumentException expected) { rejected=true; }
        context.assertTrue(rejected,"Invalid frame dimensions rejected");
        context.complete();
    }
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void vanillaAssetsAndSlotTemplates(TestContext context) throws Exception {
        try (var zip = new java.util.zip.ZipFile(System.getProperty("shelf.assetJar"))) {
            String prefix = "assets/minecraft/";
            var oakPlanks = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/oak_planks.png")));
            String[] faces = {"top", "side", "empty", "occupied"};
            for (String face : faces) {
                var template = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/chiseled_bookshelf_" + face + ".png")));
                var empty = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/chiseled_bookshelf_" + (face.equals("occupied") ? "empty" : face) + ".png")));
                var identity = ShelfTexture.compose(template,empty,oakPlanks,oakPlanks,16,16,oakPlanks,16,16,face.equals("empty") || face.equals("occupied"));
                for (int y = 0; y < 16; y++) for (int x = 0; x < 16; x++)
                    context.assertTrue(identity.getRGB(x,y) == template.getRGB(x,y), "Oak must reproduce the original vanilla carving exactly");
                for (String wood : new String[]{"acacia","bamboo","birch","cherry","crimson","dark_oak","jungle","mangrove","spruce","warped"}) {
                    var plank = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "textures/block/" + wood + "_planks.png")));
                    var output = ShelfTexture.compose(template,empty,oakPlanks,plank,plank.getWidth(),plank.getHeight(),plank,plank.getWidth(),plank.getHeight(),face.equals("empty") || face.equals("occupied"));
                    int changed=0;
                    var recessHash = new java.util.zip.CRC32();
                    var palette = new java.util.HashMap<Integer,Integer>();
                    for(int y=0;y<16;y++) for(int x=0;x<16;x++) {
                        if(template.getRGB(x,y)!=empty.getRGB(x,y)) context.assertTrue(output.getRGB(x,y)==template.getRGB(x,y),"Book decoration must be preserved");
                        if(template.getRGB(x,y) == empty.getRGB(x,y)) {
                            Integer previous = palette.putIfAbsent(template.getRGB(x,y), output.getRGB(x,y));
                            context.assertTrue(previous == null || previous == output.getRGB(x,y), "A carving shade must stay uniform instead of picking up plank grain");
                        }
                        if (face.equals("empty") && x>0 && x<15 && y!=0 && y!=7 && y!=8 && y!=15) {
                            int pixel = output.getRGB(x,y);
                            recessHash.update(pixel>>>24); recessHash.update(pixel>>>16); recessHash.update(pixel>>>8); recessHash.update(pixel);
                        }
                        if(output.getRGB(x,y)!=template.getRGB(x,y)) changed++;
                    }
                    if (face.equals("empty")) {
                        var originalRecesses = java.util.Map.of("acacia",2339093439L,"bamboo",1845664137L,"birch",192852155L,
                                "cherry",1449095171L,"crimson",2063140172L,"dark_oak",3815308245L,"jungle",2195393557L,
                                "mangrove",1221471609L,"spruce",1176680484L,"warped",372246074L);
                        context.assertTrue(recessHash.getValue()==originalRecesses.get(wood), "Existing recess pixels must remain unchanged: " + wood);
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
        var player = TestPlayers.creative(context);
        var origin = context.getAbsolutePos(new BlockPos(0, 12, 0));
        player.refreshPositionAndAngles(origin.getX(), origin.getY(), origin.getZ(), 0, 0);
        var source = player.getCommandSource().withLevel(0).withSilent();
        int total = DynamicShelves.variants().size() + 1;
        var start = origin.south(2);
        for (int index = 0; index < total; index++) {
            var pos = start.east(index);
            world.getChunk(pos);
            for (int y = 0; y < 3; y++) world.setBlockState(pos.up(y), Blocks.AIR.getDefaultState());
        }
        world.setBlockState(start.up(), Blocks.STONE.getDefaultState());
        var dispatcher = world.getServer().getCommandManager().getDispatcher();
        int placed = dispatcher.execute("variantchiseledbookshelves layout", source);
        context.assertTrue(placed == total - 1, "Non-operator creative players can place all clear displays");
        context.assertTrue(world.getBlockState(start).isAir() && world.getBlockState(start.up()).isOf(Blocks.STONE), "Blocked column is left untouched");
        for (int index = 1; index < total; index++) {
            var pos = start.east(index);
            var state = world.getBlockState(pos);
            context.assertTrue(state.getBlock() instanceof ChiseledBookshelfBlock, "Display contains a functioning bookshelf");
            context.assertTrue(state.get(net.minecraft.state.property.Properties.HORIZONTAL_FACING) == net.minecraft.util.math.Direction.NORTH, "Display faces the player");
            var upper = world.getBlockState(pos.up(2));
            context.assertTrue(upper.isOf(state.getBlock()), "Upper display uses the same bookshelf");
            context.assertTrue(upper.get(net.minecraft.state.property.Properties.HORIZONTAL_FACING) == net.minecraft.util.math.Direction.EAST, "Upper bookshelf faces sideways");
            var expectedPlank = state.isOf(Blocks.CHISELED_BOOKSHELF) ? Blocks.OAK_PLANKS :
                    Registries.BLOCK.get(DynamicShelves.variants().stream().filter(v -> v.block() == state.getBlock()).findFirst().orElseThrow().planks());
            context.assertTrue(world.getBlockState(pos.up()).isOf(expectedPlank), "Middle display is the matching source plank");
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

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void plankGrainDoesNotObscureCarvings(TestContext context) {
        var template = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        var oak = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        var wood = new BufferedImage(32,64,BufferedImage.TYPE_INT_ARGB);
        for (int y=0;y<16;y++) for(int x=0;x<16;x++) {
            oak.setRGB(x,y,0xffaa8855);
            template.setRGB(x,y,x<8?0xff443322:0xffaa8855);
        }
        for(int y=0;y<64;y++) for(int x=0;x<32;x++) {
            wood.setRGB(x,y,y<32 ? ((x+y)%2==0?0xff226600:0xff88bb33) : ((x+y)%2==0?0xff662233:0xffbb8899));
        }
        var output = ShelfTexture.compose(template,template,oak,wood,32,32);
        context.assertTrue(output.getRGB(0,0)==output.getRGB(1,0), "Alternating plank grain must not break up a recessed shade");
        context.assertTrue(output.getRGB(0,0)!=output.getRGB(16,0), "Recesses and frame retain distinct shades");
        context.assertTrue(output.getRGB(0,0)!=output.getRGB(0,32), "Palette follows each animation frame");
        wood.setRGB(0,0,0x00226600);
        output = ShelfTexture.compose(template,template,oak,wood,32,32);
        context.assertTrue((output.getRGB(0,0)>>>24)==0, "Resource-pack transparency is retained");
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void mossStaysOnExteriorAndWoodKeepsItsHue(TestContext context) throws Exception {
        try (var zip = new java.util.zip.ZipFile(System.getProperty("shelf.assetJar"))) {
            String prefix = "assets/minecraft/textures/block/";
            var oak = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "oak_planks.png")));
            var empty = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "chiseled_bookshelf_empty.png")));
            var base = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
            var mossy = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
            for (int y=0;y<16;y++) for(int x=0;x<16;x++) {
                base.setRGB(x,y,(x+y)%2==0?0xff7e3a56:0xff5c3042);
                // Different grain alignment must not be mistaken for decoration.
                mossy.setRGB(x,y,(x+y)%2==0?0xff5c3042:0xff7e3a56);
            }
            mossy.setRGB(0,4,0xff315d20);
            mossy.setRGB(4,4,0xff315d20);
            mossy.setRGB(6,7,0xff315d20);
            for (String face : new String[]{"empty", "occupied", "side", "top"}) {
                var template = javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix + "chiseled_bookshelf_" + face + ".png")));
                var reference = face.equals("occupied") ? empty : template;
                boolean front = face.equals("empty") || face.equals("occupied");
                var clean = ShelfTexture.compose(template,reference,oak,base,16,16,base,16,16,front);
                var moss = ShelfTexture.compose(template,reference,oak,mossy,16,16,base,16,16,front);
                context.assertTrue(moss.getRGB(0,4)!=clean.getRGB(0,4), "Moss is retained on exterior borders: " + face);
                context.assertTrue(moss.getRGB(6,7)!=clean.getRGB(6,7), "Moss is retained on the middle rail: " + face);
                if (face.equals("empty")) context.assertTrue(moss.getRGB(4,4)==clean.getRGB(4,4), "Moss must not enter the shelf recess");
                if (!front) context.assertTrue(moss.getRGB(4,4)!=clean.getRGB(4,4), "Moss covers the carved side/top too");
                for (int y=0;y<16;y++) for(int x=0;x<16;x++) {
                    if (mossy.getRGB(x,y)!=0xff315d20) context.assertTrue(moss.getRGB(x,y)==clean.getRGB(x,y), "Plank grain must not obscure carvings");
                    if (template.getRGB(x,y)!=reference.getRGB(x,y)) context.assertTrue(moss.getRGB(x,y)==template.getRGB(x,y), "Books keep their original pixels");
                }
                context.assertTrue(java.util.Set.of(0xff7e3a56,0xff5c3042).contains(clean.getRGB(0,4)), "Exterior colour must come directly from the clean plank palette");
                if (face.equals("empty")) {
                    int pixel = clean.getRGB(4,4);
                    double r=(pixel>>16)&255, g=(pixel>>8)&255, b=pixel&255;
                    context.assertTrue(Math.abs(r*53-g*109)<100 && Math.abs(r*76-b*109)<100, "Existing recess shading is retained");
                }
            }
        }
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void animatedPalettesUseSampledColoursAndKeepMoss(TestContext context) throws Exception {
        try (var zip = new java.util.zip.ZipFile(System.getProperty("shelf.assetJar"))) {
            String prefix="assets/minecraft/textures/block/";
            var oak=javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix+"oak_planks.png")));
            var template=javax.imageio.ImageIO.read(zip.getInputStream(zip.getEntry(prefix+"chiseled_bookshelf_side.png")));
            var base=new BufferedImage(16,32,BufferedImage.TYPE_INT_ARGB);
            var decorated=new BufferedImage(32,64,BufferedImage.TYPE_INT_ARGB);
            int[][] colours={{0xffcd8580,0xffe7bab4,0xffe7cac5},{0xff5c3042,0xff7e3a56,0xff863e5a}};
            for(int frame=0;frame<2;frame++) {
                for(int y=0;y<16;y++) for(int x=0;x<16;x++) base.setRGB(x,frame*16+y,colours[frame][x%3]);
                for(int y=0;y<32;y++) for(int x=0;x<32;x++) decorated.setRGB(x,frame*32+y,colours[frame][x/2%3]);
                decorated.setRGB(0,frame*32+8,0xff315d20);
            }
            var clean=ShelfTexture.compose(template,template,oak,base,16,16);
            var moss=ShelfTexture.compose(template,template,oak,decorated,32,32,base,16,16,false);
            context.assertTrue(moss.getWidth()==32 && moss.getHeight()==64,"High resolution animation layout retained");
            for(int frame=0;frame<2;frame++) {
                var palette=java.util.Set.of(colours[frame][0],colours[frame][1],colours[frame][2]);
                var used=new java.util.HashSet<Integer>();
                for(int y=0;y<32;y++) for(int x=0;x<32;x++) {
                    int pixel=moss.getRGB(x,frame*32+y);
                    if(x==0 && y==8) {
                        context.assertTrue(pixel!=clean.getRGB(0,frame*16+4),"Moss remains visible in each animation frame");
                    } else {
                        context.assertTrue(palette.contains(pixel),"Exterior shades must be real colours from this frame's plank palette");
                        context.assertTrue(pixel==clean.getRGB(x/2,frame*16+y/2),"Moss must not change neighbouring wood colours or carving geometry");
                        used.add(pixel);
                    }
                }
                context.assertTrue(used.size()==3,"Highlights, midtones and shadows all remain visible");
            }
        }
        context.complete();
    }

}
