package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;
import net.xanthian.variantchiseledbookshelves.util.ModModel;

import java.util.List;
import java.util.Map;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    public static void supplyChiseledBookshelfModels(MultipartBlockStateSupplier blockStateSupplier, When.PropertyCondition facingCondition, VariantSettings.Rotation rotation, Block block, BlockStateModelGenerator blockStateModelGenerator) {
        Map.of(
                Properties.SLOT_0_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_LEFT,
                Properties.SLOT_1_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_MID,
                Properties.SLOT_2_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_RIGHT,
                Properties.SLOT_3_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT,
                Properties.SLOT_4_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_MID,
                Properties.SLOT_5_OCCUPIED, Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT
        ).forEach((property, model) -> {
            String slot = getSlotFromProperty(property);
            ModelGenerator.supplyChiseledBookshelfModel(blockStateSupplier, facingCondition, rotation, property, slot, model, true, block, blockStateModelGenerator);
            ModelGenerator.supplyChiseledBookshelfModel(blockStateSupplier, facingCondition, rotation, property, slot, model, false, block, blockStateModelGenerator);
        });
    }

    public static void supplyChiseledBookshelfModel(MultipartBlockStateSupplier blockStateSupplier, When.PropertyCondition facingCondition, VariantSettings.Rotation rotation, BooleanProperty property, String slot, Model model, boolean occupied, Block block, BlockStateModelGenerator blockStateModelGenerator) {
        Identifier identifier = getModelIdForBlock(block, occupied, slot);
        blockStateSupplier.with(When.allOf(facingCondition, When.create().set(property, occupied)),
                BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, rotation)
        );
    }

    private static String getSlotFromProperty(BooleanProperty property) {
        return property.getName();
    }

    public static Identifier getModelIdForBlock(Block block, boolean occupied, String slot) {
        String string = occupied ? "_occupied" : "_empty";
        String slotName = switch (slot) {
            case "slot_0_occupied" -> "_slot_top_left";
            case "slot_1_occupied" -> "_slot_top_mid";
            case "slot_2_occupied" -> "_slot_top_right";
            case "slot_3_occupied" -> "_slot_bottom_left";
            case "slot_4_occupied" -> "_slot_bottom_mid";
            case "slot_5_occupied" -> "_slot_bottom_right";
            default -> "";
        };
        String name = block.getTranslationKey();
        if (name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        return new Identifier(Initialise.MOD_ID, "block/" + name + string + slotName);
    }

    private void createChiseledBookshelf(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier identifier = ModelIds.getBlockModelId(block);
        TextureMap textureMap = TextureMap.of(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_top")).put(TextureKey.SIDE, TextureMap.getSubId(block, "_side"));
        TextureMap textureMap2 = TextureMap.of(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_top")).put(TextureKey.SIDE, TextureMap.getSubId(block, "_side")).put(TextureKey.FRONT, TextureMap.getSubId(block, "_empty"));
        ModModel.CHISELED_BOOKSHELF.upload(ModelIds.getBlockModelId(block), textureMap, blockStateModelGenerator.modelCollector);
        ModModel.CHISELED_BOOKSHELF_INV.upload(ModelIds.getBlockSubModelId(block, "_inventory"), textureMap2, blockStateModelGenerator.modelCollector);
        List.of(
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT,
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_MID,
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT,
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_LEFT,
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_MID,
                Models.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_RIGHT
        ).forEach(model -> {
            blockStateModelGenerator.createSubModel(block, "_empty", model, TextureMap::texture);
            blockStateModelGenerator.createSubModel(block, "_occupied", model, TextureMap::texture);
        });
        MultipartBlockStateSupplier multipartBlockStateSupplier = MultipartBlockStateSupplier.create(block);
        Map.of(
                Direction.NORTH, VariantSettings.Rotation.R0,
                Direction.EAST, VariantSettings.Rotation.R90,
                Direction.SOUTH, VariantSettings.Rotation.R180,
                Direction.WEST, VariantSettings.Rotation.R270
        ).forEach((direction, rotation) -> {
            When.PropertyCondition propertyCondition = When.create().set(Properties.HORIZONTAL_FACING, direction);
            multipartBlockStateSupplier.with(propertyCondition, BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, rotation).put(VariantSettings.UVLOCK, true));
            ModelGenerator.supplyChiseledBookshelfModels(multipartBlockStateSupplier, propertyCondition, rotation, block, blockStateModelGenerator);
        });
        blockStateModelGenerator.blockStateCollector.accept(multipartBlockStateSupplier);
        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockSubModelId(block, "_inventory"));
    }

    public void registerModel(BlockStateModelGenerator blockStateModelGenerator, Map<Identifier, Block> blockMap) {
        for (Block block : blockMap.values()) {
            createChiseledBookshelf(blockStateModelGenerator, block);
        }
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        List.of(
                Vanilla.VANILLA_CHISELED_BOOKSHELVES,
                AdAstra.AA_CHISELED_BOOKSHELVES,
                BeachParty.LDBP_CHISELED_BOOKSHELVES,
                BetterArcheology.BA_CHISELED_BOOKSHELVES,
                Bewitchment.BW_CHISELED_BOOKSHELVES,
                BiomeMakeover.BM_CHISELED_BOOKSHELVES,
                DeeperAndDarker.DAD_CHISELED_BOOKSHELVES,
                EldritchEnd.EE_CHISELED_BOOKSHELVES,
                MineCells.MC_CHISELED_BOOKSHELVES,
                NaturesSpirit.NS_CHISELED_BOOKSHELVES,
                Promenade.PROM_CHISELED_BOOKSHELVES,
                RegionsUnexplored.RU_CHISELED_BOOKSHELVES,
                SnifferPlus.SP_CHISELED_BOOKSHELVES,
                TechReborn.TR_CHISELED_BOOKSHELVES,
                Vinery.LDV_CHISELED_BOOKSHELVES
        ).forEach(blockMap -> registerModel(blockStateModelGenerator, blockMap));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}