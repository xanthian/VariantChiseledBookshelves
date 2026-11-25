package net.xanthian.variantchiseledbookshelves.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.Identifier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Resolves standard JSON plank models, including inherited textures and #texture references. */
public final class PlankTextureResolver {
    private PlankTextureResolver() {
    }

    public static Resource resolve(ResourceManager resources, Identifier planks) throws IOException {
        Identifier model = Identifier.of(planks.getNamespace(), "block/" + planks.getPath());
        var blockstate = resources.getResource(Identifier.of(planks.getNamespace(),
                "blockstates/" + planks.getPath() + ".json"));
        if (blockstate.isPresent()) {
            try (Reader reader = blockstate.get().getReader()) {
                JsonObject state = JsonParser.parseReader(reader).getAsJsonObject();
                JsonElement apply = null;
                if (state.has("variants") && !state.getAsJsonObject("variants").isEmpty()) {
                    apply = state.getAsJsonObject("variants").entrySet().iterator().next().getValue();
                } else if (state.has("multipart") && !state.getAsJsonArray("multipart").isEmpty()) {
                    apply = state.getAsJsonArray("multipart").get(0).getAsJsonObject().get("apply");
                }
                if (apply != null) {
                    if (apply.isJsonArray()) {
                        apply = apply.getAsJsonArray().get(0);
                    }
                    model = Identifier.of(apply.getAsJsonObject().get("model").getAsString());
                }
            }
        }

        Map<String, String> textures = new LinkedHashMap<>();
        collectTextures(resources, model, textures, new HashSet<>());
        for (String key : new String[]{"all", "side", "top", "particle", "north", "up"}) {
            String value = textures.get(key);
            Set<String> visited = new HashSet<>();
            while (value != null && value.startsWith("#") && visited.add(value)) {
                value = textures.get(value.substring(1));
            }
            if (value != null && !value.startsWith("#")) {
                var resource = resources.getResource(Identifier.of(value).withPrefixedPath("textures/").withSuffixedPath(".png"));
                if (resource.isPresent()) {
                    return resource.get();
                }
            }
        }
        return resources.getResourceOrThrow(Identifier.of(planks.getNamespace(),
                "textures/block/" + planks.getPath() + ".png"));
    }

    private static void collectTextures(ResourceManager resources, Identifier model, Map<String, String> textures,
                                        Set<Identifier> visited) throws IOException {
        if (visited.size() >= 32 || !visited.add(model)) {
            return;
        }
        var resource = resources.getResource(model.withPrefixedPath("models/").withSuffixedPath(".json"));
        if (resource.isEmpty()) {
            return;
        }
        try (Reader reader = resource.get().getReader()) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("parent")) {
                collectTextures(resources, Identifier.of(json.get("parent").getAsString()), textures, visited);
            }
            if (json.has("textures")) {
                json.getAsJsonObject("textures").entrySet().forEach(entry -> {
                    if (entry.getValue().isJsonPrimitive()) {
                        textures.put(entry.getKey(), entry.getValue().getAsString());
                    }
                });
            }
        }
    }
}
