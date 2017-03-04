package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.vine.ThornvinesGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class VineGenerator implements IWorldGenerator
{
    public static VineGenerator INSTANCE = new VineGenerator();

    //@formatter:off
    ThornvinesGenerator thornvinesGen;
    //@formatter:on

    public VineGenerator()
    {
        this.thornvinesGen = new ThornvinesGenerator(NaturaNether.netherThornVines.getDefaultState());
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

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (biome == null)
        {
            return;
        }

        if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER))
        {
            if (Config.generateThornvines && random.nextInt(Config.thornSpawnRarity) == 0)
            {
                ySpawn = 108;

                for (int i = 0; i < 20; i++)
                {
                    IBlockState vine = this.thornvinesGen.getRandomizedVine(random);
                    xSpawn = xPos + random.nextInt(16);
                    zSpawn = zPos + random.nextInt(16);
                    int size = random.nextInt(25) + 1;
                    int height = ySpawn - (random.nextInt(size) + random.nextInt(size) + random.nextInt(size));

                    for (int yHeight = ySpawn; yHeight > height; yHeight--)
                    {
                        position = new BlockPos(xSpawn, yHeight, zSpawn);
                        this.thornvinesGen.generateVines(random, world, position, vine);
                    }
                }
            }
        }
    }
}
