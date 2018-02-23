package net.oaster2000.newmod.entity;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFireBeam extends EntityThrowable implements IEntityAdditionalSpawnData {
	public Entity shootingEntity;
	private float damage = 10.0F;
	private int breakingTime;
	private int prevBreakingTime;
	private int field_75358_j = -1;

	public EntityFireBeam(World par1World) {
		super(par1World);
		this.setRenderDistanceWeight(10.0D);
		this.ignoreFrustumCheck = true;
	}

	public EntityFireBeam(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		this.shootingEntity = par2EntityLivingBase;
		this.setRenderDistanceWeight(10.0D);
		this.ignoreFrustumCheck = true;
	}

	public EntityFireBeam(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		this.setRenderDistanceWeight(10.0D);
		this.ignoreFrustumCheck = true;
	}

	protected float func_70182_d() {
		return 4.0F;
	}

	protected float getGravityVelocity() {
		return 0.0F;
	}

	protected void onImpact(RayTraceResult hitPos) {
		GameRules gameRules = this.world.getGameRules();
		if (!this.world.isRemote) {
			if (this.shootingEntity != null) {
				List list2 = this.world.getCollisionBoxes(this.shootingEntity,
						this.getCollisionBoundingBox().expand(2.0D, 2.0D, 2.0D));
				for (int i1 = 0; i1 < list2.size(); i1++) {
					Entity entity1 = (Entity) list2.get(i1);
					if ((entity1 instanceof EntityLivingBase)) {
						entity1.attackEntityFrom(DamageSource.MAGIC,
								this.damage);
						entity1.setFire(5);
					}
				}
				if (hitPos.entityHit != null) {
					if ((hitPos.entityHit instanceof EntityLivingBase)) {
						hitPos.entityHit.attackEntityFrom(DamageSource.MAGIC, this.damage);
						hitPos.entityHit.setFire(5);
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
	  
	  public void writeSpawnData(ByteBuf buffer)
	  {
	    buffer.writeInt(this.shootingEntity != null ? this.shootingEntity.getEntityId() : -1);
	  }
	  
	  public void readSpawnData(ByteBuf buffer)
	  {
	    Entity shooter = this.world.getEntityByID(buffer.readInt());
	    if ((shooter instanceof EntityLivingBase)) {
	      this.shootingEntity = ((EntityLivingBase)shooter);
	    }
	  }
}
