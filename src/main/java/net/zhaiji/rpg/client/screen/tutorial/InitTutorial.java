package net.zhaiji.rpg.client.screen.tutorial;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.rpg.Rpg;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class InitTutorial {
    public static List<TutorialPage> PAGES = new ArrayList<>();

    public static TutorialPage ADD(TutorialPage page) {
        PAGES.add(page);
        return page;
    }

    public static TutorialTexture texture = TutorialTexture.create()
            .setTexture(ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "textures/gui/texture.png"))
            .setWidth(92)
            .setHeight(74);

    public static TutorialTip tip_1 = new TutorialTip(
            new ArrayList<>(
                    List.of(
                            Component.translatable("tip_1"),
                            Component.translatable("tip_2"),
                            Component.translatable("tip_3")
                    )
            )
    );

    public static TutorialTip tip_2 = new TutorialTip(
            new ArrayList<>(
                    List.of(
                            Component.translatable("tip_4"),
                            Component.translatable("tip_5"),
                            Component.translatable("tip_6")
                    )
            )
    );

    public static TutorialTip tip_3 = new TutorialTip(
            new ArrayList<>(
                    List.of(
                            Component.translatable("tip_7"),
                            Component.translatable("tip_8"),
                            Component.translatable("tip_9")
                    )
            )
    );

    public static TutorialPage TEST_1_1 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_1_1")
                    .setTitle(Component.translatable("test_1_1"))
                    .addCategory(TutorialScreen.Categories.TEST_1)
                    .setIcon(Items.APPLE)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_1_2 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_1_2")
                    .setTitle(Component.translatable("test_1_2"))
                    .addCategory(TutorialScreen.Categories.TEST_1)
                    .setIcon(Items.IRON_INGOT)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_1_3 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_1_3")
                    .setTitle(Component.translatable("test_1_3"))
                    .addCategory(TutorialScreen.Categories.TEST_1)
                    .setIcon(Items.STONE)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
    );

    public static TutorialPage TEST_2_1 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_2_1")
                    .setTitle(Component.translatable("test_2_1"))
                    .addCategory(TutorialScreen.Categories.TEST_2)
                    .setIcon(Items.STICK)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_2_2 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_2_2")
                    .setTitle(Component.translatable("test_2_2"))
                    .addCategory(TutorialScreen.Categories.TEST_2)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_2_3 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_2_3")
                    .setTitle(Component.translatable("test_2_3"))
                    .addCategory(TutorialScreen.Categories.TEST_2)
                    .setIcon(Items.IRON_SWORD)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_3_1 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_3_1")
                    .setTitle(Component.translatable("test_3_1"))
                    .addCategory(TutorialScreen.Categories.TEST_3)
                    .setIcon(Items.APPLE)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );

    public static TutorialPage TEST_3_2 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_3_2")
                    .setTitle(Component.translatable("test_3_2"))
                    .addCategory(TutorialScreen.Categories.TEST_3)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
    );

    public static TutorialPage TEST_3_3 = ADD(
            TutorialPage.create()
                    .setIdentifier("test_3_3")
                    .setTitle(Component.translatable("test_3_3"))
                    .addCategory(TutorialScreen.Categories.TEST_3)
                    .addPage(texture, tip_1)
                    .addPage(texture, tip_2)
                    .addPage(texture, tip_3)
                    .addAward(texture, Items.STONE.getDefaultInstance(), Items.STICK.getDefaultInstance())
    );
}
