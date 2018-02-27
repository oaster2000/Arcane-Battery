package net.oaster2000.newmod.entity;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityWaterSphere extends EntityThrowable implements IEntityAdditionalSpawnData {
	public Entity shootingEntity;
	private float damage = 10.0F;
	private int breakingTime;
	private int prevBreakingTime;
	private int field_75358_j = -1;

	public EntityWaterSphere(World par1World) {
		super(par1World);
		this.ignoreFrustumCheck = true;
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()));
	}

	public EntityWaterSphere(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		this.shootingEntity = par2EntityLivingBase;
		this.ignoreFrustumCheck = true;
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()));
	}

	public EntityWaterSphere(World par1World, double par2, double par4, double par6) {
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
			if (this.shootingEntity != null) {
				if (hitPos.entityHit != null) {
					if ((hitPos.entityHit instanceof EntityLivingBase)) {
						EntityLiving entity = (EntityLiving) hitPos.entityHit;
						entity.attackEntityFrom(DamageSource.MAGIC, this.damage);
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS));
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
