package com.progwml6.natura.overworld.block.slabs;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import slimeknights.mantle.block.EnumBlockSlab;

public class BlockColoredGrassSlab extends EnumBlockSlab<BlockColoredGrass.GrassType>
{
    public BlockColoredGrassSlab()
    {
        super(Material.GROUND, BlockColoredGrass.TYPE, BlockColoredGrass.GrassType.class);

        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

    @Override
    public IBlockState getFullBlock(IBlockState state)
    {
        return NaturaOverworld.coloredGrass.getDefaultState().withProperty(BlockColoredGrass.TYPE, state.getValue(BlockColoredGrass.TYPE));
    }
}
