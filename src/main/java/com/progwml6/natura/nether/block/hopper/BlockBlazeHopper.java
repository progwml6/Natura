package com.progwml6.natura.nether.block.hopper;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockHopper;
import net.minecraft.block.SoundType;

public class BlockBlazeHopper extends BlockHopper
{
    public BlockBlazeHopper()
    {
        super();
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setHardness(3.0F);
        this.setResistance(8.0F);
        this.setSoundType(SoundType.METAL);
    }
}
