package com.progwml6.natura.shared;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.registration.object.EnumObject;
import com.progwml6.natura.library.registration.object.ItemObject;
import com.progwml6.natura.shared.block.CloudBlock;
import com.progwml6.natura.shared.item.BoneMealBagItem;
import com.progwml6.natura.shared.item.EdibleNaturaItem;
import com.progwml6.natura.shared.item.EdibleSoupItem;
import com.progwml6.natura.shared.item.SeedBagItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.Util;

/**
 * Contains items and blocks and stuff that is shared by multiple modules, but might be required individually
 */
public final class NaturaCommons extends NaturaModule {

  static final Logger log = Util.getLogger("natura_commons");

  /*
   * Blocks
   */
  public static final EnumObject<CloudBlock.Clouds, Block> clouds = BLOCKS.registerEnum(CloudBlock.Clouds.values(), "cloud", (cloud) -> new CloudBlock(Block.Properties.create(NaturaRegistry.CLOUD).hardnessAndResistance(0.3F).sound(SoundType.CLOTH), cloud.isSulfurCloud()), GENERAL_TOOLTIP_BLOCK_ITEM);

  /*
   * Items
   */
  public static final ItemObject<Item> barley = ITEMS.register("barley", GENERAL_PROPS);
  public static final ItemObject<Item> barleyFlour = ITEMS.register("barley_flour", GENERAL_PROPS);
  public static final ItemObject<Item> wheatFlour = ITEMS.register("wheat_flour", GENERAL_PROPS);
  public static final ItemObject<Item> cotton = ITEMS.register("cotton", GENERAL_PROPS);
  public static final ItemObject<Item> sulfurPowder = ITEMS.register("sulfur_powder", GENERAL_PROPS);
  public static final ItemObject<Item> ghostwoodFletching = ITEMS.register("ghostwood_fletching", GENERAL_PROPS);
  public static final ItemObject<Item> impLeather = ITEMS.register("imp_leather", GENERAL_PROPS);
  public static final ItemObject<Item> flameString = ITEMS.register("flame_string", GENERAL_PROPS);

  public static final ItemObject<EdibleNaturaItem> rawImpMeat = ITEMS.register("raw_imp_meat", () -> new EdibleNaturaItem(NaturaFood.RAW_IMP_MEAT, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> cookedImpMeat = ITEMS.register("cooked_imp_meat", () -> new EdibleNaturaItem(NaturaFood.COOKED_IMP_MEAT, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleNaturaItem> raspberry = ITEMS.register("raspberry", () -> new EdibleNaturaItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> blueberry = ITEMS.register("blueberry", () -> new EdibleNaturaItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> blackberry = ITEMS.register("blackberry", () -> new EdibleNaturaItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> maloberry = ITEMS.register("maloberry", () -> new EdibleNaturaItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleNaturaItem> blightberry = ITEMS.register("blightberry", () -> new EdibleNaturaItem(NaturaFood.BLIGHTBERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> duskberry = ITEMS.register("duskberry", () -> new EdibleNaturaItem(NaturaFood.DUSKBERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> skyberry = ITEMS.register("skyberry", () -> new EdibleNaturaItem(NaturaFood.SKYBERRY, NaturaRegistry.tabGeneral));
  public static final ItemObject<EdibleNaturaItem> stingberry = ITEMS.register("stingberry", () -> new EdibleNaturaItem(NaturaFood.STINGBERRY, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleNaturaItem> potashApple = ITEMS.register("potash_apple", () -> new EdibleNaturaItem(NaturaFood.POTASH_APPLE, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleNaturaItem> cactusJuice = ITEMS.register("cactus_juice", () -> new EdibleNaturaItem(NaturaFood.CACTUS_JUICE, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleSoupItem> berryMedley = ITEMS.register("berry_medley", () -> new EdibleSoupItem(NaturaFood.BERRY_MEDLEY, NaturaRegistry.tabGeneral));

  public static final ItemObject<EdibleSoupItem> glowshroomStew = ITEMS.register("glowshroom_stew", () -> new EdibleSoupItem(NaturaFood.GLOWSHROOM_STEW, NaturaRegistry.tabGeneral));

  public static final ItemObject<BoneMealBagItem> boneMealBag = ITEMS.register("bone_meal_bag", () -> new BoneMealBagItem(GENERAL_PROPS));

  public static final ItemObject<SeedBagItem> wheatSeedsBag = ITEMS.register("wheat_seeds_bag", () -> new SeedBagItem(GENERAL_PROPS, Blocks.WHEAT.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<SeedBagItem> carrotsSeedBag = ITEMS.register("carrots_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, Blocks.CARROTS.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<SeedBagItem> potatoesSeedBag = ITEMS.register("potatoes_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, Blocks.POTATOES.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<SeedBagItem> netherWartSeedBag = ITEMS.register("nether_wart_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, 0)));

  @SubscribeEvent
  void commonSetup(final FMLCommonSetupEvent event) {
    NaturaRegistry.tabGeneral.setDisplayIcon(new ItemStack(cotton));
  }
}
