package com.progwml6.natura.world.worldgen.trees.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class GenericOverworldTreeFeature extends Feature<BaseOverworldTreeFeatureConfig> {

  public GenericOverworldTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public final boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseOverworldTreeFeatureConfig config) {
    Set<BlockPos> logPositions = Sets.newHashSet();
    Set<BlockPos> foliagePositions = Sets.newHashSet();
    Set<BlockPos> decoratorsBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();

    boolean flag = this.place(reader, rand, pos, logPositions, foliagePositions, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !logPositions.isEmpty()) {
      if (!config.decorators.isEmpty()) {
        List<BlockPos> logPositionsList = Lists.newArrayList(logPositions);
        List<BlockPos> foliagePositionsList = Lists.newArrayList(foliagePositions);
        logPositionsList.sort(Comparator.comparingInt(Vector3i::getY));
        foliagePositionsList.sort(Comparator.comparingInt(Vector3i::getY));
        config.decorators.forEach((decorator) -> decorator.func_225576_a_(reader, rand, logPositionsList, foliagePositionsList, decoratorsBlockPosSet, boundingBox));
      }

      VoxelShapePart voxelshapepart = getFoliageGrowthArea(reader, boundingBox, logPositions, decoratorsBlockPosSet);
      Template.updatePostProcessing(reader, 3, voxelshapepart, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Called when placing the tree feature.
   */
  public boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    int height = rand.nextInt(configIn.randomHeight) + configIn.baseHeight;

    BlockPos blockPos;

    if (!configIn.forcePlacement) {
      int oceanFloorHeight = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();

      blockPos = new BlockPos(positionIn.getX(), oceanFloorHeight, positionIn.getZ());
    } else {
      blockPos = positionIn;
    }

    if (blockPos.getY() >= 1 && blockPos.getY() + height + 1 <= 256) {
      if (!isDirtOrFarmlandAt(generationReader, blockPos.down())) {
        return false;
      } else {
        this.place(generationReader, rand, height, blockPos, logPositions, foliagePositions, boundingBoxIn, configIn);
        return true;
      }
    } else {
      return false;
    }
  }

  public abstract void place(IWorldGenerationReader generationReader, Random rand, int height, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn);

  public static VoxelShapePart getFoliageGrowthArea(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 10; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos foliagePosition : Lists.newArrayList(foliagePositions)) {
      if (boundingBox.isVecInside(foliagePosition)) {
        voxelShapePart.setFilled(foliagePosition.getX() - boundingBox.minX, foliagePosition.getY() - boundingBox.minY, foliagePosition.getZ() - boundingBox.minZ, true, true);
      }
    }

    for (BlockPos logPosition : Lists.newArrayList(logPositions)) {
      if (boundingBox.isVecInside(logPosition)) {
        voxelShapePart.setFilled(logPosition.getX() - boundingBox.minX, logPosition.getY() - boundingBox.minY, logPosition.getZ() - boundingBox.minZ, true, true);
      }

      for (Direction direction : Direction.values()) {
        mutable.setAndMove(logPosition, direction);

        if (!logPositions.contains(mutable)) {
          BlockState blockstate = world.getBlockState(mutable);

          if (blockstate.hasProperty(NaturaWorld.EXTENDED_TREE_DISTANCE)) {
            list.get(0).add(mutable.toImmutable());

            TreeFeature.setBlockStateWithoutUpdate(world, mutable, blockstate.with(NaturaWorld.EXTENDED_TREE_DISTANCE, 1));

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

                TreeFeature.setBlockStateWithoutUpdate(world, mutable, blockStateWithDistance);

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

  protected static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader reader, BlockPos pos) {
    return reader.hasBlockState(pos, (state) -> {
      Block block = state.getBlock();
      return isDirt(block) || block == Blocks.FARMLAND;
    });
  }

  protected static boolean isDirt(IWorldGenerationBaseReader reader, BlockPos pos) {
    return reader.hasBlockState(pos, (state) -> {
      Block block = state.getBlock();
      return Feature.isDirt(block) && !state.matchesBlock(Blocks.GRASS_BLOCK) && !state.matchesBlock(Blocks.MYCELIUM);
    });
  }

  protected static void setDirt(IWorldGenerationReader reader, BlockPos pos) {
    if (!isDirt(reader, pos)) {
      TreeFeature.setBlockStateWithoutUpdate(reader, pos, Blocks.DIRT.getDefaultState());
    }
  }

  protected static void addLog(IWorldGenerationReader reader, Random rand, BlockPos.Mutable pos, Set<BlockPos> logPositions, MutableBoundingBox boundingBox, BaseOverworldTreeFeatureConfig config) {
    if (TreeFeature.isLogsAt(reader, pos)) {
      addLogWithCheck(reader, rand, pos, logPositions, boundingBox, config);
    }
  }

  protected static void addLogWithCheck(IWorldGenerationReader reader, Random rand, BlockPos pos, Set<BlockPos> logPositions, MutableBoundingBox boundingBox, BaseOverworldTreeFeatureConfig config) {
    if (TreeFeature.isReplaceableAt(reader, pos)) {
      setStateWithoutUpdate(reader, pos, config.trunkProvider.getBlockState(rand, pos), boundingBox);
      logPositions.add(pos.toImmutable());
    }
  }

  protected static void addLeafWithCheck(IWorldGenerationReader reader, Random rand, BlockPos pos, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBox, BaseOverworldTreeFeatureConfig config) {
    if (TreeFeature.isReplaceableAt(reader, pos)) {
      setStateWithoutUpdate(reader, pos, config.leavesProvider.getBlockState(rand, pos), boundingBox);
      foliagePositions.add(pos.toImmutable());
    }
  }

  protected static void setStateWithoutUpdate(IWorldWriter writer, BlockPos pos, BlockState state, MutableBoundingBox area) {
    TreeFeature.setBlockStateWithoutUpdate(writer, pos, state);
    area.expandTo(new MutableBoundingBox(pos, pos));
  }
}
