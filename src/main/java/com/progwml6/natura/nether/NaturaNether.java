package com.progwml6.natura.nether;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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
    public static Block netherSlab;

    public static BlockHeatSand netherHeatSand;
    public static BlockTaintedSoil netherTaintedSoil;
    public static BlockNetherThornVines netherThornVines;

    public static BlockNetherGlass netherGlass;

    public static Block netherStairsGhostwood;
    public static Block netherStairsBloodwood;
    public static Block netherStairsDarkwood;
    public static Block netherStairsFusewood;

    public static Block netherBerryBushBlightberry;
    public static Block netherBerryBushDuskberry;
    public static Block netherBerryBushSkyberry;
    public static Block netherBerryBushStingberry;

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

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        netherLog = registerEnumBlock(new BlockNetherLog(), "nether_logs");
        netherLog2 = registerBlock(new BlockNetherLog2(), "nether_logs2");

        netherLeaves = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves()), "nether_leaves");
        ItemBlockMeta.setMappingProperty(netherLeaves, BlockNetherLeaves.TYPE);
        netherLeaves2 = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves2()), "nether_leaves2");
        ItemBlockMeta.setMappingProperty(netherLeaves2, BlockNetherLeaves2.TYPE);

        netherSapling = registerBlock(new BlockNetherSapling(), "nether_sapling", BlockNetherSapling.FOLIAGE);
        netherSapling2 = registerBlock(new BlockNetherSapling2(), "nether_sapling2", BlockNetherSapling2.FOLIAGE);

        netherPlanks = registerEnumBlock(new BlockNetherPlanks(), "nether_planks");

        netherSlab = registerEnumBlockSlab(new BlockNetherSlab(), "nether_slab");

        netherHeatSand = registerBlock(new BlockHeatSand(), "nether_heat_sand");
        netherTaintedSoil = registerEnumBlock(new BlockTaintedSoil(), "nether_tainted_soil");
        netherThornVines = registerBlock(new BlockNetherThornVines(), "nether_thorn_vines");

        netherGlass = registerEnumBlock(new BlockNetherGlass(), "nether_glass");

        netherStairsGhostwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.GHOSTWOOD, "nether_stairs_ghostwood");
        netherStairsBloodwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.BLOODWOOD, "nether_stairs_bloodwood");
        netherStairsDarkwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.DARKWOOD, "nether_stairs_darkwood");
        netherStairsFusewood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.FUSEWOOD, "nether_stairs_fusewood");

        netherBerryBushBlightberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.blightberry), "nether_berrybush_blightberry");
        netherBerryBushDuskberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.duskberry), "nether_berrybush_duskberry");
        netherBerryBushSkyberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.skyberry), "nether_berrybush_skyberry");
        netherBerryBushStingberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.stingberry), "nether_berrybush_stingberry");

        respawnObelisk = registerEnumBlock(new BlockRespawnObelisk(), "respawn_obelisk");

        netherGlowshroom = registerBlock(new BlockNetherGlowshroom(), "nether_glowshroom", BlockNetherGlowshroom.TYPE);
        netherLargeGreenGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), "nether_green_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargeBlueGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta()), "nether_blue_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargePurpleGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), "nether_purple_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);

        ghostwoodDoor = registerBlock(new BlockNaturaDoor(), "nether_door_ghostwood");
        bloodwoodDoor = registerBlock(new BlockNaturaDoor(), "nether_door_bloodwood");

        blazeHopper = registerBlock(new BlockBlazeHopper(), "blaze_hopper");

        netherLever = registerBlock(new BlockNetherLever(), "nether_lever");

        netherButton = registerBlock(new BlockNetherButton(), "nether_button");

        netherPressurePlate = registerBlock(new BlockNetherPressurePlate(), "nether_pressure_plate");

        blazeRail = registerBlock(new BlockBlazeRail(), "blaze_rail");
        blazeRailPowered = registerBlock(new BlockBlazeRailPowered(false), "blaze_rail_golden");
        blazeRailActivator = registerBlock(new BlockBlazeRailPowered(true), "blaze_rail_activator");
        blazeRailDetector = registerBlock(new BlockBlazeRailDetector(), "blaze_rail_detector");

        netherrackFurnace = registerBlock(new BlockNetherrackFurnace(false), "netherrack_furnace");
        litNetherrackFurnace = registerBlock(new BlockNetherrackFurnace(true), "lit_netherrack_furnace");

        registerTE(TileEntityNetherrackFurnace.class, "netherrack_furnace");

        // Items
        netherDoors = registerItem(new ItemNaturaDoor(), "nether_doors");

        netherDoors.setCreativeTab(NaturaRegistry.tabDecorative);

        ghostwood_door = netherDoors.addMeta(0, "ghostwood_door", NaturaNether.ghostwoodDoor.getDefaultState());
        bloodwood_door = netherDoors.addMeta(1, "bloodwood_door", NaturaNether.bloodwoodDoor.getDefaultState());

        ghostwoodDoor.setDoor(NaturaNether.ghostwood_door);
        bloodwoodDoor.setDoor(NaturaNether.bloodwood_door);

        proxy.preInit();

        if (!isOverworldLoaded())
        {
            NaturaRegistry.tabDecorative.setDisplayIcon(ghostwood_door);
        }
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerRecipes();
        this.registerSmelting();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    private void registerRecipes()
    {
        // Arrows
        GameRegistry.addRecipe(NaturaCommons.ghostwoodFletching.copy(), " s ", "#s#", "#s#", 's', NaturaCommons.ghostwood_stick.copy(), '#', new ItemStack(netherLeaves, 1, BlockNetherLeaves.LeavesType.GHOSTWOOD.getMeta()));

        // Planks
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.DARKWOOD.getMeta()));
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()), "w", 'w', new ItemStack(netherLog2, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.FUSEWOOD.getMeta()));
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.GHOSTWOOD.getMeta()));

        // Doors
        addShapedRecipe(ghostwood_door.copy(), "##", "##", "##", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));
        addShapedRecipe(bloodwood_door.copy(), "##", "##", "##", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));

        // Sticks
        addShapedRecipe(new ItemStack(NaturaCommons.sticks, 4, 9), "#", "#", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.sticks, 4, 10), "#", "#", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.sticks, 4, 11), "#", "#", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.sticks, 4, 12), "#", "#", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));

        // Empty Bowls
        addShapedRecipe(new ItemStack(NaturaCommons.empty_bowls, 4, NaturaCommons.ghostwood_emptybowl.getItemDamage()), "# #", " # ", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.empty_bowls, 4, NaturaCommons.bloodwood_emptybowl.getItemDamage()), "# #", " # ", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.empty_bowls, 4, NaturaCommons.darkwood_emptybowl.getItemDamage()), "# #", " # ", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addShapedRecipe(new ItemStack(NaturaCommons.empty_bowls, 4, NaturaCommons.fusewood_emptybowl.getItemDamage()), "# #", " # ", '#', new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));

        // Mushroom Stew Bowls
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.ghostwood_mushroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.bloodwood_mushroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.darkwood_mushroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.fusewood_mushroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM)));

        // Glowshroom Stew Bowls
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.vanilla_glowshroomstew.copy(), new ItemStack(Items.BOWL), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.ghostwood_glowshroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.bloodwood_glowshroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.darkwood_glowshroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(NaturaCommons.fusewood_glowshroomstew.copy(), NaturaCommons.ghostwood_emptybowl.copy(), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), new ItemStack(netherGlowshroom, 1, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta())));

        // Slabs
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.BLOODWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.DARKWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.FUSEWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.GHOSTWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));

        // Stairs
        addStairRecipe(netherStairsBloodwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));
        addStairRecipe(netherStairsDarkwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addStairRecipe(netherStairsFusewood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));
        addStairRecipe(netherStairsGhostwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));

        // Soul Sand
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.SOUL_SAND), netherHeatSand, netherTaintedSoil);

        // Blaze Rails
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blazeRail, 16), "X X", "X#X", "X X", 'X', Items.BLAZE_ROD, '#', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blazeRailPowered, 6), "X X", "X#X", "XRX", 'X', Items.BLAZE_ROD, 'R', "dustRedstone", '#', new ItemStack(netherLog, 1, BlockNetherLog.LogType.FUSEWOOD.getMeta())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blazeRailActivator, 6), "XSX", "X#X", "XSX", 'X', Items.BLAZE_ROD, '#', Blocks.REDSTONE_TORCH, 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blazeRailDetector, 6), "X X", "X#X", "XRX", 'X', Items.BLAZE_ROD, 'R', "dustRedstone", '#', netherPressurePlate));

        // Netherrack Furnace
        GameRegistry.addRecipe(new ItemStack(netherrackFurnace), "###", "# #", "###", '#', Blocks.NETHERRACK);

        // Respawn Obelisk
        GameRegistry.addRecipe(new ItemStack(respawnObelisk), "###", "# #", "###", '#', new ItemStack(netherLog, 1, BlockNetherLog.LogType.GHOSTWOOD.getMeta()));

        // Blaze Hopper
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blazeHopper), "# #", "#C#", " # ", '#', new ItemStack(Items.BLAZE_ROD), 'C', "chestWood"));

        // Pressure Plate
        GameRegistry.addRecipe(new ItemStack(netherPressurePlate), "##", '#', new ItemStack(Blocks.NETHERRACK));

        // Button
        GameRegistry.addRecipe(new ItemStack(netherButton), "#", '#', new ItemStack(Blocks.NETHERRACK));

        // Lever
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(netherLever), "S", "#", '#', new ItemStack(Blocks.NETHERRACK), 'S', "stickWood"));
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        furnaceRecipes.addSmeltingRecipe(new ItemStack(Blocks.SOUL_SAND, 1, 0), new ItemStack(netherGlass, 1, 0), 0.3f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(netherHeatSand, 1, 0), new ItemStack(netherGlass, 1, 1), 0.3f);
    }
}
