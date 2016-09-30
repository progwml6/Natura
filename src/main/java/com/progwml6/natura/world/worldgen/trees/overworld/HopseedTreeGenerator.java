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

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(l, j, i1));
                            Block block = iblockstate.getBlock();

                            if (!iblockstate.getBlock().isAir(iblockstate, worldIn, blockpos$mutableblockpos.setPos(l, j, i1)) && !iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                if (block != Blocks.WATER && block != Blocks.FLOWING_WATER)
                                {
                                    flag = false;
                                }
                                else if (j > position.getY())
                                {
                                    flag = false;
                                }
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

                IBlockState state = worldIn.getBlockState(position.down());
                Block soil = state.getBlock();
                boolean isSoil = (soil != null && soil.canSustainPlant(state, worldIn, position.down(), EnumFacing.UP, NaturaOverworld.overworldSapling2));

                if (isSoil)
                {
                    soil.onPlantGrow(state, worldIn, position.down(), position);

                    this.growLogs(worldIn, position);
                    this.growLeaves(worldIn, random, position, height);

                    for (int l = 0; l < height; ++l)
                    {
                        BlockPos blockpos = new BlockPos(position.getX(), position.getY() + l, position.getZ());

                        if (worldIn.getBlockState(blockpos).getBlock().isAir(worldIn.getBlockState(blockpos), worldIn, blockpos) || worldIn.getBlockState(blockpos).getBlock().canPlaceBlockAt(worldIn, blockpos) || worldIn.getBlockState(blockpos) == this.leaves)
                        {
                            this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                        }

                        if (l < height - 1)
                        {
                            blockpos = new BlockPos(position.getX() + 1, position.getY() + l, position.getZ());

                            if (worldIn.getBlockState(blockpos).getBlock().isAir(worldIn.getBlockState(blockpos), worldIn, blockpos) || worldIn.getBlockState(blockpos).getBlock().canPlaceBlockAt(worldIn, blockpos) || worldIn.getBlockState(blockpos) == this.leaves)
                            {
                                this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                            }

                            blockpos = new BlockPos(position.getX() + 1, position.getY() + l, position.getZ() + 1);

                            if (worldIn.getBlockState(blockpos).getBlock().isAir(worldIn.getBlockState(blockpos), worldIn, blockpos) || worldIn.getBlockState(blockpos).getBlock().canPlaceBlockAt(worldIn, blockpos) || worldIn.getBlockState(blockpos) == this.leaves)
                            {
                                this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                            }

                            blockpos = new BlockPos(position.getX(), position.getY() + l, position.getZ() + 1);

                            if (worldIn.getBlockState(blockpos).getBlock().isAir(worldIn.getBlockState(blockpos), worldIn, blockpos) || worldIn.getBlockState(blockpos).getBlock().canPlaceBlockAt(worldIn, blockpos) || worldIn.getBlockState(blockpos) == this.leaves)
                            {
                                this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                            }
                        }
                    }
                }
            }
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        boolean foundGround = false;

        int height = Config.seaLevel + 64;

        do
        {
            height--;
            BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

            Block underBlock = world.getBlockState(position).getBlock();

            if (underBlock == Blocks.DIRT || underBlock == Blocks.GRASS || height < Config.seaLevel)
            {
                foundGround = true;
            }
        }
        while (!foundGround);

        return new BlockPos(pos.getX(), height, pos.getZ());
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
}
