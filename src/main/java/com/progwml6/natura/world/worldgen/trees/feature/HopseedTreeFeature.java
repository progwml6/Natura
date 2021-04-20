package com.progwml6.natura.world.worldgen.trees.feature;

import com.mojang.serialization.Codec;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import java.util.Random;
import java.util.Set;

public class HopseedTreeFeature extends GenericOverworldTreeFeature {

  public HopseedTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public void place(IWorldGenerationReader generationReader, Random rand, int height, BlockPos position, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    setDirt(generationReader, position.down());
    setDirt(generationReader, position.down().east());
    setDirt(generationReader, position.down().south());
    setDirt(generationReader, position.down().south().east());

    this.placeTrunk(generationReader, rand, position, logPositions, boundingBoxIn, configIn);

    this.placeCanopy(generationReader, rand, height, position, foliagePositions, boundingBoxIn, configIn);
  }

  protected void placeTrunk(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> logPositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    addLogWithCheck(worldIn, randomIn, blockPos, logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(+1, 0, 0), logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(0, 0, +1), logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(+1, 0, +1), logPositions, boundingBoxIn, configIn);

    addLogWithCheck(worldIn, randomIn, blockPos.add(0, +1, 0), logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(+1, +1, 0), logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(0, +1, +1), logPositions, boundingBoxIn, configIn);
    addLogWithCheck(worldIn, randomIn, blockPos.add(+1, +1, +1), logPositions, boundingBoxIn, configIn);
  }

  protected void placeCanopy(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    for (int y = blockPos.getY() - 2 + treeHeight; y <= blockPos.getY() + treeHeight; ++y) {
      int subtract = y - (blockPos.getY() + treeHeight);
      int subtract2 = 2 + 1 - subtract;

      for (int x = blockPos.getX() - subtract2; x <= blockPos.getX() + subtract2; ++x) {
        int newX = x - blockPos.getX();

        for (int z = blockPos.getZ() - subtract2; z <= blockPos.getZ() + subtract2; ++z) {
          int newZ = z - blockPos.getZ();

          if ((newX >= 0 || newZ >= 0 || newX * newX + newZ * newZ <= subtract2 * subtract2) && (newX <= 0 && newZ <= 0
            || newX * newX + newZ * newZ <= (subtract2 + 1) * (subtract2 + 1)) && (randomIn.nextInt(4) != 0
            || newX * newX + newZ * newZ <= (subtract2 - 1) * (subtract2 - 1))) {
            addLeafWithCheck(worldIn, randomIn, new BlockPos(x, y, z), foliagePositions, boundingBoxIn, configIn);
          }
        }
      }
    }
  }
}
