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

    public static boolean enableRetrogen = true;

    public static int whiteCloudSpawnRarity = 10;

    public static int whiteCloudSpawnHeight = 192;

    public static int whiteCloudSpawnRange = 48;

    public static int darkCloudSpawnRarity = 10;

    public static int darkCloudSpawnHeight = 0;

    public static int darkCloudSpawnRange = 256;

    public static int sulfurCloudSpawnRarity = 8;

    public static int sulfurCloudSpawnHeight = 40;

    public static int sulfurCloudSpawnRange = 78;

    public static int ashCloudSpawnRarity = 8;

    public static int ashCloudSpawnHeight = 40;

    public static int ashCloudSpawnRange = 78;

    public static int babyHeatscarMinimum = 2;

    public static int babyHeatscarMaximum = 4;

    static Configuration configFile;

    static ConfigCategory Gameplay;

    static ConfigCategory Entities;

    static ConfigCategory Worldgen;

    static ConfigCategory Clouds;

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
        // Cloud
        {
            String cat = "worldgen.clouds";
            List<String> propOrder = Lists.newArrayList();
            Clouds = configFile.getCategory(cat);

            // White Cloud
            prop = configFile.get(cat, "whiteCloudSpawnRarity", 10);
            prop.setComment("White Cloud Spawn Rarity");
            whiteCloudSpawnRarity = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "whiteCloudSpawnHeight", 192);
            prop.setComment("White Cloud Spawn Height");
            whiteCloudSpawnHeight = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "whiteCloudSpawnRange", 48);
            prop.setComment("White Cloud Spawn Range");
            whiteCloudSpawnRange = prop.getInt();
            propOrder.add(prop.getName());

            // Dark Cloud
            prop = configFile.get(cat, "darkCloudSpawnRarity", 10);
            prop.setComment("Dark Cloud Spawn Rarity");
            darkCloudSpawnRarity = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "darkCloudSpawnHeight", 0);
            prop.setComment("Dark Cloud Spawn Height");
            darkCloudSpawnHeight = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "darkCloudSpawnRange", 256);
            prop.setComment("Dark Cloud Spawn Range");
            darkCloudSpawnRange = prop.getInt();
            propOrder.add(prop.getName());

            // Sulfur Cloud
            prop = configFile.get(cat, "sulfurCloudSpawnRarity", 8);
            prop.setComment("Sulfur Cloud Spawn Rarity");
            sulfurCloudSpawnRarity = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "sulfurCloudSpawnHeight", 40);
            prop.setComment("Sulfur Cloud Spawn Height");
            sulfurCloudSpawnHeight = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "sulfurCloudSpawnRange", 78);
            prop.setComment("Sulfur Cloud Spawn Range");
            sulfurCloudSpawnRange = prop.getInt();
            propOrder.add(prop.getName());

            // Ash Cloud
            prop = configFile.get(cat, "ashCloudSpawnRarity", 8);
            prop.setComment("Ash Cloud Spawn Rarity");
            ashCloudSpawnRarity = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "ashCloudSpawnHeight", 40);
            prop.setComment("Ash Cloud Spawn Height");
            ashCloudSpawnHeight = prop.getInt();
            propOrder.add(prop.getName());
            prop = configFile.get(cat, "ashCloudSpawnRange", 78);
            prop.setComment("Ash Cloud Spawn Range");
            ashCloudSpawnRange = prop.getInt();
            propOrder.add(prop.getName());

            Clouds.setPropertyOrder(propOrder);
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
