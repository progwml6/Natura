package com.progwml6.natura.tools.item.bows;

import javax.annotation.Nullable;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNaturaBow extends ItemBow
{
    private Item bow;

    public ItemNaturaBow(int damage, Item bow)
    {
        super();
        this.setMaxDamage(damage);
        this.setCreativeTab(NaturaRegistry.tabGeneral);
        this.setFull3D();
        this.bow = bow;

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemNaturaBow.this.updatePullProperty(stack, worldIn, entityIn);
            }
        });

        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    private float updatePullProperty(ItemStack stack, World worldIn, EntityLivingBase entityIn)
    {
        if (entityIn == null)
        {
            return 0.0F;
        }
        else
        {
            ItemStack itemstack = entityIn.getActiveItemStack();
            return !itemstack.isEmpty() && itemstack.getItem() == this.bow ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
        }
    }
}
