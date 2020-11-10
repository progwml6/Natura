package com.progwml6.natura.overworld.client;

import com.progwml6.natura.overworld.block.TreeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;

public class LeavesColorizer {

  public static int mapleColor = 0xcc5718;
  public static int amaranthColor = 10079569;
  public static int noColor = 16777215;
  public static int leaves2Color = 7842607;
  public static int leavesColor = 0xffffff;

  public static int getColorStatic(TreeType type) {
    switch (type) {
      case MAPLE:
        return LeavesColorizer.mapleColor;
      case AMARANTH:
        return LeavesColorizer.amaranthColor;
      case SILVERBELL:
      case TIGER:
        return LeavesColorizer.leavesColor;
      case EUCALYPTUS:
      case HOPSEED:
        return LeavesColorizer.leaves2Color;
      case WILLOW:
      case SAKURA:
        return LeavesColorizer.noColor;
      default:
        return LeavesColorizer.leavesColor;
    }
  }

  public static int getColorForPos(IBlockDisplayReader worldIn, BlockPos pos, TreeType type) {
    switch (type) {
      case MAPLE:
        return LeavesColorizer.mapleColor;
      case AMARANTH:
        return BiomeColors.getFoliageColor(worldIn, pos) + 0x222222;
      case SILVERBELL:
      case TIGER:
        return LeavesColorizer.leavesColor;
      case EUCALYPTUS:
      case HOPSEED:
        return BiomeColors.getFoliageColor(worldIn, pos);
      case WILLOW:
      case SAKURA:
        return LeavesColorizer.noColor;
      default:
        return LeavesColorizer.leavesColor;
    }
  }
}
