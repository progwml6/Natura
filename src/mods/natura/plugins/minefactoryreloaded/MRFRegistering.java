package mods.natura.plugins.minefactoryreloaded;

import mods.natura.common.NContent;
import mods.natura.plugins.minefactoryreloaded.fertilizables.FertilizableNaturaCrop;
import mods.natura.plugins.minefactoryreloaded.fertilizables.FertilizableSapling;
import mods.natura.plugins.minefactoryreloaded.harvestables.HarvestableNaturaBerry;
import mods.natura.plugins.minefactoryreloaded.harvestables.HarvestableNaturaCropPlant;
import mods.natura.plugins.minefactoryreloaded.harvestables.HarvestableNaturaTreeLeaves;
import mods.natura.plugins.minefactoryreloaded.harvestables.HarvestableStandard;
import mods.natura.plugins.minefactoryreloaded.plantables.PlantableNaturaBerry;
import mods.natura.plugins.minefactoryreloaded.plantables.PlantableNaturaCrop;
import mods.natura.plugins.minefactoryreloaded.plantables.PlantableNaturaNetherBerry;
import mods.natura.plugins.minefactoryreloaded.plantables.PlantableStandard;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class MRFRegistering
{
    public static void registerWithMFR ()
    { //items
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

        FactoryRegistry.registerPlantable(new PlantableNaturaCrop(seedsId, cropsId));
        FactoryRegistry.registerPlantable(new PlantableNaturaBerry(berryBushId, berryBushId));
        FactoryRegistry.registerPlantable(new PlantableNaturaNetherBerry(netherBerryBushId, netherBerryBushId));
        FactoryRegistry.registerPlantable(new PlantableStandard(saguaroFruitId, saguaroId));
        FactoryRegistry.registerPlantable(new PlantableStandard(floraSaplingId, floraSaplingId));
        FactoryRegistry.registerPlantable(new PlantableStandard(rareSaplingId, rareSaplingId));

        //misc plants
        FactoryRegistry.registerHarvestable(new HarvestableStandard(bluebellsId, HarvestType.Normal));
        //glowshrooms
        FactoryRegistry.registerHarvestable(new HarvestableStandard(glowshroomId, HarvestType.Normal));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(glowshroomBlueId, HarvestType.Normal));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(glowshroomGreenId, HarvestType.Normal));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(glowshroomPurpleId, HarvestType.Normal));
        //crops
        FactoryRegistry.registerHarvestable(new HarvestableNaturaCropPlant(cropsId, cottonItemId));
        //bushes
        FactoryRegistry.registerHarvestable(new HarvestableNaturaBerry(berryBushId, berryItemId));
        FactoryRegistry.registerHarvestable(new HarvestableNaturaBerry(netherBerryBushId, netherBerryItemId));
        //trees
        FactoryRegistry.registerHarvestable(new HarvestableStandard(treeId, HarvestType.Tree));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(rareTreeId, HarvestType.Tree));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(darkTreeId, HarvestType.Tree));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(redwoodId, HarvestType.Tree));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(willowId, HarvestType.Tree));
        FactoryRegistry.registerHarvestable(new HarvestableStandard(bloodwoodId, HarvestType.TreeFlipped));
        //leaves
        FactoryRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(rareLeavesId));
        FactoryRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(darkLeavesId));
        FactoryRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesId));
        FactoryRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(floraLeavesNoColorId));
        FactoryRegistry.registerHarvestable(new HarvestableNaturaTreeLeaves(thornVinesId));

        FactoryRegistry.registerFertilizable(new FertilizableNaturaCrop(cropsId));
        FactoryRegistry.registerFertilizable(new FertilizableSapling(floraSaplingId));
        FactoryRegistry.registerFertilizable(new FertilizableSapling(rareSaplingId));

        /*
         *  The sludge boiler takes sludge from harvester machines and boils it to get soil-like items,
         *  such as dirt, sand, clay, or rarely things like soulsand and mycelium. 
         */
        FactoryRegistry.registerSludgeDrop(5, new ItemStack(NContent.heatSand));
        FactoryRegistry.registerSludgeDrop(5, new ItemStack(NContent.taintedSoil));
    }
}
