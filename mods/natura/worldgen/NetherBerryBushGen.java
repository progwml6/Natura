package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class NetherBerryBushGen extends WorldGenerator
{
    private Block blockGen;
    private int metadata;

    public NetherBerryBushGen(Block block, int meta)
    {
        this.blockGen = block;
        metadata = meta;
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int height = findGround(world, x, y, z);
        if (height != -1)
        {
            //System.out.println("True at "+x+", "+height+", "+z);
            int type = random.nextInt(10);
            if (type == 9)
                generateLargeNode(world, random, x, height, z);
            else if (type >= 7)
                generateShrub(world, random, x, height, z);
            else if (type >= 3)
                generateSmallNode(world, random, x, height, z);
            else
                generateTinyNode(world, random, x, height, z);

            return true;
        }
        return false;
    }

    int findGround (World world, int x, int y, int z)
    {
        int returnHeight = -1;
        int blockID = world.getBlockId(x, y - 1, z);
        if (blockID != 0 && !Block.opaqueCubeLookup[world.getBlockId(x, y, z)]
                && (blockID == Block.netherrack.blockID || Block.blocksList[blockID].canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) blockGen)))
        {
            //System.out.println("Returning "+y);
            return y;
        }

        int height = y;
        do
        {
            int bID = world.getBlockId(x, height, z);
            if (bID != 0)
            {
                if (bID == Block.netherrack.blockID || Block.blocksList[bID].canSustainPlant(world, x, height, z, ForgeDirection.UP, (IPlantable) blockGen))
                {
                    if (!Block.opaqueCubeLookup[world.getBlockId(x, height + 1, z)])
                    {
                        returnHeight = height + 1;
                    }
                    break;
                }
            }
            height--;
        } while (height > 0);
        return returnHeight;
    }

    public void generateLargeNode (World world, Random random, int x, int y, int z)
    {
        for (int iterX = x - 2; iterX <= x + 2; iterX++)
        {
            for (int iterZ = z - 1; iterZ <= z + 1; iterZ++)
            {
                for (int iterY = y - 1; iterY <= y; iterY++)
                {
                    generateBerryBlock(world, iterX, iterY, iterZ, random);
                }
            }
        }

        for (int iterX = x - 1; iterX <= x + 1; iterX++)
        {
            for (int iterZ = z - 2; iterZ <= z - 2; iterZ++)
            {
                for (int iterY = y - 1; iterY <= y; iterY++)
                {
                    generateBerryBlock(world, iterX, iterY, iterZ, random);
                }
            }
        }

        for (int iterX = x - 1; iterX <= x + 1; iterX++)
        {
            for (int iterZ = z + 2; iterZ <= z + 2; iterZ++)
            {
                for (int iterY = y - 1; iterY <= y; iterY++)
                {
                    generateBerryBlock(world, iterX, iterY, iterZ, random);
                }
            }
        }

        for (int iterX = x - 1; iterX <= x + 1; iterX++)
        {
            for (int iterZ = z - 1; iterZ <= z + 1; iterZ++)
            {
                int yPos = y + 1;
                generateBerryBlock(world, iterX, yPos, iterZ, random);
                yPos = y - 2;
                generateBerryBlock(world, iterX, yPos, iterZ, random);
            }
        }
    }

    public void generateShrub (World world, Random random, int x, int y, int z)
    {
        int l;

        Block block = null;
        do
        {
            block = Block.blocksList[world.getBlockId(x, y, z)];
            if (block != null && !block.isLeaves(world, x, y, z))
            {
                break;
            }
            y--;
        } while (y > 0);

        int i1 = world.getBlockId(x, y, z);

        if (i1 == Block.dirt.blockID || i1 == Block.grass.blockID)
        {
            ++y;

            for (int yPos = y; yPos <= y + 2; ++yPos)
            {
                int k1 = yPos - y;
                int l1 = 2 - k1;

                for (int xPos = x - l1; xPos <= x + l1; ++xPos)
                {
                    int j2 = xPos - x;

                    for (int zPos = z - l1; zPos <= z + l1; ++zPos)
                    {
                        int l2 = zPos - z;

                        block = Block.blocksList[world.getBlockId(xPos, yPos, zPos)];

                        if ((Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0) && (block == null || block.canBeReplacedByLeaves(world, xPos, yPos, zPos)))
                        {
                            generateBerryBlock(world, xPos, yPos, zPos, random);
                        }
                    }
                }
            }
        }
    }

    public void generateSmallNode (World world, Random random, int x, int y, int z)
    {
        generateBerryBlock(world, x, y, z, random);
        if (random.nextBoolean())
            generateBerryBush(world, x + 1, y, z, random);
        if (random.nextBoolean())
            generateBerryBush(world, x - 1, y, z, random);
        if (random.nextBoolean())
            generateBerryBush(world, x, y, z + 1, random);
        if (random.nextBoolean())
            generateBerryBush(world, x, y, z - 1, random);
    }

    public void generateTinyNode (World world, Random random, int x, int y, int z)
    {
        generateBerryBush(world, x, y, z, random);
    }

    void generateBerryBlock (World world, int x, int y, int z, Random random)
    {
        if (!Block.opaqueCubeLookup[world.getBlockId(x, y, z)])
        {
            int metaOffset = random.nextInt(5) == 0 ? 1 : 0;
            setBlockAndMetadata(world, x, y, z, NContent.netherBerryBush.blockID, metadata + 8 + metaOffset * 4);
        }
    }

    void generateBerryBush (World world, int x, int y, int z, Random random)
    {
        if (!Block.opaqueCubeLookup[world.getBlockId(x, y, z)])
        {
            int metaOffset = random.nextInt(4);
            setBlockAndMetadata(world, x, y, z, NContent.netherBerryBush.blockID, metadata + metaOffset * 4);
        }
    }
}
