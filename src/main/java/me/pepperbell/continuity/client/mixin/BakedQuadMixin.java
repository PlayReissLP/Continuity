package me.pepperbell.continuity.client.mixin;

import me.pepperbell.continuity.client.mixinterface.IBakedQuadMixin;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BakedQuad.class)
public abstract class BakedQuadMixin implements IBakedQuadMixin {
    @Shadow
    @Final
    protected Sprite sprite;

    @Unique
    public Sprite getSprite() {
        return this.sprite;
    }
}
