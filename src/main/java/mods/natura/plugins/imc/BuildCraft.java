package mods.natura.plugins.imc;

import mods.natura.common.NContent;
import mods.natura.plugins.ICompatPlugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInterModComms;

public class BuildCraft implements ICompatPlugin
{

    @Override
    public String getModId ()
    {
        return "BuildCraft|Transport";
    }

    @Override
    public void preInit ()
    {
    }

    @Override
    public void init ()
    {

        for (int i = 0; i < 13; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.planks, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.alternateWorkbench, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.alternateBookshelf, i));
        }
        for (int i = 0; i < 4; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.tree, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.rareTree, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.rareLeaves, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.darkLeaves, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.cloud, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.berryBush, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.netherBerryBush, i));
        }
        for (int i = 0; i < 3; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.redwood, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.floraLeaves, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.floraLeavesNoColor, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.taintedSoil, i));
        }

        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.willow, 0));
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.bloodwood, 15));
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.saguaro, 0));
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.heatSand, 0));

    }

    @Override
    public void postInit ()
    {
    }

    public int getId (Block b)
    {
        return Block.getIdFromBlock(b);
    }
}
