package net.oaster2000.newmod.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oaster2000.newmod.tileentity.TileEntityStonePedestal;

public class StonePedestalBlock extends BlockContainer {

	public static boolean keepInventory;

	public StonePedestalBlock(String unlocalizeName) {
		super(Material.IRON);
		this.setUnlocalizedName(unlocalizeName);
		this.setRegistryName(unlocalizeName);
		this.setHardness(5F);
		this.setResistance(5F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.METAL);
	}

	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.stonePedestal);
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		TileEntityStonePedestal tileentityflowerpot = this.getTileEntity(worldIn, pos);

		if (tileentityflowerpot == null) {
			return false;
		} else {
			ItemStack itemstack1 = tileentityflowerpot.getFlowerItemStack();

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

			tileentityflowerpot.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 3);
			return true;
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the
	 * block.
	 */
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		Item item = null;
		return new TileEntityStonePedestal(item, meta);
	}

	@Nullable
	public TileEntityStonePedestal getTileEntity(World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof TileEntityStonePedestal ? (TileEntityStonePedestal) tileentity : null;
	}

	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(ModBlocks.stonePedestal);
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}