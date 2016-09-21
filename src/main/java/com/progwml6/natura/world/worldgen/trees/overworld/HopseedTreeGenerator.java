package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

//TODO: FINISH
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

        int yPos = position.getY();

        if (yPos >= 1 && yPos + height + 1 <= 256)
        {
            IBlockState state = worldIn.getBlockState(position.down());
            Block soil = state.getBlock();
            boolean isSoil = (soil != null && soil.canSustainPlant(state, worldIn, position.down(), EnumFacing.UP, NaturaOverworld.overworldSapling2));

            if (isSoil)
            {
                soil.onPlantGrow(state, worldIn, position.down(), position);
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
            if ((block == Blocks.DIRT || block == Blocks.GRASS) && !world.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                return pos.up();
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        return pos;
    }

    /*
     * TODO: FINISH
     * private void growLogs(World worldIn, BlockPos position)
    {
        this.setBlockAndMetadata(worldIn, position, this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, 0, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(0, 0, +1), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, 0, +1), this.log);
    
        this.setBlockAndMetadata(worldIn, position.add(0, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(0, +1, +1), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, +1, +1), this.log);
    }*/

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
