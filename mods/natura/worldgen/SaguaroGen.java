package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NaturaContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class SaguaroGen extends WorldGenerator
{
	boolean useHeight;

	public SaguaroGen(int blockID, int metadata, boolean notify)
	{
		super(true);
		useHeight = notify;
	}

	public boolean generate (World world, Random random, int x, int y, int z)
	{
		int yPos = findGround(world, x, y, z, useHeight);
		int currentID = world.getBlockId(x, yPos, z);
		System.out.println("id "+yPos);
		if (currentID != 0)
		{
			System.out.println("Tick");
			if (currentID == NaturaContent.saguaro.blockID)
			{
				Block block = Block.blocksList[world.getBlockId(x, yPos - 1, z)];
				if (block == null || !block.canSustainPlant(world, x, yPos - 1, z, ForgeDirection.UP, (IPlantable) NaturaContent.saguaro))
					return false;
			}
			else
				return false;
		}
		else
		{
			Block block = Block.blocksList[world.getBlockId(x, yPos - 1, z)];
			if (block == null || !block.canSustainPlant(world, x, yPos-1, z, ForgeDirection.UP, (IPlantable) NaturaContent.saguaro))
			{

				System.out.println("Tock "+block);
				return false;
			}
		}

		System.out.println("Tack");
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
		if (!Block.opaqueCubeLookup[world.getBlockId(x, y, z)])
			world.setBlock(x, y, z, NaturaContent.saguaro.blockID);
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
			int underID = world.getBlockId(x, height, z);
			if (underID == Block.dirt.blockID || underID == Block.grass.blockID || height < PHNatura.seaLevel)
				foundGround = true;
		} while (!foundGround);
		return height + 1;
	}

}
