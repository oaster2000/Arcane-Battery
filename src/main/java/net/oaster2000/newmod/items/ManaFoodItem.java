package net.oaster2000.newmod.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.oaster2000.newmod.capability.IMana;
import net.oaster2000.newmod.capability.ManaProvider;

public class ManaFoodItem extends ItemFood{

	int manaRestore;
	
	public ManaFoodItem(int amount, float saturation, boolean isWolfFood, int manaRestore, String name) {
		super(amount, saturation, isWolfFood);
		this.manaRestore = manaRestore;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setAlwaysEdible();
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        IMana mana = entityLiving.getCapability(ManaProvider.MANA_CAP, null);
        if(mana.getMana() <= mana.getMaxMana()) {
        	if(mana.getMana() + manaRestore <= mana.getMaxMana()) {
        		mana.fill(manaRestore);
        	}else {
        		mana.set(mana.getMaxMana());
        	}
        }
        return stack;
    }
	
}
