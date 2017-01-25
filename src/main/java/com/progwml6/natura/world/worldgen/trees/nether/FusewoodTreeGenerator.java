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

    BlockPos findGround(World world, BlockPos pos)
    {
        boolean foundGround = false;
        int height = pos.getY();

        BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

        do
        {
            position = position.down();
            Block underBlock = world.getBlockState(position).getBlock();

            if (underBlock == Blocks.NETHERRACK || underBlock == Blocks.SOUL_SAND || underBlock == NaturaNether.netherTaintedSoil || position.getY() < 0)
            {
                foundGround = true;
            }
        }
        while (!foundGround);

        return position.up();
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
            if (!this.checkClear(world, pos.getX(), yPos, pos.getY(), height))
            {
                return;
            }

            IBlockState state = world.getBlockState(pos.down());
            Block soil = state.getBlock();
            boolean isSoil = (soil != null && soil.canSustainPlant(state, world, pos.down(), EnumFacing.UP, NaturaNether.netherSapling) || soil == Blocks.NETHERRACK);

            if (isSoil && yPos < 256 - height - 1)
            {
                soil.onPlantGrow(state, world, pos.down(), pos);

                this.placeCanopy(world, random, pos, height);
                this.placeTrunk(world, pos, height);
            }
        }
    }

    boolean checkClear(World world, int xPos, int yPos, int zPos, int treeHeight)
    {
        boolean flag = true;

        for (int y = yPos; y <= yPos + 1 + treeHeight; ++y)
        {
            int range = 1;

            if (y == yPos)
            {
                range = 0;
            }

            if (y >= yPos + 1 + treeHeight - 2)
            {
                range = 2;
            }

            for (int x = xPos - range; x <= xPos + range && flag; ++x)
            {
                for (int z = zPos - range; z <= zPos + range && flag; ++z)
                {
                    if (y >= 0 && y < 256)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        IBlockState state = world.getBlockState(blockpos);
                        Block block = state.getBlock();

                        if (!block.isAir(state, world, blockpos) && !block.isLeaves(state, world, blockpos) && block != Blocks.NETHERRACK && block != Blocks.SOUL_SAND && block != NaturaNether.netherTaintedSoil && !block.isWood(world, blockpos))
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

                        if (state.getBlock() == null || state.getBlock().canBeReplacedByLeaves(state, world, blockpos))
                        {
                            world.setBlockState(blockpos, this.leaves, 2);
                        }
                    }
                }
            }
        }
    }

    protected void placeTrunk(World world, BlockPos pos, int height)
    {
        for (int localHeight = 0; localHeight < height; ++localHeight)
        {
            BlockPos blockpos = new BlockPos(pos.getX(), pos.getY() + localHeight, pos.getZ());
            IBlockState state = world.getBlockState(blockpos);
            Block block = state.getBlock();

            if (block.isAir(state, world, blockpos) || block == null || block.isLeaves(state, world, blockpos))
            {
                world.setBlockState(blockpos, this.log, 2);
            }
        }

        /*while (height >= 0)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block.isAir(state, world, pos) || block.isReplaceable(world, pos) || block.isLeaves(state, world, pos))
            {
                this.setBlockAndMetadata(world, pos, this.log);
            }

            pos = pos.up();
            height--;
        }*/
    }
}
