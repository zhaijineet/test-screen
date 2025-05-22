package net.zhaiji.rpg.handle;

import net.minecraft.nbt.CompoundTag;

public interface PlayerMixinInterface {
    CompoundTag getTutorialPages();

    void setTutorialPages(CompoundTag compoundTag);
}
