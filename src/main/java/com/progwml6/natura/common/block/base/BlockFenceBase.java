package com.progwml6.natura.common.block.base;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFenceBase extends BlockFence
{
    public BlockFenceBase()
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facingIn)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facingIn);
        Block block = iblockstate.getBlock();
        boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE && (iblockstate.getMaterial() == this.blockMaterial || block instanceof BlockFenceGate);
        return !isExcepBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
    }

    protected static boolean isExcepBlockForAttachWithPiston(Block blockIn)
    {
        return Block.isExceptBlockForAttachWithPiston(blockIn) || blockIn == Blocks.BARRIER || blockIn == Blocks.MELON_BLOCK || blockIn == Blocks.PUMPKIN || blockIn == Blocks.LIT_PUMPKIN;
    }
}
