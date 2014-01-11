package mods.natura.client;

import mods.natura.blocks.tech.BlazeHopper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class HopperRender implements ISimpleBlockRenderingHandler
{
    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == model)
        {
            return renderBlockHopper((BlazeHopper) block, x, y, z, renderer);
        }
        return true;
    }

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == model)
        {
            Tessellator tessellator = Tessellator.instance;
            renderer.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
            NProxyClient.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    public boolean renderBlockHopper (BlazeHopper par1BlockHopper, int par2, int par3, int par4, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        float f = 1.0F;
        int l = par1BlockHopper.colorMultiplier(renderer.blockAccess, par2, par3, par4);
        float f1 = (float) (l >> 16 & 255) / 255.0F;
        float f2 = (float) (l >> 8 & 255) / 255.0F;
        float f3 = (float) (l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }

        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        return this.renderBlockHopperMetadata(par1BlockHopper, par2, par3, par4, renderer.blockAccess.getBlockMetadata(par2, par3, par4), false, renderer);
    }

    public boolean renderBlockHopperMetadata (BlazeHopper par1BlockHopper, int par2, int par3, int par4, int par5, boolean par6, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        int i1 = BlockHopper.getDirectionFromMetadata(par5);
        double d0 = 0.625D;
        renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);

        if (par6)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 0, par5));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 1, par5));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 2, par5));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 3, par5));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 4, par5));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(par1BlockHopper, 5, par5));
            tessellator.draw();
        }
        else
        {
            renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
        }

        float f;

        if (!par6)
        {
            tessellator.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
            float f1 = 1.0F;
            int j1 = par1BlockHopper.colorMultiplier(renderer.blockAccess, par2, par3, par4);
            f = (float) (j1 >> 16 & 255) / 255.0F;
            float f2 = (float) (j1 >> 8 & 255) / 255.0F;
            float f3 = (float) (j1 & 255) / 255.0F;

            if (EntityRenderer.anaglyphEnable)
            {
                float f4 = (f * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
                float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
                float f6 = (f * 30.0F + f3 * 70.0F) / 100.0F;
                f = f4;
                f2 = f5;
                f3 = f6;
            }

            tessellator.setColorOpaque_F(f1 * f, f1 * f2, f1 * f3);
        }

        Icon icon = BlazeHopper.hopperIcon("hopper_outside");
        Icon icon1 = BlazeHopper.hopperIcon("hopper_inside");
        f = 0.125F;

        if (par6)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(par1BlockHopper, (double) (-1.0F + f), 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(par1BlockHopper, (double) (1.0F - f), 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, (double) (-1.0F + f), icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, (double) (1.0F - f), icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(par1BlockHopper, 0.0D, -1.0D + d0, 0.0D, icon1);
            tessellator.draw();
        }
        else
        {
            renderer.renderFaceXPos(par1BlockHopper, (double) ((float) par2 - 1.0F + f), (double) par3, (double) par4, icon);
            renderer.renderFaceXNeg(par1BlockHopper, (double) ((float) par2 + 1.0F - f), (double) par3, (double) par4, icon);
            renderer.renderFaceZPos(par1BlockHopper, (double) par2, (double) par3, (double) ((float) par4 - 1.0F + f), icon);
            renderer.renderFaceZNeg(par1BlockHopper, (double) par2, (double) par3, (double) ((float) par4 + 1.0F - f), icon);
            renderer.renderFaceYPos(par1BlockHopper, (double) par2, (double) ((float) par3 - 1.0F) + d0, (double) par4, icon1);
        }

        renderer.setOverrideBlockTexture(icon);
        double d1 = 0.25D;
        double d2 = 0.25D;
        renderer.setRenderBounds(d1, d2, d1, 1.0D - d1, d0 - 0.002D, 1.0D - d1);

        if (par6)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, icon);
            tessellator.draw();
        }
        else
        {
            renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
        }

        if (!par6)
        {
            double d3 = 0.375D;
            double d4 = 0.25D;
            renderer.setOverrideBlockTexture(icon);

            if (i1 == 0)
            {
                renderer.setRenderBounds(d3, 0.0D, d3, 1.0D - d3, 0.25D, 1.0D - d3);
                renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }

            if (i1 == 2)
            {
                renderer.setRenderBounds(d3, d2, 0.0D, 1.0D - d3, d2 + d4, d1);
                renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }

            if (i1 == 3)
            {
                renderer.setRenderBounds(d3, d2, 1.0D - d1, 1.0D - d3, d2 + d4, 1.0D);
                renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }

            if (i1 == 4)
            {
                renderer.setRenderBounds(0.0D, d2, d3, d1, d2 + d4, 1.0D - d3);
                renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }

            if (i1 == 5)
            {
                renderer.setRenderBounds(1.0D - d1, d2, d3, 1.0D, d2 + d4, 1.0D - d3);
                renderer.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
        }

        renderer.clearOverrideBlockTexture();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory ()
    {
        return true;
    }

    @Override
    public int getRenderId ()
    {
        return model;
    }
}
