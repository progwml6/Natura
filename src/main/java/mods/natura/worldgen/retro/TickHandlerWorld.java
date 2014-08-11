package mods.natura.worldgen.retro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import mantle.world.ChunkCoord;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class TickHandlerWorld
{

    public static TickHandlerWorld instance = new TickHandlerWorld();

    public static HashMap chunksToGen = new HashMap();

    @SubscribeEvent
    public void onTick (WorldTickEvent event)
    {

        if (event.phase.equals(Phase.END) && event.type.equals(Type.WORLD))
        {
            World world = event.world;
            int dim = world.provider.dimensionId;
            ArrayList chunks = (ArrayList) chunksToGen.get(Integer.valueOf(dim));

            if (chunks != null && chunks.size() > 0)
            {
                ChunkCoord c = (ChunkCoord) chunks.get(0);
                long worldSeed = world.getSeed();
                Random rand = new Random(worldSeed);
                long xSeed = rand.nextLong() >> 2 + 1L;
                long zSeed = rand.nextLong() >> 2 + 1L;
                rand.setSeed(xSeed * c.chunkX + zSeed * c.chunkZ ^ worldSeed);
                WorldHandler.instance.generateWorld(rand, c.chunkX, c.chunkZ, world, true);
                chunks.remove(0);
                chunksToGen.put(Integer.valueOf(dim), chunks);
            }
        }
    }

}