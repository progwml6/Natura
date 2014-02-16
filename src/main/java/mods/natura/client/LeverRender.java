package mods.natura.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class LeverRender implements ISimpleBlockRenderingHandler
{
    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
    {
        if (modelId == model)
        {
            int l = renderer.blockAccess.getBlockMetadata(par2, par3, par4);
            int i1 = l & 7;
            boolean flag = (l & 8) > 0;
            Tessellator tessellator = Tessellator.instance;
            boolean flag1 = renderer.hasOverrideBlockTexture();

            if (!flag1)
            {
                renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.netherrack));
            }

            float f = 0.25F;
            float f1 = 0.1875F;
            float f2 = 0.1875F;

            if (i1 == 5)
            {
                renderer.setRenderBounds(0.5F - f1, 0.0D, 0.5F - f, 0.5F + f1, f2, 0.5F + f);
            }
            else if (i1 == 6)
            {
                renderer.setRenderBounds(0.5F - f, 0.0D, 0.5F - f1, 0.5F + f, f2, 0.5F + f1);
            }
            else if (i1 == 4)
            {
                renderer.setRenderBounds(0.5F - f1, 0.5F - f, 1.0F - f2, 0.5F + f1, 0.5F + f, 1.0D);
            }
            else if (i1 == 3)
            {
                renderer.setRenderBounds(0.5F - f1, 0.5F - f, 0.0D, 0.5F + f1, 0.5F + f, f2);
            }
            else if (i1 == 2)
            {
                renderer.setRenderBounds(1.0F - f2, 0.5F - f, 0.5F - f1, 1.0D, 0.5F + f, 0.5F + f1);
            }
            else if (i1 == 1)
            {
                renderer.setRenderBounds(0.0D, 0.5F - f, 0.5F - f1, f2, 0.5F + f, 0.5F + f1);
            }
            else if (i1 == 0)
            {
                renderer.setRenderBounds(0.5F - f, 1.0F - f2, 0.5F - f1, 0.5F + f, 1.0D, 0.5F + f1);
            }
            else if (i1 == 7)
            {
                renderer.setRenderBounds(0.5F - f1, 1.0F - f2, 0.5F - f, 0.5F + f1, 1.0D, 0.5F + f);
            }

            renderer.renderStandardBlock(par1Block, par2, par3, par4);

            if (!flag1)
            {
                renderer.clearOverrideBlockTexture();
            }

            tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
            float f3 = 1.0F;

            if (par1Block.getLightValue() > 0)
            {
                f3 = 1.0F;
            }

            tessellator.setColorOpaque_F(f3, f3, f3);
            IIcon icon = renderer.getBlockIconFromSide(par1Block, 0);

            if (renderer.hasOverrideBlockTexture())
            {
                icon = renderer.overrideBlockTexture;
            }

            double d0 = icon.getMinU();
            double d1 = icon.getMinV();
            double d2 = icon.getMaxU();
            double d3 = icon.getMaxV();
            Vec3[] avec3 = new Vec3[8];
            float f4 = 0.0625F;
            float f5 = 0.0625F;
            float f6 = 0.625F;
            avec3[0] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool((-f4), 0.0D, (-f5));
            avec3[1] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool(f4, 0.0D, (-f5));
            avec3[2] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool(f4, 0.0D, f5);
            avec3[3] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool((-f4), 0.0D, f5);
            avec3[4] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool((-f4), f6, (-f5));
            avec3[5] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool(f4, f6, (-f5));
            avec3[6] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool(f4, f6, f5);
            avec3[7] = renderer.blockAccess.getWorldVec3Pool().getVecFromPool((-f4), f6, f5);

            for (int j1 = 0; j1 < 8; ++j1)
            {
                if (flag)
                {
                    avec3[j1].zCoord -= 0.0625D;
                    avec3[j1].rotateAroundX(((float) Math.PI * 2F / 9F));
                }
                else
                {
                    avec3[j1].zCoord += 0.0625D;
                    avec3[j1].rotateAroundX(-((float) Math.PI * 2F / 9F));
                }

                if (i1 == 0 || i1 == 7)
                {
                    avec3[j1].rotateAroundZ((float) Math.PI);
                }

                if (i1 == 6 || i1 == 0)
                {
                    avec3[j1].rotateAroundY(((float) Math.PI / 2F));
                }

                if (i1 > 0 && i1 < 5)
                {
                    avec3[j1].yCoord -= 0.375D;
                    avec3[j1].rotateAroundX(((float) Math.PI / 2F));

                    if (i1 == 4)
                    {
                        avec3[j1].rotateAroundY(0.0F);
                    }

                    if (i1 == 3)
                    {
                        avec3[j1].rotateAroundY((float) Math.PI);
                    }

                    if (i1 == 2)
                    {
                        avec3[j1].rotateAroundY(((float) Math.PI / 2F));
                    }

                    if (i1 == 1)
                    {
                        avec3[j1].rotateAroundY(-((float) Math.PI / 2F));
                    }

                    avec3[j1].xCoord += par2 + 0.5D;
                    avec3[j1].yCoord += par3 + 0.5F;
                    avec3[j1].zCoord += par4 + 0.5D;
                }
                else if (i1 != 0 && i1 != 7)
                {
                    avec3[j1].xCoord += par2 + 0.5D;
                    avec3[j1].yCoord += par3 + 0.125F;
                    avec3[j1].zCoord += par4 + 0.5D;
                }
                else
                {
                    avec3[j1].xCoord += par2 + 0.5D;
                    avec3[j1].yCoord += par3 + 0.875F;
                    avec3[j1].zCoord += par4 + 0.5D;
                }
            }

            Vec3 vec3 = null;
            Vec3 vec31 = null;
            Vec3 vec32 = null;
            Vec3 vec33 = null;

            for (int k1 = 0; k1 < 6; ++k1)
            {
                if (k1 == 0)
                {
                    d0 = icon.getInterpolatedU(7.0D);
                    d1 = icon.getInterpolatedV(6.0D);
                    d2 = icon.getInterpolatedU(9.0D);
                    d3 = icon.getInterpolatedV(8.0D);
                }
                else if (k1 == 2)
                {
                    d0 = icon.getInterpolatedU(7.0D);
                    d1 = icon.getInterpolatedV(6.0D);
                    d2 = icon.getInterpolatedU(9.0D);
                    d3 = icon.getMaxV();
                }

                if (k1 == 0)
                {
                    vec3 = avec3[0];
                    vec31 = avec3[1];
                    vec32 = avec3[2];
                    vec33 = avec3[3];
                }
                else if (k1 == 1)
                {
                    vec3 = avec3[7];
                    vec31 = avec3[6];
                    vec32 = avec3[5];
                    vec33 = avec3[4];
                }
                else if (k1 == 2)
                {
                    vec3 = avec3[1];
                    vec31 = avec3[0];
                    vec32 = avec3[4];
                    vec33 = avec3[5];
                }
                else if (k1 == 3)
                {
                    vec3 = avec3[2];
                    vec31 = avec3[1];
                    vec32 = avec3[5];
                    vec33 = avec3[6];
                }
                else if (k1 == 4)
                {
                    vec3 = avec3[3];
                    vec31 = avec3[2];
                    vec32 = avec3[6];
                    vec33 = avec3[7];
                }
                else if (k1 == 5)
                {
                    vec3 = avec3[0];
                    vec31 = avec3[3];
                    vec32 = avec3[7];
                    vec33 = avec3[4];
                }

                tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, d0, d3);
                tessellator.addVertexWithUV(vec31.xCoord, vec31.yCoord, vec31.zCoord, d2, d3);
                tessellator.addVertexWithUV(vec32.xCoord, vec32.yCoord, vec32.zCoord, d2, d1);
                tessellator.addVertexWithUV(vec33.xCoord, vec33.yCoord, vec33.zCoord, d0, d1);
            }
        }
        return true;
    }

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        //Nope!
    }

    @Override
    public int getRenderId ()
    {
        return model;
    }

    @Override
    public boolean shouldRender3DInInventory (int modelId)
    {
        return false;
    }
}
