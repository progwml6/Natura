package com.progwml6.natura.shared.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.mantle.item.GeneratedItem;

public class BoneBagItem extends GeneratedItem {

  public BoneBagItem(ItemGroup itemGroup) {
    super(itemGroup);
  }

  /**
   * Called when this item is used when targetting a Block
   */
  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    World world = context.getWorld();
    BlockPos blockpos = context.getPos();
    Direction direction = context.getFace();
    Hand hand = context.getHand();
    PlayerEntity player = context.getPlayer();

    if (direction != Direction.UP) {
      return ActionResultType.FAIL;
    }
    else {
      ItemStack itemstack = player.getHeldItem(hand);

      BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

      int posY = blockpos.getY();

      boolean planted = false;

      for (int posX = blockpos.getX() - 1; posX <= blockpos.getX() + 1; posX++) {
        for (int posZ = blockpos.getZ() - 1; posZ <= blockpos.getZ() + 1; posZ++) {
          BlockPos position = mutableblockpos.setPos(posX, posY, posZ);

          if (player.canPlayerEdit(position, direction, itemstack) && player.canPlayerEdit(position.up(), direction, itemstack)) {
            if (applyBonemeal(itemstack, world, position, player)) {
              planted = true;

              if (!world.isRemote) {
                world.playEvent(2005, position, 0);
              }
            }
          }
        }
      }

      if (planted) {
        if (!player.abilities.isCreativeMode) {
          itemstack.shrink(1);
        }

        if (itemstack.getCount() < 1) {
          ForgeEventFactory.onPlayerDestroyItem(player, itemstack, hand);

          player.setHeldItem(hand, ItemStack.EMPTY);
        }

        return ActionResultType.SUCCESS;
      }

      return ActionResultType.PASS;
    }
  }

  private static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, PlayerEntity player) {
    BlockState iblockstate = worldIn.getBlockState(target);

    BonemealEvent event = new BonemealEvent(player, worldIn, target, iblockstate, stack);

    if (MinecraftForge.EVENT_BUS.post(event)) {
      return false;
    }

    if (event.getResult() == Event.Result.ALLOW) {
      return true;
    }

    if (iblockstate.getBlock() instanceof IGrowable) {
      IGrowable igrowable = (IGrowable) iblockstate.getBlock();

      if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
        if (!worldIn.isRemote) {
          if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
            igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
          }
        }

        return true;
      }
    }

    return false;
  }
}
