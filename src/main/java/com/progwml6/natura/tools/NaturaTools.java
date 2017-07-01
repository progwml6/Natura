package com.progwml6.natura.tools;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.tools.item.armor.ItemNaturaImpArmor;
import com.progwml6.natura.tools.item.bows.ItemNaturaBow;
import com.progwml6.natura.tools.item.tools.ItemNaturaAxe;
import com.progwml6.natura.tools.item.tools.ItemNaturaFlintAndBlaze;
import com.progwml6.natura.tools.item.tools.ItemNaturaKama;
import com.progwml6.natura.tools.item.tools.ItemNaturaPickaxe;
import com.progwml6.natura.tools.item.tools.ItemNaturaShovel;
import com.progwml6.natura.tools.item.tools.ItemNaturaSword;

import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaTools.PulseId, description = "All of the tools + armor added by natura")
public class NaturaTools extends NaturaPulse
{
    public static final String PulseId = "NaturaTools";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.tools.ToolsClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static Item impHelmet;
    public static Item impChestplate;
    public static Item impLeggings;
    public static Item impBoots;

    public static ItemStack impHelmetStack;
    public static ItemStack impChestplateStack;
    public static ItemStack impLeggingsStack;
    public static ItemStack impBootsStack;

    public static Item ghostwoodSword;
    public static Item ghostwoodPickaxe;
    public static Item ghostwoodShovel;
    public static Item ghostwoodAxe;
    public static Item ghostwoodKama;
    public static Item ghostwoodBow;

    public static Item bloodwoodSword;
    public static Item bloodwoodPickaxe;
    public static Item bloodwoodShovel;
    public static Item bloodwoodAxe;
    public static Item bloodwoodKama;
    public static Item bloodwoodBow;

    public static Item darkwoodSword;
    public static Item darkwoodPickaxe;
    public static Item darkwoodShovel;
    public static Item darkwoodAxe;
    public static Item darkwoodKama;
    public static Item darkwoodBow;

    public static Item fusewoodSword;
    public static Item fusewoodPickaxe;
    public static Item fusewoodShovel;
    public static Item fusewoodAxe;
    public static Item fusewoodKama;
    public static Item fusewoodBow;

    public static Item netherquartzSword;
    public static Item netherquartzPickaxe;
    public static Item netherquartzShovel;
    public static Item netherquartzAxe;
    public static Item netherquartzKama;

    public static Item flintAndBlaze;
    //@formatter:on

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        if (isEntitiesLoaded())
        {
            ArmorMaterial impMaterial = EnumHelper.addArmorMaterial("Imp", "natura:impArmor", 33, new int[] { 1, 3, 2, 1 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);

            impHelmet = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.HEAD), "imp_armor_helmet");
            impChestplate = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.CHEST), "imp_armor_chestplate");
            impLeggings = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.LEGS), "imp_armor_leggings");
            impBoots = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.FEET), "imp_armor_boots");

            impHelmetStack = new ItemStack(impHelmet);
            impHelmetStack.addEnchantment(Enchantments.PROTECTION, 1);
            impHelmetStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

            impChestplateStack = new ItemStack(impChestplate);
            impChestplateStack.addEnchantment(Enchantments.BLAST_PROTECTION, 1);
            impChestplateStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

            impLeggingsStack = new ItemStack(impLeggings);
            impLeggingsStack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1);
            impLeggingsStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

            impBootsStack = new ItemStack(impBoots);
            impBootsStack.addEnchantment(Enchantments.FEATHER_FALLING, 1);
            impBootsStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
        }

        if (isNetherLoaded())
        {
            ToolMaterial bloodwoodMaterial = EnumHelper.addToolMaterial("Bloodwood", 3, 350, 7f, 3, 24);

            ghostwoodSword = registerItem(registry, new ItemNaturaSword(ToolMaterial.WOOD), "ghostwood_sword");
            ghostwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(ToolMaterial.WOOD, 0), "ghostwood_pickaxe");
            ghostwoodShovel = registerItem(registry, new ItemNaturaShovel(ToolMaterial.WOOD, 0), "ghostwood_shovel");
            ghostwoodAxe = registerItem(registry, new ItemNaturaAxe(ToolMaterial.WOOD, 0), "ghostwood_axe");
            ghostwoodKama = registerItem(registry, new ItemNaturaKama(ToolMaterial.WOOD), "ghostwood_kama");
            ghostwoodBow = registerItem(registry, new ItemNaturaBow(384, ghostwoodBow), "ghostwood_bow");

            bloodwoodSword = registerItem(registry, new ItemNaturaSword(bloodwoodMaterial), "bloodwood_sword");
            bloodwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(bloodwoodMaterial, 2), "bloodwood_pickaxe");
            bloodwoodShovel = registerItem(registry, new ItemNaturaShovel(bloodwoodMaterial, 2), "bloodwood_shovel");
            bloodwoodAxe = registerItem(registry, new ItemNaturaAxe(bloodwoodMaterial, 2, 3.0F, -3.0F), "bloodwood_axe");
            bloodwoodKama = registerItem(registry, new ItemNaturaKama(bloodwoodMaterial), "bloodwood_kama");
            bloodwoodBow = registerItem(registry, new ItemNaturaBow(1501, bloodwoodBow), "bloodwood_bow");

            darkwoodSword = registerItem(registry, new ItemNaturaSword(ToolMaterial.STONE), "darkwood_sword");
            darkwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(ToolMaterial.STONE, 1), "darkwood_pickaxe");
            darkwoodShovel = registerItem(registry, new ItemNaturaShovel(ToolMaterial.STONE, 1), "darkwood_shovel");
            darkwoodAxe = registerItem(registry, new ItemNaturaAxe(ToolMaterial.STONE, 1), "darkwood_axe");
            darkwoodKama = registerItem(registry, new ItemNaturaKama(ToolMaterial.STONE), "darkwood_kama");
            darkwoodBow = registerItem(registry, new ItemNaturaBow(162, darkwoodBow), "darkwood_bow");

            fusewoodSword = registerItem(registry, new ItemNaturaSword(ToolMaterial.IRON), "fusewood_sword");
            fusewoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(ToolMaterial.IRON, 2), "fusewood_pickaxe");
            fusewoodShovel = registerItem(registry, new ItemNaturaShovel(ToolMaterial.IRON, 2), "fusewood_shovel");
            fusewoodAxe = registerItem(registry, new ItemNaturaAxe(ToolMaterial.IRON, 2), "fusewood_axe");
            fusewoodKama = registerItem(registry, new ItemNaturaKama(ToolMaterial.IRON), "fusewood_kama");
            fusewoodBow = registerItem(registry, new ItemNaturaBow(28, fusewoodBow), "fusewood_bow");

            netherquartzSword = registerItem(registry, new ItemNaturaSword(ToolMaterial.STONE), "netherquartz_sword");
            netherquartzPickaxe = registerItem(registry, new ItemNaturaPickaxe(ToolMaterial.STONE, 1), "netherquartz_pickaxe");
            netherquartzShovel = registerItem(registry, new ItemNaturaShovel(ToolMaterial.STONE, 1), "netherquartz_shovel");
            netherquartzAxe = registerItem(registry, new ItemNaturaAxe(ToolMaterial.STONE, 1), "netherquartz_axe");
            netherquartzKama = registerItem(registry, new ItemNaturaKama(ToolMaterial.STONE), "netherquartz_kama");

            flintAndBlaze = registerItem(registry, new ItemNaturaFlintAndBlaze(), "flint_and_blaze");
        }
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
}
