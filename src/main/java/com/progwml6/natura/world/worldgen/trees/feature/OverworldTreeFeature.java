package com.progwml6.natura.world.worldgen.trees.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

public class OverworldTreeFeature extends Feature<BaseTreeFeatureConfig> {

  public OverworldTreeFeature(Codec<BaseTreeFeatureConfig> configCodec) {
    super(configCodec);
  }

  /**
   * Called when placing the tree feature.
   */
  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> blockPosSet, Set<BlockPos> blockPosSet2, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn) {
    int i = configIn.trunkPlacer.getHeight(rand);
    int j = configIn.foliagePlacer.func_230374_a_(rand, i, configIn);
    int k = i - j;
    int l = configIn.foliagePlacer.func_230376_a_(rand, k);
    BlockPos blockPos;

    if (!configIn.forcePlacement) {
      int i1 = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();
      int j1 = generationReader.getHeight(Heightmap.Type.WORLD_SURFACE, positionIn).getY();

      if (j1 - i1 > configIn.maxWaterDepth) {
        return false;
      }

      int k1;
      if (configIn.heightmap == Heightmap.Type.OCEAN_FLOOR) {
        k1 = i1;
      } else if (configIn.heightmap == Heightmap.Type.WORLD_SURFACE) {
        k1 = j1;
      } else {
        k1 = generationReader.getHeight(configIn.heightmap, positionIn).getY();
      }

      blockPos = new BlockPos(positionIn.getX(), k1, positionIn.getZ());
    } else {
      blockPos = positionIn;
    }

    if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
      if (!isDirtOrFarmlandAt(generationReader, blockPos.down())) {
        return false;
      } else {
        OptionalInt minimumSize = configIn.minimumSize.func_236710_c_();

        int l1 = this.getMaxFreeTreeHeightAt(generationReader, i, blockPos, configIn);

        if (l1 >= i || minimumSize.isPresent() && l1 >= minimumSize.getAsInt()) {
          List<FoliagePlacer.Foliage> list = configIn.trunkPlacer.getFoliages(generationReader, rand, l1, blockPos, blockPosSet, boundingBoxIn, configIn);

          list.forEach((foliage) ->
            configIn.foliagePlacer.func_236752_a_(generationReader, rand, configIn, l1, foliage, j, l, blockPosSet2, boundingBoxIn)
          );

          return true;
        } else {
          return false;
        }
      }
    } else {
      return false;
    }
  }

  private int getMaxFreeTreeHeightAt(IWorldGenerationBaseReader reader, int trunkHeight, BlockPos topPosition, BaseTreeFeatureConfig config) {
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int i = 0; i <= trunkHeight + 1; ++i) {
      int j = config.minimumSize.func_230369_a_(trunkHeight, i);

      for (int k = -j; k <= j; ++k) {
        for (int l = -j; l <= j; ++l) {
          mutable.setAndOffset(topPosition, k, i, l);
          if (!isLogsAt(reader, mutable) || !config.ignoreVines && isVinesAt(reader, mutable)) {
            return i - 2;
          }
        }
      }
    }

    return trunkHeight;
  }

  public static boolean isLogsAt(IWorldGenerationBaseReader reader, BlockPos pos) {
    return TreeFeature.isReplaceableAt(reader, pos) || reader.hasBlockState(pos, (state) -> state.isIn(BlockTags.LOGS));
  }

  private static boolean isVinesAt(IWorldGenerationBaseReader reader, BlockPos pos) {
    return reader.hasBlockState(pos, (state) -> state.matchesBlock(Blocks.VINE));
  }

  private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader reader, BlockPos pos) {
    return reader.hasBlockState(pos, (state) -> {
      Block block = state.getBlock();
      return isDirt(block) || block == Blocks.FARMLAND;
    });
  }

  @Override
  public final boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
    Set<BlockPos> logPositions = Sets.newHashSet();
    Set<BlockPos> foliagePositions = Sets.newHashSet();
    Set<BlockPos> decoratorsBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();

    boolean flag = this.place(reader, rand, pos, logPositions, foliagePositions, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !logPositions.isEmpty()) {
      if (!config.decorators.isEmpty()) {
        List<BlockPos> logBlockPosList = Lists.newArrayList(logPositions);
        List<BlockPos> leavesBlockPosList = Lists.newArrayList(foliagePositions);
        logBlockPosList.sort(Comparator.comparingInt(Vector3i::getY));
        leavesBlockPosList.sort(Comparator.comparingInt(Vector3i::getY));
        config.decorators.forEach((decorator) -> decorator.func_225576_a_(reader, rand, logBlockPosList, leavesBlockPosList, decoratorsBlockPosSet, boundingBox));
      }

      VoxelShapePart voxelshapepart = GenericOverworldTreeFeature.getFoliageGrowthArea(reader, boundingBox, logPositions, decoratorsBlockPosSet);
      Template.updatePostProcessing(reader, 3, voxelshapepart, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    } else {
      return false;
    }
  }
}
