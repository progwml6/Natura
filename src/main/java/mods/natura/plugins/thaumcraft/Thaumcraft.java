package mods.natura.plugins.thaumcraft;

import mods.natura.common.NContent;
import mods.natura.plugins.ICompatPlugin;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInterModComms;

public class Thaumcraft implements ICompatPlugin
{

    @Override
    public String getModId ()
    {
        return "Thaumcraft";
    }

    @Override
    public void preInit ()
    {

    }

    @Override
    public void init ()
    {
        //Thaumcraft
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.berryBush, 1, 12));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.berryBush, 1, 13));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.berryBush, 1, 14));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.berryBush, 1, 15));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.netherBerryBush, 1, 12));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.netherBerryBush, 1, 13));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.netherBerryBush, 1, 14));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.netherBerryBush, 1, 15));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(NContent.crops, 1, 8));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(NContent.crops, 1, 3));
    }

    @Override
    public void postInit ()
    {
        //TODO update this once Thaumcraft is 1.7.x
        /*

        try
        {
            Class.forName("thaumcraft.api.ThaumcraftApi");
            // Registering seeds
            AspectList seedTags = new AspectList();
            seedTags.add(Aspect.PLANT, 1);
            seedTags.add(Aspect.EXCHANGE, 1);
            ThaumcraftApi.registerObjectTag(seeds.itemID, 0, seedTags);
            ThaumcraftApi.registerObjectTag(seeds.itemID, 1, seedTags);

            // Registering plants
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 0, new AspectList().add(Aspect.LIFE, 2).add(Aspect.CROP, 2));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 3, new AspectList().add(Aspect.CLOTH, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 4, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 5, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 6, new AspectList().add(Aspect.BEAST, 1).add(Aspect.FLESH, 1).add(Aspect.CLOTH, 1).add(Aspect.ARMOR, 1).add(Aspect.FIRE, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 7, new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH, 1).add(Aspect.TRAP, 1).add(Aspect.FIRE, 1));

            // Registering wood
            AspectList logTags = new AspectList();
            logTags.add(Aspect.TREE, 4);
            ThaumcraftApi.registerObjectTag(tree.blockID, 0, logTags);
            ThaumcraftApi.registerObjectTag(tree.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(tree.blockID, 3, logTags);
            ThaumcraftApi.registerObjectTag(willow.blockID, 0, logTags);
            ThaumcraftApi.registerObjectTag(willow.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(redwood.blockID, 0, new AspectList().add(Aspect.ARMOR, 1).add(Aspect.TREE, 3));
            ThaumcraftApi.registerObjectTag(redwood.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(redwood.blockID, 2, new AspectList().add(Aspect.EARTH, 1).add(Aspect.TREE, 3));

            // Leafy goodness
            AspectList leafTags = new AspectList();
            leafTags.add(Aspect.PLANT, 2);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 2, new AspectList().add(Aspect.TREE, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 2, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 3, leafTags);

            // And rare trees, too.
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(rareTree.blockID, i, logTags);
                ThaumcraftApi.registerObjectTag(rareLeaves.blockID, i, leafTags);
            }

            // Add mushrooms
            AspectList shroomTag = new AspectList();
            shroomTag.add(Aspect.PLANT, 4);
            shroomTag.add(Aspect.LIGHT, 1);
            shroomTag.add(Aspect.SOUL, 1);
            for (int i = 0; i < 3; i++)
            {
                ThaumcraftApi.registerObjectTag(glowshroom.blockID, i, shroomTag);
            }

            // Adding berries!
            AspectList berryTag = new AspectList();
            berryTag.add(Aspect.LIFE, 1);
            berryTag.add(Aspect.CROP, 1);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(berryItem.itemID, i, berryTag);
            }

            // Adding berry bushes
            AspectList berryBushTag = new AspectList();
            berryBushTag.add(Aspect.PLANT, 1);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(berryBush.blockID, i, berryBushTag);
            }

            // Adding bowls, bowls of stew, and other bowl-based goodies!
            AspectList bowlEmptyTag = new AspectList();
            bowlEmptyTag.add(Aspect.VOID, 1);
            AspectList bowlStewTag = new AspectList();
            bowlStewTag.add(Aspect.PLANT, 6);
            bowlStewTag.add(Aspect.AIR, 1);
            bowlStewTag.add(Aspect.LIFE, 4);
            AspectList glowStewTag = new AspectList();
            glowStewTag.add(Aspect.PLANT, 8);
            glowStewTag.add(Aspect.AIR, 1);
            glowStewTag.add(Aspect.LIFE, 4);
            glowStewTag.add(Aspect.LIGHT, 4);

            for (int i = 0; i < 13; i++)
            {
                ThaumcraftApi.registerObjectTag(bowlEmpty.itemID, i, bowlEmptyTag);
                ThaumcraftApi.registerObjectTag(bowlStew.itemID, i, bowlStewTag);
                ThaumcraftApi.registerObjectTag(bowlStew.itemID, i + 13, glowStewTag);
            }

            // Adding other overworld saplings
            AspectList saplingTag = new AspectList();
            saplingTag.add(Aspect.TREE, 2);
            saplingTag.add(Aspect.PLANT, 2);
            ThaumcraftApi.registerObjectTag(rareSapling.blockID, 4, saplingTag);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(floraSapling.blockID, i, saplingTag);
                ThaumcraftApi.registerObjectTag(rareSapling.blockID, i, saplingTag);
            }

            //Cactus Stuff
            AspectList cactusTag = new AspectList();
            cactusTag.add(Aspect.WATER, 1);
            cactusTag.add(Aspect.PLANT, 2);
            cactusTag.add(Aspect.WEAPON, 1);
            cactusTag.add(Aspect.TREE, 2);

            ThaumcraftApi.registerObjectTag(saguaro.blockID, 0, cactusTag);
            ThaumcraftApi.registerObjectTag(waterDrop.itemID, 0, new AspectList().add(Aspect.WATER, 1));
            ThaumcraftApi.registerObjectTag(seedFood.itemID, 0, new AspectList().add(Aspect.CROP, 2).add(Aspect.PLANT, 1).add(Aspect.WATER, 1));

            // Overworld Clouds
            ThaumcraftApi.registerObjectTag(cloud.blockID, 0, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.WATER, 1).add(Aspect.WEATHER, 1));

            // Nether saplings
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 4, new AspectList().add(Aspect.SOUL, 1).add(Aspect.PLANT, 2).add(Aspect.TREE, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 5, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 6, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 7, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2));

            // Nether blocks
            ThaumcraftApi.registerObjectTag(heatSand.blockID, 0, new AspectList().add(Aspect.FIRE, 2).add(Aspect.STONE, 1));
            ThaumcraftApi.registerObjectTag(taintedSoil.blockID, 0, new AspectList().add(Aspect.STONE, 2));

            // Nether trees and leaves
            ThaumcraftApi.registerObjectTag(tree.blockID, 2, new AspectList().add(Aspect.TREE, 3).add(Aspect.SOUL, 1));
            ThaumcraftApi.registerObjectTag(planks.blockID, 2, new AspectList().add(Aspect.TREE, 1));
            ThaumcraftApi.registerObjectTag(planks.blockID, 4, new AspectList().add(Aspect.TREE, 1).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(darkTree.blockID, 0, new AspectList().add(Aspect.TREE, 4));
            ThaumcraftApi.registerObjectTag(darkTree.blockID, 1, new AspectList().add(Aspect.TREE, 4).add(Aspect.ENTROPY, 2));
            ThaumcraftApi.registerObjectTag(bloodwood.blockID, 0, new AspectList().add(Aspect.TREE, 2).add(Aspect.ENERGY, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(bloodwood.blockID, 15, new AspectList().add(Aspect.TREE, 2).add(Aspect.ENERGY, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 2, new AspectList().add(Aspect.PLANT, 2).add(Aspect.CROP, 2));
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 3, new AspectList().add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 1));

            //Nether vines and bushes
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 0, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.POISON, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 1, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.DARKNESS, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 2, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.FLIGHT, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 3, new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(thornVines.blockID, 0, new AspectList().add(Aspect.FIRE, 1).add(Aspect.PLANT, 1));

            // Nether and End clouds
            ThaumcraftApi.registerObjectTag(cloud.blockID, 2, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.FIRE, 1).add(Aspect.WEATHER, 1));
            ThaumcraftApi.registerObjectTag(cloud.blockID, 3, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.POISON, 1).add(Aspect.WEATHER, 1));
            ThaumcraftApi.registerObjectTag(cloud.blockID, 1, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.ELDRITCH, 1).add(Aspect.WEATHER, 1));

            //Other nether items/plants
            ThaumcraftApi.registerObjectTag(potashApple.itemID, 0, new AspectList().add(Aspect.CROP, 2).add(Aspect.POISON, 2));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 0, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.POISON, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 1, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.DARKNESS, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 2, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.FLIGHT, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 3, new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
        }
        catch (Exception e)
        {
            System.out.println("ThaumCraft integration failed.");
        }
        */
    }

}
