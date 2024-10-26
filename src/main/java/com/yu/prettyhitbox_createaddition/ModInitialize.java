package com.yu.prettyhitbox_createaddition;

import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModInitialize implements ModInitializer {

	@Override
	public void onInitialize() {
		ModInitializeClient.LOGGER.info("ModInitialize initialized");
	}
}
