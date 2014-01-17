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

public class WorldHandler
{
	
	private static HashSet dimensionBlacklist = new HashSet();
	
	/** Had to be differend than 0 because NBT Long returns 0 if its null **/
	private static long genHash = 10;
	
	public static WorldHandler instance = new WorldHandler();
	
	@ForgeSubscribe
	public void handleChunkSaveEvent(ChunkDataEvent.Save event)
	{
		
		NBTTagCompound tag = new NBTTagCompound();
		
		if (Natura.retrogen)
		{
			tag.setLong("Features", genHash);
		}
		event.getData().setTag("NaturaWorld", tag);
	}
	
	@ForgeSubscribe
	public void handleChunkLoadEvent(ChunkDataEvent.Load event)
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
			// Before my fix it were if(0 != 0) now the possebillities are is 10 != 10 or 0 != 10
			features = tag.getLong("Features") != genHash && Natura.retrogen;
		}
		ChunkProsition cCoord = new ChunkProsition(event.getChunk());
		
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
			ArrayList<ChunkProsition> chunks = (ArrayList) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
			
			if (chunks == null)
			{
				TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList<ChunkProsition>());
				chunks = (ArrayList<ChunkProsition>) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
			}
			if (chunks != null)
			{
				chunks.add(cCoord);
				TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), chunks);
			}
		}
	}
	
	
	/* HELPER FUNCTIONS */
	
	public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen)
	{
		//Before it were reversed. kind of stupid because Retrogen were false while it is active so it shuting itself down
		if (newGen && !Natura.retrogen)
		{
			return;
		}
		if (world.provider.dimensionId == 1)
		{
			return;
		}
		Natura.crops.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
		Natura.clouds.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
		Natura.trees.retrogen = true;
		Natura.trees.generate(random, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
		Natura.trees.retrogen = false;
		
		if (!newGen)
		{
			world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
		}
	}
	
}