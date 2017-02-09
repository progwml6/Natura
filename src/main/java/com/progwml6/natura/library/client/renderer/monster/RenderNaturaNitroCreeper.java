package com.progwml6.natura.library.client.renderer.monster;

import com.progwml6.natura.entities.entity.monster.EntityNitroCreeper;
import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNaturaNitroCreeper extends RenderCreeper
{
    public static final Factory FACTORY_nitroCreeper = new Factory();

    public static final ResourceLocation impTexture = Util.getResource("textures/entity/nitrocreeper.png");

    public RenderNaturaNitroCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity)
    {
        return impTexture;
    }

    private static class Factory implements IRenderFactory<EntityNitroCreeper>
    {
        public Factory()
        {
        }

        @Override
        public Render<? super EntityNitroCreeper> createRenderFor(RenderManager manager)
        {
            return new RenderNaturaNitroCreeper(manager);
        }
    }
}
