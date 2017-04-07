package com.progwml6.natura.world.dimension;

import java.util.Random;

import com.progwml6.natura.nether.NaturaNether;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNetheriteFire extends WorldGenerator
{
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.NETHERRACK || worldIn.getBlockState(blockpos.down()).getBlock() == NaturaNether.netherTaintedSoil))
            {
                worldIn.setBlockState(blockpos, Blocks.FIRE.getDefaultState(), 2);
            }
        }

        return true;
    }
}
