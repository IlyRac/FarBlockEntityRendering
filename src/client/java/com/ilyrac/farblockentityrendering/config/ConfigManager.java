package com.ilyrac.farblockentityrendering.config;

import com.ilyrac.farblockentityrendering.FarBlockEntityRendering;
import net.minecraft.client.Minecraft;

public class ConfigManager {

    private static SimpleConfig config;
    private static boolean initialized;

    public static void loadConfig() {
        try {
            config = SimpleConfig.load();
            initialized = true;
        } catch (Exception e) {
            FarBlockEntityRendering.LOGGER.error("Failed to load config, using defaults", e);
            config = new SimpleConfig();
            initialized = false;
        }
    }

    public static SimpleConfig getConfig() {
        if (!initialized) loadConfig();
        return config;
    }

    public static int getBlockEntityRenderDistance() {
        return Math.max(64, getConfig().getRenderDistanceBlocks());
    }

    public static double getBlockEntityRenderDistanceSquared() {
        int d = getBlockEntityRenderDistance();
        return (double) d * d;
    }

    public static void setRenderDistanceChunks(int chunks) {
        if (chunks < 4 || chunks > 32) return;

        getConfig().renderDistanceChunks = chunks;
        getConfig().save();

        refreshRenderers();

        FarBlockEntityRendering.LOGGER.info("Block Entity render distance set to {} chunks ({} blocks)",
                chunks, chunks * 16);
    }

    private static void refreshRenderers() {
        try {
            Minecraft.getInstance().levelRenderer.allChanged();
        } catch (Exception e) {
            FarBlockEntityRendering.LOGGER.warn("Renderer refresh failed", e);
        }
    }
}
