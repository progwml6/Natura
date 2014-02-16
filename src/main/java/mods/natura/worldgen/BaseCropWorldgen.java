package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

/* Adds crops to the overworld
 * Current crops: Barley, Berry bushes
 */

public class BaseCropWorldgen implements IWorldGenerator
{

    public BaseCropWorldgen()
    {
        raspgen = new BerryBushGen(0, PHNatura.seaLevel + PHNatura.raspSpawnRange);
        bluegen = new BerryBushGen(1, PHNatura.seaLevel + PHNatura.blueSpawnRange);
        blackgen = new BerryBushGen(2, PHNatura.seaLevel + PHNatura.blackSpawnRange);
        malogen = new BerryBushGen(3, PHNatura.seaLevel + PHNatura.geoSpawnRange);

        blightgen = new NetherBerryBushGen(NContent.netherBerryBush, 0);
        duskgen = new NetherBerryBushGen(NContent.netherBerryBush, 1);
        skygen = new NetherBerryBushGen(NContent.netherBerryBush, 2);
        stinggen = new NetherBerryBushGen(NContent.netherBerryBush, 3);
    }

    @Override
    public void generate (Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        final int xChunk = chunkX * 16 + 8, zChunk = chunkZ * 16 + 8;
        int xCh = chunkX * 16 + random.nextInt(16);
        int yCh = random.nextInt(128);
        int zCh = chunkZ * 16 + random.nextInt(16);

        BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(chunkX * 16, chunkZ * 16);

        //Barley
        if (PHNatura.generateBarley && random.nextInt(5) == 0 && goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
        {
            generateBarley(world, random, xCh, yCh + PHNatura.seaLevel, zCh);
            generateBarley(world, random, xCh, yCh + PHNatura.seaLevel, zCh);
        }

        //Cotton
        if (PHNatura.generateCotton && random.nextInt(12) == 0 && goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
        {
            generateCotton(world, random, xCh, yCh + PHNatura.seaLevel, zCh);
            generateCotton(world, random, xCh, yCh + PHNatura.seaLevel, zCh);
        }

        //Bluebells
        if (PHNatura.generateBluebells && random.nextInt(12) == 0)
        {
            generateBluebells(world, random, xCh, yCh + PHNatura.seaLevel, zCh);
        }

        //Berry bushes
        if (PHNatura.generateRaspberries && random.nextInt(PHNatura.raspSpawnRarity) == 0 && goodClimate(biome, 0.6f, 2.0f, 0.2f, 0.93f))
        {
            xCh = xChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.raspSpawnRange) + PHNatura.seaLevel;
            zCh = zChunk + random.nextInt(16);
            raspgen.generate(world, random, xCh, yCh, zCh);
        }

        if (PHNatura.generateBlueberries && random.nextInt(PHNatura.blueSpawnRarity) == 0 && goodClimate(biome, 0.3f, 0.81f, 0.3f, 0.8f))
        {
            xCh = xChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.blueSpawnRange) + PHNatura.seaLevel;
            zCh = zChunk + random.nextInt(16);
            bluegen.generate(world, random, xCh, yCh, zCh);
        }

        if (PHNatura.generateBlackberries && random.nextInt(PHNatura.blackSpawnRarity) == 0 && goodClimate(biome, 0.5f, 5.0f, 0.6f, 3.0f))
        {
            xCh = xChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.blackSpawnRange) + PHNatura.seaLevel;
            zCh = zChunk + random.nextInt(16);
            blackgen.generate(world, random, xCh, yCh, zCh);
        }

