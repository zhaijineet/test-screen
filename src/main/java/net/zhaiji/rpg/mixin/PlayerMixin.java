package net.zhaiji.rpg.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zhaiji.rpg.client.screen.tutorial.InitTutorial;
import net.zhaiji.rpg.handle.PlayerMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerMixinInterface {
    public CompoundTag tutorialPages;

    public PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void rpg$init(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile, CallbackInfo ci) {
        CompoundTag compoundTag = new CompoundTag();
        InitTutorial.PAGES.forEach(page -> compoundTag.putInt(page.identifier, 0));
        this.tutorialPages = compoundTag;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void rpg$readAdditionalSaveData(CompoundTag pCompound, CallbackInfo ci) {
        this.tutorialPages = pCompound.getCompound("tutorial_pages");
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void rpg$addAdditionalSaveData(CompoundTag pCompound, CallbackInfo ci) {
        pCompound.put("tutorial_pages", this.tutorialPages);
    }

    @Override
    public CompoundTag getTutorialPages() {
        return this.tutorialPages;
    }

    @Override
    public void setTutorialPages(CompoundTag compoundTag) {
        this.tutorialPages = compoundTag;
    }
}
