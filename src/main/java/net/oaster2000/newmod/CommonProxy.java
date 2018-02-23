package net.oaster2000.newmod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.oaster2000.newmod.capability.CapabilityHandler;
import net.oaster2000.newmod.capability.IMana;
import net.oaster2000.newmod.capability.Mana;
import net.oaster2000.newmod.capability.ManaEventHandler;
import net.oaster2000.newmod.capability.ManaStorage;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.handler.ItemCraftingHandler;
import net.oaster2000.newmod.handler.PlayerModelRenderHandler;
import net.oaster2000.newmod.handler.ResearchUpdateHandler;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, "firebeam"), EntityFireBeam.class, "firebeam",
				188000, Main.instance, 100, 100, true);
	}

	public void Init(FMLInitializationEvent e) {
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana.class);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new ManaEventHandler());
		MinecraftForge.EVENT_BUS.register(new ItemCraftingHandler());
		MinecraftForge.EVENT_BUS.register(new ResearchUpdateHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerModelRenderHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		int ID = 0;
		EntityEntry firebeam = EntityEntryBuilder.create()
				.entity(EntityFireBeam.class)
				.id(new ResourceLocation(Main.MODID, "fire_beam"), ID++)
				.name("fire_beam")
				.tracker(100, 100, true)
				.build();
		event.getRegistry().register(firebeam);
	}

}
