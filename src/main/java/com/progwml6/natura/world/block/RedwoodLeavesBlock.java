package com.progwml6.natura.world.block;

import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RedwoodLeavesBlock extends LeavesBlock {

  public static final IntegerProperty DISTANCE = NaturaWorld.REDWOOD_TREE_DISTANCE;

  public RedwoodLeavesBlock(Properties properties) {
    super(properties);

    this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 25).with(PERSISTENT, Boolean.FALSE));
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
    return VoxelShapes.empty();
  }

  /**
   * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
   * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
   */
  @Override
  public boolean ticksRandomly(BlockState state) {
    return state.get(DISTANCE) == 25 && !state.get(PERSISTENT);
  }

  /**
   * Performs a random tick on a block.
   */
  @Override
  public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    if (!state.get(PERSISTENT) && state.get(DISTANCE) == 25) {
      spawnDrops(state, worldIn, pos);
      worldIn.removeBlock(pos, false);
    }
  }

  @Override
  public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
  }

  @Override
  public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
    return 1;
  }

  /**
   * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
   * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
   * returns its solidified counterpart.
   * Note that this method should ideally consider only the specific face passed in.
   */
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos,
    BlockPos facingPos) {
    int i = getDistance(facingState) + 1;

    if (i != 1 || stateIn.get(DISTANCE) != i) {
      worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
    }

    return stateIn;
  }

  private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
    int i = 25;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (Direction direction : Direction.values()) {
      mutable.setAndMove(pos, direction);
      i = Math.min(i, getDistance(worldIn.getBlockState(mutable)) + 1);
      if (i == 1) {
        break;
      }
    }

    return state.with(DISTANCE, i);
  }

  private static int getDistance(BlockState neighbor) {
    if (NaturaTags.Blocks.REDWOOD_LOGS.contains(neighbor.getBlock())) {
      return 0;
    }
    else {
      return neighbor.getBlock() instanceof RedwoodLeavesBlock ? neighbor.get(DISTANCE) : 25;
    }
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(LeavesBlock.DISTANCE, DISTANCE, PERSISTENT);
  }
}
