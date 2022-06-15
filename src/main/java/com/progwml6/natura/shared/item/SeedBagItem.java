package com.progwml6.natura.shared.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
  public InteractionResult useOn(UseOnContext pContext) {
    Level level = pContext.getLevel();
    BlockPos blockPos = pContext.getClickedPos();

    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
    boolean planted = false;

    for (int posX = blockPos.getX() - 1; posX <= blockPos.getX() + 1; posX++) {
      for (int posZ = blockPos.getZ() - 1; posZ <= blockPos.getZ() + 1; posZ++) {
        BlockPos position = mutable.set(posX, blockPos.getY(), posZ);
        BlockPos position1 = position.relative(pContext.getClickedFace());

        if (pContext.getPlayer().mayUseItemAt(position1, pContext.getClickedFace(), pContext.getItemInHand())) {
          BlockState state = level.getBlockState(position);
          Block block = state.getBlock();

          if (block.canSustainPlant(state, level, position, Direction.UP, (IPlantable) this.supplier.get().getBlock()) && level.isEmptyBlock(position.above())) {
            planted = true;

            level.setBlock(position.above(), this.supplier.get(), 3);

            if (!level.isClientSide) {
              level.levelEvent(2001, position, Block.getId(this.supplier.get()));
            }
          }
        }
      }
    }

    if (planted) {
      pContext.getItemInHand().shrink(1);
      return InteractionResult.sidedSuccess(level.isClientSide);
    }

    return InteractionResult.PASS;
  }
}
