package com.progwml6.natura.library;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.progwml6.natura.Natura;
import com.progwml6.natura.world.NaturaWorld;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

public class Util
{
    public static final String MODID = "natura";

    public static final String RESOURCE = MODID.toLowerCase(Locale.US);

    public static Logger getLogger(String type)
    {
        String log = MODID;

        return LogManager.getLogger(log + "-" + type);
    }

    /**
     * Returns the given Resource prefixed with tinkers resource location. Use this function instead of hardcoding resource locations.
     */
    public static String resource(String res)
    {
        return String.format("%s:%s", RESOURCE, res);
    }

    public static ResourceLocation getResource(String res)
    {
        return new ResourceLocation(RESOURCE, res);
    }

    /**
     * Prefixes the given unlocalized name with tinkers prefix. Use this when passing unlocalized names for a uniform namespace.
     */
    public static String prefix(String name)
    {
        return String.format("%s.%s", RESOURCE, name.toLowerCase(Locale.US));
    }

    public static ResourceLocation getItemLocation(Item item)
    {
        @SuppressWarnings("deprecation")
        Object o = GameData.getItemRegistry().getNameForObject(item);

        if (o == null)
        {
            Natura.log.error("Item %s is not registered!" + item.getUnlocalizedName());
            return null;
        }

        return (ResourceLocation) o;
    }

    public static boolean isWorldLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaWorld.PulseId);
    }
}
