package net.oaster2000.newmod.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.oaster2000.newmod.Main;
import net.oaster2000.newmod.crucible.CrucibleRecipe;
import net.oaster2000.newmod.items.ModItems;
import net.oaster2000.newmod.tileentity.TileEntityCrucible;
import net.oaster2000.newmod.tileentity.TileEntityStonePedestal;

public class CrucibleBlock extends BlockContainer {

	public CrucibleBlock(String unlocalizedName, Material material, float hardness, float resistance) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName("arcanebattery:" + unlocalizedName);
		this.setCreativeTab(Main.creativeTab);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}

	public CrucibleBlock(String unlocalizedName, float hardness, float resistance) {
		this(unlocalizedName, Material.ROCK, hardness, resistance);
	}

	public CrucibleBlock(String unlocalizedName) {
		this(unlocalizedName, 2.0f, 10.0f);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		TileEntityCrucible tileentityflowerpot = this.getTileEntity(worldIn, pos);

		if (tileentityflowerpot == null) {
			return false;
		} else {
			ItemStack itemstack1 = tileentityflowerpot.getFlowerItemStack();

			if (!itemstack.getItem().equals(Items.BLAZE_POWDER)) {
				if (itemstack1.isEmpty()) {
					if (!itemstack.isEmpty()) {
						tileentityflowerpot.setItemStack(itemstack);

						if (!playerIn.capabilities.isCreativeMode) {
							itemstack.shrink(1);
						}
					}
				} else {
					if (itemstack.isEmpty()) {
						playerIn.setHeldItem(hand, itemstack1);
					} else if (!playerIn.addItemStackToInventory(itemstack1)) {
						playerIn.dropItem(itemstack1, false);
					}

					tileentityflowerpot.setItemStack(ItemStack.EMPTY);
				}
			} else {
				recipeCraft(worldIn, playerIn, tileentityflowerpot.getCrucibleItem(), tileentityflowerpot);
				tileentityflowerpot.setCrucibleItemEmpty();
			}

			tileentityflowerpot.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 3);
			return true;
		}
	}

	private void recipeCraft(World world, EntityPlayer player, Item crucibleItem, TileEntityCrucible te) {
		for (CrucibleRecipe r : CrucibleRecipe.list) {
			for (int l = 0; l < player.inventory.getSizeInventory(); l++) {
				if (player.inventory.getStackInSlot(l).equals(ItemStack.EMPTY)) {
					if (r.isBaseItem(crucibleItem)) {
						for (int j = 0; j < player.inventory.getSizeInventory(); j++) {
							ItemStack stackInSlot = player.inventory.getStackInSlot(j);
							if (stackInSlot.getItem().equals(Items.BLAZE_POWDER)) {
								stackInSlot.shrink(1);
								break;
							}
							
						}
						player.inventory.setInventorySlotContents(l, new ItemStack(r.getResult()));
						te.setCrucibleItemEmpty();
						break;
					}					
				}
			}
			break;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCrucible();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Nullable
	public TileEntityCrucible getTileEntity(World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof TileEntityCrucible ? (TileEntityCrucible) tileentity : null;
	}

}