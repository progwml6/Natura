package com.progwml6.natura.shared.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.mantle.item.TooltipItem;

import javax.annotation.Nullable;
import java.util.Random;


public class BoneMealBagItem extends TooltipItem {

  public BoneMealBagItem(Properties properties) {
    super(properties);
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

        if (applyBonemeal(pContext.getItemInHand(), level, position, pContext.getPlayer())) {
          if (!level.isClientSide) {
            level.levelEvent(1505, position, 0);
          }

          planted = true;
        } else {
          BlockState blockstate = level.getBlockState(position);
          boolean flag = blockstate.isFaceSturdy(level, position, pContext.getClickedFace());
          if (flag && growWaterPlant(pContext.getItemInHand(), level, position1, pContext.getClickedFace())) {
            if (!level.isClientSide) {
              level.levelEvent(1505, position1, 0);
            }

            planted = true;
          } else {
            return InteractionResult.PASS;
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

  @Deprecated //Forge: Use Player/Hand version
  public static boolean growCrop(ItemStack pStack, Level pLevel, BlockPos pPos) {
    if (pLevel instanceof net.minecraft.server.level.ServerLevel)
      return applyBonemeal(pStack, pLevel, pPos, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.server.level.ServerLevel) pLevel));
    return false;
  }

  public static boolean applyBonemeal(ItemStack pStack, Level pLevel, BlockPos pPos, net.minecraft.world.entity.player.Player player) {
    BlockState blockstate = pLevel.getBlockState(pPos);
    int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, pLevel, pPos, blockstate, pStack);
    if (hook != 0) return hook > 0;

    if (blockstate.getBlock() instanceof BonemealableBlock) {
      BonemealableBlock bonemealableblock = (BonemealableBlock) blockstate.getBlock();

      if (bonemealableblock.isValidBonemealTarget(pLevel, pPos, blockstate, pLevel.isClientSide)) {
        if (pLevel instanceof ServerLevel) {
          if (bonemealableblock.isBonemealSuccess(pLevel, pLevel.random, pPos, blockstate)) {
            bonemealableblock.performBonemeal((ServerLevel) pLevel, pLevel.random, pPos, blockstate);
          }
        }

        return true;
      }
    }

    return false;
  }

  public static boolean growWaterPlant(ItemStack pStack, Level pLevel, BlockPos pPos, @Nullable Direction pClickedSide) {
    if (pLevel.getBlockState(pPos).is(Blocks.WATER) && pLevel.getFluidState(pPos).getAmount() == 8) {
      if (!(pLevel instanceof ServerLevel)) {
        return true;
      } else {
        Random random = pLevel.getRandom();

        label78:
        for (int i = 0; i < 128; ++i) {
          BlockPos blockpos = pPos;
          BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

          for (int j = 0; j < i / 16; ++j) {
            blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
            if (pLevel.getBlockState(blockpos).isCollisionShapeFullBlock(pLevel, blockpos)) {
              continue label78;
            }
          }

          Holder<Biome> holder = pLevel.getBiome(blockpos);
          if (holder.is(Biomes.WARM_OCEAN)) {
            if (i == 0 && pClickedSide != null && pClickedSide.getAxis().isHorizontal()) {
              blockstate = Registry.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap((named) -> named.getRandomElement(pLevel.random)).map((blockHolder) -> blockHolder.value().defaultBlockState()).orElse(blockstate);
              if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, pClickedSide);
              }
            } else if (random.nextInt(4) == 0) {
              blockstate = Registry.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap((p_204091_) -> {
                return p_204091_.getRandomElement(pLevel.random);
              }).map((p_204095_) -> {
                return p_204095_.value().defaultBlockState();
              }).orElse(blockstate);
            }
          }

          if (blockstate.is(BlockTags.WALL_CORALS, (blockStateBase) -> blockStateBase.hasProperty(BaseCoralWallFanBlock.FACING))) {
            for (int k = 0; !blockstate.canSurvive(pLevel, blockpos) && k < 4; ++k) {
              blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
            }
          }

          if (blockstate.canSurvive(pLevel, blockpos)) {
            BlockState blockstate1 = pLevel.getBlockState(blockpos);
            if (blockstate1.is(Blocks.WATER) && pLevel.getFluidState(blockpos).getAmount() == 8) {
              pLevel.setBlock(blockpos, blockstate, 3);
            } else if (blockstate1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
              ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal((ServerLevel) pLevel, random, blockpos, blockstate1);
            }
          }
        }

        return true;
      }
    } else {
      return false;
    }
  }

}
