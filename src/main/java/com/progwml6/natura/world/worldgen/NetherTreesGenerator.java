package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.logs.BlockNetherLog2;
import com.progwml6.natura.world.worldgen.trees.nether.BloodwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.DarkwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.FusewoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.GhostwoodTreeGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NetherTreesGenerator implements IWorldGenerator
{
    public static NetherTreesGenerator INSTANCE = new NetherTreesGenerator();

    //@formatter:off
    DarkwoodTreeGenerator darkwoodTreeGen;
    FusewoodTreeGenerator fusewoodTreeGen;
    GhostwoodTreeGenerator ghostwoodTreeGen;
    BloodwoodTreeGenerator bloodwoodTreeGen;
    //@formatter:on

    public NetherTreesGenerator()
    {
        IBlockState netherLog = NaturaNether.netherLog.getDefaultState();
        IBlockState netherLog2 = NaturaNether.netherLog2.getDefaultState();
        IBlockState netherLeaves = NaturaNether.netherLeaves.getDefaultState();
        IBlockState netherLeaves2 = NaturaNether.netherLeaves2.getDefaultState();

        this.darkwoodTreeGen = new DarkwoodTreeGenerator(3, netherLog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.DARKWOOD), netherLeaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD), netherLeaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FLOWERING), netherLeaves2.withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FRUIT));
        this.fusewoodTreeGen = new FusewoodTreeGenerator(3, netherLog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.FUSEWOOD), netherLeaves.withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.FUSEWOOD));
        this.ghostwoodTreeGen = new GhostwoodTreeGenerator(netherLog.withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.GHOSTWOOD), netherLeaves.withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.GHOSTWOOD), true);
        this.bloodwoodTreeGen = new BloodwoodTreeGenerator(netherLog2.withProperty(BlockNetherLog2.META, 15), netherLog2.withProperty(BlockNetherLog2.META, 0), netherLog2.withProperty(BlockNetherLog2.META, 1), netherLog2.withProperty(BlockNetherLog2.META, 2), netherLog2.withProperty(BlockNetherLog2.META, 3), netherLeaves.withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.BLOODWOOD));
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
                if (Config.generateBloodwood && random.nextInt(Config.bloodwoodSpawnRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = 72;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.bloodwoodTreeGen.generateTree(random, world, position);
                }

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
