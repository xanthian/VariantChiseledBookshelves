package net.xanthian.variantchiseledbookshelves;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.xanthian.variantchiseledbookshelves.block.Vanilla;
import net.xanthian.variantchiseledbookshelves.item.ModItems;
import net.xanthian.variantchiseledbookshelves.util.ModCreativeTabs;

@Mod(Initialise.MOD_ID)
@Mod.EventBusSubscriber
public class Initialise {

    public static final String MOD_ID = "variantchiseledbookshelves";

    public Initialise() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        Vanilla.BLOCKS.register(modEventBus);

        ModItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}