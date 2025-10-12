package com.ilyrash.farblockentity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilyrash.farblockentity.Farblockentity;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "farblockentity.json");

    public int renderDistanceChunks = 16;

    public static SimpleConfig load() {
        // Create default config first
        SimpleConfig defaultConfig = new SimpleConfig();

        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                SimpleConfig loadedConfig = GSON.fromJson(reader, SimpleConfig.class);

                // Validate loaded config
                if (loadedConfig.renderDistanceChunks >= 1 && loadedConfig.renderDistanceChunks <= 32) {
                    return loadedConfig;
                } else {
                    Farblockentity.LOGGER.warn("Invalid render distance in config: {}, using default", loadedConfig.renderDistanceChunks);
                }
            } catch (Exception e) {
                Farblockentity.LOGGER.error("Failed to load config file: {}", e.getMessage());
            }
        }

        // Save default config
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            Farblockentity.LOGGER.error("Failed to save config: {}", e.getMessage());
        }
    }

    public int getRenderDistanceBlocks() {
        return renderDistanceChunks * 16;
    }

    public double getRenderDistanceSquared() {
        int blocks = getRenderDistanceBlocks();
        return (double) blocks * blocks;
    }
}