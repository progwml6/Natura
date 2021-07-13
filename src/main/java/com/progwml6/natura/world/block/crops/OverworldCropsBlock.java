package com.progwml6.natura.world.block.crops;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public abstract class OverworldCropsBlock extends BushBlock implements IGrowable {

  public OverworldCropsBlock(AbstractBlock.Properties builder) {
    super(builder);
    this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), 0));
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
  }

  @Override
  protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
    return state.matchesBlock(Blocks.FARMLAND);
  }

  protected abstract IntegerProperty getAgeProperty();

  public abstract int getMaxAge();

  public abstract int getBoneMealMinAge();

  public abstract int getBoneMealMaxAge();

  protected int getAge(BlockState state) {
    return state.get(this.getAgeProperty());
  }

  public BlockState withAge(int age) {
    return this.getDefaultState().with(this.getAgeProperty(), age);
  }

  public boolean isMaxAge(BlockState state) {
    return state.get(this.getAgeProperty()) >= this.getMaxAge();
  }

  /**
   * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
   * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
   */
  @Override
  public boolean ticksRandomly(BlockState state) {
    return !this.isMaxAge(state);
  }

  /**
   * Performs a random tick on a block.
   */
  @Override
  public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

    if (worldIn.getLightSubtracted(pos, 0) >= 9) {
      int i = this.getAge(state);

      if (i < this.getMaxAge()) {
        float f = getGrowthChance(this, worldIn, pos);

        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
          worldIn.setBlockState(pos, this.withAge(i + 1), 2);
          net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
      }
    }
  }

  public void grow(World worldIn, BlockPos pos, BlockState state) {
    int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
    int j = this.getMaxAge();

    if (i > j) {
      i = j;
    }

    worldIn.setBlockState(pos, this.withAge(i), 2);
  }

  protected int getBonemealAgeIncrease(World worldIn) {
    return MathHelper.nextInt(worldIn.rand, this.getBoneMealMinAge(), this.getBoneMealMaxAge());
  }

  protected static float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {
    float f = 1.0F;
    BlockPos blockpos = pos.down();

    for (int i = -1; i <= 1; ++i) {
      for (int j = -1; j <= 1; ++j) {
        float f1 = 0.0F;
        BlockState blockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

        if (blockstate.canSustainPlant(worldIn, blockpos.add(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
          f1 = 1.0F;

          if (blockstate.isFertile(worldIn, pos.add(i, 0, j))) {
            f1 = 3.0F;
          }
        }

        if (i != 0 || j != 0) {
          f1 /= 4.0F;
        }

        f += f1;
      }
    }

    BlockPos north = pos.north();
    BlockPos south = pos.south();
    BlockPos west = pos.west();
    BlockPos east = pos.east();
    boolean flag = blockIn == worldIn.getBlockState(west).getBlock() || blockIn == worldIn.getBlockState(east).getBlock();
    boolean flag1 = blockIn == worldIn.getBlockState(north).getBlock() || blockIn == worldIn.getBlockState(south).getBlock();

    if (flag && flag1) {
      f /= 2.0F;
    } else {
      boolean flag2 = blockIn == worldIn.getBlockState(west.north()).getBlock() || blockIn == worldIn.getBlockState(east.north()).getBlock() || blockIn == worldIn.getBlockState(east.south()).getBlock() || blockIn == worldIn.getBlockState(west.south()).getBlock();

      if (flag2) {
        f /= 2.0F;
      }
    }

    return f;
  }

  @Override
  public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
    return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && super.isValidPosition(state, worldIn, pos);
  }

  @Override
  public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
    if (entityIn instanceof RavagerEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
      worldIn.destroyBlock(pos, true, entityIn);
    }

    super.onEntityCollision(state, worldIn, pos, entityIn);
  }

  protected abstract IItemProvider getSeedsItem();

  @Override
  public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
    return new ItemStack(this.getSeedsItem());
  }

  /**
   * Whether this IGrowable can grow
   */
  @Override
  public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
    return !this.isMaxAge(state);
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    this.grow(worldIn, pos, state);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(this.getAgeProperty());
  }

  @Override
  public PlantType getPlantType(IBlockReader world, BlockPos pos) {
    return PlantType.CROP;
  }
}
