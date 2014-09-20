package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class SaguaroGen extends WorldGenerator
{
    boolean useHeight;

    public SaguaroGen(Block saguaro, int metadata, boolean notify)
    {
        super(true);
        useHeight = notify;
    }

    @Override
    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int yPos = findGround(world, x, y, z, useHeight);
        Block currentID = world.getBlock(x, yPos, z);
        if (!world.isAirBlock(x, yPos, z))
        {
            if (currentID == NContent.saguaro)
            {
                Block block = world.getBlock(x, yPos - 1, z);
                if (block == null || !block.canSustainPlant(world, x, yPos - 1, z, ForgeDirection.UP, (IPlantable) NContent.saguaro))
                    return false;
            }
            else
                return false;
        }
        else
        {
            Block block = world.getBlock(x, yPos - 1, z);
            if (block == null || !block.canSustainPlant(world, x, yPos - 1, z, ForgeDirection.UP, (IPlantable) NContent.saguaro))
            {
                return false;
            }
        }

        if (random.nextInt(20) == 0)
            generateCactusTree(world, random, x, yPos, z);
        else
            generateSmallCactus(world, random, x, yPos, z);

        return true;
    }

    void generateCactusTree (World world, Random random, int x, int y, int z)
    {
        for (int i = 0; i < 6; i++)
        {
            genBlock(world, x, y + i, z);
        }

        genBlock(world, x + 1, y + 2, z);
        genBlock(world, x - 1, y + 2, z);
        genBlock(world, x, y + 2, z + 1);
        genBlock(world, x, y + 2, z - 1);

        for (int height = 0; height < 2; height++)
        {
            genBlock(world, x + 2, y + height + 2, z);
            genBlock(world, x - 2, y + height + 2, z);
            genBlock(world, x, y + height + 2, z + 2);
            genBlock(world, x, y + height + 2, z - 2);

            genBlock(world, x + 3, y + height + 3, z);
            genBlock(world, x - 3, y + height + 3, z);
            genBlock(world, x, y + height + 3, z + 3);
            genBlock(world, x, y + height + 3, z - 3);

            genBlock(world, x + 1, y + height + 5, z);
            genBlock(world, x - 1, y + height + 5, z);
            genBlock(world, x, y + height + 5, z + 1);
            genBlock(world, x, y + height + 5, z - 1);
        }
    }

    void generateSmallCactus (World world, Random random, int x, int y, int z)
    {

        int height = random.nextInt(4) + 3;
        for (int iter = 0; iter < height; iter++)
        {
            genBlock(world, x, y + iter, z);
        }

        int branchY = height >= 5 ? 2 : 1;
        int size;
        y = y + branchY;
        if (random.nextBoolean())
        {
            size = random.nextInt(height - branchY) + branchY - random.nextInt(3);
            for (int branch = 0; branch < size; branch++)
            {
                genBlock(world, x + 1, y + branch, z);
            }

        }
        if (random.nextBoolean())
        {
            size = random.nextInt(height - branchY) + branchY - random.nextInt(3);
            for (int branch = 0; branch < size; branch++)
            {
                genBlock(world, x - 1, y + branch, z);
            }
        }

        if (random.nextBoolean())
        {
            size = random.nextInt(height - branchY) + branchY - random.nextInt(3);
            for (int branch = 0; branch < size; branch++)
            {
                genBlock(world, x, y + branch, z + 1);
            }
        }

        if (random.nextBoolean())
        {
            size = random.nextInt(height - branchY) + branchY - random.nextInt(3);
            for (int branch = 0; branch < size; branch++)
            {
                genBlock(world, x, y + branch, z - 1);
            }
        }
    }

    void genBlock (World world, int x, int y, int z)
    {
        if (!world.getBlock(x, y, z).isOpaqueCube())
            world.setBlock(x, y, z, NContent.saguaro);
    }

    int findGround (World world, int x, int y, int z, boolean useHeight)
    {
        if (useHeight)
            return y;

        boolean foundGround = false;
        int height = PHNatura.seaLevel + 64;
        do
        {
            height--;
            Block underID = world.getBlock(x, height, z);
            if (underID == Blocks.sand || underID == Blocks.dirt || underID == Blocks.grass || height < PHNatura.seaLevel)
                foundGround = true;
        } while (!foundGround);
        return height + 1;
    }

}
