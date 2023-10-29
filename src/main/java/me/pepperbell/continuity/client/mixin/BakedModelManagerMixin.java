package me.pepperbell.continuity.client.mixin;

import me.pepperbell.continuity.client.mixinterface.IBakedModelManagerMixin;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BakedModelManager.class)
public abstract class BakedModelManagerMixin implements IBakedModelManagerMixin {
    @Shadow
    @Final
    public abstract SpriteAtlasTexture method_24153(Identifier identifier);

    @Unique
    public SpriteAtlasTexture getAtlas(Identifier identifier) {
        return method_24153(identifier);
    }
}
