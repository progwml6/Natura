package com.progwml6.natura.world.client;

import com.progwml6.natura.world.block.TreeType;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;

public class LeavesColorizer {

  public static int mapleColor = 0xcc5718;
  public static int amaranthColor = 10079569;
  public static int noColor = 16777215;
  public static int leaves2Color = 7842607;
  public static int leavesColor = 0xffffff;

  public static int getColorStatic(TreeType type) {
    return switch (type) {
      case MAPLE -> LeavesColorizer.mapleColor;
      case AMARANTH -> LeavesColorizer.amaranthColor;
      case EUCALYPTUS, HOPSEED -> LeavesColorizer.leaves2Color;
      case WILLOW, SAKURA -> LeavesColorizer.noColor;
      default -> LeavesColorizer.leavesColor;
    };
  }

  public static int getColorForPos(BlockAndTintGetter worldIn, BlockPos pos, TreeType type) {
    return switch (type) {
      case MAPLE -> LeavesColorizer.mapleColor;
      case AMARANTH -> BiomeColors.getAverageFoliageColor(worldIn, pos) + 0x222222;
      case EUCALYPTUS, HOPSEED -> BiomeColors.getAverageFoliageColor(worldIn, pos);
      case WILLOW, SAKURA -> LeavesColorizer.noColor;
      default -> LeavesColorizer.leavesColor;
    };
  }
}
