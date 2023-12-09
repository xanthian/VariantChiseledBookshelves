package net.xanthian.variantchiseledbookshelves.util;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModel {


    public static final Model CHISELED_BOOKSHELF;
    public static final Model CHISELED_BOOKSHELF_INV;

    static {
        CHISELED_BOOKSHELF = createModel("variant_chiseled_bookshelf", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
        CHISELED_BOOKSHELF_INV = createModel("variant_chiseled_bookshelf_inventory", TextureKey.TOP, TextureKey.SIDE, TextureKey.FRONT, TextureKey.PARTICLE);
    }

    public static Identifier getBlockId(String id) {
        return new Identifier("variantchiseledbookshelves", "block/" + id);
    }

    public static Model createModel(String parent, TextureKey... requiredTextures) {
        return new Model(Optional.of(getBlockId(parent)), Optional.empty(), requiredTextures);
    }
}