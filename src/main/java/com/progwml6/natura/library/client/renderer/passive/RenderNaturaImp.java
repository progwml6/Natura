package com.progwml6.natura.library.client.renderer.passive;

import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.Util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNaturaImp extends RenderLiving<EntityImp>
{
    public static final Factory FACTORY_imp = new Factory();

    public static final ResourceLocation impTexture = Util.getResource("textures/entity/imp.png");

    public RenderNaturaImp(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelImp(), 0f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityImp entity)
    {
        return impTexture;
    }

    private static class Factory implements IRenderFactory<EntityImp>
    {
        public Factory()
        {
        }

        @Override
        public Render<? super EntityImp> createRenderFor(RenderManager manager)
        {
            return new RenderNaturaImp(manager);
        }
    }

    static class ModelImp extends ModelBase
    {
        public ModelRenderer impParts[];

        public ModelImp()
        {
            this.impParts = new ModelRenderer[6];
            this.impParts[0] = new ModelRenderer(this, 16, 16);
            this.impParts[0].addBox(-5F, 0.0F, -18.5F, 10, 5, 9, 0.0F);
            this.impParts[0].setRotationPoint(0.0F, 0.0F, 0.0F);
            this.impParts[1] = new ModelRenderer(this, 0, 0);
            this.impParts[1].addBox(-5F, -6.5F, -4F, 10, 8, 8, 0.0F);
            this.impParts[1].setRotationPoint(0.0F, 8.0F, 2.0F);
            this.impParts[2] = new ModelRenderer(this, 0, 16);
            this.impParts[2].addBox(-2F, 0.0F, -2F, 4, 6, 4, 0.0F);
            this.impParts[2].setRotationPoint(-3F, 18F, 2.0F);
            this.impParts[3] = new ModelRenderer(this, 0, 16);
            this.impParts[3].addBox(-2F, 0.0F, -2F, 4, 6, 4, 0.0F);
            this.impParts[3].setRotationPoint(3F, 18F, 2.0F);
            this.impParts[4] = new ModelRenderer(this, 32, 0);
            this.impParts[4].addBox(-1.5F, 0.0F, 1.0F, 2, 5, 2, 0.0F);
            this.impParts[4].setRotationPoint(6F, 10F, 0.0F);
            this.impParts[5] = new ModelRenderer(this, 32, 0);
            this.impParts[5].addBox(-0.5F, 0.0F, 1.0F, 2, 5, 2, 0.0F);
            this.impParts[5].setRotationPoint(-6F, 10F, 0.0F);
        }

        @Override
        public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
        {
            this.setRotationAngles(f, f1, f2, f3, f4, f5);
            this.impParts[0].render(f5);
            this.impParts[1].render(f5);
            this.impParts[2].render(f5);
            this.impParts[3].render(f5);
            this.impParts[4].render(f5);
            this.impParts[5].render(f5);
        }

        public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
        {
            this.impParts[0].rotateAngleX = 1.570796F;
            this.impParts[1].rotateAngleX = f4 / -57.29578F;
            this.impParts[1].rotateAngleY = f3 / 57.29578F;
            this.impParts[4].rotateAngleX = MathHelper.cos(f * 0.6662F + 0.0F) * 1.4F * f1;
            this.impParts[5].rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
            this.impParts[2].rotateAngleX = MathHelper.cos(f * 0.6662F + 0.0F) * 1.4F * f1;
            this.impParts[3].rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        }
    }
}
