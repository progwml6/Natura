package com.progwml6.natura.overworld.item;

import java.util.List;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.LocUtils;

@SuppressWarnings("deprecation")
public class ItemSaguaroFruit extends ItemSeedFood
{
    public final Block crop;

    public ItemSaguaroFruit(int healAmount, float saturation, Block crop)
    {
        super(healAmount, saturation, crop, Blocks.FARMLAND);

        this.crop = crop;
        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (playerIn.canPlayerEdit(pos, facing, stack) && playerIn.canPlayerEdit(pos.up(), facing, stack))
            {
                IBlockState state = worldIn.getBlockState(pos);
                Block block = state.getBlock();

                if (block != null && block.canSustainPlant(state, worldIn, pos, EnumFacing.UP, (IPlantable) this.crop) && worldIn.isAirBlock(pos.up()))
                {
                    worldIn.setBlockState(pos.up(), this.crop.getDefaultState());
                    --stack.stackSize;
                    return EnumActionResult.SUCCESS;
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }

            return EnumActionResult.FAIL;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        addOptionalTooltip(stack, tooltip);
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    public static void addOptionalTooltip(ItemStack stack, List<String> tooltip)
    {
        if (I18n.canTranslate(stack.getUnlocalizedName() + ".tooltip"))
        {
            tooltip.addAll(LocUtils.getTooltips(TextFormatting.GRAY.toString() +
                    LocUtils.translateRecursive(stack.getUnlocalizedName() + ".tooltip")));
        }
        else if (I18n.canTranslate(stack.getUnlocalizedName() + ".tooltip"))
        {
            tooltip.addAll(LocUtils.getTooltips(
                    TextFormatting.GRAY.toString() + LocUtils.translateRecursive(stack.getUnlocalizedName() + ".tooltip")));
        }
    }

}
