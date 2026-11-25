package net.xanthian.variantchiseledbookshelves.block;

import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.util.Identifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Shipped direct-plank recipes also identify slab families with unconventional names. */
public final class SlabDiscovery {
    public static Map<Identifier, List<Identifier>> recipes(List<Path> roots) {
        Map<Identifier, List<Identifier>> result = new HashMap<>();
        for (Path root : roots) {
            Path data = root.resolve("data");
            if (!Files.isDirectory(data)) continue;
            try (var files = Files.walk(data)) {
                for (Path file : files.filter(f -> f.toString().endsWith(".json") && f.toString().replace('\\', '/').contains("/recipe/")).sorted().toList()) {
                    try (var reader = Files.newBufferedReader(file)) {
                        var json = JsonParser.parseReader(reader).getAsJsonObject();
                        if (!json.has("type") || !json.get("type").getAsString().equals("minecraft:crafting_shaped") || !json.has("key")) continue;
                        Set<String> items = new HashSet<>();
                        boolean direct = true;
                        for (var value : json.getAsJsonObject("key").entrySet()) {
                            if (!value.getValue().isJsonObject() || !value.getValue().getAsJsonObject().has("item")) { direct = false; break; }
                            items.add(value.getValue().getAsJsonObject().get("item").getAsString());
                        }
                        if (!direct || items.size() != 1) continue;
                        var output = json.getAsJsonObject("result");
                        if (output == null || !output.has("id")) continue;
                        Identifier ingredient = Identifier.of(items.iterator().next());
                        Identifier slab = Identifier.of(output.get("id").getAsString());
                        if (ingredient.getNamespace().equals(slab.getNamespace())) result.computeIfAbsent(ingredient, k -> new ArrayList<>()).add(slab);
                    } catch (RuntimeException | java.io.IOException ignored) { /* unrelated or conditional recipe */ }
                }
            } catch (java.io.IOException exception) { LogUtils.getLogger().warn("Could not inspect slab recipes in {}", data, exception); }
        }
        return result;
    }
}
