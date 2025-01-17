package projectvibrantjourneys.common.biomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class CrystalLakesBiome {
	
	public static Biome makeCrystalLakesBiome() {
		MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();
		
		mobSpawnInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4));
		mobSpawnInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
		mobSpawnInfo.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
		mobSpawnInfo.setPlayerCanSpawn();
		DefaultBiomeFeatures.farmAnimals(mobSpawnInfo);
		DefaultBiomeFeatures.commonSpawns(mobSpawnInfo);
		
		BiomeGenerationSettings.Builder biomeGenBuilder = (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenBuilder);
		biomeGenBuilder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
		biomeGenBuilder.addStructureStart(StructureFeatures.VILLAGE_TAIGA);
		
		DefaultBiomeFeatures.addDefaultCarvers(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultLakes(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultMonsterRoom(biomeGenBuilder);
		DefaultBiomeFeatures.addMossyStoneBlock(biomeGenBuilder);
		DefaultBiomeFeatures.addFerns(biomeGenBuilder);
		DefaultBiomeFeatures.addExtraEmeralds(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultOres(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultSoftDisks(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultFlowers(biomeGenBuilder);
		DefaultBiomeFeatures.addGiantTaigaVegetation(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultMushrooms(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultExtraVegetation(biomeGenBuilder);
		DefaultBiomeFeatures.addDefaultSprings(biomeGenBuilder);
		DefaultBiomeFeatures.addSparseBerryBushes(biomeGenBuilder);
		DefaultBiomeFeatures.addSurfaceFreezing(biomeGenBuilder);
		DefaultBiomeFeatures.addPlainVegetation(biomeGenBuilder);
		biomeGenBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
	      
		return (new Biome.Builder()).precipitation(RainType.RAIN)
				.biomeCategory(Biome.Category.TAIGA).depth(-0.3F).scale(0.1F).temperature(0.23F)
				.downfall(0.8F)
				.specialEffects((new BiomeAmbience.Builder()).waterColor(4445678)
						.waterFogColor(329011).fogColor(12638463)
						.grassColorOverride(0x5bbf39).foliageColorOverride(0x00994d)
						.skyColor(BiomeUtils.getSkyColorWithTemperatureModifier(0.25F))
						.ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build())
				.mobSpawnSettings(mobSpawnInfo.build())
				.generationSettings(biomeGenBuilder.build()).build();
	}
}
