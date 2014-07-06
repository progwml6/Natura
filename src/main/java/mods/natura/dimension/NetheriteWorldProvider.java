package mods.natura.dimension;

import mods.natura.common.PHNatura;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class NetheriteWorldProvider extends WorldProviderHell
{
    /**
     * creates a new world chunk manager for WorldProvider
     */
    @Override
    public void registerWorldChunkManager ()
    {
        this.worldChunkMgr = new NetheriteChunkManager(BiomeGenBase.hell, 1.0F, 0.0F);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }

    @Override
    public IChunkProvider createChunkGenerator ()
    {
        return new NetheriteChunkProvider(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public boolean canRespawnHere ()
    {
        return false;
    }

    @Override
    public boolean doesXZShowFog (int par1, int par2)
    {
        return false;
    }

    @Override
    public int getRespawnDimension (EntityPlayerMP player)
    {
        if (PHNatura.canRespawnInNether)
        {
            ChunkCoordinates coords = player.getBedLocation(-1);
            if (coords != null)
                return -1;
        }
        return 0;
    }
}
