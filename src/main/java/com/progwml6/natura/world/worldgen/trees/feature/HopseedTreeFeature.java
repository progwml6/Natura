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

public class HopseedTreeFeature extends Feature<BaseOverworldTreeFeatureConfig> {

  public HopseedTreeFeature(Codec<BaseOverworldTreeFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public final boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos,
    BaseOverworldTreeFeatureConfig config) {
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
        this.setDirtAt(generationReader, blockPos.down().east(), blockPos);
        this.setDirtAt(generationReader, blockPos.down().south(), blockPos);
        this.setDirtAt(generationReader, blockPos.down().south().east(), blockPos);

        this.placeTrunk(generationReader, rand, blockPos, trunkBlockPosSet, boundingBoxIn, configIn);

        this.placeCanopy(generationReader, rand, height, blockPos, trunkBlockPosSet, boundingBoxIn, configIn);

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

  protected void placeTrunk(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
    this.setLog(worldIn, randomIn, blockPos, blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(+1, 0, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(0, 0, +1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(+1, 0, +1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);

    this.setLog(worldIn, randomIn, blockPos.add(0, +1, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(+1, +1, 0), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(0, +1, +1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
    this.setLog(worldIn, randomIn, blockPos.add(+1, +1, +1), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
  }

  protected void placeCanopy(IWorldGenerationReader worldIn, Random randomIn, int treeHeight, BlockPos blockPos, Set<BlockPos> blockPosSet,
    MutableBoundingBox mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
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
            this.setLeaf(worldIn, randomIn, new BlockPos(x, y, z), blockPosSet, mutableBoundingBoxIn, treeFeatureConfigIn);
          }
        }
      }
    }
  }

  protected boolean setLog(IWorldGenerationReader worldIn, Random randomIn, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
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

  protected boolean setLeaf(IWorldGenerationReader worldIn, Random random, BlockPos blockPos, Set<BlockPos> blockPosSet, MutableBoundingBox
    mutableBoundingBoxIn, BaseOverworldTreeFeatureConfig treeFeatureConfigIn) {
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

  private VoxelShapePart createVoxelShape(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> blockPosSet, Set<BlockPos> blockPosSet2) {
    List<Set<BlockPos>> list = Lists.newArrayList();
    VoxelShapePart voxelShapePart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());

    for (int j = 0; j < 10; ++j) {
      list.add(Sets.newHashSet());
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (BlockPos blockPos : Lists.newArrayList(blockPosSet2)) {
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
