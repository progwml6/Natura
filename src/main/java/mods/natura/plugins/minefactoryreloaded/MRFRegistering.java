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
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;

public class MRFRegistering
{
    public static void registerWithMFR ()
    { //items
        Item seedsId = NContent.seeds;
        Item berryItemId = NContent.berryItem ;
        Item netherBerryItemId = NContent.netherBerryItem ;
        Item saguaroFruitId = NContent.seedFood ;
        Item cottonItemId = NContent.plantItem ;

        //blocks -- tried to imitate the sorting of PHNatura, but grabbed the block IDs from NContent
        Block saguaroId = NContent.saguaro  ;

        Block netherSaplingId = NContent.netherSapling  ;
        Block netherBerryBushId = NContent.netherBerryBush  ;
        Block floraSaplingId = NContent.floraSapling  ;
        Block berryBushId = NContent.berryBush  ;
        Block floraLeavesNoColorId = NContent.floraLeavesNoColor  ;
        Block floraLeavesId = NContent.floraLeaves  ;
        Block cropsId = NContent.crops  ;
        Block redwoodId = NContent.redwood  ;
        Block treeId = NContent.tree  ;
        Block bloodwoodId = NContent.bloodwood  ;

        Block glowshroomId = NContent.glowshroom  ;
        Block darkTreeId = NContent.darkTree  ;
        Block darkLeavesId = NContent.darkLeaves  ;

        Block glowshroomBlueId = NContent.glowshroomBlue  ;
        Block glowshroomGreenId = NContent.glowshroomGreen  ;
        Block glowshroomPurpleId = NContent.glowshroomPurple  ;

        Block rareTreeId = NContent.rareTree  ;
        Block rareLeavesId = NContent.rareLeaves  ;
        Block rareSaplingId = NContent.rareSapling  ;

        Block willowId = NContent.willow  ;
        Block bluebellsId = NContent.bluebells  ;
        Block thornVinesId = NContent.thornVines  ;

        FactoryRegistry.sendMessage("registerPlantable",new PlantableNaturaCrop(seedsId, cropsId));
        FactoryRegistry.sendMessage("registerPlantable",new PlantableNaturaBerry(Item.getItemFromBlock(berryBushId), berryBushId));
        FactoryRegistry.sendMessage("registerPlantable",new PlantableNaturaNetherBerry(Item.getItemFromBlock(netherBerryBushId), netherBerryBushId));
        FactoryRegistry.sendMessage("registerPlantable",new PlantableStandard(saguaroFruitId, saguaroId));
        FactoryRegistry.sendMessage("registerPlantable",new PlantableStandard(Item.getItemFromBlock(floraSaplingId), floraSaplingId));
        FactoryRegistry.sendMessage("registerPlantable",new PlantableStandard(Item.getItemFromBlock(netherSaplingId), netherSaplingId));

        //misc plants
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(bluebellsId, HarvestType.Normal));
        //glowshrooms
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(glowshroomId, HarvestType.Normal));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(glowshroomBlueId, HarvestType.Normal));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(glowshroomGreenId, HarvestType.Normal));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(glowshroomPurpleId, HarvestType.Normal));
        //crops
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaCropPlant(cropsId, cottonItemId));
        //bushes
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaBerry(berryBushId, berryItemId));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaBerry(netherBerryBushId, netherBerryItemId));
        //trees
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(treeId, HarvestType.Tree));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(rareTreeId, HarvestType.Tree));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(darkTreeId, HarvestType.Tree));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(redwoodId, HarvestType.Tree));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(willowId, HarvestType.Tree));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableStandard(bloodwoodId, HarvestType.TreeFlipped));
        //leaves
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaTreeLeaves(rareLeavesId));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaTreeLeaves(darkLeavesId));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaTreeLeaves(floraLeavesId));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaTreeLeaves(floraLeavesNoColorId));
        FactoryRegistry.sendMessage("registerHarvestable",new HarvestableNaturaTreeLeaves(thornVinesId));

        FactoryRegistry.sendMessage("registerFertilizable",new FertilizableNaturaCrop(cropsId));
        FactoryRegistry.sendMessage("registerFertilizable", new FertilizableSapling(floraSaplingId));
        FactoryRegistry.sendMessage("registerFertilizable",new FertilizableSapling(rareSaplingId));

        /*
         *  The sludge boiler takes sludge from harvester machines and boils it to get soil-like items,
         *  such as dirt, sand, clay, or rarely things like soulsand and mycelium. 
         */
        NBTTagCompound tmp1 = new ItemStack(NContent.heatSand).writeToNBT(new NBTTagCompound());
        tmp1.setInteger("value", 5);
        FactoryRegistry.sendMessage("registerSludgeDrop", tmp1);
        NBTTagCompound tmp2 = new ItemStack(NContent.taintedSoil).writeToNBT(new NBTTagCompound());
        tmp1.setInteger("value", 5);
        FactoryRegistry.sendMessage("registerSludgeDrop",  tmp2);
    }
}
