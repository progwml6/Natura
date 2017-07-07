package com.progwml6.natura.nether;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.block.BlockNaturaDoor;
import com.progwml6.natura.common.item.ItemBlockLeaves;
import com.progwml6.natura.common.item.ItemNaturaDoor;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.block.bush.BlockNetherBerryBush;
import com.progwml6.natura.nether.block.button.BlockNetherButton;
import com.progwml6.natura.nether.block.furnace.BlockNetherrackFurnace;
import com.progwml6.natura.nether.block.furnace.tile.TileEntityNetherrackFurnace;
import com.progwml6.natura.nether.block.glass.BlockNetherGlass;
import com.progwml6.natura.nether.block.hopper.BlockBlazeHopper;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.lever.BlockNetherLever;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.logs.BlockNetherLog2;
import com.progwml6.natura.nether.block.obelisk.BlockRespawnObelisk;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.nether.block.pressureplate.BlockNetherPressurePlate;
import com.progwml6.natura.nether.block.rail.BlockBlazeRail;
import com.progwml6.natura.nether.block.rail.BlockBlazeRailDetector;
import com.progwml6.natura.nether.block.rail.BlockBlazeRailPowered;
import com.progwml6.natura.nether.block.sand.BlockHeatSand;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling2;
import com.progwml6.natura.nether.block.shrooms.BlockNetherGlowshroom;
import com.progwml6.natura.nether.block.shrooms.BlockNetherLargeGlowshroom;
import com.progwml6.natura.nether.block.slabs.BlockNetherSlab;
import com.progwml6.natura.nether.block.soil.BlockTaintedSoil;
import com.progwml6.natura.nether.block.vine.BlockNetherThornVines;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaNether.PulseId, description = "All of the nether blocks including trees")
public class NaturaNether extends NaturaPulse
{
    public static final String PulseId = "NaturaNether";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.nether.NetherClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static BlockNetherLog netherLog;
    public static BlockNetherLog2 netherLog2;
    public static BlockNetherLeaves netherLeaves;
    public static BlockNetherLeaves2 netherLeaves2;
    public static BlockNetherSapling netherSapling;
    public static BlockNetherSapling2 netherSapling2;
    public static BlockNetherPlanks netherPlanks;
    public static BlockNetherSlab netherSlab;

    public static BlockHeatSand netherHeatSand;
    public static BlockTaintedSoil netherTaintedSoil;
    public static BlockNetherThornVines netherThornVines;

    public static BlockNetherGlass netherGlass;

    public static Block netherStairsGhostwood;
    public static Block netherStairsBloodwood;
    public static Block netherStairsDarkwood;
    public static Block netherStairsFusewood;

    public static BlockEnumBerryBush netherBerryBushBlightberry;
    public static BlockEnumBerryBush netherBerryBushDuskberry;
    public static BlockEnumBerryBush netherBerryBushSkyberry;
    public static BlockEnumBerryBush netherBerryBushStingberry;

    public static BlockNetherGlowshroom netherGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargeGreenGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargeBlueGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargePurpleGlowshroom;

    public static BlockRespawnObelisk respawnObelisk;

    public static BlockNaturaDoor ghostwoodDoor;
    public static BlockNaturaDoor bloodwoodDoor;

    public static BlockBlazeHopper blazeHopper;

    public static BlockNetherLever netherLever;

    public static BlockNetherButton netherButton;

    public static BlockNetherPressurePlate netherPressurePlate;

    public static BlockBlazeRail blazeRail;
    public static BlockBlazeRailPowered blazeRailPowered;
    public static BlockBlazeRailPowered blazeRailActivator;
    public static BlockBlazeRailDetector blazeRailDetector;

    public static BlockNetherrackFurnace netherrackFurnace;
    public static BlockNetherrackFurnace litNetherrackFurnace;

    // Items
    public static ItemNaturaDoor netherDoors;

    public static ItemStack ghostwood_door;
    public static ItemStack bloodwood_door;
    //@formatter:on

    @SubscribeEvent
    public void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Blocks Start
        netherLog = registerBlock(registry, new BlockNetherLog(), "nether_logs");
        netherLog2 = registerBlock(registry, new BlockNetherLog2(), "nether_logs2");

        netherLeaves = registerBlock(registry, new BlockNetherLeaves(), "nether_leaves");
        netherLeaves2 = registerBlock(registry, new BlockNetherLeaves2(), "nether_leaves2");

        netherSapling = registerBlock(registry, new BlockNetherSapling(), "nether_sapling");
        netherSapling2 = registerBlock(registry, new BlockNetherSapling2(), "nether_sapling2");

        netherPlanks = registerBlock(registry, new BlockNetherPlanks(), "nether_planks");

        netherSlab = registerBlock(registry, new BlockNetherSlab(), "nether_slab");

