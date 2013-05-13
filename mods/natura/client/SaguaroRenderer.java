package mods.natura.client;

import mods.natura.blocks.trees.SaguaroBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class SaguaroRenderer implements ISimpleBlockRenderingHandler
{
	public static int model = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == model)
			NClientProxy.renderStandardInvBlock(renderer, block, metadata);
	}

	@Override
	public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		if (modelID == model)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta == 0)
				return renderCactus(renderer, world, x, y, z, (SaguaroBlock) block);
			else if (meta == 1 || meta == 2)
				return renderSmall(renderer, world, x, y, z, block);
			else
				return renderFruit((SaguaroBlock) block, x, y, z, world, meta);
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

	/* Render methods, used for saguaro */

	boolean renderCactus (RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z, SaguaroBlock cactus)
	{
		float offset = 0.125F;

		float botX = offset;
		float botY = 0.0F;
		float botZ = offset;
		float topX = 1.0F - offset;
		float topY = 1.0F - offset;
		float topZ = 1.0F - offset;

		int airBelow = iblockaccess.getBlockId(x, y - 1, z);
		int cactusAbove = iblockaccess.getBlockId(x, y + 1, z);

		if (airBelow == 0)
			botY = offset;
		if(cactusAbove == cactus.blockID)
			topY = 1.0F;

		renderblocks.setRenderBounds(botX, botY, botZ, topX, topY, topZ);
		renderblocks.renderStandardBlock(cactus, x, y, z);

		botY = offset;
		topY = 1.0F - offset;

		if (cactus.canConnectSuguaroTo(iblockaccess, x + 1, y, z) && (airBelow == 0 || iblockaccess.getBlockId(x + 1, y - 1, z) == 0))
		{
			botX = 1F - offset;
			topX = 1F;
			renderblocks.setRenderBounds(botX, botY, botZ, topX, topY, topZ);
			renderblocks.renderStandardBlock(cactus, x, y, z);
		}

		if (cactus.canConnectSuguaroTo(iblockaccess, x - 1, y, z) && (airBelow == 0 || iblockaccess.getBlockId(x - 1, y - 1, z) == 0))
		{
			botX = 0F;
			topX = offset;
			renderblocks.setRenderBounds(botX, botY, botZ, topX, topY, topZ);
			renderblocks.renderStandardBlock(cactus, x, y, z);
		}

		botX = offset;
		topX = 1.0F - offset;

		if (cactus.canConnectSuguaroTo(iblockaccess, x, y, z + 1) && (airBelow == 0 || iblockaccess.getBlockId(x, y - 1, z + 1) == 0))
		{
			botZ = 1F - offset;
			topZ = 1F;
			renderblocks.setRenderBounds(botX, botY, botZ, topX, topY, topZ);
			renderblocks.renderStandardBlock(cactus, x, y, z);
		}

		if (cactus.canConnectSuguaroTo(iblockaccess, x, y, z - 1) && (airBelow == 0 || iblockaccess.getBlockId(x, y - 1, z - 1) == 0))
		{
			botZ = 0F;
			topZ = offset;
			renderblocks.setRenderBounds(botX, botY, botZ, topX, topY, topZ);
			renderblocks.renderStandardBlock(cactus, x, y, z);
		}
		return false;
	}
	
	public boolean renderFruit(SaguaroBlock block, int x, int y, int z, IBlockAccess world, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        int direction = BlockDirectional.getDirection(meta);
        int sizer = SaguaroBlock.func_72219_c(meta+5);
        if (sizer > 4)
        	sizer = 0;
        Icon icon = block.icons[3];
        int offsetX = 4 + sizer * 2;
        int offsetY = 5 + sizer * 2;
        double d0 = 16.0D - (double)offsetX;
        double d1 = 16.0D;
        double d2 = 4.0D;
        double d3 = 4.0D + (double)offsetY;
        double d4 = (double)icon.getInterpolatedU(d0);
        double d5 = (double)icon.getInterpolatedU(d1);
        double d6 = (double)icon.getInterpolatedV(d2);
        double d7 = (double)icon.getInterpolatedV(d3);
        double d8 = 0.0D;
        double d9 = 0.0D;

        switch (direction)
        {
            case 0:
                d8 = 8.0D - (double)(offsetX / 2);
                d9 = 18.0D - (double)offsetX;
                break;
            case 1:
                d8 = -2.0D;
                d9 = 8.0D - (double)(offsetX / 2);
                break;
            case 2:
                d8 = 8.0D - (double)(offsetX / 2);
                d9 = -2.0D;
                break;
            case 3:
                d8 = 18.0D - (double)offsetX;
                d9 = 8.0D - (double)(offsetX / 2);
            /*case 4:
                d8 = 11.0D - (double)offsetX;
                d9 = 8.0D - (double)(offsetX / 2);*/
        }

        double d10 = (double)x + d8 / 16.0D;
        double d11 = (double)x + (d8 + (double)offsetX) / 16.0D;
        double d12 = (double)y + (12.0D - (double)offsetY) / 16.0D;
        double d13 = (double)y + 0.75D;
        double d14 = (double)z + d9 / 16.0D;
        double d15 = (double)z + (d9 + (double)offsetX) / 16.0D;
        tessellator.addVertexWithUV(d10, d12, d14, d4, d7);
        tessellator.addVertexWithUV(d10, d12, d15, d5, d7);
        tessellator.addVertexWithUV(d10, d13, d15, d5, d6);
        tessellator.addVertexWithUV(d10, d13, d14, d4, d6);
        tessellator.addVertexWithUV(d11, d12, d15, d4, d7);
        tessellator.addVertexWithUV(d11, d12, d14, d5, d7);
        tessellator.addVertexWithUV(d11, d13, d14, d5, d6);
        tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
        tessellator.addVertexWithUV(d11, d12, d14, d4, d7);
        tessellator.addVertexWithUV(d10, d12, d14, d5, d7);
        tessellator.addVertexWithUV(d10, d13, d14, d5, d6);
        tessellator.addVertexWithUV(d11, d13, d14, d4, d6);
        tessellator.addVertexWithUV(d10, d12, d15, d4, d7);
        tessellator.addVertexWithUV(d11, d12, d15, d5, d7);
        tessellator.addVertexWithUV(d11, d13, d15, d5, d6);
        tessellator.addVertexWithUV(d10, d13, d15, d4, d6);
        int i2 = offsetX;

        if (sizer >= 2)
        {
            i2 = offsetX - 1;
        }

        d4 = (double)icon.getMinU();
        d5 = (double)icon.getInterpolatedU((double)i2);
        d6 = (double)icon.getMinV();
        d7 = (double)icon.getInterpolatedV((double)i2);
        tessellator.addVertexWithUV(d10, d13, d15, d4, d7);
        tessellator.addVertexWithUV(d11, d13, d15, d5, d7);
        tessellator.addVertexWithUV(d11, d13, d14, d5, d6);
        tessellator.addVertexWithUV(d10, d13, d14, d4, d6);
        tessellator.addVertexWithUV(d10, d12, d14, d4, d6);
        tessellator.addVertexWithUV(d11, d12, d14, d5, d6);
        tessellator.addVertexWithUV(d11, d12, d15, d5, d7);
        tessellator.addVertexWithUV(d10, d12, d15, d4, d7);
        d4 = (double)icon.getInterpolatedU(12.0D);
        d5 = (double)icon.getMaxU();
        d6 = (double)icon.getMinV();
        d7 = (double)icon.getInterpolatedV(4.0D);
        d8 = 8.0D;
        d9 = 0.0D;
        double d16;

        switch (direction)
        {
            case 0:
                d8 = 8.0D;
                d9 = 12.0D;
                d16 = d4;
                d4 = d5;
                d5 = d16;
                break;
            case 1:
                d8 = 0.0D;
                d9 = 8.0D;
                break;
            case 2:
                d8 = 8.0D;
                d9 = 0.0D;
                break;
            case 3:
                d8 = 12.0D;
                d9 = 8.0D;
                d16 = d4;
                d4 = d5;
                d5 = d16;
        }

        d10 = (double)x + d8 / 16.0D;
        d11 = (double)x + (d8 + 4.0D) / 16.0D;
        d12 = (double)y + 0.75D;
        d13 = (double)y + 1.0D;
        d14 = (double)z + d9 / 16.0D;
        d15 = (double)z + (d9 + 4.0D) / 16.0D;

        /*if (i1 != 2 && i1 != 0)
        {
            if (i1 == 1 || i1 == 3)
            {
                tessellator.addVertexWithUV(d11, d12, d14, d4, d7);
                tessellator.addVertexWithUV(d10, d12, d14, d5, d7);
                tessellator.addVertexWithUV(d10, d13, d14, d5, d6);
                tessellator.addVertexWithUV(d11, d13, d14, d4, d6);
                tessellator.addVertexWithUV(d10, d12, d14, d5, d7);
                tessellator.addVertexWithUV(d11, d12, d14, d4, d7);
                tessellator.addVertexWithUV(d11, d13, d14, d4, d6);
                tessellator.addVertexWithUV(d10, d13, d14, d5, d6);
            }
        }
        else
        {
            tessellator.addVertexWithUV(d10, d12, d14, d5, d7);
            tessellator.addVertexWithUV(d10, d12, d15, d4, d7);
            tessellator.addVertexWithUV(d10, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d10, d13, d14, d5, d6);
            tessellator.addVertexWithUV(d10, d12, d15, d4, d7);
            tessellator.addVertexWithUV(d10, d12, d14, d5, d7);
            tessellator.addVertexWithUV(d10, d13, d14, d5, d6);
            tessellator.addVertexWithUV(d10, d13, d15, d4, d6);
        }*/

        return true;
    }
	
	boolean renderSmall (RenderBlocks renderer, IBlockAccess iblockaccess, int x, int y, int z, Block block)
	{
		renderer.setRenderBounds(0.325F, 0.0F, 0.325F, 0.675F, 0.5F, 0.675F);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}
}
