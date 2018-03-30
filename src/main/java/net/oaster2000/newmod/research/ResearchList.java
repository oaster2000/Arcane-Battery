package net.oaster2000.newmod.research;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.oaster2000.newmod.blocks.ModBlocks;
import net.oaster2000.newmod.items.ModItems;
import net.oaster2000.newmod.research.Research.EnumResearchState;

public class ResearchList {
	
	public static final Research start = new Research(0, null, "ALL", ModItems.magicalTome, 10, 10, EnumResearchState.UNDISCOVERED, "The Journey Begins", "start", true);
	public static final Research obscural = new Research(1, start, "ALL", ModBlocks.obscural, 10, 40, EnumResearchState.HIDDEN, "Infusing the Crystals", "obscural", true);
	public static final Research hope = new Research(2, obscural, "HOPE", ModItems.hCrystal, 10, 80, EnumResearchState.HIDDEN, "Be Hopeful", "hope", true);
	public static final Research faith = new Research(3, obscural, "FAITH", ModItems.fCrystal, 40, 40, EnumResearchState.HIDDEN, "Be Faithful", "faith", true);
	public static final Research power = new Research(4, obscural, "POWER", ModItems.pCrystal, 40, 80, EnumResearchState.HIDDEN, "Be Powerful", "power", true);
	
	static List<Research> list = new ArrayList<Research>();
	
	public static void initList() {
		list.add(start);
		list.add(obscural);
		list.add(hope);
		list.add(faith);
		list.add(power);
	}
	
	public static List<Research> getAll(){
		return list;
	}
}
