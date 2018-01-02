package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.shared.block.clouds.BlockCloud;
import com.progwml6.natura.world.worldgen.clouds.CloudsGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CloudGenerator implements IWorldGenerator
{
    public static CloudGenerator INSTANCE = new CloudGenerator();

    //@formatter:off
    CloudsGenerator smallWhiteCloudGen;
    CloudsGenerator mediumWhiteCloudGen;
    CloudsGenerator largeWhiteCloudGen;
    CloudsGenerator hugeWhiteCloudGen;

    CloudsGenerator tinyAshCloudGen;
    CloudsGenerator smallAshCloudGen;
    CloudsGenerator mediumAshCloudGen;
    CloudsGenerator largeAshCloudGen;
    CloudsGenerator hugeAshCloudGen;

    CloudsGenerator tinySulfurCloudGen;
    CloudsGenerator smallSulfurCloudGen;
    CloudsGenerator mediumSulfurCloudGen;
    CloudsGenerator largeSulfurCloudGen;
    CloudsGenerator hugeSulfurCloudGen;

    CloudsGenerator smallDarkCloudGen;
    CloudsGenerator mediumDarkCloudGen;
    CloudsGenerator largeDarkCloudGen;
    CloudsGenerator hugeDarkCloudGen;
    //@formatter:on

    public CloudGenerator()
    {
        if (Config.enableCloudBlocks)
        {
            IBlockState cloud = NaturaCommons.clouds.getDefaultState();

            this.smallWhiteCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.WHITE));
            this.mediumWhiteCloudGen = new CloudsGenerator(20, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.WHITE));
            this.largeWhiteCloudGen = new CloudsGenerator(30, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.WHITE));
            this.hugeWhiteCloudGen = new CloudsGenerator(40, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.WHITE));

            this.smallDarkCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.DARK));
            this.mediumDarkCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.DARK));
            this.largeDarkCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.DARK));
            this.hugeDarkCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.DARK));

            this.tinyAshCloudGen = new CloudsGenerator(3, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.ASH));
            this.smallAshCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.ASH));
            this.mediumAshCloudGen = new CloudsGenerator(18, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.ASH));
            this.largeAshCloudGen = new CloudsGenerator(27, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.ASH));
            this.hugeAshCloudGen = new CloudsGenerator(37, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.ASH));

            this.tinySulfurCloudGen = new CloudsGenerator(3, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.SULFUR));
            this.smallSulfurCloudGen = new CloudsGenerator(10, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.SULFUR));
            this.mediumSulfurCloudGen = new CloudsGenerator(18, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.SULFUR));
            this.largeSulfurCloudGen = new CloudsGenerator(27, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.SULFUR));
            this.hugeSulfurCloudGen = new CloudsGenerator(37, cloud.withProperty(BlockCloud.TYPE, BlockCloud.CloudType.SULFUR));
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        this.generateNether(random, chunkX, chunkZ, world);
        this.generateEnd(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        this.generateNether(random, chunkX, chunkZ, world);
        this.generateEnd(random, chunkX, chunkZ, world);

        world.getChunkFromChunkCoords(chunkX, chunkZ).markDirty();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world)
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

        if (Config.generateOverworldClouds && biome.getRainfall() > 0.15f && random.nextInt(Config.cloudSpawnRarity) == 0 && world.provider.getDimension() != 1 && this.shouldGenerateInDimension(world.provider.getDimension(), Config.cloudBlacklist))
        {
            xSpawn = xPos + random.nextInt(16);
            ySpawn = random.nextInt(Config.cloudSpawnRange) + Config.cloudSpawnHeight;
            zSpawn = xPos + random.nextInt(16);
            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            int size = random.nextInt(12);

            if (size < 5)
            {
                this.smallWhiteCloudGen.generateCloud(random, world, position);
            }
            else if (size < 9)
            {
                this.mediumWhiteCloudGen.generateCloud(random, world, position);
            }
            else if (size < 11)
            {
                this.largeWhiteCloudGen.generateCloud(random, world, position);
            }
            else
            {
                this.hugeWhiteCloudGen.generateCloud(random, world, position);
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

        if (world.provider.doesWaterVaporize())
        {
            if (Config.generateAshClouds && random.nextInt(Config.ashSpawnRarity) == 0 && this.shouldGenerateInDimension(world.provider.getDimension(), Config.ashBlacklist))
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.ashSpawnRange) + Config.ashSpawnHeight;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                int size = random.nextInt(12);

                if (size < 2.5)
                {
                    this.tinyAshCloudGen.generateCloud(random, world, position);
                }
                else if (size < 5)
                {
                    this.smallAshCloudGen.generateCloud(random, world, position);
                }
                else if (size < 9)
                {
                    this.smallAshCloudGen.generateCloud(random, world, position);
                }
                else if (size < 11)
                {
                    this.largeAshCloudGen.generateCloud(random, world, position);
                }
                else
                {
                    this.hugeAshCloudGen.generateCloud(random, world, position);
                }
            }

            if (Config.generateSulfurClouds && random.nextInt(Config.sulfurSpawnRarity) == 0 && this.shouldGenerateInDimension(world.provider.getDimension(), Config.sulfurCloudBlacklist))
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(Config.sulfurSpawnRange) + Config.sulfurSpawnHeight;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                int size = random.nextInt(12);

                if (size < 2.5)
                {
                    this.tinySulfurCloudGen.generateCloud(random, world, position);
                }
                else if (size < 5)
                {
                    this.smallSulfurCloudGen.generateCloud(random, world, position);
                }
                else if (size < 9)
                {
                    this.mediumSulfurCloudGen.generateCloud(random, world, position);
                }
                else if (size < 11)
                {
                    this.largeSulfurCloudGen.generateCloud(random, world, position);
                }
                else
                {
                    this.hugeSulfurCloudGen.generateCloud(random, world, position);
                }
            }
        }
    }

    public void generateEnd(Random random, int chunkX, int chunkZ, World world)
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

        if (Config.generateDarkClouds && biome == Biomes.SKY && world.provider.getDimension() == 1 && random.nextInt(4) == 0 && this.shouldGenerateInDimension(world.provider.getDimension(), Config.darkCloudBlacklist))
        {
            xSpawn = xPos + random.nextInt(16);
            zSpawn = xPos + random.nextInt(16);

            for (int iter = 0; iter < Config.darkCloudSpawnRarity; iter++)
            {
                int height = random.nextInt(Config.darkCloudSpawnRange);
                ySpawn = height + Config.darkCloudSpawnHeight;

                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                if (random.nextInt(5) == 0)
                {
                    this.smallDarkCloudGen.generateCloud(random, world, position);
                }
                else if (random.nextInt(7) == 0)
                {
                    this.mediumDarkCloudGen.generateCloud(random, world, position);
                }
                else if (random.nextInt(9) == 0)
                {
                    this.largeDarkCloudGen.generateCloud(random, world, position);
                }
                else if (random.nextInt(12) == 0)
                {
                    this.hugeDarkCloudGen.generateCloud(random, world, position);
                }
            }
        }
    }

    public boolean shouldGenerateInDimension(int dimension, int[] configSetting)
    {
        for (int dimensionId : configSetting)
        {
            if (dimension == dimensionId)
            {
                return false;
            }
        }

        return true;
    }
}
