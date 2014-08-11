package mods.natura.plugins.minefactoryreloaded.harvestables;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableStandard implements IFactoryHarvestable
{
    private Block sourceId;
    private HarvestType harvestType;

    public HarvestableStandard(Block sourceId, HarvestType harvestType)
    {
        this.sourceId = sourceId;
        this.harvestType = harvestType;
    }

    @Override
    public Block getPlant ()
    {
        return sourceId;
    }

    @Override
    public HarvestType getHarvestType ()
    {
        return harvestType;
    }

    @Override
    public boolean breakBlock ()
    {
        return true;
    }

    @Override
    public boolean canBeHarvested (World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        return true;
    }

    @Override
    public List<ItemStack> getDrops (World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
    {
        return sourceId.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    }

    @Override
    public void preHarvest (World world, int x, int y, int z)
    {
    }

    @Override
    public void postHarvest (World world, int x, int y, int z)
    {
    }
}
