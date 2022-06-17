package com.progwml6.natura.world.block;

import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class RedwoodLeavesBlock extends LeavesBlock {

  public static final IntegerProperty DISTANCE = NaturaWorld.REDWOOD_TREE_DISTANCE;

  public RedwoodLeavesBlock(Properties properties) {
    super(properties);

    this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 25).setValue(PERSISTENT, Boolean.FALSE));
  }

  /**
   * @return whether this block needs random ticking.
   */
  @Override
  public boolean isRandomlyTicking(BlockState pState) {
    return pState.getValue(DISTANCE) == 25 && !pState.getValue(PERSISTENT);
  }


  /**
   * Performs a random tick on a block.
   */
  @Override
  public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
    if (!pState.getValue(PERSISTENT) && pState.getValue(DISTANCE) == 25) {
      dropResources(pState, pLevel, pPos);
      pLevel.removeBlock(pPos, false);
    }
  }

  @Override
  public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
    pLevel.setBlock(pPos, updateDistance(pState, pLevel, pPos), 3);
  }

  /**
   * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
   * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
   * returns its solidified counterpart.
   * Note that this method should ideally consider only the specific direction passed in.
   */
  public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
    int i = getDistanceAt(pFacingState) + 1;
    if (i != 1 || pState.getValue(DISTANCE) != i) {
      pLevel.scheduleTick(pCurrentPos, this, 1);
    }

    return pState;
  }

  private static BlockState updateDistance(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
    int i = 25;
    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

    for (Direction direction : Direction.values()) {
      mutable.setWithOffset(pPos, direction);
      i = Math.min(i, getDistanceAt(pLevel.getBlockState(mutable)) + 1);
      if (i == 1) {
        break;
      }
    }

    return pState.setValue(DISTANCE, i);
  }

  private static int getDistanceAt(BlockState pNeighbor) {
    if (pNeighbor.is(NaturaWorld.redwood.getLogBlockTag())) {
      return 0;
    } else {
      return pNeighbor.getBlock() instanceof OverworldLeavesBlock ? pNeighbor.getValue(DISTANCE) : 25;
    }
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(LeavesBlock.DISTANCE, DISTANCE, PERSISTENT);
  }
}
