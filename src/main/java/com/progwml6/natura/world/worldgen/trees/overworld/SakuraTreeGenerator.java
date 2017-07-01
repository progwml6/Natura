package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.progwml6.natura.common.block.BlockEnumLog;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class SakuraTreeGenerator extends BaseTreeGenerator
{
    private Random rand;

    private World world;

    private BlockPos basePos = BlockPos.ORIGIN;

    int heightLimit;

    int height;

    double heightAttenuation = 0.618D;

    double branchSlope = 0.381D;

    double scaleWidth = 1.0D;

    double leafDensity = 1.0D;

    int trunkSize = 1;

    int heightLimitLimit = 12;

    /** Sets the distance limit for how far away the generator will populate leaves from the base leaf node. */
    int leafDistanceLimit = 4;

    List<FoliageCoordinates> foliageCoords;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean findGround;

    public final boolean isSapling;

    public SakuraTreeGenerator(IBlockState log, IBlockState leaves, boolean findGround, boolean isSapling)
    {
        this.findGround = findGround;
        this.isSapling = isSapling;
        this.log = log;
        this.leaves = leaves;
    }

    /**
     * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
     */
    void generateLeafNodeList()
    {
        this.height = (int) (this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit)
        {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));

        if (i < 1)
        {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.foliageCoords = Lists.<FoliageCoordinates> newArrayList();
        this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k)
        {
            float f = this.layerSize(k);

            if (f >= 0.0F)
            {
                for (int l = 0; l < i; ++l)
                {
                    double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
                    double d1 = this.rand.nextFloat() * 2.0F * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, k - 1, d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

                    if (this.checkBlockLine(blockpos, blockpos1) == -1)
                    {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.branchSlope;
                        int k1 = d4 > j ? j : (int) d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1)
                        {
                            this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void crosSection(BlockPos pos, float p_181631_2_, IBlockState p_181631_3_)
    {
        int i = (int) (p_181631_2_ + 0.618D);

        for (int j = -i; j <= i; ++j)
        {
            for (int k = -i; k <= i; ++k)
            {
                if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= p_181631_2_ * p_181631_2_)
                {
                    BlockPos blockpos = pos.add(j, 0, k);
                    IBlockState state = this.world.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, this.world, blockpos) || state.getBlock().isLeaves(state, this.world, blockpos))
                    {
                        this.setBlockAndMetadata(this.world, blockpos, p_181631_3_);
                    }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float layerSize(int y)
    {
        if (y < this.heightLimit * 0.3F)
        {
            return -1.0F;
        }
        else
        {
            float f = this.heightLimit / 2.0F;
            float f1 = f - y;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    float leafSize(int y)
    {
        return y >= 0 && y < this.leafDistanceLimit ? (y != 0 && y != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void generateLeafNode(BlockPos pos)
    {
        for (int i = 0; i < this.leafDistanceLimit; ++i)
        {
            this.crosSection(pos.up(i), this.leafSize(i), this.leaves);
        }
    }

    void limb(BlockPos p_175937_1_, BlockPos p_175937_2_, IBlockState state)
    {
        BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float) blockpos.getX() / (float) i;
        float f1 = (float) blockpos.getY() / (float) i;
        float f2 = (float) blockpos.getZ() / (float) i;

        for (int j = 0; j <= i; ++j)
        {
            BlockPos blockpos1 = p_175937_1_.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
            BlockEnumLog.EnumAxis enumaxis = this.getLogAxis(p_175937_1_, blockpos1);
            this.setBlockAndMetadata(this.world, blockpos1, state.withProperty(BlockEnumLog.LOG_AXIS, enumaxis));
        }
    }

    /**
     * Returns the absolute greatest distance in the BlockPos object.
     */
    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }

    private BlockEnumLog.EnumAxis getLogAxis(BlockPos p_175938_1_, BlockPos p_175938_2_)
    {
        BlockEnumLog.EnumAxis enumaxis = BlockEnumLog.EnumAxis.Y;
        int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
        int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
        int k = Math.max(i, j);

        if (k > 0)
        {
            if (i == k)
            {
                enumaxis = BlockEnumLog.EnumAxis.X;
            }
            else if (j == k)
            {
                enumaxis = BlockEnumLog.EnumAxis.Z;
            }
        }

        return enumaxis;
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves()
    {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            this.generateLeafNode(foliagecoordinates);
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean leafNodeNeedsBase(int p_76493_1_)
    {
        return p_76493_1_ >= this.heightLimit * 0.2D;
    }

    /**
     * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
     * field that is always 1 to 2.
     */
    void generateTrunk()
    {
        BlockPos blockpos = this.basePos;
        BlockPos blockpos1 = this.basePos.up(this.height);
        this.limb(blockpos, blockpos1, this.log);

        if (this.trunkSize == 2)
        {
            this.limb(blockpos.east(), blockpos1.east(), this.log);
            this.limb(blockpos.east().south(), blockpos1.east().south(), this.log);
            this.limb(blockpos.south(), blockpos1.south(), this.log);
        }
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void generateLeafNodeBases()
    {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            int i = foliagecoordinates.getBranchBase();
            BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

            if (!blockpos.equals(foliagecoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY()))
            {
                this.limb(blockpos, foliagecoordinates, this.log);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float) blockpos.getX() / (float) i;
        float f1 = (float) blockpos.getY() / (float) i;
        float f2 = (float) blockpos.getZ() / (float) i;

        if (i == 0)
        {
            return -1;
        }
        else
        {
            for (int j = 0; j <= i; ++j)
            {
                BlockPos blockpos1 = posOne.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);

                if (!this.isReplaceable(this.world, blockpos1))
                {
                    return j;
                }
            }

            return -1;
        }
    }

    public void setDecorationDefaults()
    {
        this.leafDistanceLimit = 5;
    }

    /**
     * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
     * limit, is valid.
     */
    private boolean validTreeLocation()
    {
        BlockPos down = this.basePos.down();
        IBlockState state = this.world.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, this.world, down, EnumFacing.UP, NaturaOverworld.overworldSapling2);

        if (!isSoil)
        {
            return false;
        }
        else
        {
            int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));

            if (i == -1)
            {
                return true;
            }
            else if (i < 6)
            {
                return false;
            }
            else
            {
                this.heightLimit = i;
                return true;
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

    BlockPos findGround(World world, BlockPos pos)
    {
        if (world.getWorldType() == WorldType.FLAT && this.isSapling)
        {
            boolean foundGround = false;

            int height = Config.flatSeaLevel + 64;

            do
            {
                height--;
                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());
                Block underBlock = world.getBlockState(position).getBlock();

                if (underBlock == Blocks.DIRT || underBlock == Blocks.GRASS || height < Config.flatSeaLevel)
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

                if (underBlock == Blocks.DIRT || underBlock == Blocks.GRASS || height < Config.seaLevel)
                {
                    foundGround = true;
                }
            }
            while (!foundGround);

            return new BlockPos(pos.getX(), height + 1, pos.getZ());
        }
    }

    public boolean isReplaceable(World world, BlockPos pos)
    {
        net.minecraft.block.state.IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    @Override
    public void generateTree(Random random, World worldIn, BlockPos position)
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

        if (this.heightLimit == 0)
        {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation())
        {
            this.world = null; //Fix vanilla Mem leak, holds latest world
        }
        else
        {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            this.world = null; //Fix vanilla Mem leak, holds latest world
        }
    }

    static class FoliageCoordinates extends BlockPos
    {
        private final int branchBase;

        public FoliageCoordinates(BlockPos pos, int branchBase)
        {
            super(pos.getX(), pos.getY(), pos.getZ());

            this.branchBase = branchBase;
        }

        public int getBranchBase()
        {
            return this.branchBase;
        }
    }
}
