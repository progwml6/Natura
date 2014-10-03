package mods.natura.util;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public interface ICrop
{
    public enum HarvestType
    {
        BREAK, USE, MACHINE
    }

    public static final List<ItemStack> NO_YIELD = Collections.emptyList();

    /** 
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param type
     * @return A list of items harvested. If no items are returned, NO_YIELD should be passed.
     */
    public List<ItemStack> harvestCrop (IBlockAccess world, int x, int y, int z, HarvestType type);

    public boolean isFullyGrown (IBlockAccess world, int x, int y, int z);

    public boolean hasYield (IBlockAccess world, int x, int y, int z);

    public void growthTick (IBlockAccess world, int x, int y, int z);
}
