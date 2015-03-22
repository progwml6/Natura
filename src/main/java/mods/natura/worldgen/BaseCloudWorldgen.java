package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

/* Generates clouds in every dimension
 * Current clouds: Normal, Dark, Sulfur, Ash
 * Overworld: Normal
 * Nether: Sulfur, Ash
 * End: Normal, Dark
 */

public class BaseCloudWorldgen implements IWorldGenerator
{

    public BaseCloudWorldgen()
    {
        smallcloud = new CloudGen(NContent.cloud, 0, 10, false);
        mediumcloud = new CloudGen(NContent.cloud, 0, 20, false);
        largecloud = new CloudGen(NContent.cloud, 0, 30, false);
        hugecloud = new CloudGen(NContent.cloud, 0, 40, false);
        smalldarkcloud = new CloudGen(NContent.cloud, 1, 10, false);
        mediumdarkcloud = new CloudGen(NContent.cloud, 1, 20, false);
        largedarkcloud = new CloudGen(NContent.cloud, 1, 30, false);
        hugedarkcloud = new CloudGen(NContent.cloud, 1, 40, false);
        tinyashcloud = new CloudGen(NContent.cloud, 2, 3, false);
        smallashcloud = new CloudGen(NContent.cloud, 2, 10, false);
        mediumashcloud = new CloudGen(NContent.cloud, 2, 18, false);
        largeashcloud = new CloudGen(NContent.cloud, 2, 27, false);
        hugeashcloud = new CloudGen(NContent.cloud, 2, 37, false);
        tinysulfurcloud = new CloudGen(NContent.cloud, 3, 3, false);
        smallsulfurcloud = new CloudGen(NContent.cloud, 3, 10, false);
        mediumsulfurcloud = new CloudGen(NContent.cloud, 3, 18, false);
        largesulfurcloud = new CloudGen(NContent.cloud, 3, 27, false);
        hugesulfurcloud = new CloudGen(NContent.cloud, 3, 37, false);
    }

    @Override
    public void generate (Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        //Overworld
        int xCh, yCh, zCh;
        int xChunk = chunkX * 16 + 8, zChunk = chunkZ * 16 + 8;
        BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(xChunk + 16, zChunk + 16);

        if (PHNatura.generateOverworldClouds && biome.rainfall > 0.15f && random.nextInt(PHNatura.cloudSpawnRarity) == 0 && world.provider.dimensionId != 1)
        {
            xCh = xChunk + random.nextInt(16);
            zCh = zChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.cloudSpawnRange) + PHNatura.cloudSpawnHeight;
            int size = random.nextInt(12);
            if (size < 5)
            {
                smallcloud.generate(world, random, xCh, yCh, zCh);
            }
            else if (size < 9)
            {
                mediumcloud.generate(world, random, xCh, yCh, zCh);
            }
            else if (size < 11)
            {
                largecloud.generate(world, random, xCh, yCh, zCh);
            }
            else
            {
                hugecloud.generate(world, random, xCh, yCh, zCh);
            }
        }

        //End Generation
        if (PHNatura.generateDarkClouds && biome == BiomeGenBase.sky && world.provider.dimensionId == 1 && random.nextInt(4) == 0)
        {
            xCh = xChunk + random.nextInt(16);
            zCh = zChunk + random.nextInt(16);
            for (int iter = 0; iter < PHNatura.darkCloudSpawnRarity; iter++)
            {
                int height = random.nextInt(PHNatura.darkCloudSpawnRange);
                if (random.nextInt(5) == 0)
                {
                    smalldarkcloud.generate(world, random, xCh, height + PHNatura.darkCloudSpawnHeight, zCh);
                }
                else if (random.nextInt(7) == 0)
                {
                    mediumcloud.generate(world, random, xCh, height + PHNatura.darkCloudSpawnHeight, zCh);
                }
                else if (random.nextInt(9) == 0)
                {
                    largedarkcloud.generate(world, random, xCh, height + PHNatura.darkCloudSpawnHeight, zCh);
                }
                if (random.nextInt(12) == 0)
                {
                    hugedarkcloud.generate(world, random, xCh, height + PHNatura.darkCloudSpawnHeight, zCh);
                }
            }
        }

        //Nether
        if (world.provider.isHellWorld)
        {
            if (PHNatura.generateAshClouds && random.nextInt(PHNatura.ashSpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.ashSpawnRange) + PHNatura.ashSpawnHeight;
                zCh = zChunk + random.nextInt(16);
                int size = random.nextInt(12);
                if (size < 5)
                {
                    tinyashcloud.generate(world, random, xCh, yCh, zCh);
                }
                else if (size < 9)
                {
                    smallashcloud.generate(world, random, xCh, yCh, zCh);
                }
                else if (size < 11)
                {
                    largeashcloud.generate(world, random, xCh, yCh, zCh);
                }
                else
                {
                    hugeashcloud.generate(world, random, xCh, yCh, zCh);
                }
            }

            if (PHNatura.generateSulfurClouds && random.nextInt(PHNatura.sulfurSpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.sulfurSpawnRange) + PHNatura.sulfurSpawnHeight;
                zCh = zChunk + random.nextInt(16);
                int size = random.nextInt(12);
                if (size < 5)
                {
                    tinysulfurcloud.generate(world, random, xCh, yCh, zCh);
                }
                else if (size < 9)
                {
                    smallsulfurcloud.generate(world, random, xCh, yCh, zCh);
                }
                else if (size < 11)
                {
                    largesulfurcloud.generate(world, random, xCh, yCh, zCh);
                }
                else
                {
                    hugesulfurcloud.generate(world, random, xCh, yCh, zCh);
                }
            }
        }
    }

    CloudGen smallcloud;
    CloudGen mediumcloud;
    CloudGen largecloud;
    CloudGen hugecloud;
    CloudGen smalldarkcloud;
    CloudGen mediumdarkcloud;
    CloudGen largedarkcloud;
    CloudGen hugedarkcloud;
    CloudGen tinyashcloud;
    CloudGen smallashcloud;
    CloudGen mediumashcloud;
    CloudGen largeashcloud;
    CloudGen hugeashcloud;
    CloudGen tinysulfurcloud;
    CloudGen smallsulfurcloud;
    CloudGen mediumsulfurcloud;
    CloudGen largesulfurcloud;
    CloudGen hugesulfurcloud;

}
