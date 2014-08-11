package mods.natura.plugins.minefactoryreloaded.fertilizables;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableNaturaCrop implements IFactoryFertilizable
{
    private Block _blockId;

    public FertilizableNaturaCrop(Block blockId)
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
        return fertilizerType == FertilizerType.GrowPlant && world.getBlockMetadata(x, y, z) != 3 && world.getBlockMetadata(x, y, z) != 8;
    }

    @Override
    public boolean fertilize (World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta != 3 && meta != 8)
        {
            if (meta < 3)
            {
                int output = rand.nextInt(3) + 1 + meta;
                if (output > 3)
                    output = 3;
                world.setBlockMetadataWithNotify(x, y, z, output, 3);
            }
            else
            {
                int output = rand.nextInt(4) + 1 + meta;
                if (output > 8)
                    output = 8;
                world.setBlockMetadataWithNotify(x, y, z, output, 3);
            }
            return true;
        }
        return false;
    }
}