package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.logs.BlockRedwoodLog;
import com.progwml6.natura.world.worldgen.saguaro.SaguaroGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.EucalyptusTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.HopseedTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.OverworldTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.RedwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.SakuraTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.WillowTreeGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OverworldTreesGenerator implements IWorldGenerator
{
    public static OverworldTreesGenerator INSTANCE = new OverworldTreesGenerator();

    //@formatter:off
    OverworldTreeGenerator mapleTreeGen;
    OverworldTreeGenerator silverbellTreeGen;
    OverworldTreeGenerator amaranthTreeGen;
    OverworldTreeGenerator tigerTreeGen;

    WillowTreeGenerator willowTreeGen;
    EucalyptusTreeGenerator eucalyptusTreeGen;
    HopseedTreeGenerator hopseedTreeGen;
    SakuraTreeGenerator sakuraTreeGen;

    RedwoodTreeGenerator redwoodTreeGen;

    SaguaroGenerator saguaroGen;
    //@formatter:on

    public OverworldTreesGenerator()
    {
        IBlockState log = NaturaOverworld.overworldLog.getDefaultState();
        IBlockState leaves = NaturaOverworld.overworldLeaves.getDefaultState();

        this.mapleTreeGen = new OverworldTreeGenerator(4, 2, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.MAPLE), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.MAPLE));
        this.silverbellTreeGen = new OverworldTreeGenerator(4, 2, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.SILVERBELL), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.SILVERBELL));
        this.amaranthTreeGen = new OverworldTreeGenerator(9, 8, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.AMARANTH), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.AMARANTH));
        this.tigerTreeGen = new OverworldTreeGenerator(6, 4, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.TIGER), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.TIGER));

        IBlockState log2 = NaturaOverworld.overworldLog2.getDefaultState();
        IBlockState leaves2 = NaturaOverworld.overworldLeaves2.getDefaultState();

        this.willowTreeGen = new WillowTreeGenerator(4, 5, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW));
        this.eucalyptusTreeGen = new EucalyptusTreeGenerator(6, 3, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS));
        this.hopseedTreeGen = new HopseedTreeGenerator(2, 3, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED));
        this.sakuraTreeGen = new SakuraTreeGenerator(log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), true, false);

        IBlockState redwoodLog = NaturaOverworld.redwoodLog.getDefaultState();
        IBlockState redwoodLeaves = NaturaOverworld.redwoodLeaves.getDefaultState();

        this.redwoodTreeGen = new RedwoodTreeGenerator(redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.BARK), redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.HEART), redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.ROOT), redwoodLeaves.withProperty(BlockRedwoodLeaves.TYPE, BlockRedwoodLeaves.RedwoodType.NORMAL), false, false);

        this.saguaroGen = new SaguaroGenerator(NaturaOverworld.saguaro.getDefaultState(), false, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world, false);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world, true);

        world.getChunkFromChunkCoords(chunkX, chunkZ).markDirty();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world, boolean retroGen)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        String biomeName = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider()).getBiomeName();

        if (biomeName == null)
        {
            return;
        }

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (biome == null)
        {
            return;
        }

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
            if (BiomeDictionary.hasType(biome, Type.FOREST))
            {
                if (Config.generateSakura && random.nextInt(Config.sakuraSpawnRarity * 5) == 0)
                {
                    for (int iter = 0; iter < 3; iter++)
                    {
                        xSpawn = xPos + random.nextInt(16);
                        ySpawn = random.nextInt(Config.sakuraSpawnRange) + Config.seaLevel;
                        zSpawn = zPos + random.nextInt(16);
                        position = new BlockPos(xSpawn, ySpawn, zSpawn);

                        this.sakuraTreeGen.generateTree(random, world, position);
                    }
                }

                if (Config.generateEucalyptus && random.nextInt(Config.eucalyptusSpawnRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(Config.eucalyptusSpawnRange) + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.eucalyptusTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.PLAINS))
            {
                if (!retroGen && Config.generateRedwood && random.nextInt(Config.redwoodSpawnRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 16;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.redwoodTreeGen.generateTree(random, world, position);
                }

                if (Config.generateEucalyptus && random.nextInt((int) (Config.eucalyptusSpawnRarity * 1.5)) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(Config.eucalyptusSpawnRange) + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.eucalyptusTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.MOUNTAIN) || BiomeDictionary.hasType(biome, Type.HILLS))
            {
                if (Config.generateHopseed && random.nextInt(Config.hopseedSpawnRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(Config.hopseedSpawnRange) + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.hopseedTreeGen.generateTree(random, world, position);
                }

                if (Config.generateEucalyptus && random.nextInt(Config.eucalyptusSpawnRarity) < 10)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(Config.eucalyptusSpawnRange) + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.eucalyptusTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.RIVER))
            {
                if (Config.generateSakura && random.nextInt(Config.sakuraSpawnRarity) == 0)
                {
                    for (int iter = 0; iter < 3; iter++)
                    {
                        xSpawn = xPos + random.nextInt(16);
                        ySpawn = random.nextInt(Config.sakuraSpawnRange) + Config.seaLevel;
                        zSpawn = zPos + random.nextInt(16);
                        position = new BlockPos(xSpawn, ySpawn, zSpawn);

                        this.sakuraTreeGen.generateTree(random, world, position);
                    }
                }

                if (Config.generateWillow && random.nextInt(Config.willowRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 16;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.willowTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.JUNGLE))
            {
                if (Config.generateAmaranth)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 48;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.amaranthTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.FOREST))
            {
                if (Config.generateMaple && random.nextInt(Config.mapleRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 48;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.mapleTreeGen.generateTree(random, world, position);
                }

                if (Config.generateSilverbell && random.nextInt(Config.silverbellRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 48;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.silverbellTreeGen.generateTree(random, world, position);
                }

                if (Config.generateTiger && random.nextInt(Config.tigerRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.seaLevel + 48;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.tigerTreeGen.generateTree(random, world, position);
                }
            }

            if (BiomeDictionary.hasType(biome, Type.SWAMP))
            {
                if (Config.generateWillow && random.nextInt(Config.willowRarity) == 0)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        xSpawn = xPos + random.nextInt(16);
                        ySpawn = Config.seaLevel + 16;
                        zSpawn = zPos + random.nextInt(16);
                        position = new BlockPos(xSpawn, ySpawn, zSpawn);

                        this.willowTreeGen.generateTree(random, world, position);
                    }
                }
            }

            if (BiomeDictionary.hasType(biome, Type.SANDY))
            {
                if (Config.generateSaguaro && random.nextInt(Config.saguaroSpawnRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(Config.seaLevel) + 16;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.saguaroGen.generateSaguaro(random, world, position);
                }
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

}
