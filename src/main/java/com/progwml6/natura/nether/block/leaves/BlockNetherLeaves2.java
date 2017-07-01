package com.progwml6.natura.nether.block.leaves;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherLeaves2 extends BlockLeaves
{
    public static PropertyEnum<BlockNetherLeaves2.LeavesType> TYPE = PropertyEnum.create("type", BlockNetherLeaves2.LeavesType.class);

    public BlockNetherLeaves2()
    {
        this.setCreativeTab(NaturaRegistry.tabWorld);

        Blocks.FIRE.setFireInfo(this, 0, 0);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Override
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (LeavesType type : LeavesType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(TYPE, type))));
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, @Nonnull EnumFacing side)
    {
        // isOpaqueCube returns !leavesFancy to us. We have to fix the variable before calling super
        this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    // sapling item
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            return NaturaCommons.edibles;
        }
        else
        {
            return Item.getItemFromBlock(NaturaNether.netherSapling);
        }
    }

    // sapling meta
    @Override
    public int damageDropped(IBlockState state)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            return 10;
        }
        else
        {
            return 2;
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    // item dropped on silktouching
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int type = meta % 4;
        if (type < 0 || type >= LeavesType.values().length)
        {
            type = 0;
        }
        LeavesType logtype = LeavesType.values()[type];
        return this.getDefaultState()
                .withProperty(TYPE, logtype)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = (state.getValue(TYPE)).ordinal() & 3; // only first 2 bits

        if (!state.getValue(DECAYABLE))
        {
            meta |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            meta |= 8;
        }

        return meta;
    }

    @Nonnull
    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        throw new UnsupportedOperationException(); // unused by our code.
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        return Lists.newArrayList(this.getSilkTouchDrop(state));
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            return 1;
        }

        return this.quantityDroppedWithBonus(fortune, random);
    }

    public enum LeavesType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        DARKWOOD, DARKWOOD_FLOWERING, DARKWOOD_FRUIT;

        public final int meta;

        LeavesType()
        {
            this.meta = this.ordinal();
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }

}
