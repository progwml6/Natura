package com.progwml6.natura.shared.item.bags;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ItemBoneBag extends Item
{
    public ItemBoneBag()
    {
        super();
        this.setCreativeTab(NaturaRegistry.tabGeneral);
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
            MutableBlockPos mutableblockpos = new MutableBlockPos();

            int posY = pos.getY();

            boolean planted = false;

            for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++)
            {
                for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++)
                {
                    BlockPos position = mutableblockpos.setPos(posX, posY, posZ);

                    if (playerIn.canPlayerEdit(position, facing, stack) && playerIn.canPlayerEdit(position.up(), facing, stack))
                    {
                        if (applyBonemeal(stack, worldIn, position, playerIn))
                        {
                            planted = true;
                            if (!worldIn.isRemote)
                            {
                                worldIn.playEvent(2005, position, 0);
                            }
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
                    ForgeEventFactory.onPlayerDestroyItem(playerIn, stack, hand);

                    playerIn.setHeldItem(hand, null);
                }

                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.PASS;
        }
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player)
    {
        IBlockState iblockstate = worldIn.getBlockState(target);

        BonemealEvent event = new BonemealEvent(player, worldIn, target, iblockstate);
        if (MinecraftForge.EVENT_BUS.post(event))
            return false;
        if (event.getResult() == Result.ALLOW)
            return true;

        if (iblockstate.getBlock() instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
            {
                if (!worldIn.isRemote)
                {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
                    {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }
}
