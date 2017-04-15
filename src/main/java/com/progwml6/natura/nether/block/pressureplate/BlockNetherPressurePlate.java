package com.progwml6.natura.nether.block.pressureplate;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockNetherPressurePlate extends BlockPressurePlate
{
    public BlockNetherPressurePlate()
    {
        super(Material.ROCK, Sensitivity.MOBS);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }
}
