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

public class EucalyptusTreeFeature extends Feature<BaseOverworldTreeFeatureConfig> {

  public EucalyptusTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
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
      VoxelShapePart voxelshapepart = this.createVoxelShape(seedReader, mutableboundingbox, set, set2);
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

        this.placeBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.NORTH);
        this.placeBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.SOUTH);
        this.placeBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.EAST);
        this.placeBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.WEST);

        this.placeStraightBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.NORTH);
        this.placeStraightBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.SOUTH);
        this.placeStraightBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.EAST);
        this.placeStraightBranch(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn, BranchDirection.WEST);

        this.placeNode(generationReader, rand, blockPos.up(height), trunkBlockPosSet, boundingBoxIn, configIn);
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

  protected void placeBranch(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn, BranchDirection direction) {
    int posX = blockPos.getX();
    int posY = blockPos.getY() + treeHeight - 3;
    int posZ = blockPos.getZ();
    byte byte0 = 0;
    byte byte1 = 0;

    switch (direction) {
      case NORTH:
        byte0 = 1;
        byte1 = 1;
        break;

      case SOUTH:
        byte0 = -1;
        byte1 = 1;
        break;

      case EAST:
        byte0 = 1;
        byte1 = -1;
        break;

      case WEST:
        byte0 = -1;
        byte1 = -1;
        break;
    }

    int heightShift = randomIn.nextInt(6);

    for (int bIter = 4; bIter > 0; bIter--) {
      if (heightShift % 3 != 0) {
        posX += byte0;
      }

      if (heightShift % 3 != 1) {
        posZ += byte1;
      }

      int branch = heightShift % 3;

      posY += branch;

      BlockPos blockPos1 = new BlockPos(posX, posY, posZ);

      if (branch == 2) {
        this.setLog(worldIn, randomIn, blockPos1.down(), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      this.setLog(worldIn, randomIn, blockPos1, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      if (bIter == 1) {
        this.placeNode(worldIn, randomIn, blockPos1, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      heightShift = randomIn.nextInt(6);
    }
  }

  protected void placeStraightBranch(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn, BranchDirection direction) {
    int posX = blockPos.getX();
    int posY = blockPos.getY() + treeHeight - 3;
    int posZ = blockPos.getZ();

    byte xShift = 0;
    byte zShift = 0;

    switch (direction) {
      case NORTH:
        xShift = 1;
        zShift = 0;
        break;

      case SOUTH:
        xShift = 0;
        zShift = 1;
        break;

      case EAST:
        xShift = -1;
        zShift = 0;
        break;

      case WEST:
        xShift = 0;
        zShift = -1;
        break;
    }

    int heightShift = randomIn.nextInt(6);

    for (int j2 = 4; j2 > 0; j2--) {
      if (xShift == 0) {
        posX = (posX + randomIn.nextInt(3)) - 1;
        posZ += zShift;
      }

      if (zShift == 0) {
        posX += xShift;
        posZ = (posZ + randomIn.nextInt(3)) - 1;
      }

      int branch = heightShift % 3;

      posY += branch;

      BlockPos blockPos1 = new BlockPos(posX, posY, posZ);

      if (branch == 2) {
        this.setLog(worldIn, randomIn, blockPos1.down(), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      this.setLog(worldIn, randomIn, blockPos1, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

      if (j2 == 1) {
        this.placeNode(worldIn, randomIn, blockPos1, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      heightShift = randomIn.nextInt(6);
    }
  }

  protected void placeNode(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    this.setLog(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    for (int xIter = blockPos.getX() - 2; xIter <= blockPos.getX() + 2; xIter++) {
      for (int zIter = blockPos.getZ() - 1; zIter <= blockPos.getZ() + 1; zIter++) {
        BlockPos newPos = new BlockPos(xIter, blockPos.getY(), zIter);
        this.setLeaf(worldIn, randomIn, newPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
    }

    for (int xIter = blockPos.getX() - 1; xIter <= blockPos.getX() + 1; xIter++) {
      for (int zIter = blockPos.getZ() - 2; zIter <= blockPos.getZ() + 2; zIter++) {
        BlockPos newPos = new BlockPos(xIter, blockPos.getY(), zIter);
        this.setLeaf(worldIn, randomIn, newPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
    }

    for (int xIter = blockPos.getX() - 1; xIter <= blockPos.getX() + 1; xIter++) {
      for (int zIter = blockPos.getZ() - 1; zIter <= blockPos.getZ() + 1; zIter++) {
        BlockPos newPos = new BlockPos(xIter, blockPos.getY() + 1, zIter);
        this.setLeaf(worldIn, randomIn, newPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
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

  public enum BranchDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST
  }
}
