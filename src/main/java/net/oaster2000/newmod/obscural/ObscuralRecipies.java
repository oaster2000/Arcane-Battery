package net.oaster2000.newmod.obscural;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.oaster2000.newmod.items.ModItems;

public class ObscuralRecipies {

	public static List<ObscuralRecipies> recipies = new ArrayList<ObscuralRecipies>();

	public static ObscuralRecipies powerCrystal = new ObscuralRecipies(ModItems.dCrystal, new ItemStack[] {new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE)}, ModItems.pCrystal, 30);
	public static ObscuralRecipies hopeCrystal = new ObscuralRecipies(ModItems.dCrystal, new ItemStack[] {new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4)}, ModItems.hCrystal, 30);
	public static ObscuralRecipies faithCrystal = new ObscuralRecipies(ModItems.dCrystal, new ItemStack[] {new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 2)}, ModItems.fCrystal, 30);
	public static ObscuralRecipies digSpell = new ObscuralRecipies(Items.PAPER, new ItemStack[] {new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.EMERALD_BLOCK), new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.DIAMOND_PICKAXE)}, ModItems.digSpell, 30);
	public static ObscuralRecipies fireSpell = new ObscuralRecipies(Items.PAPER, new ItemStack[] {new ItemStack(Items.FIRE_CHARGE), new ItemStack(Items.FIRE_CHARGE), new ItemStack(Items.FIRE_CHARGE), new ItemStack(Items.FIRE_CHARGE), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)}, ModItems.fireSpell, 30);
	public static ObscuralRecipies waterSpell = new ObscuralRecipies(Items.PAPER, new ItemStack[] {new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)}, ModItems.waterSpell, 30);
	
	public Item centralItem;
	public ItemStack[] outerItems;
	public Item resultItem;
	public int cost;

	public ObscuralRecipies(Item central, ItemStack[] outer, Item result, int cost) {
		centralItem = central;
		outerItems = outer;
		resultItem = result;
		recipies.add(this);
		this.cost = cost;
	}

	public static boolean isCentralItem(Item item) {
		for (int i = 0; i < recipies.size(); i++) {
			if (recipies.get(i).getCentralItem().equals(item)) {
				return true;
			}
		}
		return false;
	}

	public Item getCentralItem() {
		return centralItem;
	}

	public Item getResult() {
		return resultItem;
	}
	
	public int getCost() {
		return cost;
	}

	public Item[] getOuterItems() {
		Item[] stacks = new Item[outerItems.length];
		for(int i = 0; i < outerItems.length; i++) {
			stacks[i] = outerItems[i].getItem();
		}
		return stacks;
	}

	public int[] getOuterItemsData() {
		int[] data = new int[outerItems.length];
		for(int i = 0; i < outerItems.length; i++) {
			data[i] = outerItems[i].getMetadata();
		}
		return data;
	}
}
