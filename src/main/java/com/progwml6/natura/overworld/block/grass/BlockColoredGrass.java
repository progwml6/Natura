package com.progwml6.natura.overworld.block.grass;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
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

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

        if (plantType == EnumPlantType.Plains)
        {
            return true;
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
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
