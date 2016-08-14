package mods.natura.blocks.trees;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import mods.natura.common.NContent;
import mods.natura.worldgen.BushTreeGen;
import mods.natura.worldgen.EucalyptusTreeGenShort;
import mods.natura.worldgen.RedwoodTreeGen;
import mods.natura.worldgen.SakuraTreeGen;

import java.util.Random;

public class FloraSaplingBlock extends NSaplingBlock
{
    public FloraSaplingBlock()
    {
        super(EnumSaplingType.floraSaplings);
    }

    // canUseBonemeal
    @Override
    public boolean func_149852_a(World world, Random random, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        EnumSaplingType saplingType = getSaplingType(meta);
        return saplingType != EnumSaplingType.REDWOOD;
    }

    @Override
    public WorldGenerator getWorldGenerator(EnumSaplingType saplingType) {
        switch (saplingType) {
            case REDWOOD:
                return new RedwoodTreeGen(true, NContent.redwood);
            case EUCALYPTUS:
                return new EucalyptusTreeGenShort(0, 1);
            case HOPSEED:
                return new BushTreeGen(true, 2, 3, 2);
            case SAKURA:
                return new SakuraTreeGen(true, 1, 0);
            default:
                throw new RuntimeException("Wrong sapling type: " + saplingType);
        }
    }
}
