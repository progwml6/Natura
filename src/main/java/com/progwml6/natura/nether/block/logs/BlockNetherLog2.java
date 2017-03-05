package com.progwml6.natura.nether.block.logs;

import java.util.List;

import javax.annotation.Nonnull;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNetherLog2 extends Block
{
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);

    public BlockNetherLog2()
    {
        super(Material.WOOD);

        this.setHardness(8.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(NaturaRegistry.tabWorld);

        this.setDefaultState(this.blockState.getBaseState().withProperty(META, Integer.valueOf(0)));
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
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
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(META, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(META).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { META });
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(META).intValue() & 3);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata
     * of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        int meta = state.getValue(META).intValue();

        if (meta < 12)
        {
            return 0;
        }
        else if (meta == 15)
        {
            return 15;
        }

        return 12;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack)
    {
        if (meta >= 12)
        {
            return this.getStateFromMeta(meta);
        }

        int metadata = meta & 3;
        byte add = 0;

        switch (facing)
        {
        case DOWN:
        case UP:
            add = 0;
            if (hitX > 0.5f)
            {
                add += 1;
            }
            if (hitZ > 0.5f)
            {
                add += 2;
            }
            break;
        case NORTH:
        case SOUTH:
            add = 8;
            if (hitX > 0.5f)
            {
                add += 1;
            }
            if (hitZ < 0.5f)
            {
                add += 2;
            }
            break;
        case WEST:
        case EAST:
            add = 4;
            if (hitX < 0.5f)
            {
                add += 1;
            }
            if (hitZ < 0.5f)
            {
                add += 2;
            }
        }

        return this.getStateFromMeta(metadata | add);
    }

    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 15));
    }

    //@formatter:off
    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    //@formatter:on
}
