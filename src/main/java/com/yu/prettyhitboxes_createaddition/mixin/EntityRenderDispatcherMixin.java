package com.yu.prettyhitboxes_createaddition.mixin;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.yu.prettyhitboxes_createaddition.Config;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
	private static float clampedColorValue(int colorValue, boolean isAlpha) {
		return Math.min(1.0F, Math.max(0.0F, colorValue / (isAlpha ? 100.0F : 255.0F)));
	}

	private static boolean isTargeted(Entity entity) {
		if (MinecraftClient.getInstance().crosshairTarget != null && MinecraftClient.getInstance().crosshairTarget.getType() == HitResult.Type.ENTITY) {
			EntityHitResult target = (EntityHitResult) MinecraftClient.getInstance().crosshairTarget;
			Entity targetEntity = target.getEntity();
			return targetEntity.getUuid() == entity.getUuid();
		}

		return false;
	}

	/**
	 * @author ErrorGamer2000 and yu
	 * @reason We need to completely redefine how hitboxes are drawn
	 */
	@Inject(at = @At("HEAD"), method = "renderHitbox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;F)V", cancellable = true)
	private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) {
		Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

		Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());

		Config.Color bboxColor = config.boundingBoxColor;
		Config.Color targetColor = config.entityTargetedColor;
		//decide whether this entity should be rendered
		if (entity instanceof ItemEntity && !config.showItemHitboxes) {
			ci.cancel();
			return;
		}
		if (entity instanceof ItemFrameEntity && !config.showItemFrameHitboxes) {
			ci.cancel();
			return;
		}
		if (entity instanceof PaintingEntity && !config.showPaintingHitboxes) {
			ci.cancel();
			return;
		}
		if (entity instanceof BoatEntity && !config.showBoatHitboxes) {
			ci.cancel();
			return;
		}
		if (entity instanceof ThrownItemEntity && !config.showThrowableItemHitboxes) {
			ci.cancel();
			return;
		}
		if (AbstractContraptionEntity.class.isAssignableFrom(entity.getClass()) && !config.showCreateEntitiesHitboxes) {
			ci.cancel();
			return;
		}

		//render its hitbox and other stuffs
		if (config.showBoundingBox) {
			if (!(entity instanceof EnderDragonEntity)) {
				Config.Color color = entity instanceof ItemEntity ? config.itemHitboxColor : bboxColor;
				if (config.differentColorWhenTargeted && isTargeted(entity)) color = targetColor;
				WorldRenderer.drawBox(matrices, vertices, box, clampedColorValue(color.red, false), clampedColorValue(color.green, false), clampedColorValue(color.blue, false), clampedColorValue(color.alpha, true));
			} else if (config.showBigDragonBox) {
				EnderDragonPart[] parts = ((EnderDragonEntity) entity).getBodyParts();
				int partNum = parts.length;

				boolean targeted = false;
				for (int i = 0; i < partNum && !targeted; ++i) {
					if (isTargeted(parts[i])) targeted = true;
				}

				Config.Color color = bboxColor;
				if (targeted) color = targetColor;
				WorldRenderer.drawBox(matrices, vertices, box, clampedColorValue(color.red, false), clampedColorValue(color.green, false), clampedColorValue(color.blue, false), clampedColorValue(color.alpha, true));
			}
		}

		if (entity instanceof EnderDragonEntity) {
			double d = -MathHelper.lerp((double) tickDelta, entity.lastRenderX, entity.getX());
			double e = -MathHelper.lerp((double) tickDelta, entity.lastRenderY, entity.getY());
			double f = -MathHelper.lerp((double) tickDelta, entity.lastRenderZ, entity.getZ());
			EnderDragonPart[] var11 = ((EnderDragonEntity) entity).getBodyParts();
			int var12 = var11.length;

			for (int var13 = 0; var13 < var12; ++var13) {
				Config.Color color = config.dragonPartColor;
				EnderDragonPart enderDragonPart = var11[var13];
				if (config.differentColorWhenTargeted && isTargeted(enderDragonPart)) color = targetColor;
				matrices.push();
				double g = d + MathHelper.lerp((double) tickDelta, enderDragonPart.lastRenderX, enderDragonPart.getX());
				double h = e + MathHelper.lerp((double) tickDelta, enderDragonPart.lastRenderY, enderDragonPart.getY());
				double i = f + MathHelper.lerp((double) tickDelta, enderDragonPart.lastRenderZ, enderDragonPart.getZ());
				matrices.translate(g, h, i);
				if (config.showBoundingBox)
					WorldRenderer.drawBox(matrices, vertices, enderDragonPart.getBoundingBox().offset(-enderDragonPart.getX(), -enderDragonPart.getY(), -enderDragonPart.getZ()), clampedColorValue(color.red, false), clampedColorValue(color.green, false), clampedColorValue(color.blue, false), clampedColorValue(color.alpha, true));
				matrices.pop();
			}
		}

		if (entity instanceof LivingEntity && config.showEyeHeight) {
			Config.Color eyeHeightColor = config.eyeHeightColor;
			float j = 0.01F;
			if (!(entity instanceof EnderDragonEntity) || config.showBigDragonBox) {
				WorldRenderer.drawBox(matrices, vertices, box.minX, (double) (entity.getStandingEyeHeight() - 0.01F), box.minZ, box.maxX, (double) (entity.getStandingEyeHeight() + 0.01F), box.maxZ, clampedColorValue(eyeHeightColor.red, false), clampedColorValue(eyeHeightColor.green, false), clampedColorValue(eyeHeightColor.blue, false), clampedColorValue(eyeHeightColor.alpha, true));
			}
		}

		Vec3d vec3d = entity.getRotationVec(tickDelta);
		Matrix4f matrix4f = matrices.peek().getPositionMatrix();
		Matrix3f matrix3f = matrices.peek().getNormalMatrix();




		if (config.showEntityRotationVector) {
			Config.Color rotationVectorColor = config.entityRotationVectorColor;
			if (!(entity instanceof EnderDragonEntity) || config.showBigDragonBox) {
				vertices.vertex(matrix4f, 0.0F, entity.getStandingEyeHeight(), 0.0F).color(Math.min(255, Math.max(0, rotationVectorColor.red)), Math.min(255, Math.max(0, rotationVectorColor.green)), Math.min(255, Math.max(0, rotationVectorColor.blue)), Math.min(255, Math.max(0, (rotationVectorColor.alpha * 255) / 100))).normal(matrix3f, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z).next();
				vertices.vertex(matrix4f, (float) (vec3d.x * 2.0), (float) ((double) entity.getStandingEyeHeight() + vec3d.y * 2.0), (float) (vec3d.z * 2.0)).color(Math.min(255, Math.max(0, rotationVectorColor.red)), Math.min(255, Math.max(0, rotationVectorColor.green)), Math.min(255, Math.max(0, rotationVectorColor.blue)), Math.min(255, Math.max(0, (rotationVectorColor.alpha * 255) / 100))).normal(matrix3f, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z).next();
			}

		}

		ci.cancel();
		return;
	}
}
