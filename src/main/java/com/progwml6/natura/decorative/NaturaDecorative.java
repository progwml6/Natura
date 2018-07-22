package com.progwml6.natura.decorative;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.block.base.BlockButtonBase;
import com.progwml6.natura.common.block.base.BlockFenceBase;
import com.progwml6.natura.common.block.base.BlockFenceGateBase;
import com.progwml6.natura.common.block.base.BlockPressurePlateBase;
import com.progwml6.natura.common.block.base.BlockTrapDoorBase;
import com.progwml6.natura.decorative.block.bookshelves.BlockNetherBookshelves;
import com.progwml6.natura.decorative.block.bookshelves.BlockOverworldBookshelves;
import com.progwml6.natura.decorative.block.workbenches.BlockNetherWorkbenches;
import com.progwml6.natura.decorative.block.workbenches.BlockOverworldWorkbenches;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.library.enums.WoodTypes;
import com.progwml6.natura.library.enums.WoodTypes.WorldType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
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

    @SubscribeEvent
    public void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Blocks Start
        if (isOverworldLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.OVERWORLD)
                {
                    buttons[type.ordinal()] = registerBlock(registry, new BlockButtonBase(), type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerBlock(registry, new BlockPressurePlateBase(), type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerBlock(registry, new BlockTrapDoorBase(), type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerBlock(registry, new BlockFenceBase(), type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerBlock(registry, new BlockFenceGateBase(), type.getName() + "_fence_gate");
                }
            }

            overworldBookshelves = registerBlock(registry, new BlockOverworldBookshelves(), "overworld_bookshelves");
            overworldWorkbenches = registerBlock(registry, new BlockOverworldWorkbenches(), "overworld_workbenches");
        }

        if (isNetherLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.NETHER)
                {
                    buttons[type.ordinal()] = registerBlock(registry, new BlockButtonBase(), type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerBlock(registry, new BlockPressurePlateBase(), type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerBlock(registry, new BlockTrapDoorBase(), type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerBlock(registry, new BlockFenceBase(), type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerBlock(registry, new BlockFenceGateBase(), type.getName() + "_fence_gate");
                }
            }

            netherBookshelves = registerBlock(registry, new BlockNetherBookshelves(), "nether_bookshelves");
            netherWorkbenches = registerBlock(registry, new BlockNetherWorkbenches(), "nether_workbenches");
        }
        // Blocks End
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Blocks Start
        if (isOverworldLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.OVERWORLD)
                {
                    buttons[type.ordinal()] = registerItemBlock(registry, buttons[type.ordinal()], type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerItemBlock(registry, pressurePlates[type.ordinal()], type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerItemBlock(registry, trapDoors[type.ordinal()], type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerItemBlock(registry, fences[type.ordinal()], type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerItemBlock(registry, fenceGates[type.ordinal()], type.getName() + "_fence_gate");
                }
            }

            overworldBookshelves = registerEnumItemBlock(registry, overworldBookshelves, "overworld_bookshelves");
            overworldWorkbenches = registerEnumItemBlock(registry, overworldWorkbenches, "overworld_workbenches");
        }

        if (isNetherLoaded())
        {
            for (WoodTypes type : WoodTypes.values())
            {
                if (type.getWorldType() == WorldType.NETHER)
                {
                    buttons[type.ordinal()] = registerItemBlock(registry, buttons[type.ordinal()], type.getName() + "_button");
                    pressurePlates[type.ordinal()] = registerItemBlock(registry, pressurePlates[type.ordinal()], type.getName() + "_pressure_plate");
                    trapDoors[type.ordinal()] = registerItemBlock(registry, trapDoors[type.ordinal()], type.getName() + "_trap_door");
                    fences[type.ordinal()] = registerItemBlock(registry, fences[type.ordinal()], type.getName() + "_fence");
                    fenceGates[type.ordinal()] = registerItemBlock(registry, fenceGates[type.ordinal()], type.getName() + "_fence_gate");
                }
            }

            netherBookshelves = registerEnumItemBlock(registry, netherBookshelves, "nether_bookshelves");
            netherWorkbenches = registerEnumItemBlock(registry, netherWorkbenches, "nether_workbenches");
        }
        // Blocks End
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
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
