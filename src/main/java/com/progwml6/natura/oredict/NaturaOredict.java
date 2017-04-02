package com.progwml6.natura.oredict;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.glass.BlockNetherGlass;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaOredict.PulseId, description = "Everything that has to do with the ore dictionary", forced = true)
public class NaturaOredict extends NaturaPulse
{
    public static final String PulseId = "NaturaOredict";

    static final Logger log = Util.getLogger(PulseId);

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        ensureOredict();
        registerCommons();
        registerOverworld();
        registerNether();
        registerModCompat();
    }

    private static void ensureOredict()
    {
        oredict(Items.BOWL, "bowlWood");
        oredict(Blocks.CHEST, "chestWood");
        oredict(Blocks.TRAPPED_CHEST, "chestWood");
    }

    private static void registerCommons()
    {
        oredict(NaturaCommons.bloodwood_emptybowl, "bowlWood");
        oredict(NaturaCommons.ghostwood_emptybowl, "bowlWood");
        oredict(NaturaCommons.darkwood_emptybowl, "bowlWood");
        oredict(NaturaCommons.fusewood_emptybowl, "bowlWood");

        oredict(NaturaCommons.barleyFlour, "foodFlour");
        oredict(NaturaCommons.wheatFlour, "foodFlour");

        oredict(NaturaCommons.barley, "cropBarley");
        oredict(NaturaCommons.cotton, "cropCotton");

        oredict(NaturaCommons.barley, "listAllgrain");
        oredict(NaturaCommons.cotton, "foodEqualswheat");

        oredict(NaturaCommons.blueDye, "dyeBlue");

        oredict(NaturaCommons.sulfurPowder, "dustSulphur");
        oredict(NaturaCommons.sulfurPowder, "dustSulfur");
    }

    private static void registerOverworld()
    {
        if (isOverworldLoaded())
        {
            if (Config.dropBarley)
            {
                MinecraftForge.addGrassSeed(NaturaOverworld.barley_seeds, 3);
            }
            if (Config.dropCotton)
            {
                MinecraftForge.addGrassSeed(NaturaOverworld.cotton_seeds, 3);
            }

            //Food
            oredict(NaturaCommons.raspberry, "cropRaspberry");
            oredict(NaturaCommons.blueberry, "cropBlueberry");
            oredict(NaturaCommons.blackberry, "cropBlackberry");
            oredict(NaturaCommons.maloberry, "cropMaloberry");

            oredict(NaturaOverworld.overworldPlanks, "plankWood");

            //Logs
            oredict(NaturaOverworld.overworldLog, "logWood");
            oredict(NaturaOverworld.overworldLog2, "logWood");
            oredict(NaturaOverworld.redwoodLog, "logWood");
            //Slabs
            oredict(NaturaOverworld.overworldSlab, "slabWood");
            oredict(NaturaOverworld.overworldSlab2, "slabWood");
            //Saplings
            oredict(NaturaOverworld.overworldSapling, "treeSapling");
            oredict(NaturaOverworld.overworldSapling2, "treeSapling");
            oredict(NaturaOverworld.redwoodSapling, "treeSapling");
            //Leaves
            oredict(NaturaOverworld.overworldLeaves, "treeLeaves");
            oredict(NaturaOverworld.overworldLeaves2, "treeLeaves");
            oredict(NaturaOverworld.redwoodLeaves, "treeLeaves");

            //Stairs
            oredict(NaturaOverworld.overworldStairsAmaranth, "stairWood");
            oredict(NaturaOverworld.overworldStairsEucalyptus, "stairWood");
            oredict(NaturaOverworld.overworldStairsHopseed, "stairWood");
            oredict(NaturaOverworld.overworldStairsMaple, "stairWood");
            oredict(NaturaOverworld.overworldStairsRedwood, "stairWood");
            oredict(NaturaOverworld.overworldStairsSakura, "stairWood");
            oredict(NaturaOverworld.overworldStairsSilverbell, "stairWood");
            oredict(NaturaOverworld.overworldStairsTiger, "stairWood");
            oredict(NaturaOverworld.overworldStairsWillow, "stairWood");

            oredict(NaturaOverworld.barley_seeds, "seedBarley");
            oredict(NaturaOverworld.cotton_seeds, "seedCotton");

            oredict(NaturaOverworld.barley_seeds, "listAllseed");
            oredict(NaturaOverworld.cotton_seeds, "listAllseed");
        }
    }

    private static void registerNether()
    {
        //Nether
        if (isNetherLoaded())
        {
            oredict(NaturaCommons.blightberry, "cropBlightberry");
            oredict(NaturaCommons.duskberry, "cropDuskberry");
            oredict(NaturaCommons.skyberry, "cropSkyberry");
            oredict(NaturaCommons.stingberry, "cropStingberry");

            //Planks
            oredict(NaturaNether.netherPlanks, "plankWood");
            //Logs
            oredict(NaturaNether.netherLog, "logWood");
            //Slabs
            oredict(NaturaNether.netherSlab, "slabWood");
            //Saplings
            oredict(NaturaNether.netherSapling, "treeSapling");
            //Leaves
            oredict(NaturaNether.netherLeaves, "treeLeaves");
            oredict(NaturaNether.netherLeaves2, "treeLeaves");
            //Stairs
            oredict(NaturaNether.netherStairsBloodwood, "stairWood");
            oredict(NaturaNether.netherStairsDarkwood, "stairWood");
            oredict(NaturaNether.netherStairsGhostwood, "stairWood");
            oredict(NaturaNether.netherStairsFusewood, "stairWood");

            oredict(NaturaNether.netherTaintedSoil, "taintedSoil");

            //Glass
            oredict(new ItemStack(NaturaNether.netherGlass, 1, BlockNetherGlass.GlassType.SOUL.getMeta()), "glassSoul");//meta 0
            oredict(NaturaNether.netherGlass, "glass");
        }
    }

    private static void registerModCompat()
    {
        oredict(NaturaCommons.barley, "listAllGrain");

        oredict(NaturaCommons.wheatFlour, "foodEqualswheat");
    }

    public static void oredict(Item item, String... name)
    {
        oredict(item, OreDictionary.WILDCARD_VALUE, name);
    }

    public static void oredict(Block block, String... name)
    {
        oredict(block, OreDictionary.WILDCARD_VALUE, name);
    }

    public static void oredict(Item item, int meta, String... name)
    {
        oredict(new ItemStack(item, 1, meta), name);
    }

    public static void oredict(Block block, int meta, String... name)
    {
        oredict(new ItemStack(block, 1, meta), name);
    }

    public static void oredict(ItemStack stack, String... names)
    {
        if (stack != null && stack.getItem() != null)
        {
            for (String name : names)
            {
                OreDictionary.registerOre(name, stack);
            }
        }
    }
}
