package com.progwml6.natura.entities;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.EntityIDs;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.entities.entity.monster.EntityBabyHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityNitroCreeper;
import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.Util;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaEntities.PulseId, description = "The entites added by Natura")
public class NaturaEntities extends NaturaPulse
{
    public static final String PulseId = "NaturaEntities";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.entities.EntitiesClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        EntityRegistry.registerModEntity(EntityImp.class, "imp", EntityIDs.IMP, Natura.instance, 32, 5, true, 0xF29735, 0x2E1F10);
        LootTableList.register(EntityImp.LOOT_TABLE);

        if (Config.enableHeatscarSpider)
        {
            EntityRegistry.registerModEntity(EntityHeatscarSpider.class, "heatscarspider", EntityIDs.HEATSCARSPIDER, Natura.instance, 32, 5, true, 0xE64D10, 0x57B1BD);
            EntityRegistry.registerModEntity(EntityBabyHeatscarSpider.class, "babyheatscarspider", EntityIDs.BABYHEATSCARSPIDER, Natura.instance, 32, 5, true, 0xE64D10, 0x57B1BD);
            LootTableList.register(EntityHeatscarSpider.LOOT_TABLE);
        }

        EntityRegistry.registerModEntity(EntityNitroCreeper.class, "nitrocreeper", EntityIDs.NITROCREEPER, Natura.instance, 64, 5, true, 0xF73E6C, 0x9B5004);

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        //TODO add way to exclude some of these
        Biome[] nether = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);

        EntityRegistry.addSpawn(EntityImp.class, 10, 8, 12, EnumCreatureType.CREATURE, nether);
        if (Config.enableHeatscarSpider)
        {
            EntityRegistry.addSpawn(EntityHeatscarSpider.class, 10, 4, 4, EnumCreatureType.MONSTER, nether);
            EntityRegistry.addSpawn(EntityBabyHeatscarSpider.class, 10, 4, 4, EnumCreatureType.MONSTER, nether);
        }
        EntityRegistry.addSpawn(EntityNitroCreeper.class, 8, 4, 6, EnumCreatureType.MONSTER, nether);

        proxy.postInit();
    }
}
