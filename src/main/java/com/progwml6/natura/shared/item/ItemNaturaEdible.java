package com.progwml6.natura.shared.item;

import java.util.List;

import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import slimeknights.mantle.item.ItemEdible;

public class ItemNaturaEdible extends ItemEdible
{
    protected TIntIntHashMap itemDuration;

    public boolean displayCustomEffectsTooltip; // set to false to not display effects of food in tooltip

    public ItemNaturaEdible()
    {
        super();
        this.displayEffectsTooltip = false;
        this.displayCustomEffectsTooltip = true;
        itemDuration = new TIntIntHashMap();
    }

    public ItemStack addFood(int meta, int food, float saturation, int duration, String name, PotionEffect... effects)
    {
        return addFood(meta, food, saturation, duration, name, effects.length > 0, effects);
    }

    public ItemStack addFood(int meta, int food, float saturation, int duration, String name, boolean alwaysEdible, PotionEffect... effects)
    {
        itemDuration.put(meta, duration);
        return addFood(meta, food, saturation, name, alwaysEdible, effects);
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return itemDuration.get(stack.getMetadata());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);

        // effect info
        if (this.displayCustomEffectsTooltip)
        {
            for (PotionEffect potionEffect : this.potionEffects.get(stack.getMetadata()))
            {
                if (potionEffect.getPotion().isBadEffect())
                {
                    tooltip.add(TextFormatting.RED + I18n.translateToLocal(potionEffect.getEffectName()).trim());
                }
                else
                {
                    tooltip.add(TextFormatting.BLUE + I18n.translateToLocal(potionEffect.getEffectName()).trim());
                }
            }
        }
    }
}
