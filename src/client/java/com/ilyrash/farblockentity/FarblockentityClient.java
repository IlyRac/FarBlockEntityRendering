package com.ilyrash.farblockentity;

import com.ilyrash.farblockentity.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class FarblockentityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigManager.loadConfig();
        Farblockentity.LOGGER.info("FarBlockEntity Client Initialized!");
        Farblockentity.LOGGER.info("Block entities will render up to {} chunks ({} blocks)",
                ConfigManager.getConfig().renderDistanceChunks,
                ConfigManager.getBlockEntityRenderDistance());
    }
}