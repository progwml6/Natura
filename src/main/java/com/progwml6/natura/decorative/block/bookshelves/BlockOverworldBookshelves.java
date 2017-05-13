package com.progwml6.natura.decorative.block.bookshelves;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockOverworldBookshelves extends EnumBlock<BlockOverworldPlanks.PlankType>
{
    public static PropertyEnum<BlockOverworldPlanks.PlankType> TYPE = PropertyEnum.create("type", BlockOverworldPlanks.PlankType.class);

    public BlockOverworldBookshelves()
    {
        super(Material.WOOD, TYPE, BlockOverworldPlanks.PlankType.class);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.5F);
        this.setCreativeTab(NaturaRegistry.tabDecorative);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drops =  new ArrayList<>();
        drops.add(new ItemStack(Items.BOOK, 3));
        return drops;
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return 1;
    }
}
