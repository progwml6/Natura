package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.item.BlockNamedTooltipItem;
import com.progwml6.natura.library.utils.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.WoodBlockObject;
import slimeknights.mantle.util.SupplierCreativeTab;

import java.util.function.Function;

@SuppressWarnings("unused")
public class NaturaWorld extends NaturaModule {

  public static final CreativeModeTab TAB_OVERWORLD = new SupplierCreativeTab(Natura.MOD_ID, "world", () -> new ItemStack(NaturaWorld.maple.getLog()));
  static final Logger log = Util.getLogger("natura_world");

  /*
   * Block base properties
   */
  private static final Item.Properties OVERWORLD_PROPS = new Item.Properties().tab(TAB_OVERWORLD);
  private static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().tab(TAB_OVERWORLD).stacksTo(1);
  private static final Function<Block, ? extends BlockItem> DEFAULT_BLOCK_ITEM = (b) -> new BlockItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> NAMED_TOOLTIP_BLOCK_ITEM = (b) -> new BlockNamedTooltipItem(b, OVERWORLD_PROPS);

  private static Function<WoodBlockObject.WoodVariant, BlockBehaviour.Properties> createWood() {
    return type -> switch (type) {
      case WOOD, LOG ->
        BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).requiresCorrectToolForDrops();
      default -> BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD);
    };
  }

  public static final WoodBlockObject maple = BLOCKS.registerWood("maple", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject silverbell = BLOCKS.registerWood("silverbell", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject amaranth = BLOCKS.registerWood("amaranth", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject tiger = BLOCKS.registerWood("tiger", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject willow = BLOCKS.registerWood("willow", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject eucalyptus = BLOCKS.registerWood("eucalyptus", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject hopseed = BLOCKS.registerWood("hopseed", createWood(), true, TAB_OVERWORLD);
  public static final WoodBlockObject sakura = BLOCKS.registerWood("sakura", createWood(), true, TAB_OVERWORLD);
}
