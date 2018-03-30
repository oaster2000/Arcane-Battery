package net.oaster2000.newmod.capability;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ArcaneBatteryPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("aBattery");
	
	
	public static void init() {
		INSTANCE.registerMessage(ManaSyncMessage.Handler.class, ManaSyncMessage.class, 0, Side.CLIENT);
	}
}
