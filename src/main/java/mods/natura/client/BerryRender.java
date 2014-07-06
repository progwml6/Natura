package mods.natura.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BerryRender implements ISimpleBlockRenderingHandler
{
    public static int berryModel = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {

        if (modelId == berryModel)
        {
            int md = world.getBlockMetadata(x, y, z);
            if (md < 4)
            {
                renderer.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                renderer.renderStandardBlock(block, x, y, z);
            }
            else if (md < 8)
            {
                renderer.setRenderBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F);
                renderer.renderStandardBlock(block, x, y, z);
            }
            else
            {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == berryModel)
        {
            Tessellator tessellator = Tessellator.instance;
            renderer.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
            NProxyClient.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean shouldRender3DInInventory (int id)
    {
        return true;
    }

    @Override
    public int getRenderId ()
    {
        return berryModel;
    }
}
