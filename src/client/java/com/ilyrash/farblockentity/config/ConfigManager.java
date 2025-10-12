package com.ilyrash.farblockentity.config;

import com.ilyrash.farblockentity.Farblockentity;
import net.minecraft.client.MinecraftClient;

public class ConfigManager {
    private static SimpleConfig config;

    public static void loadConfig() {
        config = SimpleConfig.load();
    }

    public static SimpleConfig getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    public static int getBlockEntityRenderDistance() {
        return getConfig().getRenderDistanceBlocks();
    }

    public static double getBlockEntityRenderDistanceSquared() {
        return getConfig().getRenderDistanceSquared();
    }

    public static void setRenderDistanceChunks(int chunks) {
        if (chunks >= 1 && chunks <= 32) {
            getConfig().renderDistanceChunks = chunks;
            getConfig().save();
            Farblockentity.LOGGER.info("Block entity render distance changed to {} chunks ({} blocks)", chunks, chunks * 16);

            // Force refresh of block entity renderers
            refreshBlockEntityRenderers();
        }
    }

    private static void refreshBlockEntityRenderers() {
        // This forces Minecraft to reload block entity renderers with the new distance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.worldRenderer != null) {
            // Force chunk updates which will reload block entity render distances
            client.worldRenderer.scheduleTerrainUpdate();

            // If the world is loaded, we can also try to refresh the renderer cache
            if (client.world != null) {
                // This will cause block entities to re-check their render distance
                client.worldRenderer.reload();
            }
        }
    }
}