//package net.zhaiji.rpg.client.screen.tutorial;
//
//import net.minecraft.client.gui.GuiGraphics;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TutorialSide {
//    public TutorialScrollBar scrollBar;
//    public int x;
//    public int y;
//    public int width;
//    public int height;
//    public List<TutorialSideButton> buttons = new ArrayList<>();
//    public TutorialSideButton selected;
//
//    public static TutorialSide create() {
//        return new TutorialSide();
//    }
//
//    public TutorialSide setScrollBar(TutorialScrollBar scrollBar) {
//        this.scrollBar = scrollBar;
//        return this;
//    }
//
//    public TutorialSide setX(int x) {
//        this.x = x;
//        return this;
//    }
//
//    public TutorialSide setY(int y) {
//        this.y = y;
//        return this;
//    }
//
//    public TutorialSide setWidth(int width) {
//        this.width = width;
//        return this;
//    }
//
//    public TutorialSide setHeight(int height) {
//        this.height = height;
//        return this;
//    }
//
////    public TutorialSide addButtons(TutorialSideButton button) {
////        this.buttons.add(button);
////        button.setScrollBar(this.scrollBar);
////        return this;
////    }
//
//    public int getButtonsSize() {
//        return buttons.size();
//    }
//
//    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        this.enableScissor(pGuiGraphics);
//
//
//
//
//
//
//
//
//
//
//
//
//        pGuiGraphics.disableScissor();
//    }
//
//    public void enableScissor(GuiGraphics pGuiGraphics) {
//        pGuiGraphics.enableScissor(this.x, this.y, this.x + this.width, this.y + this.height);
//    }
//
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        return true;
//    }
//}
