package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class RedwoodTreeGen extends WorldGenerator
{

    /*public boolean drawCircle (World par1World, int radius, int x, int y, int z)
    {
    	int tempX = x;
    	int tempZ = z;
    	int tempRadius = radius;
    	int aTrig = 0;
    	int bTrig = 0;

    	for (int loopCount = 0; loopCount <= radius; loopCount++)
    	{
    		// draw quadrants
    		aTrig = (radius - loopCount);
    		bTrig = (int) (Math.sqrt(radius * radius - aTrig * aTrig));
    		//if ( par1World.getBlockId(x + aTrig, y, z + bTrig) != 56) // top right
    		//{
    		par1World.setBlock(x + aTrig, y, z + bTrig, 56);
    		for (int i = z + bTrig; i >= z; i--)
    		{
    			par1World.setBlock(x + aTrig, y, i, 56);
    		}
    		//}

    		//if ( par1World.getBlockId(x - aTrig, y, z - bTrig) != 56) // bottom left
    		//{
    		par1World.setBlock(x - aTrig, y, z - bTrig, 56);
    		for (int i = z - bTrig; i <= z; i++)
    		{
    			par1World.setBlock(x - aTrig, y, i, 56);
    		}
    		//}

    		//if ( par1World.getBlockId(x - aTrig, y, z + bTrig) != 56) // top left
    		//{
    		par1World.setBlock(x - aTrig, y, z + bTrig, 56);
    		for (int i = z + bTrig; i >= z; i--)
    		{
    			par1World.setBlock(x - aTrig, y, i, 56);
    		}
    		//}

    		//if ( par1World.getBlockId(x + aTrig, y, z - bTrig) != 56) // bottom right
    		//{
    		par1World.setBlock(x + aTrig, y, z - bTrig, 56);
    		for (int i = z - bTrig; i <= z; i++)
    		{
    			par1World.setBlock(x + aTrig, y, i, 56);
    		}
    		//}

    	}

    	return true;
    }*/

    static final byte otherCoordPairs[] = { 2, 0, 0, 1, 2, 1 };
    Random rand;
    World worldObj;
    int basePos[] = { 0, 0, 0 };
    int heightLimit;
    int height;
    double heightAttenuation;
    double field_875_h;
    double field_874_i;
    double field_873_j;
    double field_872_k;
    int trunkSize;
    int heightLimitLimit;
    int leafDistanceLimit;
    int leafNodes[][];
    Block genWoodID;
    int genWoodMetadata = 0;
    boolean useHeight;

    public RedwoodTreeGen(boolean flag, Block bID)
    {
        super(flag);
        useHeight = flag;
        rand = new Random();
        heightLimit = 0;
        heightAttenuation = 0.61799999999999999D;
        field_875_h = 1.0D;
        field_874_i = 0.38100000000000001D;
        field_873_j = 1.0D;
        field_872_k = 1.0D;
        trunkSize = 1;
        heightLimitLimit = 12;
        leafDistanceLimit = 4;
        genWoodID = bID;
    }

    int findGround (World world, int x, int y, int z)
    {
        boolean foundGround = false;
        int height = PHNatura.seaLevel + 64;
        do
        {
            height--;
            Block underID = world.getBlock(x, height, z);
            if (underID == Blocks.dirt || underID == Blocks.grass || height < PHNatura.seaLevel)
                foundGround = true;
        } while (!foundGround);
        return height;
    }

    public boolean isValidSpawn (World world, int x, int y, int z)
    {
        Block bID = world.getBlock(x, y, z);
        boolean ground = bID == Blocks.dirt || bID == Blocks.grass;
        boolean transparent = !world.getBlock(x, y + 1, z).func_149730_j();
        boolean valid = ground && transparent;
        return ground && transparent;
    }

    public boolean generate (World world, Random random, int x, int yPos, int z)
    {
        int groundPoint = yPos;
        if (!useHeight)
        {
            groundPoint = findGround(world, x, yPos, z);
            if (!isValidSpawn(world, x, groundPoint, z))
                return false;
        }

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
                if (currentHeight < treeHeight * 1 / 10)
                    genRing13(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 10)
                    genRing12(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 3 / 10)
                    genRing11(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 4 / 10)
                    genRing10(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 5 / 10)
                {
                    genRing9(world, random, x, currentHeight + groundPoint, z);
                    growLowBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 6 / 10)
                {
                    genRing8(world, random, x, currentHeight + groundPoint, z);
                    growLowBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 7 / 10)
                {
                    genRing7(world, random, x, currentHeight + groundPoint, z);
                    growMiddleBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 8 / 10)
                {
                    genRing6(world, random, x, currentHeight + groundPoint, z);
                    growMiddleBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 9 / 10)
                {
                    genRing5(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
            }

            growBigRoots(world, random, x, groundPoint - 1, z);
            growTop(world, random, x, height + groundPoint, z);
        }
        else if (treeHeight > 100)
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight * 1 / 8)
                    genRing11(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 8)
                    genRing10(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 3 / 8)
                    genRing9(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 4 / 8)
                    genRing8(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 5 / 8)
                {
                    genRing7(world, random, x, currentHeight + groundPoint, z);
                    growMiddleBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 6 / 8)
                {
                    genRing6(world, random, x, currentHeight + groundPoint, z);
                    growMiddleBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 7 / 8)
                {
                    genRing5(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
            }

            growMediumRoots(world, random, x, groundPoint - 1, z);
            growTop(world, random, x, height + groundPoint, z);
        }

        else
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight * 1 / 6)
                    genRing9(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 2 / 6)
                    genRing8(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 3 / 6)
                    genRing7(world, random, x, currentHeight + groundPoint, z);
                else if (currentHeight < treeHeight * 4 / 6)
                {
                    genRing6(world, random, x, currentHeight + groundPoint, z);
                    growMiddleBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else if (currentHeight < treeHeight * 5 / 6)
                {
                    genRing5(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
                else
                {
                    genRing3(world, random, x, currentHeight + groundPoint, z);
                    growHighBranch(world, random, x, currentHeight + groundPoint, z);
                }
            }
            growSmallRoots(world, random, x, groundPoint - 1, z);
            growTop(world, random, x, height + groundPoint, z);
        }
        return true;
    }

    /*else if (height > 70)
    {
    	if (heightPos >= 70)
    	{
    		for (int j3 = currentHeight + groundPoint; j3 <= (groundPoint + heightPos) - 70; j3++)
    		{
    			genRing11(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 58)
    	{
    		for (int k3 = currentHeight + groundPoint; k3 <= (groundPoint + heightPos) - 58; k3++)
    		{
    			genRing10(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 35)
    	{
    		for (int l3 = currentHeight + groundPoint; l3 <= (groundPoint + heightPos) - 35; l3++)
    		{
    			genRing9(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			growMediumLowerBranch(world, random, x, currentHeight + groundPoint, z);
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 15)
    	{
    		for (int i4 = currentHeight + groundPoint; i4 <= (groundPoint + heightPos) - 15; i4++)
    		{
    			genRing8(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			growMediumUpperBranch(world, random, x, currentHeight + groundPoint, z);
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 0)
    	{
    		for (int j4 = currentHeight + groundPoint; j4 <= (groundPoint + heightPos) - 0; j4++)
    		{
    			genRing7(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			growMediumUpperBranch(world, random, x, currentHeight + groundPoint, z);
    			currentHeight + groundPoint++;
    		}
    	}
    	growMediumRoots(world, random, x, groundPoint - 1, z);
    	growMediumTop(world, random, x, currentHeight + groundPoint, z);
    }
    else
    {
    	if (heightPos >= 50)
    	{
    		for (int k4 = currentHeight + groundPoint; k4 <= (groundPoint + heightPos) - 50; k4++)
    		{
    			genRing9(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 25)
    	{
    		for (int l4 = currentHeight + groundPoint; l4 <= (groundPoint + heightPos) - 25; l4++)
    		{
    			genRing8(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			growSmallLowerBranch(world, random, x, currentHeight + groundPoint, z);
    			currentHeight + groundPoint++;
    		}
    	}
    	if (heightPos >= 0)
    	{
    		for (int i5 = currentHeight + groundPoint; i5 <= (groundPoint + heightPos) - 0; i5++)
    		{
    			genRing7(world, random, x, currentHeight + groundPoint, z);
    			heightPos--;
    			growSmallUpperBranch(world, random, x, currentHeight + groundPoint, z);
    			currentHeight + groundPoint++;
    		}
    	}
    	growSmallRoots(world, random, x, groundPoint - 1, z);
    	growSmallTop(world, random, x, currentHeight + groundPoint, z);
    }*/

    public boolean growTop (World world, Random random, int x, int y, int z)
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

    public boolean growHighBranch (World world, Random random, int x, int y, int z)
    {
        for (int iter = 0; iter < 3; iter++)
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

    public boolean growMiddleBranch (World world, Random random, int x, int y, int z)
    {
        for (int iter = 0; iter < 6; iter++)
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

    public boolean growLowBranch (World world, Random random, int i, int j, int k)
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

    public boolean growSmallRoots (World world, Random random, int i, int j, int k)
    {
        genRing9(world, random, i, j, k);
        smallRoot1(world, random, i, j - 1, k);
        smallRoot1(world, random, i, j - 2, k);
        smallRoot1(world, random, i, j - 3, k);
        smallRoot2(world, random, i, j - 4, k);
        smallRoot2(world, random, i, j - 5, k);
        smallRoot3(world, random, i, j - 6, k);
        smallRoot3(world, random, i, j - 7, k);
        smallRoot3(world, random, i, j - 8, k);
        smallRoot3(world, random, i, j - 9, k);
        smallRoot4(world, random, i, j - 10, k);
        smallRoot4(world, random, i, j - 11, k);
        return true;
    }

    public boolean growMediumRoots (World world, Random random, int i, int j, int k)
    {
        genRing11(world, random, i, j, k);
        mediumRoot1(world, random, i, j - 1, k);
        mediumRoot1(world, random, i, j - 2, k);
        mediumRoot1(world, random, i, j - 3, k);
        mediumRoot2(world, random, i, j - 4, k);
        mediumRoot2(world, random, i, j - 5, k);
        mediumRoot3(world, random, i, j - 6, k);
        mediumRoot3(world, random, i, j - 7, k);
        mediumRoot3(world, random, i, j - 8, k);
        mediumRoot3(world, random, i, j - 9, k);
        mediumRoot4(world, random, i, j - 10, k);
        mediumRoot4(world, random, i, j - 11, k);
        mediumRoot5(world, random, i, j - 12, k);
        mediumRoot5(world, random, i, j - 13, k);
        mediumRoot5(world, random, i, j - 14, k);
        return true;
    }

    public boolean growBigRoots (World world, Random random, int i, int j, int k)
    {
        genRing13(world, random, i, j, k);
        bigRoot1(world, random, i, j - 1, k);
        bigRoot1(world, random, i, j - 2, k);
        bigRoot1(world, random, i, j - 3, k);
        bigRoot2(world, random, i, j - 4, k);
        bigRoot2(world, random, i, j - 5, k);
        bigRoot3(world, random, i, j - 6, k);
        bigRoot3(world, random, i, j - 7, k);
        bigRoot3(world, random, i, j - 8, k);
        bigRoot3(world, random, i, j - 9, k);
        bigRoot4(world, random, i, j - 10, k);
        bigRoot4(world, random, i, j - 11, k);
        bigRoot5(world, random, i, j - 12, k);
        bigRoot5(world, random, i, j - 13, k);
        bigRoot5(world, random, i, j - 14, k);
        bigRoot6(world, random, i, j - 15, k);
        bigRoot6(world, random, i, j - 16, k);
        bigRoot6(world, random, i, j - 17, k);
        bigRoot6(world, random, i, j - 18, k);
        return true;
    }

    public boolean smallRoot1 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean smallRoot2 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean smallRoot3 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean smallRoot4 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean mediumRoot1 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean mediumRoot2 (World world, Random random, int i, int j, int k)
    {
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean mediumRoot3 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean mediumRoot4 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean mediumRoot5 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot1 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 6, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 6, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 6, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 6, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 6, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 6, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 6, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 6, j, k + 2, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot2 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 5, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 6, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 4, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot3 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot4 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot5 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean bigRoot6 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 2);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 2);
        }
        return true;
    }

    public boolean genRing13 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 6, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k + 2, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing12 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 6, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 6, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 5, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 6, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 6, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing11 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 5, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 5, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 5, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 5, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing10 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 2, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing9 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 4, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 4, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 4, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 4, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing8 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 2, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing7 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 3, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 3, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 3, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 3, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing6 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 2, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing5 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 2, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing4 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 2, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 2, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 2, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing3s (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing3 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata + 1);
            setBlockAndNotifyAdequately(world, i, j, k + 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i + 1, j, k + 1, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing2 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i - 1, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i - 1, j, k, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k - 1, genWoodID, genWoodMetadata);
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata);
        }
        return true;
    }

    public boolean genRing1 (World world, Random random, int i, int j, int k)
    {
        if (world.getBlock(i, j, k) != Blocks.bedrock && j > 0)
        {
            setBlockAndNotifyAdequately(world, i, j, k, genWoodID, genWoodMetadata);
        }
        return true;
    }

    void generateLeafNodeList ()
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

    void func_523_a (int i, int j, int k, float f, byte byte0, Block l)
    {
        int i1 = (int) ((double) f + 0.61799999999999999D);
        byte byte1 = otherCoordPairs[byte0];
        byte byte2 = otherCoordPairs[byte0 + 3];
        int ai[] = { i, j, k };
        int ai1[] = { 0, 0, 0 };
        int j1 = -i1;
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
                    Block i2 = worldObj.getBlock(ai1[0], ai1[1], ai1[2]);
                    if (i2 != Blocks.air && i2 != Blocks.leaves)
                    {
                        l1++;
                    }
                    else
                    {
                        setBlockAndNotifyAdequately(worldObj, ai1[0], ai1[1], ai1[2], l, 0);
                        l1++;
                    }
                }
            }
        }
    }

    float func_528_a (int i)
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

    float func_526_b (int i)
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

    void generateLeafNode (int i, int j, int k)
    {
        int l = j;
        for (int i1 = j + leafDistanceLimit; l < i1; l++)
        {
            float f = func_526_b(l - j);
            func_523_a(i, l, k, f, (byte) 1, NContent.floraLeaves);
        }
    }

    void placeBlockLine (int ai[], int ai1[], Block i)
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
            setBlockAndNotifyAdequately(worldObj, ai3[0], ai3[1], ai3[2], i, 0);
        }
    }

    void generateLeaves ()
    {
        for (int iter = 0; iter < this.leafNodes.length; iter++)
        {
            int posX = this.leafNodes[iter][0];
            int posY = this.leafNodes[iter][1];
            int posZ = this.leafNodes[iter][2];
            this.generateLeafNode(posX, posY, posZ);
        }
    }

    boolean leafNodeNeedsBase (int i)
    {
        return (double) i >= (double) heightLimit * 0.20000000000000001D;
    }

    void generateLeafNodeBases ()
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
                placeBlockLine(ai, ai2, NContent.redwood);
            }
        }
    }

    int checkBlockLine (int ai[], int ai1[])
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
            Block l = worldObj.getBlock(ai3[0], ai3[1], ai3[2]);
            if (l != Blocks.air && l != Blocks.leaves)
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

    boolean validTreeLocation ()
    {
        int ai[] = { basePos[0], basePos[1], basePos[2] };
        int ai1[] = { basePos[0], (basePos[1] + heightLimit) - 1, basePos[2] };
        Block i = worldObj.getBlock(basePos[0], basePos[1] - 1, basePos[2]);
        if (i != Blocks.dirt && i != Blocks.glass)
        {
            return false;
        }
        int j = checkBlockLine(ai, ai1);
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