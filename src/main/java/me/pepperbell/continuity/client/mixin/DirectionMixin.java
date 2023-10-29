package me.pepperbell.continuity.client.mixin;
import me.pepperbell.continuity.client.mixinterface.IDirectionMixin;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static net.minecraft.util.math.Direction.*;

@Mixin(Direction.class)
public abstract class DirectionMixin implements IDirectionMixin {
    @Unique
    private final Direction _INSTANCE = (Direction)(Object)this;

    @Unique
    public Direction getClockwise(Direction.Axis axis) {
        switch(axis) {
            case X:
                if (_INSTANCE != Direction.WEST && _INSTANCE != Direction.EAST) {
                    return this.getClockWiseX();
                }

                return _INSTANCE;
            case Z:
                if (_INSTANCE != Direction.NORTH && _INSTANCE != Direction.SOUTH) {
                    return this.getClockWiseZ();
                }

                return _INSTANCE;
            case Y:
                if (_INSTANCE != Direction.UP && _INSTANCE != Direction.DOWN) {
                    return this.getClockWise();
                }

                return _INSTANCE;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    @Unique
    private Direction getClockWiseX() {
        switch(_INSTANCE) {
            case DOWN:
                return Direction.SOUTH;
            case UP:
                return Direction.NORTH;
            case NORTH:
                return Direction.DOWN;
            case SOUTH:
                return Direction.UP;
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + _INSTANCE);
        }
    }

    @Unique
    private Direction getClockWiseZ() {
        switch(_INSTANCE) {
            case DOWN:
                return Direction.WEST;
            case UP:
                return Direction.EAST;
            case NORTH:
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + _INSTANCE);
            case WEST:
                return Direction.UP;
            case EAST:
                return Direction.DOWN;
        }
    }

    @Unique
    private Direction getClockWise() {
        switch(_INSTANCE) {
            case NORTH:
                return Direction.EAST;
            case SOUTH:
                return Direction.WEST;
            case WEST:
                return Direction.NORTH;
            case EAST:
                return Direction.SOUTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + _INSTANCE);
        }
    }

    @Unique
    public Direction getCounterClockWise(Direction.Axis axis) {
        switch(axis) {
            case X:
                if (_INSTANCE != Direction.WEST && _INSTANCE != Direction.EAST) {
                    return this.getCounterClockWiseX();
                }

                return _INSTANCE;
            case Z:
                if (_INSTANCE != Direction.NORTH && _INSTANCE != Direction.SOUTH) {
                    return this.getCounterClockWiseZ();
                }

                return _INSTANCE;
            case Y:
                if (_INSTANCE != Direction.UP && _INSTANCE != Direction.DOWN) {
                    return this.getCounterClockWise();
                }

                return _INSTANCE;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    @Unique
    private Direction getCounterClockWiseX() {
        switch(_INSTANCE) {
            case DOWN:
                return Direction.NORTH;
            case UP:
                return Direction.SOUTH;
            case NORTH:
                return Direction.UP;
            case SOUTH:
                return Direction.DOWN;
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + _INSTANCE);
        }
    }

    @Unique
    private Direction getCounterClockWiseZ() {
        switch(_INSTANCE) {
            case DOWN:
                return Direction.EAST;
            case UP:
                return Direction.WEST;
            case NORTH:
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + _INSTANCE);
            case WEST:
                return Direction.DOWN;
            case EAST:
                return Direction.UP;
        }
    }

    @Unique
    private Direction getCounterClockWise() {
        switch(_INSTANCE) {
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            case EAST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + _INSTANCE);
        }
    }
}
