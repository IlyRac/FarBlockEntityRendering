package com.ilyrash.farblockentity.integration;

import com.ilyrash.farblockentity.config.ConfigManager;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SimpleConfigScreen::new;
    }

    private static class SimpleConfigScreen extends Screen {
        private final Screen parent;

        protected SimpleConfigScreen(Screen parent) {
            super(Text.literal("Far Block Entity Config"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            super.init();

            int centerX = this.width / 2;
            int centerY = this.height / 2;

            int currentChunks = ConfigManager.getConfig().renderDistanceChunks;

            // Title
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Block Entity Render Distance"), button -> {})
                    .dimensions(centerX - 150, centerY - 80, 300, 20)
                    .build()).active = false;

            // Current setting display
            this.addDrawableChild(ButtonWidget.builder(
                            Text.literal("Current: " + currentChunks + " chunks (" + (currentChunks * 16) + " blocks)"),
                            button -> {})
                    .dimensions(centerX - 150, centerY - 50, 300, 20)
                    .build()).active = false;

            // Increase button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("↑ Increase"), button -> {
                if (currentChunks < 32) {
                    ConfigManager.setRenderDistanceChunks(currentChunks + 1);
                    if (this.client != null) {
                        this.client.setScreen(new SimpleConfigScreen(parent));
                    }
                }
            }).dimensions(centerX - 150, centerY - 20, 145, 20).build());

            // Decrease button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("↓ Decrease"), button -> {
                if (currentChunks > 4) {
                    ConfigManager.setRenderDistanceChunks(currentChunks - 1);
                    if (this.client != null) {
                        this.client.setScreen(new SimpleConfigScreen(parent));
                    }
                }
            }).dimensions(centerX + 5, centerY - 20, 145, 20).build());

            // Preset buttons - now from 4 to 32 chunks
            this.addDrawableChild(ButtonWidget.builder(Text.literal("4 chunks (64 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(4);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX - 150, centerY + 10, 145, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("8 chunks (128 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(8);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX + 5, centerY + 10, 145, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("12 chunks (192 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(12);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX - 150, centerY + 40, 145, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("16 chunks (256 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(16);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX + 5, centerY + 40, 145, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("24 chunks (384 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(24);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX - 150, centerY + 70, 145, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("32 chunks (512 blocks)"), button -> {
                ConfigManager.setRenderDistanceChunks(32);
                if (this.client != null) {
                    this.client.setScreen(new SimpleConfigScreen(parent));
                }
            }).dimensions(centerX + 5, centerY + 70, 145, 20).build());

            // Apply and Close button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Apply and Close"), button -> {
                if (this.client != null) {
                    this.client.setScreen(parent);
                }
            }).dimensions(centerX - 75, centerY + 110, 150, 20).build());
        }

        @Override
        public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
            context.fill(0, 0, this.width, this.height, 0xCC000000);
            context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
            super.render(context, mouseX, mouseY, delta);
        }
    }
}