package com.progwml6.natura.world.worldgen.vine;

import java.util.Random;

import com.progwml6.natura.nether.NaturaNether;

import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ThornvinesGenerator implements IWorldGenerator
{
    public final IBlockState vine;

    public ThornvinesGenerator(IBlockState vine)
    {
        this.vine = vine;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    public void generateVines(Random random, World world, BlockPos pos, IBlockState vine)
    {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR)
        {
            world.setBlockState(pos, vine, 2);
        }
    }

    public IBlockState getRandomizedVine(Random random)
    {
        IBlockState state = NaturaNether.netherThornVines.getDefaultState();
        PropertyBool[] sides = new PropertyBool[] { BlockVine.NORTH, BlockVine.EAST, BlockVine.SOUTH, BlockVine.WEST };
        for (PropertyBool side : sides)
        {
            state = state.withProperty(side, false);
        }
        for (int i = random.nextInt(3) + 1; i > 0; i--)
        {
            state = state.withProperty(sides[random.nextInt(sides.length)], true);
        }

        return state;
    }
}
