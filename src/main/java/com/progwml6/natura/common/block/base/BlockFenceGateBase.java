package com.progwml6.natura.common.block.base;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;

public class BlockFenceGateBase extends BlockFenceGate
{
    public BlockFenceGateBase()
    {
        super(EnumType.OAK);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

}
