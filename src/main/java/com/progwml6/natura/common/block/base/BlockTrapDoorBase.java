package com.progwml6.natura.common.block.base;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTrapDoorBase extends BlockTrapDoor
{
    public BlockTrapDoorBase()
    {
        super(Material.WOOD);
        this.disableStats();
        this.setHardness(3.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }
}
