package net.oaster2000.newmod.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oaster2000.newmod.items.ModItems;
import net.oaster2000.newmod.obscural.ObscuralRecipies;
import net.oaster2000.newmod.tileentity.TileEntityStonePedestal;

public class ObscuralBlock extends Block {

	public ObscuralBlock(String unlocalizedName, Material material, float hardness, float resistance) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(unlocalizedName);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}

	public ObscuralBlock(String unlocalizedName, float hardness, float resistance) {
		this(unlocalizedName, Material.CIRCUITS, hardness, resistance);
	}

	public ObscuralBlock(String unlocalizedName) {
		this(unlocalizedName, 2.0f, 10.0f);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.inventory.getCurrentItem().getItem().equals(ModItems.dCrystal)) {
			if (stuctureCheck(worldIn, pos)) {
				for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
					ItemStack stackInSlot = playerIn.inventory.getStackInSlot(i);
					ItemStack stack = new ItemStack(ModItems.dCrystal);
					if (stackInSlot.getItem().equals(stack.getItem())) {
						stack.shrink(1);
						playerIn.inventory.setInventorySlotContents(i, stack);
						break;
					}

				}
				structureChange(worldIn, pos);
			}

		}
		if (!worldIn.isRemote) {
			if (isItemCentral(playerIn.inventory.getCurrentItem().getItem())) {
				createItem(worldIn, playerIn.inventory.getCurrentItem().getItem(), pos, playerIn, state);
			}
		}
		return true;
	}

	public void createItem(World world, Item item, BlockPos pos, EntityPlayer playerIn, IBlockState state) {
		for (int i = 0; i < ObscuralRecipies.recipies.size(); i++) {
			if (ObscuralRecipies.recipies.get(i).getCentralItem().equals(item)) {
				if (checkRecipie(world, ObscuralRecipies.recipies.get(i), pos.getX(), pos.getY(), pos.getZ(), state))
					world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(),
							new ItemStack(ObscuralRecipies.recipies.get(i).getResult())));
				for (int j = 0; j < playerIn.inventory.getSizeInventory(); j++) {
					ItemStack stackInSlot = playerIn.inventory.getStackInSlot(j);
					ItemStack stack = new ItemStack(ObscuralRecipies.recipies.get(i).centralItem);
					if (stackInSlot.getItem().equals(stack.getItem())) {
						stack.shrink(1);
						playerIn.inventory.setInventorySlotContents(j, stack);
						break;
					}

				}
				break;
			}
		}
	}

	private boolean checkRecipie(World world, ObscuralRecipies recipe, int x, int y, int z, IBlockState state) {
		int numberOfItemsPresent = 0;

		TileEntityStonePedestal ped0 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x + 2, y, z + 1));
		TileEntityStonePedestal ped1 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x + 2, y, z - 1));
		TileEntityStonePedestal ped2 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x - 2, y, z + 1));
		TileEntityStonePedestal ped3 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x - 2, y, z - 1));
		TileEntityStonePedestal ped4 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x + 1, y, z + 2));
		TileEntityStonePedestal ped5 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x - 1, y, z + 2));
		TileEntityStonePedestal ped6 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x + 1, y, z - 2));
		TileEntityStonePedestal ped7 = (TileEntityStonePedestal) world.getTileEntity(new BlockPos(x - 1, y, z - 2));
		
		List<TileEntityStonePedestal> possibleOutputs = new ArrayList<TileEntityStonePedestal>();
		possibleOutputs.add(ped0);
		possibleOutputs.add(ped1);
		possibleOutputs.add(ped2);
		possibleOutputs.add(ped3);
		possibleOutputs.add(ped4);
		possibleOutputs.add(ped5);
		possibleOutputs.add(ped6);
		possibleOutputs.add(ped7);

		for (Item item : recipe.outerItems) {
			for (TileEntityStonePedestal te : possibleOutputs) {
				if (te.getItem().equals(item)) {
					te.setItemStack(ItemStack.EMPTY);
					numberOfItemsPresent++;
					possibleOutputs.remove(te);
					world.markBlockRangeForRenderUpdate(te.getPos(), te.getPos());
					world.notifyBlockUpdate(te.getPos(), world.getBlockState(te.getPos()), world.getBlockState(te.getPos()), 3);
					world.scheduleBlockUpdate(te.getPos(),te.getBlockType(),0,0);
					te.markDirty();
					break;
				}
			}
			continue;
		}

		if (numberOfItemsPresent == recipe.outerItems.length) {
			return true;
		}
		return false;
	}

	private boolean isItemCentral(Item item) {
		return ObscuralRecipies.isCentralItem(item);
	}

	private void structureChange(World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		world.setBlockState(new BlockPos(x + 2, y, z - 1), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x + 2, y, z + 1), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x - 2, y, z - 1), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x - 2, y, z + 1), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x + 1, y, z + 2), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x - 1, y, z + 2), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x + 1, y, z - 2), ModBlocks.stonePedestal.getDefaultState());
		world.setBlockState(new BlockPos(x - 1, y, z - 2), ModBlocks.stonePedestal.getDefaultState());
	}

	private boolean stuctureCheck(World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if (world.getBlockState(new BlockPos(x + 2, y, z - 1)).equals(Blocks.STONE.getDefaultState())) {
			if (world.getBlockState(new BlockPos(x + 2, y, z + 1)).equals(Blocks.STONE.getDefaultState())) {
				if (world.getBlockState(new BlockPos(x - 1, y, z + 2)).equals(Blocks.STONE.getDefaultState())) {
					if (world.getBlockState(new BlockPos(x + 1, y, z + 2)).equals(Blocks.STONE.getDefaultState())) {
						if (world.getBlockState(new BlockPos(x - 2, y, z + 1)).equals(Blocks.STONE.getDefaultState())) {
							if (world.getBlockState(new BlockPos(x - 2, y, z - 1))
									.equals(Blocks.STONE.getDefaultState())) {
								if (world.getBlockState(new BlockPos(x + 1, y, z - 2))
										.equals(Blocks.STONE.getDefaultState())) {
									if (world.getBlockState(new BlockPos(x - 1, y, z - 2))
											.equals(Blocks.STONE.getDefaultState())) {
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
