package net.zhaiji.rpg.client.screen.tutorial;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TutorialSideButton extends Button {
    public int originalY;
    public TutorialPage page;
    public TutorialScrollBar scrollBar;
    public ResourceLocation texture;
    public int xOffset;
    public int yOffset;
    public int textureWidth;
    public int textureHeight;
    public int iconXOffset;
    public int iconYOffset;
    public int iconWidth;
    public int iconHeight;
    public int dotXOffset;
    public int dotYOffset;
    public int dotWidth;
    public int dotHeight;
    public int scissorMinX;
    public int scissorMinY;
    public int scissorMaxX;
    public int scissorMaxY;
    public int textColor;

    public TutorialSideButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration, TutorialPage page, TutorialScrollBar scrollBar, ResourceLocation texture, int xOffset, int yOffset, int textureWidth, int textureHeight, int iconXOffset, int iconYOffset, int iconWidth, int iconHeight, int dotXOffset, int dotYOffset, int dotWidth, int dotHeight, int scissorMinX, int scissorMinY, int scissorMaxX, int scissorMaxY, int textColor) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
        this.originalY = this.getY();
        this.page = page;
        this.scrollBar = scrollBar;
        this.texture = texture;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.iconXOffset = iconXOffset;
        this.iconYOffset = iconYOffset;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.dotXOffset = dotXOffset;
        this.dotYOffset = dotYOffset;
        this.dotWidth = dotWidth;
        this.dotHeight = dotHeight;
        this.scissorMinX = scissorMinX;
        this.scissorMinY = scissorMinY;
        this.scissorMaxX = scissorMaxX;
        this.scissorMaxY = scissorMaxY;
        this.textColor = textColor;
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

    public void renderScrollingString(GuiGraphics pGuiGraphics, Component pText, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        Font pFont = Minecraft.getInstance().font;
        int i = pFont.width(pText);
        int j = (pMinY + pMaxY - 9) / 2 + 1;
        int k = pMaxX - pMinX;
        if (i > k) {
            int l = i - k;
            double d0 = (double) Util.getMillis() / 1000.0D;
            double d1 = Math.max((double) l * 0.5D, 3.0D);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
            double d3 = Mth.lerp(d2, 0.0D, (double) l);
            pGuiGraphics.enableScissor(pMinX, pMinY, pMaxX, pMaxY);
            pGuiGraphics.drawString(pFont, pText, pMinX - (int) d3, j, pColor);
            pGuiGraphics.disableScissor();
        } else {
            pGuiGraphics.drawCenteredString(pFont, pText, (pMinX + pMaxX) / 2, j, pColor);
        }

    }

    @Override
    public boolean clicked(double pMouseX, double pMouseY) {
        return super.clicked(pMouseX, pMouseY)
                && this.scissorMinX < pMouseX && this.scissorMaxX > pMouseX
                && this.scissorMinY < pMouseY && this.scissorMaxY > pMouseY;
    }

    // render文字要改
    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.updateHovered(pMouseX, pMouseY);
        this.updateY();
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
        this.renderScrollingString(
                pGuiGraphics,
                this.getMessage(),
                this.getX() + 2,
                this.getY(),
                this.page.icon == null ? this.getX() + this.width - 2 : this.getX() + this.width - 2 - iconWidth,
                this.getY() + this.height,
                this.textColor
        );
        if (this.page.icon != null) {
            pGuiGraphics.blit(
                    this.texture,
                    this.getX() + this.width - this.iconWidth - 2,
                    this.getY() + 2,
                    this.iconXOffset,
                    this.iconYOffset,
                    this.iconWidth,
                    this.iconHeight,
                    this.textureWidth,
                    this.textureHeight
            );
            // 硬编码了，不要问我为什么（）
            pGuiGraphics.renderItem(
                    this.page.icon.getDefaultInstance(),
                    this.getX() + this.width - 16 - 2 - 1,
                    this.getY() + 2 + 1
            );
        }
        if (this.page.state == 0 || !this.page.award.isEmpty() && this.page.state == 1) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(0, 0, 200);
            pGuiGraphics.blit(
                    this.texture,
                    this.getX() + this.width - this.dotWidth - 2 - 1,
                    this.getY() + 2 + 1,
                    this.dotXOffset,
                    this.dotYOffset,
                    this.dotWidth,
                    this.dotHeight,
                    this.textureWidth,
                    this.textureHeight
            );
            pGuiGraphics.pose().popPose();
        }
    }

    public void updateHovered(int pMouseX,int pMouseY){
        if(this.active){
            this.isHovered = pMouseX >= this.getX()
                    && pMouseY >= this.getY()
                    && pMouseX < this.getX() + this.width
                    && pMouseY < this.getY() + this.height
                    && this.scissorMinX < pMouseX && this.scissorMaxX > pMouseX
                    && this.scissorMinY < pMouseY && this.scissorMaxY > pMouseY;
        }
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
        public int iconXOffset;
        public int iconYOffset;
        public int iconWidth;
        public int iconHeight;
        public int dotXOffset;
        public int dotYOffset;
        public int dotWidth;
        public int dotHeight;
        public int scissorMinX;
        public int scissorMinY;
        public int scissorMaxX;
        public int scissorMaxY;
        public int textColor = -1;

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
                    this.textureHeight,
                    this.iconXOffset,
                    this.iconYOffset,
                    this.iconWidth,
                    this.iconHeight,
                    this.dotXOffset,
                    this.dotYOffset,
                    this.dotWidth,
                    this.dotHeight,
                    this.scissorMinX,
                    this.scissorMinY,
                    this.scissorMaxX,
                    this.scissorMaxY,
                    this.textColor
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

        public Builder setIconXOffset(int iconXOffset) {
            this.iconXOffset = iconXOffset;
            return this;
        }

        public Builder setIconYOffset(int iconYOffset) {
            this.iconYOffset = iconYOffset;
            return this;
        }

        public Builder setIconWidth(int iconWidth) {
            this.iconWidth = iconWidth;
            return this;
        }

        public Builder setIconHeight(int iconHeight) {
            this.iconHeight = iconHeight;
            return this;
        }

        public Builder setDotXOffset(int dotXOffset) {
            this.dotXOffset = dotXOffset;
            return this;
        }

        public Builder setDotYOffset(int dotYOffset) {
            this.dotYOffset = dotYOffset;
            return this;
        }

        public Builder setDotWidth(int dotWidth) {
            this.dotWidth = dotWidth;
            return this;
        }

        public Builder setDotHeight(int dotHeight) {
            this.dotHeight = dotHeight;
            return this;
        }

        public Builder setScissorMinX(int scissorMinX) {
            this.scissorMinX = scissorMinX;
            return this;
        }

        public Builder setScissorMinY(int scissorMinY) {
            this.scissorMinY = scissorMinY;
            return this;
        }

        public Builder setScissorMaxX(int scissorMaxX) {
            this.scissorMaxX = scissorMaxX;
            return this;
        }

        public Builder setScissorMaxY(int scissorMaxY) {
            this.scissorMaxY = scissorMaxY;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }
    }
}
