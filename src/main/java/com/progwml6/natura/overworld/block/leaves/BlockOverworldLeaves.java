package com.progwml6.natura.overworld.block.leaves;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog.LogType;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOverworldLeaves extends BlockLeaves
{
    public BlockOverworldLeaves()
    {
        this.setCreativeTab(NaturaRegistry.tabWorld);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Override
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (LogType type : LogType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(BlockOverworldLog.TYPE, type))));
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

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        return 25;
    }

    // sapling item
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NaturaOverworld.overworldSapling);
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
        /**if (worldIn.rand.nextInt(chance) == 0)
        {
            ItemStack stack = null;
            if (state.getValue(BlockOverworldLog.TYPE) == FoliageType.PURPLE)
            {
                stack = TinkerCommons.matSlimeBallPurple.copy();
            }
            else if (state.getValue(BlockOverworldLog.TYPE) == FoliageType.BLUE)
            {
                if (worldIn.rand.nextInt(3) == 0)
                {
                    stack = TinkerCommons.matSlimeBallBlue.copy();
                }
                else
                {
                    stack = new ItemStack(Items.SLIME_BALL);
                }
            }
        
            if (stack != null)
            {
                spawnAsEntity(worldIn, pos, stack);
            }
        }*/
    }

    // sapling meta
    @Override
    public int damageDropped(IBlockState state)
    {
        return (state.getValue(BlockOverworldLog.TYPE)).ordinal() & 3; // only first 2 bits
    }

    // item dropped on silktouching
    @Override
    protected ItemStack createStackedBlock(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(BlockOverworldLog.TYPE)).ordinal() & 3);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockOverworldLog.TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int type = meta % 4;
        if (type < 0 || type >= LogType.values().length)
        {
            type = 0;
        }
        LogType logtype = LogType.values()[type];
        return this.getDefaultState()
                .withProperty(BlockOverworldLog.TYPE, logtype)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = (state.getValue(BlockOverworldLog.TYPE)).ordinal() & 3; // only first 2 bits

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
        return Lists.newArrayList(this.createStackedBlock(state));
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

}
