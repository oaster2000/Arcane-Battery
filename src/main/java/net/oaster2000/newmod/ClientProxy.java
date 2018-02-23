package net.oaster2000.newmod;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.oaster2000.newmod.client.render.blocks.BlockRenderRegister;
import net.oaster2000.newmod.client.render.items.ItemRenderRegister;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.entity.renderer.RenderFireBeam;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBeam.class, RenderFireBeam::new);
	}

	@Override
	public void Init(FMLInitializationEvent e){
		super.Init(e);
		
		ItemRenderRegister.registerItemRenderer();
	    BlockRenderRegister.registerBlockRenderer();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e){
		super.postInit(e);
	}
	
}
