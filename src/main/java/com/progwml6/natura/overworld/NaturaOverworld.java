package com.progwml6.natura.overworld;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.overworld.block.LogType;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.util.SupplierItemGroup;

import java.util.function.Function;

public class NaturaOverworld extends NaturaModule {

  public static final ItemGroup TAB_OVERWORLD = new SupplierItemGroup(Natura.modID, "world", () -> new ItemStack(NaturaOverworld.logs.get(LogType.MAPLE)));
  static final Logger log = Util.getLogger("tinker_commons");

  /*
   * Block base properties
   */
  private static final Item.Properties OVERWORLD_PROPS = new Item.Properties().group(TAB_OVERWORLD);
  private static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().group(TAB_OVERWORLD).maxStackSize(1);
  private static final Function<Block, ? extends BlockItem> DEFAULT_BLOCK_ITEM = (b) -> new BlockItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, OVERWORLD_PROPS);

  private static final Block.Properties LOG = builder(Material.WOOD, ToolType.AXE, SoundType.WOOD).hardnessAndResistance(1.5F, 5.0F);

  public static final EnumObject<LogType, RotatedPillarBlock> logs = BLOCKS.registerEnum(LogType.values(), "log", (type) -> new RotatedPillarBlock(LOG), DEFAULT_BLOCK_ITEM);
}
