package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

// 没写行最大字数限制
@OnlyIn(Dist.CLIENT)
public class TutorialTip {
    public List<Component> components;
    public int maxLineWidth = 100;
    public int color = -1;
    public int x = 0;
    public int y = 0;

    public TutorialTip(List<Component> components) {
        this.components = components;
    }

    public TutorialTip(List<Component> components, int x, int y) {
        this.components = components;
        this.x = x;
        this.y = y;
    }

    public TutorialTip setComponents(List<Component> components) {
        this.components = components;
        return this;
    }

    public TutorialTip addComponents(Component component) {
        this.components.add(component);
        return this;
    }

    public TutorialTip setMaxLineWidth(int maxLineWidth) {
        this.maxLineWidth = maxLineWidth;
        return this;
    }

    public TutorialTip setX(int x) {
        this.x = x;
        return this;
    }

    public TutorialTip setY(int y) {
        this.y = y;
        return this;
    }

    // GuiGraphics drawWordWrap
    public int drawWordWrap(GuiGraphics pGuiGraphics, Font pFont, FormattedText pText, int pX, int pY, int pLineWidth, int pColor) {
        int line = 0;
        for (FormattedCharSequence formattedcharsequence : pFont.split(pText, pLineWidth)) {
            pGuiGraphics.drawString(pFont, formattedcharsequence, pX, pY, pColor, false);
            pY += 9;
            line += 1;
        }
        return line;
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int line = 0;
        for (Component component : this.components) {
            line += this.drawWordWrap(pGuiGraphics, Minecraft.getInstance().font, component, this.x, this.y + line * 9, maxLineWidth, color);
        }
    }
}
