package net.oaster2000.newmod.tileentity;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class TESRStonePedestal
  extends TileEntitySpecialRenderer<TileEntityStonePedestal>
{
  public void render(@Nonnull TileEntityStonePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float unused)
  {
    if (!te.isInvalid() && te != null)
    {
     if (te.getItem() != null)
      {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.7D, z + 0.5D);
        GlStateManager.translate(0.0F, MathHelper.sin(((float)te.getWorld().getTotalWorldTime() + partialTicks) / 10.0F) * 0.1F + 0.1F, 0.0F);
        GlStateManager.scale(0.75D, 0.75D, 0.75D);
        float angle = ((float)te.getWorld().getTotalWorldTime() + partialTicks) / 20.0F * 57.295776F;
        GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(te.getItem()), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
      }
    }
  }
}
