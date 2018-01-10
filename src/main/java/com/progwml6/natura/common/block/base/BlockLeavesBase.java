package com.progwml6.natura.common.block.base;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;

public abstract class BlockLeavesBase extends BlockLeaves
{
    public BlockLeavesBase()
    {
        super();
    }

    @Override
    public EnumType getWoodType(int meta)
    {
        throw new UnsupportedOperationException();
    }

}
