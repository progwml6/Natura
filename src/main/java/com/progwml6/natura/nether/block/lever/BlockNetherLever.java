package com.progwml6.natura.nether.block.lever;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;

public class BlockNetherLever extends BlockLever
{
    public BlockNetherLever()
    {
        super();
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }
}
