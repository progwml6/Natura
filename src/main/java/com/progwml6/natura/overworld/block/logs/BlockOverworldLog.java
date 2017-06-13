package com.progwml6.natura.overworld.block.logs;

import java.util.Locale;

import com.progwml6.natura.common.block.BlockEnumLog;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldLog extends BlockEnumLog<BlockOverworldLog.LogType>
{
    public static PropertyEnum<BlockOverworldLog.LogType> TYPE = PropertyEnum.create("type", BlockOverworldLog.LogType.class);

    public BlockOverworldLog()
    {
        super(TYPE, BlockOverworldLog.LogType.class);

        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Y));
        this.setHarvestLevel("axe", -1);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, this.fromMeta((meta & 3)));

        switch (meta & 12)
        {
        case 0:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Y);
            break;
        case 4:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.X);
            break;
        case 8:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Z);
            break;
        default:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | (state.getValue(TYPE)).getMeta();

        switch (state.getValue(LOG_AXIS))
        {
        case X:
            i |= 4;
            break;
        case Z:
            i |= 8;
            break;
        case NONE:
            i |= 12;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { TYPE, LOG_AXIS });
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(TYPE).getMeta());
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata
     * of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getMeta();
    }

    public enum LogType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        MAPLE, SILVERBELL, AMARANTH, TIGER;

        public final int meta;

        LogType()
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
