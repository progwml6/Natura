package com.progwml6.natura.world.worldgen.trees.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WillowTreeFeature extends Feature<BaseOverworldTreeFeatureConfig> {

  public WillowTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public final boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BaseOverworldTreeFeatureConfig config) {
    Set<BlockPos> set = Sets.newHashSet();
    Set<BlockPos> set1 = Sets.newHashSet();
    Set<BlockPos> set2 = Sets.newHashSet();
    MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();
    boolean flag = this.place(seedReader, random, blockPos, set, set1, mutableboundingbox, config);

    if (mutableboundingbox.minX <= mutableboundingbox.maxX && flag && !set.isEmpty()) {
      VoxelShapePart voxelshapepart = this.func_236403_a_(seedReader, mutableboundingbox, set, set2);
      Template.func_222857_a(seedReader, 3, voxelshapepart, mutableboundingbox.minX, mutableboundingbox.minY, mutableboundingbox.minZ);
      return true;
    }
    else {
      return false;
    }
  }

  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> trunkBlockPosSet, Set<BlockPos> leavesBlockPosSet, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    int height = rand.nextInt(configIn.randomHeight) + configIn.baseHeight;

    if (!(generationReader instanceof IWorld)) {
      return false;
    }

    BlockPos blockpos;
    if (!configIn.forcePlacement) {
      int oceanFloorHeight = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();

      blockpos = new BlockPos(positionIn.getX(), oceanFloorHeight, positionIn.getZ());
    }
    else {
      blockpos = positionIn;
    }

    if (blockpos.getY() >= 1 && blockpos.getY() + height + 1 <= 256) {
      if (!isDirtOrFarmlandAt(generationReader, blockpos.down())) {
        return false;
      }
      else {
        this.setDirtAt(generationReader, blockpos.down(), blockpos);

        this.placeTrunk(generationReader, rand, height, blockpos, trunkBlockPosSet, boundingBoxIn, configIn);

        this.placeCanopy(generationReader, rand, height, blockpos, trunkBlockPosSet, boundingBoxIn, configIn);

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

  protected void placeTrunk(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    while (treeHeight > 0) {
      this.setLog(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      blockPos = blockPos.up();
      treeHeight--;
    }
  }

  protected void placeCanopy(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    for (int k1 = blockPos.getY() - 3 + treeHeight; k1 <= blockPos.getY() + treeHeight; ++k1) {
      int j2 = k1 - (blockPos.getY() + treeHeight);
      int l2 = 2 - j2 / 2;

      for (int j3 = blockPos.getX() - l2; j3 <= blockPos.getX() + l2; ++j3) {
        int k3 = j3 - blockPos.getX();

        for (int i4 = blockPos.getZ() - l2; i4 <= blockPos.getZ() + l2; ++i4) {
          int j1 = i4 - blockPos.getZ();

          if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || randomIn.nextInt(2) != 0 && j2 != 0) {
            this.setLeaf(worldIn, randomIn, new BlockPos(j3, k1, i4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
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
              this.placeDownLeaves(worldIn, randomIn, west, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, east)) {
              this.placeDownLeaves(worldIn, randomIn, east, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, north)) {
              this.placeDownLeaves(worldIn, randomIn, north, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }

            if (randomIn.nextInt(4) == 0 && isAirAt(worldIn, south)) {
              this.placeDownLeaves(worldIn, randomIn, south, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
            }
          }
        }
      }
    }
  }

  private void placeDownLeaves(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    this.setLeaf(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    int i = 4;

    for (blockPos = blockPos.down(); isAirAt(worldIn, blockPos) && i > 0; --i) {
      this.setLeaf(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      blockPos = blockPos.down();
    }
  }

  protected boolean setLog(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
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

  protected boolean setLeaf(IWorldGenerationReader worldIn, Random random, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    }
    else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.leavesProvider.getBlockState(random, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      blockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  private static boolean isWaterAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (p_236413_0_) -> p_236413_0_.isIn(Blocks.WATER));
  }

  public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (p_236411_0_) -> p_236411_0_.isAir() || p_236411_0_.isIn(BlockTags.LEAVES));
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
    return reader.hasBlockState(blockPos, (p_236406_0_) -> {
      Material material = p_236406_0_.getMaterial();
      return material == Material.TALL_PLANTS;
    });
  }

  public static boolean isReplaceableAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return isAirOrLeavesAt(reader, blockPos) || isTallPlantAt(reader, blockPos) || isWaterAt(reader, blockPos);
  }

  public static void setBlockStateAt(IWorldWriter writer, BlockPos blockPos, BlockState state) {
    writer.setBlockState(blockPos, state, 19);
  }

  private VoxelShapePart func_236403_a_(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> logs, Set<BlockPos> leaves) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 6; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos blockpos : Lists.newArrayList(leaves)) {
      if (boundingBox.isVecInside(blockpos)) {
        voxelshapepart.setFilled(blockpos.getX() - boundingBox.minX, blockpos.getY() - boundingBox.minY, blockpos.getZ() - boundingBox.minZ, true, true);
      }
    }

    for (BlockPos blockpos1 : Lists.newArrayList(logs)) {
      if (boundingBox.isVecInside(blockpos1)) {
        voxelshapepart.setFilled(blockpos1.getX() - boundingBox.minX, blockpos1.getY() - boundingBox.minY, blockpos1.getZ() - boundingBox.minZ, true, true);
      }

      for (Direction direction : Direction.values()) {
        mutable.setAndMove(blockpos1, direction);
        if (!logs.contains(mutable)) {
          BlockState blockstate = world.getBlockState(mutable);
          if (blockstate.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
            list.get(0).add(mutable.toImmutable());
            setBlockStateAt(world, mutable, blockstate.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(1)));
            if (boundingBox.isVecInside(mutable)) {
              voxelshapepart.setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
            }
          }
        }
      }
    }

    for (int l = 1; l < 6; ++l) {
      Set<BlockPos> set = list.get(l - 1);
      Set<BlockPos> set1 = list.get(l);

      for (BlockPos blockpos2 : set) {
        if (boundingBox.isVecInside(blockpos2)) {
          voxelshapepart.setFilled(blockpos2.getX() - boundingBox.minX, blockpos2.getY() - boundingBox.minY, blockpos2.getZ() - boundingBox.minZ, true, true);
        }

        for (Direction direction1 : Direction.values()) {
          mutable.setAndMove(blockpos2, direction1);
          if (!set.contains(mutable) && !set1.contains(mutable)) {
            BlockState blockstate1 = world.getBlockState(mutable);
            if (blockstate1.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
              int k = blockstate1.get(BlockStateProperties.DISTANCE_1_7);

              if (k > l + 1) {
                BlockState blockstate2 = blockstate1.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(l + 1));
                setBlockStateAt(world, mutable, blockstate2);
                if (boundingBox.isVecInside(mutable)) {
                  voxelshapepart.setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
                }

                set1.add(mutable.toImmutable());
              }
            }
          }
        }
      }
    }

    return voxelshapepart;
  }
}
