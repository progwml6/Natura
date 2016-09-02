package com.progwml6.natura.overworld.block.grass;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockColoredGrass extends EnumBlock<BlockColoredGrass.GrassType>
{
    public static PropertyEnum<GrassType> TYPE = PropertyEnum.create("type", GrassType.class);

    public BlockColoredGrass()
    {
        super(Material.GRASS, TYPE, GrassType.class);

        this.setCreativeTab(NaturaRegistry.tabWorld);
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
