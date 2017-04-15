package com.progwml6.natura.common.block.base;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPressurePlateBase extends BlockPressurePlate
{
    public BlockPressurePlateBase()
    {
        super(Material.WOOD, Sensitivity.EVERYTHING);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }
}
