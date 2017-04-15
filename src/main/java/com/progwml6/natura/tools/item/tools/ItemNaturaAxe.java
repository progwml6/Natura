package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemAxe;

public class ItemNaturaAxe extends ItemAxe
{
    private static final float[] ATTACK_DAMAGES = new float[] { 6.0F, 8.0F, 8.0F, 8.0F, 6.0F };

    private static final float[] ATTACK_SPEEDS = new float[] { -3.2F, -3.2F, -3.1F, -3.0F, -3.0F };

    public ItemNaturaAxe(ToolMaterial toolMaterialIn, int harvestLevelIn, float damageIn, float speedIn)
    {
        super(toolMaterialIn, damageIn, speedIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);

        this.setHarvestLevel("axe", harvestLevelIn);
    }

    public ItemNaturaAxe(ToolMaterial toolMaterialIn, int harvestLevelIn)
    {
        this(toolMaterialIn, harvestLevelIn, ATTACK_DAMAGES[toolMaterialIn.ordinal()], ATTACK_SPEEDS[toolMaterialIn.ordinal()]);
    }
}
