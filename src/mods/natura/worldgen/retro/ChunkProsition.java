package mods.natura.worldgen.retro;

import net.minecraft.world.chunk.Chunk;

public class ChunkProsition
{
	int chunkX;
	int chunkZ;
	
	public ChunkProsition(Chunk par1)
	{
		chunkX = par1.xPosition;
		chunkZ = par1.zPosition;
	}
	
	public int getChunkX()
	{
		return chunkX;
	}
	
	public int getChunkZ()
	{
		return chunkZ;
	}
}
