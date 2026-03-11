package com.ilyrash.farblockentity.config;

import com.ilyrash.farblockentity.Farblockentity;
import net.minecraft.client.MinecraftClient;

public class ConfigManager {
    private static SimpleConfig config;
    private static boolean initialized = false;

    public static void loadConfig() {
        try {
            config = SimpleConfig.load();
            initialized = true;
            Farblockentity.LOGGER.info("FarBlockEntity config loaded: {} chunks ({} blocks)",
                    config.renderDistanceChunks, config.getRenderDistanceBlocks());
        } catch (Exception e) {
            Farblockentity.LOGGER.error("Failed to load config, using defaults", e);
            config = new SimpleConfig();
            initialized = false;
        }
    }

    public static SimpleConfig getConfig() {
        if (!initialized) {
            loadConfig();
        }
        return config;
    }

    public static int getBlockEntityRenderDistance() {
        try {
            return Math.max(64, getConfig().getRenderDistanceBlocks()); // Minimum 64 blocks (4 chunks)
        } catch (Exception e) {
            Farblockentity.LOGGER.error("Error getting render distance, using default", e);
            return 256;
        }
    }

    public static double getBlockEntityRenderDistanceSquared() {
        int distance = getBlockEntityRenderDistance();
        return (double) distance * distance;
    }

    public static void setRenderDistanceChunks(int chunks) {
        // Updated range: 4 to 32 chunks
        if (chunks >= 4 && chunks <= 32) {
            try {
                getConfig().renderDistanceChunks = chunks;
                getConfig().save();
                initialized = true;

                int blocks = chunks * 16;
                Farblockentity.LOGGER.info("Block entity render distance changed to {} chunks ({} blocks)", chunks, blocks);

                refreshBlockEntityRenderers();
            } catch (Exception e) {
                Farblockentity.LOGGER.error("Failed to save config", e);
            }
        } else {
            Farblockentity.LOGGER.warn("Invalid render distance chunks: {}, must be between 4 and 32", chunks);
        }
    }

    private static void refreshBlockEntityRenderers() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.worldRenderer != null) {
                client.worldRenderer.scheduleTerrainUpdate();
            }
        } catch (Exception e) {
            Farblockentity.LOGGER.debug("Could not refresh block entity renderers", e);
        }
    }
}