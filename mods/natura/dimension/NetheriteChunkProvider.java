package mods.natura.dimension;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.FIRE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.GLOWSTONE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class NetheriteChunkProvider implements IChunkProvider
{
    private Random random;

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
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;

    /** Is the world that the nether is getting generated. */
    private World worldObj;
    private double[] noiseField;
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

    {
        genNetherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(genNetherBridge, NETHER_BRIDGE);
        netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, NETHER_CAVE);
    }

    public NetheriteChunkProvider(World par1World, long par2)
    {
        this.worldObj = par1World;
        this.random = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.random, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.random, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.random, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.random, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.random, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.random, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.random, 16);

        NoiseGeneratorOctaves[] noiseGens = {netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, netherNoiseGen6, netherNoiseGen7};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.random, noiseGens);
        this.netherNoiseGen1 = noiseGens[0];
        this.netherNoiseGen2 = noiseGens[1];
        this.netherNoiseGen3 = noiseGens[2];
        this.slowsandGravelNoiseGen = noiseGens[3];
        this.netherrackExculsivityNoiseGen = noiseGens[4];
        this.netherNoiseGen6 = noiseGens[5];
        this.netherNoiseGen7 = noiseGens[6];
    }

    /**
     * Generates the shape of the terrain in the nether.
     */
    public void generateNetherTerrain(int chunkX, int chunkZ, byte[] blockArray)
    {
        byte initialSize = 4;
        byte b1 = 32;
        int kay = initialSize + 1;
        byte oneSeven = 17;
        int zee = initialSize + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * initialSize, 0, chunkZ * initialSize, kay, oneSeven, zee);

        for (int iterOne = 0; iterOne < initialSize; ++iterOne)
        {
            for (int iterTwo = 0; iterTwo < initialSize; ++iterTwo)
            {
                for (int iterThree = 0; iterThree < 16; ++iterThree)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((iterOne + 0) * zee + iterTwo + 0) * oneSeven + iterThree + 0];
                    double d2 = this.noiseField[((iterOne + 0) * zee + iterTwo + 1) * oneSeven + iterThree + 0];
                    double d3 = this.noiseField[((iterOne + 1) * zee + iterTwo + 0) * oneSeven + iterThree + 0];
                    double d4 = this.noiseField[((iterOne + 1) * zee + iterTwo + 1) * oneSeven + iterThree + 0];
                    double d5 = (this.noiseField[((iterOne + 0) * zee + iterTwo + 0) * oneSeven + iterThree + 1] - d1) * d0;
                    double d6 = (this.noiseField[((iterOne + 0) * zee + iterTwo + 1) * oneSeven + iterThree + 1] - d2) * d0;
                    double d7 = (this.noiseField[((iterOne + 1) * zee + iterTwo + 0) * oneSeven + iterThree + 1] - d3) * d0;
                    double d8 = (this.noiseField[((iterOne + 1) * zee + iterTwo + 1) * oneSeven + iterThree + 1] - d4) * d0;

                    for (int idtop = 0; idtop < 8; ++idtop)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int idbase = 0; idbase < 4; ++idbase)
                        {
                            int idInArray = idbase + iterOne * 4 << 11 | 0 + iterTwo * 4 << 7 | iterThree * 8 + idtop;
                            short short1 = 128;
                            double quarter = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * quarter;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                int l2 = 0;

                                if (iterThree * 8 + idtop < b1)
                                {
                                    l2 = Block.waterStill.blockID;
                                }

                                if (d15 > 0.0D)
                                {
                                    l2 = Block.netherrack.blockID;
                                }

                                blockArray[idInArray] = (byte)l2;
                                idInArray += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /**
     * name based on ChunkProviderGenerate
     */
    public void replaceBlocksForBiome(int chunkX, int chunkZ, byte[] blockArray)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blockArray, null);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        byte b0 = 64;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int iterX = 0; iterX < 16; ++iterX)
        {
            for (int iterZ = 0; iterZ < 16; ++iterZ)
            {
                boolean flag = this.slowsandNoise[iterX + iterZ * 16] + this.random.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[iterX + iterZ * 16] + this.random.nextDouble() * 0.2D > 0.0D;
                int i1 = (int)(this.netherrackExclusivityNoise[iterX + iterZ * 16] / 3.0D + 3.0D + this.random.nextDouble() * 0.25D);
                int j1 = -1;
                byte b1 = (byte)Block.netherrack.blockID;
                byte b2 = (byte)Block.netherrack.blockID;

                for (int iterY = 255; iterY >= 0; --iterY)
                {
                    int blockPos = (iterZ * 16 + iterX) * 256 + iterY;

                    if (iterY < 255 - this.random.nextInt(5) && iterY > 0 + this.random.nextInt(5))
                    {
                        byte replaceID = blockArray[blockPos];

                        if (replaceID == 0)
                        {
                            j1 = -1;
                        }
                        else if (replaceID == Block.netherrack.blockID)
                        {
                            if (j1 == -1)
                            {
                                if (i1 <= 0)
                                {
                                    b1 = 0;
                                    b2 = (byte)Block.netherrack.blockID;
                                }
                                else if (iterY >= b0 - 4 && iterY <= b0 + 1)
                                {
                                    b1 = (byte)Block.netherrack.blockID;
                                    b2 = (byte)Block.netherrack.blockID;

                                    if (flag1)
                                    {
                                        b1 = (byte)Block.gravel.blockID;
                                    }

                                    if (flag1)
                                    {
                                        b2 = (byte)Block.netherrack.blockID;
                                    }

                                    if (flag)
                                    {
                                        b1 = (byte)Block.slowSand.blockID;
                                    }

                                    if (flag)
                                    {
                                        b2 = (byte)Block.slowSand.blockID;
                                    }
                                }

                                if (iterY < b0 && b1 == 0)
                                {
                                    b1 = (byte)Block.waterStill.blockID;
                                }

                                j1 = i1;

                                if (iterY >= b0 - 1)
                                {
                                    blockArray[blockPos] = b1;
                                }
                                else
                                {
                                    blockArray[blockPos] = b2;
                                }
                            }
                            else if (j1 > 0)
                            {
                                --j1;
                                blockArray[blockPos] = b2;
                            }
                        }
                    }
                    else
                    {
                        blockArray[blockPos] = (byte)Block.bedrock.blockID;
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int chunkX, int chunkZ)
    {
        return this.provideChunk(chunkX, chunkZ);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        this.random.setSeed((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
        byte[] blockArray = new byte[65536];
        this.generateNetherTerrain(chunkX, chunkZ, blockArray);
        this.replaceBlocksForBiome(chunkX, chunkZ, blockArray);
        this.netherCaveGenerator.generate(this, this.worldObj, chunkX, chunkZ, blockArray);
        this.genNetherBridge.generate(this, this.worldObj, chunkX, chunkZ, blockArray);
        Chunk chunk = new Chunk(this.worldObj, blockArray, chunkX, chunkZ);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, chunkX * 16, chunkZ * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)abiomegenbase[k].biomeID;
        }

    	//System.out.println("Generating at "+chunkX+", "+chunkZ);
        chunk.resetRelightChecks();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] noiseField, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ)
    {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseField, posX, posY, posZ, sizeX, sizeY, sizeZ);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
        if (noiseField == null)
        {
            noiseField = new double[sizeX * sizeY * sizeZ];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, posX, posY, posZ, sizeX, sizeY, sizeZ, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[sizeY];
        int i2;

        for (i2 = 0; i2 < sizeY; ++i2)
        {
            adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)sizeY) * 2.0D;
            double d2 = (double)i2;

            if (i2 > sizeY / 2)
            {
                d2 = (double)(sizeY - 1 - i2);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < sizeX; ++i2)
        {
            for (int j2 = 0; j2 < sizeZ; ++j2)
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
                d5 = d5 * (double)sizeY / 16.0D;
                ++l1;

                for (int k2 = 0; k2 < sizeY; ++k2)
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

                    if (k2 > sizeY - 4)
                    {
                        d11 = (double)((float)(k2 - (sizeY - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if ((double)k2 < d4)
                    {
                        d11 = (d4 - (double)k2) / 4.0D;

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

                    noiseField[k1] = d6;
                    ++k1;
                }
            }
        }

        return noiseField;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, random, par2, par3, false));

        int k = par2 * 16;
        int l = par3 * 16;
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.random, par2, par3);
        int i1;
        int j1;
        int k1;
        int l1;

        boolean doGen = TerrainGen.populate(par1IChunkProvider, worldObj, random, par2, par3, false, NETHER_LAVA);
        for (i1 = 0; doGen && i1 < 8; ++i1)
        {
            j1 = k + this.random.nextInt(16) + 8;
            k1 = this.random.nextInt(120) + 4;
            l1 = l + this.random.nextInt(16) + 8;
            (new WorldGenHellLava(Block.waterMoving.blockID, false)).generate(this.worldObj, this.random, j1, k1, l1);
        }

        i1 = this.random.nextInt(this.random.nextInt(10) + 1) + 1;
        int i2;

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, random, par2, par3, false, FIRE);
        for (j1 = 0; doGen && j1 < i1; ++j1)
        {
            k1 = k + this.random.nextInt(16) + 8;
            l1 = this.random.nextInt(120) + 4;
            i2 = l + this.random.nextInt(16) + 8;
            (new WorldGenFire()).generate(this.worldObj, this.random, k1, l1, i2);
        }

        i1 = this.random.nextInt(this.random.nextInt(10) + 1);

        doGen = TerrainGen.populate(par1IChunkProvider, worldObj, random, par2, par3, false, GLOWSTONE);
        for (j1 = 0; doGen && j1 < i1; ++j1)
        {
            k1 = k + this.random.nextInt(16) + 8;
            l1 = this.random.nextInt(120) + 4;
            i2 = l + this.random.nextInt(16) + 8;
            (new WorldGenGlowStone1()).generate(this.worldObj, this.random, k1, l1, i2);
        }

        for (j1 = 0; doGen && j1 < 10; ++j1)
        {
            k1 = k + this.random.nextInt(16) + 8;
            l1 = this.random.nextInt(128);
            i2 = l + this.random.nextInt(16) + 8;
            (new WorldGenGlowStone2()).generate(this.worldObj, this.random, k1, l1, i2);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, random, k, l));
        
        doGen = TerrainGen.decorate(worldObj, random, k, l, SHROOM);
        if (doGen && this.random.nextInt(1) == 0)
        {
            j1 = k + this.random.nextInt(16) + 8;
            k1 = this.random.nextInt(128);
            l1 = l + this.random.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.random, j1, k1, l1);
        }

        if (doGen && this.random.nextInt(1) == 0)
        {
            j1 = k + this.random.nextInt(16) + 8;
            k1 = this.random.nextInt(128);
            l1 = l + this.random.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.random, j1, k1, l1);
        }

        WorldGenMinable worldgenminable = new WorldGenMinable(Block.oreNetherQuartz.blockID, 13, Block.netherrack.blockID);
        int j2;

        for (k1 = 0; k1 < 16; ++k1)
        {
            l1 = k + this.random.nextInt(16);
            i2 = this.random.nextInt(108) + 10;
            j2 = l + this.random.nextInt(16);
            worldgenminable.generate(this.worldObj, this.random, l1, i2, j2);
        }

        for (k1 = 0; k1 < 16; ++k1)
        {
            l1 = k + this.random.nextInt(16);
            i2 = this.random.nextInt(108) + 10;
            j2 = l + this.random.nextInt(16);
            (new WorldGenHellLava(Block.waterMoving.blockID, true)).generate(this.worldObj, this.random, l1, i2, j2);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, random, k, l));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, random, par2, par3, false));

        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "HellRandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
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
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        this.genNetherBridge.generate(this, this.worldObj, par1, par2, (byte[])null);
    }
}
