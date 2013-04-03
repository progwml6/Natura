package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NaturaContent;
import mods.natura.common.PHNatura;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class BaseTreeWorldgen implements IWorldGenerator
{
	public BaseTreeWorldgen()
	{
		genRedwood = new RedwoodTreeGen(false, NaturaContent.redwood.blockID, 0);
		genBlood = new BloodTreeGen(3, 2);
		bush = new BushTreeGen(false, 2, 3, 2);
		pinkSakura = new SakuraTreeGen(false, 1, 0);
		whiteSakura = new WhiteTreeGen(false, 2, 1);
		eucalyptusShort = new EucalyptusTreeGenShort(0, 1);
		saguaro = new SaguaroGen(NaturaContent.saguaro.blockID, 0, false);
	}

	RedwoodTreeGen genRedwood;
	BloodTreeGen genBlood;
	BushTreeGen bush;
	SakuraTreeGen pinkSakura;
	WhiteTreeGen whiteSakura;
	EucalyptusTreeGenShort eucalyptusShort;
	
	SaguaroGen saguaro;
	
	FlowerGen bluebells;
	FlowerGen lily;
	FlowerGen tulip;
	FlowerGen pansy;

	@Override
	public void generate (Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int xSpawn, ySpawn, zSpawn;
		int xPos = chunkX * 16, zPos = chunkZ * 16;
		String biomeName = world.getWorldChunkManager().getBiomeGenAt(xPos, zPos).biomeName;
		
		if (biomeName == "Forest" || biomeName == "AutumnWoods" || biomeName == "BirchForest" || biomeName == "PineForest" || biomeName == "Rainforest" 
				|| biomeName == "TemperateRainforest" || biomeName == "Woodlands")
		{
			if (PHNatura.generateSakura && random.nextInt((int) (PHNatura.sakuraSpawnRarity * 5)) == 0)
			{
				for (int iter = 0; iter < 3; iter++)
				{
					xSpawn = xPos + random.nextInt(16);
					ySpawn = random.nextInt(PHNatura.sakuraSpawnRange) + PHNatura.seaLevel;
					zSpawn = zPos + random.nextInt(16);
					pinkSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
				}
			}
			if (PHNatura.generateSmallEucalyptus && random.nextInt(PHNatura.eucalyptusShortSpawnRarity) == 0)
			{
				xSpawn = xPos + random.nextInt(16);
				ySpawn = random.nextInt(PHNatura.eucalyptusShortSpawnRange) + PHNatura.seaLevel;
				zSpawn = zPos + random.nextInt(16);
				eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
		}
		if (biomeName == "Plains" || biomeName == "Meadow")
		{
			if (PHNatura.generateRedwood && random.nextInt((int) PHNatura.redwoodSpawnRarity) == 0)
			{
				xSpawn = xPos + random.nextInt(16);
				zSpawn = zPos + random.nextInt(16);
				genRedwood.generate(world, random, xSpawn, PHNatura.seaLevel + 16, zSpawn);
			}
			if (PHNatura.generateSmallEucalyptus && random.nextInt((int) (PHNatura.eucalyptusShortSpawnRarity * 1.5)) == 0)
			{
				xSpawn = xPos + random.nextInt(16);
				ySpawn = random.nextInt(32) + PHNatura.seaLevel;
				zSpawn = zPos + random.nextInt(16);
				eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
		}
		if (biomeName == "Extreme Hills" || biomeName == "Extreme Hills Edge" || biomeName == "ForestedHills" || biomeName == "GreenHills")
		{
			if (PHNatura.generateBush && random.nextInt(PHNatura.bushSpawnRarity) == 0)
			{
				xSpawn = xPos + random.nextInt(16) + 8;
				ySpawn = random.nextInt(PHNatura.bushSpawnRange) + PHNatura.seaLevel;
				zSpawn = zPos + random.nextInt(16) + 8;
				bush.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
			if (PHNatura.generateSmallEucalyptus && random.nextInt(PHNatura.eucalyptusShortSpawnRarity) < 10)
			{
				xSpawn = xPos + random.nextInt(16) + 8;
				ySpawn = random.nextInt(PHNatura.eucalyptusShortSpawnRange) + PHNatura.seaLevel;
				zSpawn = zPos + random.nextInt(16) + 8;
				eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
		}
		if (biomeName == "River")
		{
			if (PHNatura.generateSakura && random.nextInt((int) (PHNatura.sakuraSpawnRarity)) == 0)
			{
				for (int iter = 0; iter < 3; iter++)
				{
					xSpawn = xPos + random.nextInt(16);
					ySpawn = random.nextInt(PHNatura.sakuraSpawnRange) + PHNatura.seaLevel;
					zSpawn = zPos + random.nextInt(16);
					pinkSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
				}
			}
		}
		
		if (biomeName == "Desert" || biomeName == "DesertHills")
		{
			if (PHNatura.generateSaguaro && random.nextInt((int) (PHNatura.saguaroSpawnRarity)) == 0)
			{
				xSpawn = xPos + random.nextInt(16);
				ySpawn = random.nextInt(PHNatura.seaLevel)+16;
				zSpawn = zPos + random.nextInt(16);
				saguaro.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
		}
		
		if (biomeName.equals("Hell"))
		{
			if (PHNatura.generateBloodwood && random.nextInt(PHNatura.bloodSpawnRarity) == 0)
			{
				xSpawn = xPos + random.nextInt(16);
				ySpawn = random.nextInt(PHNatura.bloodSpawnRange) + PHNatura.bloodSpawnHeight;
				zSpawn = zPos + random.nextInt(16);
				genBlood.generate(world, random, xSpawn, ySpawn, zSpawn);
			}
			if (PHNatura.generateGhost && random.nextInt(PHNatura.ghostSpawnRarity) == 0)
			{
				for (int iter = 0; iter < 3; iter++)
				{
					ySpawn = random.nextInt(PHNatura.ghostSpawnRange) + PHNatura.ghostSpawnHeight;
					xSpawn = xPos + random.nextInt(16);
					zSpawn = zPos + random.nextInt(16);
					whiteSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
				}
			}
		}
	}
}
