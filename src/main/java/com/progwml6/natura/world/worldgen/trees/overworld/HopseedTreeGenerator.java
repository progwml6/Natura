package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class HopseedTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
    }

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, treeRange, log, leaves, true);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    @Override
    public void generateTree(Random random, World worldIn, BlockPos position)
    {
        int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;

        if (this.seekHeight)
        {
            position = this.findGround(worldIn, position);

            if (position.getY() < 0)
            {
                return;
            }
        }
    }

    @SuppressWarnings("deprecation")
    BlockPos findGround(World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if ((block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.SAND) && !world.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                return pos.up();
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        return pos;
    }

    private void growLogs(World worldIn, BlockPos position)
    {
        this.setBlockAndMetadata(worldIn, position, this.log);
        //this.setBlockAndMetadata(worldIn, x + 1, y, z, this.log);
        //this.setBlockAndMetadata(worldIn, x, y, z + 1, this.log);
        //this.setBlockAndMetadata(worldIn, x + 1, y, z + 1, this.log);

        //this.setBlockAndMetadata(worldIn, x, y + 1, z, this.log);
        //this.setBlockAndMetadata(worldIn, x + 1, y + 1, z, this.log);
        //this.setBlockAndMetadata(worldIn, x, y + 1, z + 1, this.log);
        //this.setBlockAndMetadata(worldIn, x + 1, y + 1, z + 1, this.log);
    }

    protected void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew)
    {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.isAir(state, world, pos) || block.canPlaceBlockAt(world, pos) || world.getBlockState(pos) == this.leaves)
        {
            world.setBlockState(pos, stateNew, 2);
        }
    }
}
