package com.progwml6.natura.tools;

import com.progwml6.natura.common.ClientProxy;

import net.minecraft.item.ItemStack;

public class ToolsClientProxy extends ClientProxy
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
        // Armor
        this.registerItemModelNatura(new ItemStack(NaturaTools.impHelmet), "imp_armor_helmet");
        this.registerItemModelNatura(new ItemStack(NaturaTools.impChestplate), "imp_armor_chestplate");
        this.registerItemModelNatura(new ItemStack(NaturaTools.impLeggings), "imp_armor_leggings");
        this.registerItemModelNatura(new ItemStack(NaturaTools.impBoots), "imp_armor_boots");

        this.registerItemModelNatura(new ItemStack(NaturaTools.ghostwoodSword), "ghostwood_sword");
        this.registerItemModelNatura(new ItemStack(NaturaTools.ghostwoodPickaxe), "ghostwood_pickaxe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.ghostwoodShovel), "ghostwood_shovel");
        this.registerItemModelNatura(new ItemStack(NaturaTools.ghostwoodAxe), "ghostwood_axe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.ghostwoodKama), "ghostwood_kama");

        ItemStack stack = new ItemStack(NaturaTools.ghostwoodBow, 1, 0);
        this.registerItemModelNatura(stack, "ghostwood_bow");
        stack = new ItemStack(NaturaTools.ghostwoodBow, 1, 1);
        this.registerItemModelNatura(stack, "ghostwood_bow_pulling_0");
        stack = new ItemStack(NaturaTools.ghostwoodBow, 1, 2);
        this.registerItemModelNatura(stack, "ghostwood_bow_pulling_1");
        stack = new ItemStack(NaturaTools.ghostwoodBow, 1, 3);
        this.registerItemModelNatura(stack, "ghostwood_bow_pulling_2");

        this.registerItemModelNatura(new ItemStack(NaturaTools.bloodwoodSword), "bloodwood_sword");
        this.registerItemModelNatura(new ItemStack(NaturaTools.bloodwoodPickaxe), "bloodwood_pickaxe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.bloodwoodShovel), "bloodwood_shovel");
        this.registerItemModelNatura(new ItemStack(NaturaTools.bloodwoodAxe), "bloodwood_axe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.bloodwoodKama), "bloodwood_kama");

        stack = new ItemStack(NaturaTools.bloodwoodBow, 1, 0);
        this.registerItemModelNatura(stack, "bloodwood_bow");
        stack = new ItemStack(NaturaTools.bloodwoodBow, 1, 1);
        this.registerItemModelNatura(stack, "bloodwood_bow_pulling_0");
        stack = new ItemStack(NaturaTools.bloodwoodBow, 1, 2);
        this.registerItemModelNatura(stack, "bloodwood_bow_pulling_1");
        stack = new ItemStack(NaturaTools.bloodwoodBow, 1, 3);
        this.registerItemModelNatura(stack, "bloodwood_bow_pulling_2");

        this.registerItemModelNatura(new ItemStack(NaturaTools.darkwoodSword), "darkwood_sword");
        this.registerItemModelNatura(new ItemStack(NaturaTools.darkwoodPickaxe), "darkwood_pickaxe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.darkwoodShovel), "darkwood_shovel");
        this.registerItemModelNatura(new ItemStack(NaturaTools.darkwoodAxe), "darkwood_axe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.darkwoodKama), "darkwood_kama");

        stack = new ItemStack(NaturaTools.darkwoodBow, 1, 0);
        this.registerItemModelNatura(stack, "darkwood_bow");
        stack = new ItemStack(NaturaTools.darkwoodBow, 1, 1);
        this.registerItemModelNatura(stack, "darkwood_bow_pulling_0");
        stack = new ItemStack(NaturaTools.darkwoodBow, 1, 2);
        this.registerItemModelNatura(stack, "darkwood_bow_pulling_1");
        stack = new ItemStack(NaturaTools.darkwoodBow, 1, 3);
        this.registerItemModelNatura(stack, "darkwood_bow_pulling_2");

        this.registerItemModelNatura(new ItemStack(NaturaTools.fusewoodSword), "fusewood_sword");
        this.registerItemModelNatura(new ItemStack(NaturaTools.fusewoodPickaxe), "fusewood_pickaxe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.fusewoodShovel), "fusewood_shovel");
        this.registerItemModelNatura(new ItemStack(NaturaTools.fusewoodAxe), "fusewood_axe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.fusewoodKama), "fusewood_kama");

        stack = new ItemStack(NaturaTools.fusewoodBow, 1, 0);
        this.registerItemModelNatura(stack, "fusewood_bow");
        stack = new ItemStack(NaturaTools.fusewoodBow, 1, 1);
        this.registerItemModelNatura(stack, "fusewood_bow_pulling_0");
        stack = new ItemStack(NaturaTools.fusewoodBow, 1, 2);
        this.registerItemModelNatura(stack, "fusewood_bow_pulling_1");
        stack = new ItemStack(NaturaTools.fusewoodBow, 1, 3);
        this.registerItemModelNatura(stack, "fusewood_bow_pulling_2");

        this.registerItemModelNatura(new ItemStack(NaturaTools.netherquartzSword), "netherquartz_sword");
        this.registerItemModelNatura(new ItemStack(NaturaTools.netherquartzPickaxe), "netherquartz_pickaxe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.netherquartzShovel), "netherquartz_shovel");
        this.registerItemModelNatura(new ItemStack(NaturaTools.netherquartzAxe), "netherquartz_axe");
        this.registerItemModelNatura(new ItemStack(NaturaTools.netherquartzKama), "netherquartz_kama");

        this.registerItemModelNatura(new ItemStack(NaturaTools.flintAndBlaze), "flint_and_blaze");
    }
}
