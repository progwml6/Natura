package mods.natura.dimension;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class NetheriteWorldProvider extends WorldProviderHell
{
    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager ()
    {
        this.worldChunkMgr = new NetheriteChunkManager(BiomeGenBase.hell, 1.0F, 0.0F);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }

    public IChunkProvider createChunkGenerator ()
    {
        return new NetheriteChunkProvider(this.worldObj, this.worldObj.getSeed());
    }

    public boolean canRespawnHere ()
    {
        return false;
    }

    public boolean doesXZShowFog (int par1, int par2)
    {
        return false;
    }
}
