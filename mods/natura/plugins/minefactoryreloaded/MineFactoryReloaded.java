package mods.natura.plugins.minefactoryreloaded;

import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import mods.natura.common.NContent;
import mods.natura.plugins.minefactoryreloaded.plantables.*;
import mods.natura.plugins.minefactoryreloaded.harvestables.*;
import mods.natura.plugins.minefactoryreloaded.fertilizables.*;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Natura|CompatMineFactoryReloaded", name = "Natura compat: MFR", version = "0.1", dependencies = "after:MineFactoryReloaded;after:Natura")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class MineFactoryReloaded
{
	@Init
	public static void load(FMLInitializationEvent ev)
	{
		if(!Loader.isModLoaded("MineFactoryReloaded"))
		{
			FMLLog.warning("MineFactoryReloaded missing - Natura Compat: MFR not loading");
			return;
		}
		try
		{
			FMLLog.fine("MineFactoryReloaded detected. Registering Natura trees/plants/crops with MFR's Farming Registry.");
			
			//items
			int seedsId = NContent.seeds.itemID;
			int berryItemId = NContent.berryItem.itemID;
			int netherBerryItemId = NContent.netherBerryItem.itemID;
			int saguaroFruitId = NContent.seedFood.itemID;
			int cottonItemId = NContent.plantItem.itemID;
			
			//blocks -- tried to imitate the sorting of PHNatura, but grabbed the block IDs from NContent
			int saguaroId = NContent.saguaro.blockID;
			
			int netherBerryBushId = NContent.netherBerryBush.blockID;
			int floraSaplingId = NContent.floraSapling.blockID;
			int berryBushId = NContent.berryBush.blockID;
			int floraLeavesNoColorId = NContent.floraLeavesNoColor.blockID;
			int floraLeavesId = NContent.floraLeaves.blockID;
			int cropsId = NContent.crops.blockID;
			int redwoodId = NContent.redwood.blockID;
			int treeId = NContent.tree.blockID;
			int bloodwoodId = NContent.bloodwood.blockID;
			
			int glowshroomId = NContent.glowshroom.blockID;
			int darkTreeId = NContent.darkTree.blockID;
			int darkLeavesId = NContent.darkLeaves.blockID;
			
			int glowshroomBlueId = NContent.glowshroomBlue.blockID;
			int glowshroomGreenId = NContent.glowshroomGreen.blockID;
			int glowshroomPurpleId = NContent.glowshroomPurple.blockID;
			
			int rareTreeId = NContent.rareTree.blockID;
			int rareLeavesId = NContent.rareLeaves.blockID;
			int rareSaplingId = NContent.rareSapling.blockID;
			
			int willowId = NContent.willow.blockID;
			int bluebellsId = NContent.bluebells.blockID;
			int thornVinesId = NContent.thornVines.blockID;
			
			FarmingRegistry.registerPlantable(new PlantableNaturaCrop(seedsId, cropsId));
			FarmingRegistry.registerPlantable(new PlantableNaturaBerry(berryBushId, berryBushId));
			FarmingRegistry.registerPlantable(new PlantableNaturaNetherBerry(netherBerryBushId, netherBerryBushId));
			FarmingRegistry.registerPlantable(new PlantableStandard(saguaroFruitId, saguaroId));
			FarmingRegistry.registerPlantable(new PlantableStandard(floraSaplingId, floraSaplingId));
			
			//misc plants
			FarmingRegistry.registerHarvestable(new HarvestableStandard(bluebellsId, HarvestType.Normal));
			//glowshrooms
			FarmingRegistry.registerHarvestable(new HarvestableStandard(glowshroomId, HarvestType.Normal));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(glowshroomBlueId, HarvestType.Normal));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(glowshroomGreenId, HarvestType.Normal));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(glowshroomPurpleId, HarvestType.Normal));
			//crops
			FarmingRegistry.registerHarvestable(new HarvestableNaturaCropPlant(cropsId, cottonItemId));
			//bushes
			FarmingRegistry.registerHarvestable(new HarvestableNaturaBerry(berryBushId, berryItemId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaBerry(netherBerryBushId, netherBerryItemId));
			//trees
			FarmingRegistry.registerHarvestable(new HarvestableStandard(treeId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(rareTreeId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(darkTreeId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(redwoodId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(willowId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(bloodwoodId, HarvestType.TreeFlipped));
			//leaves
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(rareLeavesId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(darkLeavesId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesNoColorId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(thornVinesId));
			
			FarmingRegistry.registerFertilizable(new FertilizableNaturaCrop(cropsId));
			FarmingRegistry.registerFertilizable(new FertilizableSapling(floraSaplingId));
			FarmingRegistry.registerFertilizable(new FertilizableSapling(rareSaplingId));
						
			/*
			 *  The sludge boiler takes sludge from harvester machines and boils it to get soil-like items,
			 *  such as dirt, sand, clay, or rarely things like soulsand and mycelium. 
			 */
			FarmingRegistry.registerSludgeDrop(5, new ItemStack(NContent.heatSand));
			FarmingRegistry.registerSludgeDrop(5, new ItemStack(NContent.taintedSoil));
		}
		catch(Throwable pikachu)
		{
			System.err.println("Something went wrong in Natura plugin: MineFactoryReloaded.");
			pikachu.printStackTrace();
		}
	}
}

