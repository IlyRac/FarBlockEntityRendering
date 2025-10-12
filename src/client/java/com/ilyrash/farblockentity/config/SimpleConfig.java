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

    public int renderDistanceChunks = 16;

    public static SimpleConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, SimpleConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Save default config if it doesn't exist
        SimpleConfig config = new SimpleConfig();
        config.save();
        return config;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
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