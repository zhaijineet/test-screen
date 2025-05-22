package net.zhaiji.rpg.client.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.rpg.handle.PlayerMixinInterface;

import java.util.function.Supplier;

public class PageClientPacket {
    public CompoundTag compoundTag;

    public PageClientPacket(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.compoundTag);
    }

    public static PageClientPacket decode(FriendlyByteBuf buf) {
        return new PageClientPacket(buf.readNbt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ((PlayerMixinInterface) Minecraft.getInstance().player).setTutorialPages(this.compoundTag);
        });
        context.setPacketHandled(true);
    }
}