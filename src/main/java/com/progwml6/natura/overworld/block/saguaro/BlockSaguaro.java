package com.progwml6.natura.overworld.block.saguaro;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSaguaro extends Block implements IPlantable
{
    //@formatter:off
    /** Whether this cactus connects in the northern direction */
    public static final PropertyBool NORTH = PropertyBool.create("north");
    /** Whether this cactus connects in the eastern direction */
    public static final PropertyBool EAST = PropertyBool.create("east");
    /** Whether this cactus connects in the southern direction */
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    /** Whether this cactus connects in the western direction */
    public static final PropertyBool WEST = PropertyBool.create("west");
    /** Whether this cactus connects in the upper direction */
    public static final PropertyBool UP = PropertyBool.create("up");
    /** Whether this cactus connects in the lower direction */
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
    public static final AxisAlignedBB NORMAL_BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
    public static final AxisAlignedBB UP_BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    
    // TODO: Redo bounding boxes
    /*protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D),
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), 
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
    };
    public static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1.5D, 1.0D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1.5D, 0.625D);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 0.375D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
    public static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
    public static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);*/
    //@formatter:on

    public BlockSaguaro()
    {
        super(Material.CACTUS);

        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false));

        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.3f);
        this.setTickRandomly(true);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        if (!p_185477_7_)
        {
            state = state.getActualState(worldIn, pos);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);

        // TODO: Redo bounding boxes
        /*
        if (state.getValue(NORTH).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }
        
        if (state.getValue(EAST).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }
        
        if (state.getValue(SOUTH).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }
        
        if (state.getValue(WEST).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
        
        if (state.getValue(UP).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
        }
        
        if (state.getValue(DOWN).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_AABB);
        }*/
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = this.getActualState(state, source, pos);

        if (state.getValue(UP).booleanValue())
        {
            return UP_BOUNDING_BOX;
        }
        else
        {
            return NORMAL_BOUNDING_BOX;
        }

        // TODO: Redo bounding boxes
        //state = this.getActualState(state, source, pos);
        //return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

    // TODO: Redo bounding boxes
    /**
     * Returns the correct index into boundingBoxes, based on what the cactus is connected to.
     */
    /*
    private static int getBoundingBoxIdx(IBlockState state)
    {
        int i = 0;
    
        if (state.getValue(NORTH).booleanValue())
        {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }
    
        if (state.getValue(EAST).booleanValue())
        {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }
    
        if (state.getValue(SOUTH).booleanValue())
        {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }
    
        if (state.getValue(WEST).booleanValue())
        {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }
    
        if (state.getValue(UP).booleanValue())
        {
            i |= 1 << 4; // TEMP FIX FOR UP
        }
    
        return i;
    }*/

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        return block == Blocks.BARRIER ? false : ((!(block instanceof BlockSaguaro) || iblockstate.getMaterial() != this.blockMaterial) ? (iblockstate.getMaterial().isOpaque() && iblockstate.isFullCube() ? iblockstate.getMaterial() != Material.GOURD : false) : true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as cactus connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(EAST, this.canConnectTo(worldIn, pos.east())).withProperty(SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(WEST, this.canConnectTo(worldIn, pos.west())).withProperty(UP, this.canConnectTo(worldIn, pos.up())).withProperty(DOWN, this.canConnectTo(worldIn, pos.down()));
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
        case CLOCKWISE_180:
            return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST)).withProperty(UP, state.getValue(UP)).withProperty(DOWN, state.getValue(DOWN));
        case COUNTERCLOCKWISE_90:
            return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH)).withProperty(UP, state.getValue(UP)).withProperty(DOWN, state.getValue(DOWN));
        case CLOCKWISE_90:
            return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH)).withProperty(UP, state.getValue(UP)).withProperty(DOWN, state.getValue(DOWN));
        default:
            return state;
        }
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
        case LEFT_RIGHT:
            return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(UP, state.getValue(UP)).withProperty(DOWN, state.getValue(DOWN));
        case FRONT_BACK:
            return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST)).withProperty(UP, state.getValue(UP)).withProperty(DOWN, state.getValue(DOWN));
        default:
            return super.withMirror(state, mirrorIn);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, WEST, SOUTH, UP, DOWN });
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getWorldInfo().isRaining() && rand.nextInt(20) == 0 && worldIn.isAirBlock(pos.up()))
        {
            switch (rand.nextInt(4))
            {
            case 0:
                if (worldIn.isAirBlock(pos.north()))
                {
                    worldIn.setBlockState(pos.north(), NaturaOverworld.saguaroFruit.getDefaultState().withProperty(BlockSaguaroFruit.FACING, EnumFacing.SOUTH), 3);
                }
                break;
            case 1:
                if (worldIn.isAirBlock(pos.east()))
                {
                    worldIn.setBlockState(pos.east(), NaturaOverworld.saguaroFruit.getDefaultState().withProperty(BlockSaguaroFruit.FACING, EnumFacing.WEST), 3);
                }
                break;
            case 2:
                if (worldIn.isAirBlock(pos.south()))
                {
                    worldIn.setBlockState(pos.south(), NaturaOverworld.saguaroFruit.getDefaultState().withProperty(BlockSaguaroFruit.FACING, EnumFacing.NORTH), 3);
                }
                break;
            case 3:
                if (worldIn.isAirBlock(pos.west()))
                {
                    worldIn.setBlockState(pos.west(), NaturaOverworld.saguaroFruit.getDefaultState().withProperty(BlockSaguaroFruit.FACING, EnumFacing.EAST), 3);
                }
                break;
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos.down());
        Block block = state.getBlock();

        return block == this || block == Blocks.SAND || block == null;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!(entityIn instanceof EntityItem))
        {
            entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Desert;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return getDefaultState();
    }
}
