package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.world.worldgen.trees.nether.DarkwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.FusewoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.GhostwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TreeGenerator implements IWorldGenerator
{
    public static TreeGenerator INSTANCE = new TreeGenerator();

    //@formatter:off
    OverworldTreeGenerator mapleTreeGen;
    OverworldTreeGenerator silverbellTreeGen;
    OverworldTreeGenerator amaranthTreeGen;
    OverworldTreeGenerator tigerTreeGen;

    WillowTreeGenerator willowTreeGen;
    EucalyptusTreeGenerator eucalyptusTreeGen;
    HopseedTreeGenerator hopseedTreeGen;
    SakuraTreeGenerator sakuraTreeGen;

    DarkwoodTreeGenerator darkwoodTreeGen;
    FusewoodTreeGenerator fusewoodTreeGen;
    GhostwoodTreeGenerator ghostwoodTreeGen;

    RedwoodTreeGenerator redwoodTreeGen;
    //@formatter:on

    public TreeGenerator()
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
        this.sakuraTreeGen = new SakuraTreeGenerator(log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), true);

        IBlockState netherlog = NaturaNether.netherLog.getDefaultState();
        IBlockState netherleaves = NaturaNether.netherLeaves.getDefaultState();
        IBlockState netherleaves2 = NaturaNether.netherLeaves2.getDefaultState();

        this.darkwoodTreeGen = new DarkwoodTreeGenerator(3, netherlog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.DARKWOOD), netherleaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD), netherleaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FLOWERING), netherleaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FRUIT));
        this.fusewoodTreeGen = new FusewoodTreeGenerator(3, netherlog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.FUSEWOOD), netherleaves.withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.FUSEWOOD));
        this.ghostwoodTreeGen = new GhostwoodTreeGenerator(netherlog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.GHOSTWOOD), netherleaves.withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.GHOSTWOOD));

        this.redwoodTreeGen = new RedwoodTreeGenerator();
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        this.generateNether(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        this.generateNether(random, chunkX, chunkZ, world);
        world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world)
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

        if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST))
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

        if (BiomeDictionary.isBiomeOfType(biome, Type.PLAINS))
        {
            if (Config.generateRedwood && random.nextInt(Config.redwoodSpawnRarity) == 0){
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.eucalyptusSpawnRange) + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);
                if(redwoodTreeGen.validTreeLocation(position,world)) {
                    this.redwoodTreeGen.generateTree(random,world,position);
                }

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

        if (BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN) && BiomeDictionary.isBiomeOfType(biome, Type.HILLS))
        {
            if (Config.generateHopseed && random.nextInt(Config.hopseedSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16) + 8;
                ySpawn = random.nextInt(Config.hopseedSpawnRange) + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16) + 8;
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.hopseedTreeGen.generateTree(random, world, position);
            }

            if (Config.generateEucalyptus && random.nextInt(Config.eucalyptusSpawnRarity) < 10)
            {
                xSpawn = xPos + random.nextInt(16) + 8;
                ySpawn = random.nextInt(Config.eucalyptusSpawnRange) + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16) + 8;
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.eucalyptusTreeGen.generateTree(random, world, position);
            }
        }

        if (BiomeDictionary.isBiomeOfType(biome, Type.RIVER))
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

        if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE))
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

        if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST))
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

        if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP))
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
            /*if (Config.generateBloodwood && random.nextInt(Config.bloodwoodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = 72;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);
            
                this.bloodwoodTreeGen.generateTree(random, world, position);
            
                this.genBlood.generate(world, random, xSpawn, ySpawn, zSpawn);
            }*/

            if (Config.generateDarkwood && random.nextInt(Config.darkwoodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(64) + 32;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.darkwoodTreeGen.generateTree(random, world, position);
            }

            if (Config.generateFusewood && random.nextInt(Config.fusewoodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(64) + 32;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.fusewoodTreeGen.generateTree(random, world, position);
            }

            if (Config.generateGhostwood && random.nextInt(Config.ghostwoodSpawnRarity) == 0)
            {
                for (int iter = 0; iter < 3; iter++)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(80) + 16;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.ghostwoodTreeGen.generateTree(random, world, position);
                }
            }
        }
    }

}
