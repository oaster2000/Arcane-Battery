package net.oaster2000.newmod;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.oaster2000.newmod.blocks.ModBlocks;
import net.oaster2000.newmod.capability.ArcaneBatteryPacketHandler;
import net.oaster2000.newmod.crafting.CraftingHandler;
import net.oaster2000.newmod.creativetabs.TutorialTab;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.entity.renderer.RenderFireBeam;
import net.oaster2000.newmod.handler.GuiHandler;
import net.oaster2000.newmod.items.ModItems;
import net.oaster2000.newmod.tileentity.MaceratorTileEntity;
import net.oaster2000.newmod.tileentity.TESRStonePedestal;
import net.oaster2000.newmod.tileentity.TileEntityCrucible;
import net.oaster2000.newmod.tileentity.TileEntityDeconstructor;
import net.oaster2000.newmod.tileentity.TileEntityElectricFurnace;
import net.oaster2000.newmod.tileentity.TileEntityGenerator;
import net.oaster2000.newmod.tileentity.TileEntitySolarGenerator;
import net.oaster2000.newmod.tileentity.TileEntityStonePedestal;
import net.oaster2000.newmod.tileentity.TileEntityWire;
import net.oaster2000.newmod.world.NewModWorldGen;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

	public static final String MODID = "arcanebattery";
	public static final String MODNAME = "Arcane Battery";
	public static final String VERSION = "1.0.0";

	public static final TutorialTab creativeTab = new TutorialTab();

	@SidedProxy(clientSide = "net.oaster2000.newmod.ClientProxy", serverSide = "net.oaster2000.newmod.ServerProxy")
	public static CommonProxy proxy;

	@Instance
	public static Main instance = new Main();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
		ModItems.createItems();
		ModBlocks.createBlocks();
		ArcaneBatteryPacketHandler.init();
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {
		proxy.Init(e);
		CraftingHandler.addSmeltingRecipies();
		GameRegistry.registerWorldGenerator(new NewModWorldGen(), 0);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		GameRegistry.registerTileEntity(TileEntityCrucible.class, "TileEntityCrucible");
		GameRegistry.registerTileEntity(MaceratorTileEntity.class, "MaceratorTileEntity");
		GameRegistry.registerTileEntity(TileEntityGenerator.class, "TileEntityGenerator");
		GameRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire");
		GameRegistry.registerTileEntity(TileEntitySolarGenerator.class, "TileEntitySolarGenerator");
		GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, "TileEntityElectricFurnace");
		GameRegistry.registerTileEntity(TileEntityDeconstructor.class, "TileEntityDeconstructor");
		GameRegistry.registerTileEntity(TileEntityStonePedestal.class, "TileEntityStonePedestal");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
