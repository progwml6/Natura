package com.progwml6.natura.decorative.block.workbenches;

import javax.annotation.Nullable;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.GuiIDs;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldWorkbenches extends EnumBlock<BlockOverworldPlanks.PlankType>
{
    public static PropertyEnum<BlockOverworldPlanks.PlankType> TYPE = PropertyEnum.create("type", BlockOverworldPlanks.PlankType.class);

    public BlockOverworldWorkbenches()
    {
        super(Material.WOOD, TYPE, BlockOverworldPlanks.PlankType.class);

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
}
