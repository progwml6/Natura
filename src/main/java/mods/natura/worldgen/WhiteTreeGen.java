package mods.natura.worldgen;

import java.util.Random;

import mods.natura.blocks.trees.NLeaves;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WhiteTreeGen extends WorldGenerator
{
    /**
     * Contains three sets of two values that provide complimentary indices for a given 'major' index - 1 and 2 for 0, 0
     * and 2 for 1, and 0 and 1 for 2.
     */
    static final byte[] otherCoordPairs = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };

    /** random seed for GenBigTree */
    Random rand = new Random();

    /** Reference to the World object. */
    //World world;
    int[] basePos = new int[] { 0, 0, 0 };
    int heightLimit = 0;
    int height;
    double heightAttenuation = 0.618D;
    double branchDensity = 1.0D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;

    /**
     * Currently always 1, can be set to 2 in the class constructor to generate a double-sized tree trunk for big trees.
     */
    int trunkSize = 1;

    /**
     * Sets the limit of the random value used to initialize the height limit.
     */
    int heightLimitLimit = 12;

    /**
     * Sets the distance limit for how far away the generator will populate leaves from the base leaf node.
     */
    int leafDistanceLimit = 4;

    /** Contains a list of a points at which to generate groups of leaves. */
    int[][] leafNodes;

    int metaWood;
    int metaLeaves;
    boolean dontFindHeight;

    public WhiteTreeGen(boolean notify, int mdwood, int mdleaves)
    {
        super(notify);
        dontFindHeight = notify;
        metaWood = mdwood;
        metaLeaves = mdleaves;
    }

    /**
     * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
     */
    void generateLeafNodeList (World world)
    {
        this.height = (int) ((double) this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit)
        {
            this.height = this.heightLimit - 1;
        }

        int var1 = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));

        if (var1 < 1)
        {
            var1 = 1;
        }

        int[][] var2 = new int[var1 * this.heightLimit][4];
        int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
        int var4 = 1;
        int var5 = this.basePos[1] + this.height;
        int var6 = var3 - this.basePos[1];
        var2[0][0] = this.basePos[0];
        var2[0][1] = var3;
        var2[0][2] = this.basePos[2];
        var2[0][3] = var5;
        --var3;

        while (var6 >= 0)
        {
            int var7 = 0;
            float var8 = this.layerSize(var6);

            if (var8 < 0.0F)
            {
                --var3;
                --var6;
            }
            else
            {
                for (double var9 = 0.5D; var7 < var1; ++var7)
                {
                    double var11 = this.scaleWidth * (double) var8 * ((double) this.rand.nextFloat() + 0.328D);
                    double var13 = (double) this.rand.nextFloat() * 2.0D * Math.PI;
                    int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + (double) this.basePos[0] + var9);
                    int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + (double) this.basePos[2] + var9);
                    int[] var17 = new int[] { var15, var3, var16 };
                    int[] var18 = new int[] { var15, var3 + this.leafDistanceLimit, var16 };

                    if (this.checkBlockLine(var17, var18, world) == -1)
                    {
                        int[] var19 = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
                        double var20 = Math.sqrt(Math.pow((double) Math.abs(this.basePos[0] - var17[0]), 2.0D) + Math.pow((double) Math.abs(this.basePos[2] - var17[2]), 2.0D));
                        double var22 = var20 * this.branchSlope;

                        if ((double) var17[1] - var22 > (double) var5)
                        {
                            var19[1] = var5;
                        }
                        else
                        {
                            var19[1] = (int) ((double) var17[1] - var22);
                        }

                        if (this.checkBlockLine(var19, var17, world) == -1)
                        {
                            var2[var4][0] = var15;
                            var2[var4][1] = var3;
                            var2[var4][2] = var16;
                            var2[var4][3] = var19[1];
                            ++var4;
                        }
                    }
                }

                --var3;
                --var6;
            }
        }

        this.leafNodes = new int[var4][4];
        System.arraycopy(var2, 0, this.leafNodes, 0, var4);
    }

    void genTreeLayer (int x, int y, int z, float size, byte loc, Block b, World world)
    {
        int var7 = (int) ((double) size + 0.618D);
        byte var8 = otherCoordPairs[loc];
        byte var9 = otherCoordPairs[loc + 3];
        int[] origPos = new int[] { x, y, z };
        int[] leafPos = new int[] { 0, 0, 0 };
        int var12 = -var7;
        int var13 = -var7;

        for (leafPos[loc] = origPos[loc]; var12 <= var7; ++var12)
        {
            leafPos[var8] = origPos[var8] + var12;
            var13 = -var7;

            while (var13 <= var7)
            {
                double var15 = Math.pow((double) Math.abs(var12) + 0.5D, 2.0D) + Math.pow((double) Math.abs(var13) + 0.5D, 2.0D);

                if (var15 > (double) (size * size))
                {
                    ++var13;
                }
                else
                {
                    leafPos[var9] = origPos[var9] + var13;
                    Block block = world.getBlock(leafPos[0], leafPos[1], leafPos[2]);

                    if (block != null && !block.isLeaves(world, leafPos[0], leafPos[1], leafPos[2]))
                    {
                        ++var13;
                    }
                    else
                    {
                        world.setBlock(leafPos[0], leafPos[1], leafPos[2], b, metaLeaves, 0);
                        ++var13;
                    }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float layerSize (int par1)
    {
        if ((double) par1 < (double) ((float) this.heightLimit) * 0.3D)
        {
            return -1.618F;
        }
        else
        {
            float var2 = (float) this.heightLimit / 2.0F;
            float var3 = (float) this.heightLimit / 2.0F - (float) par1;
            float var4;

            if (var3 == 0.0F)
            {
                var4 = var2;
            }
            else if (Math.abs(var3) >= var2)
            {
                var4 = 0.0F;
            }
            else
            {
                var4 = (float) Math.sqrt(Math.pow((double) Math.abs(var2), 2.0D) - Math.pow((double) Math.abs(var3), 2.0D));
            }

            var4 *= 0.5F;
            return var4;
        }
    }

    float leafSize (int par1)
    {
        return par1 >= 0 && par1 < this.leafDistanceLimit ? (par1 != 0 && par1 != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void generateLeafNode (int x, int y, int z, World world)
    {
        int height = y;

        for (int iter = y + this.leafDistanceLimit; height < iter; ++height)
        {
            float var6 = this.leafSize(height - y);
            this.genTreeLayer(x, height, z, var6, (byte) 1, NContent.floraLeavesNoColor, world);
        }
    }

    /**
     * Places a line of the specified block into the world from the first coordinate triplet to the second.
     */
    void placeBlockLine (int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block b, World world)
    {
        int[] var4 = new int[] { 0, 0, 0 };
        byte var5 = 0;
        byte var6;

        for (var6 = 0; var5 < 3; ++var5)
        {
            var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];

            if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
            {
                var6 = var5;
            }
        }

        if (var4[var6] != 0)
        {
            byte var7 = otherCoordPairs[var6];
            byte var8 = otherCoordPairs[var6 + 3];
            byte var9;

            if (var4[var6] > 0)
            {
                var9 = 1;
            }
            else
            {
                var9 = -1;
            }

            double var10 = (double) var4[var7] / (double) var4[var6];
            double var12 = (double) var4[var8] / (double) var4[var6];
            int[] posArray = new int[] { 0, 0, 0 };
            int var15 = 0;

            for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
            {
                posArray[var6] = MathHelper.floor_double((double) (par1ArrayOfInteger[var6] + var15) + 0.5D);
                posArray[var7] = MathHelper.floor_double((double) par1ArrayOfInteger[var7] + (double) var15 * var10 + 0.5D);
                posArray[var8] = MathHelper.floor_double((double) par1ArrayOfInteger[var8] + (double) var15 * var12 + 0.5D);
                int metadata = metaWood;
                int var18 = Math.abs(posArray[0] - par1ArrayOfInteger[0]);
                int var19 = Math.abs(posArray[2] - par1ArrayOfInteger[2]);
                int var20 = Math.max(var18, var19);

                if (var20 > 0)
                {
                    if (var18 == var20)
                    {
                        metadata += 4;
                    }
                    else if (var19 == var20)
                    {
                        metadata += 8;
                    }
                }

                world.setBlock(posArray[0], posArray[1], posArray[2], b, metadata, 0);
            }
        }
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves (World world)
    {
        for (int iter = 0; iter < this.leafNodes.length; iter++)
        {
            int posX = this.leafNodes[iter][0];
            int posY = this.leafNodes[iter][1];
            int posZ = this.leafNodes[iter][2];
            this.generateLeafNode(posX, posY, posZ, world);
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean leafNodeNeedsBase (int par1)
    {
        return (double) par1 >= (double) this.heightLimit * 0.2D;
    }

    /**
     * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
     * field that is always 1 to 2.
     */
    void generateTrunk (World world)
    {
        int var1 = this.basePos[0];
        int var2 = this.basePos[1];
        int var3 = this.basePos[1] + this.height;
        int var4 = this.basePos[2];
        int[] var5 = new int[] { var1, var2, var4 };
        int[] var6 = new int[] { var1, var3, var4 };
        this.placeBlockLine(var5, var6, NContent.tree, world);

        if (this.trunkSize == 2)
        {
            ++var5[0];
            ++var6[0];
            this.placeBlockLine(var5, var6, NContent.tree, world);
            ++var5[2];
            ++var6[2];
            this.placeBlockLine(var5, var6, NContent.tree, world);
            var5[0] += -1;
            var6[0] += -1;
            this.placeBlockLine(var5, var6, NContent.tree, world);
        }
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void generateLeafNodeBases (World world)
    {
        int var1 = 0;
        int var2 = this.leafNodes.length;

        for (int[] var3 = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] }; var1 < var2; ++var1)
        {
            int[] var4 = this.leafNodes[var1];
            int[] var5 = new int[] { var4[0], var4[1], var4[2] };
            var3[1] = var4[3];
            int var6 = var3[1] - this.basePos[1];

            if (this.leafNodeNeedsBase(var6))
            {
                this.placeBlockLine(var3, var5, NContent.tree, world);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int checkBlockLine (int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, World world)
    {
        int[] intArray = new int[] { 0, 0, 0 };
        byte arrayPos = 0;
        byte iter;

        for (iter = 0; arrayPos < 3; ++arrayPos)
        {
            intArray[arrayPos] = par2ArrayOfInteger[arrayPos] - par1ArrayOfInteger[arrayPos];

            if (Math.abs(intArray[arrayPos]) > Math.abs(intArray[iter]))
            {
                iter = arrayPos;
            }
        }

        if (intArray[iter] == 0)
        {
            return -1;
        }
        else
        {
            byte var6 = otherCoordPairs[iter];
            byte var7 = otherCoordPairs[iter + 3];
            byte increase;

            if (intArray[iter] > 0)
            {
                increase = 1;
            }
            else
            {
                increase = -1;
            }

            double var9 = (double) intArray[var6] / (double) intArray[iter];
            double var11 = (double) intArray[var7] / (double) intArray[iter];
            int[] arrayCopy = new int[] { 0, 0, 0 };
            int returnValue = 0;
            int compare;

            for (compare = intArray[iter] + increase; returnValue != compare; returnValue += increase)
            {
                arrayCopy[iter] = par1ArrayOfInteger[iter] + returnValue;
                arrayCopy[var6] = MathHelper.floor_double((double) par1ArrayOfInteger[var6] + (double) returnValue * var9);
                arrayCopy[var7] = MathHelper.floor_double((double) par1ArrayOfInteger[var7] + (double) returnValue * var11);
                Block block = world.getBlock(arrayCopy[0], arrayCopy[1], arrayCopy[2]);

                if (block != null && !block.isLeaves(world, arrayCopy[0], arrayCopy[1], arrayCopy[2]))
                {
                    break;
                }
            }
            return returnValue == compare ? -1 : Math.abs(returnValue);
        }
    }

    /**
     * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
     * limit, is valid.
     */
    boolean validTreeLocation (World world)
    {
        int[] var1 = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
        int[] var2 = new int[] { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
        Block blockID = world.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);

        if (blockID != Blocks.netherrack && blockID != Blocks.soul_sand && blockID != NContent.taintedSoil)
        {
            return false;
        }
        else
        {
            int blockLine = this.checkBlockLine(var1, var2, world);

            if (blockLine == -1)
            {
                return true;
            }
            else if (blockLine < 6)
            {
                return false;
            }
            else
            {
                this.heightLimit = blockLine;
                return true;
            }
        }
    }

    /**
     * Rescales the generator settings, only used in WorldGenBigTree
     */
    public void setScale (double par1, double par3, double par5)
    {
        this.heightLimitLimit = (int) (par1 * 12.0D);

        if (par1 > 0.5D)
        {
            this.leafDistanceLimit = 5;
        }

        this.scaleWidth = par3;
        this.leafDensity = par5;
    }

    int findGround (World world, int x, int y, int z)
    {
        boolean foundGround = false;
        int height = 104;
        do
        {
            height--;
            Block underID = world.getBlock(x, height, z);
            if (underID == Blocks.netherrack || underID == Blocks.soul_sand || underID == NContent.taintedSoil || height < 0)
                foundGround = true;
        } while (!foundGround);
        return height + 1;
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        long var6 = random.nextLong();
        this.rand.setSeed(var6);
        this.basePos[0] = x;
        if (this.dontFindHeight)
            this.basePos[1] = y;
        else
            this.basePos[1] = findGround(world, x, y, z);
        this.basePos[2] = z;

        if (this.heightLimit == 0)
        {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation(world))
        {
            return false;
        }
        else
        {
            this.generateLeafNodeList(world);
            this.generateLeaves(world);
            this.generateTrunk(world);
            this.generateLeafNodeBases(world);
            return true;
        }
    }
}
