package mods.natura.worldgen.retro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import mantle.world.ChunkCoord;
import mods.natura.Natura;
import mods.natura.worldgen.BaseTreeWorldgen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldHandler
{

    private static HashSet dimensionBlacklist = new HashSet();

    private static long genHash = 100;

    public static WorldHandler instance = new WorldHandler();

    @SubscribeEvent
    public void handleChunkSaveEvent (ChunkDataEvent.Save event)
    {

        NBTTagCompound tag = new NBTTagCompound();

        if (Natura.retrogen)
        {
            tag.setLong("Features", genHash);
        }
        event.getData().setTag("NaturaWorld", tag);
    }

    @SubscribeEvent
    public void handleChunkLoadEvent (ChunkDataEvent.Load event)
    {

        int dim = event.world.provider.dimensionId;

        if (dimensionBlacklist.contains(dim))
        {
            return;
        }
        boolean features = false;
        boolean regen = false;
        NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("NaturaWorld");

        if (tag != null)
        {
            features = tag.getLong("Features") != genHash && Natura.retrogen;
        }
        ChunkCoord cCoord = new ChunkCoord(event.getChunk());

        if (tag == null && (Natura.retrogen) && !event.getData().getBoolean("Natura.Retrogen"))
        {
            regen = true;
        }

        if (features)
        {
            regen = true;
        }
        if (regen)
        {
            ArrayList chunks = (ArrayList) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));

            if (chunks == null)
            {
                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList());
                chunks = (ArrayList) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
            }
            if (chunks != null)
            {
                chunks.add(cCoord);
                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), chunks);
            }
        }
    }

    /* HELPER FUNCTIONS */

    public void generateWorld (Random random, int chunkX, int chunkZ, World world, boolean newGen)
    {
        if (newGen && !Natura.retrogen)
        {
            return;
        }
        if (world.provider.dimensionId == 1)
        {//|| world.provider.dimensionId == -1) {
            return;
        }
        Natura.crops.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
        Natura.clouds.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
        BaseTreeWorldgen.retrogen = true;
        Natura.trees.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
        BaseTreeWorldgen.retrogen = false;

        if (!newGen)
        {
            world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
        }
    }

}