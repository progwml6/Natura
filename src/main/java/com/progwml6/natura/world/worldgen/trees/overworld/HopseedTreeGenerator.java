package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class HopseedTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public final boolean isSapling;

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight, boolean isSapling)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
        this.isSapling = isSapling;
    }

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, treeRange, log, leaves, true, false);
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

        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + height; ++j)
            {
                int k = 2;

                if (j == position.getY())
                {
                    k = 1;
                }

                if (j >= position.getY() + 1 + height - 2)
                {
                    k = 2;
                }

                MutableBlockPos mutableblockpos = new MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            if (!this.isReplaceable(worldIn, mutableblockpos.setPos(l, j, i1)))
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
                BlockPos downPosition = position.down();

                IBlockState state = worldIn.getBlockState(downPosition);

                boolean isSoil = (state.getBlock() != null && state.getBlock().canSustainPlant(state, worldIn, downPosition, EnumFacing.UP, NaturaOverworld.overworldSapling2));

                if (isSoil && position.getY() < 256 - height - 1)
                {
                    this.onPlantGrow(worldIn, downPosition, position);
                    this.onPlantGrow(worldIn, downPosition.east(), position);
                    this.onPlantGrow(worldIn, downPosition.south(), position);
                    this.onPlantGrow(worldIn, downPosition.south().east(), position);

                    this.growLogs(worldIn, position);

                    this.growLeaves(worldIn, random, position, height);

                    for (int l = 0; l < height; ++l)
                    {
                        BlockPos blockpos = position.up(l);

                        IBlockState newState = worldIn.getBlockState(blockpos);

                        if (newState.getBlock() == Blocks.AIR || newState.getBlock() == null || newState.getBlock().isLeaves(newState, worldIn, blockpos))
                        {
                            worldIn.setBlockState(blockpos, this.log, 0);
                        }

                        if (l < height - 1)
                        {
                            blockpos = position.add(1, l, 0);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR || newState.getBlock() == null || newState.getBlock().isLeaves(newState, worldIn, blockpos))
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }

                            blockpos = position.add(1, l, 1);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR || newState.getBlock() == null || newState.getBlock().isLeaves(newState, worldIn, blockpos))
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }

                            blockpos = position.add(0, l, 1);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR || newState.getBlock() == null || newState.getBlock().isLeaves(newState, worldIn, blockpos))
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        if (world.getWorldType() == WorldType.FLAT && this.isSapling)
        {
            int returnHeight = 0;

            boolean foundGround = false;

            int height = Config.flatSeaLevel + 64;

            do
            {
                height--;
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

                Block underBlock = world.getBlockState(position).getBlock();

                if (underBlock == Blocks.DIRT || underBlock == Blocks.GRASS || height < Config.flatSeaLevel)
                {
                    returnHeight = height + 1;

                    foundGround = true;
                }
            }
            while (!foundGround);

            return new BlockPos(pos.getX(), returnHeight, pos.getZ());
        }
        else
        {
            int returnHeight = 0;

            boolean foundGround = false;

            int height = Config.seaLevel + 64;

            do
            {
                height--;
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

                Block underBlock = world.getBlockState(position).getBlock();

                if (underBlock == Blocks.DIRT || underBlock == Blocks.GRASS || height < Config.seaLevel)
                {
                    returnHeight = height + 1;

                    foundGround = true;
                }
            }
            while (!foundGround);

            return new BlockPos(pos.getX(), returnHeight, pos.getZ());
        }
    }

    private void growLogs(World worldIn, BlockPos position)
    {
        this.setBlockAndMetadata(worldIn, position, this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, 0, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(0, 0, +1), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, 0, +1), this.log);

        this.setBlockAndMetadata(worldIn, position.add(0, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, position.add(0, +1, +1), this.log);
        this.setBlockAndMetadata(worldIn, position.add(+1, +1, +1), this.log);
    }

    protected void growLeaves(World world, Random random, BlockPos pos, int height)
    {
        for (int y = pos.getY() - 2 + height; y <= pos.getY() + height; ++y)
        {
            int subract = y - (pos.getY() + height);
            int subract2 = 2 + 1 - subract;

            for (int x = pos.getX() - subract2; x <= pos.getX() + subract2; ++x)
            {
                int mathX = x - pos.getX();

                for (int z = pos.getZ() - subract2; z <= pos.getZ() + subract2; ++z)
                {
                    int mathZ = z - pos.getZ();

                    BlockPos blockpos = new BlockPos(x, y, z);
                    IBlockState state = world.getBlockState(blockpos);

                    if ((mathX >= 0 || mathZ >= 0 || mathX * mathX + mathZ * mathZ <= subract2 * subract2) && (mathX <= 0 && mathZ <= 0 || mathX * mathX + mathZ * mathZ <= (subract2 + 1) * (subract2 + 1)) && (random.nextInt(4) != 0 || mathX * mathX + mathZ * mathZ <= (subract2 - 1) * (subract2 - 1)) && (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos) || state.getBlock().canBeReplacedByLeaves(state, world, blockpos)))
                    {
                        this.setBlockAndMetadata(world, blockpos, this.leaves);
                    }
                }
            }
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

    public boolean isReplaceable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos);
    }

    private void onPlantGrow(World world, BlockPos pos, BlockPos source)
    {
        IBlockState state = world.getBlockState(pos);

        state.getBlock().onPlantGrow(state, world, pos, source);
    }
}
