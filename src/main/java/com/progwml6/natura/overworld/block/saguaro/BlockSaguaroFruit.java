package com.progwml6.natura.overworld.block.saguaro;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSaguaroFruit extends Block
{
    //@formatter:off
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.1875D, 0.25D, 1.125D, 0.75D, 0.75D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(-0.125D, 0.1875D, 0.25D, 0.375D, 0.75D, 0.75D);

    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25D, 0.1875D, 0.625D, 0.75D, 0.75D, 1.125D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25D, 0.1875D, -0.125D, 0.75D, 0.75D, 0.375D);
    //@formatter:on

    public BlockSaguaroFruit()
    {
        super(Material.CACTUS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.3f);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
        case SOUTH:
            return SOUTH_AABB;
        case NORTH:
        default:
            return NORTH_AABB;
        case WEST:
            return WEST_AABB;
        case EAST:
            return EAST_AABB;
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(NaturaOverworld.saguaroFruitItem, 1);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }
}
