package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class WillowTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public WillowTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
    }

    public WillowTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
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
        int height;
        if (this.seekHeight)
        {
            position = this.findGround(worldIn, position);
            if (position.getY() < 0)
            {
                return;
            }
        }

        for (height = random.nextInt(this.treeHeightRange) + this.minTreeHeight; worldIn.getBlockState(position.down()).getMaterial() == Material.WATER; position = position.down())
        {
            ;
        }

        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + height; ++j)
            {
                int k = 1;

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + height - 2)
                {
                    k = 3;
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
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, NaturaOverworld.overworldSapling2);

                if (isSoil && position.getY() < worldIn.getHeight() - height - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, position.down(), position);

                    for (int k1 = position.getY() - 3 + height; k1 <= position.getY() + height; ++k1)
                    {
                        int j2 = k1 - (position.getY() + height);
                        int l2 = 2 - j2 / 2;

                        for (int j3 = position.getX() - l2; j3 <= position.getX() + l2; ++j3)
                        {
                            int k3 = j3 - position.getX();

                            for (int i4 = position.getZ() - l2; i4 <= position.getZ() + l2; ++i4)
                            {
                                int j1 = i4 - position.getZ();

                                if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || random.nextInt(2) != 0 && j2 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(j3, k1, i4);
                                    state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
                                    {
                                        this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                                    }
                                }
                            }
                        }
                    }

                    for (int l1 = 0; l1 < height; ++l1)
                    {
                        BlockPos upN = position.up(l1);
                        IBlockState iblockstate1 = worldIn.getBlockState(upN);
                        Block block2 = iblockstate1.getBlock();

                        if (block2.isAir(iblockstate1, worldIn, upN) || block2.isLeaves(iblockstate1, worldIn, upN) || block2 == Blocks.FLOWING_WATER || block2 == Blocks.WATER)
                        {
                            this.setBlockAndMetadata(worldIn, position.up(l1), this.log);
                        }
                    }

                    for (int i2 = position.getY() - 3 + height; i2 <= position.getY() + height; ++i2)
                    {
                        int k2 = i2 - (position.getY() + height);
                        int i3 = 2 - k2 / 2;
                        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                        for (int l3 = position.getX() - i3; l3 <= position.getX() + i3; ++l3)
                        {
                            for (int j4 = position.getZ() - i3; j4 <= position.getZ() + i3; ++j4)
                            {
                                blockpos$mutableblockpos1.setPos(l3, i2, j4);

                                if (worldIn.getBlockState(blockpos$mutableblockpos1).getMaterial() == Material.LEAVES)
                                {
                                    BlockPos blockpos3 = blockpos$mutableblockpos1.west();
                                    BlockPos blockpos4 = blockpos$mutableblockpos1.east();
                                    BlockPos blockpos1 = blockpos$mutableblockpos1.north();
                                    BlockPos blockpos2 = blockpos$mutableblockpos1.south();

                                    if (random.nextInt(4) == 0 && this.isAir(worldIn, blockpos3))
                                    {
                                        this.addDownLeaves(worldIn, blockpos3);
                                    }

                                    if (random.nextInt(4) == 0 && this.isAir(worldIn, blockpos4))
                                    {
                                        this.addDownLeaves(worldIn, blockpos4);
                                    }

                                    if (random.nextInt(4) == 0 && this.isAir(worldIn, blockpos1))
                                    {
                                        this.addDownLeaves(worldIn, blockpos1);
                                    }

                                    if (random.nextInt(4) == 0 && this.isAir(worldIn, blockpos2))
                                    {
                                        this.addDownLeaves(worldIn, blockpos2);
                                    }
                                }
                            }
                        }
                    }
                }
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

    private void addDownLeaves(World worldIn, BlockPos pos)
    {
        this.setBlockAndMetadata(worldIn, pos, this.leaves);
        int i = 4;

        for (pos = pos.down(); this.isAir(worldIn, pos) && i > 0; --i)
        {
            this.setBlockAndMetadata(worldIn, pos, this.leaves);
            pos = pos.down();
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

    private boolean isAir(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos);
    }
}
