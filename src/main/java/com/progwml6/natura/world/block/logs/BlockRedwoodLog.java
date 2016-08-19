package com.progwml6.natura.world.block.logs;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import slimeknights.mantle.block.EnumBlock;

public class BlockRedwoodLog extends EnumBlock<BlockRedwoodLog.RedwoodType>
{
    public final static PropertyEnum<RedwoodType> TYPE = PropertyEnum.create("type", RedwoodType.class);

    public BlockRedwoodLog()
    {
        super(Material.WOOD, TYPE, RedwoodType.class);
        Blocks.FIRE.setFireInfo(this, 5, 20);
        this.setHardness(2.0f);
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setSoundType(SoundType.WOOD);
    }

    //@formatter:off
    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    //@formatter:on

    public enum RedwoodType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        BARK, HEART, ROOT;

        public final int meta;

        RedwoodType()
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
