package net.zhaiji.rpg.client.screen.tutorial.pop_up;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.rpg.Rpg;
import net.zhaiji.rpg.client.screen.tutorial.*;
import net.zhaiji.rpg.handle.PlayerMixinInterface;
import net.zhaiji.rpg.network.RpgPacket;
import net.zhaiji.rpg.network.packet.AwardServerPacket;
import net.zhaiji.rpg.network.packet.PageServerPacket;

public class PopUpScreen extends Screen {
    public int leftPos;
    public int topPos;
    public TutorialTexture POP_UP_SCREEN = TutorialTexture.create();
    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "textures/gui/pop_up.png");
    public int screenWidth = 183;
    public int screenHeight = 206;
    public int textureWidth = 256;
    public int textureHeight = 256;

    public TutorialPage page;

    public TutorialPageButton leftButton;
    public TutorialPageButton rightButton;
    public int buttonX = 13;
    public int buttonY = 175;
    public int buttonSpacing = 135;

    public TutorialButton claimButton;
    public int claimButtonX = 55;
    public int claimButtonY = 185;
    public int claimButtonWidth = 73;
    public int claimButtonHeight = 16;
    public int claimButtonXOffset = 259;
    public int claimButtonYOffset = 145;

    public int slotIconWidth = 18;
    public int slotIconHeight = 18;
    public int slotIconXOffset = 260;
    public int slotIconYOffset = 56;
    public int slotIconSpacing = 7;

    public int pageDotWidth = 3;
    public int pageDotHeight = 3;
    public int pageEmptyDotXOffset = 281;
    public int pageEmptyDotYOffset = 71;
    public int pageSolidDotXOffset = 287;
    public int pageSolidDotYOffset = 71;
    public int pageDotSpacing = 3;

    public PopUpScreen(Component pTitle,TutorialPage page) {
        super(pTitle);
        this.page = page;
    }
    public PopUpScreen(TutorialPage page) {
        super(Component.empty());
        this.page = page;
    }

    @Override
    public void init() {
        super.init();

        this.leftPos = (this.width - this.screenWidth) / 2;
        this.topPos = (this.height - this.screenHeight) / 2;

        this.page.updateState(((PlayerMixinInterface) Minecraft.getInstance().player).getTutorialPages().getInt(this.page.identifier));
        this.page.init(this.leftPos + 15, this.topPos + 12);

        this.POP_UP_SCREEN
                .setTexture(this.TEXTURE)
                .setTextureWidth(this.textureWidth)
                .setTextureHeight(this.textureHeight)
                .setWidth(this.screenWidth)
                .setHeight(this.screenHeight)
                .setX(this.leftPos)
                .setY(this.topPos);

        this.leftButton = this.addRenderableWidget(
                new TutorialPageButton(
                        this.leftPos + this.buttonX,
                        this.topPos + this.buttonY,
                        false,
                        pButton -> {
                            this.page.pageNumber--;
                            this.updateButton();
                        },
                        true
                )
        );

        this.rightButton = this.addRenderableWidget(
                new TutorialPageButton(
                        this.leftPos + this.buttonX + this.buttonSpacing,
                        this.topPos + this.buttonY,
                        true,
                        pButton -> {
                            this.page.pageNumber++;
                            this.updateButton();
                            if (!this.rightButton.visible) {
                                this.updatePageState(1);
                            }
                        },
                        true
                )
        );

        this.claimButton = this.addRenderableWidget(
                TutorialButton.builder()
                        .setTexture(TutorialScreen.TEXTURE)
                        .setX(this.leftPos + this.claimButtonX)
                        .setY(this.topPos + this.claimButtonY)
                        .setWidth(this.claimButtonWidth)
                        .setHeight(this.claimButtonHeight)
                        .setXOffset(this.claimButtonXOffset)
                        .setYOffset(this.claimButtonYOffset)
                        .setMessage(Component.translatable("rpg.button.claim"))
                        .setOnPress(pButton -> {
                            this.updatePageState(2);
                            RpgPacket.sendToServer(new AwardServerPacket(this.page.identifier));
                            this.onClose();
                        })
                        .setTextureWidth(TutorialScreen.textureWidth)
                        .setTextureHeight(TutorialScreen.textureHeight)
                        .build()
        );

        this.updateButton();
    }

    public void updateButton() {
            this.leftButton.visible = this.page.pageNumber > 0;
            this.rightButton.visible = this.page.pageNumber < this.page.maxPageNumber - 1;
            this.claimButton.visible = this.page.pageNumber >= this.page.maxPageNumber - 1 && this.page.state != 2 && !this.page.award.isEmpty();
    }

    public void updatePageState(int state) {
        this.page.updateState(state);
        PlayerMixinInterface playerMixinInterface = ((PlayerMixinInterface) Minecraft.getInstance().player);
        playerMixinInterface.getTutorialPages().putInt(this.page.identifier, state);
        RpgPacket.sendToServer(new PageServerPacket(playerMixinInterface.getTutorialPages()));
    }

    public void awardRender(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.page.state != 2 && this.page.pageNumber >= this.page.maxPageNumber - 1) {
            int x = this.leftPos + this.claimButtonX + this.claimButtonWidth / 2;
            int y = this.topPos + this.claimButtonY - 5 - this.pageDotHeight - 3 - this.slotIconHeight;
            int totalWidth = (this.slotIconWidth + this.slotIconSpacing) * this.page.award.size() - this.slotIconSpacing;
            int startX = x - totalWidth / 2;
            for (int i = 0; i < this.page.award.size(); i++) {
                int renderX = startX + i * (this.slotIconWidth + this.slotIconSpacing);
                ItemStack itemStack = this.page.award.get(i);
                if (pMouseX > renderX
                        && pMouseX < renderX + this.slotIconWidth
                        && pMouseY > y
                        && pMouseY < y + this.slotIconHeight) {
                    pGuiGraphics.renderTooltip(
                            this.font,
                            itemStack,
                            pMouseX,
                            pMouseY
                    );
                }
                pGuiGraphics.blit(
                        TutorialScreen.TEXTURE,
                        renderX,
                        y,
                        this.slotIconXOffset,
                        this.slotIconYOffset,
                        this.slotIconWidth,
                        this.slotIconHeight,
                        TutorialScreen.textureWidth,
                        TutorialScreen.textureHeight
                );
                pGuiGraphics.renderItem(
                        itemStack,
                        renderX + 1,
                        y + 1
                );
                pGuiGraphics.renderItemDecorations(
                        this.font,
                        itemStack,
                        renderX + 1,
                        y + 1
                );
            }
        }
    }

    public void pageNumberRender(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int x = this.leftPos + this.claimButtonX + this.claimButtonWidth / 2;
        int y = this.topPos + this.claimButtonY - 5 - this.pageDotHeight;
        int totalWidth = (this.pageDotWidth + this.pageDotSpacing) * this.page.maxPageNumber - this.pageDotSpacing;
        int startX = x - totalWidth / 2;
        for (int i = 0; i < this.page.maxPageNumber; i++) {
            pGuiGraphics.blit(
                    TutorialScreen.TEXTURE,
                    startX + i * (this.pageDotWidth + this.pageDotSpacing),
                    y,
                    i == this.page.pageNumber ? this.pageSolidDotXOffset : this.pageEmptyDotXOffset,
                    i == this.page.pageNumber ? this.pageSolidDotYOffset : this.pageEmptyDotYOffset,
                    this.pageDotWidth,
                    this.pageDotHeight,
                    TutorialScreen.textureWidth,
                    TutorialScreen.textureHeight
            );
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        this.POP_UP_SCREEN.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.page.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.awardRender(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.pageNumberRender(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
