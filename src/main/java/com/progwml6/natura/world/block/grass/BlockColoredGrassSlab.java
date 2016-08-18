package com.progwml6.natura.world.block.grass;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.world.NaturaWorld;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import slimeknights.mantle.block.EnumBlockSlab;

public class BlockColoredGrassSlab extends EnumBlockSlab<BlockColoredGrass.GrassType>
{
    public final static PropertyEnum<BlockColoredGrass.GrassType> TYPE = PropertyEnum.create("type", BlockColoredGrass.GrassType.class);

    public BlockColoredGrassSlab()
    {
        super(Material.GROUND, TYPE, BlockColoredGrass.GrassType.class);

        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public IBlockState getFullBlock(IBlockState state)
    {
        return NaturaWorld.coloredGrass.getDefaultState().withProperty(BlockColoredGrass.TYPE, state.getValue(TYPE));
    }
}
