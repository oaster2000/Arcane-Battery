package net.oaster2000.newmod.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEventHandler {

	public static float renderTick;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		renderTick = event.renderTickTime;
	}
}
