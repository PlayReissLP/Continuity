package me.pepperbell.continuity.client.util.biome;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

public final class BiomeRetriever {
	private static final Provider PROVIDER = getProvider();

	private static Provider getProvider() {
		ClassLoader classLoader = BiomeRetriever.class.getClassLoader();

		if (FabricLoader.getInstance().isModLoaded("sodium")) {
			try {
				Class<?> worldSliceClass = Class.forName("me.jellysquid.mods.sodium.client.world.WorldSlice", false, classLoader);
				worldSliceClass.getMethod("getBiome", int.class, int.class, int.class);
				return BiomeRetriever::getBiomeByWorldSlice;
			} catch (ClassNotFoundException | NoSuchMethodException e) {
				//
			}
			return BiomeRetriever::getBiomeByWorldView;
		}

		if (ArrayUtils.contains(ChunkRendererRegion.class.getInterfaces(), BiomeView.class)) {
			return BiomeRetriever::getBiomeByExtension;
		}
		return BiomeRetriever::getBiomeByWorldView;
	}

	@Nullable
	public static Biome getBiome(BlockRenderView blockView, BlockPos pos) {
		return PROVIDER.getBiome(blockView, pos);
	}

	@ApiStatus.Internal
	public static void init() {
	}

	private static Biome getBiomeByWorldView(BlockRenderView blockView, BlockPos pos) {
		if (blockView instanceof WorldView) {
			return ((WorldView)blockView).getBiome(pos);
		}
		return null;
	}

	private static Biome getBiomeByExtension(BlockRenderView blockView, BlockPos pos) {
		if (blockView instanceof BiomeView) {
			return ((BiomeView)blockView).getBiome(pos);
		}
		return getBiomeByWorldView(blockView, pos);
	}

	// Sodium
	private static Biome getBiomeByWorldSlice(BlockRenderView blockView, BlockPos pos) {
		if (blockView instanceof WorldSlice) {
			return ((WorldSlice)blockView).getBiome(pos.getX(), pos.getY(), pos.getZ());
		}
		return getBiomeByWorldView(blockView, pos);
	}

	private interface Provider {
		Biome getBiome(BlockRenderView blockView, BlockPos pos);
	}
}
