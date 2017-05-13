package com.progwml6.natura.decorative.block.bookshelves;

import java.util.Random;

import javax.annotation.Nullable;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherBookshelves extends EnumBlock<BlockNetherPlanks.PlankType>
{
    public static PropertyEnum<BlockNetherPlanks.PlankType> TYPE = PropertyEnum.create("type", BlockNetherPlanks.PlankType.class);

    public BlockNetherBookshelves()
    {
        super(Material.WOOD, TYPE, BlockNetherPlanks.PlankType.class);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.5F);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return 3;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.BOOK;
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return 1;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
