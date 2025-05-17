//package net.zhaiji.rpg.client.screen;
//
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.client.gui.components.EditBox;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.List;
//
//public class TestScreen extends Screen {
//    public int scrollX = 50;
//    public double scrollY = 32;
//    public double minScrollY = 32;
//    public double maxScrollY = 137;
//    public boolean scrolling = false;
//
//    public static final List<String> searchString = List.of("a", "b", "c", "我");
//
//    public EditBox searchBox;
//
//    public TestScreen() {
//        super(Component.empty());
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//        this.searchBox = this.addRenderableWidget(
//                new EditBox(
//                        this.font,
//                        this.width / 2 - 192 / 2 + 4,
//                        this.height / 2 - 160 / 2 + 20,
//                        42,
//                        9,
//                        Component.translatable("itemGroup.search")
//                )
//        );
//        this.searchBox.setMaxLength(50);
//        this.searchBox.setCanLoseFocus(true);
//        this.searchBox.setBordered(false);
//        this.searchBox.setTextColor(16777215);
//    }
//
//    @Override
//    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        if (minecraft == null) return;
//        this.renderBackground(pGuiGraphics);
//        pGuiGraphics.blit(
//                new ResourceLocation("rpg:textures/gui/screen.png"),
//                this.width / 2 - 192 / 2,
//                this.height / 2 - 160 / 2,
//                0,
//                0,
//                192,
//                160
//        );
//
//        pGuiGraphics.blit(
//                new ResourceLocation("rpg:textures/gui/screen.png"),
//                this.width / 2 - 192 / 2 + scrollX,
//                this.height / 2 - 160 / 2 + (int) scrollY,
//                this.scrolling ? 192 + 12 : 192,
//                0,
//                12,
//                15
//        );
//
//        List<String> list = this.findString();
//        if (!this.searchBox.getValue().isEmpty() && !list.isEmpty()) {
//            String text = list.get(0);
//            pGuiGraphics.drawString(
//                    this.font,
//                    text,
//                    this.width / 2 - font.width(text) / 2,
//                    this.height / 2,
//                    -1,
//                    true
//            );
//        }
//
//        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
//    }
//
//    @Override
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        if (pMouseX > (this.width / 2 - 192 / 2 + scrollX)
//                && pMouseX < (this.width / 2 - 192 / 2 + scrollX + 12)
//                && pMouseY > (this.height / 2 - 160 / 2 + scrollY)
//                && pMouseY < (this.height / 2 - 160 / 2 + scrollY + 15)
//        ) {
//            this.scrolling = true;
//        }
//        return super.mouseClicked(pMouseX, pMouseY, pButton);
//    }
//
//    @Override
//    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
//        this.scrolling = false;
//        return super.mouseReleased(pMouseX, pMouseY, pButton);
//    }
//
//    @Override
//    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
//        if (this.scrolling) {
//            this.scrollY = Math.max(this.minScrollY, Math.min(this.maxScrollY, this.scrollY + pDragY));
//        }
//        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
//    }
//
//    @Override
//    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
//        this.scrollY = Math.max(this.minScrollY, Math.min(this.maxScrollY, this.scrollY - pDelta));
//        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
//    }
//
//    public List<String> findString(){
//        return searchString.stream().filter(string -> string.contains(this.searchBox.getValue())).toList();
//    }
//}
