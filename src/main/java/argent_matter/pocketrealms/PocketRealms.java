package argent_matter.pocketrealms;

import argent_matter.pocketrealms.api.registries.PocketRealmsRegistries;
import argent_matter.pocketrealms.common.data.PocketRealmsBlocks;
import argent_matter.pocketrealms.common.data.PocketRealmsItems;
import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(PocketRealms.MOD_ID)
public class PocketRealms {
    public static final String
    MOD_ID = "pocketrealms";

    public static final Logger LOGGER = LogManager.getLogger();

    public PocketRealms() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        PocketRealmsItems.init();
        PocketRealmsBlocks.init();
        /*
        PocketRealmsDatagen.init();
         */

        registerEventListeners(modEventBus);
    }


    protected void registerEventListeners(IEventBus bus) {
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public void kaboom(ExplosionEvent.Detonate event) {
    }
}
