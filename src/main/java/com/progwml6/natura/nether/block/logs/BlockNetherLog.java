package com.progwml6.natura.nether.block.logs;

import java.util.Locale;

import com.progwml6.natura.common.block.BlockEnumLog;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherLog extends BlockEnumLog<BlockNetherLog.LogType>
{
    public static PropertyEnum<BlockNetherLog.LogType> TYPE = PropertyEnum.create("type", BlockNetherLog.LogType.class);

    public BlockNetherLog()
    {
        super(TYPE, BlockNetherLog.LogType.class);

        this.setHardness(3.5F);
        this.setResistance(40F);
        Blocks.FIRE.setFireInfo(this, 0, 0);

        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Y));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (state.getValue(BlockNetherLog.TYPE) == BlockNetherLog.LogType.FUSEWOOD)
        {
            if (worldIn.getDifficulty() == EnumDifficulty.HARD)
            {
                worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, false);
            }
            else if (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.EASY)
            {
                worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1.75f, false);
            }
            else if (worldIn.getDifficulty() == EnumDifficulty.PEACEFUL)
            {
            }
        }
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
        GHOSTWOOD, DARKWOOD, FUSEWOOD;

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
