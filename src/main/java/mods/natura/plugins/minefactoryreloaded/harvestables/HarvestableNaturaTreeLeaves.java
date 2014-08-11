package mods.natura.plugins.minefactoryreloaded.harvestables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableNaturaTreeLeaves extends HarvestableStandard
{
    public HarvestableNaturaTreeLeaves(Block id)
    {
        super(id, HarvestType.TreeLeaf);
    }

    @Override
    public List<ItemStack> getDrops (World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        if (harvesterSettings.get("silkTouch") != null && harvesterSettings.get("silkTouch"))
        {
            ArrayList<ItemStack> drops = Lists.newArrayList();
            drops.add(new ItemStack(getPlant(), 1, world.getBlockMetadata(x, y, z)));
            return drops;
        }
        else
        {
            return getPlant().getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        }
    }
}
