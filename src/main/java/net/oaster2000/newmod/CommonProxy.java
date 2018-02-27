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
import net.oaster2000.newmod.blocks.ModBlocks;
import net.oaster2000.newmod.capability.CapabilityHandler;
import net.oaster2000.newmod.capability.IMana;
import net.oaster2000.newmod.capability.Mana;
import net.oaster2000.newmod.capability.ManaEventHandler;
import net.oaster2000.newmod.capability.ManaStorage;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.handler.ItemCraftingHandler;
import net.oaster2000.newmod.handler.PlayerModelRenderHandler;
import net.oaster2000.newmod.handler.ResearchUpdateHandler;
import net.oaster2000.newmod.items.ModItems;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void Init(FMLInitializationEvent e) {
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana.class);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new ManaEventHandler());
		}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
