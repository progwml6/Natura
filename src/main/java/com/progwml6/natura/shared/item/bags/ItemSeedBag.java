package com.progwml6.natura.shared.item.bags;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.item.ItemMetaDynamic;

public class ItemSeedBag extends ItemMetaDynamic
{
    protected TIntObjectHashMap<IBlockState> states = new TIntObjectHashMap<>();

    public ItemStack addMeta(int meta, String name, IBlockState state)
    {
        states.put(meta, state);
        ItemStack ret = addMeta(meta, name);
        return ret;
    }

    @Override
    public ItemStack addMeta(int meta, String name)
    {
        if (!states.containsKey(meta))
        {
            throw new RuntimeException("Usage of wrong function. Use the addMeta function that has an amount paired with it with this implementation");
        }
        return super.addMeta(meta, name);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing != EnumFacing.UP)//!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            int meta = stack.getMetadata();

            if (isValid(meta))
            {
                MutableBlockPos mutableblockpos = new MutableBlockPos();

                int posY = pos.getY();

                boolean planted = false;

                BlockPos position = pos;

                for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++)
                {
                    for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++)
                    {
                        position = mutableblockpos.setPos(posX, posY, posZ);

                        if (playerIn.canPlayerEdit(position, facing, stack) && playerIn.canPlayerEdit(position.up(), facing, stack))
                        {
                            IBlockState state = worldIn.getBlockState(position);
                            Block block = state.getBlock();

                            if (block != null && block.canSustainPlant(state, worldIn, position, EnumFacing.UP, (IPlantable) states.get(meta).getBlock()) && worldIn.isAirBlock(position.up()))
                            {
                                planted = true;

                                worldIn.setBlockState(position.up(), states.get(meta), 3);
                            }
                        }
                    }
                }

                if (planted)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        stack.stackSize--;
                    }

                    if (stack.stackSize < 1)
                    {
                        worldIn.playEvent(2001, position, Block.getIdFromBlock(states.get(meta).getBlock()));
                    }

                    return EnumActionResult.SUCCESS;
                }

                return EnumActionResult.PASS;
            }
            else
            {
                return EnumActionResult.PASS;
            }
        }
    }
}
