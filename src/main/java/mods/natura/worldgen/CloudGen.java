package mods.natura.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class CloudGen extends WorldGenerator
{
    private Block bID;
    private int meta;
    private int numberOfBlocks;
    private boolean flatCloud;

    public CloudGen(Block cloud, int metadata, int size, boolean flag)
    {
        bID = cloud;
        meta = metadata;
        numberOfBlocks = size;
        flatCloud = flag;
    }

    @Override
	public boolean generate (World world, Random random, int x, int y, int z)
    {
        int l = random.nextInt(3) - 1;
        int i1 = random.nextInt(3) - 1;
        for (int j1 = 0; j1 < numberOfBlocks; j1++)
        {
            x += (random.nextInt(3) - 1) + l;
            z += (random.nextInt(3) - 1) + i1;
            if (random.nextBoolean() && !flatCloud || flatCloud && random.nextInt(10) == 0)
            {
                y += random.nextInt(3) - 1;
            }
            for (int xIter = x; xIter < x + random.nextInt(4) + 3 * (flatCloud ? 3 : 1); xIter++)
            {
                for (int yIter = y; yIter < y + random.nextInt(1) + 2; yIter++)
                {
                    for (int zIter = z; zIter < z + random.nextInt(4) + 3 * (flatCloud ? 3 : 1); zIter++)
                    {
                        if (world.getBlock(xIter, yIter, zIter) == Blocks.air && Math.abs(xIter - x) + Math.abs(yIter - y) + Math.abs(zIter - z) < 4 * (flatCloud ? 3 : 1) + random.nextInt(2))
                        {
                            setBlockAndNotifyAdequately(world, xIter, yIter, zIter, bID, meta);
                        }
                    }
                }
            }
        }

        return true;
    }
}
