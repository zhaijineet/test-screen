package net.zhaiji.rpg.client.screen.tutorial;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TutorialSideButton extends Button {
    public int originalY;
    public TutorialPage page;
    public ResourceLocation texture;
    public int xOffset;
    public int yOffset;
    public int textureWidth;
    public int textureHeight;
    public TutorialScrollBar scrollBar;

    public TutorialSideButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration, TutorialPage page, TutorialScrollBar scrollBar, ResourceLocation texture, int xOffset, int yOffset, int textureWidth, int textureHeight) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
        this.originalY = this.getY();
        this.page = page;
        this.scrollBar = scrollBar;
        this.texture = texture;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getScrollOffset() {
        if (this.scrollBar.canScrollRange <= 0) {
            return 0;
        }
        return ((this.scrollBar.barY - this.scrollBar.minY) / this.scrollBar.canScrollRange) * this.scrollBar.hiddenRange;
    }

    // 限制点击区域
    @Override
    public boolean clicked(double pMouseX, double pMouseY) {
        return super.clicked(pMouseX, pMouseY);
    }

    // render要改isHovered的检测区域
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    // render文字还没写
    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        this.updateY();
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
    }

    public void updateY() {
        this.setY(this.originalY - (int) this.getScrollOffset());
    }

    public static class Builder {
        public int x;
        public int y;
        public int width;
        public int height;
        public Component message;
        public Button.OnPress onPress;
        public Tooltip tooltip;
        public CreateNarration createNarration = Button.DEFAULT_NARRATION;
        public TutorialPage page;
        public TutorialScrollBar scrollBar;
        public ResourceLocation texture;
        public int xOffset = 0;
        public int yOffset = 0;
        public int textureWidth = 256;
        public int textureHeight = 256;

        public TutorialSideButton build() {
            return new TutorialSideButton(
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.message,
                    this.onPress,
                    this.createNarration,
                    this.page,
                    this.scrollBar,
                    this.texture,
                    this.xOffset,
                    this.yOffset,
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

        public Builder setCreateNarration(CreateNarration createNarration ) {
            this.createNarration = createNarration;
            return this;
        }

        public Builder setPage(TutorialPage page) {
            this.page = page;
            return this;
        }

        public Builder setScrollBar(TutorialScrollBar scrollBar) {
            this.scrollBar = scrollBar;
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
