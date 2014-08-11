package mods.natura.plugins.minefactoryreloaded.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableSapling implements IFactoryFertilizable
{
    private Block _blockId;

    public FertilizableSapling(Block blockId)
    {
        _blockId = blockId;
    }

    @Override
    public Block getPlant ()
    {
        return _blockId;
    }

    @Override
    public boolean canFertilize (World world, int x, int y, int z, FertilizerType fertilizerType)
    {
        return fertilizerType == FertilizerType.GrowPlant;
    }

    @Override
    public boolean fertilize (World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
    {
        ((BlockSapling) world.getBlock(x, y, z)).func_149878_d(world, x, y, z, world.rand);
        return world.getBlock(x, y, z) != _blockId;
    }
}
