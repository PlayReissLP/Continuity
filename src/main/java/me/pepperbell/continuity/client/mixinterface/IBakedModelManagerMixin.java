package me.pepperbell.continuity.client.mixinterface;

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public interface IBakedModelManagerMixin {
    SpriteAtlasTexture getAtlas(Identifier identifier);
}
