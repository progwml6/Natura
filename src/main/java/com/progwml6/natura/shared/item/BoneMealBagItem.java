package com.progwml6.natura.shared.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import slimeknights.mantle.item.TooltipItem;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class BoneMealBagItem extends TooltipItem {

  public BoneMealBagItem(Properties properties) {
    super(properties);
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

        if (applyBonemeal(context.getItem(), world, position, context.getPlayer())) {
          if (!world.isRemote) {
            world.playEvent(2005, position, 0);
          }

          planted = true;
        } else {
          BlockState blockstate = world.getBlockState(position);
          boolean flag = blockstate.isSolidSide(world, position, context.getFace());
          if (flag && growSeagrass(context.getItem(), world, position1, context.getFace())) {
            if (!world.isRemote) {
              world.playEvent(2005, position1, 0);
            }

            planted = true;
          } else {
            return ActionResultType.PASS;
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

  public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity player) {
    BlockState blockstate = worldIn.getBlockState(pos);

    int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
    if (hook != 0) return hook > 0;

    if (blockstate.getBlock() instanceof IGrowable) {
      IGrowable igrowable = (IGrowable) blockstate.getBlock();
      if (igrowable.canGrow(worldIn, pos, blockstate, worldIn.isRemote)) {
        if (worldIn instanceof ServerWorld) {
          if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, blockstate)) {
            igrowable.grow((ServerWorld) worldIn, worldIn.rand, pos, blockstate);
          }
        }

        return true;
      }
    }

    return false;
  }

  public static boolean growSeagrass(ItemStack stack, World worldIn, BlockPos pos, @Nullable Direction side) {
    if (worldIn.getBlockState(pos).isIn(Blocks.WATER) && worldIn.getFluidState(pos).getLevel() == 8) {
      if (!(worldIn instanceof ServerWorld)) {
        return true;
      } else {
        label80:
        for (int i = 0; i < 128; ++i) {
          BlockPos blockpos = pos;
          BlockState blockstate = Blocks.SEAGRASS.getDefaultState();

          for (int j = 0; j < i / 16; ++j) {
            blockpos = blockpos.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
            if (worldIn.getBlockState(blockpos).hasOpaqueCollisionShape(worldIn, blockpos)) {
              continue label80;
            }
          }

          Optional<RegistryKey<Biome>> optional = worldIn.func_242406_i(blockpos);
          if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
            if (i == 0 && side != null && side.getAxis().isHorizontal()) {
              blockstate = BlockTags.WALL_CORALS.getRandomElement(worldIn.rand).getDefaultState().with(DeadCoralWallFanBlock.FACING, side);
            } else if (random.nextInt(4) == 0) {
              blockstate = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).getDefaultState();
            }
          }

          if (blockstate.getBlock().isIn(BlockTags.WALL_CORALS)) {
            for (int k = 0; !blockstate.isValidPosition(worldIn, blockpos) && k < 4; ++k) {
              blockstate = blockstate.with(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.random(random));
            }
          }

          if (blockstate.isValidPosition(worldIn, blockpos)) {
            BlockState blockstate1 = worldIn.getBlockState(blockpos);
            if (blockstate1.isIn(Blocks.WATER) && worldIn.getFluidState(blockpos).getLevel() == 8) {
              worldIn.setBlockState(blockpos, blockstate, 3);
            } else if (blockstate1.isIn(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
              ((IGrowable) Blocks.SEAGRASS).grow((ServerWorld) worldIn, random, blockpos, blockstate1);
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
