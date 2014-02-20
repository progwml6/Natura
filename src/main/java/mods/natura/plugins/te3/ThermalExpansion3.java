package mods.natura.plugins.te3;

import java.util.Arrays;
import java.util.List;

import mods.natura.blocks.trees.Planks;
import mods.natura.common.NContent;
import mods.natura.items.blocks.NDoorItem;
import mods.natura.plugins.ICompatPlugin;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThermalExpansion3 implements ICompatPlugin
{
    private List<String> list;

    public int findPlankForName (String name)
    {
        return list.indexOf(name);
    }

    public void createSawmillRecipe (int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int chance)
    {
        NBTTagCompound toSend = new NBTTagCompound();
        toSend.setInteger("energy", energy);
        toSend.setTag("input", new NBTTagCompound());
        toSend.setTag("primaryOutput", new NBTTagCompound());

        input.writeToNBT(toSend.getCompoundTag("input"));
        primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));

        if (secondaryOutput != null)
        {
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", chance);
        }

        FMLInterModComms.sendMessage(getModId(), "SawmillRecipe", toSend);
    }

    public void createSawmillRecipe (int energy, ItemStack input, ItemStack primaryOutput)
    {
        createSawmillRecipe(energy, input, primaryOutput, null, 0);
    }

    public void addFenceGate (Block input, String type)
    {
        createSawmillRecipe(2400, new ItemStack(input, 1), new ItemStack(NContent.planks, 2, findPlankForName(type)), GameRegistry.findItemStack("ThermalExpansion", "sawdust", 1), 100);
    }

    public void addTrapdoor (Block input, String type)
    {
        createSawmillRecipe(2400, new ItemStack(input, 1), new ItemStack(NContent.planks, 3, findPlankForName(type)));
    }

    public void addWood (Block log, int meta, String type)
    {
        createSawmillRecipe(800, new ItemStack(log, 1, meta), new ItemStack(NContent.planks, 6, findPlankForName(type)), GameRegistry.findItemStack("ThermalExpansion", "sawdust", 1), 100);
    }

    public void addPressurePlate (Block input, String type)
    {
        createSawmillRecipe(2400, new ItemStack(input, 1), new ItemStack(NContent.planks, 2, findPlankForName(type)));
    }

    @Override
    public String getModId ()
    {
        return "ThermalExpansion";
    }

    @Override
    public void preInit ()
    {

    }

    @Override
    public void init ()
    {
        list = Arrays.asList(Planks.textureNames);
        // Sawmill recipes
        // - Doors
        String[] doorNames = ((NDoorItem) NContent.doorItem).textureNames;
        for (int i = 0; i < doorNames.length; i++)
        {
            int plankMeta = findPlankForName(doorNames[i]);
            if (plankMeta >= 0)
            {
                createSawmillRecipe(2400, new ItemStack(NContent.doorItem, 1, i), new ItemStack(NContent.planks, 6, plankMeta));
            }
        }
        String[] texNames = NContent.woodTextureNames;
        for (int i = 0; i < texNames.length; i++)
        {
            int plankMeta = findPlankForName(texNames[i]);
            if (plankMeta >= 0)
            {
                // Workbenches
                createSawmillRecipe(2400, new ItemStack(NContent.alternateWorkbench, 1, i), new ItemStack(NContent.planks, 4, plankMeta));
                // Bookshelves
                createSawmillRecipe(2400, new ItemStack(NContent.alternateBookshelf, 1, i), new ItemStack(NContent.planks, 6, plankMeta), new ItemStack(Items.book, 3), 100);
            }
        }
        // - Fence Gates
        addFenceGate(NContent.fenceGateAmaranth, "purpleheart");
        addFenceGate(NContent.fenceGateBloodwood, "bloodwood");
        addFenceGate(NContent.fenceGateDarkwood, "darkwood");
        addFenceGate(NContent.fenceGateEucalyptus, "eucalyptus");
        addFenceGate(NContent.fenceGateFusewood, "fusewood");
        addFenceGate(NContent.fenceGateGhostwood, "ghostwood");
        addFenceGate(NContent.fenceGateHopseed, "hopseed");
        addFenceGate(NContent.fenceGateMaple, "maple");
        addFenceGate(NContent.fenceGateRedwood, "redwood");
        addFenceGate(NContent.fenceGateSakura, "sakura");
        addFenceGate(NContent.fenceGateSilverbell, "silverbell");
        addFenceGate(NContent.fenceGateTiger, "tiger");
        addFenceGate(NContent.fenceGateWillow, "willow");
        // - Pressure Plates
        addPressurePlate(NContent.pressurePlateAmaranth, "purpleheart");
        addPressurePlate(NContent.pressurePlateBloodwood, "bloodwood");
        addPressurePlate(NContent.pressurePlateDarkwood, "darkwood");
        addPressurePlate(NContent.pressurePlateEucalyptus, "eucalyptus");
        addPressurePlate(NContent.pressurePlateFusewood, "fusewood");
        addPressurePlate(NContent.pressurePlateGhostwood, "ghostwood");
        addPressurePlate(NContent.pressurePlateHopseed, "hopseed");
        addPressurePlate(NContent.pressurePlateMaple, "maple");
        addPressurePlate(NContent.pressurePlateRedwood, "redwood");
        addPressurePlate(NContent.pressurePlateSakura, "sakura");
        addPressurePlate(NContent.pressurePlateSilverbell, "silverbell");
        addPressurePlate(NContent.pressurePlateTiger, "tiger");
        addPressurePlate(NContent.pressurePlateWillow, "willow");
        // - Trapdoors
        addTrapdoor(NContent.trapdoorAmaranth, "purpleheart");
        addTrapdoor(NContent.trapdoorBloodwood, "bloodwood");
        addTrapdoor(NContent.trapdoorDarkwood, "darkwood");
        addTrapdoor(NContent.trapdoorEucalyptus, "eucalyptus");
        addTrapdoor(NContent.trapdoorFusewood, "fusewood");
        addTrapdoor(NContent.trapdoorGhostwood, "ghostwood");
        addTrapdoor(NContent.trapdoorHopseed, "hopseed");
        addTrapdoor(NContent.trapdoorMaple, "maple");
        addTrapdoor(NContent.trapdoorRedwood, "redwood");
        addTrapdoor(NContent.trapdoorSakura, "sakura");
        addTrapdoor(NContent.trapdoorSilverbell, "silverbell");
        addTrapdoor(NContent.trapdoorTiger, "tiger");
        addTrapdoor(NContent.trapdoorWillow, "willow");
        // - Wood
        addWood(NContent.rareTree, 2, "amaranth");
        addWood(NContent.bloodwood, 0, "bloodwood");
        addWood(NContent.darkTree, 0, "darkwood");
        addWood(NContent.tree, 0, "eucalyptus");
        addWood(NContent.darkTree, 1, "fusewood");
        addWood(NContent.tree, 2, "ghostwood");
        addWood(NContent.tree, 3, "hopseed");
        addWood(NContent.rareTree, 0, "maple");
        addWood(NContent.redwood, 1, "redwood");
        addWood(NContent.tree, 1, "sakura");
        addWood(NContent.rareTree, 1, "silverbell");
        addWood(NContent.rareTree, 3, "tiger");
        addWood(NContent.willow, 0, "willow");
    }

    @Override
    public void postInit ()
    {
    }

}
