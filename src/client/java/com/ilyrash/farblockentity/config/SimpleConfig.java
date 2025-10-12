package com.ilyrash.farblockentity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "farblockentity.json");

    public int renderDistanceChunks = 16; // Default: 16 chunks (256 blocks)

    public static SimpleConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                SimpleConfig loadedConfig = GSON.fromJson(reader, SimpleConfig.class);

                // Validate the loaded config - now 4 to 32 chunks
                if (loadedConfig.renderDistanceChunks >= 4 && loadedConfig.renderDistanceChunks <= 32) {
                    return loadedConfig;
                } else {
                    System.err.println("Invalid render distance in config: " + loadedConfig.renderDistanceChunks + ", using default");
                }
            } catch (Exception e) {
                System.err.println("Failed to load config file: " + e.getMessage());
            }
        }

        // Save default config
        SimpleConfig config = new SimpleConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
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