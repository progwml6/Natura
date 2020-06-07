package com.progwml6.natura.shared.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;

public class BoneMealBagItem extends Item {

  public BoneMealBagItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    BlockPos pos = context.getPos();
    Direction facing = context.getFace();
    World world = context.getWorld();
    PlayerEntity player = context.getPlayer();
    Hand hand = context.getHand();
    ItemStack stack = context.getItem();

    if (player == null) {
      return ActionResultType.FAIL;
    }

    if (facing != Direction.UP) {
      return ActionResultType.FAIL;
    }
    else {
      BlockPos.Mutable mutable = new BlockPos.Mutable();

      int posY = pos.getY();

      boolean planted = false;

      for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++) {
        for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++) {
          BlockPos position = mutable.setPos(posX, posY, posZ);

          if (player.canPlayerEdit(position, facing, stack) && player.canPlayerEdit(position.up(), facing, stack)) {
            if (applyBoneMeal(stack, world, position, player)) {
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
          stack.shrink(1);
        }

        if (stack.getCount() < 1) {
          net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);

          player.setHeldItem(hand, ItemStack.EMPTY);
        }

        return ActionResultType.SUCCESS;
      }

      return ActionResultType.PASS;
    }
  }

  public static boolean applyBoneMeal(ItemStack itemStack, World worldIn, BlockPos target, PlayerEntity playerEntity) {
    BlockState blockState = worldIn.getBlockState(target);

    BonemealEvent event = new BonemealEvent(playerEntity, worldIn, target, blockState, itemStack);

    if (MinecraftForge.EVENT_BUS.post(event)) {
      return false;
    }

    if (event.getResult() == Event.Result.ALLOW) {
      return true;
    }

    if (blockState.getBlock() instanceof IGrowable) {
      IGrowable growable = (IGrowable) blockState.getBlock();

      if (growable.canGrow(worldIn, target, blockState, worldIn.isRemote)) {
        if (worldIn instanceof ServerWorld) {
          if (growable.canUseBonemeal(worldIn, worldIn.rand, target, blockState)) {
            growable.grow((ServerWorld) worldIn, worldIn.rand, target, blockState);
          }
        }

        return true;
      }
    }

    return false;
  }
}
