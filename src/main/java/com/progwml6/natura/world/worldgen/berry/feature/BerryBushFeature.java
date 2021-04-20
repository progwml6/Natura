package com.progwml6.natura.world.worldgen.berry.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.progwml6.natura.world.worldgen.berry.config.BerryBushFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
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

import java.util.Random;
import java.util.Set;

public class BerryBushFeature extends Feature<BerryBushFeatureConfig> {

  public BerryBushFeature(Codec<BerryBushFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BerryBushFeatureConfig config) {
    Set<BlockPos> bushBlockPosSet = Sets.newHashSet();
    MutableBoundingBox boundingBox = MutableBoundingBox.getNewBoundingBox();

    boolean flag = this.place(seedReader, random, blockPos, bushBlockPosSet, boundingBox, config);

    if (boundingBox.minX <= boundingBox.maxX && flag && !bushBlockPosSet.isEmpty()) {
      VoxelShapePart voxelshapepart = this.createVoxelShape(boundingBox, bushBlockPosSet);
      Template.updatePostProcessing(seedReader, 3, voxelshapepart, boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      return true;
    }
    else {
      return false;
    }
  }

  private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> blockPosSet,
    MutableBoundingBox boundingBoxIn, BerryBushFeatureConfig configIn) {

    if (!(generationReader instanceof IWorld)) {
      return false;
    }

    int oceanFloorHeight = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();
    BlockPos blockPos = new BlockPos(positionIn.getX(), oceanFloorHeight, positionIn.getZ());

    if (blockPos.getY() >= 1) {
      int size = rand.nextInt(10);

      if (size == 9) {

      }
      else if (size >= 7) {

      }
      else if (size >= 3) {

      }
      else {

      }

      return true;
    }
    else {
      return false;
    }
  }

  protected void generateTinyNode(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> bushBlockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BerryBushFeatureConfig berryBushFeatureConfigIn) {

  }

  protected boolean setBush(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> bushBlockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, BerryBushFeatureConfig configIn) {
    if (!isAirOrLeavesAt(worldIn, blockPos)) {
      return false;
    }
    else {
      //this.setBlockState(worldIn, blockPos, configIn.bushProvider.getBlockState(randomIn, blockPos).with());
      mutableBoundingBoxIn.expandTo(new MutableBoundingBox(blockPos, blockPos));
      bushBlockPosSet.add(blockPos.toImmutable());
      return true;
    }
  }

  public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader reader, BlockPos blockPos) {
    return reader.hasBlockState(blockPos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
  }

  public static void setBlockStateAt(IWorldWriter writer, BlockPos blockPos, BlockState state) {
    writer.setBlockState(blockPos, state, 19);
  }

  private VoxelShapePart createVoxelShape(MutableBoundingBox boundingBox, Set<BlockPos> bushBlockPosSet) {
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (BlockPos blockPos : Lists.newArrayList(bushBlockPosSet)) {
      if (boundingBox.isVecInside(blockPos)) {
        voxelShapePart
          .setFilled(blockPos.getX() - boundingBox.minX, blockPos.getY() - boundingBox.minY, blockPos.getZ() - boundingBox.minZ, true, true);
      }
    }

    return voxelShapePart;
  }
}
