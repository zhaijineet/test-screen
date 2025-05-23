package net.zhaiji.rpg.client.screen.tutorial;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TutorialButton extends Button {
    public ResourceLocation texture;
    public int xOffset;
    public int yOffset;
    public int textureWidth;
    public int textureHeight;

    public TutorialButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration, ResourceLocation texture, int yOffset, int xOffset, int textureWidth, int textureHeight) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
        this.texture = texture;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public static Builder builder() {
        return new Builder();
    }

    // 没写文字渲染
    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        pGuiGraphics.blit(
                this.texture,
                this.getX(),
                this.getY(),
                this.xOffset,
                this.isHoveredOrFocused() ? this.yOffset + this.height : this.yOffset,
                this.width,
                this.height,
                this.textureWidth,
                this.textureHeight
        );
        //得改
        this.renderString(pGuiGraphics, Minecraft.getInstance().font, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public static class Builder {
        public int x;
        public int y;
        public int width;
        public int height;
        public Component message;
        public Button.OnPress onPress;
        public Tooltip tooltip;
        private CreateNarration createNarration = Button.DEFAULT_NARRATION;
        public ResourceLocation texture;
        public int xOffset = 0;
        public int yOffset = 0;
        public int textureWidth = 256;
        public int textureHeight = 256;

        public TutorialButton build() {
            return new TutorialButton(
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.message,
                    this.onPress,
                    this.createNarration,
                    this.texture,
                    this.yOffset,
                    this.xOffset,
                    this.textureWidth,
                    this.textureHeight
            );
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setMessage(Component message) {
            this.message = message;
            return this;
        }

        public Builder setOnPress(OnPress onPress) {
            this.onPress = onPress;
            return this;
        }

        public Builder setTooltip(Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder setCreateNarration(CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public Builder setTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder setXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public Builder setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public Builder setTextureWidth(int textureWidth) {
            this.textureWidth = textureWidth;
            return this;
        }

        public Builder setTextureHeight(int textureHeight) {
            this.textureHeight = textureHeight;
            return this;
        }
    }
}
