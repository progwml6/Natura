package mods.natura.blocks.nether;

import mods.natura.blocks.NBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class TaintedSoil extends NBlock
{
    public PropertyInteger METADATA;

    public TaintedSoil()
    {
        super(Material.ground, 2.2f, new String[] { "tainted_soil", "tainted_farmland_dry", "tainted_farmland_heated" });
        this.setStepSound(Block.soundTypeGravel);
        this.setResistance(25f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
    }

    public int getMetaFromState (IBlockState state)
    {
        return (Integer) state.getValue(METADATA);
    }

    @Override
    public boolean isFertile (World world, BlockPos pos)
    {
        return getMetaFromState(world.getBlockState(pos)) == 2;
    }

    @Override
    public boolean isReplaceableOreGen (World world, BlockPos pos, com.google.common.base.Predicate<IBlockState> target)
    {
        return this == target || target == Blocks.netherrack;
    }

    public boolean canSustainPlant (World world, BlockPos pos, EnumFacing direction, IPlantable plant)
    {
        EnumPlantType plantType = plant.getPlantType(world, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        return plantType == EnumPlantType.Nether || super.canSustainPlant(world, pos, direction, plant);
    }

    @Override
    public boolean isFireSource (World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
