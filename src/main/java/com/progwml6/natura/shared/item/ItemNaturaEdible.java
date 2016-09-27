package com.progwml6.natura.shared.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import slimeknights.mantle.item.ItemEdible;

public class ItemNaturaEdible extends ItemEdible
{
    public boolean displayCustomEffectsTooltip; // set to false to not display effects of food in tooltip

    public ItemNaturaEdible()
    {
        super();
        this.displayEffectsTooltip = false;
        this.displayCustomEffectsTooltip = true;
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
                    tooltip.add("§4" + I18n.translateToLocal(potionEffect.getEffectName()).trim());
                }
                else
                {
                    tooltip.add("§9" + I18n.translateToLocal(potionEffect.getEffectName()).trim());
                }
            }
        }
    }
}