        netherHeatSand = registerBlock(registry, new BlockHeatSand(), "nether_heat_sand");
        netherTaintedSoil = registerBlock(registry, new BlockTaintedSoil(), "nether_tainted_soil");
        netherThornVines = registerBlock(registry, new BlockNetherThornVines(), "nether_thorn_vines");

        netherGlass = registerBlock(registry, new BlockNetherGlass(), "nether_glass");

        netherStairsGhostwood = registerBlockStairsFrom(registry, netherPlanks, BlockNetherPlanks.PlankType.GHOSTWOOD, "nether_stairs_ghostwood");
        netherStairsBloodwood = registerBlockStairsFrom(registry, netherPlanks, BlockNetherPlanks.PlankType.BLOODWOOD, "nether_stairs_bloodwood");
        netherStairsDarkwood = registerBlockStairsFrom(registry, netherPlanks, BlockNetherPlanks.PlankType.DARKWOOD, "nether_stairs_darkwood");
        netherStairsFusewood = registerBlockStairsFrom(registry, netherPlanks, BlockNetherPlanks.PlankType.FUSEWOOD, "nether_stairs_fusewood");

        netherBerryBushBlightberry = registerBlock(registry, new BlockNetherBerryBush(), "nether_berrybush_blightberry");
        netherBerryBushDuskberry = registerBlock(registry, new BlockNetherBerryBush(), "nether_berrybush_duskberry");
        netherBerryBushSkyberry = registerBlock(registry, new BlockNetherBerryBush(), "nether_berrybush_skyberry");
        netherBerryBushStingberry = registerBlock(registry, new BlockNetherBerryBush(), "nether_berrybush_stingberry");

        respawnObelisk = registerBlock(registry, new BlockRespawnObelisk(), "respawn_obelisk");

