package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;


@OnlyIn(Dist.CLIENT)
public class TutorialPage {
    public Component title;
    public List<TutorialScreen.Categories> categories = new ArrayList<>();
    public List<TutorialTexture> textures = new ArrayList<>();
    public List<TutorialTip> tips = new ArrayList<>();
    public int pageNumber = 0;
    public int maxPageNumber = 0;
    public int x;
    public int y;

    public Item icon;
    public List<Item> award = new ArrayList<>();

    public TutorialPage() {
        this.categories.add(TutorialScreen.Categories.ALL);
    }

    public TutorialPage(Component title, List<TutorialScreen.Categories> categories, List<TutorialTexture> textures, List<TutorialTip> tips, int pageNumber, int maxPageNumber, int x, int y) {
        this.title = title;
        this.categories = categories;
        this.textures = textures;
        this.tips = tips;
        this.pageNumber = pageNumber;
        this.maxPageNumber = maxPageNumber;
        this.x = x;
        this.y = y;
    }

    public TutorialPage copy() {
        return new TutorialPage(
                title,
                categories,
                textures,
                tips,
                0,
                maxPageNumber,
                x,
                y
        );
    }

    public static TutorialPage create() {
        return new TutorialPage();
    }

    public TutorialPage setTitle(Component title) {
        this.title = title;
        return this;
    }

    public TutorialPage setIcon(Item icon) {
        this.icon = icon;
        return this;
    }

    public TutorialPage addAward(Item... item) {
        this.award.addAll(List.of(item));
        return this;
    }

    public TutorialPage addCategory(TutorialScreen.Categories category) {
        this.categories.add(category);
        return this;
    }

    public TutorialPage addPage(TutorialTexture tutorialTexture, TutorialTip tutorialTip) {
        this.textures.add(tutorialTexture);
        this.tips.add(tutorialTip);
        this.maxPageNumber++;
        return this;
    }

    public TutorialTexture getTexture(int index){
        return textures.get(index);
    }

    public TutorialTip getTip(int index) {
        return tips.get(index);
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        textures.forEach(texture-> texture.setX(x).setY(y));
        tips.forEach(tip -> tip.setX(x).setY(y + 100));
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.getTexture(this.pageNumber).render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.getTip(this.pageNumber).render(pGuiGraphics, Minecraft.getInstance().font, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawString(
                Minecraft.getInstance().font,
                this.pageNumber + "/" + (this.maxPageNumber - 1),
                this.x,
                this.y,
                -1
        );
    }
}