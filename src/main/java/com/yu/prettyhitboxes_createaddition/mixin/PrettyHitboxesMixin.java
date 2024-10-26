package com.yu.prettyhitboxes_createaddition.mixin;


import com.yu.prettyhitboxes_createaddition.Config;


import me.shedaniel.autoconfig.AutoConfig;


import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class PrettyHitboxesMixin {
	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info){
		Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
		MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(config.hitboxesEnabledByDefault);
	}
}
