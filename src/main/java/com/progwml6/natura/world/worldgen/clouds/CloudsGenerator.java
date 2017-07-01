package com.progwml6.natura.world.worldgen.clouds;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CloudsGenerator implements IWorldGenerator
{
    public final int cloudSize;

    public final IBlockState cloud;

    public final boolean flatCloud;

    public CloudsGenerator(int cloudSize, IBlockState cloud, boolean flatCloud)
    {
        this.cloudSize = cloudSize;
        this.cloud = cloud;
        this.flatCloud = flatCloud;
    }

    public CloudsGenerator(int cloudSize, IBlockState cloud)
    {
        this(cloudSize, cloud, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    public void generateCloud(Random random, World world, BlockPos pos)
    {
        int rand = random.nextInt(3) - 1;
        int rand1 = random.nextInt(3) - 1;

        for (int block = 0; block < this.cloudSize; block++)
        {
            int xIter = pos.getX() + (random.nextInt(3) - 1) + rand;
            int yIter = pos.getY();
            int zIter = pos.getZ() + (random.nextInt(3) - 1) + rand1;

            if (random.nextBoolean() && !this.flatCloud || this.flatCloud && random.nextInt(10) == 0)
            {
                yIter = pos.getY() + random.nextInt(3) - 1;
            }

            for (int x = xIter; x < xIter + random.nextInt(4) + 3 * (this.flatCloud ? 3 : 1); x++)
            {
                int mathX = xIter - x;

                for (int y = yIter; y < yIter + random.nextInt(1) + 2; y++)
                {
                    int mathY = yIter - y;

                    for (int z = zIter; z < zIter + random.nextInt(4) + 3 * (this.flatCloud ? 3 : 1); z++)
                    {
                        int mathZ = zIter - z;

                        if (Math.abs(mathX) + Math.abs(mathY) + Math.abs(mathZ) < 4 * (this.flatCloud ? 3 : 1) + random.nextInt(2))
                        {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            IBlockState state = world.getBlockState(blockpos);

                            if (state.getBlock() == Blocks.AIR)
                            {
                                world.setBlockState(blockpos, this.cloud, 2);
                            }
                        }
                    }
                }
            }
        }
    }
}
