package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.rpg.Rpg;

@OnlyIn(Dist.CLIENT)
public class TutorialPageButton extends Button {
    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;
    private final boolean isForward;
    private final boolean playTurnSound;

    public TutorialPageButton(int pX, int pY, boolean pIsForward, OnPress pOnPress, boolean pPlayTurnSound) {
        super(pX, pY, 19, 11, CommonComponents.EMPTY, pOnPress, DEFAULT_NARRATION);
        this.texture = ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "textures/gui/tutorial.png");
        this.textureWidth = 512;
        this.textureHeight = 512;
        this.isForward = pIsForward;
        this.playTurnSound = pPlayTurnSound;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(
                this.texture,
                this.getX(),
                this.getY(),
                this.isForward ? 257 + this.width : 257,
                this.isHoveredOrFocused() ? 121 + this.height : 121,
                this.width,
                this.height,
                this.textureWidth,
                this.textureHeight
        );
    }

    public void playDownSound(SoundManager pHandler) {
        if (this.playTurnSound) {
            pHandler.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }
    }
}
