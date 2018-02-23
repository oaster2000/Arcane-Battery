package net.oaster2000.newmod.factory;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.oaster2000.newmod.entity.EntityFireBeam;
import net.oaster2000.newmod.entity.renderer.RenderFireBeam;

public class ProjectileFactory {

	public static class RenderFireBeamFactory implements IRenderFactory<EntityFireBeam> {

	    public static final RenderFireBeamFactory INSTANCE = new RenderFireBeamFactory();

	    @Override
	    public Render<EntityFireBeam> createRenderFor(RenderManager manager) {
	        return new RenderFireBeam(manager);
	    }

	}
}
