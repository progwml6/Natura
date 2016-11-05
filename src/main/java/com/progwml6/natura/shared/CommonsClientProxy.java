package com.progwml6.natura.shared;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;
import static com.progwml6.natura.shared.NaturaCommons.edibles;
import static com.progwml6.natura.shared.NaturaCommons.empty_bowls;
import static com.progwml6.natura.shared.NaturaCommons.materials;
import static com.progwml6.natura.shared.NaturaCommons.soups;

import com.progwml6.natura.common.ClientProxy;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;

public class CommonsClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    protected void registerModels()
    {
        ModelLoader.setCustomStateMapper(NaturaCommons.blaze_hopper, (new StateMap.Builder()).ignore(BlockHopper.ENABLED).build());

        registerItemModel(NaturaCommons.blaze_hopper);
        registerItemBlockMeta(NaturaCommons.clouds);

        materials.registerItemModels();
        empty_bowls.registerItemModels();
        soups.registerItemModels();
        edibles.registerItemModels();
    }
}
