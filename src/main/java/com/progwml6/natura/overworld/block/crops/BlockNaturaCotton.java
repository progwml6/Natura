package com.progwml6.natura.overworld.block.crops;

import javax.annotation.Nullable;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockNaturaCotton extends BlockOverworldCrops
{
    public final static PropertyInteger AGE = PropertyInteger.create("age", 0, 4);

    public BlockNaturaCotton()
    {
        super();
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        if (!worldIn.isRemote)
        {
            int age = worldIn.getBlockState(pos).getValue(AGE).intValue();

            if (age == 4)
            {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(2)), 3);

                EntityItem entityitem = new EntityItem(worldIn, playerIn.posX, playerIn.posY - 1.0D, playerIn.posZ, NaturaCommons.cotton.copy());

                worldIn.spawnEntityInWorld(entityitem);

                if (!(playerIn instanceof FakePlayer))
                {
                    entityitem.onCollideWithPlayer(playerIn);
                }
            }
        }
    }

    /* Right-click harvests berries */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        int age = state.getValue(AGE).intValue();

        if (age == 4)
        {
            if (worldIn.isRemote)
                return true;

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(2)), 3);

            EntityItem entityitem = new EntityItem(worldIn, playerIn.posX, playerIn.posY - 1.0D, playerIn.posZ, NaturaCommons.cotton.copy());

            worldIn.spawnEntityInWorld(entityitem);

            if (!(playerIn instanceof FakePlayer))
            {
                entityitem.onCollideWithPlayer(playerIn);
            }
            return true;
        }

        return false;
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return 4;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { AGE });
    }

    @Override
    protected ItemStack getSeed()
    {
        return NaturaOverworld.cotton_seeds.copy();
    }

    @Override
    protected ItemStack getCrop()
    {
        return NaturaCommons.cotton.copy();
    }

}
