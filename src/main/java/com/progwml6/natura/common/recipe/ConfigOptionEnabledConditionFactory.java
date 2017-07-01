package com.progwml6.natura.common.recipe;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.progwml6.natura.common.config.Config;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ConfigOptionEnabledConditionFactory implements IConditionFactory
{
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json)
    {
        String configSetting = JsonUtils.getString(json, "config_setting", "");

        switch (configSetting)
        {
        case "enableWheatRecipe":
            return () -> Config.enableWheatRecipe;
        default:
            return () -> false;
        }
    }
}
