package mods.natura.worldgen.retro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
 

import mods.natura.worldgen.BaseCloudWorldgen;
import mods.natura.worldgen.BaseCropWorldgen;
import mods.natura.worldgen.BaseTreeWorldgen;
import mods.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import cpw.mods.fml.common.IWorldGenerator;
 
public class WorldHandler implements IWorldGenerator {
 
        private static HashSet<String> featureNames = new HashSet();
        private static HashSet<OreGenEvent.GenerateMinable.EventType> vanillaGenEvents = new HashSet();
        private static HashSet dimensionBlacklist = new HashSet();
 
        private static long genHash = 0;
 
        static {
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.COAL);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.DIAMOND);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.DIRT);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.GOLD);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.GRAVEL);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.IRON);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.LAPIS);
                vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.REDSTONE);
        }
 
        public static WorldHandler instance = new WorldHandler();
 
        @ForgeSubscribe
        public void handleChunkSaveEvent(ChunkDataEvent.Save event) {
 
                NBTTagCompound tag = new NBTTagCompound();
 
                if (Natura.retrogen) {
                        tag.setLong("Features", genHash);
                }
                event.getData().setTag("NaturaWorld", tag);
        }
 
        @ForgeSubscribe
        public void handleChunkLoadEvent(ChunkDataEvent.Load event) {
 
                int dim = event.world.provider.dimensionId;
 
                if (dimensionBlacklist.contains(dim)) {
                        return;
                }
                boolean features = false;
                boolean regen = false;
                NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("NaturaWorld");
 
                if (tag != null) {
                        features = tag.getLong("Features") != genHash && Natura.retrogen;
                }
                ChunkCoord cCoord = new ChunkCoord(event.getChunk());
 
                if (tag == null && (Natura.retrogen) && !event.getData().getBoolean("Natura.Retrogen")) {
                        regen = true;
                }
                
                if (features) {
                       // CoFHWorld.log.log(Level.INFO, "Retroactively generating features for the chunk at " + cCoord.toString() + ".");
                        regen = true;
                }
                if (regen) {
                        ArrayList chunks = (ArrayList) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
 
                        if (chunks == null) {
                                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList());
                                chunks = (ArrayList) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
                        }
                        if (chunks != null) {
                                chunks.add(cCoord);
                                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), chunks);
                        }
                }
        }
 
        @ForgeSubscribe
        public void handleOreGenEvent(OreGenEvent.GenerateMinable event) {
                if (vanillaGenEvents.contains(event.type)) {
                        event.setResult(Result.DENY);
                }
        }
 
        /* IWorldGenerator */
        @Override
        public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
 
                generateWorld(random, chunkX, chunkZ, world, true);
        }
 
 
        /* HELPER FUNCTIONS */
        
 
        public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen) {
  
                if (!newGen && Natura.retrogen) {
                        return;
                }
                if (world.provider.dimensionId == 1 ){//|| world.provider.dimensionId == -1) {
                        return;
                }        
                Natura.crops.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
                Natura.clouds.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
                Natura.trees.retrogen = true;
                Natura.trees.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
                Natura.trees.retrogen = false;

                if (!newGen) {
                        world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
                }
        }
 
 
}