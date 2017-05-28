package com.progwml6.natura.plugin;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.gui.common.WorkbenchContainer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = CraftingTweaks.PulseId, modsRequired = CraftingTweaks.modid, defaultEnable = true)
public class CraftingTweaks
{
    public static final String modid = "craftingtweaks";

    public static final String PulseId = modid + "Integration";

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("ContainerClass", WorkbenchContainer.class.getName());
        tagCompound.setString("AlignToGrid", "left");
        FMLInterModComms.sendMessage(modid, "RegisterProvider", tagCompound);
    }
}
