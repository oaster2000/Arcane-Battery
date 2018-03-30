package net.oaster2000.newmod.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.oaster2000.newmod.utils.ManaUtils;

public class ManaEventHandler {
	
	@SubscribeEvent
	public void onDeath(PlayerLoggedInEvent event) {
			IMana mana = event.player.getCapability(ManaProvider.MANA_CAP, null);
			ManaUtils.syncMana(event.player);
	}

	@SubscribeEvent
	public void onDeath(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IMana mana = event.getOriginal().getCapability(ManaProvider.MANA_CAP, null);
			IMana newMana = event.getEntityPlayer().getCapability(ManaProvider.MANA_CAP, null);

			newMana.set(mana.getMana());
		}
	}

	@SubscribeEvent
	public void onPlayerSleep(PlayerSleepInBedEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if (player.world.isRemote)
			return;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		if (mana.getMana() < mana.getMaxMana()) {
			if (mana.getMana() + 50 < mana.getMaxMana()) {
				mana.fill(50);
			} else {
				mana.set(mana.getMaxMana());
			}
		}
		ManaUtils.syncMana(player);
	}

	@SubscribeEvent
	public void onPlayerFalls(LivingFallEvent event)
	{
		Entity entity = event.getEntity();
		if (entity.world.isRemote || !(entity instanceof EntityPlayerMP) || event.getDistance() < 3)
			return;
		EntityPlayer player = (EntityPlayer) entity;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		float points = mana.getMana();
		float cost = event.getDistance() * 2;
		if (points > cost)
		{
			mana.consume(cost);
			event.setCanceled(true);
		}
		ManaUtils.syncMana(player);
	}
}
