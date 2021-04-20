package com.progwml6.natura.world.worldgen.trees.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.worldgen.trees.config.RedwoodTreeFeatureConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

// TODO REWORK THIS ENTIRE TREE.....
public class RedwoodTreeFeature extends Feature<RedwoodTreeFeatureConfig> {

  public RedwoodTreeFeature(Codec<RedwoodTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public final boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos,
                                RedwoodTreeFeatureConfig config) {
    Set<BlockPos> logsBlockPosSet = Sets.newHashSet();
    Set<BlockPos> leavesBlockPosSet = Sets.newHashSet();
    Set<BlockPos> decoratorsBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();
    boolean flag = this.place(seedReader, random, blockPos, logsBlockPosSet, leavesBlockPosSet, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !logsBlockPosSet.isEmpty()) {
      if (!config.decorators.isEmpty()) {
        List<BlockPos> logsBlockPosList = Lists.newArrayList(logsBlockPosSet);
        List<BlockPos> leavesList = Lists.newArrayList(leavesBlockPosSet);
        logsBlockPosList.sort(Comparator.comparingInt(Vector3i::getY));
        leavesList.sort(Comparator.comparingInt(Vector3i::getY));
        config.decorators.forEach((decorator) -> decorator.func_225576_a_(seedReader, random, logsBlockPosList, leavesList, decoratorsBlockPosSet, boundingBox));
      }

      VoxelShapePart voxelShape = this.createVoxelShape(seedReader, boundingBox, logsBlockPosSet, decoratorsBlockPosSet);
      Template.updatePostProcessing(seedReader, 3, voxelShape, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    } else {
      return false;
    }
  }

  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> trunkBlockPosSet,
                        Set<BlockPos> leavesBlockPosSet, MutableBoundingBox boundingBoxIn, RedwoodTreeFeatureConfig configIn) {

    int height = rand.nextInt(configIn.randomHeight) + configIn.baseHeight;
    int heightLimit = 5 + rand.nextInt(configIn.heightLimit);

    if (!(generationReader instanceof IWorld)) {
      return false;
    }

    BlockPos blockPos;
    int currentHeight = 0;

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
        if (height > 120) {
          for (int currentTreeHeight = 0; currentTreeHeight < height; currentTreeHeight++) {
            if (currentTreeHeight < height / 10) {
              this.genRing13(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 2 / 10) {
              this.genRing12(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 3 / 10) {
              this.genRing11(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 4 / 10) {
              this.genRing10(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 5 / 10) {
              this.genRing9(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growLowBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 6 / 10) {
              this.genRing8(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growLowBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 7 / 10) {
              this.genRing7(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growMiddleBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 8 / 10) {
              this.genRing6(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 9 / 10) {
              this.genRing5(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else {
              this.genRing3(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            }
          }

          this.growBigRoots(generationReader, rand, blockPos.down(), trunkBlockPosSet, boundingBoxIn, configIn);

          this.growTop(generationReader, rand, blockPos.up(currentHeight),
            heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
        } else if (height > 100) {
          for (int currentTreeHeight = 0; currentTreeHeight < height; currentTreeHeight++) {
            if (currentTreeHeight < height / 8) {
              this.genRing11(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 2 / 8) {
              this.genRing10(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 3 / 8) {
              this.genRing9(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 4 / 8) {
              this.genRing8(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 5 / 8) {
              this.genRing7(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growMiddleBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 6 / 8) {
              this.genRing6(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growMiddleBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 7 / 8) {
              this.genRing5(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else {
              this.genRing3(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            }
          }
          this.growMediumRoots(generationReader, rand, blockPos.down(), trunkBlockPosSet, boundingBoxIn, configIn);

          this.growTop(generationReader, rand, blockPos.up(currentHeight),
            heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
        } else {
          for (int currentTreeHeight = 0; currentTreeHeight < height; currentTreeHeight++) {
            if (currentTreeHeight < height / 6) {
              this.genRing9(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 2 / 6) {
              this.genRing8(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 3 / 6) {
              this.genRing7(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 4 / 6) {
              this.genRing6(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growMiddleBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else if (currentTreeHeight < height * 5 / 6) {
              this.genRing5(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            } else {
              this.genRing3(generationReader, rand, blockPos.up(currentTreeHeight), trunkBlockPosSet, boundingBoxIn, configIn);

              currentHeight = this.growHighBranch(generationReader, rand, blockPos.up(currentTreeHeight),
                heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);
            }
          }

          this.growSmallRoots(generationReader, rand, blockPos.down(), trunkBlockPosSet, boundingBoxIn, configIn);

          this.growTop(generationReader, rand, blockPos.up(currentHeight),
            heightLimit, trunkBlockPosSet, leavesBlockPosSet, boundingBoxIn, configIn);

        }
        return true;
      }
    } else {
      return false;
    }
  }

  // TODO REWORK THIS

  /**
   * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
   */
  private int generateLeafNodeList(IWorldGenerationReader worldIn, BlockPos position, Random random, int heightLimit,
                                   List<FoliageCoordinates> foliageCoords, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    int currentHeight = (int) (heightLimit * treeFeatureConfigIn.heightAttenuation);

    if (currentHeight >= heightLimit) {
      currentHeight = heightLimit - 1;
    }

    int i = (int) (1.382D + Math.pow(treeFeatureConfigIn.leafDensity * heightLimit / 13.0D, 2.0D));

    if (i < 1) {
      i = 1;
    }

    int j = position.getY() + currentHeight;
    int k = heightLimit - treeFeatureConfigIn.leafDistanceLimit;

    foliageCoords.add(new FoliageCoordinates(position.up(k), j));

    for (; k >= 0; --k) {
      float f = this.layerSize(k, heightLimit);

      if (f >= 0.0F) {
        for (int l = 0; l < i; ++l) {
          double d0 = treeFeatureConfigIn.scaleWidth * f * (random.nextFloat() + 0.328D);
          double d1 = random.nextFloat() * 2.0F * Math.PI;
          double d2 = d0 * Math.sin(d1) + 0.5D;
          double d3 = d0 * Math.cos(d1) + 0.5D;
          BlockPos blockPos = position.add(d2, -1, d3);
          BlockPos blockpos1 = blockPos.up(treeFeatureConfigIn.leafDistanceLimit);

          if (this.checkBlockLine(worldIn, blockPos, blockpos1) == -1) {
            int i1 = position.getX() - blockPos.getX();
            int j1 = position.getZ() - blockPos.getZ();
            double d4 = blockPos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * treeFeatureConfigIn.branchSlope;
            int k1 = d4 > j ? j : (int) d4;
            BlockPos blockpos2 = new BlockPos(position.getX(), k1, position.getZ());

            if (this.checkBlockLine(worldIn, blockpos2, blockPos) == -1) {
              foliageCoords.add(new FoliageCoordinates(blockPos, blockpos2.getY()));
            }
          }
        }
      }
    }

    return currentHeight;
  }

  private void crossSection(IWorldGenerationReader worldIn, Random randomIn, BlockPos position, Set<BlockPos> leavesBlockPosSet,
                            MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn, float size) {
    int i = (int) (size + 0.618D);

    for (int j = -i; j <= i; ++j) {
      for (int k = -i; k <= i; ++k) {
        if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= size * size) {
          this.setLeaf(worldIn, randomIn, position.add(j, 0, k), leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
        }
      }
    }
  }

  /**
   * Gets the rough size of a layer of the tree.
   */
  private float layerSize(int y, int heightLimit) {
    if (y < heightLimit * 0.3F) {
      return -1.0F;
    } else {
      float f = heightLimit / 2.0F;
      float f1 = f - y;
      float f2 = MathHelper.sqrt(f * f - f1 * f1);

      if (f1 == 0.0F) {
        f2 = f;
      } else if (Math.abs(f1) >= f) {
        return 0.0F;
      }

      return f2 * 0.5F;
    }
  }

  private float leafSize(int y) {
    return y >= 0 && y < 4 ? (y != 0 && y != 4 - 1 ? 3.0F : 2.0F) : -1.0F;
  }

  /**
   * Generates the leaves surrounding an individual entry in the leafNodes list.
   */
  private void generateLeafNode(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> leavesBlockPosSet,
                                MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    for (int i = 0; i < treeFeatureConfigIn.leafDistanceLimit; ++i) {

      this.crossSection(worldIn, randomIn, blockPos.up(i), leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, this.leafSize(i));
    }
  }

  private void limb(IWorldGenerationReader worldIn, Random randomIn, BlockPos pos1, BlockPos pos2, Set<BlockPos> blockPosSet,
                    MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    BlockPos blockpos = pos2.add(-pos1.getX(), -pos1.getY(), -pos1.getZ());
    int i = this.getGreatestDistance(blockpos);
    float f = (float) blockpos.getX() / (float) i;
    float f1 = (float) blockpos.getY() / (float) i;
    float f2 = (float) blockpos.getZ() / (float) i;

    for (int j = 0; j <= i; ++j) {
      BlockPos blockpos1 = pos1.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
      this.setBark(worldIn, randomIn, blockpos1, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  /**
   * Returns the absolute greatest distance in the BlockPos object.
   */
  private int getGreatestDistance(BlockPos posIn) {
    int i = MathHelper.abs(posIn.getX());
    int j = MathHelper.abs(posIn.getY());
    int k = MathHelper.abs(posIn.getZ());
    return k > i && k > j ? k : (Math.max(j, i));
  }

  /**
   * Generates the leaf portion of the tree as specified by the leafNodes list.
   */
  private void generateLeaves(IWorldGenerationReader worldIn, Random randomIn, Set<BlockPos> leavesBlockPosSet,
                              MutableBoundingBox mutableBoundingBoxIn,
                              RedwoodTreeFeatureConfig treeFeatureConfigIn, List<FoliageCoordinates> foliageCoords) {
    for (FoliageCoordinates foliageCoordinates : foliageCoords) {
      this.generateLeafNode(worldIn, randomIn, foliageCoordinates, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  /**
   * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
   */
  private boolean leafNodeNeedsBase(int branchSlope, int heightLimit) {
    return branchSlope >= heightLimit * 0.2D;
  }

  /**
   * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
   */
  private void generateLeafNodeBases(IWorldGenerationReader worldIn, Random randomIn,
                                     Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn, BlockPos position,
                                     int heightLimit, List<FoliageCoordinates> foliageCoords) {

    for (FoliageCoordinates foliageCoordinates : foliageCoords) {
      int i = foliageCoordinates.getBranchBase();
      BlockPos blockPos = new BlockPos(position.getX(), i, position.getZ());

      if (!blockPos.equals(foliageCoordinates) && this.leafNodeNeedsBase(i - position.getY(), heightLimit)) {
        this.limb(worldIn, randomIn, blockPos, foliageCoordinates, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
    }
  }

  /**
   * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
   * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
   */
  private int checkBlockLine(IWorldGenerationReader worldIn, BlockPos posOne, BlockPos posTwo) {
    BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
    int i = this.getGreatestDistance(blockpos);
    float f = (float) blockpos.getX() / (float) i;
    float f1 = (float) blockpos.getY() / (float) i;
    float f2 = (float) blockpos.getZ() / (float) i;

    if (i != 0) {
      for (int j = 0; j <= i; ++j) {
        BlockPos blockpos1 = posOne.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);

        if (!isReplaceableAt(worldIn, blockpos1)) {
          return j;
        }
      }

    }
    return -1;
  }

  // Helper methods

  protected boolean setBark(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    } else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.barkProvider.getBlockState(randomIn, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      blockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  protected boolean setHeart(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    } else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.heartProvider.getBlockState(randomIn, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      blockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  protected boolean setRoot(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    } else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.rootProvider.getBlockState(randomIn, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      blockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  protected boolean setLeaf(IWorldGenerationReader worldIn, Random random, BlockPos blockPos, Set<BlockPos> leavesBlockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    } else {
      this.setBlockState(worldIn, blockPos, treeFeatureConfigIn.leavesProvider.getBlockState(random, blockPos));
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      leavesBlockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  private static boolean isWaterAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.matchesBlock(Blocks.WATER));
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

  private static boolean canGroIntoAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> {
      Material material = state.getMaterial();
      Block block = state.getBlock();
      return material == Material.AIR || material == Material.LEAVES || block == Blocks.GRASS || block == Blocks.DIRT ||
        block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG
        || block == Blocks.DARK_OAK_LOG ||
        block == Blocks.OAK_SAPLING || block == Blocks.SPRUCE_SAPLING || block == Blocks.BIRCH_SAPLING || block == Blocks.JUNGLE_SAPLING
        || block == Blocks.ACACIA_SAPLING || block == Blocks.DARK_OAK_SAPLING ||
        block == Blocks.VINE;
    });
  }

  public static boolean isReplaceableAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return isAirOrLeavesAt(reader, blockPos) || isTallPlantAt(reader, blockPos) || isWaterAt(reader, blockPos) || canGroIntoAt(reader, blockPos);
  }

  private static boolean isBedrockAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> {
      Block block = state.getBlock();
      return block == Blocks.BEDROCK;
    });
  }

  public static void setBlockStateAt(IWorldWriter writer, BlockPos blockPos, BlockState state) {
    writer.setBlockState(blockPos, state, 19);
  }

  private VoxelShapePart createVoxelShape(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> blockPosSet,
                                          Set<BlockPos> decoratorsBlockPosSet) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 26; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos blockPos : Lists.newArrayList(decoratorsBlockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart
          .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }
    }

    for (BlockPos blockPos : Lists.newArrayList(blockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart
          .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }

      for (Direction direction : Direction.values()) {
        mutable.setAndMove(blockPos, direction);

        if (!blockPosSet.contains(mutable)) {
          BlockState blockstate = world.getBlockState(mutable);

          if (blockstate.hasProperty(NaturaWorld.REDWOOD_TREE_DISTANCE)) {
            list.get(0).add(mutable.toImmutable());

            setBlockStateAt(world, mutable, blockstate.with(NaturaWorld.REDWOOD_TREE_DISTANCE, 1));

            if (boundingBox.isVecInside(mutable)) {
              voxelShapePart
                .setFilled(mutable.getX() - boundingBox.minX, mutable.getY() - boundingBox.minY, mutable.getZ() - boundingBox.minZ, true, true);
            }
          }
        }
      }
    }

    for (int l = 1; l < 25; ++l) {
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

            if (blockState.hasProperty(NaturaWorld.REDWOOD_TREE_DISTANCE)) {
              int distance = blockState.get(NaturaWorld.REDWOOD_TREE_DISTANCE);

              if (distance > l + 1) {
                BlockState blockStateWithDistance = blockState.with(NaturaWorld.REDWOOD_TREE_DISTANCE, l + 1);

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

  // BRANCHES AND TOP START
  public void growTop(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, int heightLimit, Set<BlockPos> blockPosSet,
                      Set<BlockPos> leavesBlockPosSet, MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {

    List<FoliageCoordinates> foliageCoords = Lists.newArrayList();
    BlockPos newPos = blockPos.up(4);

    this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

    foliageCoords = Lists.newArrayList();
    newPos = blockPos.up(4);

    this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

    foliageCoords = Lists.newArrayList();
    newPos = blockPos;

    this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

    foliageCoords = Lists.newArrayList();
    newPos = blockPos;

    this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

  }

  public int growHighBranch(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, int heightLimit, Set<BlockPos> blockPosSet,
                            Set<BlockPos> leavesBlockPosSet, MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    int xPos = blockPos.getX();
    int yPos = blockPos.getY();
    int zPos = blockPos.getZ();
    int currentHeight = 0;

    for (int iter = 0; iter < 3; iter++) {
      BlockPos newPos = new BlockPos((xPos + randomIn.nextInt(21)) - 10, yPos, (zPos + randomIn.nextInt(21)) - 10);
      List<FoliageCoordinates> foliageCoords = Lists.newArrayList();

      currentHeight = this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
      this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
      this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);
    }

    return currentHeight;
  }

  public int growMiddleBranch(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, int heightLimit, Set<BlockPos> blockPosSet,
                              Set<BlockPos> leavesBlockPosSet, MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    int xPos = blockPos.getX();
    int yPos = blockPos.getY();
    int zPos = blockPos.getZ();
    int currentHeight = 0;

    for (int iter = 0; iter < 6; iter++) {
      BlockPos newPos = new BlockPos((xPos + randomIn.nextInt(31)) - 15, yPos, (zPos + randomIn.nextInt(31)) - 15);
      List<FoliageCoordinates> foliageCoords = Lists.newArrayList();

      currentHeight = this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
      this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
      this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);
    }

    return currentHeight;
  }

  public int growLowBranch(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, int heightLimit, Set<BlockPos> blockPosSet,
                           Set<BlockPos> leavesBlockPosSet, MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    int xPos = blockPos.getX();
    int yPos = blockPos.getY();
    int zPos = blockPos.getZ();
    int currentHeight = 0;


    List<FoliageCoordinates> foliageCoords = Lists.newArrayList();
    BlockPos newPos = new BlockPos((xPos + randomIn.nextInt(17)) - 8, yPos, (zPos + randomIn.nextInt(17)) - 8);

    currentHeight = this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

    if (randomIn.nextInt(2) == 0) {
      newPos = new BlockPos((xPos + randomIn.nextInt(17)) - 8, yPos, (zPos + randomIn.nextInt(17)) - 8);

      currentHeight = this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
      this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
      this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);
    }

    newPos = new BlockPos((xPos + randomIn.nextInt(17)) - 8, yPos, (zPos + randomIn.nextInt(17)) - 8);

    currentHeight = this.generateLeafNodeList(worldIn, newPos, randomIn, heightLimit, foliageCoords, treeFeatureConfigIn);
    this.generateLeaves(worldIn, randomIn, leavesBlockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, foliageCoords);
    this.generateLeafNodeBases(worldIn, randomIn, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn, newPos, heightLimit, foliageCoords);

    return currentHeight;
  }

  // BRANCHES AND TOP END

  // ROOTS START
  public void growSmallRoots(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {

    this.genRing9(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.smallRoot1(worldIn, randomIn, blockPos.down(1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot1(worldIn, randomIn, blockPos.down(2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot1(worldIn, randomIn, blockPos.down(3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.smallRoot2(worldIn, randomIn, blockPos.down(4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot2(worldIn, randomIn, blockPos.down(5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.smallRoot3(worldIn, randomIn, blockPos.down(6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot3(worldIn, randomIn, blockPos.down(7), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot3(worldIn, randomIn, blockPos.down(8), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot3(worldIn, randomIn, blockPos.down(9), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.smallRoot4(worldIn, randomIn, blockPos.down(10), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.smallRoot4(worldIn, randomIn, blockPos.down(11), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
  }

  public void growMediumRoots(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                              MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    this.genRing11(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.mediumRoot1(worldIn, randomIn, blockPos.down(1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot1(worldIn, randomIn, blockPos.down(2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot1(worldIn, randomIn, blockPos.down(3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.mediumRoot2(worldIn, randomIn, blockPos.down(4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot2(worldIn, randomIn, blockPos.down(5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.mediumRoot3(worldIn, randomIn, blockPos.down(6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot3(worldIn, randomIn, blockPos.down(7), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot3(worldIn, randomIn, blockPos.down(8), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot3(worldIn, randomIn, blockPos.down(9), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.mediumRoot4(worldIn, randomIn, blockPos.down(10), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot4(worldIn, randomIn, blockPos.down(11), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.mediumRoot5(worldIn, randomIn, blockPos.down(12), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot5(worldIn, randomIn, blockPos.down(13), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.mediumRoot5(worldIn, randomIn, blockPos.down(14), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
  }

  public void growBigRoots(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                           MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    this.genRing13(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot1(worldIn, randomIn, blockPos.down(1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot1(worldIn, randomIn, blockPos.down(2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot1(worldIn, randomIn, blockPos.down(3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot2(worldIn, randomIn, blockPos.down(4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot2(worldIn, randomIn, blockPos.down(5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot3(worldIn, randomIn, blockPos.down(6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot3(worldIn, randomIn, blockPos.down(7), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot3(worldIn, randomIn, blockPos.down(8), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot3(worldIn, randomIn, blockPos.down(9), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot4(worldIn, randomIn, blockPos.down(10), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot4(worldIn, randomIn, blockPos.down(11), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot5(worldIn, randomIn, blockPos.down(12), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot5(worldIn, randomIn, blockPos.down(13), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot5(worldIn, randomIn, blockPos.down(14), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.bigRoot6(worldIn, randomIn, blockPos.down(15), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot6(worldIn, randomIn, blockPos.down(16), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot6(worldIn, randomIn, blockPos.down(17), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.bigRoot6(worldIn, randomIn, blockPos.down(18), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
  }
  // ROOTS END

  // RINGS START
  public boolean genRing13(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                           MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(6, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean genRing12(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                           MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean genRing11(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                           MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public void genRing10(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                        MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void genRing9(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void genRing8(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void genRing7(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public boolean genRing6(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                          MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean genRing5(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                          MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setHeart(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean genRing3(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                          MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setHeart(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(0, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setBark(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setBark(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  // RINGS END

  // SMALL ROOTS START

  public boolean smallRoot1(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                            MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean smallRoot2(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                            MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean smallRoot3(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                            MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean smallRoot4(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                            MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  // SMALL ROOTS END
  // MEDIUM ROOTS START

  public boolean mediumRoot1(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean mediumRoot2(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean mediumRoot3(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean mediumRoot4(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public boolean mediumRoot5(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                             MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  //MEDIUM ROOTS END
  //BIG ROOTS START

  public boolean bigRoot1(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                          MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-6, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-6, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(6, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(6, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(6, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(6, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }

    return true;
  }

  public void bigRoot2(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 6), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, -1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(5, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void bigRoot3(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 2), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void bigRoot4(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 5), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(4, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void bigRoot5(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(1, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(3, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  public void bigRoot6(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
                       MutableBoundingBox mutableBoundingBoxIn, RedwoodTreeFeatureConfig treeFeatureConfigIn) {
    if (!isBedrockAt(worldIn, blockPos) && blockPos.getY() > 0) {
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(-2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, -4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 3), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      this.setRoot(worldIn, randomIn, blockPos.add(2, 0, 4), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    }
  }

  // BIG ROOTS END

  static class FoliageCoordinates extends BlockPos {

    private final int branchBase;

    public FoliageCoordinates(BlockPos pos, int branchBase) {
      super(pos.getX(), pos.getY(), pos.getZ());
      this.branchBase = branchBase;
    }

    public int getBranchBase() {
      return this.branchBase;
    }
  }
}
