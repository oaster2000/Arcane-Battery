package net.oaster2000.newmod.tileentity;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;

public class TileEntityStonePedestal extends TileEntity
{
    private Item flowerPotItem;

    public TileEntityStonePedestal()
    {
    }

    public TileEntityStonePedestal(Item potItem)
    {
        this.flowerPotItem = potItem;
    }

    public static void registerFixesFlowerPot(DataFixer fixer)
    {
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(this.flowerPotItem);
        compound.setString("Item", resourcelocation == null ? "" : resourcelocation.toString());
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Item", 8))
        {
            this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
        }
        else
        {
            this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
        }
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    public void setItemStack(ItemStack stack)
    {
        this.flowerPotItem = stack.getItem();
    }

    public void setItem(Item item)
    {
        this.flowerPotItem = item;
    }

    public ItemStack getItemStack()
    {
        return this.flowerPotItem == null ? ItemStack.EMPTY : new ItemStack(this.flowerPotItem, 1);
    }

    @Nullable
    public Item getItem()
    {
        return this.flowerPotItem;
    }
}