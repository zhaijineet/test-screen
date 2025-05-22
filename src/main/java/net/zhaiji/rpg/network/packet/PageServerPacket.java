package net.zhaiji.rpg.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.rpg.handle.PlayerMixinInterface;

import java.util.function.Supplier;

public class PageServerPacket {
    public CompoundTag compoundTag;

    public PageServerPacket(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.compoundTag);
    }

    public static PageServerPacket decode(FriendlyByteBuf buf) {
        return new PageServerPacket(buf.readNbt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ((PlayerMixinInterface) context.getSender()).setTutorialPages(this.compoundTag);
        });
        context.setPacketHandled(true);
    }
}
