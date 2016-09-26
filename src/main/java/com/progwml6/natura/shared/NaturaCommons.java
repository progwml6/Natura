package com.progwml6.natura.shared;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.block.hopper.BlockBlazeHopper;
import com.progwml6.natura.shared.item.ItemEdibleSoup;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import slimeknights.mantle.item.ItemEdible;
import slimeknights.mantle.item.ItemMetaDynamic;
import slimeknights.mantle.pulsar.pulse.Pulse;

/**
 * Contains items and blocks and stuff that is shared by multiple pulses, but might be required individually
 */
@Pulse(id = NaturaCommons.PulseId, forced = true)
public class NaturaCommons extends NaturaPulse
{
    public static final String PulseId = "NaturaCommons";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.shared.CommonsClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    // Blocks
    public static BlockBlazeHopper blaze_hopper;

    // Items
    public static ItemMetaDynamic materials;
    public static ItemMetaDynamic empty_bowls;
    public static ItemEdible edibles;
    public static ItemEdibleSoup soups;

    // Material Itemstacks
    public static ItemStack barley;
    public static ItemStack barleyFlour;
    public static ItemStack wheatFlour;
    public static ItemStack cotton;

    public static ItemStack sulfurPowder;
    public static ItemStack ghostwoodFletching;
    public static ItemStack blueDye;

    public static ItemStack impLeather;
    public static ItemStack flameString;

    // Imp Meat
    public static ItemStack impmeatRaw;
    public static ItemStack impmeatCooked;

    // Bowls
    public static ItemStack ghostwood_emptybowl;
    public static ItemStack bloodwood_emptybowl;
    public static ItemStack darkwood_emptybowl;
    public static ItemStack fusewood_emptybowl;

    // Soup
    public static ItemStack ghostwood_mushroomstew;
    public static ItemStack bloodwood_mushroomstew;
    public static ItemStack darkwood_mushroomstew;
    public static ItemStack fusewood_mushroomstew;

    public static ItemStack vanilla_glowshroomstew;
    public static ItemStack ghostwood_glowshroomstew;
    public static ItemStack bloodwood_glowshroomstew;
    public static ItemStack darkwood_glowshroomstew;
    public static ItemStack fusewood_glowshroomstew;

    // Berries
    public static ItemStack raspberry;
    public static ItemStack blueberry;
    public static ItemStack blackberry;
    public static ItemStack maloberry;
    public static ItemStack blightberry;
    public static ItemStack duskberry;
    public static ItemStack skyberry;
    public static ItemStack stingberry;

    public static ItemStack berryMedley;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        // Blocks
        blaze_hopper = registerBlock(new BlockBlazeHopper(), "blaze_hopper");

        // Items
        materials = registerItem(new ItemMetaDynamic(), "materials");
        empty_bowls = registerItem(new ItemMetaDynamic(), "empty_bowls");
        edibles = registerItem(new ItemEdible(), "edibles");
        soups = registerItem(new ItemEdibleSoup(), "soups");

        materials.setCreativeTab(NaturaRegistry.tabGeneral);
        empty_bowls.setCreativeTab(NaturaRegistry.tabGeneral);
        edibles.setCreativeTab(NaturaRegistry.tabGeneral);
        soups.setCreativeTab(NaturaRegistry.tabGeneral);

        barley = materials.addMeta(0, "barley");
        barleyFlour = materials.addMeta(1, "barley_flour");
        wheatFlour = materials.addMeta(2, "wheat_flour");
        cotton = materials.addMeta(3, "cotton");
        sulfurPowder = materials.addMeta(4, "sulfur_powder");
        ghostwoodFletching = materials.addMeta(5, "ghostwood_fletching");
        blueDye = materials.addMeta(8, "blue_dye");

