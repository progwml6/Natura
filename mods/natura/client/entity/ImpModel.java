package mods.natura.client.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ImpModel extends ModelBase
{
    public ModelRenderer impParts[];

    public ImpModel()
    {
        impParts = new ModelRenderer[6];
        impParts[0] = new ModelRenderer(this, 16, 16);
        impParts[0].addBox(-5F, 0.0F, -18.5F, 10, 5, 9, 0.0F);
        impParts[0].setRotationPoint(0.0F, 0.0F, 0.0F);
        impParts[1] = new ModelRenderer(this, 0, 0);
        impParts[1].addBox(-5F, -6.5F, -4F, 10, 8, 8, 0.0F);
        impParts[1].setRotationPoint(0.0F, 8.0F, 2.0F);
        impParts[2] = new ModelRenderer(this, 0, 16);
        impParts[2].addBox(-2F, 0.0F, -2F, 4, 6, 4, 0.0F);
        impParts[2].setRotationPoint(-3F, 18F, 2.0F);
        impParts[3] = new ModelRenderer(this, 0, 16);
        impParts[3].addBox(-2F, 0.0F, -2F, 4, 6, 4, 0.0F);
        impParts[3].setRotationPoint(3F, 18F, 2.0F);
        impParts[4] = new ModelRenderer(this, 32, 0);
        impParts[4].addBox(-1.5F, 0.0F, 1.0F, 2, 5, 2, 0.0F);
        impParts[4].setRotationPoint(6F, 10F, 0.0F);
        impParts[5] = new ModelRenderer(this, 32, 0);
        impParts[5].addBox(-0.5F, 0.0F, 1.0F, 2, 5, 2, 0.0F);
        impParts[5].setRotationPoint(-6F, 10F, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        impParts[0].render(f5);
        impParts[1].render(f5);
        impParts[2].render(f5);
        impParts[3].render(f5);
        impParts[4].render(f5);
        impParts[5].render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        impParts[0].rotateAngleX = 1.570796F;
        impParts[1].rotateAngleX = f4 / -57.29578F;
        impParts[1].rotateAngleY = f3 / 57.29578F;
        impParts[4].rotateAngleX = MathHelper.cos(f * 0.6662F + 0.0F) * 1.4F * f1;
        impParts[5].rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        impParts[2].rotateAngleX = MathHelper.cos(f * 0.6662F + 0.0F) * 1.4F * f1;
        impParts[3].rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
    }
}
