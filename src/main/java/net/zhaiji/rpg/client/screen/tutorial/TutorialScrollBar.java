package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class TutorialScrollBar {
    public ResourceLocation texture;

    public int barX;
    public double barY;
    public int railX;
    public int railY;

    public double minY;
    public double maxY;
    public double hiddenRange;
    public double canScrollRange;

    public int barXOffset;
    public int barYOffset;
    public int railXOffset;
    public int railYOffset;

    public int barWidth;
    public int barHeight;
    public int railWidth;
    public int railHeight;

    public int textureWidth = 256;
    public int textureHeight = 256;

    public static TutorialScrollBar create() {
        return new TutorialScrollBar();
    }

    public boolean contains(double pMouseX, double pMouseY) {
        return pMouseX > this.barX && pMouseX < (this.barX + this.barWidth)
                && pMouseY > this.barY && pMouseY < (this.barY + this.barHeight);
    }

    public void mouseDragged(double pDragY) {
        this.barY = Math.max(this.minY, Math.min(this.maxY, this.barY + pDragY));
    }

    public void mouseScrolled(double pDelta) {
        this.barY = Math.max(this.minY, Math.min(this.maxY, this.barY + pDelta));
    }

    public void render(GuiGraphics pGuiGraphics) {
        pGuiGraphics.blit(
                this.texture,
                this.railX,
                this.railY,
                this.railXOffset,
                this.railYOffset,
                this.railWidth,
                this.railHeight,
                this.textureWidth,
                this.textureHeight
        );
        if (this.barHeight != this.railHeight) {
            pGuiGraphics.blit(
                    this.texture,
                    this.barX,
                    (int) this.barY,
                    this.barXOffset,
                    this.barYOffset,
                    this.barWidth,
                    1,
                    this.textureWidth,
                    this.textureHeight
            );
            pGuiGraphics.blit(
                    this.texture,
                    this.barX,
                    (int) this.barY + 1,
                    this.barXOffset,
                    this.barYOffset + 1,
                    this.barWidth,
                    this.barHeight - 1 - 1, // 顶和底，所以减两次
                    this.textureWidth,
                    this.textureHeight
            );
            pGuiGraphics.blit(
                    this.texture,
                    this.barX,
                    (int) (this.barY + this.barHeight - 1),
                    this.barXOffset,
                    this.barYOffset + this.railHeight - 1,
                    this.barWidth,
                    1,
                    this.textureWidth,
                    this.textureHeight
            );
        } else {
            pGuiGraphics.blit(
                    this.texture,
                    this.barX,
                    (int) this.barY,
                    this.barXOffset,
                    this.barYOffset,
                    this.barWidth,
                    this.barHeight,
                    this.textureWidth,
                    this.textureHeight
            );
        }
    }

    public TutorialScrollBar setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public TutorialScrollBar setBarX(int barX) {
        this.barX = barX;
        return this;
    }

    public TutorialScrollBar setBarY(double barY) {
        this.barY = barY;
        return this;
    }

    public TutorialScrollBar setRailX(int railX) {
        this.railX = railX;
        return this;
    }

    public TutorialScrollBar setRailY(int railY) {
        this.railY = railY;
        return this;
    }

    public TutorialScrollBar setMinY(double minY) {
        this.minY = minY;
        return this;
    }

    public TutorialScrollBar setMaxY(double maxY) {
        this.maxY = maxY;
        return this;
    }

    public TutorialScrollBar setBarXOffset(int barXOffset) {
        this.barXOffset = barXOffset;
        return this;
    }

    public TutorialScrollBar setBarYOffset(int barYOffset) {
        this.barYOffset = barYOffset;
        return this;
    }

    public TutorialScrollBar setRailXOffset(int railXOffset) {
        this.railXOffset = railXOffset;
        return this;
    }

    public TutorialScrollBar setRailYOffset(int railYOffset) {
        this.railYOffset = railYOffset;
        return this;
    }

    public TutorialScrollBar setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        return this;
    }

    public TutorialScrollBar setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        return this;
    }

    public TutorialScrollBar setRailWidth(int railWidth) {
        this.railWidth = railWidth;
        return this;
    }

    public TutorialScrollBar setRailHeight(int railHeight) {
        this.railHeight = railHeight;
        return this;
    }

    public TutorialScrollBar setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
        return this;
    }

    public TutorialScrollBar setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
        return this;
    }
}
