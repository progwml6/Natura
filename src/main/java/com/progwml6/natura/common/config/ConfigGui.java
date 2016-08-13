package com.progwml6.natura.common.config;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.progwml6.natura.library.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(GuiScreen parentScreen)
    {
        super(parentScreen, getConfigElements(), Util.MODID, false, false, Util.prefix("configgui.title"));
    }

    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement(Config.Worldgen));

        return list;
    }

    public static class ConfigGuiFactory implements IModGuiFactory
    {
        @Override
        public void initialize(Minecraft minecraftInstance)
        {
        }

        @Override
        public Class<? extends GuiScreen> mainConfigGuiClass()
        {
            return ConfigGui.class;
        }

        @Override
        public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
        {
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
        {
            return null;
        }

    }
}
