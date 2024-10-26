package com.yu.prettyhitboxes_createaddition;

import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ModInitializeClient implements ClientModInitializer {
	public static final String ID = "prettyhitboxes_createaddition";
	public static final String NAME = "Pretty Hitboxes-Create Addition";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitializeClient() {
		LOGGER.info("ModInitializeClient initialized");
		AutoConfig.register(Config.class, GsonConfigSerializer::new);
	}


}
