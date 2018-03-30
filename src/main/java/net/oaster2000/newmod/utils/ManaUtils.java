package net.oaster2000.newmod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.oaster2000.newmod.capability.ArcaneBatteryPacketHandler;
import net.oaster2000.newmod.capability.ManaProvider;
import net.oaster2000.newmod.capability.ManaSyncMessage;

public class ManaUtils {

	public static void syncMana(EntityPlayer player) {
		if(player.world.isRemote) 
			return;
		EntityPlayerMP p1 = (EntityPlayerMP) player;
		ArcaneBatteryPacketHandler.INSTANCE.sendTo(
				new ManaSyncMessage(player.getCapability(ManaProvider.MANA_CAP, null).getMana()),
				p1);
	}

}
