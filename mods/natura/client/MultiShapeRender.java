package mods.natura.client;

import mods.natura.blocks.MultiShapeBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MultiShapeRender implements ISimpleBlockRenderingHandler
{
    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
    }

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == this.model)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta < 8)
                return renderBlockStairs((MultiShapeBlock) block, world, x, y, z, renderer);
            else if (meta < 14)
                return renderBlockSlab(block, world, meta, x, y, z, renderer);
            else if (meta == 14)
                return renderBlockWall((MultiShapeBlock) block, world, x, y, z, renderer);
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
    
    public boolean renderBlockStairs(MultiShapeBlock block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer)
    {
        block.func_82541_d(world, x, y, z);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);
        boolean flag = block.func_82542_g(world, x, y, z);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        if (flag && block.func_82544_h(world, x, y, z))
        {
            renderer.setRenderBoundsFromBlock(block);
            renderer.renderStandardBlock(block, x, y, z);
        }

        return true;
    }
    
    public boolean renderBlockSlab (Block block, IBlockAccess world, int meta, int x, int y, int z, RenderBlocks renderer)
    {
        switch (meta)
        {
        case 8: renderer.setRenderBounds(0f, 0f, 0f, 1f, 0.5f, 1f); break;
        case 9: renderer.setRenderBounds(0f, 0.5f, 0f, 1f, 1f, 1f); break;
        case 10: renderer.setRenderBounds(0.5f, 0f, 0f, 1f, 1f, 1f); break;
        case 11: renderer.setRenderBounds(0f, 0f, 0.5f, 1f, 1f, 1f); break;
        case 12: renderer.setRenderBounds(0f, 0f, 0f, 0.5f, 1f, 1f); break;
        case 13: renderer.setRenderBounds(0f, 0f, 0f, 1f, 1f, 0.5f); break;
        }
        
        renderer.renderStandardBlock(block, x, y, z);
        return false;
    }
    
    public boolean renderBlockWall(MultiShapeBlock block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer)
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

        block.setBlockBoundsBasedOnState(world, x, y, z);
        return true;
    }
}
