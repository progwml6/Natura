package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NetherMinableGenerator implements IWorldGenerator
{
    public static NetherMinableGenerator INSTANCE = new NetherMinableGenerator();

    //@formatter:off
    WorldGenMinable tainedSoilGen;
    WorldGenMinable heatSandGen;
    //@formatter:on

    public NetherMinableGenerator()
    {
        this.tainedSoilGen = new WorldGenMinable(NaturaNether.netherTaintedSoil.getDefaultState(), Config.tainedSoilClusterSize, BlockMatcher.forBlock(Blocks.NETHERRACK));
        this.heatSandGen = new WorldGenMinable(NaturaNether.netherHeatSand.getDefaultState(), Config.heatSandClusterSize, BlockMatcher.forBlock(Blocks.SOUL_SAND));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateNether(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateNether(random, chunkX, chunkZ, world);

        world.getChunkFromChunkCoords(chunkX, chunkZ).markDirty();
    }

    public void generateNether(Random random, int chunkX, int chunkZ, World world)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16;
        int yPos = world.getSeaLevel() / 2 + 1 - 5;
        int zPos = chunkZ * 16;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (biome == null)
        {
            return;
        }

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
            if (BiomeDictionary.hasType(biome, Type.NETHER))
            {
                if (Config.generateTaintedSoil)
                {
                    for (int i = 0; i < Config.tainedSoilClusterCount; i++)
                    {
                        xSpawn = xPos + random.nextInt(16);
                        ySpawn = yPos + random.nextInt(10);
                        zSpawn = zPos + random.nextInt(16);

                        this.tainedSoilGen.generate(world, random, new BlockPos(xSpawn, ySpawn, zSpawn));
                    }
                }

                if (Config.generateHeatSand)
                {
                    for (int i = 0; i < Config.heatSandClusterCount; i++)
                    {
                        xSpawn = xPos + random.nextInt(16);
                        ySpawn = yPos + random.nextInt(10);
                        zSpawn = zPos + random.nextInt(16);

                        this.heatSandGen.generate(world, random, new BlockPos(xSpawn, ySpawn, zSpawn));
                    }
                }
            }
        }
    }

    public boolean shouldGenerateInDimension(int dimension)
    {
        for (int dimensionId : Config.netherWorldGenBlacklist)
        {
            if (dimension == dimensionId)
            {
                return false;
            }
        }

        return true;
    }
}
