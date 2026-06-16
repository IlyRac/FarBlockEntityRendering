package com.ilyrac.farblockentityrendering.client;

import com.ilyrac.farblockentityrendering.FarBlockEntityRendering;
import com.ilyrac.farblockentityrendering.client.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class FarBlockEntityRenderingClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ConfigManager.loadConfig();
		FarBlockEntityRendering.LOGGER.info("Client initialized — BE render distance = {} blocks",
				ConfigManager.getBlockEntityRenderDistance());
	}
}
