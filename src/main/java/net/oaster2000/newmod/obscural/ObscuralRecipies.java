package net.oaster2000.newmod.obscural;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.oaster2000.newmod.items.ModItems;

public class ObscuralRecipies {

	public static List<ObscuralRecipies> recipies = new ArrayList<ObscuralRecipies>();

	public static ObscuralRecipies powerCrystal = new ObscuralRecipies(ModItems.dCrystal, new Item[] {Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.REDSTONE}, ModItems.pCrystal);
	

	public Item centralItem;
	public Item[] outerItems;
	public Item resultItem;

	public ObscuralRecipies(Item central, Item[] outer, Item result) {
		centralItem = central;
		outerItems = outer;
		resultItem = result;
		recipies.add(this);
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

	public Item[] getOuterItems() {
		return outerItems;
	}
}
