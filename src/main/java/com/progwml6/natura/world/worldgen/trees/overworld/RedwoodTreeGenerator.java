package com.progwml6.natura.world.worldgen.trees.overworld;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockRedwoodLog;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;


public class RedwoodTreeGenerator extends BaseTreeGenerator
{

    private static final byte otherCoordPairs[] = { 2, 0, 0, 1, 2, 1 };
    private Random rand;
    private World worldObj;
    private int basePos[] = { 0, 0, 0 };
    private int heightLimit;
    private int height;
    private double heightAttenuation;
    private double field_874_i;
    private double field_873_j;
    private double field_872_k;
    private int heightLimitLimit;
    private int leafDistanceLimit;
    private int leafNodes[][];


    public final IBlockState log = NaturaOverworld.redwoodLog.getDefaultState();
    public final IBlockState leaves = NaturaOverworld.redwoodLeaves.getDefaultState();




    public RedwoodTreeGenerator()
    {
        rand = new Random();
        heightLimit = 0;
        heightAttenuation = 0.61799999999999999D;
        field_874_i = 0.38100000000000001D;
        field_873_j = 1.0D;
        field_872_k = 1.0D;
        heightLimitLimit = 12;
        leafDistanceLimit = 4;

    }

    private int findGround(World world, int x, int z)
    {
        boolean foundGround = false;
        int height = 256;
        do
        {
            height--;
            Block underID = world.getBlockState(new BlockPos(x,height,z)).getBlock();
            if(underID == Blocks.DIRT || underID == Blocks.GRASS || height < Config.seaLevel)
                foundGround = true;
        } while (!foundGround);
        return height;
    }

    private boolean isValidSpawn(World world, BlockPos pos)
    {
        Block bID = world.getBlockState(pos).getBlock();
        boolean ground = bID == Blocks.DIRT || bID == Blocks.GRASS;

        boolean good = true;

        for(int iZ= -2; iZ <= 2; iZ++){
            for(int iX = -2; iX <= 2; iX++) {
                BlockPos posTest = pos.north(iZ);
                posTest = posTest.west(iX);

                Block testBlock = world.getBlockState(posTest).getBlock();
                if(testBlock == Blocks.WATER || testBlock == Blocks.AIR) {
                    good = false;
                }
            }
        }

        return good & ground;
    }


    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {}

    @Override
    public void generateTree(Random random, World worldIn, BlockPos position) {
        this.generate(worldIn,random,position.getX(), position.getZ());
    }

