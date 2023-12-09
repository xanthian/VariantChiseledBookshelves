package net.xanthian.variantchiseledbookshelves.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.block.compatability.*;

import java.util.Map;

public class LangFileGenerator extends FabricLanguageProvider {
    public LangFileGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    public static String generateBlockDisplayName(Block block) {
        Identifier name = Registries.BLOCK.getId(block);
        String blockName = name.getPath();
        int underscoreIndex = blockName.indexOf('_');
        if (underscoreIndex != -1 && underscoreIndex < blockName.length() - 1) {
            blockName = blockName.substring(underscoreIndex + 1);
        }
        String[] parts = blockName.split("_");
        StringBuilder displayName = new StringBuilder();
        for (String part : parts) {
            displayName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
        }
        return displayName.toString().trim();
    }

    private static void registerTranslations(FabricLanguageProvider.TranslationBuilder translationBuilder, Map<Identifier, Block> blockMap) {
        for (Block block : blockMap.values()) {
            translationBuilder.add(block, generateBlockDisplayName(block));
        }
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {

        translationBuilder.add(Vanilla.ACACIA_CHISELED_BOOKSHELF, "Acacia Chiseled Bookshelf");
        translationBuilder.add(Vanilla.BAMBOO_CHISELED_BOOKSHELF, "Bamboo Chiseled Bookshelf");
        translationBuilder.add(Vanilla.BIRCH_CHISELED_BOOKSHELF, "Birch Chiseled Bookshelf");
        translationBuilder.add(Vanilla.CHERRY_CHISELED_BOOKSHELF, "Cherry Chiseled Bookshelf");
        translationBuilder.add(Vanilla.CRIMSON_CHISELED_BOOKSHELF, "Crimson Chiseled Bookshelf");
        translationBuilder.add(Vanilla.DARK_OAK_CHISELED_BOOKSHELF, "Dark Oak Chiseled Bookshelf");
        translationBuilder.add(Vanilla.JUNGLE_CHISELED_BOOKSHELF, "Jungle Chiseled Bookshelf");
        translationBuilder.add(Vanilla.MANGROVE_CHISELED_BOOKSHELF, "Mangrove Chiseled Bookshelf");
        translationBuilder.add(Blocks.CHISELED_BOOKSHELF, "Oak Chiseled Bookshelf");
        translationBuilder.add(Vanilla.SPRUCE_CHISELED_BOOKSHELF, "Spruce Chiseled Bookshelf");
        translationBuilder.add(Vanilla.WARPED_CHISELED_BOOKSHELF, "Warped Chiseled Bookshelf");

        registerTranslations(translationBuilder, AdAstra.AA_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, BeachParty.LDBP_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, BetterArcheology.BA_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Bewitchment.BW_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, BiomeMakeover.BM_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Blockus.BLS_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Botania.BOT_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Cinderscapes.CS_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, DeeperAndDarker.DAD_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Desolation.DS_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, EldritchEnd.EE_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, MineCells.MC_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, NaturesSpirit.NS_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Promenade.PROM_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, RegionsUnexplored.RU_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, SnifferPlus.SP_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, TechReborn.TR_CHISELED_BOOKSHELVES);
        registerTranslations(translationBuilder, Vinery.LDV_CHISELED_BOOKSHELVES);

    }
}
