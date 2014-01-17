package mods.natura.worldgen.retro;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerWorld implements ITickHandler
{
	
	public static TickHandlerWorld instance = new TickHandlerWorld();
	
	//Defined is better so people know whats happening
	public static HashMap<Integer, ArrayList<ChunkProsition>> chunksToGen = new HashMap<Integer, ArrayList<ChunkProsition>>();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		
		World world = (World) tickData[0];
		int dim = world.provider.dimensionId;
		ArrayList chunks = (ArrayList) chunksToGen.get(Integer.valueOf(dim));
		
		if (chunks != null && chunks.size() > 0)
		{
			ChunkProsition c = (ChunkProsition) chunks.get(0);
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
	
	@Override
	public EnumSet<TickType> ticks()
	{
		
		return EnumSet.of(TickType.WORLD);
	}
	
	@Override
	public String getLabel()
	{
		
		return "NaturaWorld";
	}
	
}
