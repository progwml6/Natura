package com.progwml6.natura.library.client.renderer.monster;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.entities.entity.monster.EntityHeatscarSpider;
import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNautraHeatscarSpider extends RenderSpider<EntityHeatscarSpider>
{
    public static final Factory FACTORY_heatscarSpider = new Factory();

    public static final ResourceLocation heatscarSpiderTexture = Util.getResource("textures/entity/heatscarspider.png");

    public RenderNautraHeatscarSpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityHeatscarSpider entity)
    {
        return heatscarSpiderTexture;
    }

    @Override
    protected void preRenderCallback(EntityHeatscarSpider par1EntityLivingBase, float par2)
    {
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    private static class Factory implements IRenderFactory<EntityHeatscarSpider>
    {
        public Factory()
        {
        }

        @Override
        public Render<? super EntityHeatscarSpider> createRenderFor(RenderManager manager)
        {
            return new RenderNautraHeatscarSpider(manager);
        }
    }
}
