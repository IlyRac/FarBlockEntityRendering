package com.ilyrash.farblockentity.mixin.client;

import com.ilyrash.farblockentity.config.ConfigManager;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockEntityRenderer.class)
public interface BlockEntityRendererInterfaceMixin<T extends BlockEntity> {

    /**
     * @author IlyRash
     * @reason Override render distance for all block entities
     */
    @Overwrite
    default int getRenderDistance() {
        return ConfigManager.getBlockEntityRenderDistance();
    }

    /**
     * @author IlyRash
     * @reason Override distance check for all block entities
     */
    @Overwrite
    default boolean isInRenderDistance(T blockEntity, Vec3d pos) {
        double distance = Vec3d.ofCenter(blockEntity.getPos()).squaredDistanceTo(pos);
        return distance < ConfigManager.getBlockEntityRenderDistanceSquared();
    }
}