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
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class EucalyptusTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public EucalyptusTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
    }

    public EucalyptusTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, treeRange, log, leaves, true);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    @Override
    public void generateTree(Random random, World world, BlockPos pos)
    {
        int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;
        if (this.seekHeight)
        {
            pos = this.findGround(world, pos);
            if (pos.getY() < 0)
            {
                return;
            }
        }
        boolean flag = true;

        if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256)
        {
            for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j)
            {
                int k = 1;

                if (j == pos.getY())
                {
                    k = 0;
                }

                if (j >= pos.getY() + 1 + height - 2)
                {
                    k = 3;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l)
                {
                    for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            IBlockState iblockstate = world.getBlockState(blockpos$mutableblockpos.setPos(l, j, i1));

                            if (!iblockstate.getBlock().isAir(iblockstate, world, blockpos$mutableblockpos.setPos(l, j, i1)) && !iblockstate.getBlock().isLeaves(iblockstate, world, blockpos$mutableblockpos.setPos(l, j, i1)))
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

            if (!flag)
            {
            }
            else
            {
                IBlockState state = world.getBlockState(pos.down());
                Block soil = state.getBlock();
                boolean isSoil = (soil != null && soil.canSustainPlant(state, world, pos.down(), EnumFacing.UP, NaturaOverworld.overworldSapling));

                if (isSoil)
                {
                    soil.onPlantGrow(state, world, pos.down(), pos);

                    this.placeTrunk(world, pos, height);
                    this.genBranch(world, random, pos, height, 1);
                    this.genBranch(world, random, pos, height, 2);
                    this.genBranch(world, random, pos, height, 3);
                    this.genBranch(world, random, pos, height, 4);
                    this.genStraightBranch(world, random, pos, height, 1);
                    this.genStraightBranch(world, random, pos, height, 2);
                    this.genStraightBranch(world, random, pos, height, 3);
                    this.genStraightBranch(world, random, pos, height, 4);
                    this.generateNode(world, random, pos.up(height));
                }
            }
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        int returnHeight = 0;
        IBlockState stateDown = world.getBlockState(pos.down());
        Block blockDown = stateDown.getBlock();

        if (!world.getBlockState(pos).isFullBlock() && (blockDown == Blocks.GRASS || blockDown == Blocks.DIRT))
        {
            return pos;
        }

        int y = 96;

        do
        {
            BlockPos position = new BlockPos(pos.getX(), y, pos.getZ());

            if (y < 32)
            {
                break;
            }

            IBlockState state = world.getBlockState(position);
            Block block = state.getBlock();

            if ((block == Blocks.DIRT || block == Blocks.GRASS) && !world.getBlockState(position.up()).isFullBlock())
            {
                returnHeight = y + 1;
                break;
            }
            y--;
        }
        while (y > 0);

        return new BlockPos(pos.getX(), returnHeight, pos.getZ());
    }

    private void genBranch(World world, Random random, BlockPos pos, int height, int direction)
    {
        int posX = pos.getX();
        int posY = pos.getY() + height - 3;
        int posZ = pos.getZ();
        byte byte0 = 0;
        byte byte1 = 0;

        switch (direction)
        {
        case 1:
            byte0 = 1;
            byte1 = 1;
            break;

        case 2:
            byte0 = -1;
            byte1 = 1;
            break;

        case 3:
            byte0 = 1;
            byte1 = -1;
            break;

        case 4:
            byte0 = -1;
            byte1 = -1;
            break;
        }

        int heightShift = random.nextInt(6);

        for (int bIter = 4; bIter > 0; bIter--)
        {
            if (heightShift % 3 != 0)
            {
                posX += byte0;
            }

            if (heightShift % 3 != 1)
            {
                posZ += byte1;
            }

            int branch = heightShift % 3;

            posY += branch;

            BlockPos blockpos = new BlockPos(posX, posY, posZ);

            if (branch == 2)
            {
                this.setBlockAndMetadata(world, blockpos.down(), this.log);
            }

            this.setBlockAndMetadata(world, blockpos, this.log);

            if (bIter == 1)
            {
                this.generateNode(world, random, blockpos);
            }

            heightShift = random.nextInt(6);
        }
    }

    private void genStraightBranch(World world, Random random, BlockPos pos, int height, int direction)
    {
        int posX = pos.getX();
        int posY = pos.getY() + height - 3;
        int posZ = pos.getZ();

        byte xShift = 0;
        byte zShift = 0;

        switch (direction)
        {
        case 1:
            xShift = 1;
            zShift = 0;
            break;

        case 2:
            xShift = 0;
            zShift = 1;
            break;

        case 3:
            xShift = -1;
            zShift = 0;
            break;

        case 4:
            xShift = 0;
            zShift = -1;
            break;
        }

        int heightShift = random.nextInt(6);

        for (int j2 = 4; j2 > 0; j2--)
        {
            if (xShift == 0)
            {
                posX = (posX + random.nextInt(3)) - 1;
                posZ += zShift;
            }

            if (zShift == 0)
            {
                posX += xShift;
                posZ = (posZ + random.nextInt(3)) - 1;
            }

            int branch = heightShift % 3;

            posY += branch;

            BlockPos blockpos = new BlockPos(posX, posY, posZ);

            if (branch == 2)
            {
                this.setBlockAndMetadata(world, blockpos.down(), this.log);
            }

            this.setBlockAndMetadata(world, blockpos, this.log);

            if (j2 == 1)
            {
                this.generateNode(world, random, blockpos);
            }

            heightShift = random.nextInt(6);
        }
    }

    @SuppressWarnings("deprecation")
    public boolean generateNode(World world, Random random, BlockPos pos)
    {
        this.setBlockAndMetadata(world, pos, this.log);

        for (int xIter = pos.getX() - 2; xIter <= pos.getX() + 2; xIter++)
        {
            for (int zIter = pos.getZ() - 1; zIter <= pos.getZ() + 1; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY(), zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaOverworld.overworldLeaves2 && !block.isFullBlock(state))
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }

        for (int xIter = pos.getX() - 1; xIter <= pos.getX() + 1; xIter++)
        {
            for (int zIter = pos.getZ() - 2; zIter <= pos.getZ() + 2; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY(), zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaOverworld.overworldLeaves2 && !block.isFullBlock(state))
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }

        for (int xIter = pos.getX() - 1; xIter <= pos.getX() + 1; xIter++)
        {
            for (int zIter = pos.getZ() - 1; zIter <= pos.getZ() + 1; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY() + 1, zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaOverworld.overworldLeaves2 && !block.isFullBlock(state))
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }

        return true;
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
