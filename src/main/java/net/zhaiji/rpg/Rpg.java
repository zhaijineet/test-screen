package net.zhaiji.rpg;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.zhaiji.rpg.client.packet.PageClientPacket;
import net.zhaiji.rpg.handle.PlayerMixinInterface;
import net.zhaiji.rpg.network.RpgPacket;
import org.slf4j.Logger;

@Mod(Rpg.MODID)
public class Rpg {
    public static final String MODID = "rpg";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Rpg() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> RpgClient::init);
        RpgPacket.register();
        MinecraftForge.EVENT_BUS.addListener(this::PlayerLoggedInEvent);
    }

    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            RpgPacket.sendToClient(new PageClientPacket(((PlayerMixinInterface) serverPlayer).getTutorialPages()), serverPlayer);
        }
    }
}
