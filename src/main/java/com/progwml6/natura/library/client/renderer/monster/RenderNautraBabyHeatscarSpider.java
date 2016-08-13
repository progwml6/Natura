package com.progwml6.natura.library.client.renderer.monster;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.entities.entity.monster.EntityBabyHeatscarSpider;
import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNautraBabyHeatscarSpider extends RenderSpider<EntityBabyHeatscarSpider>
{
    public static final Factory FACTORY_babyHeatscarSpider = new Factory();

    public static final ResourceLocation babyHeatscarSpiderTexture = Util.getResource("textures/entity/heatscarspider.png");

    public RenderNautraBabyHeatscarSpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBabyHeatscarSpider entity)
    {
        return babyHeatscarSpiderTexture;
    }

    @Override
    protected void preRenderCallback(EntityBabyHeatscarSpider par1EntityLivingBase, float par2)
    {
        GL11.glScalef(0.85f, 0.85f, 0.85f);
    }

    private static class Factory implements IRenderFactory<EntityBabyHeatscarSpider>
    {
        public Factory()
        {
        }

        @Override
        public Render<? super EntityBabyHeatscarSpider> createRenderFor(RenderManager manager)
        {
            return new RenderNautraBabyHeatscarSpider(manager);
        }
    }
}