    public boolean generate(World world, Random random, int x, int z)
    {

        int groundPoint = findGround(world,x, z);
        if (!isValidSpawn(world, new BlockPos(x,groundPoint,z)))
                return false;

        int treeHeight = random.nextInt(60) + 80;
        worldObj = world;
        long ran = random.nextLong();
        rand.setSeed(ran);
        basePos[0] = x;
        basePos[1] = groundPoint;
        basePos[2] = z;
        heightLimit = 5 + rand.nextInt(heightLimitLimit);

        if (treeHeight > 120)
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 10)
                    genRing13(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 10)
                    genRing12(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 3 / 10)
                    genRing11(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 4 / 10)
                    genRing10(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 5 / 10)
                {
                    genRing9(world, x, currentHeight + groundPoint, z);
                    growLowBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 6 / 10)
                {
                    genRing8(world, x, currentHeight + groundPoint, z);
                    growLowBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 7 / 10)
                {
                    genRing7(world, x, currentHeight + groundPoint, z);
                    growMiddleBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 8 / 10)
                {
                    genRing6(world, x, currentHeight + groundPoint, z);
                    growMiddleBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 9 / 10)
                {
                    genRing5(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
            }

            growBigRoots(world, x, groundPoint - 1, z);
            growTop(x, height + groundPoint, z);
        }
        else if (treeHeight > 100)
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 8)
                    genRing11(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 8) {
                    genRing10(world, x, currentHeight + groundPoint, z);
                } else if (currentHeight < treeHeight * 3 / 8) {
                    genRing9(world, x, currentHeight + groundPoint, z);
                } else if (currentHeight < treeHeight * 4 / 8) {
                    genRing8(world, x, currentHeight + groundPoint, z);
                } else if (currentHeight < ((treeHeight * 5) / 8))
                {
                    genRing7(world, x, currentHeight + groundPoint, z);
                    growMiddleBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 6 / 8)
                {
                    genRing6(world, x, currentHeight + groundPoint, z);
                    growMiddleBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 7 / 8)
                {
                    genRing5(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
            }

            growMediumRoots(world, x, groundPoint - 1, z);
            growTop(x, height + groundPoint, z);
        }

        else
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 6)
                    genRing9(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 6)
                    genRing8(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 3 / 6)
                    genRing7(world, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 4 / 6)
                {
                    genRing6(world, x, currentHeight + groundPoint, z);
                    growMiddleBranch(random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 5 / 6)
                {
                    genRing5(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, x, currentHeight + groundPoint, z);
                    growHighBranch(random, x, currentHeight + groundPoint, z);
                }
            }
            growSmallRoots(world, x, groundPoint - 1, z);
            growTop(x, height + groundPoint, z);
        }
        return true;
    }

    private boolean growTop(int x, int y, int z)
    {
        basePos[0] = x;
        basePos[1] = y + 4;
        basePos[2] = z;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        basePos[0] = x;
        basePos[1] = y + 4;
        basePos[2] = z;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        basePos[0] = x;
        basePos[1] = y;
        basePos[2] = z;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        basePos[0] = x;
        basePos[1] = y;
        basePos[2] = z;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        return false;
    }

    private boolean growHighBranch(Random random, int x, int y, int z)
    {
        for (int i = 0; i < 3; i++)
        {
            basePos[0] = (x + random.nextInt(21)) - 10;
            basePos[1] = y;
            basePos[2] = (z + random.nextInt(21)) - 10;
            generateLeafNodeList();
            generateLeaves();
            generateLeafNodeBases();
        }
        return false;
    }

    private boolean growMiddleBranch(Random random, int x, int y, int z)
    {
        for (int i = 0; i < 6; i++)
        {
            basePos[0] = (x + random.nextInt(31)) - 15;
            basePos[1] = y;
            basePos[2] = (z + random.nextInt(31)) - 15;
            generateLeafNodeList();
            generateLeaves();
            generateLeafNodeBases();
        }

        return false;
    }

    private boolean growLowBranch(Random random, int i, int j, int k)
    {
        basePos[0] = (i + random.nextInt(17)) - 8;
        basePos[1] = j;
        basePos[2] = (k + random.nextInt(17)) - 8;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        if (random.nextInt(2) == 0)
        {
            basePos[0] = (i + random.nextInt(17)) - 8;
            basePos[1] = j;
            basePos[2] = (k + random.nextInt(17)) - 8;
            generateLeafNodeList();
            generateLeaves();
            generateLeafNodeBases();
        }
        basePos[0] = (i + random.nextInt(17)) - 8;
        basePos[1] = j;
        basePos[2] = (k + random.nextInt(17)) - 8;
        generateLeafNodeList();
        generateLeaves();
        generateLeafNodeBases();
        return false;
    }

    private boolean growSmallRoots(World world, int i, int j, int k)
    {
        genRing9(world, i, j, k);
        smallRoot1(world, i, j - 1, k);
        smallRoot1(world, i, j - 2, k);
        smallRoot1(world, i, j - 3, k);
        smallRoot2(world, i, j - 4, k);
        smallRoot2(world, i, j - 5, k);
        smallRoot3(world, i, j - 6, k);
        smallRoot3(world, i, j - 7, k);
        smallRoot3(world, i, j - 8, k);
        smallRoot3(world, i, j - 9, k);
        smallRoot4(world, i, j - 10, k);
        smallRoot4(world, i, j - 11, k);
        return true;
    }

    private boolean growMediumRoots(World world, int i, int j, int k)
    {
        genRing11(world, i, j, k);
        mediumRoot1(world, i, j - 1, k);
        mediumRoot1(world, i, j - 2, k);
        mediumRoot1(world, i, j - 3, k);
        mediumRoot2(world, i, j - 4, k);
        mediumRoot2(world, i, j - 5, k);
        mediumRoot3(world, i, j - 6, k);
        mediumRoot3(world, i, j - 7, k);
        mediumRoot3(world, i, j - 8, k);
        mediumRoot3(world, i, j - 9, k);
        mediumRoot4(world, i, j - 10, k);
        mediumRoot4(world, i, j - 11, k);
        mediumRoot5(world, i, j - 12, k);
        mediumRoot5(world, i, j - 13, k);
        mediumRoot5(world, i, j - 14, k);
        return true;
    }

    private boolean growBigRoots(World world, int i, int j, int k)
    {
        genRing13(world, i, j, k);
        bigRoot1(world, i, j - 1, k);
        bigRoot1(world, i, j - 2, k);
        bigRoot1(world, i, j - 3, k);
        bigRoot2(world, i, j - 4, k);
        bigRoot2(world, i, j - 5, k);
        bigRoot3(world, i, j - 6, k);
        bigRoot3(world, i, j - 7, k);
        bigRoot3(world, i, j - 8, k);
        bigRoot3(world, i, j - 9, k);
        bigRoot4(world, i, j - 10, k);
        bigRoot4(world, i, j - 11, k);
        bigRoot5(world, i, j - 12, k);
        bigRoot5(world, i, j - 13, k);
        bigRoot5(world, i, j - 14, k);
        bigRoot6(world, i, j - 15, k);
        bigRoot6(world, i, j - 16, k);
        bigRoot6(world, i, j - 17, k);
        bigRoot6(world, i, j - 18, k);
        return true;
    }

    private void setBlockAndNotifyAdequately1(World world, int x, int y, int z, BlockRedwoodLog.RedwoodType setType)
    {
        Block checkBlock = world.getBlockState(new BlockPos(x,y,z)).getBlock();
        if(checkBlock == Blocks.BEDROCK) return;
        world.setBlockState(new BlockPos(x,y,z),log.withProperty(BlockRedwoodLog.TYPE,setType));
    }


    private boolean smallRoot1(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i,j,k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean smallRoot2(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i,j,k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean smallRoot3(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean smallRoot4(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean mediumRoot1(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean mediumRoot2(World world, int i, int j, int k)
    {
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean mediumRoot3(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean mediumRoot4(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean mediumRoot5(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot1(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 6, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 6, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 6, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 6, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 6, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 6, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 6, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 6, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot2(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 6, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot3(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot4(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot5(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean bigRoot6(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.ROOT);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.ROOT);
        }
        return true;
    }

    private boolean genRing13(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 6, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing12(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 6, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 6, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 5, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 6, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 6, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing11(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 5, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 5, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 5, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 5, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing10(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing9(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 4, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 4, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 4, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 4, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing8(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing7(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 3, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 3, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 3, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 3, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing6(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing5(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 2, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    @SuppressWarnings("unused")
    public boolean genRing4(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 2, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 2, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 2, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    @SuppressWarnings("unused")
    public boolean genRing3s(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private boolean genRing3(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.HEART);
            setBlockAndNotifyAdequately1(world, i, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i + 1, j, k + 1, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    @SuppressWarnings("unused")
    public boolean genRing2(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i - 1, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i - 1, j, k, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k - 1, BlockRedwoodLog.RedwoodType.BARK);
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    @SuppressWarnings("unused")
    public boolean genRing1(World world, int i, int j, int k)
    {
        if (world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.BEDROCK && j > 0)
        {
            setBlockAndNotifyAdequately1(world, i, j, k, BlockRedwoodLog.RedwoodType.BARK);
        }
        return true;
    }

    private void generateLeafNodeList()
    {
        height = (int) ((double) heightLimit * heightAttenuation);
        if (height >= heightLimit)
        {
            height = heightLimit - 1;
        }
        int i = (int) (1.3819999999999999D + Math.pow((field_872_k * (double) heightLimit) / 13D, 2D));
        if (i < 1)
        {
            i = 1;
        }
        int ai[][] = new int[i * heightLimit][4];
        int j = (basePos[1] + heightLimit) - leafDistanceLimit;
        int k = 1;
        int l = basePos[1] + height;
        int i1 = j - basePos[1];
        ai[0][0] = basePos[0];
        ai[0][1] = j;
        ai[0][2] = basePos[2];
        ai[0][3] = l;
        j--;
        while (i1 >= 0)
        {
            int j1 = 0;
            float f = func_528_a(i1);
            if (f < 0.0F)
            {
                j--;
                i1--;
            }
            else
            {
                double d = 0.5D;
                for (; j1 < i; j1++)
                {
                    double d1 = field_873_j * ((double) f * ((double) rand.nextFloat() + 0.32800000000000001D));
                    double d2 = (double) rand.nextFloat() * 2D * 3.1415899999999999D;
                    int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + (double) basePos[0] + d);
                    int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + (double) basePos[2] + d);
                    int ai1[] = { k1, j, l1 };
                    int ai2[] = { k1, j + leafDistanceLimit, l1 };
                    if (checkBlockLine(ai1, ai2) != -1)
                    {
                        continue;
                    }
                    int ai3[] = { basePos[0], basePos[1], basePos[2] };
                    double d3 = Math.sqrt(Math.pow(Math.abs(basePos[0] - ai1[0]), 2D) + Math.pow(Math.abs(basePos[2] - ai1[2]), 2D));
                    double d4 = d3 * field_874_i;
                    if ((double) ai1[1] - d4 > (double) l)
                    {
                        ai3[1] = l;
                    }
                    else
                    {
                        ai3[1] = (int) ((double) ai1[1] - d4);
                    }
                    if (checkBlockLine(ai3, ai1) == -1)
                    {
                        ai[k][0] = k1;
                        ai[k][1] = j;
                        ai[k][2] = l1;
                        ai[k][3] = ai3[1];
                        k++;
                    }
                }

                j--;
                i1--;
            }
        }
        leafNodes = new int[k][4];
        System.arraycopy(ai, 0, leafNodes, 0, k);
    }

    private void func_523_a(int i, int j, int k, float f, byte byte0, @SuppressWarnings("unused") Block l)
    {
        int i1 = (int) ((double) f + 0.61799999999999999D);
        byte byte1 = otherCoordPairs[byte0];
        byte byte2 = otherCoordPairs[byte0 + 3];
        int ai[] = { i, j, k };
        int ai1[] = { 0, 0, 0 };
        int j1 = -i1;
        //noinspection unused  TODO get rid of  these crazy old funcs
        int k1 = -i1;
        ai1[byte0] = ai[byte0];
        for (; j1 <= i1; j1++)
        {
            ai1[byte1] = ai[byte1] + j1;
            for (int l1 = -i1; l1 <= i1;)
            {
                double d = Math.sqrt(Math.pow((double) Math.abs(j1) + 0.5D, 2D) + Math.pow((double) Math.abs(l1) + 0.5D, 2D));
                if (d > (double) f)
                {
                    l1++;
                }
                else
                {
                    ai1[byte2] = ai[byte2] + l1;
                    Block i2 = worldObj.getBlockState(new BlockPos(ai1[0], ai1[1], ai1[2])).getBlock();
                    if (i2 != Blocks.AIR && i2 != Blocks.LEAVES)
                    {
                        l1++;
                    }
                    else
                    {
                        worldObj.setBlockState(new BlockPos(ai1[0], ai1[1], ai1[2]),leaves.withProperty(BlockRedwoodLeaves.TYPE,BlockRedwoodLeaves.RedwoodType.NORMAL));
                        //setBlockAndNotifyAdequately1(worldObj, ai1[0], ai1[1], ai1[2],leaves,BlockRedwoodLeaves.RedwoodType.NORMAL.ordinal());
                        l1++;
                    }
                }
            }
        }
    }

    private float func_528_a(int i)
    {
        if ((double) i < (double) (float) heightLimit * 0.29999999999999999D)
        {
            return -1.618F;
        }
        float f = (float) heightLimit / 2.0F;
        float f1 = (float) heightLimit / 2.0F - (float) i;
        float f2;
        if (f1 == 0.0F)
        {
            f2 = f;
        }
        else if (Math.abs(f1) >= f)
        {
            f2 = 0.0F;
        }
        else
        {
            f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2D) - Math.pow(Math.abs(f1), 2D));
        }
        f2 *= 0.5F;
        return f2;
    }

    private float func_526_b(int i)
    {
        if (i < 0 || i >= leafDistanceLimit)
        {
            return -1F;
        }
        else
        {
            return i == 0 || i == leafDistanceLimit - 1 ? 2.0F : 3F;
        }
    }

    private void generateLeafNode(int i, int j, int k)
    {
        int l = j;
        for (int i1 = j + leafDistanceLimit; l < i1; l++)
        {
            float f = func_526_b(l - j);
            func_523_a(i, l, k, f, (byte) 1,  leaves.getBlock()  );
        }
    }

    private void placeBlockLine(int ai[], int ai1[])//, Block i)
    {
        int ai2[] = { 0, 0, 0 };
        byte byte0 = 0;
        int j = 0;
        for (; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if (Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if (ai2[j] == 0)
        {
            return;
        }
        byte byte1 = otherCoordPairs[j];
        byte byte2 = otherCoordPairs[j + 3];
        byte byte3;
        if (ai2[j] > 0)
        {
            byte3 = 1;
        }
        else
        {
            byte3 = -1;
        }
        double d = (double) ai2[byte1] / (double) ai2[j];
        double d1 = (double) ai2[byte2] / (double) ai2[j];
        int ai3[] = { 0, 0, 0 };
        int k = 0;
        for (int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double) (ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double) ai[byte1] + (double) k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double) ai[byte2] + (double) k * d1 + 0.5D);
            setBlockAndNotifyAdequately1(worldObj, ai3[0], ai3[1], ai3[2],BlockRedwoodLog.RedwoodType.BARK);
        }
    }

    private void generateLeaves()
    {
        for (int[] leafNode : this.leafNodes) {
            int posX = leafNode[0];
            int posY = leafNode[1];
            int posZ = leafNode[2];
            this.generateLeafNode(posX, posY, posZ);
        }
    }

    private boolean leafNodeNeedsBase(int i)
    {
        return (double) i >= (double) heightLimit * 0.20000000000000001D;
    }

    private void generateLeafNodeBases()
    {
        int i = 0;
        int j = leafNodes.length;
        int ai[] = { basePos[0], basePos[1], basePos[2] };
        for (; i < j; i++)
        {
            int ai1[] = leafNodes[i];
            int ai2[] = { ai1[0], ai1[1], ai1[2] };
            ai[1] = ai1[3];
            int k = ai[1] - basePos[1];
            if (leafNodeNeedsBase(k))
            {
                placeBlockLine(ai, ai2); //,log.withProperty(BlockRedwoodLog.TYPE,BlockRedwoodLog.RedwoodType.BARK).getBlock());
            }
        }
    }

    private int checkBlockLine(BlockPos pos1, BlockPos pos2){
        int ai[] = {pos1.getX(),pos1.getY(),pos1.getZ()};
        int ai1[] = {pos2.getX(),pos2.getY(),pos2.getZ()};
        return checkBlockLine(ai,ai1);
    }

    private int checkBlockLine(int ai[], int ai1[])
    {
        int ai2[] = { 0, 0, 0 };
        byte byte0 = 0;
        int i = 0;
        for (; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if (Math.abs(ai2[byte0]) > Math.abs(ai2[i]))
            {
                i = byte0;
            }
        }

        if (ai2[i] == 0)
        {
            return -1;
        }
        byte byte1 = otherCoordPairs[i];
        byte byte2 = otherCoordPairs[i + 3];
        byte byte3;
        if (ai2[i] > 0)
        {
            byte3 = 1;
        }
        else
        {
            byte3 = -1;
        }
        double d = (double) ai2[byte1] / (double) ai2[i];
        double d1 = (double) ai2[byte2] / (double) ai2[i];
        int ai3[] = { 0, 0, 0 };
        int j = 0;
        int k = ai2[i] + byte3;
        do
        {
            if (j == k)
            {
                break;
            }
            ai3[i] = ai[i] + j;
            ai3[byte1] = MathHelper.floor_double((double) ai[byte1] + (double) j * d);
            ai3[byte2] = MathHelper.floor_double((double) ai[byte2] + (double) j * d1);
            Block l = worldObj.getBlockState(new BlockPos(ai3[0], ai3[1], ai3[2])).getBlock();
            if (l != Blocks.AIR && l != Blocks.LEAVES)
            {
                break;
            }
            j += byte3;
        } while (true);
        if (j == k)
        {
            return -1;
        }
        else
        {
            return Math.abs(j);
        }
    }

    @SuppressWarnings("unused")
    public boolean validTreeLocation (BlockPos pos,World worldIn)
    {
        worldObj = worldIn;
        //int ai[] = { basePos[0], basePos[1], basePos[2] };
        //int ai1[] = { basePos[0], (basePos[1] + heightLimit) - 1, basePos[2] };
        int checkPos = pos.getY();
        BlockPos pos1;
        do {
            pos1 = new BlockPos(pos.getX(),checkPos-1,pos.getZ());
            checkPos--;
            Block x = worldIn.getBlockState(pos1).getBlock();
        } while(worldIn.getBlockState(pos1).getBlock() == Blocks.AIR);
        Block i = worldIn.getBlockState(pos1).getBlock();
        if (i != Blocks.DIRT && i != Blocks.GRASS)
        {
            return false;
        }
        int j = checkBlockLine(pos,pos1);
        if (j == -1)
        {
            return true;
        }
        if (j < 6)
        {
            return false;
        }
        else
        {
            heightLimit = j;
            return true;
        }
    }

    @SuppressWarnings("unused")
    public void func_517_a (double d, double d1, double d2)
    {
        heightLimitLimit = (int) (d * 12D);
        if (d > 0.5D)
        {
            leafDistanceLimit = 5;
        }
        field_873_j = d1;
        field_872_k = d2;
    }



}
