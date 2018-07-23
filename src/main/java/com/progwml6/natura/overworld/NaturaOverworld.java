package com.progwml6.natura.overworld;

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
import com.progwml6.natura.overworld.block.saguaro.BlockSaguaro;
import com.progwml6.natura.overworld.block.saguaro.BlockSaguaroBaby;
import com.progwml6.natura.overworld.block.saguaro.BlockSaguaroFruit;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;
import com.progwml6.natura.overworld.block.slabs.BlockColoredGrassSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab2;
import com.progwml6.natura.overworld.item.ItemSaguaroFruit;
import com.progwml6.natura.overworld.item.ItemSeeds;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.shared.item.bags.ItemSeedBag;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
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

    public static BlockOverworldSlab overworldSlab;
    public static BlockOverworldSlab2 overworldSlab2;

    public static Block overworldStairsMaple;
    public static Block overworldStairsSilverbell;
    public static Block overworldStairsAmaranth;
    public static Block overworldStairsTiger;
    public static Block overworldStairsWillow;
    public static Block overworldStairsEucalyptus;
    public static Block overworldStairsHopseed;
    public static Block overworldStairsSakura;
    public static Block overworldStairsRedwood;

    public static BlockEnumBerryBush overworldBerryBushRaspberry;
    public static BlockEnumBerryBush overworldBerryBushBlueberry;
    public static BlockEnumBerryBush overworldBerryBushBlackberry;
    public static BlockEnumBerryBush overworldBerryBushMaloberry;

    public static BlockNaturaBarley barleyCrop;
    public static BlockNaturaCotton cottonCrop;

    public static BlockNaturaDoor eucalyptusDoor;
    public static BlockNaturaDoor hopseedDoor;
    public static BlockNaturaDoor sakuraDoor;
    public static BlockNaturaDoor redwoodDoor;
    public static BlockNaturaDoor redwoodBarkDoor;
    public static BlockNaturaDoor tigerDoor;
    public static BlockNaturaDoor mapleDoor;
    public static BlockNaturaDoor silverbellDoor;

    public static BlockSaguaro saguaro;
    public static BlockSaguaroBaby saguaroBaby;
    public static BlockSaguaroFruit saguaroFruit;

    // Items
    public static ItemSeeds overworldSeeds;
    public static ItemSeedBag overworldSeedBags;
    public static ItemNaturaDoor overworldDoors;

    public static ItemSaguaroFruit saguaroFruitItem;

    public static ItemStack barley_seeds;
    public static ItemStack cotton_seeds;

    public static ItemStack cotton_seed_bag;
    public static ItemStack barley_seed_bag;

    public static ItemStack eucalyptus_door;
    public static ItemStack hopseed_door;
    public static ItemStack sakura_door;
    public static ItemStack redwood_door;
    public static ItemStack redwood_bark_door;
    public static ItemStack tiger_door;
    public static ItemStack maple_door;
    public static ItemStack silverbell_door;
    //@formatter:on

    @SubscribeEvent
    public void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Blocks Start
        coloredGrass = registerBlock(registry, new BlockColoredGrass(), "colored_grass");
        coloredGrassSlab = registerBlock(registry, new BlockColoredGrassSlab(), "colored_grass_slab");
        coloredGrassStairsTopiary = registerBlockGrassStairsFrom(registry, coloredGrass, BlockColoredGrass.GrassType.TOPIARY, "colored_grass_stairs_topiary");
        coloredGrassStairsBlueGrass = registerBlockGrassStairsFrom(registry, coloredGrass, BlockColoredGrass.GrassType.BLUEGRASS, "colored_grass_stairs_bluegrass");
        coloredGrassStairsAutumnal = registerBlockGrassStairsFrom(registry, coloredGrass, BlockColoredGrass.GrassType.AUTUMNAL, "colored_grass_stairs_autumnal");

        overworldLog = registerBlock(registry, new BlockOverworldLog(), "overworld_logs");
        overworldLog2 = registerBlock(registry, new BlockOverworldLog2(), "overworld_logs2");
        redwoodLog = registerBlock(registry, new BlockRedwoodLog(), "redwood_logs");

        overworldLeaves = registerBlock(registry, new BlockOverworldLeaves(), "overworld_leaves");
        overworldLeaves2 = registerBlock(registry, new BlockOverworldLeaves2(), "overworld_leaves2");
        redwoodLeaves = registerBlock(registry, new BlockRedwoodLeaves(), "redwood_leaves");

        overworldSapling = registerBlock(registry, new BlockOverworldSapling(), "overworld_sapling");
        overworldSapling2 = registerBlock(registry, new BlockOverworldSapling2(), "overworld_sapling2");
        redwoodSapling = registerBlock(registry, new BlockRedwoodSapling(), "redwood_sapling");

        bluebellsFlower = registerBlock(registry, new BlockBluebellsFlower(), "bluebells_flower");

        overworldPlanks = registerBlock(registry, new BlockOverworldPlanks(), "overworld_planks");

        overworldSlab = registerBlock(registry, new BlockOverworldSlab(), "overworld_slab");
        overworldSlab2 = registerBlock(registry, new BlockOverworldSlab2(), "overworld_slab2");

        overworldStairsMaple = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.MAPLE, "overworld_stairs_maple");
        overworldStairsSilverbell = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.SILVERBELL, "overworld_stairs_silverbell");
        overworldStairsAmaranth = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.AMARANTH, "overworld_stairs_amaranth");
        overworldStairsTiger = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.TIGER, "overworld_stairs_tiger");
        overworldStairsWillow = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.WILLOW, "overworld_stairs_willow");
        overworldStairsEucalyptus = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.EUCALYPTUS, "overworld_stairs_eucalyptus");
        overworldStairsHopseed = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.HOPSEED, "overworld_stairs_hopseed");
        overworldStairsSakura = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.SAKURA, "overworld_stairs_sakura");
        overworldStairsRedwood = registerBlockStairsFrom(registry, overworldPlanks, BlockOverworldPlanks.PlankType.REDWOOD, "overworld_stairs_redwood");

        overworldBerryBushRaspberry = registerBlock(registry, new BlockOverworldBerryBush(), "overworld_berrybush_raspberry");
        overworldBerryBushBlueberry = registerBlock(registry, new BlockOverworldBerryBush(), "overworld_berrybush_blueberry");
        overworldBerryBushBlackberry = registerBlock(registry, new BlockOverworldBerryBush(), "overworld_berrybush_blackberry");
        overworldBerryBushMaloberry = registerBlock(registry, new BlockOverworldBerryBush(), "overworld_berrybush_maloberry");

        barleyCrop = registerBlock(registry, new BlockNaturaBarley(), "barley_crop");
        cottonCrop = registerBlock(registry, new BlockNaturaCotton(), "cotton_crop");

        eucalyptusDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_eucalyptus");
        hopseedDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_hopseed");
        sakuraDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_sakura");
        redwoodDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_redwood");
        redwoodBarkDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_redwood_bark");
        tigerDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_tiger");
        mapleDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_maple");
        silverbellDoor = registerBlock(registry, new BlockNaturaDoor(), "overworld_door_silverbell");

        saguaro = registerBlock(registry, new BlockSaguaro(), "saguaro");
        saguaroBaby = registerBlock(registry, new BlockSaguaroBaby(), "saguaro_baby");
        saguaroFruit = registerBlock(registry, new BlockSaguaroFruit(), "saguaro_fruit");
        // Blocks End
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Blocks Start
        coloredGrass = registerEnumItemBlock(registry, coloredGrass, "colored_grass");
        coloredGrassSlab = registerEnumItemBlockSlab(registry, coloredGrassSlab, "colored_grass_slab");
        coloredGrassStairsTopiary = registerItemBlock(registry, coloredGrassStairsTopiary, "colored_grass_stairs_topiary");
        coloredGrassStairsBlueGrass = registerItemBlock(registry, coloredGrassStairsBlueGrass, "colored_grass_stairs_bluegrass");
        coloredGrassStairsAutumnal = registerItemBlock(registry, coloredGrassStairsAutumnal, "colored_grass_stairs_autumnal");

        overworldLog = registerEnumItemBlock(registry, overworldLog, "overworld_logs");
        overworldLog2 = registerEnumItemBlock(registry, overworldLog2, "overworld_logs2");
        redwoodLog = registerEnumItemBlock(registry, redwoodLog, "redwood_logs");

        overworldLeaves = registerItemBlockProp(registry, new ItemBlockLeaves(overworldLeaves), "overworld_leaves", BlockOverworldLog.TYPE);
        overworldLeaves2 = registerItemBlockProp(registry, new ItemBlockLeaves(overworldLeaves2), "overworld_leaves2", BlockOverworldLog2.TYPE);
        redwoodLeaves = registerItemBlockProp(registry, new ItemBlockLeaves(redwoodLeaves), "redwood_leaves", BlockRedwoodLeaves.TYPE);

        overworldSapling = registerItemBlockProp(registry, new ItemBlockMeta(overworldSapling), "overworld_sapling", BlockOverworldSapling.FOLIAGE);
        overworldSapling2 = registerItemBlockProp(registry, new ItemBlockMeta(overworldSapling2), "overworld_sapling2", BlockOverworldSapling2.FOLIAGE);
        redwoodSapling = registerItemBlockProp(registry, new ItemBlockMeta(redwoodSapling), "redwood_sapling", BlockRedwoodSapling.FOLIAGE);

        bluebellsFlower = registerItemBlock(registry, bluebellsFlower, "bluebells_flower");

        overworldPlanks = registerEnumItemBlock(registry, overworldPlanks, "overworld_planks");

        overworldSlab = registerEnumItemBlockSlab(registry, overworldSlab, "overworld_slab");
        overworldSlab2 = registerEnumItemBlockSlab(registry, overworldSlab2, "overworld_slab2");

        overworldStairsMaple = registerItemBlock(registry, overworldStairsMaple, "overworld_stairs_maple");
        overworldStairsSilverbell = registerItemBlock(registry, overworldStairsSilverbell, "overworld_stairs_silverbell");
        overworldStairsAmaranth = registerItemBlock(registry, overworldStairsAmaranth, "overworld_stairs_amaranth");
        overworldStairsTiger = registerItemBlock(registry, overworldStairsTiger, "overworld_stairs_tiger");
        overworldStairsWillow = registerItemBlock(registry, overworldStairsWillow, "overworld_stairs_willow");
        overworldStairsEucalyptus = registerItemBlock(registry, overworldStairsEucalyptus, "overworld_stairs_eucalyptus");
        overworldStairsHopseed = registerItemBlock(registry, overworldStairsHopseed, "overworld_stairs_hopseed");
        overworldStairsSakura = registerItemBlock(registry, overworldStairsSakura, "overworld_stairs_sakura");
        overworldStairsRedwood = registerItemBlock(registry, overworldStairsRedwood, "overworld_stairs_redwood");

        overworldBerryBushRaspberry = registerItemBlock(registry, overworldBerryBushRaspberry, "overworld_berrybush_raspberry");
        overworldBerryBushBlueberry = registerItemBlock(registry, overworldBerryBushBlueberry, "overworld_berrybush_blueberry");
        overworldBerryBushBlackberry = registerItemBlock(registry, overworldBerryBushBlackberry, "overworld_berrybush_blackberry");
        overworldBerryBushMaloberry = registerItemBlock(registry, overworldBerryBushMaloberry, "overworld_berrybush_maloberry");

        overworldBerryBushRaspberry.setItemDrop(NaturaCommons.raspberry);
        overworldBerryBushBlueberry.setItemDrop(NaturaCommons.blueberry);
        overworldBerryBushBlackberry.setItemDrop(NaturaCommons.blackberry);
        overworldBerryBushMaloberry.setItemDrop(NaturaCommons.maloberry);

        barleyCrop = registerItemBlock(registry, barleyCrop, "barley_crop");
        cottonCrop = registerItemBlock(registry, cottonCrop, "cotton_crop");

        eucalyptusDoor = registerItemBlock(registry, eucalyptusDoor, "overworld_door_eucalyptus");
        hopseedDoor = registerItemBlock(registry, hopseedDoor, "overworld_door_hopseed");
        sakuraDoor = registerItemBlock(registry, sakuraDoor, "overworld_door_sakura");
        redwoodDoor = registerItemBlock(registry, redwoodDoor, "overworld_door_redwood");
        redwoodBarkDoor = registerItemBlock(registry, redwoodBarkDoor, "overworld_door_redwood_bark");
        tigerDoor = registerItemBlock(registry, tigerDoor, "overworld_door_tiger");
        mapleDoor = registerItemBlock(registry, mapleDoor, "overworld_door_maple");
        silverbellDoor = registerItemBlock(registry, silverbellDoor, "overworld_door_silverbell");

        saguaro = registerItemBlock(registry, saguaro, "saguaro");
        saguaroBaby = registerItemBlock(registry, saguaroBaby, "saguaro_baby");
        saguaroFruit = registerItemBlock(registry, saguaroFruit, "saguaro_fruit");
        // Blocks End

        // Items Start
        overworldSeeds = registerItem(registry, new ItemSeeds(), "overworld_seeds");
        overworldSeedBags = registerItem(registry, new ItemSeedBag(), "overworld_seed_bags");
        overworldDoors = registerItem(registry, new ItemNaturaDoor(), "overworld_doors");

        saguaroFruitItem = registerItem(registry, new ItemSaguaroFruit(3, 0.3f, NaturaOverworld.saguaroBaby), "saguaro_fruit_item");

        overworldSeeds.setCreativeTab(NaturaRegistry.tabGeneral);
        overworldSeedBags.setCreativeTab(NaturaRegistry.tabGeneral);
        overworldDoors.setCreativeTab(NaturaRegistry.tabDecorative);

        barley_seeds = overworldSeeds.addMeta(0, "barley_seeds", NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, 0));
        cotton_seeds = overworldSeeds.addMeta(1, "cotton_seeds", NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, 0));

        barley_seed_bag = overworldSeedBags.addMeta(0, "barley_seed_bag", NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, Integer.valueOf(0)));
        cotton_seed_bag = overworldSeedBags.addMeta(1, "cotton_seed_bag", NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, Integer.valueOf(0)));

        eucalyptus_door = overworldDoors.addMeta(0, "eucalyptus_door", NaturaOverworld.eucalyptusDoor.getDefaultState());
        hopseed_door = overworldDoors.addMeta(1, "hopseed_door", NaturaOverworld.hopseedDoor.getDefaultState());
        sakura_door = overworldDoors.addMeta(2, "sakura_door", NaturaOverworld.sakuraDoor.getDefaultState());
        redwood_door = overworldDoors.addMeta(3, "redwood_door", NaturaOverworld.redwoodDoor.getDefaultState());
        redwood_bark_door = overworldDoors.addMeta(4, "redwood_bark_door", NaturaOverworld.redwoodBarkDoor.getDefaultState());
        tiger_door = overworldDoors.addMeta(5, "tiger_door", NaturaOverworld.tigerDoor.getDefaultState());
        maple_door = overworldDoors.addMeta(6, "maple_door", NaturaOverworld.mapleDoor.getDefaultState());
        silverbell_door = overworldDoors.addMeta(7, "silverbell_door", NaturaOverworld.silverbellDoor.getDefaultState());

        eucalyptusDoor.setDoor(NaturaOverworld.eucalyptus_door);
        hopseedDoor.setDoor(NaturaOverworld.hopseed_door);
        sakuraDoor.setDoor(NaturaOverworld.sakura_door);
        redwoodDoor.setDoor(NaturaOverworld.redwood_door);
        redwoodBarkDoor.setDoor(NaturaOverworld.redwood_bark_door);
        tigerDoor.setDoor(NaturaOverworld.tiger_door);
        mapleDoor.setDoor(NaturaOverworld.maple_door);
        silverbellDoor.setDoor(NaturaOverworld.silverbell_door);
        // Items End

        NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(coloredGrass));
        NaturaRegistry.tabDecorative.setDisplayIcon(redwood_door);
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

        furnaceRecipes.addSmeltingRecipe(new ItemStack(saguaro, 1, 0), new ItemStack(Items.DYE, 1, 2), 0.2F);

        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.MAPLE.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.AMARANTH.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.SILVERBELL.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog, 1, BlockOverworldLog.LogType.TIGER.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.EUCALYPTUS.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.HOPSEED.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.SAKURA.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(overworldLog2, 1, BlockOverworldLog2.LogType.WILLOW.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.BARK.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.ROOT.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
        furnaceRecipes.addSmeltingRecipe(new ItemStack(redwoodLog, 1, BlockRedwoodLog.RedwoodType.HEART.getMeta()), new ItemStack(Items.COAL, 1, 1), 0.15f);
    }
}
