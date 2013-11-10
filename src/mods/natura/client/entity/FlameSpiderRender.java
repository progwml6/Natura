package mods.natura.client.entity;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class FlameSpiderRender extends RenderSpider
{

    public FlameSpiderRender()
    {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture (Entity par1Entity)
    {
        return texture;
    }

    static final ResourceLocation texture = new ResourceLocation("natura", "textures/mob/flamespider.png");

}
