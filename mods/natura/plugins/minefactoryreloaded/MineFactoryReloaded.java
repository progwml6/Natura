package mods.natura.plugins.minefactoryreloaded;

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
			
			int seedsId = NContent.seeds.itemID;
			int berryItemId = NContent.berryItem.itemID;
			int netherBerryItemId = NContent.netherBerryItem.itemID;
			int saguaroFruitId = NContent.seedFood.itemID;
			int cottonItemId = NContent.plantItem.itemID;
			
			int cropsId = NContent.crops.blockID;
			int berryBushId = NContent.berryBush.blockID;
			int netherBerryBushId = NContent.netherBerryBush.blockID;
			int saguaroId = NContent.saguaro.blockID;
			int treeId = NContent.tree.blockID;
			int redwoodId = NContent.redwood.blockID;
			int floraSaplingId = NContent.floraSapling.blockID;
			int floraLeavesId = NContent.floraLeaves.blockID;
			int floraLeavesNoColorId = NContent.floraLeavesNoColor.blockID;
			int bloodwoodId = NContent.bloodwood.blockID;
			
			FarmingRegistry.registerPlantable(new PlantableNaturaCrop(seedsId, cropsId));
			FarmingRegistry.registerPlantable(new PlantableStandard(berryBushId, berryBushId));
			FarmingRegistry.registerPlantable(new PlantableStandard(netherBerryBushId, netherBerryBushId));
			FarmingRegistry.registerPlantable(new PlantableStandard(saguaroFruitId, saguaroId));
			FarmingRegistry.registerPlantable(new PlantableStandard(floraSaplingId, floraSaplingId));
			
			FarmingRegistry.registerHarvestable(new HarvestableNaturaCropPlant(cropsId, cottonItemId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaBerry(berryBushId, berryItemId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaBerry(netherBerryBushId, netherBerryItemId));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(treeId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(redwoodId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableStandard(bloodwoodId, HarvestType.Tree));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesId));
			FarmingRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesNoColorId));
			
			FarmingRegistry.registerFertilizable(new FertilizableNaturaCrop(cropsId));
			FarmingRegistry.registerFertilizable(new FertilizableSapling(floraSaplingId));
		}
		catch(Exception e)
		{
			System.err.println("Something went wrong in Natura plugin: MineFactoryReloaded.");
			e.printStackTrace();
		}
	}
}

