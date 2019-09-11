package com.progwml6.natura.shared.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.item.GeneratedItem;

public class SeedBagItem extends GeneratedItem {

  private final BlockState cropState;

  public SeedBagItem(BlockState cropState, ItemGroup itemGroup) {
    super(itemGroup);

    this.cropState = cropState;
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
      ItemStack itemStack = player.getHeldItem(hand);

      BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

      int posY = blockpos.getY();

      boolean planted = false;

      BlockPos position = blockpos;

      for (int posX = blockpos.getX() - 1; posX <= blockpos.getX() + 1; posX++) {
        for (int posZ = blockpos.getZ() - 1; posZ <= blockpos.getZ() + 1; posZ++) {
          position = mutableblockpos.setPos(posX, posY, posZ);

          if (player.canPlayerEdit(position, direction, itemStack) && player.canPlayerEdit(position.up(), direction, itemStack)) {
            BlockState state = world.getBlockState(position);
            Block block = state.getBlock();

            if (block != null && block.canSustainPlant(state, world, position, Direction.UP, (IPlantable) this.cropState.getBlock()) && world.isAirBlock(position.up())) {
              planted = true;

              world.setBlockState(position.up(), this.cropState, 3);
            }
          }
        }
      }

      if (planted) {
        if (!player.abilities.isCreativeMode) {
          itemStack.shrink(1);
        }

        if (itemStack.getCount() < 1) {
          world.playEvent(player, 2001, blockpos, Block.getStateId(this.cropState));
        }

        return ActionResultType.SUCCESS;
      }
      else {
        return ActionResultType.PASS;
      }
    }
  }
}
