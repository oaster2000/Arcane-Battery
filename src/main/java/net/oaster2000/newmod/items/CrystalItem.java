package net.oaster2000.newmod.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalItem extends BasicItem {

	public CrystalItem(String unlocalizedName) {
		super(unlocalizedName);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		this.setMaxDamage(128);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote) {
			if(worldIn.getBlockState(pos).equals(Blocks.BOOKSHELF.getDefaultState())) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.magicalTome)));
			}
		}
        return EnumActionResult.PASS;
    }

}
