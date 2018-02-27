package net.oaster2000.newmod.entity.renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.handler.ClientEventHandler;

@SideOnly(Side.CLIENT)
public class RenderFireBeam extends Render {
	public RenderFireBeam(RenderManager renderManager) {
		super(renderManager);
	}

	public void doRender(Entity entity1, double x, double y, double z, float entityYaw, float partialTicks) {
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}