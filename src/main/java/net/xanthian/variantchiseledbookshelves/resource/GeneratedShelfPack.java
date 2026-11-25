package net.xanthian.variantchiseledbookshelves.resource;

import com.google.gson.Gson;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackInfo;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/** Supplies generated resources between vanilla defaults and mod/user packs. */
public final class GeneratedShelfPack implements ResourcePack {
    public static final Identifier ATLAS_SOURCE_ID = Identifier.of(Initialise.MOD_ID, "chiseled_bookshelves");
    public static final Identifier SOURCE_MOD_FONT_ID = Identifier.of(Initialise.MOD_ID, "source_mod");
    private static final Identifier CHISELED_BOOKSHELF = Identifier.ofVanilla("chiseled_bookshelf");
    private static final Identifier TABLE_TAG = Identifier.of(Initialise.MOD_ID, "chiseled_bookshelves");
    private static final Gson GSON = new Gson();
    private static final ResourcePackInfo INFO = new ResourcePackInfo(Initialise.MOD_ID + ":generated",
            Text.literal("Generated Chiseled Bookshelves"), ResourcePackSource.BUILTIN, Optional.empty());

    private final ResourceType type;
    private final Map<Identifier, byte[]> resources = new LinkedHashMap<>();
    private final Set<String> namespaces;

    public GeneratedShelfPack(ResourceType type, List<DynamicShelves.Variant> variants) {
        this.type = type;
        if (type == ResourceType.CLIENT_RESOURCES) {
            generateClientAssets(variants);
        } else {
            generateServerData(variants);
        }
        namespaces = resources.keySet().stream().map(Identifier::getNamespace).collect(Collectors.toUnmodifiableSet());
    }

    private void generateClientAssets(List<DynamicShelves.Variant> variants) {
        Map<String, String> names = new LinkedHashMap<>();
        for (var variant : variants) {
            Identifier id = variant.id();
            String model = id.withPrefixedPath("block/").toString();
            List<Object> parts = new ArrayList<>();
            String[] directions = {"north", "east", "south", "west"};
            String[] slots = {"top_left", "top_mid", "top_right", "bottom_left", "bottom_mid", "bottom_right"};
            put(id, "models/block/", Map.of("parent", "minecraft:block/chiseled_bookshelf", "textures",
                    Map.of("top", model + "_top", "side", model + "_side", "particle", model + "_top")));
            put(id, "models/item/", Map.of("parent", "minecraft:block/chiseled_bookshelf_inventory", "textures",
                    Map.of("top", model + "_top", "side", model + "_side", "front", model + "_empty", "particle", model + "_top")));
            for (int facing = 0; facing < 4; facing++) {
                parts.add(Map.of("when", Map.of("facing", directions[facing]), "apply", Map.of("model", model, "y", facing * 90, "uvlock", true)));
                for (int slot = 0; slot < 6; slot++) {
                    for (boolean occupied : List.of(false, true)) {
                        String suffix = "_slot_" + slots[slot] + (occupied ? "_occupied" : "_empty");
                        parts.add(Map.of("when", Map.of("facing", directions[facing], "slot_" + slot + "_occupied", occupied ? "true" : "false"),
                                "apply", Map.of("model", model + suffix, "y", facing * 90)));
                        put(id.withSuffixedPath(suffix), "models/block/", Map.of("parent", "minecraft:block/template_chiseled_bookshelf_slot_" + slots[slot],
                                "textures", Map.of("texture", model + (occupied ? "_occupied" : "_empty"))));
                    }
                }
            }
            put(id, "blockstates/", Map.of("multipart", parts));
            names.put(variant.block().getTranslationKey(), displayName(variant.planks()));
        }

        names.put("itemGroup.variantchiseledbookshelves", "Variant Chiseled Bookshelves");
        names.put("tag.item.variantchiseledbookshelves.chiseled_bookshelves", "Chiseled Bookshelves");
        names.put("tag.item.c.chiseled_bookshelves", "Chiseled Bookshelves");
        put(Identifier.of(Initialise.MOD_ID, "en_us"), "lang/", names);
        put(Identifier.ofVanilla("en_us"), "lang/", Map.of("block.minecraft.chiseled_bookshelf", "Oak Chiseled Bookshelf"));
        put(Identifier.ofVanilla("blocks"), "atlases/", Map.of("sources", List.of(Map.of("type", ATLAS_SOURCE_ID.toString()))));
        putSourceModFont();
    }

    private void putSourceModFont() {
        // ASCII glyphs occupy their code point's cell in Minecraft's 16 by 16 bitmap.
        List<String> rows = new ArrayList<>(16);
        for (int row = 0; row < 16; row++) {
            char[] glyphs = new char[16];
            for (int column = 0; column < 16; column++) {
                int codePoint = row * 16 + column;
                if (codePoint >= '!' && codePoint <= '~') {
                    glyphs[column] = (char) codePoint;
                }
            }
            rows.add(new String(glyphs));
        }
        put(SOURCE_MOD_FONT_ID, "font/", Map.of("providers", List.of(
                Map.of("type", "space", "advances", Map.of(" ", 3)),
                Map.of("type", "bitmap", "file", "minecraft:font/ascii.png", "height", 6, "ascent", 5, "chars", rows),
                Map.of("type", "reference", "id", "minecraft:default"))));
    }

