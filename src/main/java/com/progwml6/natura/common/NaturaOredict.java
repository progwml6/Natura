package com.progwml6.natura.common;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.glass.BlockNetherGlass;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.pulsar.pulse.Pulse;

/**
 * Created by progwml6 on 9/19/16.
 */
@Pulse(id = NaturaOredict.PULSE_ID, forced = true)

public class NaturaOredict
{
    public static final String PULSE_ID = "NaturaOredict";

    @Subscribe
    public static void oreDictAllTheThings(FMLPreInitializationEvent event)
    {
        ensureOredict();
        registerCommons();
        registerOverworld();
        registerNether();
        registerModCompat();
    }
    /*
        //Crafting table
        OreDictionary.registerOre("crafterWood", new ItemStack(alternateWorkbench, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("craftingTableWood", new ItemStack(alternateWorkbench, 1, OreDictionary.WILDCARD_VALUE));


        //Stick
        OreDictionary.registerOre("stickWood", new ItemStack(stickItem, 1, OreDictionary.WILDCARD_VALUE));
     */

    // Things that are not from natura but should be oredicted
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
        //Food
        //overworld
        oredict(NaturaCommons.raspberry, "cropRaspberry");
        oredict(NaturaCommons.blueberry, "cropBlueberry");
        oredict(NaturaCommons.blackberry, "cropBlackberry");
        oredict(NaturaCommons.maloberry, "cropMaloberry");
        oredict(NaturaCommons.barleyFlour, "foodFlour");
        oredict(NaturaCommons.wheatFlour, "foodFlour");

        //TODO enable when barley plant addded
        //oredict(NaturaCommons.barleyPlant, "cropBarley");
        //TODO enable when cotton plant addded
        //oredict(NaturaCommons.cottonPlant, "cropCotton");

        //seeds
        //OreDictionary.registerOre("seedBarley", new ItemStack(seeds, 1, 0));
        //OreDictionary.registerOre("seedCotton", new ItemStack(seeds, 1, 1));

        //nether
        oredict(NaturaCommons.blightberry, "cropBlightberry");
        oredict(NaturaCommons.duskberry, "cropDuskberry");
        oredict(NaturaCommons.skyberry, "cropSkyberry");
        oredict(NaturaCommons.stingberry, "cropStingberry");

        //Dye
        oredict(NaturaCommons.blueDye, "dyeBlue");

        //Dusts
        oredict(NaturaCommons.sulfurPowder, "dustSulphur");
        oredict(NaturaCommons.sulfurPowder, "dustSulfur");

    }

    private static void registerOverworld()
    {

        //Planks
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
    }

    private static void registerNether()
    {
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

    //TODO 1.10 double check the objects in here and finish the rest
    private static void registerModCompat()
    {
        //For Harvestcraft
        //OreDictionary.registerOre("listAllseed", new ItemStack(seeds, 1, 0));
        //OreDictionary.registerOre("listAllseed", new ItemStack(seeds, 1, 1));
        oredict(NaturaCommons.wheatFlour, "foodEqualswheat");
        oredict(NaturaCommons.barley, "listAllGrain");//this name is wrong

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
