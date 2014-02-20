package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BloodTreeLargeGen extends WorldGenerator
{
    private int mdWood;
    private int mdLeaves;

    public BloodTreeLargeGen(int wood, int leaves)
    {
        mdWood = wood;
        mdLeaves = leaves;
    }

    @Override
    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int height = findCeiling(world, x, y, z);
        if (height == -1)
            return false;

        return generateRandomTree(world, random, x, height, z);
    }

    int findCeiling (World world, int x, int y, int z)
    {
        int ret = -1;
        int height = y;
        do
        {
            Block heightID = world.getBlock(x, height, z);
            if ((heightID == Blocks.netherrack || heightID == Blocks.soul_sand || heightID == NContent.taintedSoil) && !world.getBlock(x, height - 1, z).isOpaqueCube())
            {
                ret = height - 1;
                break;
            }
            height++;
        } while (height <= 120);
        return ret;
    }

    public boolean generateRandomTree (World world, Random random, int x, int y, int z)
    {
        int treeHeight = random.nextInt(8) + 8;

        /*boolean valid = true;
        for (int yPos = 0; yPos < treeHeight+2; yPos++)
        {
            int range = 1;
            
            if (yPos == 0)
                range = 0;
            else if (yPos >= treeHeight -2)
                range = 3;
            
            for (int xPos = 0-range; xPos < 2+range; xPos++)
            {
                for (int zPos = 0-range; zPos < 2+range; zPos++)
                {
                    Block block = Block.blocksList[world.getBlockId(x + xPos, y - yPos, z + zPos)];
                    if (block == null || block.isLeaves(world, x + xPos, y - yPos, z + zPos))
                        valid = false;
                }
            }
            if (!valid)
                return false;
        }*/

        for (int heightIter = 0; heightIter < treeHeight; heightIter++)
        {
            Block localID = world.getBlock(x, y - heightIter, z);
            if (localID == Blocks.air || localID == NContent.floraLeaves)
            {
                world.setBlock(x, y - heightIter, z, NContent.bloodwood, 0, 0);
                world.setBlock(x + 1, y - heightIter, z, NContent.bloodwood, 1, 0);
                world.setBlock(x, y - heightIter, z + 1, NContent.bloodwood, 2, 0);
                world.setBlock(x + 1, y - heightIter, z + 1, NContent.bloodwood, 3, 0);
            }
        }

        genBranch(world, random, x, y, z, -treeHeight, 1);
        genBranch(world, random, x + 1, y, z, -treeHeight, 2);
        genBranch(world, random, x, y, z + 1, -treeHeight, 3);
        genBranch(world, random, x + 1, y, z + 1, -treeHeight, 4);
        genStraightBranch(world, random, x, y, z, -treeHeight, 1);
        genStraightBranch(world, random, x + 1, y, z, -treeHeight, 2);
        genStraightBranch(world, random, x, y, z + 1, -treeHeight, 3);
        genStraightBranch(world, random, x + 1, y, z + 1, -treeHeight, 4);
        return true;
    }

    private void genBranch (World world, Random random, int x, int y, int z, int height, int direction)
    {
        int xPos = x;
        int yPos = y + height;
        int zPos = z;
        byte offsetX = 0;
        byte offsetZ = 0;
        switch (direction)
        {
        case 1:
            offsetX = 1;
            offsetZ = 1;
            break;

        case 2:
            offsetX = -1;
            offsetZ = 1;
            break;

        case 3:
            offsetX = 1;
            offsetZ = -1;
            break;

        case 4:
            offsetX = -1;
            offsetZ = -1;
            break;
        }
        int i2 = random.nextInt(15);
        for (int j2 = 4; j2 > 0; j2--)
        {
            if (i2 % 3 != 0)
            {
                xPos += offsetX;
            }
            if (i2 % 3 != 1)
            {
                zPos += offsetZ;
            }
            yPos += i2 % 3 - 1;
            generateNode(world, random, xPos, yPos, zPos);
            i2 = random.nextInt(15);
        }
    }

    private void genStraightBranch (World world, Random random, int x, int y, int z, int height, int direction)
    {
        int xPos = x;
        int yPos = y + height;
        int zPos = z;
        byte offsetX = 0;
        byte offsetZ = 0;
        switch (direction)
        {
        case 1:
            offsetX = 1;
            offsetZ = 0;
            break;

        case 2:
            offsetX = 0;
            offsetZ = 1;
            break;

        case 3:
            offsetX = -1;
            offsetZ = 0;
            break;

        case 4:
            offsetX = 0;
            offsetZ = -1;
            break;
        }
        int i2 = random.nextInt(5);
        for (int j2 = 4; j2 > 0; j2--)
        {
            if (offsetX == 0)
            {
                xPos = (xPos + random.nextInt(3)) - 1;
                zPos += offsetZ;
            }
            if (offsetZ == 0)
            {
                xPos += offsetX;
                zPos = (zPos + random.nextInt(3)) - 1;
            }
            yPos -= i2 % 3 - 1;
            generateNode(world, random, xPos, yPos, zPos);
            i2 = random.nextInt(5);
        }
    }

    public boolean generateNode (World world, Random random, int x, int y, int z)
    {
        world.setBlock(x, y, z, NContent.bloodwood, 15, 0);
        for (int l = x - 1; l <= x + 1; l++)
        {
            for (int k1 = z - 1; k1 <= z + 1; k1++)
            {
                for (int j2 = y - 1; j2 <= y + 1; j2++)
                {
                    Block i3 = world.getBlock(l, j2, k1);
                    if (i3 != NContent.floraLeaves && !i3.isOpaqueCube())
                    {
                        world.setBlock(l, j2, k1, NContent.floraLeavesNoColor, mdLeaves, 0);
                    }
                }
            }
        }

        for (int i1 = x - 1; i1 <= x + 1; i1++)
        {
            for (int l1 = z - 2; l1 <= z + 2; l1++)
            {
                Block k2 = world.getBlock(i1, y, l1);
                if (k2 != NContent.floraLeaves && !k2.isOpaqueCube())
                {
                    world.setBlock(i1, y, l1, NContent.floraLeavesNoColor, mdLeaves, 0);
                }
            }
        }

        for (int j1 = x - 2; j1 <= x + 2; j1++)
        {
            for (int i2 = z - 1; i2 <= z + 1; i2++)
            {
                Block l2 = world.getBlock(j1, y + 1, i2);
                if (l2 != NContent.floraLeaves && !l2.isOpaqueCube())
                {
                    world.setBlock(j1, y, i2, NContent.floraLeavesNoColor, mdLeaves, 0);
                }
            }
        }

        return true;
    }
}
