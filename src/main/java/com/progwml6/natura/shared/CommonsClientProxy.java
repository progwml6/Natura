package com.progwml6.natura.shared;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;
import static com.progwml6.natura.shared.NaturaCommons.boneMealBag;
import static com.progwml6.natura.shared.NaturaCommons.clouds;
import static com.progwml6.natura.shared.NaturaCommons.edibles;
import static com.progwml6.natura.shared.NaturaCommons.empty_bowls;
import static com.progwml6.natura.shared.NaturaCommons.materials;
import static com.progwml6.natura.shared.NaturaCommons.seed_bags;
import static com.progwml6.natura.shared.NaturaCommons.soups;
import static com.progwml6.natura.shared.NaturaCommons.sticks;

import com.progwml6.natura.common.ClientProxy;

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
