package com.progwml6.natura.world.worldgen.trees.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WillowTreeFeature extends Feature<BaseOverworldTreeFeatureConfig> {

  public WillowTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public final boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos,
    BaseOverworldTreeFeatureConfig config) {
    Set<BlockPos> trunkBlockPosSet = Sets.newHashSet();
    Set<BlockPos> leavesBlockPosSet = Sets.newHashSet();
    Set<BlockPos> decoratorsBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();

    boolean flag = this.place(seedReader, random, blockPos, trunkBlockPosSet, leavesBlockPosSet, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !trunkBlockPosSet.isEmpty()) {
      if (!config.decorators.isEmpty()) {
        List<BlockPos> list = Lists.newArrayList(trunkBlockPosSet);
        List<BlockPos> list1 = Lists.newArrayList(leavesBlockPosSet);
        list.sort(Comparator.comparingInt(Vector3i::getY));
        list1.sort(Comparator.comparingInt(Vector3i::getY));
        config.decorators.forEach((decorator) -> decorator.func_225576_a_(seedReader, random, list, list1, decoratorsBlockPosSet, boundingBox));
      }

      VoxelShapePart voxelshapepart = this.createVoxelShape(seedReader, boundingBox, trunkBlockPosSet, decoratorsBlockPosSet);
      Template.func_222857_a(seedReader, 3, voxelshapepart, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    }
    else {
      return false;
    }
  }

  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> trunkBlockPosSet,
    Set<BlockPos> leavesBlockPosSet, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    int height = rand.nextInt(configIn.randomHeight) + configIn.baseHeight;

    if (!(generationReader instanceof IWorld)) {
      return false;
    }

    BlockPos blockPos;

    if (!configIn.forcePlacement) {
      int oceanFloorHeight = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();

      blockPos = new BlockPos(positionIn.getX(), oceanFloorHeight, positionIn.getZ());
    }
    else {
      blockPos = positionIn;
    }

    if (blockPos.getY() >= 1 && blockPos.getY() + height + 1 <= 256) {
      if (!isDirtOrFarmlandAt(generationReader, blockPos.down())) {
        return false;
      }
      else {
        this.setDirtAt(generationReader, blockPos.down(), blockPos);

        this.placeTrunk(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn);

        this.placeCanopy(generationReader, rand, height, blockPos, leavesBlockPosSet, boundingBoxIn, configIn);

        return true;
      }
    }
    else {
      return false;
    }
  }

  protected void setDirtAt(IWorldGenerationReader reader, BlockPos pos, BlockPos origin) {
    if (!(reader instanceof IWorld)) {
      return;
    }

    ((IWorld) reader).getBlockState(pos).getBlock().onPlantGrow(((IWorld) reader).getBlockState(pos), (IWorld) reader, pos, origin);
  }

  protected void placeTrunk(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    while (treeHeight > 0) {
      this.setLog(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      blockPos = blockPos.up();
      treeHeight--;
    }
  }

  protected void placeCanopy(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> leavesBlockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    for (int k1 = blockPos.getY() - 3 + treeHeight; k1 <= blockPos.getY() + treeHeight; ++k1) {
      int j2 = k1 - (blockPos.getY() + treeHeight);
      int l2 = 2 - j2 / 2;

      for (int j3 = blockPos.getX() - l2; j3 <= blockPos.getX() + l2; ++j3) {
        int k3 = j3 - blockPos.getX();

        for (int i4 = blockPos.getZ() - l2; i4 <= blockPos.getZ() + l2; ++i4) {
          int j1 = i4 - blockPos.getZ();

          if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || randomIn.nextInt(2) != 0 && j2 != 0) {
            this.setLeaf(worldIn, randomIn, new BlockPos(j3, k1, i4), leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
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

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, west)) {
              this.placeDownLeaves(worldIn, randomIn, west, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, east)) {
              this.placeDownLeaves(worldIn, randomIn, east, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, north)) {
              this.placeDownLeaves(worldIn, randomIn, north, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, south)) {
              this.placeDownLeaves(worldIn, randomIn, south, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }
          }
        }
      }
    }
  }

  private void placeDownLeaves(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> leavesBlockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    this.setLeaf(worldIn, randomIn, blockPos, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    int i = 4;

    for (blockPos = blockPos.down(); isAirAt(worldIn, blockPos) && i > 0; --i) {
      this.setLeaf(worldIn, randomIn, blockPos, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      blockPos = blockPos.down();
    }
  }

  protected boolean setLog(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    }
    else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.trunkProvider.getBlockState(randomIn, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      blockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  protected boolean setLeaf(IWorldGenerationReader worldIn, Random random, BlockPos blockPos, Set<BlockPos> leavesBlockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    }
    else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.leavesProvider.getBlockState(random, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      leavesBlockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  private static boolean isWaterAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isIn(Blocks.WATER));
  }

  public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
  }

  public static boolean isAirAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, AbstractBlock.AbstractBlockState::isAir);
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

  public static boolean isReplaceableAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return isAirOrLeavesAt(reader, blockPos) || isTallPlantAt(reader, blockPos) || isWaterAt(reader, blockPos);
  }

  public static void setBlockStateAt(IWorldWriter writer, BlockPos blockPos, BlockState state) {
    writer.setBlockState(blockPos, state, 19);
  }

  private VoxelShapePart createVoxelShape(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> trunkBlockPosSet,
    Set<BlockPos> decoratorsBlockPosSet) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 10; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos blockPos : Lists.newArrayList(decoratorsBlockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart
          .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }
    }

    for (BlockPos blockPos : Lists.newArrayList(trunkBlockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart
          .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }

      for (Direction direction : Direction.values()) {
        mutable.setAndMove(blockPos, direction);

        if (!trunkBlockPosSet.contains(mutable)) {
          BlockState blockstate = world.getBlockState(mutable);

          if (blockstate.hasProperty(NaturaWorld.EXTENDED_TREE_DISTANCE)) {
            list.get(0).add(mutable.toImmutable());

            setBlockStateAt(world, mutable, blockstate.with(NaturaWorld.EXTENDED_TREE_DISTANCE, 1));

            if (boundingBox.isVecInside(mutable)) {
              voxelShapePart
                .setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
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
          voxelShapePart
            .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
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
                  voxelShapePart
                    .setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
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
