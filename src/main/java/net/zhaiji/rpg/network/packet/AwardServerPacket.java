package net.zhaiji.rpg.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.rpg.client.screen.tutorial.InitTutorial;

import java.util.function.Supplier;

public class AwardServerPacket {
    public String identifier;

    public AwardServerPacket(String identifier) {
        this.identifier = identifier;
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.identifier);
    }

    public static AwardServerPacket decode(FriendlyByteBuf buf) {
        return new AwardServerPacket(buf.readUtf());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            InitTutorial.PAGES.forEach(page -> {
                if (page.identifier.equals(this.identifier)) {
                    page.award.forEach(stack -> context.getSender().spawnAtLocation(stack));
                }
            });
        });
        context.setPacketHandled(true);
    }
}
