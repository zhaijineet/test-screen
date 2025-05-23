package net.zhaiji.rpg.client.screen.tutorial;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TutorialTabButton extends Button {
    public ResourceLocation texture;
    public TutorialScreen.Categories category;
    public Item icon;
    public int xOffset;
    public int yOffset;
    public int textureWidth;
    public int textureHeight;
    public int activeX;
    public int activeY;
    public int activeWidth;
    public int activeHeight;
    public int activeXOffset;
    public int activeYOffset;

    protected TutorialTabButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration, ResourceLocation texture, TutorialScreen.Categories category, Item icon, int xOffset, int yOffset, int textureWidth, int textureHeight, int activeX, int activeY, int activeWidth, int activeHeight, int activeXOffset, int activeYOffset) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
        this.texture = texture;
        this.category = category;
        this.icon = icon;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.activeX = activeX;
        this.activeY = activeY;
        this.activeWidth = activeWidth;
        this.activeHeight = activeHeight;
        this.activeXOffset = activeXOffset;
        this.activeYOffset = activeYOffset;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean clicked(double pMouseX, double pMouseY) {
        if(this.isHoveredOrFocused()){
            return this.active && this.visible && pMouseX >= (double)this.activeX && pMouseY >= (double)this.activeY && pMouseX < (double)(this.activeX + this.activeWidth) && pMouseY < (double)(activeY + this.activeHeight);
        }else {
            return this.active && this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height);
        }
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isHoveredOrFocused()) {
            pGuiGraphics.blit(
                    this.texture,
                    this.activeX,
                    this.activeY,
                    this.activeXOffset,
                    this.activeYOffset,
                    this.activeWidth,
                    this.activeHeight,
                    this.textureWidth,
                    this.textureHeight
            );
            // 异形按钮，我这里直接硬编码写了
            int iconX = this.activeX + (this.activeWidth - 16) / 2 + 1;
            int iconY = this.getY() + (this.height - 16) / 2;
            pGuiGraphics.renderItem(
                    this.icon.getDefaultInstance(),
                    iconX,
                    iconY
            );
        } else {
            pGuiGraphics.blit(
                    this.texture,
                    this.getX(),
                    this.getY(),
                    this.xOffset,
                    this.yOffset,
                    this.width,
                    this.height,
                    this.textureWidth,
                    this.textureHeight
            );
            // 异形按钮，我这里直接硬编码写了
            int iconX = this.getX() + (this.width - 16 + 4) / 2;
            int iconY = this.getY() + (this.height - 16) / 2;
            pGuiGraphics.renderItem(
                    this.icon.getDefaultInstance(),
                    iconX,
                    iconY
            );
        }
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
        public TutorialScreen.Categories category;
        public Item icon;
        public int xOffset = 0;
        public int yOffset = 0;
        public int textureWidth = 256;
        public int textureHeight = 256;
        public int activeX;
        public int activeY;
        public int activeWidth;
        public int activeHeight;
        public int activeXOffset;
        public int activeYOffset;

        public TutorialTabButton build() {
            return new TutorialTabButton(
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.message,
                    this.onPress,
                    this.createNarration,
                    this.texture,
                    this.category,
                    this.icon,
                    this.xOffset,
                    this.yOffset,
                    this.textureWidth,
                    this.textureHeight,
                    this.activeX,
                    this.activeY,
                    this.activeWidth,
                    this.activeHeight,
                    this.activeXOffset,
                    this.activeYOffset
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

        public Builder setCategory(TutorialScreen.Categories category) {
            this.category = category;
            return this;
        }

        public Builder setIcon(Item icon) {
            this.icon = icon;
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

        public Builder setActiveX(int activeX) {
            this.activeX = activeX;
            return this;
        }

        public Builder setActiveY(int activeY) {
            this.activeY = activeY;
            return this;
        }

        public Builder setActiveWidth(int activeWidth) {
            this.activeWidth = activeWidth;
            return this;
        }

        public Builder setActiveHeight(int activeHeight) {
            this.activeHeight = activeHeight;
            return this;
        }

        public Builder setActiveXOffset(int activeXOffset) {
            this.activeXOffset = activeXOffset;
            return this;
        }

        public Builder setActiveYOffset(int activeYOffset) {
            this.activeYOffset = activeYOffset;
            return this;
        }
    }
}
