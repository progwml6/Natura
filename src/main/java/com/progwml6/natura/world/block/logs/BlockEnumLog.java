package com.progwml6.natura.world.block.logs;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockEnumLog<T extends Enum<T> & EnumBlock.IEnumMeta & IStringSerializable> extends EnumBlock<T>
{
    public static final PropertyEnum<BlockEnumLog.EnumAxis> LOG_AXIS = PropertyEnum.<BlockEnumLog.EnumAxis> create("axis", BlockEnumLog.EnumAxis.class);

    public BlockEnumLog(PropertyEnum<T> prop, Class<T> clazz)
    {
        super(Material.WOOD, prop, clazz);

        this.setHardness(1.5F);
        this.setResistance(5F);
        this.setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 20);
        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }

    @Override
    public boolean rotateBlock(net.minecraft.world.World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        for (IProperty<?> prop : state.getProperties().keySet())
        {
            if (prop.getName().equals("axis"))
            {
                world.setBlockState(pos, state.cycleProperty(prop));
                return true;
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
        {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
            {
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
                {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                }
            }
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
     */
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
        case COUNTERCLOCKWISE_90:
        case CLOCKWISE_90:

            switch (state.getValue(LOG_AXIS))
            {
            case X:
                return state.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Z);
            case Z:
                return state.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.X);
            default:
                return state;
            }

        default:
            return state;
        }
    }

    //@formatter:off
    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    //@formatter:on

    public static enum EnumAxis implements IStringSerializable
    {
        X("x"), Y("y"), Z("z"), NONE("none");

        private final String name;

        private EnumAxis(String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return this.name;
        }

        public static BlockEnumLog.EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
            case X:
                return X;
            case Y:
                return Y;
            case Z:
                return Z;
            default:
                return NONE;
            }
        }

        @Override
        public String getName()
        {
            return this.name;
        }
    }
}
