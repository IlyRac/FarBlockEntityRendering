package com.ilyrac.farblockentityrendering.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class SimpleConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            "farblockentityrendering.json"
    );

    public int renderDistanceChunks = 16;

    public static SimpleConfig load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                SimpleConfig cfg = GSON.fromJson(reader, SimpleConfig.class);
                if (cfg.renderDistanceChunks >= 4 && cfg.renderDistanceChunks <= 32)
                    return cfg;
            } catch (Exception ignored) {}
        }

        SimpleConfig cfg = new SimpleConfig();
        cfg.save();
        return cfg;
    }

    public void save() {
        try {
            //noinspection ResultOfMethodCallIgnored
            FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public int getRenderDistanceBlocks() {
        return renderDistanceChunks * 16;
    }
}
