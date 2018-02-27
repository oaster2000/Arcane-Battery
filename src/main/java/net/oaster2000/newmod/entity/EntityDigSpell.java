package net.oaster2000.newmod.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDigSpell extends EntityThrowable implements IEntityAdditionalSpawnData {
	public Entity shootingEntity;
	private float damage = 10.0F;
	private int breakingTime;
	private int prevBreakingTime;
	private int field_75358_j = -1;

	public EntityDigSpell(World par1World) {
		super(par1World);
		this.ignoreFrustumCheck = true;
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()));
	}

	public EntityDigSpell(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		this.shootingEntity = par2EntityLivingBase;
		this.ignoreFrustumCheck = true;
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()));
	}

	public EntityDigSpell(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		this.ignoreFrustumCheck = true;
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()));
	}

	protected float func_70182_d() {
		return 4.0F;
	}

	protected float getGravityVelocity() {
		return 0.0F;
	}

	protected void onImpact(RayTraceResult hitPos) {
		if (!this.world.isRemote) {
			if(shootingEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) shootingEntity;
			if (hitPos.getBlockPos() != null) {
				if (world.getBlockState(hitPos.getBlockPos()).getBlock() != null) {
					if (!world.getBlockState(hitPos.getBlockPos()).getBlock().equals(Blocks.AIR) && !world.getBlockState(hitPos.getBlockPos()).getBlock().equals(Blocks.BEDROCK)) {
						player.inventory.addItemStackToInventory(new ItemStack(world.getBlockState(hitPos.getBlockPos()).getBlock().getItemDropped(world.getBlockState(hitPos.getBlockPos()), rand, 0), world.getBlockState(hitPos.getBlockPos()).getBlock().quantityDropped(world.getBlockState(hitPos.getBlockPos()), 0, rand)));
						world.setBlockState(hitPos.getBlockPos(), Blocks.AIR.getDefaultState());
					}
				}
			}
			}
			setDead();
		}

	}

	public void onUpdate() {
		super.onUpdate();
		if (this.ticksExisted >= 100) {
			setDead();
		}
	}

	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.shootingEntity != null ? this.shootingEntity.getEntityId() : -1);
	}

	public void readSpawnData(ByteBuf buffer) {
		Entity shooter = this.world.getEntityByID(buffer.readInt());
		if ((shooter instanceof EntityLivingBase)) {
			this.shootingEntity = ((EntityLivingBase) shooter);
		}
	}
}
