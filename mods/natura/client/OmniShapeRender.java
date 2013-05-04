package mods.natura.client;

import org.lwjgl.opengl.GL11;

import mods.natura.blocks.OmniShapeBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class OmniShapeRender implements ISimpleBlockRenderingHandler
{
    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (metadata < 8)
        {
            renderer.setRenderBounds(0.0, 0.0F, 0.0, 1.0, 0.5F, 1.0);
            this.renderInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(0.0, 0.5, 0.0, 1.0, 1.0, 0.5);
            this.renderInvBlock(renderer, block, metadata);
        }
        else if (metadata < 14)
        {
            renderer.setRenderBounds(0.0, 0.0F, 0.0, 1.0, 0.5F, 1.0);
            this.renderInvBlock(renderer, block, metadata);
        }
        else if (metadata < 15)
        {
            renderer.setRenderBounds(0.375, 0.0F, 0.375, 0.625, 1F, 0.625);
            this.renderInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == this.model)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta < 8)
            {
                //renderer.renderStandardBlock(block, x, y, z);
                return renderBlockStairs((OmniShapeBlock) block, world, x, y, z, renderer);
            }
            else if (meta < 14)
            {
                return renderBlockSlab(block, world, meta, x, y, z, renderer);
            }
            else if (meta == 14)
            {
                return renderBlockWall((OmniShapeBlock) block, world, x, y, z, renderer);
            }
        }
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

    public boolean renderBlockStairs (OmniShapeBlock block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer)
    {
        /*block.func_82541_d(world, x, y, z);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);
        boolean flag = block.func_82542_g(world, x, y, z);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        if (flag && block.func_82544_h(world, x, y, z))
        {
            renderer.setRenderBoundsFromBlock(block);
            renderer.renderStandardBlock(block, x, y, z);
        }*/

        return true;
    }

    public boolean renderBlockSlab (Block block, IBlockAccess world, int meta, int x, int y, int z, RenderBlocks renderer)
    {
        float offset = 0.5f;
        switch (meta)
        {
        case 8:
            renderer.setRenderBounds(0f, offset, 0f, 1f, 1f, 1f);
            break;
        case 9:
            renderer.setRenderBounds(0f, 0f, 0f, 1f, offset, 1f);
            break;
        case 10:
            renderer.setRenderBounds(0f, 0f, offset, 1f, 1f, 1f);
            break;
        case 11:
            renderer.setRenderBounds(0f, 0f, 0f, 1f, 1f, offset);
            break;
        case 12:
            renderer.setRenderBounds(offset, 0f, 0f, 1f, 1f, 1f);
            break;
        case 13:
            renderer.setRenderBounds(0f, 0f, 0f, offset, 1f, 1f);
            break;
        }

        renderer.renderStandardBlock(block, x, y, z);
        return false;
    }

    public boolean renderBlockWall (OmniShapeBlock block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer)
    {
        boolean flag = block.canConnectWallTo(world, x - 1, y, z);
        boolean flag1 = block.canConnectWallTo(world, x + 1, y, z);
        boolean flag2 = block.canConnectWallTo(world, x, y, z - 1);
        boolean flag3 = block.canConnectWallTo(world, x, y, z + 1);
        boolean flag4 = flag2 && flag3 && !flag && !flag1;
        boolean flag5 = !flag2 && !flag3 && flag && flag1;
        boolean flag6 = world.isAirBlock(x, y + 1, z);

        if ((flag4 || flag5) && flag6)
        {
            if (flag4)
            {
                renderer.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
                renderer.renderStandardBlock(block, x, y, z);
            }
            else
            {
                renderer.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                renderer.renderStandardBlock(block, x, y, z);
            }
        }
        else
        {
            renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
            renderer.renderStandardBlock(block, x, y, z);

            if (flag)
            {
                renderer.setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (flag1)
            {
                renderer.setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (flag2)
            {
                renderer.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (flag3)
            {
                renderer.setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
                renderer.renderStandardBlock(block, x, y, z);
            }
        }

        //block.setBlockBoundsBasedOnState(world, x, y, z);
        return true;
    }

    private void renderInvBlock (RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