    private void generateServerData(List<DynamicShelves.Variant> variants) {
        for (var variant : variants) {
            Identifier id = variant.id();
            putShapedRecipe(id, variant.ingredient(), variant.slab(), id);
            putRecipeUnlock(id, "has_planks", Map.of("items", variant.ingredient().toString()));
            put(id, "loot_table/blocks/", Map.of("type", "minecraft:block", "pools", List.of(Map.of(
                    "rolls", 1, "conditions", List.of(Map.of("condition", "minecraft:match_tool", "predicate", Map.of(
                            "predicates", Map.of("minecraft:enchantments", List.of(Map.of("enchantments", "minecraft:silk_touch",
                                    "levels", Map.of("min", 1))))))),
                    "entries", List.of(Map.of("type", "minecraft:item", "name", id.toString()))))));
        }

        List<String> ids = variants.stream().map(variant -> variant.id().toString()).toList();
        putTag(TABLE_TAG, "block", ids);
        putTag(TABLE_TAG, "item", ids);
        List<String> tableTag = List.of("#" + TABLE_TAG);
        putTag(Identifier.ofVanilla("mineable/axe"), "block", tableTag);
        putTag(Identifier.of("c", "chiseled_bookshelves"), "block", tableTag);
        putTag(Identifier.of("c", "chiseled_bookshelves"), "item", tableTag);
        List<String> nonFlammable = variants.stream().filter(DynamicShelves.Variant::nonFlammable)
                .map(variant -> variant.id().toString()).toList();
        if (!nonFlammable.isEmpty()) {
            putTag(Identifier.ofVanilla("non_flammable_wood"), "item", nonFlammable);
        }

        // Preserve the conversion recipe's shipped 1.21 ID for existing recipe books.
        Identifier conversion = Identifier.of(Initialise.MOD_ID, "chiseled_bookshelf");
        put(conversion, "recipe/", Map.of("type", "minecraft:crafting_shapeless", "category", "misc",
                "ingredients", List.of(Map.of("tag", TABLE_TAG.toString())), "result", Map.of("id", CHISELED_BOOKSHELF.toString())));
        putRecipeUnlock(conversion, "has_chiseled_bookshelf", Map.of("items", "#" + TABLE_TAG));
        putShapedRecipe(CHISELED_BOOKSHELF, Identifier.ofVanilla("oak_planks"), Identifier.ofVanilla("oak_slab"), CHISELED_BOOKSHELF);
    }

    private void putShapedRecipe(Identifier id, Identifier ingredient, Identifier slab, Identifier result) {
        put(id, "recipe/", Map.of("type", "minecraft:crafting_shaped", "category", "misc",
                "pattern", List.of("###", "SSS", "###"), "key", Map.of("#", Map.of("item", ingredient.toString()), "S", Map.of("item", slab.toString())),
                "result", Map.of("id", result.toString())));
    }

    private void putRecipeUnlock(Identifier id, String criterion, Map<String, String> itemPredicate) {
        put(id, "advancement/recipes/misc/", Map.of("parent", "minecraft:recipes/root", "criteria", Map.of(
                criterion, Map.of("trigger", "minecraft:inventory_changed",
                        "conditions", Map.of("items", List.of(itemPredicate))),
                "has_the_recipe", Map.of("trigger", "minecraft:recipe_unlocked",
                        "conditions", Map.of("recipe", id.toString()))),
                "requirements", List.of(List.of(criterion, "has_the_recipe")),
                "rewards", Map.of("recipes", List.of(id.toString()))));
    }

    private void putTag(Identifier id, String registry, List<String> values) {
        put(id, "tags/" + registry + "/", Map.of("replace", false, "values", values));
    }

    private void put(Identifier id, String directory, Object json) {
        resources.put(Identifier.of(id.getNamespace(), directory + id.getPath() + ".json"),
                GSON.toJson(json).getBytes(StandardCharsets.UTF_8));
    }

    private static String displayName(Identifier planks) {
        String path = planks.getPath().replaceFirst("(^|[/_])planks$", "").replace('/', ' ').replace('_', ' ').trim();
        if (path.isEmpty()) {
            path = planks.getNamespace().replace('_', ' ').trim();
            if (path.isEmpty()) {
                path = planks.getNamespace();
            }
        }
        StringBuilder name = new StringBuilder();
        for (String word : path.split(" +")) {
            name.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(' ');
        }
        return name + "Chiseled Bookshelf";
    }

    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        byte[] bytes = this.type == type ? resources.get(id) : null;
        return bytes == null ? null : () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer output) {
        if (this.type == type) {
            resources.forEach((id, bytes) -> {
                if (id.getNamespace().equals(namespace) && (prefix.isEmpty() || id.getPath().startsWith(prefix + "/"))) {
                    output.accept(id, () -> new ByteArrayInputStream(bytes));
                }
            });
        }
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.type == type ? namespaces : Set.of();
    }

    @Override
    public ResourcePackInfo getInfo() {
        return INFO;
    }

    @Override
    public InputSupplier<InputStream> openRoot(String... path) {
        return null;
    }

    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> reader) {
        return null;
    }

    @Override
    public void close() {
        // Only in-memory data is retained.
    }
}
