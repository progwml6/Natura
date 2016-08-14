package com.progwml6.natura.world;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.world.block.logs.BlockEnumLog.EnumAxis;
import com.progwml6.natura.world.block.logs.BlockOverworldLog;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class WorldClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        registerItemBlockMeta(NaturaWorld.cloudBlock);
        registerItemBlockMeta(NaturaWorld.redwoodLog);

        //@formatter:off
        Item log = Item.getItemFromBlock(NaturaWorld.overworldLog);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s",
                    BlockOverworldLog.LOG_AXIS.getName(),
                    BlockOverworldLog.LOG_AXIS.getName(EnumAxis.Y),
                    BlockOverworldLog.TYPE.getName(),
                    BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(log, type.meta, new ModelResourceLocation(log.getRegistryName(), variant));
        }
        //@formatter:on
    }
}
