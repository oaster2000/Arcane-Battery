package net.oaster2000.newmod.crucible;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.oaster2000.newmod.items.ModItems;

public class CrucibleRecipe {

	public static List<CrucibleRecipe> list = new ArrayList<CrucibleRecipe>();

	public static CrucibleRecipe crystal = new CrucibleRecipe(ModItems.dGem, ModItems.dCrystal, 0);
	
	public Item baseItem;
	public Item resultItem;
	public int cost;

	public CrucibleRecipe(Item base, Item result, int cost) {
		baseItem = base;
		resultItem = result;
		list.add(this);
		this.cost = cost;
	}

	public static boolean isBaseItem(Item item) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBaseItem().equals(item)) {
				return true;
			}
		}
		return false;
	}

	public Item getBaseItem() {
		return baseItem;
	}

	public Item getResult() {
		return resultItem;
	}
	
	public int getCost() {
		return cost;
	}
}
