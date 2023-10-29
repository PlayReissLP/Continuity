package me.pepperbell.continuity.client.mixinterface;

import net.minecraft.util.math.Direction;
public interface IDirectionMixin {
    Direction getClockwise(Direction.Axis axis);
    Direction getCounterClockWise(Direction.Axis axis);
}