        if (PHNatura.generateBlackberries && random.nextInt(PHNatura.blackSpawnRarity / 3) == 0 && biome == BiomeGenBase.swampland)
        {
            xCh = xChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.blackSpawnRange) + PHNatura.seaLevel;
            zCh = zChunk + random.nextInt(16);
            blackgen.generate(world, random, xCh, yCh, zCh);
        }

        if (PHNatura.generateMaloberries && random.nextInt(PHNatura.geoSpawnRarity) == 0 && goodClimate(biome, 0.0f, 0.3f, 0.0f, 5.0f))
        {
            xCh = xChunk + random.nextInt(16);
            yCh = random.nextInt(PHNatura.geoSpawnRange) + PHNatura.seaLevel;
            zCh = zChunk + random.nextInt(16);
            malogen.generate(world, random, xCh, yCh, zCh);
        }

        if (world.provider.isHellWorld)
        {
            if (PHNatura.generateBlightberries && random.nextInt(PHNatura.blightSpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.blightSpawnRange) + 16;
                zCh = zChunk + random.nextInt(16);
                blightgen.generate(world, random, xCh, yCh, zCh);
            }

            if (PHNatura.generateDuskberries && random.nextInt(PHNatura.duskSpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.duskSpawnRange) + 16;
                zCh = zChunk + random.nextInt(16);
                duskgen.generate(world, random, xCh, yCh, zCh);
            }

            if (PHNatura.generateSkyberries && random.nextInt(PHNatura.skySpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.skySpawnRange) + 16;
                zCh = zChunk + random.nextInt(16);
                skygen.generate(world, random, xCh, yCh, zCh);
            }

            if (PHNatura.generateStingberries && random.nextInt(PHNatura.stingSpawnRarity) == 0)
            {
                xCh = xChunk + random.nextInt(16);
                yCh = random.nextInt(PHNatura.stingSpawnRange) + 16;
                zCh = zChunk + random.nextInt(16);
                stinggen.generate(world, random, xCh, yCh, zCh);
            }
        }
    }

    public boolean generateBarley (World world, Random random, int x, int y, int z)
    {
        for (int tries = 0; tries < 64; tries++)
        {
            int i1 = (x + random.nextInt(8)) - random.nextInt(8);
            int j1 = (y + random.nextInt(4)) - random.nextInt(4);
            int k1 = (z + random.nextInt(8)) - random.nextInt(8);
            if (world.isAirBlock(i1, j1, k1) && Blocks.yellow_flower.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, NContent.crops, 3, 2);
            }
        }

        return true;
    }

    public boolean generateCotton (World world, Random random, int x, int y, int z)
    {
        for (int tries = 0; tries < 64; tries++)
        {
            int i1 = (x + random.nextInt(8)) - random.nextInt(8);
            int j1 = (y + random.nextInt(4)) - random.nextInt(4);
            int k1 = (z + random.nextInt(8)) - random.nextInt(8);
            if (world.isAirBlock(i1, j1, k1) && Blocks.yellow_flower.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, NContent.crops, 8, 2);
            }
        }

        return true;
    }

    public boolean generateBluebells (World world, Random random, int x, int y, int z)
    {
        for (int tries = 0; tries < 40; tries++)
        {
            int i1 = (x + random.nextInt(8)) - random.nextInt(8);
            int j1 = (y + random.nextInt(8)) - random.nextInt(8);
            int k1 = (z + random.nextInt(8)) - random.nextInt(8);
            if (world.isAirBlock(i1, j1, k1) && Blocks.yellow_flower.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, NContent.bluebells, 0, 2);
            }
        }

        return true;
    }

    public boolean goodClimate (BiomeGenBase biome, float minTemp, float maxTemp, float minRain, float maxRain)
    {
        float temp = biome.temperature;
        float rain = biome.rainfall;
        if (minTemp <= temp && temp <= maxTemp && minRain <= rain && rain <= maxRain)
            return true;

        return false;
    }

    BerryBushGen raspgen;
    BerryBushGen bluegen;
    BerryBushGen blackgen;
    BerryBushGen malogen;

    NetherBerryBushGen blightgen;
    NetherBerryBushGen duskgen;
    NetherBerryBushGen skygen;
    NetherBerryBushGen stinggen;

    FlowerGen bluebells;
    FlowerGen lily;
    FlowerGen tulip;
    FlowerGen pansy;
}
