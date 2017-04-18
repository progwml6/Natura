package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.berry.overworld.OverworldBerryBushGenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OverworldBerryBushesGenerator implements IWorldGenerator
{
    public static OverworldBerryBushesGenerator INSTANCE = new OverworldBerryBushesGenerator();

    //@formatter:off
    OverworldBerryBushGenerator raspberryBushGen;
    OverworldBerryBushGenerator blueberryBushGen;
    OverworldBerryBushGenerator blackberryBushGen;
    OverworldBerryBushGenerator maloberryBushGen;
    //@formatter:on

    public OverworldBerryBushesGenerator()
    {
        this.raspberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.raspberrySpawnRange, NaturaOverworld.overworldBerryBushRaspberry.getDefaultState());
        this.blueberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.blueberrySpawnRange, NaturaOverworld.overworldBerryBushBlueberry.getDefaultState());
        this.blackberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.blackberrySpawnRange, NaturaOverworld.overworldBerryBushBlackberry.getDefaultState());
        this.maloberryBushGen = new OverworldBerryBushGenerator(Config.seaLevel + Config.maloberrySpawnRange, NaturaOverworld.overworldBerryBushMaloberry.getDefaultState());
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
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
    }

    public boolean shouldGenerateInDimension(int dimension)
    {
        for (int dimensionId : Config.overworldWorldGenBlacklist)
        {
            if (dimension == dimensionId)
            {
                return false;
            }
        }

        return true;
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
