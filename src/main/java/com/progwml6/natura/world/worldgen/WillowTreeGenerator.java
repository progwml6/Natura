package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.overworld.NaturaOverworld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
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

        int yPos = pos.getY();

        if (yPos >= 1 && yPos + height + 1 <= 128)
        {
            IBlockState state = world.getBlockState(pos.down());
            Block soil = state.getBlock();
            boolean isSoil = (soil != null && soil.canSustainPlant(state, world, pos.down(), EnumFacing.UP, NaturaOverworld.overworldSapling));

            if (isSoil)
            {
                soil.onPlantGrow(state, world, pos.down(), pos);
                this.placeCanopy(world, random, pos, height);
                this.placeTrunk(world, pos, height);
            }
        }
    }

    @SuppressWarnings("deprecation")
    BlockPos findGround(World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block heightID = state.getBlock();
            if ((heightID == Blocks.DIRT || heightID == Blocks.GRASS || heightID == Blocks.SAND) && !world.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                return pos.up();
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        return pos;
    }

    void placeCanopy(World world, Random random, BlockPos pos, int height)
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

                        if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos) || state.getMaterial() == Material.VINE)
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
