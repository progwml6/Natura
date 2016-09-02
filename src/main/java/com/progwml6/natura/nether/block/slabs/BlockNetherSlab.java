package com.progwml6.natura.nether.block.slabs;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;
import slimeknights.mantle.block.EnumBlockSlab;

public class BlockNetherSlab extends EnumBlockSlab<BlockNetherSlab.PlankType>
{
    public final static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public BlockNetherSlab()
    {
        super(Material.WOOD, TYPE, PlankType.class);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public IBlockState getFullBlock(IBlockState state)
    {
        if (NaturaNether.netherPlanks == null)
        {
            return null;
        }
        return NaturaNether.netherPlanks.getDefaultState().withProperty(BlockNetherPlanks.TYPE, state.getValue(TYPE).asFullBlock());
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

        public BlockNetherPlanks.PlankType asFullBlock()
        {
            switch (this)
            {
            case GHOSTWOOD:
                return BlockNetherPlanks.PlankType.GHOSTWOOD;
            case BLOODWOOD:
                return BlockNetherPlanks.PlankType.BLOODWOOD;
            case DARKWOOD:
                return BlockNetherPlanks.PlankType.DARKWOOD;
            case FUSEWOOD:
                return BlockNetherPlanks.PlankType.FUSEWOOD;
            default:
                return null;
            }
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
