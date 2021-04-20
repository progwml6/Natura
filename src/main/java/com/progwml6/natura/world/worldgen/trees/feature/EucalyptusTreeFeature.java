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

public class EucalyptusTreeFeature extends GenericOverworldTreeFeature {

  public EucalyptusTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public void place(IWorldGenerationReader generationReader, Random rand, int height, BlockPos position, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseOverworldTreeFeatureConfig configIn) {
    setDirt(generationReader, position.down());

    this.placeTrunk(generationReader, rand, height, position, logPositions, boundingBoxIn, configIn);

    this.placeBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.NORTH);
    this.placeBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.SOUTH);
    this.placeBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.EAST);
    this.placeBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.WEST);

    this.placeStraightBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.NORTH);
    this.placeStraightBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.SOUTH);
    this.placeStraightBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.EAST);
    this.placeStraightBranch(generationReader, rand, height, position, logPositions, foliagePositions, boundingBoxIn, configIn, BranchDirection.WEST);

    this.placeNode(generationReader, rand, position.up(height), logPositions, foliagePositions, boundingBoxIn, configIn);
  }

  protected void placeTrunk(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> logPositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    while (treeHeight > 0) {
      addLogWithCheck(worldIn, randomIn, blockPos, logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);

      blockPos = blockPos.up();
      treeHeight--;
    }
  }

  protected void placeBranch(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn, BranchDirection direction) {
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
        addLogWithCheck(worldIn, randomIn, blockPos1.down(), logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      addLogWithCheck(worldIn, randomIn, blockPos1, logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);

      if (bIter == 1) {
        this.placeNode(worldIn, randomIn, blockPos1, logPositions, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      heightShift = randomIn.nextInt(6);
    }
  }

  protected void placeStraightBranch(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn, BranchDirection direction) {
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
        addLogWithCheck(worldIn, randomIn, blockPos1.down(), logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      addLogWithCheck(worldIn, randomIn, blockPos1, logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);

      if (j2 == 1) {
        this.placeNode(worldIn, randomIn, blockPos1, logPositions, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }

      heightShift = randomIn.nextInt(6);
    }
  }

  protected void placeNode(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    addLogWithCheck(worldIn, randomIn, blockPos, logPositions, mutableBoundingBoxIn, treeFeatureConfigIn);

    placeNode(worldIn, randomIn, blockPos, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn, 2, 1);

    placeNode(worldIn, randomIn, blockPos, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn, 1, 2);

    for (int xIter = blockPos.getX() - 1; xIter <= blockPos.getX() + 1; xIter++) {
      for (int zIter = blockPos.getZ() - 1; zIter <= blockPos.getZ() + 1; zIter++) {
        BlockPos newPos = new BlockPos(xIter, blockPos.getY() + 1, zIter);
        addLeafWithCheck(worldIn, randomIn, newPos, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
    }
  }

  private void placeNode(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> foliagePositions, MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn, int i, int i2) {
    for (int xIter = blockPos.getX() - i; xIter <= blockPos.getX() + i; xIter++) {
      for (int zIter = blockPos.getZ() - i2; zIter <= blockPos.getZ() + i2; zIter++) {
        BlockPos newPos = new BlockPos(xIter, blockPos.getY(), zIter);
        addLeafWithCheck(worldIn, randomIn, newPos, foliagePositions, mutableBoundingBoxIn, treeFeatureConfigIn);
      }
    }
  }

  public enum BranchDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST
  }
}
