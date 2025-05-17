package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.zhaiji.rpg.Rpg;

import java.util.ArrayList;
import java.util.List;

public class TutorialScreen extends Screen {
    public int leftPos;
    public int topPos;
    public TutorialTexture TUTORIAL_SCREEN = TutorialTexture.create();
    public int screenWidth = 256;
    public int screenHeight = 253;
    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "textures/gui/tutorial.png");
    public int textureWidth = 512;
    public int textureHeight = 512;
    public Categories category = Categories.ALL;
    public List<TutorialPage> allPages = InitTutorial.PAGES;
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

    public List<TutorialTabButton> Tabs = new ArrayList<>();
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
    public int tabDistance = 5;

    public TutorialPageButton leftButton;
    public TutorialPageButton rightButton;
    public int buttonX = 91;
    public int buttonY = 208+14;
    public int buttonDistance = 135;

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

    public int sideButtonWidth = 63;
    public int sideButtonHeight = 22;
    public int sideButtonXOffset = 259;
    public int sideButtonYOffset = 76;
    public int sideButtonX = 7;
    public int sideButtonY = 67;
    public int sideButtonDistance = 2;
    public int sideButtonVisibleCount = 7;

    public int sideButtonTotalHeight;
    public int sideButtonVisibleHeight;

    public List<TutorialSideButton> sideButton = new ArrayList<>();

    public TutorialScreen(Component pTitle) {
        super(pTitle);
    }

    public enum Categories {
        ALL(Component.translatable("all"), Items.APPLE),
        TEST_1(Component.translatable("test_1"), Items.STONE),
        TEST_2(Component.translatable("test_2"), Items.STICK),
        TEST_3(Component.translatable("test_3"), Items.DIAMOND),;

        public final Component title;
        public final Item icon;

        Categories(Component title, Item icon) {
            this.title = title;
            this.icon = icon;
        }
    }

    @Override
    protected void init() {
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
                        this.leftPos + this.buttonX + this.buttonDistance,
                        this.topPos + this.buttonY,
                        true,
                        pButton -> {
                            this.selectPage.pageNumber++;
                            this.updateButton();
                        },
                        true
                )
        );
        // 还差退出按钮

        this.scrollBar
                .setTexture(this.TEXTURE)
                .setTextureWidth(this.textureWidth)
                .setTextureHeight(this.textureHeight)
                .setBarX(this.leftPos + this.scrollBarX)
                .setBarY(this.topPos + this.scrollBarY)
                .setBarWidth(this.scrollBarWidth)
                .setBarHeight(this.scrollBarHeight)
                .setMinY(this.topPos + this.scrollBarY)
//                .setMaxY(this.topPos + this.scrollBarY + this.scrollRailHeight)
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
        for (Categories category : Categories.values()) {
            this.Tabs.add(
                    this.addRenderableWidget(
                            TutorialTabButton.builder()
                                    .setTexture(this.TEXTURE)
                                    .setTextureWidth(this.textureWidth)
                                    .setTextureHeight(this.textureHeight)
                                    .setCategory(category)
                                    .setIcon(category.icon)
                                    .setX(this.leftPos + this.tabX + this.Tabs.size() * (this.tabWidth + this.tabDistance) - this.tabDistance)
                                    .setY(this.topPos + this.tabY)
                                    .setWidth(this.tabWidth)
                                    .setHeight(this.tabHeight)
                                    .setXOffset(this.tabXOffset)
                                    .setYOffset(this.tabYOffset)
                                    .setActiveX(this.leftPos + this.tabActiveX + this.Tabs.size() * (this.tabWidth + this.tabDistance) - this.tabDistance)
                                    .setActiveY(this.topPos + this.tabActiveY)
                                    .setActiveWidth(this.tabActiveWidth)
                                    .setActiveHeight(this.tabActiveHeight)
                                    .setActiveXOffset(this.tabActiveXOffset)
                                    .setActiveYOffset(this.tabActiveYOffset)
                                    .setMessage(category.title)
                                    .setOnPress(pButton -> {
                                        if (pButton instanceof TutorialTabButton button) {
                                            if (!this.category.equals(button.category)) {
                                                this.category = button.category;
                                            } else {
                                                this.category = Categories.ALL;
                                            }
                                            this.loadCategoryPages();
                                            if (!this.searchPages()) {
                                                this.updateSideButton();
                                            }
                                        }
                                    })
                                    .build()
                    )
            );
        }
    }

    public void initSideButtonTotalAndVisibleHeight() {
        this.sideButtonTotalHeight = (this.sideButtonHeight + this.sideButtonDistance) * this.sideButton.size() - this.sideButtonDistance;
        this.sideButtonVisibleHeight = (this.sideButtonHeight + this.sideButtonDistance) * this.sideButtonVisibleCount - this.sideButtonDistance;
    }

    public void updateScrollBar() {
        this.initSideButtonTotalAndVisibleHeight();
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
                            .setPage(page)
                            .setScrollBar(this.scrollBar)
                            .setMessage(page.title)
                            .setOnPress(pButton -> {
                                if (pButton instanceof TutorialSideButton button) {
                                    if (this.selectPage != null && button.page.title.equals(this.selectPage.title)) {
                                        this.selectPage = null;
                                    } else {
                                        this.selectPage = button.page.copy();
                                    }
                                    this.updateButton();
                                }
                            }).build()
            );
        }
        this.updateScrollBar();
    }

    public void addSideButton(TutorialSideButton button){
        button.setY(this.topPos + this.sideButtonY + this.sideButton.size() * (sideButtonHeight + sideButtonDistance));
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
        } else {
            this.leftButton.visible = false;
            this.rightButton.visible = false;
        }
    }

    // page的位置没有调整
    public void loadCategoryPages() {
        this.categoryPages = this.allPages.stream().filter(page -> page.categories.contains(this.category)).toList();
        for (TutorialPage page : this.categoryPages) {
            page.init(this.leftPos + 92, this.topPos + 40);
        }
        this.pages = new ArrayList<>(this.categoryPages);
    }

    public boolean searchPages() {
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
            return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        this.TUTORIAL_SCREEN.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (this.selectPage != null) {
            this.selectPage.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
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
                this.setFocused(guieventlistener);
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