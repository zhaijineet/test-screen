package net.zhaiji.rpg;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
//import net.zhaiji.rpg.client.screen.TestScreen;
import net.zhaiji.rpg.client.screen.tutorial.TutorialScreen;
import org.lwjgl.glfw.GLFW;


@OnlyIn(Dist.CLIENT)
public class RpgClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(RpgClient::InputEvent$MouseButton$Post);
    }

    public static void InputEvent$MouseButton$Post(InputEvent.MouseButton.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || event.getAction() != 1 || event.getButton() != GLFW.GLFW_MOUSE_BUTTON_RIGHT) return;
        if (!minecraft.player.isCrouching()) return;
        minecraft.setScreen(new TutorialScreen(Component.empty()));
//        minecraft.setScreen(new TestScreen());
    }
}
