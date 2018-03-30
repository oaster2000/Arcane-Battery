package net.oaster2000.newmod.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.oaster2000.newmod.capability.IMana;
import net.oaster2000.newmod.capability.ManaProvider;
import net.oaster2000.newmod.entity.EntityDigSpell;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.entity.EntityWaterSphere;
import net.oaster2000.newmod.utils.ManaUtils;
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
				case "armor":
					worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, x, y, z, 0D, 0D, 0D, new int[] {Block.getIdFromBlock(Blocks.DIRT)});
					break;
				}
			}
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
		if (mana.getMana() > 0) {
			if (!playerIn.isSneaking()) {
				switch (type) {
				case "fire":
					if (mana.getMana() >= 5) {
						shootFireBeam(playerIn, worldIn);
						decreaseMana(5, playerIn);
					}
					break;
				case "dig":
					if (mana.getMana() >= 5) {
						shootDigSpell(playerIn, worldIn);
						decreaseMana(5, playerIn);
					}
					break;
				case "water":
					if (mana.getMana() >= 5) {
						shootWaterSphere(playerIn, worldIn);
						decreaseMana(5, playerIn);
					}
					break;
				case "armor":
					if (mana.getMana() >= 5) {
						createArmor(playerIn, worldIn);
						decreaseMana(5, playerIn);
					}
					break;
				}
			} else {
				switch (type) {
				case "dig":
					if (mana.getMana() >= 15) {
						dig3by3(playerIn, worldIn);
						decreaseMana(15, playerIn);
					}
					break;
				case "water":
					if (mana.getMana() >= 1 && playerIn.isBurning()) {
						playerIn.extinguish();
						decreaseMana(1, playerIn);
					}
					break;
				}
			}
			ManaUtils.syncMana(playerIn);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	private void createArmor(EntityPlayer playerIn, World worldIn) {
		playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 3));
	}

	private void decreaseMana(int i, EntityPlayer player) {
		if (player.world.isRemote)
			return;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		mana.consume(i);
	}

	private void dig3by3(EntityPlayer playerIn, World worldIn) {
		BlockPos centre = new BlockPos(playerIn.getPosition().getX(), playerIn.getPosition().getY() - 1,
				playerIn.getPosition().getZ());
		if (worldIn.getBlockState(centre) != null) {
			playerIn.inventory.addItemStackToInventory(new ItemStack(
					worldIn.getBlockState(centre).getBlock().getItemDropped(worldIn.getBlockState(centre), itemRand, 0),
					worldIn.getBlockState(centre).getBlock().quantityDropped(worldIn.getBlockState(centre), 0,
							itemRand)));
			playerIn.inventory.addItemStackToInventory(new ItemStack(
					worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() + 1)).getBlock()
							.getItemDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() + 1)),
									itemRand, 0),
					worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() + 1)).getBlock()
							.quantityDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() + 1)),
									0, itemRand)));
			playerIn.inventory
					.addItemStackToInventory(
							new ItemStack(
									worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ()))
											.getBlock()
											.getItemDropped(worldIn.getBlockState(
													new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ())),
													itemRand, 0),
									worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ()))
											.getBlock()
											.quantityDropped(worldIn.getBlockState(
													new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ())), 0,
													itemRand)));
			playerIn.inventory.addItemStackToInventory(new ItemStack(
					worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() - 1)).getBlock()
							.getItemDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() - 1)),
									itemRand, 0),
					worldIn.getBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() - 1)).getBlock()
							.quantityDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() - 1)),
									0, itemRand)));
			playerIn.inventory
					.addItemStackToInventory(
							new ItemStack(
									worldIn.getBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() + 1))
											.getBlock()
											.getItemDropped(worldIn.getBlockState(
													new BlockPos(centre.getX(), centre.getY(), centre.getZ() + 1)),
													itemRand, 0),
									worldIn.getBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() + 1))
											.getBlock()
											.quantityDropped(worldIn.getBlockState(
													new BlockPos(centre.getX(), centre.getY(), centre.getZ() + 1)), 0,
													itemRand)));
			playerIn.inventory
					.addItemStackToInventory(
							new ItemStack(
									worldIn.getBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() - 1))
											.getBlock()
											.getItemDropped(worldIn.getBlockState(
													new BlockPos(centre.getX(), centre.getY(), centre.getZ() - 1)),
													itemRand, 0),
									worldIn.getBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() - 1))
											.getBlock()
											.quantityDropped(worldIn.getBlockState(
													new BlockPos(centre.getX(), centre.getY(), centre.getZ() - 1)), 0,
													itemRand)));
			playerIn.inventory.addItemStackToInventory(new ItemStack(
					worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() + 1)).getBlock()
							.getItemDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() + 1)),
									itemRand, 0),
					worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() + 1)).getBlock()
							.quantityDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() + 1)),
									0, itemRand)));
			playerIn.inventory
					.addItemStackToInventory(
							new ItemStack(
									worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ()))
											.getBlock()
											.getItemDropped(worldIn.getBlockState(
													new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ())),
													itemRand, 0),
									worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ()))
											.getBlock()
											.quantityDropped(worldIn.getBlockState(
													new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ())), 0,
													itemRand)));
			playerIn.inventory.addItemStackToInventory(new ItemStack(
					worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() - 1)).getBlock()
							.getItemDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() - 1)),
									itemRand, 0),
					worldIn.getBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() - 1)).getBlock()
							.quantityDropped(
									worldIn.getBlockState(
											new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() - 1)),
									0, itemRand)));

			worldIn.setBlockState(centre, Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() - 1),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ()),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() + 1, centre.getY(), centre.getZ() + 1),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() + 1),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX(), centre.getY(), centre.getZ() - 1),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() + 1),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ()),
					Blocks.AIR.getDefaultState());
			worldIn.setBlockState(new BlockPos(centre.getX() - 1, centre.getY(), centre.getZ() - 1),
					Blocks.AIR.getDefaultState());
		}
	}

	public static void shootFireBeam(EntityPlayer player, World world) {
		if (!world.isRemote) {
			EntityFireBeam entity = new EntityFireBeam(world, player);
			entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entity);
		}
	}

	public static void shootWaterSphere(EntityPlayer player, World world) {
		if (!world.isRemote) {
			EntityWaterSphere entity = new EntityWaterSphere(world, player);
			entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entity);
		}
	}

	public static void shootDigSpell(EntityPlayer player, World world) {
		if (!world.isRemote) {
			EntityDigSpell entity = new EntityDigSpell(world, player);
			entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entity);
		}
	}

}
