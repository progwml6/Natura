package com.progwml6.natura.plugin.waila;

import java.util.List;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.crops.BlockOverworldCrops;
import com.progwml6.natura.shared.NaturaCommons;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class HUDHandlerNatura implements IWailaDataProvider
{
    @Nonnull
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();

        if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
        {
            // Barley crop should display Barley item
            if (block == NaturaOverworld.barleyCrop)
            {
                return NaturaCommons.barley.copy();
            }

            // Cotton crop should display Cotton item
            if (block == NaturaOverworld.cottonCrop)
            {
                return NaturaCommons.cotton.copy();
            }
        }

        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();

        // Displays maturity percentage
        if (config.getConfig("general.showcrop") && BlockOverworldCrops.class.isInstance(block))
        {
            float growthValue = (accessor.getMetadata() / (float) ((BlockOverworldCrops) block).getMaxAge()) * 100.0F;
            if (growthValue < 100.0)
            {
                currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
            }
            else
            {
                currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.growth"), LangUtil.translateG("hud.msg.mature")));
            }

            return currenttip;
        }

        return currenttip;
    }

    public static void register(IWailaRegistrar registrar)
    {
        IWailaDataProvider provider = new HUDHandlerNatura();

        //Overworld
        registrar.registerStackProvider(provider, BlockOverworldCrops.class);
        registrar.registerBodyProvider(provider, BlockOverworldCrops.class);
    }
}
