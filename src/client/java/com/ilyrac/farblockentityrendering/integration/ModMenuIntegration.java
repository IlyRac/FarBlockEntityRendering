package com.ilyrac.farblockentityrendering.integration;

import com.ilyrac.farblockentityrendering.config.ConfigManager;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SimpleScreen::new;
    }

    private static class SimpleScreen extends Screen {
        private final Screen parent;
        private ChunkSlider chunkSlider;

        protected SimpleScreen(Screen parent) {
            super(Component.literal("Far Block Entity Rendering"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int cx = width / 2;
            int cy = 55;

            int currentChunks = ConfigManager.getConfig().renderDistanceChunks;

            // Slider
            chunkSlider = new ChunkSlider(cx - 150, cy, 305, 20, currentChunks, 4, 32);
            addRenderableWidget(chunkSlider);

            // Preset buttons (two columns)
            int[] presets = {4, 8, 12, 16, 20, 24, 28, 32};
            for (int i = 0; i < presets.length; i++) {
                int preset = presets[i];
                int x = cx - 150 + (i % 2) * 155;
                int y = cy + 35 + (i / 2) * 25; // compact spacing
                addRenderableWidget(Button.builder(
                        Component.literal(preset + " chunks (" + (preset * 16) + " blocks)"),
                        b -> chunkSlider.setSliderValue(preset)
                ).bounds(x, y, 150, 20).build());
            }

            // Done button
            addRenderableWidget(Button.builder(
                    Component.literal("Done"),
                    b -> minecraft.setScreen(parent)
            ).bounds(cx - 60, cy + 160, 120, 20).build());
        }

        @Override
        public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            super.render(guiGraphics, mouseX, mouseY, partialTick);

            guiGraphics.drawCenteredString(this.font, this.title, width / 2, 15, 0xFFFFFFFF);

            Component warning = Component.literal("Warning: High values can significantly impact FPS in areas with many block entities.")
                    .withStyle(style -> style.withColor(0xFFFFFF00));

            guiGraphics.drawCenteredString(this.font, warning, this.width / 2, 30, -1);
        }

        private static class ChunkSlider extends AbstractSliderButton {
            private final int min;
            private final int max;

            protected ChunkSlider(int x, int y, int width, int height, int currentValue, int min, int max) {
                super(x, y, width, height, Component.literal(""), (currentValue - min) / (double) (max - min));
                this.min = min;
                this.max = max;
                updateMessage();
            }

            @Override
            protected void updateMessage() {
                int val = getValue();
                setMessage(Component.literal(val + " chunks (" + (val * 16) + " blocks)"));
            }

            @Override
            protected void applyValue() {
                ConfigManager.setRenderDistanceChunks(getValue());
            }

            private int getValue() {
                return (int) Math.round(this.value * (max - min) + min);
            }

            // update the slider
            public void setSliderValue(int newValue) {
                this.value = (newValue - min) / (double) (max - min);
                updateMessage();
                applyValue();
            }
        }
    }
}
