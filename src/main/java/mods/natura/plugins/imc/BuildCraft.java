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
        for (int i = 0; i < 4; i++)
        {            
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.berryBush, i));
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(NContent.netherBerryBush, i));
        }

        addFacade(NContent.saguaro, 0);
    }

    @Override
    public void postInit ()
    {
    }

    private void addFacade (Block b, int meta)
	{
	    FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(b, 1, meta));
	}

    public int getId (Block b)
    {
        return Block.getIdFromBlock(b);
    }
}
