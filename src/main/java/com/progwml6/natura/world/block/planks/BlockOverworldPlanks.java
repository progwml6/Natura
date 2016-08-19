package com.progwml6.natura.world.block.planks;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldPlanks extends EnumBlock<BlockOverworldPlanks.PlankType>
{
    public static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public BlockOverworldPlanks()
    {
        super(Material.WOOD, TYPE, PlankType.class);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setHardness(2.0f);
        this.setSoundType(SoundType.WOOD);
    }

    public enum PlankType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        MAPLE, SILVERBELL, AMARANTHA, TIGER, WILLOW, EUCALYPTUS, HOPSEED, SAKURA, REDWOOD;

        public final int meta;

        PlankType()
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
