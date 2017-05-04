package com.progwml6.natura.overworld.item;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.item.ItemMetaDynamic;

public class ItemSeeds extends ItemMetaDynamic implements IPlantable
{
    protected TIntObjectHashMap<IBlockState> states = new TIntObjectHashMap<>();

    private static IBlockState crop;

    public ItemStack addMeta(int meta, String name, IBlockState state)
    {
        this.states.put(meta, state);

        crop = state;

        ItemStack ret = this.addMeta(meta, name);

        return ret;
    }

    @Override
    public ItemStack addMeta(int meta, String name)
    {
        if (!this.states.containsKey(meta))
        {
            throw new RuntimeException("Usage of wrong function. Use the addMeta function that has an amount paired with it with this implementation");
        }

        return super.addMeta(meta, name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            ItemStack itemstack = player.getHeldItem(hand);

            int meta = itemstack.getMetadata();

            if (this.isValid(meta))
            {
                if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(pos.up(), facing, itemstack))
                {
                    IBlockState state = worldIn.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block != null && block.canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
                    {
                        worldIn.setBlockState(pos.up(), this.states.get(meta));
                        itemstack.shrink(1);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }

            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return crop;
    }
}
