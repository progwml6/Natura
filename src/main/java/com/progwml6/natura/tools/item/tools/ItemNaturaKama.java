package com.progwml6.natura.tools.item.tools;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import slimeknights.mantle.item.ItemTooltip;

public class ItemNaturaKama extends ItemSword
{
    public ItemNaturaKama(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        Block block = blockIn.getBlock();

        return block == Blocks.WEB || block == Blocks.REDSTONE_WIRE || block == Blocks.TRIPWIRE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        return block != Blocks.WEB && state.getMaterial() != Material.LEAVES ? (block == Blocks.WOOL ? 5.0F : super.getDestroySpeed(stack, state)) : 15.0F;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand)
    {
        if (entity.world.isRemote)
        {
            return false;
        }
        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable) entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);

            if (target.isShearable(itemstack, entity.world, pos))
            {
                List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

                Random rand = new Random();

                for (ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);

                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }

                itemstack.damageItem(1, entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        if (player.world.isRemote || player.capabilities.isCreativeMode)
        {
            return false;
        }

        Block block = player.world.getBlockState(pos).getBlock();

        if (block instanceof IShearable)
        {
            IShearable target = (IShearable) block;

            if (target.isShearable(itemstack, player.world, pos))
            {
                List<ItemStack> drops = target.onSheared(itemstack, player.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));
                Random rand = new Random();

                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;

                    EntityItem entityitem = new EntityItem(player.world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, stack);

                    entityitem.setDefaultPickupDelay();

                    player.world.spawnEntity(entityitem);
                }

                itemstack.damageItem(1, player);

                player.addStat(StatList.getBlockStats(block));
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flag)
    {
        ItemTooltip.addOptionalTooltip(stack, tooltip);

        super.addInformation(stack, worldIn, tooltip, flag);
    }
}
