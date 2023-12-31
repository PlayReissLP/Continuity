package me.pepperbell.continuity.client.util;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import me.pepperbell.continuity.client.mixinterface.IBakedQuadMixin;
import org.jetbrains.annotations.ApiStatus;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

public final class SpriteCalculator {
	private static final BlockModels MODELS = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
	private static final Supplier<Random> RANDOM_SUPPLIER = new Supplier<Random>() {
		private final Random random = new Random();

		@Override
		public Random get() {
			// Use item rendering seed for consistency
			random.setSeed(42L);
			return random;
		}
	};

	private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
	private static final EnumMap<Direction, Map<BlockState, Sprite>> SPRITE_CACHE = new EnumMap<>(Direction.class);
	static {
		for (Direction direction : Direction.values()) {
			SPRITE_CACHE.put(direction, new Object2ObjectOpenHashMap<>());
		}
	}

	public static Sprite getSprite(BlockState state, Direction face) {
		Map<BlockState, Sprite> map = SPRITE_CACHE.get(face);
		Sprite sprite;
		LOCK.readLock().lock();
		try {
			sprite = map.get(state);
		} finally {
			LOCK.readLock().unlock();
		}
		if (sprite == null) {
			LOCK.writeLock().lock();
			try {
				sprite = calculateSprite(state, face, RANDOM_SUPPLIER);
				map.put(state, sprite);
			} finally {
				LOCK.writeLock().unlock();
			}
		}
		return sprite;
	}

	public static Sprite calculateSprite(BlockState state, Direction face, Supplier<Random> randomSupplier) {
		BakedModel model = MODELS.getModel(state);
		try {
			List<BakedQuad> quads = model.getQuads(state, face, randomSupplier.get());
			if (!quads.isEmpty()) {
				return ((IBakedQuadMixin)quads.get(0)).getSprite();
			}
			quads = model.getQuads(state, null, randomSupplier.get());
			if (!quads.isEmpty()) {
				int amount = quads.size();
				for (int i = 0; i < amount; i++) {
					BakedQuad quad = quads.get(i);
					if (quad.getFace() == face) {
						return ((IBakedQuadMixin)quad).getSprite();
					}
				}
			}
		} catch (Exception e) {
			//
		}
		return model.getSprite();
	}

	@ApiStatus.Internal
	public static void clearCache() {
		for (Map<BlockState, Sprite> map : SPRITE_CACHE.values()) {
			map.clear();
		}
	}
}
