package net.oaster2000.newmod.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.oaster2000.newmod.utils.RecipeReference;

public class ResearchGui extends GuiScreen {

	private static final ResourceLocation BG = new ResourceLocation("arcanebattery", "textures/gui/researchpage.png");
	private static final ResourceLocation ICONS = new ResourceLocation("arcanebattery",
			"textures/gui/research_crafting.png");

	private String name;
	private boolean showsCrafting;
	private int x;
	private int y;

	public ResearchGui(String name, boolean showCrafting, int x, int y) {
		this.name = name;
		showsCrafting = showCrafting;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		int i = (this.width - 256) / 2;
		int j = (this.height - 256) / 2;
		this.mc.getTextureManager().bindTexture(BG);
		this.drawTexturedModalRect(i - 4, j - 3, 0, 0, 256, 256);
		this.mc.getTextureManager().bindTexture(ICONS);
		if (showsCrafting) {
			this.drawTexturedModalRect(i + 139, j + 48, 0, 0, 96, 96);
			this.drawTexturedModalRect(i + 174, j + 18, 96, 0, 26, 26);
			renderRecipe(i, j);
		}
		drawText(i, j);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawText(int i, int j) {
		fontRenderer.drawStringWithShadow(I18n.format("research." + name + ".title"), i + 8, j + 8, 0xffcccccc);
		fontRenderer.drawSplitString(I18n.format("research." + name + ".desc"), i + 8, j + 28, 115, 0xffcccccc);
	}

	private void renderRecipe(int guiX, int guiY) {
		Item[] recipe = null;

		switch (name) {
		case "start":
			recipe = RecipeReference.obscural;
		}

		if (recipe != null) {
			int itemId = 0;
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					int ix = ((x * 33) + 7) + guiX + 139;
					int iy = ((y * 33) + 7) + guiY + 48;
					RenderHelper.enableGUIStandardItemLighting();
					Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(recipe[itemId]), ix, iy);
					itemId++;
				}
			}
			RenderHelper.enableGUIStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(recipe[itemId]), guiX + 179, guiY + 18);
		}
	}
}
