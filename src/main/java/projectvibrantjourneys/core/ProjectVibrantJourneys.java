package projectvibrantjourneys.core;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import projectvibrantjourneys.common.world.FeatureManager;
import projectvibrantjourneys.init.PVJBlocks;
import projectvibrantjourneys.init.PVJEvents;
import projectvibrantjourneys.init.PVJVanillaIntegration;

@Mod(ProjectVibrantJourneys.MOD_ID)
public class ProjectVibrantJourneys {
	
	public static final String MOD_ID = "projectvibrantjourneys";
	public static final Logger LOGGER = LogManager.getLogManager().getLogger(MOD_ID);
	
	public ProjectVibrantJourneys() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PVJConfig.COMMON_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PVJConfig.CLIENT_CONFIG);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
		
		PVJConfig.loadConfig(PVJConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("projectvibrantjourneys-common.toml"));
		PVJConfig.loadConfig(PVJConfig.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("projectvibrantjourneys-client.toml"));
	}
	
	private void commonSetup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new PVJEvents());
		FeatureManager.init();
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		PVJBlocks.registerRenderers();
		PVJBlocks.registerColors();
	}
	
	private void loadComplete(FMLLoadCompleteEvent event) {
		FeatureManager.init();
		PVJVanillaIntegration.init();
	}
}
