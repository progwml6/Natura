package mods.natura.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CropRender implements ISimpleBlockRenderingHandler
{
    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        //Doesn't have an inventory sprite
    }

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (modelId == this.model)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta < 4)
                return renderer.renderBlockCrops(block, x, y, z);
            else
                return renderer.renderCrossedSquares(block, x, y, z);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory ()
    {
        return false;
    }

    @Override
    public int getRenderId ()
    {
        return model;
    }
}
