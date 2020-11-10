package com.progwml6.natura.world.block;

import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class OverworldLeavesBlock extends LeavesBlock {

  public static final IntegerProperty DISTANCE = NaturaWorld.EXTENDED_TREE_DISTANCE;
  private final TreeType treeType;

  public OverworldLeavesBlock(TreeType treeType, Properties properties) {
    super(properties);

    this.treeType = treeType;

    this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, treeType.getMaxDistance()).with(PERSISTENT, Boolean.FALSE));
  }

  /**
   * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
   * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
   */
  @Override
  public boolean ticksRandomly(BlockState state) {
    return state.get(DISTANCE) == treeType.getMaxDistance() && !state.get(PERSISTENT);
  }

  /**
   * Performs a random tick on a block.
   */
  @Override
  public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    if (!state.get(PERSISTENT) && state.get(DISTANCE) == treeType.getMaxDistance()) {
      spawnDrops(state, worldIn, pos);
      worldIn.removeBlock(pos, false);
    }
  }

  @Override
  public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    worldIn.setBlockState(pos, updateDistance(state, worldIn, pos, this.treeType), 3);
  }

  /**
   * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
   * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
   * returns its solidified counterpart.
   * Note that this method should ideally consider only the specific face passed in.
   */
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    int i = getDistance(facingState, this.treeType) + 1;

    if (i != 1 || stateIn.get(DISTANCE) != i) {
      worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
    }

    return stateIn;
  }

  private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos, TreeType type) {
    int i = type.getMaxDistance();
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (Direction direction : Direction.values()) {
      mutable.setAndMove(pos, direction);
      i = Math.min(i, getDistance(worldIn.getBlockState(mutable), type) + 1);
      if (i == 1) {
        break;
      }
    }

    return state.with(DISTANCE, i);
  }

  private static int getDistance(BlockState neighbor, TreeType type) {
    if (BlockTags.LOGS.contains(neighbor.getBlock())) {
      return 0;
    }
    else {
      return neighbor.getBlock() instanceof OverworldLeavesBlock ? neighbor.get(DISTANCE) : type.getMaxDistance();
    }
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(LeavesBlock.DISTANCE, DISTANCE, PERSISTENT);
  }
}
