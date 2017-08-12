package com.progwml6.natura.entities;

import java.util.Set;

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
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.crafting.FurnaceRecipes;
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
        EntityRegistry.registerModEntity(Util.getResource("imp"), EntityImp.class, "imp", EntityIDs.IMP, Natura.instance, 32, 5, true, 0xF29735, 0x2E1F10);
        LootTableList.register(EntityImp.LOOT_TABLE);

        if (Config.enableHeatscarSpider)
        {
            EntityRegistry.registerModEntity(Util.getResource("heatscarspider"), EntityHeatscarSpider.class, "heatscarspider", EntityIDs.HEATSCARSPIDER, Natura.instance, 32, 5, true, 0xE64D10, 0x57B1BD);
            EntityRegistry.registerModEntity(Util.getResource("babyheatscarspider"), EntityBabyHeatscarSpider.class, "babyheatscarspider", EntityIDs.BABYHEATSCARSPIDER, Natura.instance, 32, 5, true, 0xE64D10, 0x57B1BD);
            LootTableList.register(EntityHeatscarSpider.LOOT_TABLE);
        }

        EntityRegistry.registerModEntity(Util.getResource("nitrocreeper"), EntityNitroCreeper.class, "nitrocreeper", EntityIDs.NITROCREEPER, Natura.instance, 64, 5, true, 0xF73E6C, 0x9B5004);

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerSmelting();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        //TODO add way to exclude some of these
        Set<Biome> biomeList = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER);

        Biome[] biomes = biomeList.toArray(new Biome[biomeList.size()]);

        EntityRegistry.addSpawn(EntityImp.class, 10, 8, 12, EnumCreatureType.CREATURE, biomes);

        if (Config.enableHeatscarSpider)
        {
            EntityRegistry.addSpawn(EntityHeatscarSpider.class, 10, 4, 4, EnumCreatureType.MONSTER, biomes);
            EntityRegistry.addSpawn(EntityBabyHeatscarSpider.class, 10, 4, 4, EnumCreatureType.MONSTER, biomes);
        }

        EntityRegistry.addSpawn(EntityNitroCreeper.class, 8, 4, 6, EnumCreatureType.MONSTER, biomes);

        proxy.postInit();
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        if (isEntitiesLoaded())
        {
            furnaceRecipes.addSmeltingRecipe(NaturaCommons.impmeatRaw.copy(), NaturaCommons.impmeatCooked.copy(), 0.2F);
        }
    }
}
