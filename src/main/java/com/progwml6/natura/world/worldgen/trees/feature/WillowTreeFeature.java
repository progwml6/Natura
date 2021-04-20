package com.progwml6.natura.world.worldgen.trees.feature;

import com.mojang.serialization.Codec;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;

import java.util.Random;
import java.util.Set;

public class WillowTreeFeature extends GenericOverworldTreeFeature {

  public WillowTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public void place(IWorldGenerationReader generationReader, Random rand, int height, BlockPos position, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    setDirt(generationReader, position.down());

    this.placeTrunk(generationReader, rand, height, position, logPositions, boundingBoxIn, configIn);

    this.placeCanopy(generationReader, rand, height, position, foliagePositions, boundingBoxIn, configIn);
  }

  protected void placeTrunk(IWorldGenerationReader worldIn, Random rand, int treeHeight, BlockPos blockPos, Set<BlockPos> logPositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    while (treeHeight > 0) {
      addLogWithCheck(worldIn, rand, blockPos, logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);

      blockPos = blockPos.up();
      treeHeight--;
    }
  }

  protected void placeCanopy(IWorldGenerationReader worldIn, Random rand, int treeHeight, BlockPos blockPos, Set<BlockPos> foliagePositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    for (int k1 = blockPos.getY() - 3 + treeHeight; k1 <= blockPos.getY() + treeHeight; ++k1) {
      int j2 = k1 - (blockPos.getY() + treeHeight);
      int l2 = 2 - j2 / 2;

      for (int j3 = blockPos.getX() - l2; j3 <= blockPos.getX() + l2; ++j3) {
        int k3 = j3 - blockPos.getX();

        for (int i4 = blockPos.getZ() - l2; i4 <= blockPos.getZ() + l2; ++i4) {
          int j1 = i4 - blockPos.getZ();

          if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || rand.nextInt(2) != 0 && j2 != 0) {
            addLeafWithCheck(worldIn, rand, new BlockPos(j3, k1, i4), foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn);
          }
        }
      }
    }

    for (int i2 = blockPos.getY() - 3 + treeHeight; i2 <= blockPos.getY() + treeHeight; ++i2) {
      int k2 = i2 - (blockPos.getY() + treeHeight);
      int i3 = 2 - k2 / 2;
      BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

      for (int l3 = blockPos.getX() - i3; l3 <= blockPos.getX() + i3; ++l3) {
        for (int j4 = blockPos.getZ() - i3; j4 <= blockPos.getZ() + i3; ++j4) {
          mutableBlockPos.setPos(l3, i2, j4);

          if (worldIn.hasBlockState(mutableBlockPos, (state -> state.getMaterial() == Material.LEAVES))) {
            BlockPos west = mutableBlockPos.west();
            BlockPos east = mutableBlockPos.east();
            BlockPos north = mutableBlockPos.north();
            BlockPos south = mutableBlockPos.south();

            placeDownLeaves(worldIn, rand, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn, west, east);
            placeDownLeaves(worldIn, rand, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn, north, south);
          }
        }
      }
    }
  }

  private void placeDownLeaves(IWorldGenerationReader worldIn, Random rand, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig config, BlockPos west, BlockPos east) {
    if (rand.nextInt(4) == 0 && isAirAt(worldIn, west)) {
      this.placeDownLeaves(worldIn, rand, west, foliagePositions, boundingBoxIn, config);
    }

    if (rand.nextInt(4) == 0 && isAirAt(worldIn, east)) {
      this.placeDownLeaves(worldIn, rand, east, foliagePositions, boundingBoxIn, config);
    }
  }

  private void placeDownLeaves(IWorldGenerationReader worldIn, Random rand, BlockPos blockPos, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig config) {
    addLeafWithCheck(worldIn, rand, blockPos, foliagePositions, boundingBoxIn, config);
    int i = 4;

    for (blockPos = blockPos.down(); isAirAt(worldIn, blockPos) && i > 0; --i) {
      addLeafWithCheck(worldIn, rand, blockPos, foliagePositions, boundingBoxIn, config);
      blockPos = blockPos.down();
    }
  }
}
