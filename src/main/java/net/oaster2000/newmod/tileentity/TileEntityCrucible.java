package net.oaster2000.newmod.tileentity;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;

public class TileEntityCrucible extends TileEntity
{
    private Item crucibleItem;
    private int flowerPotData;

    public TileEntityCrucible()
    {
    }

    public TileEntityCrucible(Item potItem, int potData)
    {
        this.crucibleItem = potItem;
        this.flowerPotData = potData;
    }

    public static void registerFixesFlowerPot(DataFixer fixer)
    {
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(this.crucibleItem);
        compound.setString("Item", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setInteger("Data", this.flowerPotData);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Item", 8))
        {
            this.crucibleItem = Item.getByNameOrId(compound.getString("Item"));
        }
        else
        {
            this.crucibleItem = Item.getItemById(compound.getInteger("Item"));
        }

        this.flowerPotData = compound.getInteger("Data");
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    public void setItemStack(ItemStack stack)
    {
        this.crucibleItem = stack.getItem();
        this.flowerPotData = stack.getMetadata();
    }

    public ItemStack getFlowerItemStack()
    {
        return this.crucibleItem == null ? ItemStack.EMPTY : new ItemStack(this.crucibleItem, 1, this.flowerPotData);
    }

    @Nullable
    public Item getCrucibleItem()
    {
        return this.crucibleItem;
    }
    
    public void setCrucibleItemEmpty()
    {
        this.crucibleItem = null;
    }

    public int getFlowerPotData()
    {
        return this.flowerPotData;
    }
}