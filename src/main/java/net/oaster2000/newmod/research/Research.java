package net.oaster2000.newmod.research;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public class Research {

	private final int id;
	private EnumResearchState state;
	private static final ResourceLocation ICON_TEXTURES = new ResourceLocation("newmod", "textures/gui/obscural_icons.png");
	private Minecraft mc = Minecraft.getMinecraft();
	public String type = "ALL";
    private final Research parent;
    private final List<Research> children = new ArrayList<Research>();
    private ItemStack icon;
    private String name;
    private String identifier;
    private boolean showCraftingOnPage;
    
    private boolean showToast;
    
    public int x;
    public int y;
	
	public Research(int id, @Nullable Research parent, String type, Item icon) {
		this(id, parent, type, icon, 0, 0, EnumResearchState.UNDISCOVERED, "", "", false);
	}
	
	public Research(int id, @Nullable Research parent, String type, Block icon, int x, int y, EnumResearchState state, String name, String identifier, boolean showCrafting) {
		this(id, parent, type, Item.getItemFromBlock(icon), x, y, state, name, identifier, showCrafting);
	}
	
	public Research(int id, @Nullable Research parent, String type, Item icon, int x, int y, EnumResearchState state, String name, String identifier, boolean showCrafting) {
		this.id = id;
		this.type = type;
		this.parent = parent;
		this.state = state;
		if(this.parent != null)this.parent.children.add(this);
		this.icon = new ItemStack(icon, 1);
		this.x = x;
		this.y = y;
		this.name = name;
		this.identifier = identifier;
		showCraftingOnPage = showCrafting;
		initShowToast(state);
	}
	
    private void initShowToast(EnumResearchState currState) {
    	switch(currState) {
    	case FOUND:
    		this.showToast = false;
    	case HIDDEN:
    		this.showToast = false;
    	case UNDISCOVERED:
    		this.showToast = true;
    	}
	}

	public Research getParent()
    {
        return this.parent;
    }
	
	public boolean shouldShowToast() {
		return this.showToast;
	}
	
	public void setShowToast(boolean toast) {
		this.showToast = toast;
	}
    
    public String getName()
    {
        return this.name;
    }
    
    public String getStringID()
    {
        return this.identifier;
    }
    
    public ItemStack getIcon() {
    	return icon;
    }
    
    public EnumResearchState getState() {
    	return state;
    }
    
    public void setState(EnumResearchState state) {
    	this.state = state;
    }
    
    public int getY() {
    	return x;
    }
    
    public int getX() {
    	return y;
    }
    
    public String getType() {
    	return type;
    }
    
    public List<Research> getChildren()
    {
        return this.children;
    }
    
    public boolean isChildrenEmpty()
    {
        return this.children.isEmpty();
    }
    
    public void addChild(Research advancementIn)
    {
        this.children.add(advancementIn);
    }
    
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else if (!(obj instanceof Research))
        {
            return false;
        }
        else
        {
        	Research research = (Research)obj;
            return this.id == research.id;
        }
    }
    
    public int getID() {
    	return id;
    }
    
    public boolean hasCrafting() {
    	return showCraftingOnPage;
    }
    
    public static enum EnumResearchState implements IStringSerializable {
		FOUND, UNDISCOVERED, HIDDEN;

		private EnumResearchState() {
		}

		public String getName() {
			return name().toLowerCase();
		}
	}

	public void writeToNBT(NBTTagCompound compound) {
		boolean completed = this.state.equals(EnumResearchState.FOUND);
		compound.setBoolean("complete", completed);
	}

	public void readFromNBT(NBTTagCompound compound) {
		boolean complete = compound.getBoolean("complete");
		if(complete) {
			this.setState(EnumResearchState.FOUND);
		}else {
			if(this.parent.getState().equals(EnumResearchState.FOUND)) {
				this.setState(EnumResearchState.UNDISCOVERED);
			}else {
				this.setState(EnumResearchState.HIDDEN);				
			}
		}
	}
}
