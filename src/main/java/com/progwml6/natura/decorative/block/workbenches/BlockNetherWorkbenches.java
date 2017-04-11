package com.progwml6.natura.decorative.block.workbenches;

import java.util.Locale;

import javax.annotation.Nullable;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.GuiIDs;
import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherWorkbenches extends EnumBlock<BlockNetherWorkbenches.PlankType>
{
    public static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public BlockNetherWorkbenches()
    {
        super(Material.WOOD, TYPE, PlankType.class);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(2.5F);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            playerIn.openGui(Natura.instance, GuiIDs.CRAFTING_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
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
