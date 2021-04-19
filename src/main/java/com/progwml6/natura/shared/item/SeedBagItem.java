package com.progwml6.natura.shared.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.item.TooltipItem;

import java.util.function.Supplier;

public class SeedBagItem extends TooltipItem {

  private final Supplier<BlockState> supplier;

  public SeedBagItem(Properties properties, Supplier<BlockState> supplier) {
    super(properties);
    this.supplier = supplier;
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    World world = context.getWorld();
    BlockPos blockPos = context.getPos();

    BlockPos.Mutable mutable = new BlockPos.Mutable();
    boolean planted = false;

    for (int posX = blockPos.getX() - 1; posX <= blockPos.getX() + 1; posX++) {
      for (int posZ = blockPos.getZ() - 1; posZ <= blockPos.getZ() + 1; posZ++) {
        BlockPos position = mutable.setPos(posX, blockPos.getY(), posZ);
        BlockPos position1 = position.offset(context.getFace());

        if (context.getPlayer().canPlayerEdit(position1, context.getFace(), context.getItem())) {
          BlockState state = world.getBlockState(position);
          Block block = state.getBlock();

          if (block.canSustainPlant(state, world, position, Direction.UP, (IPlantable) this.supplier.get().getBlock()) && world.isAirBlock(position.up())) {
            planted = true;

            world.setBlockState(position.up(), this.supplier.get(), 3);

            if (!world.isRemote) {
              world.playEvent(2001, position, Block.getStateId(this.supplier.get()));
            }
          }
        }
      }
    }

    if (planted) {
      context.getItem().shrink(1);
      return ActionResultType.func_233537_a_(world.isRemote);
    }

    return ActionResultType.PASS;
  }
}
