package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemSpade;

public class ItemNaturaShovel extends ItemSpade
{
    public ItemNaturaShovel(ToolMaterial toolMaterialIn, int harvestLevelIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);

        this.setHarvestLevel("shovel", harvestLevelIn);
    }
}
