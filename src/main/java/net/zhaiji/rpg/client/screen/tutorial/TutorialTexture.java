package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TutorialTexture {
    public ResourceLocation texture;

    public int x = 0;
    public int y = 0;

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public int textureWidth = 256;
    public int textureHeight = 256;

    public static TutorialTexture create() {
        return new TutorialTexture();
    }

    public TutorialTexture setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public TutorialTexture setX(int x) {
        this.x = x;
        return this;
    }

    public TutorialTexture setY(int y) {
        this.y = y;
        return this;
    }

    public TutorialTexture setXOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public TutorialTexture setYOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public TutorialTexture setWidth(int width) {
        this.width = width;
        return this;
    }

    public TutorialTexture setHeight(int height) {
        this.height = height;
        return this;
    }

    public TutorialTexture setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
        return this;
    }

    public TutorialTexture setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
        return this;
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(
                this.texture,
                this.x,
                this.y,
                this.xOffset,
                this.yOffset,
                this.width,
                this.height,
                this.textureWidth,
                this.textureHeight
        );
    }
}
