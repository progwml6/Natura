package com.progwml6.natura.shared.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.item.TooltipItem;

import javax.annotation.Nullable;
import java.util.List;

public class SeedBagItem extends Item {

  private final BlockState placedState;

  public SeedBagItem(Properties properties, BlockState state) {
    super(properties);

    this.placedState = state;
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

      BlockPos position = pos;

      for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++) {
        for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++) {
          position = mutable.setPos(posX, posY, posZ);

          if (player.canPlayerEdit(position, facing, stack) && player.canPlayerEdit(position.up(), facing, stack)) {
            BlockState blockState = world.getBlockState(position);
            Block block = blockState.getBlock();

            if (block.canSustainPlant(blockState, world, position, Direction.UP, (IPlantable) this.placedState.getBlock()) && world.isAirBlock(position.up())) {
              planted = true;

              world.setBlockState(position.up(), this.placedState, 3);
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

          world.playEvent(2001, position, Block.getStateId(this.placedState));
        }

        return ActionResultType.SUCCESS;
      }
    }

    return ActionResultType.PASS;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    TooltipItem.addOptionalTooltip(stack, tooltip);
  }
}
