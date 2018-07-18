package com.progwml6.natura.oredict;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.decorative.NaturaDecorative;
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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaOredict.PulseId, description = "Everything that has to do with the ore dictionary", forced = true)
public class NaturaOredict extends NaturaPulse
{
    public static final String PulseId = "NaturaOredict";

    static final Logger log = Util.getLogger(PulseId);

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        ensureOredict();

        registerCommons();
        registerOverworld();
        registerNether();
        registerDecorative();
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

        oredict(NaturaCommons.barley, "cropBarley");
        oredict(NaturaCommons.cotton, "cropCotton");

        oredict(NaturaCommons.barleyFlour, "foodFlour");
        oredict(NaturaCommons.wheatFlour, "foodFlour");

        oredict(NaturaCommons.barley, "listAllgrain");
        oredict(NaturaCommons.wheatFlour, "foodEqualswheat");

        oredict(NaturaCommons.blueDye, "dyeBlue");

        oredict(NaturaCommons.sulfurPowder, "dustSulphur");
        oredict(NaturaCommons.sulfurPowder, "dustSulfur");

        oredict(NaturaCommons.sticks, "stickWood");
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

            // Food
            oredict(NaturaCommons.raspberry, "cropRaspberry");
            oredict(NaturaCommons.raspberry, "listAllfruit");
            oredict(NaturaCommons.blueberry, "cropBlueberry");
            oredict(NaturaCommons.blueberry, "listAllfruit");
            oredict(NaturaCommons.blackberry, "cropBlackberry");
            oredict(NaturaCommons.blackberry, "listAllfruit");
            oredict(NaturaCommons.maloberry, "cropMaloberry");
            oredict(NaturaCommons.maloberry, "listAllfruit");

            // Planks
            oredict(NaturaOverworld.overworldPlanks, "plankWood");

            // Logs
            oredict(NaturaOverworld.overworldLog, "logWood");
            oredict(NaturaOverworld.overworldLog2, "logWood");
            oredict(NaturaOverworld.redwoodLog, "logWood");

            // Slabs
            oredict(NaturaOverworld.overworldSlab, "slabWood");
            oredict(NaturaOverworld.overworldSlab2, "slabWood");

            // Saplings
            oredict(NaturaOverworld.overworldSapling, "treeSapling");
            oredict(NaturaOverworld.overworldSapling2, "treeSapling");
            oredict(NaturaOverworld.redwoodSapling, "treeSapling");

            // Leaves
            oredict(NaturaOverworld.overworldLeaves, "treeLeaves");
            oredict(NaturaOverworld.overworldLeaves2, "treeLeaves");
            oredict(NaturaOverworld.redwoodLeaves, "treeLeaves");

            // Stairs
            oredict(NaturaOverworld.overworldStairsAmaranth, "stairWood");
            oredict(NaturaOverworld.overworldStairsEucalyptus, "stairWood");
            oredict(NaturaOverworld.overworldStairsHopseed, "stairWood");
            oredict(NaturaOverworld.overworldStairsMaple, "stairWood");
            oredict(NaturaOverworld.overworldStairsRedwood, "stairWood");
            oredict(NaturaOverworld.overworldStairsSakura, "stairWood");
            oredict(NaturaOverworld.overworldStairsSilverbell, "stairWood");
            oredict(NaturaOverworld.overworldStairsTiger, "stairWood");
            oredict(NaturaOverworld.overworldStairsWillow, "stairWood");

            // Seeds
            oredict(NaturaOverworld.barley_seeds, "seedBarley");
            oredict(NaturaOverworld.cotton_seeds, "seedCotton");
            oredict(NaturaOverworld.barley_seeds, "listAllseed");
            oredict(NaturaOverworld.cotton_seeds, "listAllseed");

            // Bookshelves
            oredict(NaturaDecorative.overworldBookshelves, "bookshelf");
        }
    }

    private static void registerNether()
    {
        // Nether
        if (isNetherLoaded())
        {
            oredict(NaturaCommons.blightberry, "cropBlightberry");
            oredict(NaturaCommons.blightberry, "listAllfruit");
            oredict(NaturaCommons.duskberry, "cropDuskberry");
            oredict(NaturaCommons.duskberry, "listAllfruit");
            oredict(NaturaCommons.skyberry, "cropSkyberry");
            oredict(NaturaCommons.skyberry, "listAllfruit");
            oredict(NaturaCommons.stingberry, "cropStingberry");
            oredict(NaturaCommons.stingberry, "listAllfruit");

            // Tained Soil
            oredict(NaturaNether.netherTaintedSoil, "taintedSoil");

            // Planks
            oredict(NaturaNether.netherPlanks, "plankWood");

            // Logs
            oredict(NaturaNether.netherLog, "logWood");
            oredict(NaturaNether.netherLog2, "logWood");

            // Slabs
            oredict(NaturaNether.netherSlab, "slabWood");

            // Saplings
            oredict(NaturaNether.netherSapling, "treeSapling");

            // Leaves
            oredict(NaturaNether.netherLeaves, "treeLeaves");
            oredict(NaturaNether.netherLeaves2, "treeLeaves");

            // Stairs
            oredict(NaturaNether.netherStairsBloodwood, "stairWood");
            oredict(NaturaNether.netherStairsDarkwood, "stairWood");
            oredict(NaturaNether.netherStairsGhostwood, "stairWood");
            oredict(NaturaNether.netherStairsFusewood, "stairWood");

            // Glass
            oredict(NaturaNether.netherGlass, BlockNetherGlass.GlassType.SOUL.getMeta(), "glassSoul");
            oredict(NaturaNether.netherGlass, "glass");

            // Vines
            OreDictionary.registerOre("cropVine", new ItemStack(NaturaNether.netherThornVines));

            // Bookshelves
            oredict(NaturaDecorative.netherBookshelves, "bookshelf");
        }
    }

    private static void registerDecorative()
    {
        // Decorative
        if (isDecorativeLoaded())
        {
            // Overworld
            if (isOverworldLoaded())
            {
                oredict(NaturaDecorative.overworldWorkbenches, "crafterWood");
                oredict(NaturaDecorative.overworldWorkbenches, "craftingTableWood");
            }

            // Nether
            if (isNetherLoaded())
            {
                oredict(NaturaDecorative.netherWorkbenches, "crafterWood");
                oredict(NaturaDecorative.netherWorkbenches, "craftingTableWood");
            }
        }
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
        if (!stack.isEmpty() && stack.getItem() != Items.AIR)
        {
            for (String name : names)
            {
                OreDictionary.registerOre(name, stack);
            }
        }
    }
}
