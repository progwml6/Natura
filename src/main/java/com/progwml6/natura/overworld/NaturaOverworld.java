package com.progwml6.natura.overworld;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.block.BlockNaturaDoor;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.common.item.ItemBlockLeaves;
import com.progwml6.natura.common.item.ItemNaturaDoor;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.overworld.block.bush.BlockOverworldBerryBush;
import com.progwml6.natura.overworld.block.crops.BlockNaturaBarley;
import com.progwml6.natura.overworld.block.crops.BlockNaturaCotton;
import com.progwml6.natura.overworld.block.flower.BlockBluebellsFlower;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;
import com.progwml6.natura.overworld.block.leaves.BlockOverworldLeaves;
import com.progwml6.natura.overworld.block.leaves.BlockOverworldLeaves2;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.logs.BlockRedwoodLog;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;
import com.progwml6.natura.overworld.block.slabs.BlockColoredGrassSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab2;
import com.progwml6.natura.overworld.item.ItemSeeds;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.shared.item.bags.ItemSeedBag;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaOverworld.PulseId, description = "All of the overworld blocks including trees")
public class NaturaOverworld extends NaturaPulse
{
    public static final String PulseId = "NaturaOverworld";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.overworld.OverworldClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static BlockColoredGrass coloredGrass;
    public static BlockColoredGrassSlab coloredGrassSlab;
    public static Block coloredGrassStairsTopiary;
    public static Block coloredGrassStairsBlueGrass;
    public static Block coloredGrassStairsAutumnal;

    public static BlockOverworldLog overworldLog;
    public static BlockOverworldLeaves overworldLeaves;
    public static BlockOverworldSapling overworldSapling;

    public static BlockOverworldLog2 overworldLog2;
    public static BlockOverworldLeaves2 overworldLeaves2;
    public static BlockOverworldSapling2 overworldSapling2;

    public static BlockOverworldPlanks overworldPlanks;

    public static BlockRedwoodLog redwoodLog;
    public static BlockRedwoodSapling redwoodSapling;
    public static BlockRedwoodLeaves redwoodLeaves;

    public static BlockBluebellsFlower bluebellsFlower;

    public static Block overworldSlab;
    public static Block overworldSlab2;

    public static Block overworldStairsMaple;
    public static Block overworldStairsSilverbell;
    public static Block overworldStairsAmaranth;
    public static Block overworldStairsTiger;
    public static Block overworldStairsWillow;
    public static Block overworldStairsEucalyptus;
    public static Block overworldStairsHopseed;
    public static Block overworldStairsSakura;
    public static Block overworldStairsRedwood;

    public static Block overworldBerryBushRaspberry;
    public static Block overworldBerryBushBlueberry;
    public static Block overworldBerryBushBlackberry;
    public static Block overworldBerryBushMaloberry;

    public static BlockNaturaBarley barleyCrop;
    public static BlockNaturaCotton cottonCrop;

    public static BlockNaturaDoor eucalyptusDoor;
    public static BlockNaturaDoor hopseedDoor;
    public static BlockNaturaDoor sakuraDoor;
    public static BlockNaturaDoor redwoodDoor;
    public static BlockNaturaDoor redwoodBarkDoor;

    // Items
    public static ItemSeeds overworldSeeds;
    public static ItemSeedBag overworldSeedBags;
    public static ItemNaturaDoor overworldDoors;

    public static ItemStack barley_seeds;
    public static ItemStack cotton_seeds;

    public static ItemStack cotton_seed_bag;
    public static ItemStack barley_seed_bag;

