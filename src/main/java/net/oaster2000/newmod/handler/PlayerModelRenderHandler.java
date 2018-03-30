package net.oaster2000.newmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.oaster2000.newmod.items.SpellItem;

public class PlayerModelRenderHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent()
	public void renderModel(RenderPlayerEvent.Pre event) {
		if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof SpellItem
				|| event.getEntityPlayer().getHeldItemOffhand().getItem() instanceof SpellItem) {
			event.getRenderer().getMainModel().bipedRightArm.isHidden = true;
			event.getRenderer().getMainModel().bipedLeftArm.isHidden = true;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent()
	public void renderModel(RenderPlayerEvent.Post event) {
		if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof SpellItem
				|| event.getEntityPlayer().getHeldItemOffhand().getItem() instanceof SpellItem) {
			EntityPlayer player = event.getEntityPlayer();
			Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
			if (!player.isSneaking()) {
				event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointZ = -MathHelper
						.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointY = 20;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointX = -MathHelper
						.cos((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleX = 8.25f;
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleY = (float) -Math
						.toRadians(player.renderYawOffset);
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleZ = -0.25f;
				event.getRenderer().getMainModel().bipedRightArm.renderWithRotation(0.0625f);
				event.getRenderer().getMainModel().bipedRightArm.rotationPointY = 2;

				event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointZ = -MathHelper
						.sin((float) Math.toRadians(player.renderYawOffset)) * -5.0f;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointY = 20;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointX = -MathHelper
						.cos((float) Math.toRadians(player.renderYawOffset)) * -5.0f;
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleX = 8.25f;
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleY = (float) -Math
						.toRadians(player.renderYawOffset);
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleZ = 0.25f;
				event.getRenderer().getMainModel().bipedLeftArm.renderWithRotation(0.0625f);
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointY = 2;
			}else {
				event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointZ = -MathHelper
						.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointY = 14;
				event.getRenderer().getMainModel().bipedRightArm.rotationPointX = -MathHelper
						.cos((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleX = 8.25f;
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleY = (float) -Math
						.toRadians(player.renderYawOffset);
				event.getRenderer().getMainModel().bipedRightArm.rotateAngleZ = -0.25f;
				event.getRenderer().getMainModel().bipedRightArm.renderWithRotation(0.0625f);
				event.getRenderer().getMainModel().bipedRightArm.rotationPointY = 2;

				event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointZ = -MathHelper
						.sin((float) Math.toRadians(player.renderYawOffset)) * -5.0f;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointY = 14;
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointX = -MathHelper
						.cos((float) Math.toRadians(player.renderYawOffset)) * -5.0f;
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleX = 8.25f;
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleY = (float) -Math
						.toRadians(player.renderYawOffset);
				event.getRenderer().getMainModel().bipedLeftArm.rotateAngleZ = 0.25f;
				event.getRenderer().getMainModel().bipedLeftArm.renderWithRotation(0.0625f);
				event.getRenderer().getMainModel().bipedLeftArm.rotationPointY = 2;
			}

		}
	}
}
