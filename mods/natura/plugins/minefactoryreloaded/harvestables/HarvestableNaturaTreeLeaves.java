package mods.natura.plugins.minefactoryreloaded.harvestables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableNaturaTreeLeaves extends HarvestableStandard
{
    public HarvestableNaturaTreeLeaves(int id)
    {
        super(id, HarvestType.TreeLeaf);
    }

    @Override
    public List<ItemStack> getDrops (World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        if (harvesterSettings.get("silkTouch") != null && harvesterSettings.get("silkTouch"))
        {
            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            drops.add(new ItemStack(getPlantId(), 1, world.getBlockMetadata(x, y, z)));
            return drops;
        }
        else
        {
            return Block.blocksList[getPlantId()].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        }
    }
}
