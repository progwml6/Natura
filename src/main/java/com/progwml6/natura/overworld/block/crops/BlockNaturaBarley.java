package com.progwml6.natura.overworld.block.crops;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemStack;

public class BlockNaturaBarley extends BlockOverworldCrops
{
    public final static PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    public BlockNaturaBarley()
    {
        super();
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return 3;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { AGE });
    }

    @Override
    protected ItemStack getSeed()
    {
        return NaturaOverworld.barley_seeds.copy();
    }

    @Override
    protected ItemStack getCrop()
    {
        return NaturaCommons.barley.copy();
    }

}
