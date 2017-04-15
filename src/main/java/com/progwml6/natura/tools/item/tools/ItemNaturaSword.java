package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemSword;

public class ItemNaturaSword extends ItemSword
{
    public ItemNaturaSword(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);
        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
