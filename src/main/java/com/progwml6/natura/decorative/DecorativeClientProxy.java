package com.progwml6.natura.decorative;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;

import com.progwml6.natura.common.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;

public class DecorativeClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    protected void registerModels()
    {
        for (Block block : NaturaDecorative.fenceGates)
        {
            if (block != null)
            {
                ModelLoader.setCustomStateMapper(block, (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
            }
        }

        for (Block block : NaturaDecorative.buttons)
        {
            if (block != null)
            {
                registerItemModel(block);
            }
        }

        for (Block block : NaturaDecorative.pressurePlates)
        {
            if (block != null)
            {
                registerItemModel(block);
            }
        }

        for (Block block : NaturaDecorative.trapDoors)
        {
            if (block != null)
            {
                registerItemModel(block);
            }
        }

        for (Block block : NaturaDecorative.fences)
        {
            if (block != null)
            {
                registerItemModel(block);
            }
        }

        for (Block block : NaturaDecorative.fenceGates)
        {
            if (block != null)
            {
                registerItemModel(block);
            }
        }

        registerItemBlockMeta(NaturaDecorative.overworldBookshelves);
        registerItemBlockMeta(NaturaDecorative.netherBookshelves);

        registerItemBlockMeta(NaturaDecorative.overworldWorkbenches);
        registerItemBlockMeta(NaturaDecorative.netherWorkbenches);
    }
}
