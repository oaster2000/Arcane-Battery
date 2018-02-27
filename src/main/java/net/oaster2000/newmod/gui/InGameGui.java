package net.oaster2000.newmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.oaster2000.newmod.capability.IMana;
import net.oaster2000.newmod.capability.ManaProvider;

public class InGameGui extends Gui{

	public InGameGui(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		IMana mana = mc.player.getCapability(ManaProvider.MANA_CAP, null);
		float currentMana = mana.getMana();
		drawCenteredString(mc.fontRenderer, "" + currentMana, width / 2, (height / 2) - 4,
				Integer.parseInt("CCCCCC", 16));
	}
}
