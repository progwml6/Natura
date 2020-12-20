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

  public static boolean isReplaceableOrLogAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return isReplaceableAt(reader, blockPos) || reader.hasBlockState(blockPos, (state) -> state.isIn(BlockTags.LOGS));
  }

  private static boolean isVineAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isIn(Blocks.VINE));
  }

  private static boolean isWaterAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isIn(Blocks.WATER));
  }

  public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
  }

  private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> {
      Block block = state.getBlock();
      return isDirt(block) || block == Blocks.FARMLAND;
    });
  }

  private static boolean isTallPlantAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> {
      Material material = state.getMaterial();
      return material == Material.TALL_PLANTS;
    });
  }

  public static void setBlockStateAt(IWorldWriter writer, BlockPos blockPos, BlockState state) {
    writer.setBlockState(blockPos, state, 19);
  }

  public static boolean isReplaceableAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return isAirOrLeavesAt(reader, blockPos) || isTallPlantAt(reader, blockPos) || isWaterAt(reader, blockPos);
  }

  /**
   * Called when placing the tree feature.
   */
  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> blockPosSet, Set<BlockPos> blockPosSet2, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn) {
    int i = configIn.trunkPlacer.func_236917_a_(rand);
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
      if (configIn.field_236682_l_ == Heightmap.Type.OCEAN_FLOOR) {
        k1 = i1;
      }
      else if (configIn.field_236682_l_ == Heightmap.Type.WORLD_SURFACE) {
        k1 = j1;
      }
      else {
        k1 = generationReader.getHeight(configIn.field_236682_l_, positionIn).getY();
      }

      blockPos = new BlockPos(positionIn.getX(), k1, positionIn.getZ());
    }
    else {
      blockPos = positionIn;
    }

    if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
      if (!isDirtOrFarmlandAt(generationReader, blockPos.down())) {
        return false;
      }
      else {
        OptionalInt minimumSize = configIn.minimumSize.func_236710_c_();

        int l1 = this.getHeight(generationReader, i, blockPos, configIn);

        if (l1 >= i || minimumSize.isPresent() && l1 >= minimumSize.getAsInt()) {
          List<FoliagePlacer.Foliage> list = configIn.trunkPlacer.func_230382_a_(generationReader, rand, l1, blockPos, blockPosSet, boundingBoxIn, configIn);

          list.forEach((foliage) ->
            configIn.foliagePlacer.func_236752_a_(generationReader, rand, configIn, l1, foliage, j, l, blockPosSet2, boundingBoxIn)
          );

          return true;
        }
        else {
          return false;
        }
      }
    }
    else {
      return false;
    }
  }

  private int getHeight(IWorldGenerationBaseReader reader, int height, BlockPos blockPos, BaseTreeFeatureConfig config) {
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int i = 0; i <= height + 1; ++i) {
      int j = config.minimumSize.func_230369_a_(height, i);

      for (int k = -j; k <= j; ++k) {
        for (int l = -j; l <= j; ++l) {
          mutable.setAndOffset(blockPos, k, i, l);

          if (!isReplaceableOrLogAt(reader, mutable) || !config.ignoreVines && isVineAt(reader, mutable)) {
            return i - 2;
          }
        }
      }
    }

    return height;
  }

  @Override
  protected void setBlockState(IWorldWriter world, BlockPos pos, BlockState state) {
    setBlockStateAt(world, pos, state);
  }

  @Override
  public final boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
    Set<BlockPos> trunkBlockPosSet = Sets.newHashSet();
    Set<BlockPos> leavesBlockPosSet = Sets.newHashSet();
    Set<BlockPos> decoratorsBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();

    boolean flag = this.place(reader, rand, pos, trunkBlockPosSet, leavesBlockPosSet, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !trunkBlockPosSet.isEmpty()) {
      if (!config.decorators.isEmpty()) {
        List<BlockPos> logBlockPosList = Lists.newArrayList(trunkBlockPosSet);
        List<BlockPos> leavesBlockPosList = Lists.newArrayList(leavesBlockPosSet);
        logBlockPosList.sort(Comparator.comparingInt(Vector3i::getY));
        leavesBlockPosList.sort(Comparator.comparingInt(Vector3i::getY));
        config.decorators.forEach((decorator) -> decorator.func_225576_a_(reader, rand, logBlockPosList, leavesBlockPosList, decoratorsBlockPosSet, boundingBox));
      }

      VoxelShapePart voxelshapepart = this.createVoxelShape(reader, boundingBox, trunkBlockPosSet, decoratorsBlockPosSet);
      Template.func_222857_a(reader, 3, voxelshapepart, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    }
    else {
      return false;
    }
  }

  private VoxelShapePart createVoxelShape(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> blockPosSet, Set<BlockPos> blockPosSet2) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 10; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos blockPos : Lists.newArrayList(blockPosSet2)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart.setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }
    }

    for (BlockPos blockPos : Lists.newArrayList(blockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart.setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }

      for (Direction direction : Direction.values()) {
        mutable.setAndMove(blockPos, direction);

        if (!blockPosSet.contains(mutable)) {
          BlockState blockstate = world.getBlockState(mutable);

          if (blockstate.hasProperty(NaturaWorld.EXTENDED_TREE_DISTANCE)) {
            list.get(0).add(mutable.toImmutable());

            setBlockStateAt(world, mutable, blockstate.with(NaturaWorld.EXTENDED_TREE_DISTANCE, 1));

            if (boundingBox.isVecInside(mutable)) {
              voxelShapePart.setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
            }
          }
        }
      }
    }

    for (int l = 1; l < 10; ++l) {
      Set<BlockPos> set = list.get(l - 1);
      Set<BlockPos> set1 = list.get(l);

      for (BlockPos blockPos : set) {
        if (boundingBox.isVecInside(blockPos)) {
          voxelShapePart.setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
        }

        for (Direction direction : Direction.values()) {
          mutable.setAndMove(blockPos, direction);

          if (!set.contains(mutable) && !set1.contains(mutable)) {
            BlockState blockState = world.getBlockState(mutable);

            if (blockState.hasProperty(NaturaWorld.EXTENDED_TREE_DISTANCE)) {
              int distance = blockState.get(NaturaWorld.EXTENDED_TREE_DISTANCE);

              if (distance > l + 1) {
                BlockState blockStateWithDistance = blockState.with(NaturaWorld.EXTENDED_TREE_DISTANCE, l + 1);

                setBlockStateAt(world, mutable, blockStateWithDistance);

                if (boundingBox.isVecInside(mutable)) {
                  voxelShapePart.setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
                }

                set1.add(mutable.toImmutable());
              }
            }
          }
        }
      }
    }

    return voxelShapePart;
  }
}
