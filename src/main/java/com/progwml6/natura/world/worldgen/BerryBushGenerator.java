package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.berry.nether.NetherBerryBushGenerator;
import com.progwml6.natura.world.worldgen.berry.overworld.OverworldBerryBushGenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BerryBushGenerator implements IWorldGenerator
{
    public static BerryBushGenerator INSTANCE = new BerryBushGenerator();

    //@formatter:off
    OverworldBerryBushGenerator raspberryBushGen;
    OverworldBerryBushGenerator blueberryBushGen;
    OverworldBerryBushGenerator blackberryBushGen;
    OverworldBerryBushGenerator maloberryBushGen;

    NetherBerryBushGenerator blightberryBushGen;
    NetherBerryBushGenerator duskberryBushGen;
    NetherBerryBushGenerator skyberryBushGen;
    NetherBerryBushGenerator stingberryBushGen;
    //@formatter:on

    public BerryBushGenerator()
    {
        this.raspberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.raspberrySpawnRange, NaturaOverworld.overworldBerryBushRaspberry.getDefaultState());
        this.blueberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.blueberrySpawnRange, NaturaOverworld.overworldBerryBushBlueberry.getDefaultState());
        this.blackberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.blackberrySpawnRange, NaturaOverworld.overworldBerryBushBlackberry.getDefaultState());
        this.maloberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.maloberrySpawnRange, NaturaOverworld.overworldBerryBushMaloberry.getDefaultState());

        this.blightberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushBlightberry.getDefaultState());
        this.duskberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushDuskberry.getDefaultState());
        this.skyberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushSkyberry.getDefaultState());
        this.stingberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushStingberry.getDefaultState());
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        this.generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (Config.generateRaspberries && random.nextInt(Config.raspberrySpawnRarity) == 0 && this.goodClimate(biome, 0.6f, 2.0f, 0.2f, 0.93f))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.raspberrySpawnRange) + Config.seaLevel;
            zSpawn = zPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.raspberryBushGen.generateBush(random, world, position);
        }

        if (Config.generateBlueberries && random.nextInt(Config.blueberrySpawnRarity) == 0 && this.goodClimate(biome, 0.3f, 0.81f, 0.3f, 0.8f))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.blueberrySpawnRange) + Config.seaLevel;
            zSpawn = zPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.blueberryBushGen.generateBush(random, world, position);
        }

        if (Config.generateBlackberries && random.nextInt(Config.blackberrySpawnRarity) == 0 && this.goodClimate(biome, 0.5f, 5.0f, 0.6f, 3.0f))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.blackberrySpawnRange) + Config.seaLevel;
            zSpawn = zPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.blackberryBushGen.generateBush(random, world, position);
        }

        if (Config.generateBlackberries && random.nextInt(Config.blackberrySpawnRarity / 3) == 0 && BiomeDictionary.isBiomeOfType(biome, Type.SWAMP))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.blackberrySpawnRange) + Config.seaLevel;
            zSpawn = zPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.blackberryBushGen.generateBush(random, world, position);
        }

        if (Config.generateMaloberries && random.nextInt(Config.maloberrySpawnRarity) == 0 && this.goodClimate(biome, 0.0f, 0.3f, 0.0f, 5.0f))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.maloberrySpawnRange) + Config.seaLevel;
            zSpawn = zPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.maloberryBushGen.generateBush(random, world, position);
        }
    }

    public void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos position;

        if (world.provider.doesWaterVaporize())
        {
            if (Config.generateBlightberries && random.nextInt(Config.blightberrySpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.blightberrySpawnRange) + 16;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.blightberryBushGen.generateBush(random, world, position);
            }

            if (Config.generateDuskberries && random.nextInt(Config.duskberrySpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.duskberrySpawnRange) + 16;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.duskberryBushGen.generateBush(random, world, position);
            }

            if (Config.generateSkyberries && random.nextInt(Config.skyberrySpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.skyberrySpawnRange) + 16;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.skyberryBushGen.generateBush(random, world, position);
            }

            if (Config.generateStingberries && random.nextInt(Config.stingberrySpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.stingberrySpawnRange) + 16;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.stingberryBushGen.generateBush(random, world, position);
            }
        }
    }

    public boolean goodClimate(Biome biome, float minTemp, float maxTemp, float minRain, float maxRain)
    {
        float temp = biome.getTemperature();
        float rain = biome.getRainfall();
        if (minTemp <= temp && temp <= maxTemp && minRain <= rain && rain <= maxRain)
        {
            return true;
        }

        return false;
    }
}