        if (isEntitiesLoaded())
        {
            impLeather = materials.addMeta(6, "imp_leather");
            flameString = materials.addMeta(7, "flame_string");

            impmeatRaw = edibles.addFood(0, 3, 0.2f, "impmeat_raw", new PotionEffect(MobEffects.HUNGER, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
            impmeatCooked = edibles.addFood(1, 8, 0.6f, "impmeat_cooked", new PotionEffect(MobEffects.FIRE_RESISTANCE, 15 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
        }

        if (isOverworldLoaded())
        {
            raspberry = edibles.addFood(2, 1, 0.4F, "raspberry", true);
            blueberry = edibles.addFood(3, 1, 0.4F, "blueberry", true);
            blackberry = edibles.addFood(4, 1, 0.4F, "blackberry", true);
            maloberry = edibles.addFood(5, 1, 0.4F, "maloberry", true);
            berryMedley = soups.addFood(9, 5, 1.4F, "berry_medley", new ItemStack(Items.BOWL));
        }

        if (isNetherLoaded())
        {
            blightberry = edibles.addFood(6, 1, 0.4F, "blightberry", new PotionEffect(MobEffects.REGENERATION, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0), new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
            duskberry = edibles.addFood(7, 1, 0.4F, "duskberry", new PotionEffect(MobEffects.NIGHT_VISION, 15 * 20, 0), new PotionEffect(MobEffects.BLINDNESS, 3 * 20, 0));
            skyberry = edibles.addFood(8, 1, 0.4F, "skyberry", new PotionEffect(MobEffects.JUMP_BOOST, 8 * 20, 0), new PotionEffect(MobEffects.SLOWNESS, 3 * 20, 0));
            stingberry = edibles.addFood(9, 1, 0.4F, "stingberry", new PotionEffect(MobEffects.STRENGTH, 10 * 20, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 10 * 20, 0));
        }

        ghostwood_emptybowl = empty_bowls.addMeta(0, "ghostwood_bowl");
        bloodwood_emptybowl = empty_bowls.addMeta(1, "bloodwood_bowl");
        darkwood_emptybowl = empty_bowls.addMeta(2, "darkwood_bowl");
        fusewood_emptybowl = empty_bowls.addMeta(3, "fusewood_bowl");

        ghostwood_mushroomstew = soups.addFood(0, 6, 0.6f, "ghostwood_mushroomstew", ghostwood_emptybowl);
        bloodwood_mushroomstew = soups.addFood(1, 6, 0.6f, "bloodwood_mushroomstew", bloodwood_emptybowl);
        darkwood_mushroomstew = soups.addFood(2, 6, 0.6f, "darkwood_mushroomstew", darkwood_emptybowl);
        fusewood_mushroomstew = soups.addFood(3, 6, 0.6f, "fusewood_mushroomstew", fusewood_emptybowl);

        vanilla_glowshroomstew = soups.addFood(4, 6, 0.6f, "vanilla_glowshroomstew", new ItemStack(Items.BOWL), new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        ghostwood_glowshroomstew = soups.addFood(5, 6, 0.6f, "ghostwood_glowshroomstew", ghostwood_emptybowl, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        bloodwood_glowshroomstew = soups.addFood(6, 3, 0.2f, "bloodwood_glowshroomstew", bloodwood_emptybowl, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        darkwood_glowshroomstew = soups.addFood(7, 3, 0.2f, "darkwood_glowshroomstew", darkwood_emptybowl, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        fusewood_glowshroomstew = soups.addFood(8, 3, 0.2f, "fusewood_glowshroomstew", fusewood_emptybowl, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));

        proxy.preInit();

        NaturaRegistry.tabGeneral.setDisplayIcon(cotton);
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        this.registerRecipes();
        this.registerSmelting();
    }

    private void registerRecipes()
    {
        String[] berryTypes = new String[] { "cropRaspberry", "cropBlueberry", "cropBlackberry", "cropMaloberry", "cropStrawberry", "cropCranberry" };

        for (int iter1 = 0; iter1 < berryTypes.length - 2; iter1++)
        {
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 1; iter2++)
            {
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length; iter3++)
                {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(berryMedley.copy(), "bowlWood", berryTypes[iter1], berryTypes[iter2], berryTypes[iter3]));
                }
            }
        }

        ItemStack berryMix = berryMedley.copy();
        berryMix.stackSize = 2;

        for (int iter1 = 0; iter1 < berryTypes.length - 3; iter1++)
        {
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 2; iter2++)
            {
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length - 1; iter3++)
                {
                    for (int iter4 = iter3 + 1; iter4 < berryTypes.length; iter4++)
                    {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(berryMix.copy(), "bowlWood", "bowlWood", berryTypes[iter1], berryTypes[iter2], berryTypes[iter3], berryTypes[iter4]));
                    }
                }
            }
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STRING), "sss", 's', "cropCotton"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.WOOL), "sss", "sss", "sss", 's', "cropCotton"));
        GameRegistry.addRecipe(new ItemStack(Items.LEATHER, 2), "##", "##", '#', new ItemStack(impLeather.getItem(), 1, 6));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.ARROW, 4, 0), " f ", "#s#", " # ", 's', "stickWood", '#', new ItemStack(ghostwoodFletching.getItem(), 1, 5), 'f', Items.FLINT));
        GameRegistry.addRecipe(new ItemStack(Items.CAKE, 1), "AAA", "BEB", " C ", 'A', Items.MILK_BUCKET, 'B', Items.SUGAR, 'C', wheatFlour.copy(), 'E', Items.EGG);
        GameRegistry.addRecipe(new ItemStack(Items.CAKE, 1), "AAA", "BEB", " C ", 'A', Items.MILK_BUCKET, 'B', Items.SUGAR, 'C', barleyFlour.copy(), 'E', Items.EGG);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blaze_hopper), "# #", "#C#", " # ", '#', new ItemStack(Items.BLAZE_ROD), 'C', "chestWood"));

    }

    private void registerSmelting()
    {
        //FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(saguaro, 1, 0), new ItemStack(Items.DYE, 1, 2), 0.2F);
        FurnaceRecipes.instance().addSmeltingRecipe(impmeatRaw.copy(), impmeatCooked.copy(), 0.2F);
        FurnaceRecipes.instance().addSmeltingRecipe(barleyFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
        FurnaceRecipes.instance().addSmeltingRecipe(wheatFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
    }
}
