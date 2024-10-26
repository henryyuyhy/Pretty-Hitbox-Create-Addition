package com.yu.prettyhitboxes_createaddition.mixin;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderDispatcher.class)
public interface EntityRenderDispatcherReadable { //umm I don't know what it's for but since it exists in errorgames2000's project then it should be necessary
	@Accessor("renderHitboxes")
	boolean renderHitboxes();
}
