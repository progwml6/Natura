package mods.natura.plugins.imc;

import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;

@Pulse(id = "Natura BuildCraft Compatibility", modsRequired = BuildCraft.modId)
public class BuildCraft
{

    public static final String modId = "BuildCraft|Transport";

    @Handler
    public void init (FMLInitializationEvent evt)
    {
        for (int i = 0; i < 4; i++)
        {
            addFacade(NContent.berryBush, i);
            addFacade(NContent.netherBerryBush, i);
        }

        addFacade(NContent.saguaro, 0);
    }

    private void addFacade (Block b, int meta)
    {
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", new ItemStack(b, 1, meta));
    }
}
