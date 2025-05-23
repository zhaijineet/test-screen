package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.rpg.Rpg;
import net.zhaiji.rpg.handle.PlayerMixinInterface;
import net.zhaiji.rpg.network.RpgPacket;
import net.zhaiji.rpg.network.packet.AwardServerPacket;
import net.zhaiji.rpg.network.packet.PageServerPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class TutorialScreen extends Screen {
    public static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "textures/gui/tutorial.png");
    public static int textureWidth = 512;
    public static int textureHeight = 512;
    public int leftPos;
    public int topPos;
    public TutorialTexture TUTORIAL_SCREEN = TutorialTexture.create();
    public int screenWidth = 256;
    public int screenHeight = 253;
    public int titleX;
    public int titleY;
    public Categories category = Categories.ALL;
    public List<TutorialPage> allPages = new ArrayList<>();
    public List<TutorialPage> categoryPages;
    public List<TutorialPage> pages;
    public TutorialPage selectPage;
    public EditBox searchBox;
    public int searchBoxX = 12;
    public int searchBoxY = 46;
    public int searchWidth = 61;
    public int searchHeight = 14;
    public float searchBoxScale = 1.3f;
    public String oldSearch = "";

    public List<TutorialTabButton> tabs = new ArrayList<>();
    public int tabX = 135;
    public int tabY = 12;
    public int tabWidth = 25;
    public int tabHeight = 23;
    public int tabXOffset = 288;
    public int tabYOffset = 30;
    public int tabActiveX = 135;
    public int tabActiveY = -1;
    public int tabActiveWidth = 21;
    public int tabActiveHeight = 36;
    public int tabActiveXOffset = 259;
    public int tabActiveYOffset = 17;
    public int tabSpacing = 5;

    public TutorialPageButton leftButton;
    public TutorialPageButton rightButton;
    public int buttonX = 91;
    public int buttonY = 222;
    public int buttonSpacing = 135;

    public TutorialButton claimButton;
    public int claimButtonX = 129;
    public int claimButtonY = 222;
    public int claimButtonWidth = 73;
    public int claimButtonHeight = 16;
    public int claimButtonXOffset = 259;
    public int claimButtonYOffset = 145;

    public TutorialScrollBar scrollBar = TutorialScrollBar.create();
    public boolean scrolling = false;
    public int scrollBarX = 75;
    public double scrollBarY = 66;
    public int scrollBarWidth = 3;
    public int scrollBarHeight = 168;
    public int scrollBarXOffset = 340;
    public int scrollBarYOffset = 18;

    public int scrollRailX = 75;
    public int scrollRailY = 66;
    public int scrollRailWidth = 3;
    public int scrollRailHeight = 168;
    public int scrollRailXOffset = 334;
    public int scrollRailYOffset = 18;

    public int slotIconWidth = 18;
    public int slotIconHeight = 18;
    public int slotIconXOffset = 260;
    public int slotIconYOffset = 56;
    public int slotIconSpacing = 7;

    public int sideButtonWidth = 63;
    public int sideButtonHeight = 22;
    public int sideButtonXOffset = 259;
    public int sideButtonYOffset = 76;
    public int sideButtonDotWidth = 4;
    public int sideButtonDotHeight = 4;
    public int sideButtonDotXOffset = 281;
    public int sideButtonDotYOffset = 56;
    public int sideButtonX = 7;
    public int sideButtonY = 67;
    public int sideButtonSpacing = 2;
    public int sideButtonVisibleCount = 7;

    public int sideButtonTotalHeight;
    public int sideButtonVisibleHeight;

    public List<TutorialSideButton> sideButton = new ArrayList<>();

    public int pageDotWidth = 3;
    public int pageDotHeight = 3;
    public int pageEmptyDotXOffset = 281;
    public int pageEmptyDotYOffset = 71;
    public int pageSolidDotXOffset = 287;
    public int pageSolidDotYOffset = 71;
    public int pageDotSpacing = 3;

    public TutorialScreen(Component pTitle) {
        super(pTitle);
    }

    public TutorialScreen() {
        super(Component.translatable("rpg.screen.title"));
    }

    public enum Categories {
        ALL(Component.translatable("rpg.screen.title_1"), Items.APPLE),
        TEST_1(Component.translatable("rpg.screen.title_2"), Items.STONE),
        TEST_2(Component.translatable("rpg.screen.title_3"), Items.STICK),
        TEST_3(Component.translatable("rpg.screen.title_4"), Items.DIAMOND),;

        public final Component title;
        public final Item icon;

        Categories(Component title, Item icon) {
            this.title = title;
            this.icon = icon;
        }
    }

    @Override
    public void init() {
        super.init();

        this.leftPos = (this.width - this.screenWidth) / 2;
        this.topPos = (this.height - this.screenHeight) / 2;

        this.TUTORIAL_SCREEN
                .setTexture(this.TEXTURE)
                .setTextureWidth(this.textureWidth)
                .setTextureHeight(this.textureHeight)
                .setWidth(this.screenWidth)
                .setHeight(this.screenHeight)
                .setX(this.leftPos)
                .setY(this.topPos);

        this.titleX = this.leftPos + 56;
        this.titleY = this.topPos + 17;

        this.initPages();
        this.loadCategoryPages();

        this.selectPage = null;

        this.searchBox = this.addRenderableWidget(
                new TutorialEditBox(
                        this.font,
                        this.leftPos + this.searchBoxX,
                        this.topPos + this.searchBoxY,
                        this.searchWidth,
                        this.searchHeight,
                        this.searchBoxScale,
                        Component.translatable("itemGroup.search")
                )
        );
        this.searchBox.setMaxLength(50);
        this.searchBox.setCanLoseFocus(true);
        this.searchBox.setBordered(false);
        this.searchBox.setTextColor(16777215);

        this.initTabButton();

        this.leftButton = this.addRenderableWidget(
                new TutorialPageButton(
                        this.leftPos + this.buttonX,
                        this.topPos + this.buttonY,
                        false,
                        pButton -> {
                            this.selectPage.pageNumber--;
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
                            this.selectPage.pageNumber++;
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
                        .setTexture(this.TEXTURE)
                        .setX(this.leftPos + this.claimButtonX)
                        .setY(this.topPos + this.claimButtonY)
                        .setWidth(this.claimButtonWidth)
                        .setHeight(this.claimButtonHeight)
                        .setXOffset(this.claimButtonXOffset)
                        .setYOffset(this.claimButtonYOffset)
                        .setMessage(Component.translatable("rpg.button.claim"))
                        .setOnPress(pButton -> {
                            this.updatePageState(2);
                            RpgPacket.sendToServer(new AwardServerPacket(this.selectPage.identifier));
                            this.selectPage.pageNumber--;
                            this.updateButton();
                        })
                        .setTextureWidth(this.textureWidth)
                        .setTextureHeight(this.textureHeight)
                        .build()
        );

        this.scrollBar
                .setTexture(this.TEXTURE)
                .setTextureWidth(this.textureWidth)
                .setTextureHeight(this.textureHeight)
                .setBarX(this.leftPos + this.scrollBarX)
                .setBarY(this.topPos + this.scrollBarY)
                .setBarWidth(this.scrollBarWidth)
                .setBarHeight(this.scrollBarHeight)
                .setMinY(this.topPos + this.scrollBarY)
                .setBarXOffset(this.scrollBarXOffset)
                .setBarYOffset(this.scrollBarYOffset)
                .setRailX(this.leftPos + this.scrollRailX)
                .setRailY(this.topPos + this.scrollRailY)
                .setRailWidth(this.scrollRailWidth)
                .setRailHeight(this.scrollRailHeight)
                .setRailXOffset(this.scrollRailXOffset)
                .setRailYOffset(this.scrollRailYOffset);

        this.initSideButton();

        this.updateButton();
    }

    @Override
    public void tick() {
        super.tick();
        this.searchBox.tick();
    }

    public void initTabButton() {
        this.tabs.clear();
        for (Categories category : Categories.values()) {
            this.tabs.add(
                    this.addRenderableWidget(
                            TutorialTabButton.builder()
                                    .setTexture(this.TEXTURE)
                                    .setTextureWidth(this.textureWidth)
                                    .setTextureHeight(this.textureHeight)
                                    .setCategory(category)
                                    .setIcon(category.icon)
                                    .setX(this.leftPos + this.tabX + this.tabs.size() * (this.tabWidth + this.tabSpacing) - this.tabSpacing)
                                    .setY(this.topPos + this.tabY)
                                    .setWidth(this.tabWidth)
                                    .setHeight(this.tabHeight)
                                    .setXOffset(this.tabXOffset)
                                    .setYOffset(this.tabYOffset)
                                    .setActiveX(this.leftPos + this.tabActiveX + this.tabs.size() * (this.tabWidth + this.tabSpacing) - this.tabSpacing)
                                    .setActiveY(this.topPos + this.tabActiveY)
                                    .setActiveWidth(this.tabActiveWidth)
                                    .setActiveHeight(this.tabActiveHeight)
                                    .setActiveXOffset(this.tabActiveXOffset)
                                    .setActiveYOffset(this.tabActiveYOffset)
                                    .setMessage(category.title)
                                    .setOnPress(pButton -> {
                                        if (pButton instanceof TutorialTabButton button) {
                                            if (this.category.equals(button.category)) {
                                                this.category = Categories.ALL;
                                            } else {
                                                this.category = button.category;
                                            }
                                            this.tabs.forEach(tab -> {
                                                tab.setFocused(tab == button);
                                            });
                                            this.loadCategoryPages();
                                            this.pages.clear();
                                            for (TutorialPage page : this.categoryPages) {
                                                if (page.title.getString().contains(this.searchBox.getValue())) {
                                                    this.pages.add(page);
                                                }
                                            }
                                            this.updateSideButton();
                                            if (this.selectPage != null) {
                                                this.sideButton.forEach(sideButton -> {
                                                    if (sideButton.page.identifier.equals(this.selectPage.identifier)) {
                                                        sideButton.setFocused(true);
                                                    }
                                                });
                                            }
                                        }
                                    })
                                    .build()
                    )
            );
        }
    }

    public void initSideButtonVisibleHeight() {
        this.sideButtonVisibleHeight = (this.sideButtonHeight + this.sideButtonSpacing) * this.sideButtonVisibleCount - this.sideButtonSpacing;
    }

    public void initSideButtonTotalHeight() {
        this.sideButtonTotalHeight = (this.sideButtonHeight + this.sideButtonSpacing) * this.sideButton.size() - this.sideButtonSpacing;
    }

    public void updateScrollBar() {
        this.initSideButtonTotalHeight();
        if (this.sideButtonTotalHeight > this.sideButtonVisibleHeight) {
            int newBarHeight = this.scrollRailHeight * this.sideButtonVisibleHeight / this.sideButtonTotalHeight;
            this.scrollBar.setBarHeight(newBarHeight);
            this.scrollBar
                    .setBarX(this.leftPos + this.scrollBarX)
                    .setMaxY(this.scrollBar.minY + this.scrollRailHeight - newBarHeight)
                    .setBarY(this.topPos + this.scrollBarY);
            this.scrollBar.hiddenRange = this.sideButtonTotalHeight - this.sideButtonVisibleHeight;
            this.scrollBar.canScrollRange = this.scrollBar.maxY - this.scrollBar.minY;
        }else {
            this.scrollBar
                    .setBarHeight(this.scrollRailHeight)
                    .setBarX(this.leftPos + this.scrollBarX)
                    .setBarY(this.topPos + this.scrollBarY)
                    .setMaxY(this.scrollBar.minY);
            this.scrollBar.hiddenRange = 0;
            this.scrollBar.canScrollRange = 0;
        }
    }

    public void initSideButton() {
        this.sideButton.clear();
        this.initSideButtonVisibleHeight();
        for (TutorialPage page : this.pages) {
            this.addSideButton(
                    TutorialSideButton.builder()
                            .setX(this.leftPos + this.sideButtonX)
                            .setY(this.topPos + this.sideButtonY)
                            .setWidth(this.sideButtonWidth)
                            .setHeight(this.sideButtonHeight)
                            .setTexture(this.TEXTURE)
                            .setTextureWidth(this.textureWidth)
                            .setTextureHeight(this.textureHeight)
                            .setXOffset(this.sideButtonXOffset)
                            .setYOffset(this.sideButtonYOffset)
                            .setIconWidth(this.slotIconWidth)
                            .setIconHeight(this.slotIconHeight)
                            .setIconXOffset(this.slotIconXOffset)
                            .setIconYOffset(this.slotIconYOffset)
                            .setDotWidth(this.sideButtonDotWidth)
                            .setDotHeight(this.sideButtonDotHeight)
                            .setDotXOffset(this.sideButtonDotXOffset)
                            .setDotYOffset(this.sideButtonDotYOffset)
                            .setScissorMinX(this.leftPos + this.sideButtonX)
                            .setScissorMinY(this.topPos + this.sideButtonY)
                            .setScissorMaxX(this.leftPos + this.sideButtonX + this.sideButtonWidth)
                            .setScissorMaxY(this.topPos + this.sideButtonY + this.sideButtonVisibleHeight)
                            .setPage(page)
                            .setScrollBar(this.scrollBar)
                            .setMessage(page.title)
                            .setOnPress(pButton -> {
                                if (pButton instanceof TutorialSideButton button) {
                                    this.sideButton.forEach(sideButton -> sideButton.setFocused(false));
                                    if (this.selectPage != null && button.page.identifier.equals(this.selectPage.identifier)) {
                                        this.selectPage = null;
                                    } else {
                                        this.selectPage = button.page.copy();
                                        button.setFocused(true);
                                    }
                                    this.updateButton();
                                }
                            }).build()
            );
        }
        this.updateScrollBar();
    }

    public void addSideButton(TutorialSideButton button){
        button.setY(this.topPos + this.sideButtonY + this.sideButton.size() * (sideButtonHeight + sideButtonSpacing));
        this.sideButton.add(button);
        button.originalY = button.getY();
        this.addWidget(button);
    }

    public void updateSideButton(){
        this.sideButton.forEach(this::removeWidget);
        this.initSideButton();
    }

    public void updateButton() {
        if (this.selectPage != null) {
            this.leftButton.visible = this.selectPage.pageNumber > 0;
            this.rightButton.visible = this.selectPage.pageNumber < this.selectPage.maxPageNumber - 1;
            this.claimButton.visible = this.selectPage.pageNumber >= this.selectPage.maxPageNumber - 1 && this.selectPage.state != 2 && !this.selectPage.award.isEmpty();
        } else {
            this.leftButton.visible = false;
            this.rightButton.visible = false;
            this.claimButton.visible = false;
        }
    }

    public void initPages() {
        PlayerMixinInterface playerMixinInterface = ((PlayerMixinInterface) Minecraft.getInstance().player);
        this.allPages.clear();
        for (TutorialPage page : InitTutorial.PAGES) {
            this.allPages.add(page.copy());
        }
        System.out.println(playerMixinInterface.getTutorialPages());
        this.allPages.forEach(page -> {
            System.out.println(playerMixinInterface.getTutorialPages().getInt(page.identifier));
            page.updateState(playerMixinInterface.getTutorialPages().getInt(page.identifier));
        });
    }

    public void updatePageState(int state) {
        CompoundTag compoundTag = new CompoundTag();
        this.allPages.forEach(page -> {
            if (this.selectPage != null && page.identifier.equals(this.selectPage.identifier)) {
                page.updateState(state);
                this.selectPage.updateState(state);
            }
            compoundTag.putInt(page.identifier, page.state);
        });
        PlayerMixinInterface playerMixinInterface = ((PlayerMixinInterface) Minecraft.getInstance().player);
        if (!compoundTag.equals(playerMixinInterface.getTutorialPages())) {
            playerMixinInterface.setTutorialPages(compoundTag);
            RpgPacket.sendToServer(new PageServerPacket(playerMixinInterface.getTutorialPages()));
        }
    }

    // page的位置没有调整
    public void loadCategoryPages() {
        this.categoryPages = this.allPages.stream().filter(page -> page.categories.contains(this.category)).collect(Collectors.toList());
        for (TutorialPage page : this.categoryPages) {
            page.init(this.leftPos + 92, this.topPos + 40);
        }
        this.pages = new ArrayList<>(this.categoryPages);
    }

    public void searchPages() {
        String searchString = this.searchBox.getValue();
        if (!searchString.equals(this.oldSearch)) {
            this.pages.clear();
            for (TutorialPage page : this.categoryPages) {
                if (page.title.getString().contains(searchString)) {
                    this.pages.add(page);
                }
            }
            this.oldSearch = searchString;
            this.updateSideButton();
        }
    }

    public void awardRender(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.selectPage.state != 2 && this.selectPage.pageNumber >= this.selectPage.maxPageNumber - 1) {
            int x = this.leftPos + this.claimButtonX + this.claimButtonWidth / 2;
            int y = this.topPos + this.claimButtonY - 5 - this.pageDotHeight - 3 - this.slotIconHeight;
            int totalWidth = (this.slotIconWidth + this.slotIconSpacing) * this.selectPage.award.size() - this.slotIconSpacing;
            int startX = x - totalWidth / 2;
            for (int i = 0; i < this.selectPage.award.size(); i++) {
                int renderX = startX + i * (this.slotIconWidth + this.slotIconSpacing);
                ItemStack itemStack = this.selectPage.award.get(i);
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
                        this.TEXTURE,
                        renderX,
                        y,
                        this.slotIconXOffset,
                        this.slotIconYOffset,
                        this.slotIconWidth,
                        this.slotIconHeight,
                        this.textureWidth,
                        this.textureHeight
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
        int totalWidth = (this.pageDotWidth + this.pageDotSpacing) * this.selectPage.maxPageNumber - this.pageDotSpacing;
        int startX = x - totalWidth / 2;
        for (int i = 0; i < this.selectPage.maxPageNumber; i++) {
            pGuiGraphics.blit(
                    this.TEXTURE,
                    startX + i * (this.pageDotWidth + this.pageDotSpacing),
                    y,
                    i == this.selectPage.pageNumber ? this.pageSolidDotXOffset : this.pageEmptyDotXOffset,
                    i == this.selectPage.pageNumber ? this.pageSolidDotYOffset : this.pageEmptyDotYOffset,
                    this.pageDotWidth,
                    this.pageDotHeight,
                    this.textureWidth,
                    this.textureHeight
            );
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        this.TUTORIAL_SCREEN.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        // 得改的地方
        float scale = 1.5f;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(scale, scale, 1.0f);
        pGuiGraphics.drawCenteredString(
                this.font,
                Component.translatable("rpg.screen.title_0", this.title, this.category.title),
                (int) (this.titleX / scale),
                (int) (this.titleY / scale),
                -1
        );
        pGuiGraphics.pose().popPose();

        if (this.selectPage != null) {
            this.selectPage.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.awardRender(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.pageNumberRender(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        this.searchPages();

        this.enableScissor(pGuiGraphics);

        for (TutorialSideButton button : this.sideButton) {
            button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        pGuiGraphics.disableScissor();

        this.scrollBar.render(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }


    public void enableScissor(GuiGraphics pGuiGraphics) {
        pGuiGraphics.enableScissor(
                this.leftPos + this.sideButtonX,
                this.topPos + this.sideButtonY,
                this.leftPos + this.sideButtonX + this.sideButtonWidth,
                this.topPos + this.sideButtonY + this.sideButtonVisibleHeight
        );
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.scrollBar.contains(pMouseX, pMouseY)) {
            this.scrolling = true;
        }
        for(GuiEventListener guieventlistener : this.children()) {
            if (guieventlistener.mouseClicked(pMouseX, pMouseY, pButton)) {
                this.focused = guieventlistener;
                if (pButton == 0) {
                    this.setDragging(true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        this.scrolling = false;
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.scrolling) {
            this.scrollBar.mouseDragged(pDragY);
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        this.scrollBar.mouseScrolled(-pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }
}