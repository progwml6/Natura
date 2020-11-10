package com.progwml6.natura.overworld;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.overworld.block.TreeType;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
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

@SuppressWarnings("unused")
public class NaturaOverworld extends NaturaModule {

  public static final ItemGroup TAB_OVERWORLD = new SupplierItemGroup(Natura.modID, "world", () -> new ItemStack(NaturaOverworld.logs.get(TreeType.MAPLE)));
  static final Logger log = Util.getLogger("tinker_commons");

  /*
   * Block base properties
   */
  private static final Item.Properties OVERWORLD_PROPS = new Item.Properties().group(TAB_OVERWORLD);
  private static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().group(TAB_OVERWORLD).maxStackSize(1);
  private static final Function<Block, ? extends BlockItem> DEFAULT_BLOCK_ITEM = (b) -> new BlockItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, OVERWORLD_PROPS);

  private static final Block.Properties LOG = builder(Material.WOOD, ToolType.AXE, SoundType.WOOD).hardnessAndResistance(1.5F, 5.0F);
  public static final EnumObject<TreeType, RotatedPillarBlock> logs = BLOCKS.registerEnum(TreeType.values(), "log", (type) -> new RotatedPillarBlock(LOG), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties LEAVES = builder(Material.LEAVES, NO_TOOL, SoundType.PLANT).hardnessAndResistance(0.2F).tickRandomly().notSolid()
    .setAllowsSpawn((state, reader, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
    .setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false);
  public static final EnumObject<TreeType, LeavesBlock> leaves = BLOCKS.registerEnum(TreeType.values(), "leaves", (type) -> new LeavesBlock(LEAVES), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties SAPLING = builder(Material.PLANTS, NO_TOOL, SoundType.PLANT).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance();
  public static final EnumObject<TreeType, Block> sapling = BLOCKS.registerEnum(TreeType.values(), "sapling", (type) -> new Block(SAPLING), DEFAULT_BLOCK_ITEM);
}
