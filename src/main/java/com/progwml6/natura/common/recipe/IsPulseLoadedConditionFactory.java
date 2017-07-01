package com.progwml6.natura.common.recipe;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.progwml6.natura.Natura;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class IsPulseLoadedConditionFactory implements IConditionFactory
{
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json)
    {
        String pulseName = JsonUtils.getString(json, "pulse_name");

        if (Natura.pulseManager.isPulseLoaded(pulseName))
        {
            return () -> true;
        }
        else
        {
            return () -> false;
        }
    }
}
