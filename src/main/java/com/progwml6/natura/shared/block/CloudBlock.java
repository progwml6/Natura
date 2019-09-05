package com.progwml6.natura.shared.block;

import com.progwml6.natura.library.NaturaRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class CloudBlock extends Block {

  protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

  private final boolean isSulfurCloud;

  public CloudBlock() {
    this(false);
  }

  public CloudBlock(boolean isSulfurCloud) {
    super(Block.Properties.create(NaturaRegistry.CLOUD).hardnessAndResistance(0.3F).sound(SoundType.CLOTH));
    this.isSulfurCloud = isSulfurCloud;
  }

  @Override
  public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
    if (this.isSulfurCloud && entityIn instanceof AbstractArrowEntity && !worldIn.isRemote) {
      AbstractArrowEntity abstractArrowEntity = (AbstractArrowEntity) entityIn;
      Entity entity = abstractArrowEntity.getShooter();

      if (abstractArrowEntity.isBurning()) {
        this.explode(worldIn, pos, 1, entity instanceof LivingEntity ? (LivingEntity) entity : null);

        worldIn.removeBlock(pos, false);

        return;
      }
    }

    Vec3d vec3d = entityIn.getMotion();
    if (vec3d.y < 0.0D) {
      entityIn.setMotion(vec3d.x, vec3d.y * 0.005D, vec3d.z);
    }

    entityIn.fallDistance = 0.0F;
  }

  @Override
  @Deprecated
  public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    ItemStack itemstack = player.getHeldItem(handIn);

    if (!itemstack.isEmpty()) {
      if (this.isSulfurCloud && itemstack.getItem() != Items.AIR && itemstack.getItem() == Items.FLINT_AND_STEEL) {
        worldIn.removeBlock(pos, false);

        this.explode(worldIn, pos, 1, player);

        return true;
      }
    }

    return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
  }

  @Override
  public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {

  }

  private void explode(World world, BlockPos pos, int size, @Nullable LivingEntity living) {
    world.createExplosion(living, pos.getX(), pos.getY(), pos.getZ(), size, true, Explosion.Mode.BREAK);
  }

  /**
   * Return whether this block can drop from an explosion.
   */
  @Override
  public boolean canDropFromExplosion(Explosion explosionIn) {
    return false;
  }

  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
    return adjacentBlockState.getMaterial() != this.material && super.isSideInvisible(state, adjacentBlockState, side);
  }

  @Override
  @Deprecated
  public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
    Material material = worldIn.getBlockState(pos).getMaterial();

    return material != this.material && super.isNormalCube(state, worldIn, pos);
  }

  @Override
  @Deprecated
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    if (worldIn.getBlockState(pos.down()).getBlock() instanceof CloudBlock) {
      return VoxelShapes.empty();
    }
    else {
      return SHAPE;
    }
  }
}
