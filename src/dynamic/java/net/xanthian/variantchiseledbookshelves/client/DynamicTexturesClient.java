package net.xanthian.variantchiseledbookshelves.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.xanthian.variantchiseledbookshelves.block.DynamicShelves;
import net.xanthian.variantchiseledbookshelves.mixin.AtlasSourceManagerAccessor;
import net.xanthian.variantchiseledbookshelves.resource.GeneratedShelfPack;

public final class DynamicTexturesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AtlasSourceManagerAccessor.variantchiseledbookshelves$getSourceTypes()
                .put(GeneratedShelfPack.ATLAS_SOURCE_ID, ShelfSpriteSource.TYPE);
        SourceModTooltip.register();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> DynamicShelves.logRegistration());
    }
}
