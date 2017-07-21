package com.progwml6.natura.shared;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.block.clouds.BlockCloud;
import com.progwml6.natura.shared.item.bags.ItemBoneBag;
import com.progwml6.natura.shared.item.bags.ItemSeedBag;
import com.progwml6.natura.shared.item.food.ItemNaturaEdible;
import com.progwml6.natura.shared.item.food.ItemNaturaEdibleSoup;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
    public static BlockCloud clouds;

    // Items
    public static ItemMetaDynamic materials;
    public static ItemMetaDynamic empty_bowls;
    public static ItemNaturaEdible edibles;
    public static ItemNaturaEdibleSoup soups;
    public static ItemSeedBag seed_bags;
    public static ItemBoneBag boneMealBag;
    public static ItemMetaDynamic sticks;

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

    // Apples
    public static ItemStack potashApple;

    public static ItemStack berryMedley;

    public static ItemStack cactusJuice;

    //Seed Bags
    public static ItemStack wheat_seed_bag;
    public static ItemStack carrots_seed_bag;
    public static ItemStack potatoes_seed_bag;
    public static ItemStack nether_wart_seed_bag;

    //Wood Sticks
    public static ItemStack maple_stick;
    public static ItemStack silverbell_stick;
    public static ItemStack amaranth_stick;
    public static ItemStack tiger_stick;
    public static ItemStack willow_stick;
    public static ItemStack eucalyptus_stick;
    public static ItemStack hopseed_stick;
    public static ItemStack sakura_stick;
    public static ItemStack redwood_stick;
    public static ItemStack ghostwood_stick;
    public static ItemStack darkwood_stick;
    public static ItemStack fusewood_stick;
    public static ItemStack bloodwood_stick;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        // Blocks
        clouds = registerEnumBlock(new BlockCloud(), "clouds");

        // Items
        materials = registerItem(new ItemMetaDynamic(), "materials");
        empty_bowls = registerItem(new ItemMetaDynamic(), "empty_bowls");
        edibles = registerItem(new ItemNaturaEdible(), "edibles");
        soups = registerItem(new ItemNaturaEdibleSoup(), "soups");
        seed_bags = registerItem(new ItemSeedBag(), "seed_bags");
        sticks = registerItem(new ItemMetaDynamic(), "sticks");

        materials.setCreativeTab(NaturaRegistry.tabGeneral);
        empty_bowls.setCreativeTab(NaturaRegistry.tabGeneral);
        edibles.setCreativeTab(NaturaRegistry.tabGeneral);
        soups.setCreativeTab(NaturaRegistry.tabGeneral);
        seed_bags.setCreativeTab(NaturaRegistry.tabGeneral);
        sticks.setCreativeTab(NaturaRegistry.tabGeneral);

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

            impmeatRaw = edibles.addFood(0, 3, 0.2f, 32, "impmeat_raw", false, new PotionEffect(MobEffects.HUNGER, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
            impmeatCooked = edibles.addFood(1, 8, 0.6f, 32, "impmeat_cooked", false, new PotionEffect(MobEffects.FIRE_RESISTANCE, 15 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
        }

        if (isOverworldLoaded())
        {
            raspberry = edibles.addFood(2, 1, 0.4F, 16, "raspberry", false);
            blueberry = edibles.addFood(3, 1, 0.4F, 16, "blueberry", false);
            blackberry = edibles.addFood(4, 1, 0.4F, 16, "blackberry", false);
            maloberry = edibles.addFood(5, 1, 0.4F, 16, "maloberry", false);
            berryMedley = soups.addFood(9, 5, 1.4F, 32, "berry_medley", new ItemStack(Items.BOWL), false);
        }

        if (isNetherLoaded())
        {
            blightberry = edibles.addFood(6, 1, 0.4F, 16, "blightberry", false, new PotionEffect(MobEffects.REGENERATION, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0), new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
            duskberry = edibles.addFood(7, 1, 0.4F, 16, "duskberry", false, new PotionEffect(MobEffects.NIGHT_VISION, 15 * 20, 0), new PotionEffect(MobEffects.BLINDNESS, 3 * 20, 0));
            skyberry = edibles.addFood(8, 1, 0.4F, 16, "skyberry", false, new PotionEffect(MobEffects.JUMP_BOOST, 8 * 20, 0), new PotionEffect(MobEffects.SLOWNESS, 3 * 20, 0));
            stingberry = edibles.addFood(9, 1, 0.4F, 16, "stingberry", false, new PotionEffect(MobEffects.STRENGTH, 10 * 20, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 10 * 20, 0));

            potashApple = edibles.addFood(10, 4, 0.4F, "potashapple", false, new PotionEffect(MobEffects.POISON, 2 * 25, 0));
        }

        cactusJuice = edibles.addFood(11, 1, 0.1f, 12, "cactusjuice", false);

        ghostwood_emptybowl = empty_bowls.addMeta(0, "ghostwood_bowl");
        bloodwood_emptybowl = empty_bowls.addMeta(1, "bloodwood_bowl");
        darkwood_emptybowl = empty_bowls.addMeta(2, "darkwood_bowl");
        fusewood_emptybowl = empty_bowls.addMeta(3, "fusewood_bowl");

        ghostwood_mushroomstew = soups.addFood(0, 6, 0.6f, 32, "ghostwood_mushroomstew", ghostwood_emptybowl, false);
        bloodwood_mushroomstew = soups.addFood(1, 6, 0.6f, 32, "bloodwood_mushroomstew", bloodwood_emptybowl, false);
        darkwood_mushroomstew = soups.addFood(2, 6, 0.6f, 32, "darkwood_mushroomstew", darkwood_emptybowl, false);
        fusewood_mushroomstew = soups.addFood(3, 6, 0.6f, 32, "fusewood_mushroomstew", fusewood_emptybowl, false);

        vanilla_glowshroomstew = soups.addFood(4, 6, 0.6f, 32, "vanilla_glowshroomstew", new ItemStack(Items.BOWL), false, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        ghostwood_glowshroomstew = soups.addFood(5, 6, 0.6f, 32, "ghostwood_glowshroomstew", ghostwood_emptybowl, false, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        bloodwood_glowshroomstew = soups.addFood(6, 3, 0.2f, 32, "bloodwood_glowshroomstew", bloodwood_emptybowl, false, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        darkwood_glowshroomstew = soups.addFood(7, 3, 0.2f, 32, "darkwood_glowshroomstew", darkwood_emptybowl, false, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));
        fusewood_glowshroomstew = soups.addFood(8, 3, 0.2f, 32, "fusewood_glowshroomstew", fusewood_emptybowl, false, new PotionEffect(MobEffects.NIGHT_VISION, 45 * 25, 0), new PotionEffect(MobEffects.POISON, 16 * 25, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 25, 0));

        wheat_seed_bag = seed_bags.addMeta(0, "wheat_seed_bag", Blocks.WHEAT.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        carrots_seed_bag = seed_bags.addMeta(1, "carrots_seed_bag", Blocks.CARROTS.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        potatoes_seed_bag = seed_bags.addMeta(2, "potatoes_seed_bag", Blocks.POTATOES.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        nether_wart_seed_bag = seed_bags.addMeta(3, "nether_wart_seed_bag", Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(0)));

        boneMealBag = registerItem(new ItemBoneBag(), "bonemeal_bag");

        if (isOverworldLoaded())
        {
            maple_stick = sticks.addMeta(0, "maple_stick");
            silverbell_stick = sticks.addMeta(1, "silverbell_stick");
            amaranth_stick = sticks.addMeta(2, "amaranth_stick");
            tiger_stick = sticks.addMeta(3, "tiger_stick");
            willow_stick = sticks.addMeta(4, "willow_stick");
            eucalyptus_stick = sticks.addMeta(5, "eucalyptus_stick");
            hopseed_stick = sticks.addMeta(6, "hopseed_stick");
            sakura_stick = sticks.addMeta(7, "sakura_stick");
            redwood_stick = sticks.addMeta(8, "redwood_stick");
        }

        if (isNetherLoaded())
        {
            ghostwood_stick = sticks.addMeta(9, "ghostwood_stick");
            darkwood_stick = sticks.addMeta(10, "darkwood_stick");
            fusewood_stick = sticks.addMeta(11, "fusewood_stick");
            bloodwood_stick = sticks.addMeta(12, "bloodwood_stick");
        }

        proxy.preInit();

        NaturaRegistry.tabGeneral.setDisplayIcon(cotton);

        if (!isOverworldLoaded())
        {
            NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(clouds));
        }
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
        // Crops
        GameRegistry.addRecipe(wheat_seed_bag.copy(), "sss", "sss", "sss", 's', Items.WHEAT_SEEDS);
        GameRegistry.addRecipe(new ShapedOreRecipe(potatoes_seed_bag.copy(), "sss", "sss", "sss", 's', "cropPotato"));
        GameRegistry.addRecipe(new ShapedOreRecipe(carrots_seed_bag.copy(), "sss", "sss", "sss", 's', "cropCarrot"));
        GameRegistry.addRecipe(nether_wart_seed_bag.copy(), "bcb", "bab", "bbb", 'a', new ItemStack(Blocks.NETHER_WART_BLOCK), 'b', new ItemStack(Items.STRING), 'c', new ItemStack(Items.PAPER));
        GameRegistry.addRecipe(new ItemStack(boneMealBag, 1, 0), "bcb", "bab", "bbb", 'a', new ItemStack(Blocks.BONE_BLOCK), 'b', new ItemStack(Items.STRING), 'c', new ItemStack(Items.PAPER));

        GameRegistry.addRecipe(new ItemStack(Items.WHEAT_SEEDS, 9, 0), "s", 's', wheat_seed_bag.copy());
        GameRegistry.addRecipe(new ItemStack(Items.POTATO, 9, 0), "s", 's', potatoes_seed_bag.copy());
        GameRegistry.addRecipe(new ItemStack(Items.CARROT, 9, 0), "s", 's', carrots_seed_bag.copy());
        GameRegistry.addRecipe(new ItemStack(Items.NETHER_WART, 9, 0), "s", 's', nether_wart_seed_bag.copy());
        GameRegistry.addRecipe(new ItemStack(Items.DYE, 9, 15), "s", 's', boneMealBag);

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STRING), "sss", 's', "cropCotton"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.WOOL), "sss", "sss", "sss", 's', "cropCotton"));

        GameRegistry.addRecipe(cactusJuice.copy(), "X", 'X', Blocks.CACTUS);
        GameRegistry.addRecipe(new ItemStack(Items.WATER_BUCKET, 1), "www", "wBw", "www", 'w', cactusJuice.copy(), 'B', Items.BUCKET);

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.BREAD), "bbb", 'b', "cropBarley"));
        GameRegistry.addRecipe(new ShapedOreRecipe(barleyFlour.copy(), "X", 'X', "cropBarley"));

        if (Config.enableWheatRecipe)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(wheatFlour.copy(), "X", 'X', "cropWheat"));
        }

        // Cake
        GameRegistry.addRecipe(new ItemStack(Items.CAKE, 1), "AAA", "BEB", " C ", 'A', Items.MILK_BUCKET, 'B', Items.SUGAR, 'C', wheatFlour.copy(), 'E', Items.EGG);
        GameRegistry.addRecipe(new ItemStack(Items.CAKE, 1), "AAA", "BEB", " C ", 'A', Items.MILK_BUCKET, 'B', Items.SUGAR, 'C', barleyFlour.copy(), 'E', Items.EGG);

        // Leather
        if (isEntitiesLoaded())
        {
            GameRegistry.addRecipe(new ItemStack(Items.LEATHER, 2), "##", "##", '#', impLeather.copy());
        }

        // Clouds
        GameRegistry.addRecipe(new ItemStack(Items.COAL, 1, 1), "ccc", "ccc", "ccc", 'c', new ItemStack(clouds, 1, BlockCloud.CloudType.ASH.getMeta()));
        GameRegistry.addRecipe(sulfurPowder.copy(), "cc", "cc", 'c', new ItemStack(clouds, 1, BlockCloud.CloudType.SULFUR.getMeta()));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.GUNPOWDER, 1, 0), "cc", "cc", 'c', "dustSulfur"));

        // Arrows
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.ARROW, 4, 0), " f ", "#s#", " # ", 's', "stickWood", '#', ghostwoodFletching.copy(), 'f', Items.FLINT));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.ARROW, 4, 0), " f ", "#s#", " # ", 's', new ItemStack(sticks, 1, OreDictionary.WILDCARD_VALUE), '#', ghostwoodFletching.copy(), 'f', Items.FLINT));

        // Misc
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.GLASS_BOTTLE, 3), "# #", " # ", '#', "glass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR), "GGG", "QQQ", "WWW", 'G', "glass", 'Q', "gemQuartz", 'W', "slabWood"));
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        furnaceRecipes.addSmeltingRecipe(barleyFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
        furnaceRecipes.addSmeltingRecipe(wheatFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
    }
}
