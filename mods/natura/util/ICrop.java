package mods.natura.util;

import net.minecraft.world.IBlockAccess;

public interface ICrop
{
    public enum HarvestType
    {
    	CLICK,
    	BREAK,
    	USE,
    	MACHINE
    }
    
    public boolean harvestCrop(IBlockAccess world, int x, int y, int z, HarvestType type);
    public boolean isFullyGrown(IBlockAccess world, int x, int y, int z);
    public float getCurrentGrowthStage();
    public void growthTick(IBlockAccess world, int x, int y, int z);
}
