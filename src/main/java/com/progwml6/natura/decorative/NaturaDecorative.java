package com.progwml6.natura.decorative;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.decorative.block.bookshelves.BlockNetherBookshelves;
import com.progwml6.natura.decorative.block.bookshelves.BlockOverworldBookshelves;
import com.progwml6.natura.decorative.block.workbenches.BlockNetherWorkbenches;
import com.progwml6.natura.decorative.block.workbenches.BlockOverworldWorkbenches;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.library.enums.WoodTypes;
import com.progwml6.natura.library.enums.WoodTypes.WorldType;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaDecorative.PulseId, description = "Everything that's decorative for Natura. (bookshelfs, etc)")
public class NaturaDecorative extends NaturaPulse
{
    public static final String PulseId = "NaturaDecorative";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.decorative.DecorativeClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static Block[] buttons = new Block[WoodTypes.values().length];
    public static Block[] pressurePlates = new Block[WoodTypes.values().length];
    public static Block[] trapDoors = new Block[WoodTypes.values().length];
    public static Block[] fences = new Block[WoodTypes.values().length];
    public static Block[] fenceGates = new Block[WoodTypes.values().length];
    
    public static BlockOverworldBookshelves overworldBookshelves;
    public static BlockNetherBookshelves netherBookshelves;
    
    public static BlockOverworldWorkbenches overworldWorkbenches;
    public static BlockNetherWorkbenches netherWorkbenches;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        if (isOverworldLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.OVERWORLD)
                {
                    buttons[type.ordinal()] = registerBlockButton(type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerBlockPressurePlate(type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerBlockTrapDoor(type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerBlockFence(type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerBlockFenceGate(type.getName() + "_fence_gate");
                }
            }

            overworldBookshelves = registerEnumBlock(new BlockOverworldBookshelves(), "overworld_bookshelves");
            overworldWorkbenches = registerEnumBlock(new BlockOverworldWorkbenches(), "overworld_workbenches");
        }

        if (isNetherLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.NETHER)
                {
                    buttons[type.ordinal()] = registerBlockButton(type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerBlockPressurePlate(type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerBlockTrapDoor(type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerBlockFence(type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerBlockFenceGate(type.getName() + "_fence_gate");
                }
            }

            netherBookshelves = registerEnumBlock(new BlockNetherBookshelves(), "nether_bookshelves");
            netherWorkbenches = registerEnumBlock(new BlockNetherWorkbenches(), "nether_workbenches");
        }

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerRecipes();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    private void registerRecipes()
    {
        if (isOverworldLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.OVERWORLD)
                {
                    addShapedRecipe(new ItemStack(buttons[type.ordinal()], 1), "#", '#', type.getOverworldPlankType());
                    addShapedRecipe(new ItemStack(pressurePlates[type.ordinal()], 1), "##", '#', type.getOverworldPlankType());
                    addShapedRecipe(new ItemStack(trapDoors[type.ordinal()], 2), "###", "###", '#', type.getOverworldPlankType());
                    addShapedRecipe(new ItemStack(fences[type.ordinal()], 2), "###", "###", '#', type.getStickItemStack());
                    addShapedRecipe(new ItemStack(fenceGates[type.ordinal()], 1), "s#s", "s#s", '#', type.getOverworldPlankType(), 's', type.getStickItemStack());
                }
            }

            for (BlockOverworldPlanks.PlankType type : BlockOverworldPlanks.PlankType.values())
            {
                addShapedRecipe(new ItemStack(overworldWorkbenches, 1, type.getMeta()), "##", "##", '#', new ItemStack(NaturaOverworld.overworldPlanks, 1, type.getMeta()));
                addShapedRecipe(new ItemStack(overworldBookshelves, 1, type.getMeta()), "###", "bbb", "###", '#', new ItemStack(NaturaOverworld.overworldPlanks, 1, type.getMeta()), 'b', Items.BOOK);
            }
        }

        if (isNetherLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.NETHER)
                {
                    addShapedRecipe(new ItemStack(buttons[type.ordinal()], 1), "#", '#', type.getNetherPlankType());
                    addShapedRecipe(new ItemStack(pressurePlates[type.ordinal()], 1), "##", '#', type.getNetherPlankType());
                    addShapedRecipe(new ItemStack(trapDoors[type.ordinal()], 2), "###", "###", '#', type.getNetherPlankType());
                    addShapedRecipe(new ItemStack(fences[type.ordinal()], 2), "###", "###", '#', type.getStickItemStack());
                    addShapedRecipe(new ItemStack(fenceGates[type.ordinal()], 1), "s#s", "s#s", '#', type.getNetherPlankType(), 's', type.getStickItemStack());
                }
            }

            for (BlockNetherPlanks.PlankType type : BlockNetherPlanks.PlankType.values())
            {
                addShapedRecipe(new ItemStack(netherWorkbenches, 1, type.getMeta()), "##", "##", '#', new ItemStack(NaturaNether.netherPlanks, 1, type.getMeta()));
                addShapedRecipe(new ItemStack(netherBookshelves, 1, type.getMeta()), "###", "bbb", "###", '#', new ItemStack(NaturaNether.netherPlanks, 1, type.getMeta()), 'b', Items.BOOK);
            }
        }
    }
}
