package com.progwml6.natura.world.worldgen.trees.nether;

import java.util.Random;

import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

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

    BlockPos findGround(World world, BlockPos pos)
    {
        int returnHeight = 0;

        int y = pos.getY() - 1;

        do
        {
            BlockPos position = new BlockPos(pos.getX(), y, pos.getZ());
            IBlockState state = world.getBlockState(position);

            if (state.getBlock() == Blocks.NETHERRACK || state.getBlock() == Blocks.SOUL_SAND || state.getBlock() == NaturaNether.netherTaintedSoil || position.getY() < 0)
            {
                returnHeight = y + 1;
                break;
            }

            y--;
        }
        while (y > 0);

        return new BlockPos(pos.getX(), returnHeight, pos.getZ());
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    public boolean isReplaceable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() != Blocks.AIR && !state.getBlock().isLeaves(state, world, pos) && state.getBlock() != Blocks.NETHERRACK && state.getBlock() != Blocks.SOUL_SAND && state.getBlock() != NaturaNether.netherTaintedSoil && !state.getBlock().isWood(world, pos);
    }

    @Override
    public void generateTree(Random rand, World worldIn, BlockPos position)
    {
        int height = rand.nextInt(3) + this.minTreeHeight;
        if (height < 4)
        {
            height = 4;
        }

        if (this.seekHeight)
        {
            position = this.findGround(worldIn, position);
            if (position.getY() < 0)
            {
                return;
            }
        }

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            if (!this.checkClear(worldIn, position, height))
            {
                return;
            }

            BlockPos down = position.down();

            IBlockState state = worldIn.getBlockState(down);
            boolean isSoil = (state.getBlock() != null && state.getBlock().canSustainPlant(state, worldIn, down, EnumFacing.UP, NaturaNether.netherSapling) || state.getBlock() == Blocks.NETHERRACK);

            if (isSoil && position.getY() < 256 - height - 1)
            {
                state.getBlock().onPlantGrow(state, worldIn, down, position);

                this.placeCanopy(worldIn, rand, position, height);
                this.placeTrunk(worldIn, position, height);
            }
        }
    }

    boolean checkClear(World worldIn, BlockPos position, int height)
    {
        boolean flag = true;

        for (int j = position.getY(); j <= position.getY() + 1 + height; ++j)
        {
            int range = 1;

            if (j == position.getY())
            {
                range = 0;
            }

            if (j >= position.getY() + 1 + height - 2)
            {
                range = 2;
            }

            MutableBlockPos mutableblockpos = new MutableBlockPos();

            for (int l = position.getX() - range; l <= position.getX() + range && flag; ++l)
            {
                for (int i1 = position.getZ() - range; i1 <= position.getZ() + range && flag; ++i1)
                {
                    if (j >= 0 && j < 256)
                    {
                        if (this.isReplaceable(worldIn, mutableblockpos.setPos(l, j, i1)))
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
        }

        return flag;
    }

    protected void placeCanopy(World worldIn, Random rand, BlockPos position, int height)
    {
        for (int y = position.getY() - 3 + height; y <= position.getY() + height; ++y)
        {
            int subract = y - (position.getY() + height);
            int subract2 = 1 - subract / 2;

            for (int x = position.getX() - subract2; x <= position.getX() + subract2; ++x)
            {
                int mathX = x - position.getX();

                for (int z = position.getZ() - subract2; z <= position.getZ() + subract2; ++z)
                {
                    int mathZ = z - position.getZ();

                    if (Math.abs(mathX) != subract2 || Math.abs(mathZ) != subract2 || rand.nextInt(2) != 0 && subract != 0)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        IBlockState state2 = worldIn.getBlockState(blockpos);

                        if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos))
                        {
                            worldIn.setBlockState(blockpos, this.leaves, 2);
                        }
                    }
                }
            }
        }
    }

    protected void placeTrunk(World worldIn, BlockPos position, int height)
    {
        for (int localHeight = 0; localHeight < height; ++localHeight)
        {
            BlockPos upN = position.up(localHeight);
            IBlockState state2 = worldIn.getBlockState(upN);

            if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
            {
                worldIn.setBlockState(position.up(localHeight), this.log, 2);
            }
        }
    }
}
