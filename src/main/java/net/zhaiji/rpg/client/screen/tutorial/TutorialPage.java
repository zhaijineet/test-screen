package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;


@OnlyIn(Dist.CLIENT)
public class TutorialPage {
    public String identifier;
    public Component title;
    public Item icon;
    public List<TutorialScreen.Categories> categories = new ArrayList<>();
    public List<TutorialTexture> textures = new ArrayList<>();
    public List<TutorialTip> tips = new ArrayList<>();

    public List<ItemStack> award = new ArrayList<>();
    public int state = 0;

    public int pageNumber = 0;
    public int maxPageNumber = 0;
    public int x;
    public int y;

    public TutorialPage() {
        this.categories.add(TutorialScreen.Categories.ALL);
    }

    public TutorialPage(String identifier, Component title, Item icon, List<TutorialScreen.Categories> categories, List<TutorialTexture> textures, List<TutorialTip> tips, List<ItemStack> award, int state, int pageNumber, int maxPageNumber, int x, int y) {
        this.identifier = identifier;
        this.title = title;
        this.icon = icon;
        this.categories = categories;
        this.textures = textures;
        this.tips = tips;
        this.award = award;
        this.state = state;
        this.pageNumber = pageNumber;
        this.maxPageNumber = maxPageNumber;
        this.x = x;
        this.y = y;
    }

    public TutorialPage copy() {
        return new TutorialPage(
                this.identifier,
                this.title,
                this.icon,
                this.categories,
                this.textures,
                this.tips,
                this.award,
                this.state,
                0,
                this.maxPageNumber,
                this.x,
                this.y
        );
    }

    public static TutorialPage create() {
        return new TutorialPage();
    }

    public TutorialPage setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TutorialPage setTitle(Component title) {
        this.title = title;
        return this;
    }

    public TutorialPage setIcon(Item icon) {
        this.icon = icon;
        return this;
    }

    public TutorialPage addAward(TutorialTexture tutorialTexture, ItemStack... item) {
        this.award.addAll(List.of(item));
        this.textures.add(tutorialTexture);
        this.maxPageNumber++;
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

    public void updateState(int state) {
        if (state == 2 && this.state != state) {
            this.maxPageNumber--;
        }
        if (this.state < state) {
            this.state = state;
        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.getTexture(this.pageNumber).render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (this.award.isEmpty() || this.pageNumber < this.maxPageNumber - 1) {
            this.getTip(this.pageNumber).render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }
}