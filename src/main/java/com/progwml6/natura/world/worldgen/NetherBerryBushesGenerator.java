package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.berry.nether.NetherBerryBushGenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NetherBerryBushesGenerator implements IWorldGenerator
{
    public static NetherBerryBushesGenerator INSTANCE = new NetherBerryBushesGenerator();

    //@formatter:off
    NetherBerryBushGenerator blightberryBushGen;
    NetherBerryBushGenerator duskberryBushGen;
    NetherBerryBushGenerator skyberryBushGen;
    NetherBerryBushGenerator stingberryBushGen;
    //@formatter:on

    public NetherBerryBushesGenerator()
    {
        this.blightberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushBlightberry.getDefaultState());
        this.duskberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushDuskberry.getDefaultState());
        this.skyberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushSkyberry.getDefaultState());
        this.stingberryBushGen = new NetherBerryBushGenerator(NaturaNether.netherBerryBushStingberry.getDefaultState());
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateNether(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateNether(random, chunkX, chunkZ, world);
        world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
    }

    public void generateNether(Random random, int chunkX, int chunkZ, World world)
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
}
