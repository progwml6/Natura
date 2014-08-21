package mods.natura.plugins.imc;

import mantle.module.ILoadableModule;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInterModComms;

public class BuildCraft implements ILoadableModule
{

    public static String modId = "BuildCraft|Transport";

    @Override
    public void preInit ()
    {
    }

    @Override
    public void init ()
    {
        for (int i = 0; i < 4; i++)
        {
            addFacade(NContent.berryBush, i);
            addFacade(NContent.netherBerryBush, i);
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
}
