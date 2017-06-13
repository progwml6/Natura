package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class OverworldTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public final boolean isSapling;

    public OverworldTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight, boolean isSapling)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
        this.isSapling = isSapling;
    }

    public OverworldTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, treeRange, log, leaves, true, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    @Override
    public void generateTree(Random rand, World worldIn, BlockPos position)
    {
        int heightRange = rand.nextInt(this.treeHeightRange) + this.minTreeHeight;

        if (this.seekHeight)
        {
            position = this.findGround(worldIn, position);

            if (position.getY() < 0)
            {
                return;
            }
        }

        if (position.getY() >= 1 && position.getY() + heightRange + 1 <= 256)
        {
            IBlockState state = worldIn.getBlockState(position.down());
            Block soil = state.getBlock();
            boolean isSoil = (soil != null && soil.canSustainPlant(state, worldIn, position.down(), EnumFacing.UP, NaturaOverworld.overworldSapling));

            if (isSoil)
            {
                if (!checkIfCanGrow(position, heightRange, worldIn))
                {
                    return;
                }

                soil.onPlantGrow(state, worldIn, position.down(), position);
                this.placeCanopy(worldIn, rand, position, heightRange);
                this.placeTrunk(worldIn, position, heightRange);
            }
        }
    }

    private boolean checkIfCanGrow(BlockPos position, int heightRange, World worldIn)
    {
        boolean canGrowTree = true;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

        byte range;
        int z;

        for (int y = position.getY(); y <= position.getY() + 1 + heightRange; ++y)
        {
            range = 1;

            if (y == position.getY())
            {
                range = 0;
            }

            if (y >= position.getY() + 1 + heightRange - 2)
            {
                range = 2;
            }

            for (int x = position.getX() - range; x <= position.getX() + range && canGrowTree; ++x)
            {
                for (z = position.getZ() - range; z <= position.getZ() + range && canGrowTree; ++z)
                {
                    if (y >= 0 && y < worldIn.getActualHeight())
                    {
                        pos.setPos(x, y, z);

                        IBlockState state = worldIn.getBlockState(pos);
                        Block block = state.getBlock();

                        if (block != null && block != NaturaOverworld.overworldSapling || !block.isLeaves(state, worldIn, pos))
                        {
                            canGrowTree = false;
                        }
                    }
                    else
                    {
                        canGrowTree = false;
                    }
                }
            }
        }

        return canGrowTree;
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        int returnHeight = 0;

        int height = pos.getY();

        if (world.getWorldType() == WorldType.FLAT && this.isSapling)
        {
            do
            {
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

                Block block = world.getBlockState(position).getBlock();

                if ((block == Blocks.DIRT || block == Blocks.GRASS) && !world.getBlockState(position.up()).isFullBlock())
                {
                    returnHeight = height + 1;
                    break;
                }

                height--;
            }
            while (height > Config.flatSeaLevel);

            return new BlockPos(pos.getX(), returnHeight, pos.getZ());
        }
        else
        {

            do
            {
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

                Block block = world.getBlockState(position).getBlock();

                if ((block == Blocks.DIRT || block == Blocks.GRASS) && !world.getBlockState(position.up()).isFullBlock())
                {
                    returnHeight = height + 1;
                    break;
                }

                height--;
            }
            while (height > Config.seaLevel);

            return new BlockPos(pos.getX(), returnHeight, pos.getZ());
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

                        if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().canBeReplacedByLeaves(state, world, blockpos))
                        {
                            world.setBlockState(blockpos, this.leaves, 2);
                        }
                    }
                }
            }
        }
    }

    protected boolean canGrowInto(Block blockType)
    {
        Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.GRASS || blockType == Blocks.DIRT || blockType == Blocks.LOG || blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE;
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
    }
}
