package com.progwml6.natura.nether.block.planks;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherPlanks extends EnumBlock<BlockNetherPlanks.PlankType>
{
    public static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public BlockNetherPlanks()
    {
        super(Material.WOOD, TYPE, PlankType.class);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setHardness(2.0f);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }

    public enum PlankType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GHOSTWOOD, BLOODWOOD, DARKWOOD, FUSEWOOD;

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