    public static ItemStack eucalyptus_door;
    public static ItemStack hopseed_door;
    public static ItemStack sakura_door;
    public static ItemStack redwood_door;
    public static ItemStack redwood_bark_door;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        coloredGrass = registerEnumBlock(new BlockColoredGrass(), "colored_grass");
        coloredGrassSlab = registerEnumBlockSlab(new BlockColoredGrassSlab(), "colored_grass_slab");
        coloredGrassStairsTopiary = registerBlockGrassStairsFrom(coloredGrass, BlockColoredGrass.GrassType.TOPIARY, "colored_grass_stairs_topiary");
        coloredGrassStairsBlueGrass = registerBlockGrassStairsFrom(coloredGrass, BlockColoredGrass.GrassType.BLUEGRASS, "colored_grass_stairs_bluegrass");
        coloredGrassStairsAutumnal = registerBlockGrassStairsFrom(coloredGrass, BlockColoredGrass.GrassType.AUTUMNAL, "colored_grass_stairs_autumnal");

        overworldLog = registerEnumBlock(new BlockOverworldLog(), "overworld_logs");
        overworldLog2 = registerEnumBlock(new BlockOverworldLog2(), "overworld_logs2");
        redwoodLog = registerEnumBlock(new BlockRedwoodLog(), "redwood_logs");

        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLog.TYPE);
        overworldLeaves2 = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves2()), "overworld_leaves2");
        ItemBlockMeta.setMappingProperty(overworldLeaves2, BlockOverworldLog2.TYPE);
        redwoodLeaves = registerBlock(new ItemBlockLeaves(new BlockRedwoodLeaves()), "redwood_leaves");
        ItemBlockMeta.setMappingProperty(redwoodLeaves, BlockRedwoodLeaves.TYPE);

        overworldSapling = registerBlock(new BlockOverworldSapling(), "overworld_sapling", BlockOverworldSapling.FOLIAGE);
        overworldSapling2 = registerBlock(new BlockOverworldSapling2(), "overworld_sapling2", BlockOverworldSapling2.FOLIAGE);
        redwoodSapling = registerBlock(new BlockRedwoodSapling(), "redwood_sapling", BlockRedwoodSapling.FOLIAGE);

        bluebellsFlower = registerBlock(new BlockBluebellsFlower(), "bluebells_flower");

        overworldPlanks = registerEnumBlock(new BlockOverworldPlanks(), "overworld_planks");

        overworldSlab = registerEnumBlockSlab(new BlockOverworldSlab(), "overworld_slab");
        overworldSlab2 = registerEnumBlockSlab(new BlockOverworldSlab2(), "overworld_slab2");

        overworldStairsMaple = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.MAPLE, "overworld_stairs_maple");
        overworldStairsSilverbell = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.SILVERBELL, "overworld_stairs_silverbell");
        overworldStairsAmaranth = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.AMARANTH, "overworld_stairs_amaranth");
        overworldStairsTiger = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.TIGER, "overworld_stairs_tiger");
        overworldStairsWillow = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.WILLOW, "overworld_stairs_willow");
        overworldStairsEucalyptus = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.EUCALYPTUS, "overworld_stairs_eucalyptus");
        overworldStairsHopseed = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.HOPSEED, "overworld_stairs_hopseed");
        overworldStairsSakura = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.SAKURA, "overworld_stairs_sakura");
        overworldStairsRedwood = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.REDWOOD, "overworld_stairs_redwood");

        overworldBerryBushRaspberry = registerBlock(new BlockOverworldBerryBush(NaturaCommons.raspberry), "overworld_berrybush_raspberry");
        overworldBerryBushBlueberry = registerBlock(new BlockOverworldBerryBush(NaturaCommons.blueberry), "overworld_berrybush_blueberry");
        overworldBerryBushBlackberry = registerBlock(new BlockOverworldBerryBush(NaturaCommons.blackberry), "overworld_berrybush_blackberry");
        overworldBerryBushMaloberry = registerBlock(new BlockOverworldBerryBush(NaturaCommons.maloberry), "overworld_berrybush_maloberry");

        barleyCrop = registerBlock(new BlockNaturaBarley(), "barley_crop");
        cottonCrop = registerBlock(new BlockNaturaCotton(), "cotton_crop");

        eucalyptusDoor = registerBlock(new BlockNaturaDoor(), "overworld_door_eucalyptus");
        hopseedDoor = registerBlock(new BlockNaturaDoor(), "overworld_door_hopseed");
        sakuraDoor = registerBlock(new BlockNaturaDoor(), "overworld_door_sakura");
        redwoodDoor = registerBlock(new BlockNaturaDoor(), "overworld_door_redwood");
        redwoodBarkDoor = registerBlock(new BlockNaturaDoor(), "overworld_door_redwood_bark");

        overworldSeeds = registerItem(new ItemSeeds(), "overworld_seeds");
        overworldSeedBags = registerItem(new ItemSeedBag(), "overworld_seed_bags");
        overworldDoors = registerItem(new ItemNaturaDoor(), "overworld_doors");

        overworldSeeds.setCreativeTab(NaturaRegistry.tabGeneral);
        overworldSeedBags.setCreativeTab(NaturaRegistry.tabGeneral);
        overworldDoors.setCreativeTab(NaturaRegistry.tabGeneral);

        barley_seeds = overworldSeeds.addMeta(0, "barley_seeds", NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, 0));
        cotton_seeds = overworldSeeds.addMeta(1, "cotton_seeds", NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, 0));

        if (isOverworldLoaded())
        {
            barley_seed_bag = overworldSeedBags.addMeta(0, "barley_seed_bag", NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, Integer.valueOf(0)));
            cotton_seed_bag = overworldSeedBags.addMeta(1, "cotton_seed_bag", NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, Integer.valueOf(0)));
        }

        eucalyptus_door = overworldDoors.addMeta(0, "eucalyptus_door", NaturaOverworld.eucalyptusDoor.getDefaultState());
        hopseed_door = overworldDoors.addMeta(1, "hopseed_door", NaturaOverworld.hopseedDoor.getDefaultState());
        sakura_door = overworldDoors.addMeta(2, "sakura_door", NaturaOverworld.sakuraDoor.getDefaultState());
        redwood_door = overworldDoors.addMeta(3, "redwood_door", NaturaOverworld.redwoodDoor.getDefaultState());
        redwood_bark_door = overworldDoors.addMeta(4, "redwood_bark_door", NaturaOverworld.redwoodBarkDoor.getDefaultState());

        eucalyptusDoor.setDoor(NaturaOverworld.eucalyptus_door);
        hopseedDoor.setDoor(NaturaOverworld.hopseed_door);
        sakuraDoor.setDoor(NaturaOverworld.sakura_door);
        redwoodDoor.setDoor(NaturaOverworld.redwood_door);
        redwoodBarkDoor.setDoor(NaturaOverworld.redwood_bark_door);

        proxy.preInit();

        NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(coloredGrass));
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
        @SuppressWarnings("unused")
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.MAPLE.getMeta()), "w", 'w', new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.MAPLE.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.SILVERBELL.getMeta()), "w", 'w', new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.SILVERBELL.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.AMARANTH.getMeta()), "w", 'w', new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.AMARANTH.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.TIGER.getMeta()), "w", 'w', new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.TIGER.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.WILLOW.getMeta()), "w", 'w', new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.WILLOW.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.EUCALYPTUS.getMeta()), "w", 'w', new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.EUCALYPTUS.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.HOPSEED.getMeta()), "w", 'w', new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.HOPSEED.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.SAKURA.getMeta()), "w", 'w', new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.SAKURA.getMeta()));
        GameRegistry.addRecipe(new ItemStack(overworldPlanks, 4, BlockOverworldPlanks.PlankType.REDWOOD.getMeta()), "w", 'w', new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.HEART.getMeta()));
        //TODO test these

        //SLABS
        addSlabRecipe(overworldSlab, BlockOverworldSlab.PlankType.AMARANTH.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.AMARANTH.getMeta()));
        addSlabRecipe(overworldSlab, BlockOverworldSlab.PlankType.MAPLE.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.MAPLE.getMeta()));
        addSlabRecipe(overworldSlab, BlockOverworldSlab.PlankType.SILVERBELL.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.SILVERBELL.getMeta()));
        addSlabRecipe(overworldSlab, BlockOverworldSlab.PlankType.TIGER.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.TIGER.getMeta()));
        addSlabRecipe(overworldSlab, BlockOverworldSlab.PlankType.WILLOW.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.WILLOW.getMeta()));
        addSlabRecipe(overworldSlab2, BlockOverworldSlab2.PlankType.EUCALYPTUS.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.EUCALYPTUS.getMeta()));
        addSlabRecipe(overworldSlab2, BlockOverworldSlab2.PlankType.HOPSEED.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.HOPSEED.getMeta()));
        addSlabRecipe(overworldSlab2, BlockOverworldSlab2.PlankType.REDWOOD.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.REDWOOD.getMeta()));
        addSlabRecipe(overworldSlab2, BlockOverworldSlab2.PlankType.SAKURA.getMeta(), new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.SAKURA.getMeta()));

        //STAIRS
        addStairRecipe(overworldStairsAmaranth, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.AMARANTH.getMeta()));
        addStairRecipe(overworldStairsMaple, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.MAPLE.getMeta()));
        addStairRecipe(overworldStairsSilverbell, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.SILVERBELL.getMeta()));
        addStairRecipe(overworldStairsTiger, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.TIGER.getMeta()));
        addStairRecipe(overworldStairsWillow, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.WILLOW.getMeta()));
        addStairRecipe(overworldStairsEucalyptus, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.EUCALYPTUS.getMeta()));
        addStairRecipe(overworldStairsHopseed, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.HOPSEED.getMeta()));
        addStairRecipe(overworldStairsRedwood, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.REDWOOD.getMeta()));
        addStairRecipe(overworldStairsSakura, new ItemStack(overworldPlanks, 1, BlockOverworldPlanks.PlankType.SAKURA.getMeta()));

        //GRASS STUFF
        GameRegistry.addRecipe(new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.TOPIARY.getMeta()), " s ", "s#s", " s ", 's', new ItemStack(Items.WHEAT_SEEDS), '#', new ItemStack(Blocks.DIRT));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.BLUEGRASS.getMeta()), new ItemStack(coloredGrass, 1, 0), "dyeBlue"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.AUTUMNAL.getMeta()), new ItemStack(coloredGrass, 1, 0), "dyeRed"));

        addSlabRecipe(coloredGrassSlab, BlockColoredGrass.GrassType.TOPIARY.getMeta(), new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.TOPIARY.getMeta()));
        addSlabRecipe(coloredGrassSlab, BlockColoredGrass.GrassType.BLUEGRASS.getMeta(), new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.BLUEGRASS.getMeta()));
        addSlabRecipe(coloredGrassSlab, BlockColoredGrass.GrassType.AUTUMNAL.getMeta(), new ItemStack(coloredGrass, 1, BlockColoredGrass.GrassType.AUTUMNAL.getMeta()));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.BREAD), "bbb", 'b', "cropBarley"));
        GameRegistry.addRecipe(new ShapedOreRecipe(NaturaCommons.barleyFlour.copy(), "X", 'X', "cropBarley"));
        if (Config.enableWheatRecipe)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(NaturaCommons.wheatFlour.copy(), "X", 'X', "cropWheat"));
        }

        GameRegistry.addShapelessRecipe(NaturaCommons.blueDye, new ItemStack(bluebellsFlower));

        // Crops
        GameRegistry.addRecipe(new ShapedOreRecipe(barley_seed_bag.copy(), "sss", "sss", "sss", 's', "seedBarley"));
        GameRegistry.addRecipe(new ShapedOreRecipe(cotton_seed_bag.copy(), "sss", "sss", "sss", 's', "seedCotton"));

        GameRegistry.addRecipe(new ItemStack(overworldSeeds, 9, 0), "s", 's', barley_seed_bag.copy());
        GameRegistry.addRecipe(new ItemStack(overworldSeeds, 9, 1), "s", 's', cotton_seed_bag.copy());

    }

    private void registerSmelting()
    {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.MAPLE.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.AMARANTH.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.SILVERBELL.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.TIGER.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.EUCALYPTUS.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.HOPSEED.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.SAKURA.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.WILLOW.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.BARK.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.ROOT.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.HEART.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
    }
}
