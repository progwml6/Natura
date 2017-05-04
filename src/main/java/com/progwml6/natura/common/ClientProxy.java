package com.progwml6.natura.common;

import com.progwml6.natura.library.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class ClientProxy extends CommonProxy
{
    protected void registerItemModelNatura(ItemStack item, String name)
    {
        if (!item.isEmpty() && !StringUtils.isNullOrEmpty(name))
        {
            ModelRegisterUtil.registerItemModel(item, Util.getResource(name));
        }
    }

    @Override
    public boolean fancyGraphicsEnabled()
    {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }
}
