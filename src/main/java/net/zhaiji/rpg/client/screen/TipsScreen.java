package net.zhaiji.rpg.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TipsScreen extends Screen {
    public final int maxPage;
    public PageButton leftButton;
    public PageButton rightButton;
    public Button finishButton;
    public int page = 0;
    public static int pickup = 0;

    public TipsScreen(Component pTitle, int maxPage) {
        super(pTitle);
        this.maxPage = maxPage;
    }

    @Override
    protected void init() {
        super.init();
        this.leftButton = this.addRenderableWidget(
                new PageButton(
                        (this.width - 140) / 2,
                        this.height / 2 + 15,
                        false,
                        pButton -> {
                            --this.page;
                            this.buttonOnPress();
                        },
                        true
                )
        );

        this.rightButton = this.addRenderableWidget(
                new PageButton(
                        (this.width + 90) / 2,
                        this.height / 2 + 15,
                        true,
                        pButton -> {
                            ++this.page;
                            this.buttonOnPress();
                        },
                        true
                )
        );

        this.finishButton = this.addRenderableWidget(
                Button.builder(
                        Component.translatable("rpg.screen.tips.finish_button"),
                        pButton -> {
                            this.onClose();
                            pButton.setFocused(false);
                        }
                ).bounds(
                        (this.width - 200) / 2,
                        this.height / 2 + 40,
                        200,
                        20
                ).build()
        );

        this.buttonOnPress();
    }

    public void buttonOnPress() {
        this.leftButton.visible = this.page > 0;
        this.rightButton.visible = this.page < this.maxPage - 1;
        this.finishButton.visible = this.page >= this.maxPage - 1;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (minecraft == null) return;
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.blit(
                new ResourceLocation("tutorialTextures/gui/toasts.png"),
                this.width / 2 - 160 / 2,
                this.height / 2,
                0,
                96,
                160,
                32
        );

        switch (this.page){
            case 0:
                pGuiGraphics.drawCenteredString(
                        this.font,
                        Component.translatable("rpg.screen.tips.text_0"),
                        this.width / 2,
                        this.height / 2+5,
                        -1
                );
                break;
            case 1:
                pGuiGraphics.drawCenteredString(
                        this.font,
                        Component.translatable("rpg.screen.tips.text_1"),
                        this.width / 2,
                        this.height / 2+5,
                        -1
                );
                break;
            case 2:
                pGuiGraphics.drawCenteredString(
                        this.font,
                        Component.translatable("rpg.screen.tips.text_2"),
                        this.width / 2,
                        this.height / 2+5,
                        -1
                );
                break;
        }
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        for(GuiEventListener guieventlistener : this.children()) {
            if (guieventlistener.mouseClicked(pMouseX, pMouseY, pButton)) {
                if (pButton == 0) {
                    this.setDragging(true);
                }
                return true;
            }
        }
        return false;
    }
}
