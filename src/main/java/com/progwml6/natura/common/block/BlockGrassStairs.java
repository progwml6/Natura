package com.progwml6.natura.common.block;

import java.util.Locale;

import com.progwml6.natura.common.block.base.BlockNaturaStairsBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockGrassStairs extends BlockNaturaStairsBase
{
    public final IBlockState customModelState;

    public BlockGrassStairs(IBlockState modelState)
    {
        super(modelState);
        this.customModelState = modelState;
        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
    }

    public enum GrassType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        TOPIARY, BLUEGRASS, AUTUMNAL;

        public final int meta;

        GrassType()
        {
            this.meta = this.ordinal();
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
