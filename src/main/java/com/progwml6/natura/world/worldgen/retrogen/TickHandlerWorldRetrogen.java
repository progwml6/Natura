package com.progwml6.natura.world.worldgen.retrogen;

import java.util.List;
import java.util.Random;

import com.google.common.collect.LinkedListMultimap;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.worldgen.CloudGenerator;
import com.progwml6.natura.world.worldgen.CropGenerator;
import com.progwml6.natura.world.worldgen.GlowshroomGenerator;
import com.progwml6.natura.world.worldgen.NetherBerryBushesGenerator;
import com.progwml6.natura.world.worldgen.NetherTreesGenerator;
import com.progwml6.natura.world.worldgen.OverworldBerryBushesGenerator;
import com.progwml6.natura.world.worldgen.OverworldTreesGenerator;
import com.progwml6.natura.world.worldgen.VineGenerator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class TickHandlerWorldRetrogen
{
    public static TickHandlerWorldRetrogen INSTANCE = new TickHandlerWorldRetrogen();

    // Overworld
    //@formatter:off
    private OverworldTreesGenerator overworldTreesGenerator;
    private OverworldBerryBushesGenerator overworldBerryBushesGenerator;
    private CloudGenerator cloudGenerator;
    private CropGenerator cropGenerator;

    // Nether
    private NetherTreesGenerator netherTreesGenerator;
    private NetherBerryBushesGenerator netherBerryBushesGenerator;
    private GlowshroomGenerator glowshroomGenerator;
    private VineGenerator vineGenerator;
    //@formatter:on

    private final LinkedListMultimap<Integer, ChunkCoords> chunkRegenList = LinkedListMultimap.create();

    public TickHandlerWorldRetrogen()
    {
        // Overworld
        if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
        {
            overworldTreesGenerator = new OverworldTreesGenerator();
            overworldBerryBushesGenerator = new OverworldBerryBushesGenerator();
            cloudGenerator = new CloudGenerator();
            cropGenerator = new CropGenerator();
        }

        // Nether
        if (Natura.pulseManager.isPulseLoaded(NaturaNether.PulseId))
        {
            netherTreesGenerator = new NetherTreesGenerator();
            netherBerryBushesGenerator = new NetherBerryBushesGenerator();
            glowshroomGenerator = new GlowshroomGenerator();
            vineGenerator = new VineGenerator();
        }
    }

    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event)
    {
        if (event.phase != Phase.END)
        {
            return;
        }

        if (Config.doRetrogen)
        {
            World world = event.world;
            int dimensionID = world.provider.getDimension();
            List<ChunkCoords> chunkList = this.chunkRegenList.get(dimensionID);

            if (!chunkList.isEmpty())
            {
                ChunkCoords coords = chunkList.get(0);
                chunkList.remove(0);

                // This bit is from FML's GameRegistry.generateWorld where the seed is constructed.
                long worldSeed = world.getSeed();
                Random random = new Random(worldSeed);
                long xSeed = random.nextLong() >> 2 + 1L;
                long zSeed = random.nextLong() >> 2 + 1L;
                random.setSeed(xSeed * coords.xCoord + zSeed * coords.zCoord ^ worldSeed);

                if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
                {
                    this.overworldTreesGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    this.overworldBerryBushesGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    if (Config.enableCloudBlocks)
                    {
                        this.cloudGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    }
                    this.cropGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                }

                if (Natura.pulseManager.isPulseLoaded(NaturaNether.PulseId))
                {
                    this.netherTreesGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    this.netherBerryBushesGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    this.glowshroomGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                    this.vineGenerator.retroGen(random, coords.xCoord, coords.zCoord, world);
                }
            }
        }
    }

    @SubscribeEvent
    public void chunkSaveEventHandler(ChunkDataEvent.Save event)
    {
        NBTTagCompound tag = new NBTTagCompound();

        if (Config.doRetrogen)
        {
            tag.setBoolean("retrogen", true);
        }

        event.getData().setTag(NaturaWorld.PulseId, tag);
    }

    @SubscribeEvent
    public void chunkLoadEventHandler(ChunkDataEvent.Load event)
    {
        if (Config.doRetrogen)
        {
            NBTTagCompound tag = (NBTTagCompound) event.getData().getTag(NaturaWorld.PulseId);

            if (tag == null || !tag.hasKey("retrogen"))
            {
                ChunkCoords coords = new ChunkCoords(event.getChunk());
                this.chunkRegenList.put(coords.dimension, coords);
            }
        }
    }

    private static class ChunkCoords
    {
        public final int dimension;

        public final int xCoord;

        public final int zCoord;

        public ChunkCoords(Chunk chunk)
        {
            this.dimension = chunk.getWorld().provider.getDimension();
            this.xCoord = chunk.xPosition;
            this.zCoord = chunk.zPosition;
        }
    }
}