        netherGlowshroom = registerBlock(registry, new BlockNetherGlowshroom(), "nether_glowshroom");
        netherLargeGreenGlowshroom = registerBlock(registry, new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), "nether_green_large_glowshroom");
        netherLargeBlueGlowshroom = registerBlock(registry, new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta()), "nether_blue_large_glowshroom");
        netherLargePurpleGlowshroom = registerBlock(registry, new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), "nether_purple_large_glowshroom");

        ghostwoodDoor = registerBlock(registry, new BlockNaturaDoor(), "nether_door_ghostwood");
        bloodwoodDoor = registerBlock(registry, new BlockNaturaDoor(), "nether_door_bloodwood");

        blazeHopper = registerBlock(registry, new BlockBlazeHopper(), "blaze_hopper");

        netherLever = registerBlock(registry, new BlockNetherLever(), "nether_lever");

        netherButton = registerBlock(registry, new BlockNetherButton(), "nether_button");

        netherPressurePlate = registerBlock(registry, new BlockNetherPressurePlate(), "nether_pressure_plate");

        blazeRail = registerBlock(registry, new BlockBlazeRail(), "blaze_rail");
        blazeRailPowered = registerBlock(registry, new BlockBlazeRailPowered(false), "blaze_rail_golden");
        blazeRailActivator = registerBlock(registry, new BlockBlazeRailPowered(true), "blaze_rail_activator");
        blazeRailDetector = registerBlock(registry, new BlockBlazeRailDetector(), "blaze_rail_detector");

        netherrackFurnace = registerBlock(registry, new BlockNetherrackFurnace(false), "netherrack_furnace");
        litNetherrackFurnace = registerBlock(registry, new BlockNetherrackFurnace(true), "lit_netherrack_furnace");
        // Blocks End

        registerTE(TileEntityNetherrackFurnace.class, "netherrack_furnace");
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Blocks Start
        netherLog = registerEnumItemBlock(registry, netherLog, "nether_logs");
        netherLog2 = registerItemBlock(registry, netherLog2, "nether_logs2");

        netherLeaves = registerItemBlockProp(registry, new ItemBlockLeaves(netherLeaves), "nether_leaves", BlockNetherLeaves.TYPE);
        netherLeaves2 = registerItemBlockProp(registry, new ItemBlockLeaves(netherLeaves2), "nether_leaves2", BlockNetherLeaves2.TYPE);

        netherSapling = registerItemBlockProp(registry, new ItemBlockMeta(netherSapling), "nether_sapling", BlockNetherSapling.FOLIAGE);
        netherSapling2 = registerItemBlockProp(registry, new ItemBlockMeta(netherSapling2), "nether_sapling2", BlockNetherSapling2.FOLIAGE);

        netherPlanks = registerEnumItemBlock(registry, netherPlanks, "nether_planks");

        netherSlab = registerEnumItemBlockSlab(registry, netherSlab, "nether_slab");

        netherHeatSand = registerItemBlock(registry, netherHeatSand, "nether_heat_sand");
        netherTaintedSoil = registerEnumItemBlock(registry, netherTaintedSoil, "nether_tainted_soil");
        netherThornVines = registerItemBlock(registry, netherThornVines, "nether_thorn_vines");

        netherGlass = registerEnumItemBlock(registry, netherGlass, "nether_glass");

        netherStairsGhostwood = registerItemBlock(registry, netherStairsGhostwood, "nether_stairs_ghostwood");
        netherStairsBloodwood = registerItemBlock(registry, netherStairsBloodwood, "nether_stairs_bloodwood");
        netherStairsDarkwood = registerItemBlock(registry, netherStairsDarkwood, "nether_stairs_darkwood");
        netherStairsFusewood = registerItemBlock(registry, netherStairsFusewood, "nether_stairs_fusewood");

        netherBerryBushBlightberry = registerItemBlock(registry, netherBerryBushBlightberry, "nether_berrybush_blightberry");
        netherBerryBushDuskberry = registerItemBlock(registry, netherBerryBushDuskberry, "nether_berrybush_duskberry");
        netherBerryBushSkyberry = registerItemBlock(registry, netherBerryBushSkyberry, "nether_berrybush_skyberry");
        netherBerryBushStingberry = registerItemBlock(registry, netherBerryBushStingberry, "nether_berrybush_stingberry");

        netherBerryBushBlightberry.setItemDrop(NaturaCommons.blightberry);
        netherBerryBushDuskberry.setItemDrop(NaturaCommons.duskberry);
        netherBerryBushSkyberry.setItemDrop(NaturaCommons.skyberry);
        netherBerryBushStingberry.setItemDrop(NaturaCommons.stingberry);

        respawnObelisk = registerEnumItemBlock(registry, respawnObelisk, "respawn_obelisk");

        netherGlowshroom = registerItemBlockProp(registry, new ItemBlockMeta(netherGlowshroom), "nether_glowshroom", BlockNetherGlowshroom.TYPE);

        netherLargeGreenGlowshroom = registerItemBlockProp(registry, new ItemBlockMeta(netherLargeGreenGlowshroom), "nether_green_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargeBlueGlowshroom = registerItemBlockProp(registry, new ItemBlockMeta(netherLargeBlueGlowshroom), "nether_blue_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargePurpleGlowshroom = registerItemBlockProp(registry, new ItemBlockMeta(netherLargePurpleGlowshroom), "nether_purple_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);

        ghostwoodDoor = registerItemBlock(registry, ghostwoodDoor, "nether_door_ghostwood");
        bloodwoodDoor = registerItemBlock(registry, bloodwoodDoor, "nether_door_bloodwood");

        blazeHopper = registerItemBlock(registry, blazeHopper, "blaze_hopper");

        netherLever = registerItemBlock(registry, netherLever, "nether_lever");

        netherButton = registerItemBlock(registry, netherButton, "nether_button");

        netherPressurePlate = registerItemBlock(registry, netherPressurePlate, "nether_pressure_plate");

        blazeRail = registerItemBlock(registry, blazeRail, "blaze_rail");
        blazeRailPowered = registerItemBlock(registry, blazeRailPowered, "blaze_rail_golden");
        blazeRailActivator = registerItemBlock(registry, blazeRailActivator, "blaze_rail_activator");
        blazeRailDetector = registerItemBlock(registry, blazeRailDetector, "blaze_rail_detector");

        netherrackFurnace = registerItemBlock(registry, netherrackFurnace, "netherrack_furnace");
        litNetherrackFurnace = registerItemBlock(registry, litNetherrackFurnace, "lit_netherrack_furnace");
        // Blocks End

        // Items Start
        netherDoors = registerItem(registry, new ItemNaturaDoor(), "nether_doors");

        netherDoors.setCreativeTab(NaturaRegistry.tabDecorative);

        ghostwood_door = netherDoors.addMeta(0, "ghostwood_door", NaturaNether.ghostwoodDoor.getDefaultState());
        bloodwood_door = netherDoors.addMeta(1, "bloodwood_door", NaturaNether.bloodwoodDoor.getDefaultState());

        ghostwoodDoor.setDoor(NaturaNether.ghostwood_door);
        bloodwoodDoor.setDoor(NaturaNether.bloodwood_door);
        // Items End

        if (!isOverworldLoaded())
        {
            NaturaRegistry.tabDecorative.setDisplayIcon(ghostwood_door);
        }
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

        this.registerSmelting();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        furnaceRecipes.addSmeltingRecipe(new ItemStack(Blocks.SOUL_SAND, 1, 0), new ItemStack(netherGlass, 1, 0), 0.3f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(netherHeatSand, 1, 0), new ItemStack(netherGlass, 1, 1), 0.3f);
    }
}
