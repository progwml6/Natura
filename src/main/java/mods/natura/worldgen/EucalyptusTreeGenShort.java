package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class EucalyptusTreeGenShort extends WorldGenerator
{
    private int mdWood;
    private int mdLeaves;

    public EucalyptusTreeGenShort(int metadata, int leavesMetadata)
    {
        mdWood = metadata;
        mdLeaves = leavesMetadata;
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int height = findGround(world, x, y, z);
		return generateRandomTree(world, random, x, height, z);
    }

    int findGround (World world, int x, int y, int z)
    {
        int l = 0;
        Block i1 = world.getBlock(x, y - 1, z);
        if (!world.getBlock(x, y, z).func_149730_j() && (i1 == Blocks.grass || i1 == Blocks.dirt))
        {
            return y;
        }
        int k1 = 96;
        do
        {
            if (k1 < 32)
            {
                break;
            }
            Block j1 = world.getBlock(x, k1, z);
            if ((j1 == Blocks.grass || j1 == Blocks.dirt) && !world.getBlock(x, k1 + 1, z).func_149730_j())
            {
                l = k1 + 1;
                break;
            }
            k1--;
        } while (k1 > 0);
        return l;
    }

    public boolean generateRandomTree (World world, Random random, int posX, int posY, int posZ)
    {
        int height = random.nextInt(3) + 6; //Height
        boolean flag = true;
        if (posY < 1 || posY + height + 1 > 256)
        {
            return false;
        }
        for (int i1 = posY; i1 <= posY + 1 + height; i1++)
        {
            byte byte0 = 1;
            if (i1 == posY)
            {
                byte0 = 0;
            }
            if (i1 >= (posY + 1 + height) - 2)
            {
                byte0 = 2;
            }
            label0: for (int l1 = posX - byte0; l1 <= posX + byte0 && flag; l1++)
            {
                int j2 = posZ - byte0;
                do
                {
                    if (j2 > posZ + byte0 || !flag)
                    {
                        continue label0;
                    }
                    if (i1 >= 0 && i1 < 256)
                    {
                        Block k2 = world.getBlock(l1, i1, j2);
                        if (k2 != Blocks.air && k2 != NContent.floraLeaves)
                        {
                            flag = false;
                            continue label0;
                        }
                    }
                    else
                    {
                        flag = false;
                        continue label0;
                    }
                    j2++;
                } while (true);
            }
        }

        if (!flag)
        {
            return false;
        }
        Block j1 = world.getBlock(posX, posY - 1, posZ);
        if (j1 != Blocks.grass && j1 != Blocks.dirt || posY >= 256 - height - 1)
        {
            return false;
        }
        world.setBlock(posX, posY - 1, posZ, Blocks.dirt);
        world.setBlock(posX, posY, posZ, Blocks.air);
        Block test = world.getBlock(posX, posY, posZ);
        boolean test1;
        if (test == Blocks.air)
        {
            test1 = world.setBlock(posX, posY, posZ, NContent.tree, mdWood, 3);
        }
        for (int k1 = 0; k1 < height; k1++)
        {
            Block i2 = world.getBlock(posX, posY + k1, posZ);
            if (i2 == Blocks.air || i2 == NContent.floraLeaves || i2 == NContent.floraSapling)
            {
                this.setBlockAndNotifyAdequately(world, posX, posY + k1, posZ, NContent.tree, mdWood);
            }
        }

        genBranch(world, random, posX, posY, posZ, height, 1);
        genBranch(world, random, posX, posY, posZ, height, 2);
        genBranch(world, random, posX, posY, posZ, height, 3);
        genBranch(world, random, posX, posY, posZ, height, 4);
        genStraightBranch(world, random, posX, posY, posZ, height, 1);
        genStraightBranch(world, random, posX, posY, posZ, height, 2);
        genStraightBranch(world, random, posX, posY, posZ, height, 3);
        genStraightBranch(world, random, posX, posY, posZ, height, 4);
        generateNode(world, random, posX, posY + height, posZ);
        return true;
    }

    private void genBranch (World world, Random random, int x, int y, int z, int height, int direction)
    {
        int posX = x;
        int posY = y + height - 3;
        int posZ = z;
        byte byte0 = 0;
        byte byte1 = 0;
        switch (direction)
        {
        case 1:
            byte0 = 1;
            byte1 = 1;
            break;

        case 2:
            byte0 = -1;
            byte1 = 1;
            break;

        case 3:
            byte0 = 1;
            byte1 = -1;
            break;

        case 4:
            byte0 = -1;
            byte1 = -1;
            break;
        }
        int heightShift = random.nextInt(6);
        for (int bIter = 4; bIter > 0; bIter--)
        {
            if (heightShift % 3 != 0)
            {
                posX += byte0;
            }
            if (heightShift % 3 != 1)
            {
                posZ += byte1;
            }
            int branch = heightShift % 3;
            posY += branch;
            if (branch == 2)
                this.setBlockAndNotifyAdequately(world, posX, posY - 1, posZ, NContent.tree, mdWood);
            this.setBlockAndNotifyAdequately(world, posX, posY, posZ, NContent.tree, mdWood);
            if (bIter == 1)
                generateNode(world, random, posX, posY, posZ);
            heightShift = random.nextInt(6);
        }
    }

    private void genStraightBranch (World world, Random random, int x, int y, int z, int height, int direction)
    {
        int posX = x;
        int posY = y + height - 3;
        int posZ = z;
        byte xShift = 0;
        byte zShift = 0;
        switch (direction)
        {
        case 1:
            xShift = 1;
            zShift = 0;
            break;

        case 2:
            xShift = 0;
            zShift = 1;
            break;

        case 3:
            xShift = -1;
            zShift = 0;
            break;

        case 4:
            xShift = 0;
            zShift = -1;
            break;
        }
        int heightShift = random.nextInt(6);
        for (int j2 = 4; j2 > 0; j2--)
        {
            if (xShift == 0)
            {
                posX = (posX + random.nextInt(3)) - 1;
                posZ += zShift;
            }
            if (zShift == 0)
            {
                posX += xShift;
                posZ = (posZ + random.nextInt(3)) - 1;
            }
            int branch = heightShift % 3;
            posY += branch;
            if (branch == 2)
                this.setBlockAndNotifyAdequately(world, posX, posY - 1, posZ, NContent.tree, mdWood);
            this.setBlockAndNotifyAdequately(world, posX, posY, posZ, NContent.tree, mdWood);
            if (j2 == 1)
                generateNode(world, random, posX, posY, posZ);
            heightShift = random.nextInt(6);
        }
    }

    public boolean generateNode (World world, Random random, int x, int y, int z)
    {
        this.setBlockAndNotifyAdequately(world, x, y, z, NContent.tree, mdWood);
        for (int xIter = x - 2; xIter <= x + 2; xIter++)
        {
            for (int zIter = z - 1; zIter <= z + 1; zIter++)
            {
                Block bID = world.getBlock(xIter, y, zIter);
                if (bID != NContent.floraLeaves && !bID.func_149730_j())
                {
                    this.setBlockAndNotifyAdequately(world, xIter, y, zIter, NContent.floraLeaves, mdLeaves);
                }
            }
        }

        for (int xIter = x - 1; xIter <= x + 1; xIter++)
        {
            for (int zIter = z - 2; zIter <= z + 2; zIter++)
            {
                Block bID = world.getBlock(xIter, y, zIter);
                if (bID != NContent.floraLeaves && !bID.func_149730_j())
                {
                    this.setBlockAndNotifyAdequately(world, xIter, y, zIter, NContent.floraLeaves, mdLeaves);
                }
            }
        }

        for (int xIter = x - 1; xIter <= x + 1; xIter++)
        {
            for (int zIter = z - 1; zIter <= z + 1; zIter++)
            {
                Block bID = world.getBlock(xIter, y + 1, zIter);
                if (bID != NContent.floraLeaves && !bID.func_149730_j())
                {
                    this.setBlockAndNotifyAdequately(world, xIter, y + 1, zIter, NContent.floraLeaves, mdLeaves);
                }
            }
        }

        return true;
    }
}