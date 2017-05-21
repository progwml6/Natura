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

public class DarkwoodTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final IBlockState log;

    public final IBlockState leaves;

    public final IBlockState flowering;

    public final IBlockState fruiting;

    public final boolean seekHeight;

    public DarkwoodTreeGenerator(int treeHeight, IBlockState log, IBlockState leaves, IBlockState flowering, IBlockState fruiting, boolean seekHeight)
    {
        this.minTreeHeight = treeHeight;
        this.log = log;
        this.leaves = leaves;
        this.flowering = flowering;
        this.fruiting = fruiting;
        this.seekHeight = seekHeight;
    }

    public DarkwoodTreeGenerator(int treeHeight, IBlockState log, IBlockState leaves, IBlockState flowering, IBlockState fruiting)
    {
        this(treeHeight, log, leaves, flowering, fruiting, true);
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
    public void generateTree(Random rand, World worldIn, BlockPos position)
    {
        int heightRange = rand.nextInt(3) + this.minTreeHeight;

        if (heightRange < 4)
        {
            heightRange = 4;
        }

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
            if (checkIfCanGrow(position, heightRange, worldIn))
            {
                IBlockState state = worldIn.getBlockState(position.down());
                Block soil = state.getBlock();
                boolean isSoil = (soil != null && soil.canSustainPlant(state, worldIn, position.down(), EnumFacing.UP, NaturaNether.netherSapling) || soil == Blocks.NETHERRACK);

                if (isSoil && position.getY() < 256 - heightRange - 1)
                {
                    soil.onPlantGrow(state, worldIn, position.down(), position);

                    this.placeCanopy(worldIn, rand, position, heightRange);
                    this.placeTrunk(worldIn, position, heightRange);
                }
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

                        if (!worldIn.isAirBlock(pos) && !block.isAir(state, worldIn, pos) && !block.isLeaves(state, worldIn, pos) && block != Blocks.NETHERRACK && block != Blocks.SOUL_SAND && block != NaturaNether.netherTaintedSoil && !block.isWood(worldIn, pos))
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
                            world.setBlockState(blockpos, this.getRandomizedLeaves(random), 2);
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

    protected IBlockState getRandomizedLeaves(Random random)
    {
        return (random.nextInt(25) == 0 ? this.fruiting : random.nextInt(15) == 0 ? this.flowering : this.leaves);
    }
}
