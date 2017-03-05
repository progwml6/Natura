package com.progwml6.natura.plugin;

import java.lang.reflect.Method;
import java.util.List;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.BlockNaturaDoor;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.logs.BlockNetherLog2;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.crops.BlockOverworldCrops;
import com.progwml6.natura.shared.NaturaCommons;

import mcp.mobius.waila.addons.HUDHandlerBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class HUDHandlerNatura extends HUDHandlerBase
{
    //@formatter:off
    static Method getCrop;
    static Method getDoor;
    
    //Overworld Blocks
    static Block overworldLog = NaturaOverworld.overworldLog;
    static Block overworldLog2 = NaturaOverworld.overworldLog2;
    static Block redwoodLog = NaturaOverworld.redwoodLog;
    static Block overworldLeaves = NaturaOverworld.overworldLeaves;
    static Block overworldLeaves2 = NaturaOverworld.overworldLeaves2;
    static Block redwoodLeaves = NaturaOverworld.redwoodLeaves;
    static Block overworldSapling = NaturaOverworld.overworldSapling;
    static Block overworldSapling2 = NaturaOverworld.overworldSapling2;
    static Block redwoodSapling = NaturaOverworld.redwoodSapling;
    static Block barley = NaturaOverworld.barleyCrop;
    static Block cotton = NaturaOverworld.cottonCrop;
    static Block eucalyptusDoor = NaturaOverworld.eucalyptusDoor;
    static Block hopseedDoor = NaturaOverworld.hopseedDoor;
    static Block sakuraDoor = NaturaOverworld.sakuraDoor;
    static Block redwoodDoor = NaturaOverworld.redwoodDoor;
    static Block redwoodBarkDoor = NaturaOverworld.redwoodBarkDoor;
    
    //Nether Blocks
    static Block netherLog = NaturaNether.netherLog;
    static Block netherLog2 = NaturaNether.netherLog2;
    static Block netherLeaves = NaturaNether.netherLeaves;
    static Block netherLeaves2 = NaturaNether.netherLeaves2;
    static Block netherSapling = NaturaNether.netherSapling;
    static Block netherSapling2 = NaturaNether.netherSapling2;
    static Block ghostwoodDoor = NaturaNether.ghostwoodDoor;
    static Block bloodwoodDoor = NaturaNether.bloodwoodDoor;
    //@formatter:on

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();

        if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
        {
            if (block == overworldLog || block == overworldLog2 || block == redwoodLog)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == overworldLeaves || block == overworldLeaves2 || block == redwoodLeaves)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == overworldSapling || block == overworldSapling2 || block == redwoodSapling)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 8);
            }

            if (config.getConfig("natura.alternatecropitem"))
            {
                if (block instanceof BlockOverworldCrops)
                {
                    if (getCrop == null)
                        getCrop = ReflectionHelper.findMethod(BlockOverworldCrops.class, null, new String[] { "getCrop", "getCrop" });

                    try
                    {
                        return new ItemStack((Item) getCrop.invoke(block));
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
            else
            {
                // Barley crop should display Barley item
                if (block == barley)
                    return NaturaCommons.barley.copy();

                // Cotton crop should display Cotton item
                if (block == cotton)
                    return NaturaCommons.cotton.copy();
            }

            if (config.getConfig("natura.alternatedooritem"))
            {
                if (block instanceof BlockNaturaDoor)
                {
                    if (getDoor == null)
                        getDoor = ReflectionHelper.findMethod(BlockNaturaDoor.class, null, new String[] { "getDoor", "getDoor" });

                    try
                    {
                        return new ItemStack((Item) getDoor.invoke(block));
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
            else
            {
                // Eucalyptus Door should display Eucalyptus Door item
                if (block == eucalyptusDoor)
                    return NaturaOverworld.eucalyptus_door.copy();

                // Hopseed Door should display Hopseed Door item
                if (block == hopseedDoor)
                    return NaturaOverworld.hopseed_door.copy();

                // Sakura Door should display Sakura Door item
                if (block == sakuraDoor)
                    return NaturaOverworld.sakura_door.copy();

                // Redwood Door should display Redwood Door item
                if (block == redwoodDoor)
                    return NaturaOverworld.redwood_door.copy();

                // Redwood Bark Door should display Redwood Bark Door item
                if (block == redwoodBarkDoor)
                    return NaturaOverworld.redwood_bark_door.copy();
            }
        }

        if (Natura.pulseManager.isPulseLoaded(NaturaNether.PulseId))
        {
            if (block == netherLog)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == netherLog2)
            {
                return new ItemStack(block, 1, accessor.getBlockState().getValue(BlockNetherLog2.META));
            }

            if (block == netherLeaves || block == netherLeaves2)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == netherSapling || block == netherSapling2)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 8);
            }

            if (config.getConfig("natura.alternatedooritem"))
            {
                if (block instanceof BlockNaturaDoor)
                {
                    if (getDoor == null)
                        getDoor = ReflectionHelper.findMethod(BlockNaturaDoor.class, null, new String[] { "getDoor", "getDoor" });

                    try
                    {
                        return new ItemStack((Item) getDoor.invoke(block));
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
            else
            {
                // Ghostwood Door should display Ghostwood Door item
                if (block == ghostwoodDoor)
                    return NaturaNether.ghostwood_door.copy();

                // Bloodwood Bark Door should display Bloodwood Bark Door item
                if (block == bloodwoodDoor)
                    return NaturaNether.bloodwood_door.copy();
            }
        }

        return null;
    }

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

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        if (te != null)
            te.writeToNBT(tag);
        return tag;
    }

    public static void register(IWailaRegistrar registrar)
    {
        registrar.addConfig("Natura", "natura.alternatecropitem");
        registrar.addConfig("Natura", "natura.alternatedooritem");

        IWailaDataProvider provider = new HUDHandlerNatura();

        //Overworld
        registrar.registerStackProvider(provider, overworldLog.getClass());
        registrar.registerStackProvider(provider, overworldLog2.getClass());
        registrar.registerStackProvider(provider, redwoodLog.getClass());
        registrar.registerStackProvider(provider, overworldLeaves.getClass());
        registrar.registerStackProvider(provider, overworldLeaves2.getClass());
        registrar.registerStackProvider(provider, redwoodLeaves.getClass());
        registrar.registerStackProvider(provider, overworldSapling.getClass());
        registrar.registerStackProvider(provider, overworldSapling2.getClass());
        registrar.registerStackProvider(provider, redwoodSapling.getClass());
        registrar.registerStackProvider(provider, barley.getClass());
        registrar.registerStackProvider(provider, cotton.getClass());
        registrar.registerStackProvider(provider, eucalyptusDoor.getClass());
        registrar.registerBodyProvider(provider, BlockOverworldCrops.class);
        registrar.registerBodyProvider(provider, BlockNaturaDoor.class);
        registrar.registerNBTProvider(provider, cotton.getClass());
        registrar.registerNBTProvider(provider, barley.getClass());
        registrar.registerNBTProvider(provider, eucalyptusDoor.getClass());

        //Nether
        registrar.registerStackProvider(provider, netherLog.getClass());
        registrar.registerStackProvider(provider, netherLog2.getClass());
        registrar.registerStackProvider(provider, netherLeaves.getClass());
        registrar.registerStackProvider(provider, netherLeaves2.getClass());
        registrar.registerStackProvider(provider, netherSapling.getClass());
        registrar.registerStackProvider(provider, netherSapling2.getClass());
    }
}
