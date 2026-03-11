package com.ilyrash.farblockentity;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Farblockentity implements ModInitializer {
	public static final String MOD_ID = "farblockentity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("... FarBlockEntity Initialized ...");

	}
}