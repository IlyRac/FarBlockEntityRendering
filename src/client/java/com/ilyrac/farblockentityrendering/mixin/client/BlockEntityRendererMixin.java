package com.ilyrac.farblockentityrendering.mixin.client;

import com.ilyrac.farblockentityrendering.config.ConfigManager;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockEntityRenderer.class)
public interface BlockEntityRendererMixin<T extends BlockEntity> {

    /**
        @author IlyRac
        @reason Extend Block Entity Rendering
     */
    @Overwrite
    default boolean shouldRender(T blockEntity, Vec3 cameraPos) {
        double dist = Vec3.atCenterOf(blockEntity.getBlockPos())
                .distanceToSqr(cameraPos);

        return dist < ConfigManager.getBlockEntityRenderDistanceSquared();
    }
}
