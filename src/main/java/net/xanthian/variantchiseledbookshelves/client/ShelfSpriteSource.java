package net.xanthian.variantchiseledbookshelves.client;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteDimensions;
import net.minecraft.client.texture.SpriteOpener;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.AtlasSourceType;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/** Runs as part of atlas stitching, so generation and lifetime follow resource reloads. */
public final class ShelfSpriteSource implements AtlasSource {
    private static final MapCodec<ShelfSpriteSource> CODEC = MapCodec.unit(ShelfSpriteSource::new);
    public static final AtlasSourceType TYPE = new AtlasSourceType(CODEC);
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> FACES = List.of("empty", "occupied", "side", "top");

    @Override
    public AtlasSourceType getType() {
        return TYPE;
    }

    @Override
    public void load(ResourceManager resources, SpriteRegions output) {
        for (var variant : DynamicShelves.variants()) {
            Identifier table = variant.id();
            for (String face : FACES) {
                Identifier sprite = table.withPrefixedPath("block/").withSuffixedPath("_" + face);
                // A pack may still supply a hand-drawn texture at the original location.
                var override = resources.getResource(RESOURCE_FINDER.toResourcePath(sprite));
                if (override.isPresent()) {
                    output.add(sprite, override.get());
                } else {
                    output.add(sprite, loader -> generate(resources, loader, sprite, variant.planks(), face));
                }
            }
        }
    }

    private static SpriteContents generate(ResourceManager resources, SpriteOpener loader,
                                           Identifier sprite, Identifier planksId, String face) {
        Identifier templateId = Identifier.ofVanilla("textures/block/"
                + "chiseled_bookshelf_" + face + ".png");
        try {
            Resource plankResource = PlankTextureResolver.resolve(resources, planksId);
            // Built-in empty and occupied templates identify book pixels independently of resource packs.
            BufferedImage template = read(builtIn(resources, templateId));
            BufferedImage oak = read(builtIn(resources,
                    Identifier.ofVanilla("textures/block/chiseled_bookshelf_" + (face.equals("occupied") ? "empty" : face) + ".png")));
            BufferedImage oakPlanks = read(builtIn(resources, Identifier.ofVanilla("textures/block/oak_planks.png")));
            BufferedImage planks = read(plankResource);
            var animation = plankResource.getMetadata().decode(AnimationResourceMetadata.READER);
            SpriteDimensions frame = animation.map(value -> value.getSize(planks.getWidth(), planks.getHeight()))
                    .orElseGet(() -> new SpriteDimensions(planks.getWidth(), planks.getHeight()));
            BufferedImage image = ShelfTexture.compose(template, oak, oakPlanks, planks, frame.width(), frame.height());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            if (!ImageIO.write(image, "PNG", bytes)) {
                throw new IOException("PNG encoder unavailable");
            }
            byte[] png = bytes.toByteArray();
            Resource generated = new Resource(plankResource.getPack(), () -> new ByteArrayInputStream(png),
                    plankResource::getMetadata);
            // Let Minecraft validate and load animation and texture metadata normally.
            SpriteContents result = loader.loadSprite(sprite, generated);
            if (result != null) {
                return result;
            }
            LOGGER.warn("Could not load generated texture {}; using the vanilla chiseled bookshelf", sprite);
        } catch (IOException | RuntimeException exception) {
            LOGGER.warn("Could not generate texture {}; using the vanilla chiseled bookshelf", sprite, exception);
        }
        return resources.getResource(templateId).map(resource -> loader.loadSprite(sprite, resource)).orElse(null);
    }

    private static Resource builtIn(ResourceManager resources, Identifier id) throws IOException {
        List<Resource> stack = resources.getAllResources(id);
        if (stack.isEmpty()) {
            throw new IOException("Missing vanilla texture " + id);
        }
        // Resource stacks are ordered from lowest to highest pack priority.
        return stack.getFirst();
    }

    private static BufferedImage read(Resource resource) throws IOException {
        try (InputStream stream = resource.getInputStream()) {
            BufferedImage image = ImageIO.read(stream);
            if (image == null) {
                throw new IOException("Invalid image in pack " + resource.getPackId());
            }
            return image;
        }
    }
}
