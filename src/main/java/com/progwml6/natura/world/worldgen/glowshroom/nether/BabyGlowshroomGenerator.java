package com.progwml6.natura.world.worldgen.glowshroom.nether;

import java.util.Random;

import com.progwml6.natura.nether.block.shrooms.BlockNetherGlowshroom;
import com.progwml6.natura.world.worldgen.glowshroom.BaseGlowshroomGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class BabyGlowshroomGenerator extends BaseGlowshroomGenerator
{
    public final IBlockState glowshroom;

    public BabyGlowshroomGenerator(IBlockState glowshroom)
    {
        this.glowshroom = glowshroom;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    @Override
    public void generateShroom(Random random, World world, BlockPos pos)
    {
        BlockNetherGlowshroom block = (BlockNetherGlowshroom) this.glowshroom.getBlock();

        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && (!world.provider.getHasNoSky() || blockpos.getY() < world.getHeight() - 1) && block.canBlockStay(world, blockpos, this.glowshroom))
            {
                world.setBlockState(blockpos, this.glowshroom, 2);
            }
        }
    }
}
