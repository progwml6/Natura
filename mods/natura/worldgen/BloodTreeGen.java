package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NaturaContent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BloodTreeGen extends WorldGenerator
{
    private int mdWood;
    private int mdLeaves;

    public BloodTreeGen(int wood, int leaves)
    {
        mdWood = wood;
        mdLeaves = leaves;
    }

    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int height = findGround(world, x, y, z);
        return generateRandomTree(world, random, x, height, z);
    }

    int findGround(World world, int x, int y, int z)
    {
        int l = 0;
        int bID = world.getBlockId(x, y - 1, z);
        if (!Block.opaqueCubeLookup[world.getBlockId(x, y, z)] && (bID == Block.netherrack.blockID || bID == Block.slowSand.blockID))
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
            int j1 = world.getBlockId(x, k1, z);
            if ((j1 == Block.netherrack.blockID || j1 == Block.slowSand.blockID) && !Block.opaqueCubeLookup[world.getBlockId(x, k1 + 1, z)])
            {
                l = k1 + 1;
                break;
            }
            k1--;
        }
        while (true);
        return l;
    }

    public boolean generateRandomTree(World world, Random random, int x, int y, int z)
    {
        int treeHeight = random.nextInt(5) + 8;
        boolean flag = true;
        if (y < 1 || y + treeHeight + 1 > 256)
        {
            return false;
        }
        for (int i1 = y; i1 <= y + 1 + treeHeight; i1++)
        {
            byte byte0 = 1;
            if (i1 == y)
            {
                byte0 = 0;
            }
            if (i1 >= (y + 1 + treeHeight) - 2)
            {
                byte0 = 2;
            }
            label0:
            for (int l1 = x - byte0; l1 <= x + byte0 && flag; l1++)
            {
                int j2 = z - byte0;
                do
                {
                    if (j2 > z + byte0 || !flag)
                    {
                        continue label0;
                    }
                    if (i1 >= 0 && i1 < 256)
                    {
                        int k2 = world.getBlockId(l1, i1, j2);
                        if (k2 != 0 && k2 != NaturaContent.floraLeavesNoColor.blockID)
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
                }
                while (true);
            }
        }

        if (!flag)
        {
            return false;
        }
        int j1 = world.getBlockId(x, y - 1, z);
        if (j1 != Block.netherrack.blockID && j1 != Block.slowSand.blockID || y >= 256 - treeHeight - 1)
        {
            return false;
        }
        world.setBlock(x, y - 1, z, Block.slowSand.blockID);
        world.setBlock(x + 1, y - 1, z, Block.slowSand.blockID);
        world.setBlock(x, y - 1, z + 1, Block.slowSand.blockID);
        world.setBlock(x + 1, y - 1, z + 1, Block.slowSand.blockID);
        for (int k1 = 0; k1 < treeHeight; k1++)
        {
            int i2 = world.getBlockId(x, y + k1, z);
            if (i2 == 0 || i2 == NaturaContent.floraLeaves.blockID)
            {
                setBlockAndMetadata(world, x, y + k1, z, NaturaContent.bloodwood.blockID, 0);
                setBlockAndMetadata(world, x + 1, y + k1, z, NaturaContent.bloodwood.blockID, 1);
                setBlockAndMetadata(world, x, y + k1, z + 1, NaturaContent.bloodwood.blockID, 2);
                setBlockAndMetadata(world, x + 1, y + k1, z + 1, NaturaContent.bloodwood.blockID, 3);
            }
        }

        genBranch(world, random, x, y, z, treeHeight, 1);
        genBranch(world, random, x + 1, y, z, treeHeight, 2);
        genBranch(world, random, x, y, z + 1, treeHeight, 3);
        genBranch(world, random, x + 1, y, z + 1, treeHeight, 4);
        genStraightBranch(world, random, x, y, z, treeHeight, 1);
        genStraightBranch(world, random, x + 1, y, z, treeHeight, 2);
        genStraightBranch(world, random, x, y, z + 1, treeHeight, 3);
        genStraightBranch(world, random, x + 1, y, z + 1, treeHeight, 4);
        return true;
    }

    private void genBranch(World world, Random random, int i, int j, int k, int l, int i1)
    {
        int j1 = i;
        int k1 = j + l;
        int l1 = k;
        byte byte0 = 0;
        byte byte1 = 0;
        switch (i1)
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
        int i2 = random.nextInt(15);
        for (int j2 = 4; j2 > 0; j2--)
        {
            if (i2 % 3 != 0)
            {
                j1 += byte0;
            }
            if (i2 % 3 != 1)
            {
                l1 += byte1;
            }
            k1 += i2 % 3 - 1;
            generateNode(world, random, j1, k1, l1);
            i2 = random.nextInt(15);
        }
    }

    private void genStraightBranch(World world, Random random, int i, int j, int k, int l, int i1)
    {
        int j1 = i;
        int k1 = j + l;
        int l1 = k;
        byte byte0 = 0;
        byte byte1 = 0;
        switch (i1)
        {
            case 1:
                byte0 = 1;
                byte1 = 0;
                break;

            case 2:
                byte0 = 0;
                byte1 = 1;
                break;

            case 3:
                byte0 = -1;
                byte1 = 0;
                break;

            case 4:
                byte0 = 0;
                byte1 = -1;
                break;
        }
        int i2 = random.nextInt(5);
        for (int j2 = 4; j2 > 0; j2--)
        {
            if (byte0 == 0)
            {
                j1 = (j1 + random.nextInt(3)) - 1;
                l1 += byte1;
            }
            if (byte1 == 0)
            {
                j1 += byte0;
                l1 = (l1 + random.nextInt(3)) - 1;
            }
            k1 += i2 % 3 - 1;
            generateNode(world, random, j1, k1, l1);
            i2 = random.nextInt(5);
        }
    }

    public boolean generateNode(World world, Random random, int i, int j, int k)
    {
        setBlockAndMetadata(world, i, j, k, NaturaContent.bloodwood.blockID, 15);
        for (int l = i - 1; l <= i + 1; l++)
        {
            for (int k1 = k - 1; k1 <= k + 1; k1++)
            {
                for (int j2 = j - 1; j2 <= j + 1; j2++)
                {
                    int i3 = world.getBlockId(l, j2, k1);
                    if (i3 != NaturaContent.floraLeaves.blockID && !Block.opaqueCubeLookup[i3])
                    {
                        setBlockAndMetadata(world, l, j2, k1, NaturaContent.floraLeavesNoColor.blockID, mdLeaves);
                    }
                }
            }
        }

        for (int i1 = i - 1; i1 <= i + 1; i1++)
        {
            for (int l1 = k - 2; l1 <= k + 2; l1++)
            {
                int k2 = world.getBlockId(i1, j, l1);
                if (k2 != NaturaContent.floraLeaves.blockID && !Block.opaqueCubeLookup[k2])
                {
                    setBlockAndMetadata(world, i1, j, l1, NaturaContent.floraLeavesNoColor.blockID, mdLeaves);
                }
            }
        }

        for (int j1 = i - 2; j1 <= i + 2; j1++)
        {
            for (int i2 = k - 1; i2 <= k + 1; i2++)
            {
                int l2 = world.getBlockId(j1, j+1, i2);
                if (l2 != NaturaContent.floraLeaves.blockID && !Block.opaqueCubeLookup[l2])
                {
                    setBlockAndMetadata(world, j1, j, i2, NaturaContent.floraLeavesNoColor.blockID, mdLeaves);
                }
            }
        }

        return true;
    }
}
