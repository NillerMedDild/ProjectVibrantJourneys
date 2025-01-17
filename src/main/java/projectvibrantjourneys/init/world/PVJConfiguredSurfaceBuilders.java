package projectvibrantjourneys.init.world;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import projectvibrantjourneys.core.ProjectVibrantJourneys;

public class PVJConfiguredSurfaceBuilders {

	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> VERDANT_SANDS = register("verdant_sands", PVJSurfaceBuilders.VERDANT_SANDS.configured(SurfaceBuilder.CONFIG_OCEAN_SAND));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> REDWOODS = register("redwoods", PVJSurfaceBuilders.REDWOODS.configured(SurfaceBuilder.CONFIG_GRASS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> ALPINE_HEIGHTS = register("alpine_heights", PVJSurfaceBuilders.ALPINE_HEIGHTS.configured(SurfaceBuilder.CONFIG_GRASS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> GRAVEL_SHORE = register("gravel_shore", SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRAVEL));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> RED_ROCK_VALLEY = register("red_rock_valley", PVJSurfaceBuilders.RED_ROCK_VALLEY.configured(SurfaceBuilder.CONFIG_BADLANDS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> PINE_MEADOWS = register("pine_meadows", PVJSurfaceBuilders.SCATTERED_NOISE_STRIP.configured(PVJSurfaceBuilders.CONFIG_DIORITE));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> WINDSWEPT_CLIFFS = register("windswept_cliffs", PVJSurfaceBuilders.WINDSWEPT_CLIFFS.configured(SurfaceBuilder.CONFIG_GRASS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> MEADOWS = register("meadows", PVJSurfaceBuilders.SCATTERED_NOISE_STRIP.configured(SurfaceBuilder.CONFIG_COARSE_DIRT));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> STONY_FIELDS = register("stony_fields", PVJSurfaceBuilders.STONY_FIELDS.configured(SurfaceBuilder.CONFIG_GRASS));
	
	private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name,
			ConfiguredSurfaceBuilder<SC> builder) {
		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,
				new ResourceLocation(ProjectVibrantJourneys.MOD_ID, name), builder);
	}
}
