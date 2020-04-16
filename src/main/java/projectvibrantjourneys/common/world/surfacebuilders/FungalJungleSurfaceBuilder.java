package projectvibrantjourneys.common.world.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class FungalJungleSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
	public FungalJungleSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config) {
		super(config);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		double d0 = Biome.INFO_NOISE.noiseAt((double) x * 0.25D, (double) z * 0.25D, false);
		if (d0 > 0.2D) {
			int i = x & 15;
			int j = z & 15;
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for (int k = startHeight; k >= 0; --k) {
				blockpos$mutable.setPos(i, k, j);
				if (!chunkIn.getBlockState(blockpos$mutable).isAir()) {
					if (k == 62 && chunkIn.getBlockState(blockpos$mutable).getBlock() != defaultFluid.getBlock()) {
						chunkIn.setBlockState(blockpos$mutable, defaultFluid, false);
					}
					break;
				}
			}
		}

		SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
				defaultFluid, seaLevel, seed, config);
	}
}