package com.progwml6.natura.world.worldgen.saguaro;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class SaguaroGenerator implements IWorldGenerator
{
    private Random rand;

    private World world;

    private BlockPos basePos = BlockPos.ORIGIN;

    public final IBlockState saguaro;

    public final boolean findGround;

    public final boolean isBaby;

    public SaguaroGenerator(IBlockState saguaro, boolean findGround, boolean isBaby)
    {
        this.saguaro = saguaro;
        this.findGround = findGround;
        this.isBaby = isBaby;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    public void generateSaguaro(Random random, World worldIn, BlockPos position)
    {
        this.world = worldIn;

        if (this.findGround)
        {
            this.basePos = this.findGround(worldIn, position);
        }
        else
        {
            this.basePos = position;
        }

        this.rand = new Random(random.nextLong());

        if (!this.validSaguaroLocation())
        {
            this.world = null; //Fix vanilla Mem leak, holds latest world
        }
        else
        {
            this.generateSaguaro();
            this.world = null; //Fix vanilla Mem leak, holds latest world
        }
    }

    private boolean validSaguaroLocation()
    {
        BlockPos down = this.basePos.down();

        IBlockState currentState = this.world.getBlockState(this.basePos);
        Block currentBlock = currentState.getBlock();

        IBlockState stateBelow = world.getBlockState(down);
        Block blockBelow = stateBelow.getBlock();

        if (!this.world.isAirBlock(this.basePos))
        {
            if (currentBlock == NaturaOverworld.saguaroBaby)
            {
                if (blockBelow == null || !blockBelow.canSustainPlant(stateBelow, this.world, down, net.minecraft.util.EnumFacing.UP, NaturaOverworld.saguaro))
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            if (blockBelow == null || !blockBelow.canSustainPlant(stateBelow, this.world, down, net.minecraft.util.EnumFacing.UP, NaturaOverworld.saguaro))
            {
                return false;
            }
        }

        return true;
    }

    void generateSaguaro()
    {
        if (this.rand.nextInt(20) == 0)
        {
            this.generateCactusTree();
        }
        else
        {
            this.generateSmallCactus();
        }
    }

    void generateCactusTree()
    {
        for (int height = 0; height < 6; height++)
        {
            this.setBlock(this.basePos.up(height));
        }

        this.setBlock(this.basePos.up(2).east());
        this.setBlock(this.basePos.up(2).west());
        this.setBlock(this.basePos.up(2).south());
        this.setBlock(this.basePos.up(2).north());

        for (int height = 0; height < 2; height++)
        {
            BlockPos pos = this.basePos.up(height + 2);
            this.setBlock(pos.east(2));
            this.setBlock(pos.west(2));
            this.setBlock(pos.south(2));
            this.setBlock(pos.north(2));

            pos = this.basePos.up(height + 3);
            this.setBlock(pos.east(3));
            this.setBlock(pos.west(3));
            this.setBlock(pos.south(3));
            this.setBlock(pos.north(3));

            pos = this.basePos.up(height + 5);
            this.setBlock(pos.east());
            this.setBlock(pos.west());
            this.setBlock(pos.south());
            this.setBlock(pos.north());
        }
    }

    void generateSmallCactus()
    {
        int height = this.rand.nextInt(4) + 3;

        for (int i = 0; i < height; i++)
        {
            this.setBlock(this.basePos.up(i));
        }

        int branchY = height >= 5 ? 2 : 1;

        int size;

        BlockPos pos = this.basePos.up(branchY);

        if (this.rand.nextBoolean())
        {
            size = this.rand.nextInt(height - branchY) + branchY - this.rand.nextInt(3);

            for (int branch = 0; branch < size; branch++)
            {
                this.setBlock(pos.up(branch).east());
            }

        }

        if (this.rand.nextBoolean())
        {
            size = this.rand.nextInt(height - branchY) + branchY - this.rand.nextInt(3);

            for (int branch = 0; branch < size; branch++)
            {
                this.setBlock(pos.up(branch).west());
            }
        }

        if (this.rand.nextBoolean())
        {
            size = this.rand.nextInt(height - branchY) + branchY - this.rand.nextInt(3);

            for (int branch = 0; branch < size; branch++)
            {
                this.setBlock(pos.up(branch).south());
            }
        }

        if (this.rand.nextBoolean())
        {
            size = this.rand.nextInt(height - branchY) + branchY - this.rand.nextInt(3);

            for (int branch = 0; branch < size; branch++)
            {
                this.setBlock(pos.up(branch).north());
            }
        }
    }

    void setBlock(BlockPos pos)
    {
        if (!this.world.getBlockState(pos).isOpaqueCube())
        {
            this.world.setBlockState(pos, this.saguaro, 2);
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        if (world.getWorldType() == WorldType.FLAT && this.isBaby)
        {
            boolean foundGround = false;

            int height = Config.flatSeaLevel + 64;

            do
            {
                height--;
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());
                Block underBlock = world.getBlockState(position).getBlock();

                if (underBlock == Blocks.SAND || height < Config.flatSeaLevel)
                {
                    foundGround = true;
                }
            }
            while (!foundGround);

            return new BlockPos(pos.getX(), height + 1, pos.getZ());
        }
        else
        {
            boolean foundGround = false;

            int height = Config.seaLevel + 64;

            do
            {
                height--;
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());
                Block underBlock = world.getBlockState(position).getBlock();

                if (underBlock == Blocks.SAND || height < Config.seaLevel)
                {
                    foundGround = true;
                }
            }
            while (!foundGround);

            return new BlockPos(pos.getX(), height + 1, pos.getZ());
        }
    }

}
