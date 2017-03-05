package com.progwml6.natura.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNaturaDoor extends BlockDoor
{
    private ItemStack itemStack;

    public BlockNaturaDoor()
    {
        super(Material.WOOD);

        this.setHardness(3F);
        this.setSoundType(SoundType.WOOD);
        this.disableStats();
    }

    public void setDoor(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    protected ItemStack getDoor()
    {
        return this.itemStack.copy();
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.itemStack.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return this.itemStack;
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return this.itemStack.getMetadata();
    }
}
