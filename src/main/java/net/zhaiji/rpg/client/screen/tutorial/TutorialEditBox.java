package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;

public class TutorialEditBox extends EditBox {
    public float scale;

    public TutorialEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, float scale, Component pMessage) {
        super(pFont, pX, pY, pWidth, pHeight, pMessage);
        this.scale = scale;
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.isVisible()) {
            if (this.bordered) {
                int i = this.isFocused() ? -1 : -6250336;
                pGuiGraphics.fill(this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY() + this.height + 1, i);
                pGuiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, -16777216);
            }

            int i2 = this.isEditable ? this.textColor : this.textColorUneditable;
            int j = this.cursorPos - this.displayPos;
            int k = this.highlightPos - this.displayPos;
            String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused() && this.frame / 6 % 2 == 0 && flag;
            int l = this.bordered ? this.getX() + 4 : this.getX();
            int i1 = this.bordered ? this.getY() + (this.height - 8) / 2 : this.getY();
            //
            l = (int)(l / this.scale);
            i1 = (int)(i1 / this.scale);
            //
            int j1 = l;
            if (k > s.length()) {
                k = s.length();
            }

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(this.scale, this.scale, this.scale);

            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = pGuiGraphics.drawString(this.font, this.formatter.apply(s1, this.displayPos), l, i1, i2);
            }

            boolean flag2 = this.cursorPos < this.value.length() || this.value.length() >= this.maxLength;
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length()) {
                pGuiGraphics.drawString(this.font, this.formatter.apply(s.substring(j), this.cursorPos), j1, i1, i2);
            }

            if (this.hint != null && s.isEmpty() && !this.isFocused()) {
                pGuiGraphics.drawString(this.font, this.hint, j1, i1, i2);
            }

            if (!flag2 && this.suggestion != null) {
                pGuiGraphics.drawString(this.font, this.suggestion, k1 - 1, i1, -8355712);
            }

            if (flag1) {
                if (flag2) {
                    pGuiGraphics.fill(RenderType.guiOverlay(), k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
                } else {
                    pGuiGraphics.drawString(this.font, "_", k1, i1, i2);
                }
            }
            if (k != j) {
                int l1 = l + this.font.width(s.substring(0, k));
                this.renderHighlight(pGuiGraphics, k1, i1 - 1, l1 - 1, i1 + 1 + 9);
            }
            pGuiGraphics.pose().popPose();
        }
    }

    public void renderHighlight(GuiGraphics pGuiGraphics, int pMinX, int pMinY, int pMaxX, int pMaxY){
        if (pMinX < pMaxX) {
            int i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }

        if (pMinY < pMaxY) {
            int j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }

        if (pMaxX > this.getX() + this.width) {
            pMaxX = this.getX() + this.width;
        }

        if (pMinX > this.getX() + this.width) {
            pMinX = this.getX() + this.width;
        }

        pGuiGraphics.fill(RenderType.guiTextHighlight(), pMinX, pMinY, pMaxX, pMaxY, -16776961);
    }
}
