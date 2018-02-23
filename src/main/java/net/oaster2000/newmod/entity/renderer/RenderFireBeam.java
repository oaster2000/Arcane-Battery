package net.oaster2000.newmod.entity.renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
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
public class RenderFireBeam
  extends Render
{
	public RenderFireBeam(RenderManager renderManager)
    {
		super(renderManager);
    }

	public void doRender(Entity entity1, double x, double y, double z, float entityYaw, float partialTicks)
  {
    EntityFireBeam entity = (EntityFireBeam)entity1;
    EntityPlayer player = (EntityPlayer)entity.shootingEntity;
    EntityPlayer clientPlayer = Minecraft.getMinecraft().player;
    if ((player != null))
    {
      if (player.ticksExisted == 0)
      {
        player.lastTickPosX = player.posX;
        player.lastTickPosY = player.posY;
        player.lastTickPosZ = player.posZ;
      }
      double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
      double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
      double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
      x = posX;
      y = posY;
      z = posZ;
      
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      if ((player == clientPlayer) && (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
      {
        if (player.isSneaking()) {
          GL11.glTranslatef((float)x, (float)y - 0.05F, (float)z);
        }
      }
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bb = tessellator.getBuffer();
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glDisable(2884);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 32772);
      GL11.glAlphaFunc(516, 0.003921569F);
      for (int beam = 0; beam < 2; beam++)
      {
        float scale = 1.03F;
        double offset = player == clientPlayer ? -1.62D : 0.0D;
        Vec3d vec = getOffsetCoords(player, 0.0D, 0.0D, 0.0D, 1.0f);
        Vec3d src = vec.subtract(getBeamVec(player, beam, true, partialTicks)).addVector(0.0D, offset, 0.0D);
        Vec3d dst = vec.subtract(getBeamVec(player, beam, false, partialTicks)).addVector(0.0D, offset, 0.0D);
        if ((player == clientPlayer) && (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
        {
          if (player.isSneaking()) {
            src = vec.subtract(getOffsetCoords(player, 0.05F * (beam * 2 - 1) * scale, -5.4505806E-10D, -0.10000000149011612D, partialTicks).addVector(0.0D, 1.62F * scale, 0.0D)).addVector(0.0D, offset, 0.0D);
          } else {
            src = vec.subtract(getOffsetCoords(player, 0.05F * (beam * 2 - 1) * scale, -0.05000000074505806D, -0.10000000149011612D, partialTicks).addVector(0.0D, 1.62F * scale, 0.0D)).addVector(0.0D, offset, 0.0D);
          }
          scale = 1.0F;
        }
        int layers = 10 + Minecraft.getMinecraft().gameSettings.ambientOcclusion * 30;
        for (int count = 0; count <= layers; count++)
        {
          GL11.glPushMatrix();
          if (count < layers)
          {
            Color color = Color.RED;
            
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F / layers / 2.0F);
            GL11.glDepthMask(false);
          }
          else
          {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
          }
          double size = 0.25D + (count < layers ? count * (1.25D / layers) : 0.0D);
          double width = 0.0625D * size * scale;
          double height = 0.03125D * size * scale;
          double length = src.distanceTo(dst) + (count < layers ? count * (1.0D / layers) : 0.0D) * 0.0625D;
          
          GL11.glTranslated(src.x, src.y, src.z);
          if ((player == clientPlayer) && (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
          {
            GL11.glRotated(-median(player.rotationYaw, player.prevRotationYaw) - (beam * 2 - 1) * (32.0D / length) * 0.20999999344348907D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(median(player.rotationPitch, player.prevRotationPitch), 1.0D, 0.0D, 0.0D);
          }
          else
          {
            GL11.glRotated(-median(player.rotationYawHead, player.prevRotationYawHead) - (beam * 2 - 1) * (32.0D / length) * 0.20999999344348907D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(median(player.rotationPitch, player.prevRotationPitch), 1.0D, 0.0D, 0.0D);
          }
          bb.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
          bb.pos(-width, height, length);
          bb.pos(width, height, length);
          bb.pos(width, height, 0.0D);
          bb.pos(-width, height, 0.0D);
          bb.pos(width, -height, 0.0D);
          bb.pos(width, -height, length);
          bb.pos(-width, -height, length);
          bb.pos(-width, -height, 0.0D);
          bb.pos(-width, -height, 0.0D);
          bb.pos(-width, -height, length);
          bb.pos(-width, height, length);
          bb.pos(-width, height, 0.0D);
          bb.pos(width, height, length);
          bb.pos(width, -height, length);
          bb.pos(width, -height, 0.0D);
          bb.pos(width, height, 0.0D);
          bb.pos(width, -height, length);
          bb.pos(width, height, length);
          bb.pos(-width, height, length);
          bb.pos(-width, -height, length);
          bb.finishDrawing();;
          GL11.glPopMatrix();
        }
      }
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
    }
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return null;
  }
  
  private Vec3d getOffsetCoords(EntityPlayer player, double xOffset, double yOffset, double zOffset, float partialTicks)
  {
    Vec3d vec3 = new Vec3d(xOffset, yOffset, zOffset);
    vec3.rotatePitch(-(player.rotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks) * 3.1415927F / 180.0F);
    vec3.rotateYaw(-(player.rotationYaw - (player.rotationYaw - player.prevRotationYaw) * partialTicks) * 3.1415927F / 180.0F);
    
    return new Vec3d(player.posX + vec3.x, getAccuratePlayerY(player) + vec3.y, player.posZ + vec3.z);
  }
  
  private static double getAccuratePlayerY(EntityPlayer player)
  {
    return player.posY + player.getEyeHeight();
  }
  
  public Vec3d getBeamVec(EntityPlayer player, int side, boolean start, float partialTicks)
  {
    double range = 100.0D;
    float f = 1.62F;
    float scale = 1.0F;

    if (player.isSneaking()) {
      if (player == Minecraft.getMinecraft().player) {
        scale -= 0.05F;
      } else {
        scale -= 0.11F;
      }
    }
    Vec3d src = getOffsetCoords(player, 0.115F * (side * 2 - 1) * scale, 0.21F * scale, 0.235F * scale, partialTicks).addVector(0.0D, 1.4125F * scale - f, 0.0D);
    Vec3d src1 = getOffsetCoords(player, 0.0D, 0.21F * scale, 0.235F * scale, partialTicks).addVector(0.0D, 1.4125F * scale - f, 0.0D);
    Vec3d dst = getOffsetCoords(player, 0.0D, 0.21F * scale, (0.23499999940395355D + range) * scale, partialTicks).addVector(0.0D, 1.4125F * scale - f, 0.0D);
    
    RayTraceResult res = player.world.rayTraceBlocks(copy(src1), copy(dst));
    Vec3d vec3 = null;
    if (res == null) {
      vec3 = dst;
    } else {
      vec3 = res.hitVec;
    }
    if (start) {
      return src.addVector(0.0D, f, 0.0D);
    }
    return vec3.addVector(0.0D, f, 0.0D);
  }
  
  public Vec3d copy(Vec3d vec3)
  {
    return new Vec3d(vec3.x, vec3.y, vec3.z);
  }
  
  public float median(double curr, double prev)
  {
    return (float)(prev + (curr - prev) * ClientEventHandler.renderTick);
  }
}
