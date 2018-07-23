package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

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
            while (position.getY() > 1 && worldIn.isAirBlock(position) || worldIn.getBlockState(position).getBlock().isLeaves(worldIn.getBlockState(position), worldIn, position))
            {
                position = position.down();
            }

            if (position.getY() < 0)
            {
                return;
            }
        }

        if (!(this.blocksMatch(worldIn, position)))
        {
            return;
        }

        position = position.up();

        boolean hasSpace = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            int radius;

            for (int y = position.getY(); y <= position.getY() + 1 + height; ++y)
            {
                radius = 1;

                if (y == position.getY())
                {
                    radius = 0;
                }

                if (y >= position.getY() + 1 + height - 2)
                {
                    radius = 2;
                }

                MutableBlockPos mutableblockpos = new MutableBlockPos();

                for (int x = position.getX() - radius; x <= position.getX() + radius && hasSpace; ++x)
                {
                    for (int z = position.getZ() - radius; z <= position.getZ() + radius && hasSpace; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                            if (!this.isReplaceable(worldIn, mutableblockpos.setPos(x, y, z)))
                            {
                                hasSpace = false;
                            }
                        }
                        else
                        {
                            hasSpace = false;
                        }
                    }
                }
            }

            if (!hasSpace)
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

    private boolean blocksMatch(World world, BlockPos pos)
    {
        Block underBlock = world.getBlockState(pos).getBlock();

        if (!(underBlock == Blocks.DIRT || underBlock == Blocks.GRASS))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void growLogs(World worldIn, BlockPos positionIn)
    {
        this.setBlockAndMetadata(worldIn, positionIn, this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, 0, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(0, 0, +1), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, 0, +1), this.log);

        this.setBlockAndMetadata(worldIn, positionIn.add(0, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(0, +1, +1), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, +1, +1), this.log);
    }

    protected void growLeaves(World worldIn, Random random, BlockPos positionIn, int height)
    {
        for (int y = positionIn.getY() - 2 + height; y <= positionIn.getY() + height; ++y)
        {
            int subract = y - (positionIn.getY() + height);
            int subract2 = 2 + 1 - subract;

            for (int x = positionIn.getX() - subract2; x <= positionIn.getX() + subract2; ++x)
            {
                int mathX = x - positionIn.getX();

                for (int z = positionIn.getZ() - subract2; z <= positionIn.getZ() + subract2; ++z)
                {
                    int mathZ = z - positionIn.getZ();

                    BlockPos blockpos = new BlockPos(x, y, z);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if ((mathX >= 0 || mathZ >= 0 || mathX * mathX + mathZ * mathZ <= subract2 * subract2) && (mathX <= 0 && mathZ <= 0 || mathX * mathX + mathZ * mathZ <= (subract2 + 1) * (subract2 + 1)) && (random.nextInt(4) != 0 || mathX * mathX + mathZ * mathZ <= (subract2 - 1) * (subract2 - 1)) && (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos)))
                    {
                        this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                    }
                }
            }
        }
    }

    protected void setBlockAndMetadata(World worldIn, BlockPos positionIn, IBlockState stateNew)
    {
        IBlockState state = worldIn.getBlockState(positionIn);
        Block block = state.getBlock();

        if (block.isAir(state, worldIn, positionIn) || block.canPlaceBlockAt(worldIn, positionIn) || worldIn.getBlockState(positionIn) == this.leaves)
        {
            worldIn.setBlockState(positionIn, stateNew, 2);
        }
    }

    public boolean isReplaceable(World worldIn, BlockPos positionIn)
    {
        IBlockState state = worldIn.getBlockState(positionIn);

        return state.getBlock().isAir(state, worldIn, positionIn) || state.getBlock().isLeaves(state, worldIn, positionIn) || state.getBlock().isWood(worldIn, positionIn);
    }

    private void onPlantGrow(World worldIn, BlockPos positionIn, BlockPos source)
    {
        IBlockState state = worldIn.getBlockState(positionIn);

        state.getBlock().onPlantGrow(state, worldIn, positionIn, source);
    }
}
