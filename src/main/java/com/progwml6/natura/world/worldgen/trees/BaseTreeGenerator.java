package com.progwml6.natura.world.worldgen.trees;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BaseTreeGenerator implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    public void generateTree(Random random, World world, BlockPos pos)
    {
    }

    @Nullable
    private static BlockPos getValidPos(World world, int x, int z, Block tree)
    {
        // get to the ground
        final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));

        if (topPos.getY() == 0)
        {
            return null;
        }

        final MutableBlockPos pos = new MutableBlockPos(topPos);

        IBlockState blockState = world.getBlockState(pos);

        while (canReplace(blockState, world, pos))
        {
            pos.move(EnumFacing.DOWN);
            if (pos.getY() <= 0)
            {
                return null;
            }
            blockState = world.getBlockState(pos);
        }

        if (tree instanceof IPlantable && blockState.getBlock().canSustainPlant(blockState, world, pos, EnumFacing.UP, (IPlantable) tree))
        {
            return pos.up();
        }

        return null;
    }

    public static boolean canReplace(IBlockState blockState, World world, BlockPos pos)
    {
        Block block = blockState.getBlock();

        return block.isReplaceable(world, pos) && !blockState.getMaterial().isLiquid();
    }
}
