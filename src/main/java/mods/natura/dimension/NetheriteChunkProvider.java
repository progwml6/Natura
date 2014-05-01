package mods.natura.dimension;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.FIRE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.GLOWSTONE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.worldgen.FlowerGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class NetheriteChunkProvider implements IChunkProvider
{
    private Random hellRNG;

    /** A NoiseGeneratorOctaves used in generating nether terrain */
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;

    /** Determines whether slowsand or gravel can be generated at a location */
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;

    /**
     * Determines whether something other than nettherack can be generated at a location
     */
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen4;
    public NoiseGeneratorOctaves netherNoiseGen5;

    /** Is the world that the nether is getting generated. */
    private World worldObj;
    private double[] noiseField;
    private double[] secondNoiseField;
    public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();

    /**
     * Holds the noise used to determine whether slowsand can be generated at a location
     */
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];

    /**
     * Holds the noise used to determine whether something other than netherrack can be generated at a location
     */
    private double[] netherrackExclusivityNoise = new double[256];
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    double[] secondNoiseData1;
    double[] secondNoiseData2;
    double[] secondNoiseData3;
    double[] secondNoiseData4;
    double[] secondNoiseData5;

    {
        genNetherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(genNetherBridge, NETHER_BRIDGE);
        netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, NETHER_CAVE);
    }

    public NetheriteChunkProvider(World par1World, long par2)
    {
        this.worldObj = par1World;
        this.hellRNG = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen4 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen5 = new NoiseGeneratorOctaves(this.hellRNG, 16);

        // TODO 1.7 check these casts somehow
        NoiseGenerator[] noiseGens = { netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, netherNoiseGen4, netherNoiseGen5 };
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.hellRNG, noiseGens);
        this.netherNoiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.netherNoiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.netherNoiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.slowsandGravelNoiseGen = (NoiseGeneratorOctaves) noiseGens[3];
        this.netherrackExculsivityNoiseGen = (NoiseGeneratorOctaves) noiseGens[4];
        this.netherNoiseGen4 = (NoiseGeneratorOctaves) noiseGens[5];
        this.netherNoiseGen5 = (NoiseGeneratorOctaves) noiseGens[6];
    }

    /**
     * Generates the shape of the terrain in the nether.
     */
    public void generateNetherTerrain (int chunkX, int chunkZ, Block[] lowerIDs)
    {
        byte noiseInit = 4;
        byte b1 = 32;
        int k = noiseInit + 1;
        byte b2 = 17;
        int l = noiseInit + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * noiseInit, 0, chunkZ * noiseInit, k, b2, l);

        for (int iterX = 0; iterX < noiseInit; ++iterX)
        {
            for (int iterZ = 0; iterZ < noiseInit; ++iterZ)
            {
                for (int iterY = 0; iterY < 16; ++iterY)
                {
                    double noiseOffset = 0.125D;
                    double n1 = this.noiseField[((iterX + 0) * l + iterZ + 0) * b2 + iterY + 0];
                    double n2 = this.noiseField[((iterX + 0) * l + iterZ + 1) * b2 + iterY + 0];
                    double n3 = this.noiseField[((iterX + 1) * l + iterZ + 0) * b2 + iterY + 0];
                    double n4 = this.noiseField[((iterX + 1) * l + iterZ + 1) * b2 + iterY + 0];
                    double n5 = (this.noiseField[((iterX + 0) * l + iterZ + 0) * b2 + iterY + 1] - n1) * noiseOffset;
                    double n6 = (this.noiseField[((iterX + 0) * l + iterZ + 1) * b2 + iterY + 1] - n2) * noiseOffset;
                    double n7 = (this.noiseField[((iterX + 1) * l + iterZ + 0) * b2 + iterY + 1] - n3) * noiseOffset;
                    double n8 = (this.noiseField[((iterX + 1) * l + iterZ + 1) * b2 + iterY + 1] - n4) * noiseOffset;

                    for (int offsetY = 0; offsetY < 8; ++offsetY)
                    {
                        double d9 = 0.25D;
                        double d10 = n1;
                        double d11 = n2;
                        double d12 = (n3 - n1) * d9;
                        double d13 = (n4 - n2) * d9;

                        for (int offsetX = 0; offsetX < 4; ++offsetX)
                        {
                            int layerPos = offsetX + iterX * 4 << 11 | 0 + iterZ * 4 << 7 | iterY * 8 + offsetY;
                            short amountPerLayer = 128;
                            double d14 = 0.25D;
                            double lValue = d10;
                            double lOffset = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                Block blockID = Blocks.air;

                                if (iterY * 8 + offsetY < b1)
                                {
                                    blockID = Blocks.lava;
                                }

                                if (lValue > 0.0D)
                                {
                                    blockID = Blocks.netherrack;
                                }

                                if (lValue > 56.0D)
                                {
                                    blockID = NContent.taintedSoil;
                                }

                                lowerIDs[layerPos] = blockID;
                                layerPos += amountPerLayer;
                                lValue += lOffset;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        n1 += n5;
                        n2 += n6;
                        n3 += n7;
                        n4 += n8;
                    }
                }
            }
        }
    }

    // TODO 1.7 probably wrong. Gotta do something with the Block[]. This may or may not leave giant empty holes in the terain
    public void replaceBlocksForBiome (int par1, int par2, Block[] blocks, Block[] lowerIDs)
    {
        //Lower nether
        byte seaLevel = 64;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, par1 * 16, 109, par2 * 16, 16, 1, 16, d0, 1.0D, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int iterX = 0; iterX < 16; ++iterX)
        {
            for (int iterZ = 0; iterZ < 16; ++iterZ)
            {
                boolean flag = this.slowsandNoise[iterX + iterZ * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[iterX + iterZ * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                int i1 = (int) (this.netherrackExclusivityNoise[iterX + iterZ * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
                int j1 = -1;
                Block b1 = Blocks.netherrack;
                Block b2 = NContent.taintedSoil;

                for (int k1 = 127; k1 >= 0; --k1)
                {
                    int l1 = (iterZ * 16 + iterX) * 128 + k1;

                    if (k1 < 127 - this.hellRNG.nextInt(5) && k1 > 0 + this.hellRNG.nextInt(5))
                    {
                        Block b3 = lowerIDs[l1];

                        if (b3 == null || b3 == Blocks.air)
                        {
                            j1 = -1;
                        }
                        else if (b3 == Blocks.netherrack)
                        {
                            if (j1 == -1)
                            {
                                if (i1 <= 0)
                                {
                                    b1 = null;
                                    b2 = Blocks.netherrack;
                                }
                                else if (k1 >= seaLevel - 4 && k1 <= seaLevel + 1)
                                {
                                    b1 = Blocks.netherrack;
                                    b2 = NContent.taintedSoil;

                                    if (flag1)
                                    {
                                        b1 = Blocks.gravel;
                                    }

                                    if (flag1)
                                    {
                                        b2 = Blocks.netherrack;
                                    }

                                    if (flag)
                                    {
                                        b1 = Blocks.soul_sand;
                                    }

                                    if (flag)
                                    {
                                        b2 = NContent.heatSand;
                                    }
                                }

                                if (k1 < seaLevel && b1 == null || b1 == Blocks.air)
                                {
                                    b1 = Blocks.lava;
                                }

                                j1 = i1;

                                if (k1 >= seaLevel - 1)
                                {
                                    lowerIDs[l1] = b1;
                                }
                                else
                                {
                                    lowerIDs[l1] = b2;
                                }
                            }
                            else if (j1 > 0)
                            {
                                --j1;
                                lowerIDs[l1] = b2;
                            }
                        }
                    }
                    else
                    {
                        lowerIDs[l1] = Blocks.bedrock;
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    @Override
    public Chunk loadChunk (int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk (int chunkX, int chunkZ)
    {
        this.hellRNG.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        Block[] lowerArray = new Block[32768];
        Block[] aBlock = new Block[32768];
        //byte[] upperArray = new byte[32768];
        this.generateNetherTerrain(chunkX, chunkZ, lowerArray);
        this.replaceBlocksForBiome(chunkX, chunkZ, aBlock, lowerArray);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, chunkX, chunkZ, aBlock);
        this.genNetherBridge.func_151539_a(this, this.worldObj, chunkX, chunkZ, aBlock);
        Chunk chunk = new NetheriteChunk(this.worldObj, lowerArray, chunkX, chunkZ);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, chunkX * 16, chunkZ * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte) abiomegenbase[k].biomeID;
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField (double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
        MinecraftForge.EVENT_BUS.post(event);
        event.getResult();
        if (event.getResult() == Result.DENY)
            return event.noisefield;
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.netherNoiseGen4.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen5.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];
        int i2;

        for (i2 = 0; i2 < par6; ++i2)
        {
            adouble1[i2] = Math.cos(i2 * Math.PI * 6.0D / par6) * 2.0D;
            double d2 = i2;

            if (i2 > par6 / 2)
            {
                d2 = par6 - 1 - i2;
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < par5; ++i2)
        {
            for (int j2 = 0; j2 < par7; ++j2)
            {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;

                if (d5 < 0.0D)
                {
                    d5 = -d5;
                }

                d5 = d5 * 3.0D - 3.0D;

                if (d5 < 0.0D)
                {
                    d5 /= 2.0D;

                    if (d5 < -1.0D)
                    {
                        d5 = -1.0D;
                    }

                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                }
                else
                {
                    if (d5 > 1.0D)
                    {
                        d5 = 1.0D;
                    }

                    d5 /= 6.0D;
                }

                d3 += 0.5D;
                d5 = d5 * par6 / 16.0D;
                ++l1;

                for (int k2 = 0; k2 < par6; ++k2)
                {
                    double d6 = 0.0D;
                    double d7 = adouble1[k2];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    double d11;

                    if (k2 > par6 - 4)
                    {
                        d11 = (k2 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if (k2 < d4)
                    {
                        d11 = (d4 - k2) / 4.0D;

                        if (d11 < 0.0D)
                        {
                            d11 = 0.0D;
                        }

                        if (d11 > 1.0D)
                        {
                            d11 = 1.0D;
                        }

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[k1] = d6;
                    ++k1;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
    public boolean chunkExists (int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate (IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockFalling.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        int blockX = par2 * 16;
        int blockZ = par3 * 16;
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        int i1;
        int xPos;
        int yPos;
        int zPos;

        boolean doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, NETHER_LAVA);
        for (i1 = 0; doGen && i1 < 8; ++i1)
        {
            xPos = blockX + this.hellRNG.nextInt(16) + 8;
            yPos = this.hellRNG.nextInt(120) + 4;
            zPos = blockZ + this.hellRNG.nextInt(16) + 8;
            (new WorldGenHellLava(Blocks.lava, false)).generate(this.worldObj, this.hellRNG, xPos, yPos, zPos);
        }

        i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        int i2;

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, FIRE);
        for (xPos = 0; doGen && xPos < i1; ++xPos)
        {
            yPos = blockX + this.hellRNG.nextInt(16) + 8;
            zPos = this.hellRNG.nextInt(120) + 4;
            i2 = blockZ + this.hellRNG.nextInt(16) + 8;
            (new FireGen()).generate(this.worldObj, this.hellRNG, yPos, zPos, i2);
        }

        i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, GLOWSTONE);
        for (xPos = 0; doGen && xPos < i1; ++xPos)
        {
            yPos = blockX + this.hellRNG.nextInt(16) + 8;
            zPos = this.hellRNG.nextInt(120) + 4;
            i2 = blockZ + this.hellRNG.nextInt(16) + 8;
            (new WorldGenGlowStone1()).generate(this.worldObj, this.hellRNG, yPos, zPos, i2);
        }

        for (xPos = 0; doGen && xPos < 10; ++xPos)
        {
            yPos = blockX + this.hellRNG.nextInt(16) + 8;
            zPos = this.hellRNG.nextInt(128);
            i2 = blockZ + this.hellRNG.nextInt(16) + 8;
            (new WorldGenGlowStone2()).generate(this.worldObj, this.hellRNG, yPos, zPos, i2);
        }

        WorldGenMinable worldgenminable = new WorldGenMinable(Blocks.quartz_ore, 13, Blocks.netherrack);
        int j2;

        for (yPos = 0; yPos < 16; ++yPos)
        {
            zPos = blockX + this.hellRNG.nextInt(16);
            i2 = this.hellRNG.nextInt(108) + 10;
            j2 = blockZ + this.hellRNG.nextInt(16);
            worldgenminable.generate(this.worldObj, this.hellRNG, zPos, i2, j2);
        }

        for (yPos = 0; yPos < 16; ++yPos)
        {
            zPos = blockX + this.hellRNG.nextInt(16);
            i2 = this.hellRNG.nextInt(108) + 10;
            j2 = blockZ + this.hellRNG.nextInt(16);
            (new WorldGenHellLava(Blocks.lava, true)).generate(this.worldObj, this.hellRNG, zPos, i2, j2);
        }
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, hellRNG, blockX, blockZ));
        doGen = TerrainGen.decorate(worldObj, hellRNG, blockX, blockZ, SHROOM);

        /*if (doGen && this.hellRNG.nextInt(1) == 0)
        {
            xPos = blockX + this.hellRNG.nextInt(16) + 8;
            yPos = this.hellRNG.nextInt(128);
            zPos = blockZ + this.hellRNG.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.hellRNG, xPos, yPos, zPos);
        }

        if (doGen && this.hellRNG.nextInt(1) == 0)
        {
            xPos = blockX + this.hellRNG.nextInt(16) + 8;
            yPos = this.hellRNG.nextInt(128);
            zPos = blockZ + this.hellRNG.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.hellRNG, xPos, yPos, zPos);
        }*/

        if (doGen && hellRNG.nextInt(7) == 0)
        {
            int l2 = blockX + hellRNG.nextInt(16) + 8;
            int k4 = hellRNG.nextInt(128);
            int j6 = blockZ + hellRNG.nextInt(16) + 8;
            (new FlowerGen(NContent.glowshroom, 0)).generate(worldObj, hellRNG, l2, k4, j6);
        }
        if (doGen && hellRNG.nextInt(8) == 0)
        {
            int i3 = blockX + hellRNG.nextInt(16) + 8;
            int l4 = hellRNG.nextInt(128);
            int k6 = blockZ + hellRNG.nextInt(16) + 8;
            (new FlowerGen(NContent.glowshroom, 1)).generate(worldObj, hellRNG, i3, l4, k6);
        }
        if (doGen && hellRNG.nextInt(9) == 0)
        {
            int i3 = blockX + hellRNG.nextInt(16) + 8;
            int l4 = hellRNG.nextInt(128);
            int k6 = blockZ + hellRNG.nextInt(16) + 8;
            (new FlowerGen(NContent.glowshroom, 2)).generate(worldObj, hellRNG, i3, l4, k6);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, hellRNG, blockX, blockZ));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        BlockFalling.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    @Override
    public boolean saveChunks (boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    @Override
    public boolean unloadQueuedChunks ()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
    public boolean canSave ()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString ()
    {
        return "HellRandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List getPossibleCreatures (EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        if (par1EnumCreatureType == EnumCreatureType.monster && this.genNetherBridge.hasStructureAt(par2, par3, par4))
        {
            return this.genNetherBridge.getSpawnList();
        }
        else
        {
            BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
            return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
        }
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    @Override
    public ChunkPosition func_147416_a (World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    @Override
    public int getLoadedChunkCount ()
    {
        return 0;
    }

    @Override
    public void recreateStructures (int par1, int par2)
    {
        genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, null);
    }

    @Override
    public void saveExtraData ()
    {
        // NYI
    }
}
