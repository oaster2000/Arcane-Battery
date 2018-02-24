package net.oaster2000.newmod.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.utils.VectorUtils;

public class SpellItem extends BasicItem {

	String type;
	String strain;

	public SpellItem(String name, String type, String strain) {
		super(name);
		this.setMaxStackSize(1);
		this.type = type;
		this.strain = strain;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isRemote) {
			if (isSelected
					|| entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getHeldItemOffhand() == stack) {

				EntityPlayer player = (EntityPlayer) entityIn;
				Vec3d look = entityIn.getLookVec();
				look = VectorUtils.divide(look, 2);
				double x = entityIn.getPositionVector().x + look.x;
				double y = entityIn.getPositionVector().y + look.y + entityIn.getEyeHeight() - 0.25;
				double z = entityIn.getPositionVector().z + look.z;
				switch (type) {
				case "fire":
					worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0D, 0D, 0D, new int[0]);
					break;
				case "water":
					worldIn.spawnParticle(EnumParticleTypes.WATER_WAKE, x, y, z, 0D, 0D, 0D, new int[0]);
					break;
				case "dig":

					if (worldIn
							.getBlockState(new BlockPos(player.getPosition().getX(), player.getPosition().getY() - 1,
									player.getPosition().getZ()))
							.getBlock() != null
							&& worldIn
									.getBlockState(new BlockPos(player.getPosition().getX(),
											player.getPosition().getY() - 1, player.getPosition().getZ()))
									.getBlock() != Blocks.AIR) {
						int[] args = new int[] {
								Block.getIdFromBlock(worldIn
										.getBlockState(new BlockPos(player.getPosition().getX(),
												player.getPosition().getY() - 1, player.getPosition().getZ()))
										.getBlock()) };
						worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, x, y, z, 0D, 0D, 0D, args);
					}
					break;
				}
			}
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		switch (type) {
		case "fire":
			shootFireBeam(playerIn, worldIn);
			break;
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	public static void shootFireBeam(EntityPlayer player, World world)
  {
    EntityFireBeam entity = new EntityFireBeam(world, player);
    if (!world.isRemote)
    {
    	System.out.println("Entity Spawning Method Called");
      entity.setPositionAndRotation(player.posX, player.posY + 1.600000023841858D, player.posZ, player.rotationYaw, player.rotationPitch);
      world.spawnEntity(entity);
    }
  }

}
