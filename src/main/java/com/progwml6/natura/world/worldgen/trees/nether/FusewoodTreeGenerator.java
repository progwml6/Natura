package com.progwml6.natura.world.worldgen.trees.nether;

import java.util.Random;

import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class FusewoodTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public FusewoodTreeGenerator(int treeHeight, IBlockState log, IBlockState leaves, boolean seekHeight)
    {
        this.minTreeHeight = treeHeight;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
    }

    public FusewoodTreeGenerator(int treeHeight, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, log, leaves, true);
    }

    @SuppressWarnings("deprecation")
    BlockPos findGround(World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if ((block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND || block == NaturaNether.netherTaintedSoil) && !world.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                return pos.up();
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        return pos;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    @Override
    public void generateTree(Random random, World world, BlockPos pos)
    {
        int height = random.nextInt(3) + this.minTreeHeight;
        if (height < 4)
        {
            height = 4;
        }
        if (this.seekHeight)
        {
            pos = this.findGround(world, pos);
            if (pos.getY() < 0)
            {
                return;
            }
        }

        int yPos = pos.getY();

        if (yPos >= 1 && yPos + height + 1 <= 256)
        {
            IBlockState state = world.getBlockState(pos.down());
            Block soil = state.getBlock();
            boolean isSoil = (soil != null && soil.canSustainPlant(state, world, pos.down(), EnumFacing.UP, NaturaNether.netherSapling) || soil == Blocks.NETHERRACK);

            if (isSoil)
            {
                soil.onPlantGrow(state, world, pos.down(), pos);

                this.placeCanopy(world, random, pos, height);
                this.placeTrunk(world, pos, height);
            }
        }
    }

    protected void placeCanopy(World world, Random random, BlockPos pos, int height)
    {
        for (int y = pos.getY() - 3 + height; y <= pos.getY() + height; ++y)
        {
            int subract = y - (pos.getY() + height);
            int subract2 = 1 - subract / 2;

            for (int x = pos.getX() - subract2; x <= pos.getX() + subract2; ++x)
            {
                int mathX = x - pos.getX();

                for (int z = pos.getZ() - subract2; z <= pos.getZ() + subract2; ++z)
                {
                    int mathZ = z - pos.getZ();

                    if (Math.abs(mathX) != subract2 || Math.abs(mathZ) != subract2 || random.nextInt(2) != 0 && subract != 0)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        IBlockState state = world.getBlockState(blockpos);

                        if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos) || state.getBlock().canBeReplacedByLeaves(state, world, blockpos))
                        {
                            this.setBlockAndMetadata(world, blockpos, this.leaves);
                        }
                    }
                }
            }
        }
    }

    protected void placeTrunk(World world, BlockPos pos, int height)
    {
        while (height >= 0)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block.isAir(state, world, pos) || block.isReplaceable(world, pos) || block.isLeaves(state, world, pos))
            {
                this.setBlockAndMetadata(world, pos, this.log);
            }

            pos = pos.up();
            height--;
        }
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
