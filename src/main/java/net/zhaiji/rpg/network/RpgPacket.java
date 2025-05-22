package net.zhaiji.rpg.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zhaiji.rpg.Rpg;
import net.zhaiji.rpg.client.packet.PageClientPacket;
import net.zhaiji.rpg.network.packet.AwardServerPacket;
import net.zhaiji.rpg.network.packet.PageServerPacket;

public class RpgPacket {
    public static final String VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(Rpg.MODID, "main"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.messageBuilder(PageServerPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(PageServerPacket::encode)
                .decoder(PageServerPacket::decode)
                .consumerMainThread(PageServerPacket::handle)
                .add();
        INSTANCE.messageBuilder(AwardServerPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(AwardServerPacket::encode)
                .decoder(AwardServerPacket::decode)
                .consumerMainThread(AwardServerPacket::handle)
                .add();
        INSTANCE.messageBuilder(PageClientPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(PageClientPacket::encode)
                .decoder(PageClientPacket::decode)
                .consumerMainThread(PageClientPacket::handle)
                .add();
    }

    public static <MSG> void sendToClient(MSG msg, ServerPlayer serverPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }
}
