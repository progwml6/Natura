package com.progwml6.natura.world.worldgen.berry.nether;

import java.util.Random;

import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.world.worldgen.berry.BaseBerryBushGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class NetherBerryBushGenerator extends BaseBerryBushGenerator
{
    public final IBlockState berryBush;

    public NetherBerryBushGenerator(IBlockState berryBush)
    {
        this.berryBush = berryBush;
    }

    @Override
    public void generateBush(Random random, World world, BlockPos pos)
    {
        pos = this.findGround(world, pos);
        if (pos.getY() < 0)
        {
            return;
        }

        int yPos = pos.getY();

        if (yPos >= 1)
        {
            int size = random.nextInt(10);

            if (size == 9)
            {
                this.generateLargeNode(random, world, pos);
            }
            else if (size >= 7)
            {
                this.generateShrub(random, world, pos);
            }
            else if (size >= 3)
            {
                this.generateSmallNode(random, world, pos);
            }
            else
            {
                this.generateTinyNode(random, world, pos);
            }
        }
    }

    protected void generateLargeNode(Random random, World world, BlockPos pos)
    {
        for (int iterX = pos.getX() - 2; iterX <= pos.getX() + 2; iterX++)
        {
            for (int iterZ = pos.getZ() - 1; iterZ <= pos.getZ() + 1; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() - 2; iterZ <= pos.getZ() - 2; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() + 2; iterZ <= pos.getZ() + 2; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() - 1; iterZ <= pos.getZ() + 1; iterZ++)
            {
                int yPos = pos.getY() + 1;
                BlockPos blockpos = new BlockPos(iterX, yPos, iterZ);

                this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));

                yPos = pos.getY() - 2;
                blockpos = new BlockPos(iterX, yPos, iterZ);

                this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
            }
        }
    }

    protected void generateShrub(Random random, World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (!block.isAir(state, world, pos) && !block.isLeaves(state, world, pos))
            {
                break;
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND)
        {
            pos.up();

            for (int y = pos.getY(); y <= pos.getY() + 2; ++y)
            {
                int subract = y - pos.getY();
                int subract2 = 2 - subract;

                for (int x = pos.getX() - subract2; x <= pos.getX() + subract2; ++x)
                {
                    int mathX = x - pos.getX();

                    for (int z = pos.getZ() - subract2; z <= pos.getZ() + subract2; ++z)
                    {
                        int mathZ = z - pos.getZ();

                        BlockPos blockpos = new BlockPos(x, y, z);
                        IBlockState blockState = world.getBlockState(blockpos);

                        if (Math.abs(mathX) != subract2 || Math.abs(mathZ) != subract2 || random.nextInt(2) != 0 && (block == null || block.canBeReplacedByLeaves(blockState, world, blockpos)))
                        {
                            this.setBlockAndMetadata(random, world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                        }
                    }
                }
            }
        }
    }

    protected void generateSmallNode(Random random, World world, BlockPos pos)
    {
        this.setBlockAndMetadata(random, world, pos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(random, world, pos.east(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(random, world, pos.west(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(random, world, pos.south(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(random, world, pos.north(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }
    }

    protected void generateTinyNode(Random random, World world, BlockPos pos)
    {
        this.setBlockAndMetadata(random, world, pos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
    }

    protected void setBlockAndMetadata(Random random, World world, BlockPos pos, IBlockState stateNew)
    {
        if (!this.isOpaqueCube(world, pos, null))
        {
            world.setBlockState(pos, stateNew, 2);
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if ((block == Blocks.NETHERRACK) || block.canSustainPlant(state, world, pos.down(), EnumFacing.UP, (IPlantable) this.berryBush.getBlock()))
            {
                if (!this.isOpaqueCube(world, pos, pos.up()))
                {
                    return pos.up();
                }
            }
            pos = pos.down();
        }
        while (pos.getY() > 0);

        return pos;
    }

    @SuppressWarnings("deprecation")
    boolean isOpaqueCube(World world, BlockPos pos, @Nullable BlockPos posUp)
    {
        IBlockState state = world.getBlockState(pos);
        if (posUp != null)
        {
            return world.getBlockState(posUp).getBlock().isOpaqueCube(state);
        }
        else
        {
            return world.getBlockState(pos).getBlock().isOpaqueCube(state);
        }
    }

    int randomFullAge(Random random)
    {
        int fruiting = random.nextInt(5) == 0 ? 1 : 0;
        return 2 + fruiting;
    }

    int randomAge(Random random)
    {
        return random.nextInt(3);
    }
}
