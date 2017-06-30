package com.progwml6.natura.tools;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.tools.item.armor.ItemNaturaImpArmor;
import com.progwml6.natura.tools.item.bows.ItemNaturaBow;
import com.progwml6.natura.tools.item.tools.ItemNaturaAxe;
import com.progwml6.natura.tools.item.tools.ItemNaturaFlintAndBlaze;
import com.progwml6.natura.tools.item.tools.ItemNaturaKama;
import com.progwml6.natura.tools.item.tools.ItemNaturaPickaxe;
import com.progwml6.natura.tools.item.tools.ItemNaturaShovel;
import com.progwml6.natura.tools.item.tools.ItemNaturaSword;

import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
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

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        if (isEntitiesLoaded())
        {
            ArmorMaterial impMaterial = EnumHelper.addArmorMaterial("Imp", "natura:impArmor", 33, new int[] { 1, 3, 2, 1 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);

            impHelmet = registerItem(new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.HEAD), "imp_armor_helmet");
            impChestplate = registerItem(new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.CHEST), "imp_armor_chestplate");
            impLeggings = registerItem(new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.LEGS), "imp_armor_leggings");
            impBoots = registerItem(new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.FEET), "imp_armor_boots");
        }

        if (isNetherLoaded())
        {
            ToolMaterial bloodwoodMaterial = EnumHelper.addToolMaterial("Bloodwood", 3, 350, 7f, 3, 24);

            ghostwoodSword = registerItem(new ItemNaturaSword(ToolMaterial.WOOD), "ghostwood_sword");
            ghostwoodPickaxe = registerItem(new ItemNaturaPickaxe(ToolMaterial.WOOD, 0), "ghostwood_pickaxe");
            ghostwoodShovel = registerItem(new ItemNaturaShovel(ToolMaterial.WOOD, 0), "ghostwood_shovel");
            ghostwoodAxe = registerItem(new ItemNaturaAxe(ToolMaterial.WOOD, 0), "ghostwood_axe");
            ghostwoodKama = registerItem(new ItemNaturaKama(ToolMaterial.WOOD), "ghostwood_kama");
            ghostwoodBow = registerItem(new ItemNaturaBow(384, ghostwoodBow), "ghostwood_bow");

            bloodwoodSword = registerItem(new ItemNaturaSword(bloodwoodMaterial), "bloodwood_sword");
            bloodwoodPickaxe = registerItem(new ItemNaturaPickaxe(bloodwoodMaterial, 2), "bloodwood_pickaxe");
            bloodwoodShovel = registerItem(new ItemNaturaShovel(bloodwoodMaterial, 2), "bloodwood_shovel");
            bloodwoodAxe = registerItem(new ItemNaturaAxe(bloodwoodMaterial, 2, 3.0F, -3.0F), "bloodwood_axe");
            bloodwoodKama = registerItem(new ItemNaturaKama(bloodwoodMaterial), "bloodwood_kama");
            bloodwoodBow = registerItem(new ItemNaturaBow(1501, bloodwoodBow), "bloodwood_bow");

            darkwoodSword = registerItem(new ItemNaturaSword(ToolMaterial.STONE), "darkwood_sword");
            darkwoodPickaxe = registerItem(new ItemNaturaPickaxe(ToolMaterial.STONE, 1), "darkwood_pickaxe");
            darkwoodShovel = registerItem(new ItemNaturaShovel(ToolMaterial.STONE, 1), "darkwood_shovel");
            darkwoodAxe = registerItem(new ItemNaturaAxe(ToolMaterial.STONE, 1), "darkwood_axe");
            darkwoodKama = registerItem(new ItemNaturaKama(ToolMaterial.STONE), "darkwood_kama");
            darkwoodBow = registerItem(new ItemNaturaBow(162, darkwoodBow), "darkwood_bow");

            fusewoodSword = registerItem(new ItemNaturaSword(ToolMaterial.IRON), "fusewood_sword");
            fusewoodPickaxe = registerItem(new ItemNaturaPickaxe(ToolMaterial.IRON, 2), "fusewood_pickaxe");
            fusewoodShovel = registerItem(new ItemNaturaShovel(ToolMaterial.IRON, 2), "fusewood_shovel");
            fusewoodAxe = registerItem(new ItemNaturaAxe(ToolMaterial.IRON, 2), "fusewood_axe");
            fusewoodKama = registerItem(new ItemNaturaKama(ToolMaterial.IRON), "fusewood_kama");
            fusewoodBow = registerItem(new ItemNaturaBow(28, fusewoodBow), "fusewood_bow");

            netherquartzSword = registerItem(new ItemNaturaSword(ToolMaterial.STONE), "netherquartz_sword");
            netherquartzPickaxe = registerItem(new ItemNaturaPickaxe(ToolMaterial.STONE, 1), "netherquartz_pickaxe");
            netherquartzShovel = registerItem(new ItemNaturaShovel(ToolMaterial.STONE, 1), "netherquartz_shovel");
            netherquartzAxe = registerItem(new ItemNaturaAxe(ToolMaterial.STONE, 1), "netherquartz_axe");
            netherquartzKama = registerItem(new ItemNaturaKama(ToolMaterial.STONE), "netherquartz_kama");

            flintAndBlaze = registerItem(new ItemNaturaFlintAndBlaze(), "flint_and_blaze");
        }

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        this.registerRecipes();
    }

    private void registerRecipes()
    {
        // Tools
        if (isNetherLoaded())
        {
            int[] plankMeta = { BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta(), BlockNetherPlanks.PlankType.BLOODWOOD.getMeta(), BlockNetherPlanks.PlankType.DARKWOOD.getMeta(), BlockNetherPlanks.PlankType.FUSEWOOD.getMeta() };
            ItemStack[] stickItem = { NaturaCommons.ghostwood_stick.copy(), NaturaCommons.bloodwood_stick.copy(), NaturaCommons.darkwood_stick.copy(), NaturaCommons.fusewood_stick.copy() };

            Item[][] tools = { { ghostwoodSword, ghostwoodPickaxe, ghostwoodShovel, ghostwoodAxe, ghostwoodKama, ghostwoodBow },
                    { bloodwoodSword, bloodwoodPickaxe, bloodwoodShovel, bloodwoodAxe, bloodwoodKama, bloodwoodBow },
                    { darkwoodSword, darkwoodPickaxe, darkwoodShovel, darkwoodAxe, darkwoodKama, darkwoodBow },
                    { fusewoodSword, fusewoodPickaxe, fusewoodShovel, fusewoodAxe, fusewoodKama, fusewoodBow } };

            for (int i = 0; i < plankMeta.length; i++)
            {
                addShapedRecipe(new ItemStack(tools[i][0], 1, 0), "#", "#", "s", '#', new ItemStack(NaturaNether.netherPlanks, 1, plankMeta[i]), 's', stickItem[i]);
                addShapedRecipe(new ItemStack(tools[i][1], 1, 0), "###", " s ", " s ", '#', new ItemStack(NaturaNether.netherPlanks, 1, plankMeta[i]), 's', stickItem[i]);
                addShapedRecipe(new ItemStack(tools[i][2], 1, 0), "#", "s", "s", '#', new ItemStack(NaturaNether.netherPlanks, 1, plankMeta[i]), 's', stickItem[i]);
                addShapedRecipe(new ItemStack(tools[i][3], 1, 0), "##", "#s", " s", '#', new ItemStack(NaturaNether.netherPlanks, 1, plankMeta[i]), 's', stickItem[i]);
                addShapedRecipe(new ItemStack(tools[i][4], 1, 0), "##", " s", " s", '#', new ItemStack(NaturaNether.netherPlanks, 1, plankMeta[i]), 's', stickItem[i]);
                addShapedRecipe(new ItemStack(tools[i][5], 1, 0), "#s ", "# s", "#s ", '#', NaturaCommons.flameString.copy(), 's', stickItem[i]);
            }

            GameRegistry.addRecipe(new ItemStack(netherquartzSword, 1, 0), "#", "#", "s", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE), 's', NaturaCommons.ghostwood_stick.copy());
            GameRegistry.addRecipe(new ItemStack(netherquartzPickaxe, 1, 0), "###", " s ", " s ", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE), 's', NaturaCommons.ghostwood_stick.copy());
            GameRegistry.addRecipe(new ItemStack(netherquartzShovel, 1, 0), "#", "s", "s", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE), 's', NaturaCommons.ghostwood_stick.copy());
            GameRegistry.addRecipe(new ItemStack(netherquartzAxe, 1, 0), "##", "#s", " s", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE), 's', NaturaCommons.ghostwood_stick.copy());
            GameRegistry.addRecipe(new ItemStack(netherquartzKama, 1, 0), "##", " s", " s", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE), 's', NaturaCommons.ghostwood_stick.copy());

            GameRegistry.addRecipe(new ItemStack(flintAndBlaze, 1, 0), "AB", 'A', Items.BLAZE_ROD, 'B', Items.FLINT);
        }

        if (isEntitiesLoaded())
        {
            impHelmetStack = new ItemStack(impHelmet);
            impHelmetStack.addEnchantment(Enchantments.PROTECTION, 1);
            impHelmetStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
            GameRegistry.addRecipe(impHelmetStack.copy(), "###", "# #", '#', NaturaCommons.impLeather.copy());

            impChestplateStack = new ItemStack(impChestplate);
            impChestplateStack.addEnchantment(Enchantments.BLAST_PROTECTION, 1);
            impChestplateStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
            GameRegistry.addRecipe(impChestplateStack.copy(), "# #", "###", "###", '#', NaturaCommons.impLeather.copy());

            impLeggingsStack = new ItemStack(impLeggings);
            impLeggingsStack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1);
            impLeggingsStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
            GameRegistry.addRecipe(impLeggingsStack.copy(), "###", "# #", "# #", '#', NaturaCommons.impLeather.copy());

            impBootsStack = new ItemStack(impBoots);
            impBootsStack.addEnchantment(Enchantments.FEATHER_FALLING, 1);
            impBootsStack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
            GameRegistry.addRecipe(impBootsStack.copy(), "# #", "# #", '#', NaturaCommons.impLeather.copy());
        }
    }

}
