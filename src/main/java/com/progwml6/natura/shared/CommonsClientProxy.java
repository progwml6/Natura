package com.progwml6.natura.shared;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;
import static com.progwml6.natura.shared.NaturaCommons.blaze_hopper;
import static com.progwml6.natura.shared.NaturaCommons.boneMealBag;
import static com.progwml6.natura.shared.NaturaCommons.clouds;
import static com.progwml6.natura.shared.NaturaCommons.edibles;
import static com.progwml6.natura.shared.NaturaCommons.empty_bowls;
import static com.progwml6.natura.shared.NaturaCommons.materials;
import static com.progwml6.natura.shared.NaturaCommons.seed_bags;
import static com.progwml6.natura.shared.NaturaCommons.soups;
import static com.progwml6.natura.shared.NaturaCommons.sticks;

import com.progwml6.natura.common.ClientProxy;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
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
        ModelLoader.setCustomStateMapper(blaze_hopper, (new Builder()).ignore(BlockHopper.ENABLED).build());

        registerItemModel(blaze_hopper);
        registerItemBlockMeta(clouds);

        materials.registerItemModels();
        empty_bowls.registerItemModels();
        soups.registerItemModels();
        edibles.registerItemModels();
        seed_bags.registerItemModels();
        sticks.registerItemModels();

        registerItemModel(boneMealBag);
    }
}
