package net.xanthian.variantchiseledbookshelves.block;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.xanthian.variantchiseledbookshelves.Initialise;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Handles existing and future plank block items through Fabric's registry callbacks. */
public final class DynamicShelves {

    public record Variant(Identifier id, Identifier planks, Identifier ingredient, Block block,
                          boolean nonFlammable, Identifier slab) {
    }

    private static List<Variant> variants = List.of();
    private static boolean initialized;
    private static boolean logged;
    private static Set<Identifier> blockTags = Set.of();
    private static Set<Identifier> itemTags = Set.of();
    private static Set<Identifier> nonFlammable = Set.of();
    private static final Set<Identifier> processed = new HashSet<>();
    private static final Map<Block, Identifier> pendingItems = new IdentityHashMap<>();
    private static final Map<Identifier, Variant> registered = new TreeMap<>();
    private static final Map<Identifier, Identifier> sourcePlanks = new HashMap<>();

    private DynamicShelves() {
    }

    public static List<Variant> variants() {
        return variants;
    }

    public static void register() {
        if (initialized) {
            return;
        }
        initialized = true;

        // Companion mods can resolve a shelf's wood after registration without depending on our classes.
        FabricLoader.getInstance().getObjectShare().put(Initialise.MOD_ID + ":source_planks",
                Collections.unmodifiableMap(sourcePlanks));

        List<Path> roots = FabricLoader.getInstance().getAllMods().stream()
                .sorted(Comparator.comparing(mod -> mod.getMetadata().getId()))
                .flatMap(mod -> mod.getRootPaths().stream()).toList();
        slabRecipes = SlabDiscovery.recipes(roots);
        PlankDiscovery discovery = new PlankDiscovery(roots);
        blockTags = discovery.entries("block", Identifier.ofVanilla("planks"));
        itemTags = discovery.entries("item", Identifier.ofVanilla("planks"));
        nonFlammable = discovery.entries("item", Identifier.ofVanilla("non_flammable_wood"));

        // Normally blocks arrive before their items. Also handle the reverse registration order.
        RegistryEntryAddedCallback.event(Registries.BLOCK).register((rawId, id, block) -> {
            Identifier ingredient = pendingItems.remove(block);
            if (ingredient != null) {
                rescan();
            }
        });
        RegistryEntryAddedCallback.event(Registries.ITEM).register((rawId, id, item) -> rescan());
        // Take a snapshot because registering shelves also adds entries to the item registry.
        rescan();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            ServerLifecycleEvents.SERVER_STARTING.register(server -> logRegistration());
        }
    }

    private static Map<Identifier, List<Identifier>> slabRecipes = Map.of();
    private static boolean scanning;
    private static void rescan() {
        if (scanning) return;
        scanning = true;
        try { Registries.ITEM.stream().toList().forEach(item -> onItemAdded(Registries.ITEM.getId(item), item)); }
        finally { scanning = false; }
    }
    private static Identifier slabFor(Identifier plank, Identifier ingredient) {
        String base = plank.getPath().replaceFirst("(^|[/_])planks$", "");
        var candidates = new java.util.ArrayList<Identifier>(slabRecipes.getOrDefault(ingredient, List.of()));
        for (String candidate : List.of(base + "_slab", base + "_plank_slab", base + "_planks_slab", base + "/slab")) candidates.add(Identifier.of(plank.getNamespace(), candidate));
        for (Identifier itemId : candidates) {
            var sourceItem = Registries.ITEM.getOrEmpty(itemId).orElse(null);
            if (!(sourceItem instanceof BlockItem sourceBlockItem)) continue;
            Identifier id = Registries.BLOCK.getId(sourceBlockItem.getBlock());
            var block = Registries.BLOCK.getOrEmpty(id).orElse(null);
            if (block instanceof net.minecraft.block.SlabBlock && block.asItem() != net.minecraft.item.Items.AIR
                    && Registries.ITEM.containsId(Registries.ITEM.getId(block.asItem()))) return Registries.ITEM.getId(block.asItem());
        }
        return null;
    }
    private static void onItemAdded(Identifier id, Item item) {
        if (item instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof ChiseledBookshelfBlock)) {
            registerCandidate(blockItem.getBlock(), id);
        }
    }

    private static void registerCandidate(Block planks, Identifier ingredient) {
        // The defaulted registry's getId would report minecraft:air for an unregistered block.
        Identifier plankId = Registries.BLOCK.getKey(planks).map(RegistryKey::getValue).orElse(null);
        if (plankId == null) {
            pendingItems.putIfAbsent(planks, ingredient);
            return;
        }
        if (plankId.equals(Identifier.ofVanilla("oak_planks"))
                || (!PlankDiscovery.hasPlankName(plankId) && !blockTags.contains(plankId) && !itemTags.contains(ingredient))
                || processed.contains(plankId)) {
            return;
        }
        Identifier slab = slabFor(plankId, ingredient);
        if (slab == null) return;
        processed.add(plankId);
        Identifier id = ShelfIds.forPlanks(plankId);
        if (Registries.BLOCK.containsId(id) || Registries.ITEM.containsId(id)) {
            LogUtils.getLogger().warn("Skipping chiseled bookshelf for {}: {} is already registered", plankId, id);
            return;
        }
        // Never let a defaulted registry turn a missing ingredient into an air recipe.
        if (ingredient.equals(Identifier.ofVanilla("air")) || !Registries.ITEM.containsId(ingredient)) {
            LogUtils.getLogger().warn("Skipping chiseled bookshelf for {}: ingredient {} is not a registered usable item",
                    plankId, ingredient);
            return;
        }
        // Read mod-owned behaviour before constructing objects with intrusive registry holders.
        // A failed source read changes only properties, never the client/server table catalogue.
        var blockProperties = tableProperties(planks, plankId);
        Block block;
        String stage = "constructing the block";
        try {
            block = new VariantShelfBlock(blockProperties);
            stage = "constructing the item";
            BlockItem item = new BlockItem(block, new Item.Settings());
            // Prepare both objects before writing either registry. Constructor failures cannot
            // safely be skipped either: constructors may already have created intrusive holders.
            stage = "registering the block";
            Registry.register(Registries.BLOCK, id, block);
            stage = "registering the item";
            Registry.register(Registries.ITEM, id, item);
        } catch (RuntimeException exception) {
            throw new IllegalStateException("[Variant Chiseled Bookshelves] Failed " + stage + " for " + id
                    + " from plank " + plankId + " (ingredient " + ingredient + ")."
                    + " Loading must stop because registry changes cannot be safely rolled back.", exception);
        }
        registered.put(id, new Variant(id, plankId, ingredient, block, nonFlammable.contains(ingredient), slab));
        var accessor = (net.xanthian.variantchiseledbookshelves.mixin.BlockEntityTypeAccessor) (Object) net.minecraft.block.entity.BlockEntityType.CHISELED_BOOKSHELF;
        var supported = new HashSet<>(accessor.variantchiseledbookshelves$getBlocks());
        supported.add(block);
        accessor.variantchiseledbookshelves$setBlocks(Set.copyOf(supported));
        sourcePlanks.put(id, plankId);
        // Consumers always see an immutable, sorted catalogue, regardless of registration order.
        variants = List.copyOf(registered.values());
    }

    private static AbstractBlock.Settings tableProperties(Block planks, Identifier plankId) {
        // Vanilla table properties, starting without the oak shelf's lava-ignition flag.
        // That flag can only be enabled by the properties API, so copying oak cannot clear it.
        var properties = AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                .instrument(NoteBlockInstrument.BASS).strength(1.5F).sounds(BlockSoundGroup.CHISELED_BOOKSHELF);
        boolean ignitedByLava = false;
        try {
            ignitedByLava = planks.getDefaultState().isBurnable();
        } catch (RuntimeException exception) {
            LogUtils.getLogger().warn("Could not read lava ignition for {}; keeping its chiseled bookshelf lava-safe",
                    plankId, exception);
        }
        if (ignitedByLava) {
            properties.burnable();
        }
        return properties;
    }

    public static void logRegistration() {
        if (logged) {
            return;
        }
        logged = true;
        var counts = variants.stream().collect(Collectors.groupingBy(
                variant -> variant.planks().getNamespace(), TreeMap::new, Collectors.counting()));
        var sources = new StringJoiner(", ");
        // Count the shelves generated by this mod, including the ten vanilla wood variants.
        sources.add("vanilla (" + counts.getOrDefault("minecraft", 0L) + ")");
        counts.remove("minecraft");
        counts.forEach((namespace, count) -> {
            String name = FabricLoader.getInstance().getModContainer(namespace)
                    .map(mod -> mod.getMetadata().getName()).orElse(namespace);
            sources.add(name.replace('\r', ' ').replace('\n', ' ') + " (" + count + ")");
        });
        LogUtils.getLogger().info("[Variant Chiseled Bookshelves] loaded {} shelves from {}", variants.size(), sources);
    }
}
