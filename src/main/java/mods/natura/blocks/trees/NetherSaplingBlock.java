package mods.natura.blocks.trees;

import mods.natura.common.NContent;
import mods.natura.worldgen.BloodTreeLargeGen;
import mods.natura.worldgen.DarkwoodGen;
import mods.natura.worldgen.FusewoodGen;
import mods.natura.worldgen.WhiteTreeGen;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class NetherSaplingBlock extends NSaplingBlock {

    public NetherSaplingBlock()
    {
        super(EnumSaplingType.netherSaplings);
    }

    @Override
    public boolean canPlaceBlockAt (World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block == null || block.isReplaceable(world, x, y, z))
        {
            Block soil = world.getBlock(x, y - 1, z);
            if (soil != null)
                if (canGrowOnBlock(soil) || soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this))
                    return true;

            Block ceiling = world.getBlock(x, y + 1, z);
            if (ceiling != null)
                if (canGrowOnBlock(ceiling) || ceiling.canSustainPlant(world, x, y + 1, z, ForgeDirection.DOWN, this))
                    return true;
        }
        return false;
    }

    public boolean canGrowOnBlock (Block id)
    {
        return id == Blocks.soul_sand || id == Blocks.netherrack || id == NContent.taintedSoil;
    }

    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (!(block instanceof NetherSaplingBlock))
            throw new RuntimeException("Expected a NetherSaplingBlock, got " + block);
        int meta = world.getBlockMetadata(x, y, z);
        EnumSaplingType saplingType = getSaplingType(meta);

        switch (saplingType)
        {
            case GHOSTWOOD:
            case DARKWOOD:
            case FUSEWOOD:
                Block netherSoil = world.getBlock(x, y - 1, z);
                if (netherSoil == null)
                    return false;
                return netherSoil == Blocks.netherrack || netherSoil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case BLOODWOOD:
                Block netherCeiling = world.getBlock(x, y + 1, z);
                if (netherCeiling == null)
                    return false;
                return canGrowOnBlock(netherCeiling) || netherCeiling.canSustainPlant(world, x, y + 1, z, ForgeDirection.DOWN, this);
            default:
                return true;
        }
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Nether;
    }

    @Override
    protected boolean hasEnoughLightToGrow(EnumSaplingType saplingType, World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public WorldGenerator getWorldGenerator(EnumSaplingType saplingType)
    {
        switch (saplingType) {
            case GHOSTWOOD:
                return new WhiteTreeGen(true, 2, 1);
            case BLOODWOOD:
                return new BloodTreeLargeGen(3, 2);
            case DARKWOOD:
                return new DarkwoodGen(true, 3, 0);
            case FUSEWOOD:
                return new FusewoodGen(true, 3, 1);
            default:
                throw new RuntimeException("Wrong sapling type: " + saplingType);
        }
    }

}
