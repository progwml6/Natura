package com.progwml6.natura.shared;

import static com.progwml6.natura.shared.NaturaCommons.edibles;
import static com.progwml6.natura.shared.NaturaCommons.empty_bowls;
import static com.progwml6.natura.shared.NaturaCommons.materials;
import static com.progwml6.natura.shared.NaturaCommons.soups;

import com.progwml6.natura.common.ClientProxy;

import net.minecraft.item.ItemStack;

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
        materials.registerItemModels();
        empty_bowls.registerItemModels();
        soups.registerItemModels();
        edibles.registerItemModels();
    }

    @Override
    protected void registerItemModel(ItemStack item, String name)
    {
        // safety! We call it for everything even if it wasn't registered
        if (item == null)
        {
            return;
        }

        super.registerItemModel(item, name);
    }
}
