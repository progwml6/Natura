package com.progwml6.natura.shared;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.data.CommonRecipeProvider;
import com.progwml6.natura.shared.item.BoneMealBagItem;
import com.progwml6.natura.shared.item.SeedBagItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.EdibleItem;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.ItemObject;

/**
 * Contains items and blocks and stuff that is shared by multiple modules, but might be required individually
 */
@SuppressWarnings("unused")
public class NaturaCommons extends NaturaModule {

  static final Logger log = Util.getLogger("natura_commons");

  /*
   * Blocks
   */
  // clay
  private static final Block.Properties DRIED_CLAY = builder(Material.ROCK, ToolType.PICKAXE, SoundType.STONE).setRequiresTool().hardnessAndResistance(1.5F, 20.0F);
  public static final BuildingBlockObject driedClay = BLOCKS.registerBuilding("dried_clay", DRIED_CLAY, GENERAL_BLOCK_ITEM);
  public static final BuildingBlockObject driedClayBricks = BLOCKS.registerBuilding("dried_clay_bricks", DRIED_CLAY, GENERAL_BLOCK_ITEM);

  /*
   * Items
   */
  public static final ItemObject<Item> driedBrick = ITEMS.register("dried_brick", GENERAL_PROPS);

  public static final ItemObject<Item> barley = ITEMS.register("barley", TOOLTIP_ITEM);
  public static final ItemObject<Item> barleyFlour = ITEMS.register("barley_flour", TOOLTIP_ITEM);
  public static final ItemObject<Item> wheatFlour = ITEMS.register("wheat_flour", TOOLTIP_ITEM);
  public static final ItemObject<Item> cotton = ITEMS.register("cotton", TOOLTIP_ITEM);

  public static final ItemObject<EdibleItem> cactusJuice = ITEMS.register("cactus_juice", () -> new EdibleItem(NaturaFood.CACTUS_JUICE, TAB_GENERAL));

  public static final ItemObject<Item> wheatSeedBag = ITEMS.register("wheat_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, () -> Blocks.WHEAT.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<Item> carrotsSeedBag = ITEMS.register("carrots_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, () -> Blocks.CARROTS.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<Item> potatoesSeedBag = ITEMS.register("potatoes_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, () -> Blocks.POTATOES.getDefaultState().with(CropsBlock.AGE, 0)));
  public static final ItemObject<Item> netherWartSeedBag = ITEMS.register("nether_wart_seed_bag", () -> new SeedBagItem(GENERAL_PROPS, () -> Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, 0)));

  public static final ItemObject<Item> boneMealBag = ITEMS.register("bone_meal_bag", () -> new BoneMealBagItem(GENERAL_PROPS));

  @SubscribeEvent
  void gatherData(final GatherDataEvent event) {
    if (event.includeServer()) {
      DataGenerator dataGenerator = event.getGenerator();
      dataGenerator.addProvider(new CommonRecipeProvider(dataGenerator));
    }
  }
}
