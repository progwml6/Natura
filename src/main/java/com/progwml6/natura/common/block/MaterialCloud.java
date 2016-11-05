package com.progwml6.natura.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialCloud extends Material
{
    public MaterialCloud()
    {
        super(MapColor.SNOW);
        this.setNoPushMobility();
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    @Override
    public boolean isLiquid()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    @Override
    public boolean blocksMovement()
    {
        return false;
    }

    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    @Override
    public boolean isSolid()
    {
        return false;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    @Override
    public boolean blocksLight()
    {
        return false;
    }
}
