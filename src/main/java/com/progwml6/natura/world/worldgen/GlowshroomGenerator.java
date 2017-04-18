package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.glowshroom.nether.BlueGlowshroomGenerator;
import com.progwml6.natura.world.worldgen.glowshroom.nether.GreenGlowshroomGenerator;
import com.progwml6.natura.world.worldgen.glowshroom.nether.PurpleGlowshroomGenerator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class GlowshroomGenerator implements IWorldGenerator
{
    public static GlowshroomGenerator INSTANCE = new GlowshroomGenerator();

    //@formatter:off
    GreenGlowshroomGenerator greenGlowshroomGen;
    BlueGlowshroomGenerator blueGlowshroomGen;
    PurpleGlowshroomGenerator purpleGlowshroomGen;
    //@formatter:on

    public GlowshroomGenerator()
    {
        this.greenGlowshroomGen = new GreenGlowshroomGenerator(NaturaNether.netherLargeGreenGlowshroom.getDefaultState());
        this.blueGlowshroomGen = new BlueGlowshroomGenerator(NaturaNether.netherLargeBlueGlowshroom.getDefaultState());
        this.purpleGlowshroomGen = new PurpleGlowshroomGenerator(NaturaNether.netherLargePurpleGlowshroom.getDefaultState());
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

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
            if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER))
            {
                if (Config.generateGlowshroomtree && random.nextInt(35) == 0)
                {
                    for (int iter = 0; iter < 5; iter++)
                    {
                        xSpawn = xPos + random.nextInt(24) - 4;
                        zSpawn = zPos + random.nextInt(24) - 4;
                        ySpawn = this.findGround(world, xSpawn, random.nextInt(64) + 32, zSpawn);
                        position = new BlockPos(xSpawn, ySpawn, zSpawn);

                        if (random.nextInt(3) == 0)
                        {
                            this.purpleGlowshroomGen.generateShroom(random, world, position);
                        }
                        else
                        {
                            if (random.nextInt(2) == 0)
                            {
                                this.greenGlowshroomGen.generateShroom(random, world, position);
                            }
                            else
                            {
                                this.blueGlowshroomGen.generateShroom(random, world, position);
                            }
                        }
                    }
                }
            }
        }
    }

    int findGround(World world, int x, int y, int z)
    {
        boolean foundGround = false;
        int height = y;
        do
        {
            height--;
            BlockPos pos = new BlockPos(x, height, z);

            Block block = world.getBlockState(pos).getBlock();

            if (block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND || block == NaturaNether.netherTaintedSoil || height < 0)
            {
                foundGround = true;
            }
        }
        while (!foundGround);

        return height + 1;
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
