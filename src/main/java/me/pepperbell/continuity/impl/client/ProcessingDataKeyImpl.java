package me.pepperbell.continuity.impl.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

import me.pepperbell.continuity.api.client.ProcessingDataKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ProcessingDataKeyImpl<T> implements ProcessingDataKey<T> {
	private final Identifier id;
	private final int rawId;
	private final Supplier<T> valueSupplier;
	private final Consumer<T> valueResetAction;

	public ProcessingDataKeyImpl(Identifier id, int rawId, Supplier<T> valueSupplier, Consumer<T> valueResetAction) {
		this.id = id;
		this.rawId = rawId;
		this.valueSupplier = valueSupplier;
		this.valueResetAction = valueResetAction;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public int getRawId() {
		return rawId;
	}

	@Override
	public Supplier<T> getValueSupplier() {
		return valueSupplier;
	}

	@Override
	@Nullable
	public Consumer<T> getValueResetAction() {
		return valueResetAction;
	}
}
