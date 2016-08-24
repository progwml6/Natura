package com.progwml6.natura.common.config;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.progwml6.natura.library.Util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.config.ForgeCFG;

public final class Config
{
    public static ForgeCFG pulseConfig = new ForgeCFG("NaturaModules", "Modules");

    public static Config instance = new Config();

    public static Logger log = Util.getLogger("Config");

    private Config()
    {
    }

    //@formatter:off
    public static boolean enableRetrogen = true;

    public static int babyHeatscarMinimum = 2;
    public static int babyHeatscarMaximum = 4;

    static Configuration configFile;

    static ConfigCategory Gameplay;
    static ConfigCategory Entities;
    static ConfigCategory Worldgen;
    static ConfigCategory Clouds;
    //@formatter:on

    public static void load(FMLPreInitializationEvent event)
    {
        configFile = new Configuration(event.getSuggestedConfigurationFile(), "0.1", false);

        MinecraftForge.EVENT_BUS.register(instance);

        syncConfig();
    }

    @SubscribeEvent
    public void update(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Util.MODID))
        {
            syncConfig();
        }
    }

    public static boolean syncConfig()
    {
        Property prop;
        // Worldgen
        {
            String cat = "worldgen";
            List<String> propOrder = Lists.newArrayList();
            Worldgen = configFile.getCategory(cat);

            prop = configFile.get(cat, "enableRetrogen", enableRetrogen);
            prop.setComment("Retroactive Generation");
            enableRetrogen = prop.getBoolean();
            propOrder.add(prop.getName());

            Worldgen.setPropertyOrder(propOrder);
        }
        // Entities
        {
            String cat = "entites";
            List<String> propOrder = Lists.newArrayList();
            Worldgen = configFile.getCategory(cat);

            prop = configFile.get(cat, "babyHeatscarMinimum", babyHeatscarMinimum);
            prop.setComment("Minimum Baby Heatscar Spiders on Spider Death");
            babyHeatscarMinimum = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "babyHeatscarMaximum", babyHeatscarMaximum);
            prop.setComment("Maximum Baby Heatscar Spiders on Spider Death");
            babyHeatscarMaximum = prop.getInt();
            propOrder.add(prop.getName());

            Worldgen.setPropertyOrder(propOrder);
        }

        // save changes if any
        boolean changed = false;
        if (configFile.hasChanged())
        {
            configFile.save();
            changed = true;
        }
        return changed;
    }
}
