package com.ilyrash.farblockentity.mixin.client;

import com.ilyrash.farblockentity.config.ConfigManager;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderer.class)
public interface BlockEntityRendererMixin {

    @Inject(method = "getRenderDistance", at = @At("HEAD"), cancellable = true)
    default void increaseRenderDistance(CallbackInfoReturnable<Integer> cir) {
        // Always get the current value from config, never cache it
        cir.setReturnValue(ConfigManager.getBlockEntityRenderDistance());
    }

    @Inject(method = "isInRenderDistance", at = @At("HEAD"), cancellable = true)
    default void overrideDistanceCheck(BlockEntity blockEntity, Vec3d pos, CallbackInfoReturnable<Boolean> cir) {
        // Always use current config value
        double distance = blockEntity.getPos().getSquaredDistance(pos);
        cir.setReturnValue(distance < ConfigManager.getBlockEntityRenderDistanceSquared());
    }
}