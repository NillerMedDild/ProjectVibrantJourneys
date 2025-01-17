package projectvibrantjourneys.init.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import projectvibrantjourneys.common.world.features.FallenTreeFeature;
import projectvibrantjourneys.core.PVJConfig;
import projectvibrantjourneys.core.ProjectVibrantJourneys;

@Mod.EventBusSubscriber(modid = ProjectVibrantJourneys.MOD_ID)
public class PVJWorldGen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void addBiomeFeatures(BiomeLoadingEvent event) {
		RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
		Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
		List<Supplier<ConfiguredFeature<?, ?>>> vegetalFeatures = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
		
		//GROUNDCOVER
		if(!PVJConfig.groundcoverBlacklist.get().contains(event.getName().toString())) {
			if(event.getCategory() == Biome.Category.NETHER) {
				if(PVJConfig.charredBones.get())
					event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> PVJConfiguredFeatures.charred_bones);
			} else if(hasType(biomeTypes, Type.OVERWORLD)) {
				if(hasType(biomeTypes, Type.FOREST, Type.PLAINS)) {
					if(PVJConfig.twigs.get())
						vegetalFeatures.add(() -> PVJConfiguredFeatures.twigs);
					
					if(PVJConfig.fallenLeaves.get())
						vegetalFeatures.add(() -> PVJConfiguredFeatures.fallen_leaves);
				}

				if(hasType(biomeTypes, Type.CONIFEROUS) && PVJConfig.pinecones.get()) {
					vegetalFeatures.add(() -> PVJConfiguredFeatures.pinecones);
				}
				if(hasType(biomeTypes, Type.OCEAN, Type.BEACH) && PVJConfig.seashells.get()) {
					vegetalFeatures.add(() -> PVJConfiguredFeatures.seashells);
					vegetalFeatures.add(() -> PVJConfiguredFeatures.ocean_seashells);
				}
				
				if(!hasType(biomeTypes, Type.MUSHROOM) && PVJConfig.rocks.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.rocks);
				
				if(!hasType(biomeTypes, Type.MUSHROOM) && PVJConfig.bones.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.bones);
			
				if(hasType(biomeTypes, Type.SNOWY) && PVJConfig.iceChunks.get()) {
					vegetalFeatures.add(() -> PVJConfiguredFeatures.ice_chunks);
				}
			}
		}
		
		if(event.getCategory() == Biome.Category.NETHER) {
			if(PVJConfig.glowcap.get())
				event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> PVJConfiguredFeatures.glowcap);
			
			if(biome == Biomes.NETHER_WASTES || biome == Biomes.CRIMSON_FOREST || biome == Biomes.WARPED_FOREST) {
				event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> PVJConfiguredFeatures.cindercane);
			}
			
			if(PVJConfig.netherNettles.get()) {
				if(biome == Biomes.CRIMSON_FOREST)
					event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> PVJConfiguredFeatures.crimson_nettle);
				if(biome == Biomes.WARPED_FOREST)
					event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(() -> PVJConfiguredFeatures.warped_nettle);
			}

		} else if(hasType(biomeTypes, Type.OVERWORLD) && event.getCategory() != Biome.Category.THEEND && event.getCategory() != Biome.Category.MUSHROOM) {
			//plants
			if(hasType(biomeTypes, Type.OCEAN, Type.BEACH) && !hasType(biomeTypes, Type.SNOWY) && PVJConfig.seaOats.get())
				vegetalFeatures.add(() -> PVJConfiguredFeatures.sea_oats);
			if(!hasType(biomeTypes, Type.OCEAN, Type.BEACH) && event.getCategory() != Biome.Category.DESERT && PVJConfig.cattails.get()) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.cattails);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.water_cattails);
			}
			
			if(hasType(biomeTypes, Type.PLAINS) && PVJConfig.bushes.get())
				vegetalFeatures.add(() -> PVJConfiguredFeatures.bushes);
			
			if(PVJConfig.barkMushrooms.get())
				vegetalFeatures.add(() -> PVJConfiguredFeatures.bark_mushrooms);
			
			if(PVJConfig.shortGrass.get() && !PVJConfig.groundcoverBlacklist.get().contains(event.getName().toString()))
				vegetalFeatures.add(() -> PVJConfiguredFeatures.short_grass);
			
			if(hasType(biomeTypes, Type.OCEAN, Type.BEACH) && !hasType(biomeTypes, Type.SNOWY) && PVJConfig.beachGrass.get())
				vegetalFeatures.add(() -> PVJConfiguredFeatures.beach_grass);
			
			//other
			if(PVJConfig.cobwebs.get())
				vegetalFeatures.add(() -> PVJConfiguredFeatures.cobwebs);
			
			if(event.getCategory() != Biome.Category.DESERT
					&& event.getCategory() != Biome.Category.MESA
					&& event.getCategory() != Biome.Category.RIVER
					&& event.getCategory() != Biome.Category.OCEAN
					&& PVJConfig.moreSeagrass.get()) {
				vegetalFeatures.add(() -> Features.SEAGRASS_RIVER);
			}
			
			if(event.getCategory() == Biome.Category.RIVER && PVJConfig.moreGrassInRivers.get()) {
				vegetalFeatures.add(() -> Features.PATCH_GRASS_PLAIN);
			}

			//PVJ BIOMES
			if(biome == PVJBiomes.Keys.OVERGROWN_SPIRES) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.overgrown_spires_vegetation);
				if(PVJConfig.enablePalmTrees.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.vanilla_palm);
			} else if(biome == PVJBiomes.Keys.REDWOODS || biome == PVJBiomes.Keys.REDWOOD_PEAKS || biome == PVJBiomes.Keys.SNOWY_REDWOODS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.redwood_forest_vegetation);
			} else if(biome == PVJBiomes.Keys.BOREAL_FOREST || biome == PVJBiomes.Keys.SNOWY_BOREAL_FOREST || biome == PVJBiomes.Keys.ALPINE_HEIGHTS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.boreal_forest_vegetation);
			} else if(biome == PVJBiomes.Keys.PINE_MEADOWS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.pine_meadows_vegetation);
			} else if(biome == PVJBiomes.Keys.VERDANT_SANDS) {
				if(PVJConfig.beachGrass.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.beach_grass);
				if(PVJConfig.cattails.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.cattails);
			} else if(biome == PVJBiomes.Keys.BOREAL_PLATEAU) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.boreal_plateau_vegetation);
			} else if(biome == PVJBiomes.Keys.ASPEN_GROVE || biome == PVJBiomes.Keys.ASPEN_GROVE_HILLS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.aspen_grove_vegetation);
				
				if(PVJConfig.fallenLeaves.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.autumn_floor_foliage);
			} else if(biome == PVJBiomes.Keys.MANGROVE_MARSH) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.mangrove_marsh_vegetation);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.marsh_tall_grass);
			} else if(biome == PVJBiomes.Keys.WILLOW_WETLANDS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.willow_wetlands_vegetation);
			} else if(biome == PVJBiomes.Keys.BAOBAB_FIELDS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.baobab_fields_vegetation);
			} else if(biome == PVJBiomes.Keys.PRAIRIE) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.prairie_grass);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.prairie_vegetation);
			} else if(biome == PVJBiomes.Keys.BLOSSOMING_FIELDS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.blossoming_fields_vegetation);
				
				if(PVJConfig.fallenLeaves.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.sakura_floor_foliage);
			} else if(biome == PVJBiomes.Keys.AUTUMNAL_CONIFEROUS_FOREST) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.autumnnal_coniferous_forest_vegetation);
			} else if(biome == PVJBiomes.Keys.CRIMSON_THICKET) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.crimson_thicket_vegetation);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.crimson_thicket_floor_foliage);
			} else if (biome == PVJBiomes.Keys.DESERT_SHRUBLAND) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.desert_shrubland_vegetation);
				event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(() -> PVJConfiguredFeatures.desert_rock);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.dry_grass);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.desert_sage);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.blooming_desert_agave);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.desert_agave);
			} else if (biome == PVJBiomes.Keys.RED_ROCK_VALLEY) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.desert_shrubland_vegetation);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.blooming_desert_agave);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.desert_agave);
				
				if(PVJConfig.enableJuniperTrees.get())
					vegetalFeatures.add(() -> PVJConfiguredFeatures.vanilla_juniper);
			} else if (biome == PVJBiomes.Keys.TROPICAL_BEACH) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.tropical_beach_vegetation);
			} else if (biome == PVJBiomes.Keys.CRYSTAL_LAKES) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.crystal_lakes_vegetation);
			} else if (biome == PVJBiomes.Keys.WINDSWEPT_CLIFFS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.windswept_cliffs_vegetation);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.cliff_rocks);
			} else if (biome == PVJBiomes.Keys.MEADOWS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.lots_of_grass);
				vegetalFeatures.add(() -> PVJConfiguredFeatures.meadows_vegetation);
			} else if (biome == PVJBiomes.Keys.FLOODED_WOODLANDS) {
				vegetalFeatures.add(() -> PVJConfiguredFeatures.flooded_forest_extra_vegetation);
			} else if (biome == PVJBiomes.Keys.STONY_FIELDS) {
				event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(() -> PVJConfiguredFeatures.boulder);
				event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(() -> PVJConfiguredFeatures.stone_spike);
			}
			
			//PVJ TREES --> VANILLA BIOMES
			if(PVJConfig.enableJuniperTrees.get()) {
				if(biome == Biomes.BADLANDS || biome == Biomes.BADLANDS_PLATEAU || biome == Biomes.ERODED_BADLANDS || biome == Biomes.MODIFIED_BADLANDS_PLATEAU) {
					vegetalFeatures.add(() -> PVJConfiguredFeatures.vanilla_juniper);
				}
			}

			if(PVJConfig.enablePalmTrees.get()) {
				if(biome == Biomes.BEACH || biome == Biomes.JUNGLE || biome == Biomes.JUNGLE_HILLS || biome == Biomes.MODIFIED_JUNGLE) {
					vegetalFeatures.add(() -> PVJConfiguredFeatures.vanilla_palm);
				}
			}
		}
	}
	
	/*
	 * EventPriority is low to ensure that feature lists of modded biomes are complete
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void addFallenTreeFeatures(BiomeLoadingEvent event) {
		if(!PVJConfig.fallenTrees.get())
			return;
		
		//just gonna wrap this in a try catch to really make sure things don't go fubar
		try {
//			ProjectVibrantJourneys.LOGGER.debug(event.getName().toString());
			List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION);
			RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
			Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
			List<ConfiguredFeature<?, ?>> trees = new ArrayList<ConfiguredFeature<?, ?>>();
			for(Supplier<ConfiguredFeature<?, ?>> cf : features)
				getFeatureNames(cf.get(), trees);
			
			Random rand = new Random();
			
			for(ConfiguredFeature<?, ?> pair : trees) {
				if(pair.config() instanceof BaseTreeFeatureConfig) {
					try {
						Block block = ((BaseTreeFeatureConfig)pair.config()).trunkProvider.getState(rand, null).getBlock();
						if(block instanceof RotatedPillarBlock) {
//							ProjectVibrantJourneys.LOGGER.debug("----> " + block.getRegistryName());
							FallenTreeFeature.LOGS.add(new Pair<String, Block>(event.getName().toString(), block));
						}
					} catch(Exception e) {}
				}
			}
			if(trees.size() > 0) {
				features.add(() -> PVJConfiguredFeatures.fallen_tree);
			}
		} catch(Exception e) {
//			ProjectVibrantJourneys.LOGGER.debug("Caught error when trying to add fallen trees");
//			e.printStackTrace();
		}
	
	}
	
	//used to help find tree features hidden in decorated configured features
	//most biomes group their trees in random selectors, these features are added to a list
	public static String getFeatureNames(ConfiguredFeature<?, ?> cf, List<ConfiguredFeature<?, ?>> list) {
		if(Feature.RANDOM_SELECTOR.getRegistryName().equals(cf.feature().getRegistryName())) {
			((MultipleRandomFeatureConfig)cf.config()).features.forEach((s) -> list.add(s.feature.get()));
		} else if(cf.config() instanceof DecoratedFeatureConfig) {
			ConfiguredFeature<?, ?> feature = ((DecoratedFeatureConfig)cf.config()).feature.get();
			
			if(feature.config() instanceof BaseTreeFeatureConfig) {
				list.add(feature);
			} else
				return getFeatureNames(feature, list);
		}
		return cf.feature().getRegistryName().toString();
	}	
	
	private static boolean hasType(Set<BiomeDictionary.Type> list, BiomeDictionary.Type...types) {
		for(BiomeDictionary.Type t : types) {
			if(list.contains(t)) return true;
		}
		return false;
	}
}
