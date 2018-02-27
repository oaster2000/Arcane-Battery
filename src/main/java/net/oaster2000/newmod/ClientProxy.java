package net.oaster2000.newmod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.oaster2000.newmod.client.render.blocks.BlockRenderRegister;
import net.oaster2000.newmod.client.render.items.ItemRenderRegister;
import net.oaster2000.newmod.entity.EntityDigSpell;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.entity.EntityWaterSphere;
import net.oaster2000.newmod.entity.renderer.RenderFireBeam;
import net.oaster2000.newmod.handler.ItemCraftingHandler;
import net.oaster2000.newmod.handler.PlayerModelRenderHandler;
import net.oaster2000.newmod.handler.ResearchUpdateHandler;
import net.oaster2000.newmod.tileentity.TESRStonePedestal;
import net.oaster2000.newmod.tileentity.TileEntityStonePedestal;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBeam.class, RenderFireBeam::new);
	}

	@Override
	public void Init(FMLInitializationEvent e){
		super.Init(e);
		MinecraftForge.EVENT_BUS.register(new ItemCraftingHandler());
		MinecraftForge.EVENT_BUS.register(new ResearchUpdateHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerModelRenderHandler());
		
		ItemRenderRegister.registerItemRenderer();
	    BlockRenderRegister.registerBlockRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStonePedestal.class, new TESRStonePedestal());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e){
		super.postInit(e);
	}
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		int ID = 0;
		EntityEntry firebeam = EntityEntryBuilder.create().entity(EntityFireBeam.class)
				.id(new ResourceLocation(Main.MODID, "firebeam"), ID++).name("firebeam").tracker(100, 100, true)
				.build();
		event.getRegistry().register(firebeam);
		EntityEntry digspell = EntityEntryBuilder.create().entity(EntityDigSpell.class)
				.id(new ResourceLocation(Main.MODID, "digspell"), ID++).name("digspell").tracker(100, 100, true)
				.build();
		event.getRegistry().register(digspell);
		EntityEntry waterspell = EntityEntryBuilder.create().entity(EntityWaterSphere.class)
				.id(new ResourceLocation(Main.MODID, "waterspell"), ID++).name("waterspell").tracker(100, 100, true)
				.build();
		event.getRegistry().register(digspell);
	}
	
}
