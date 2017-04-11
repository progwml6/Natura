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

import net.minecraft.block.Block;
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
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
