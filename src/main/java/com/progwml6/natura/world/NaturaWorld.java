package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.item.BlockNamedTooltipItem;
import com.progwml6.natura.library.utils.Util;
import com.progwml6.natura.world.block.OverworldLeavesBlock;
import com.progwml6.natura.world.block.PlankType;
import com.progwml6.natura.world.block.RedwoodLeavesBlock;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.block.crops.BarleyCropBlock;
import com.progwml6.natura.world.block.crops.CottonCropBlock;
import com.progwml6.natura.world.worldgen.trees.OverworldTree;
import com.progwml6.natura.world.worldgen.trees.RedwoodTree;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.registration.object.WoodBlockObject;
import slimeknights.mantle.util.SupplierItemGroup;

import java.util.function.Function;

@SuppressWarnings("unused")
public class NaturaWorld extends NaturaModule {

  public static final ItemGroup TAB_OVERWORLD = new SupplierItemGroup(Natura.MOD_ID, "world", () -> new ItemStack(NaturaWorld.maple.getLog()));
  static final Logger log = Util.getLogger("natura_world");

  /*
   * Block base properties
   */
  private static final Item.Properties OVERWORLD_PROPS = new Item.Properties().group(TAB_OVERWORLD);
  private static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().group(TAB_OVERWORLD).maxStackSize(1);
  private static final Function<Block, ? extends BlockItem> DEFAULT_BLOCK_ITEM = (b) -> new BlockItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, OVERWORLD_PROPS);
  private static final Function<Block, ? extends BlockItem> NAMED_TOOLTIP_BLOCK_ITEM = (b) -> new BlockNamedTooltipItem(b, OVERWORLD_PROPS);

  public static final WoodBlockObject maple = BLOCKS.registerWood("maple", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject silverbell = BLOCKS.registerWood("silverbell", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject amaranth = BLOCKS.registerWood("amaranth", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject tiger = BLOCKS.registerWood("tiger", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject willow = BLOCKS.registerWood("willow", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject eucalyptus = BLOCKS.registerWood("eucalyptus", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject hopseed = BLOCKS.registerWood("hopseed", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);
  public static final WoodBlockObject sakura = BLOCKS.registerWood("sakura", Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, ToolType.AXE, Material.WOOD, MaterialColor.WOOD, SoundType.WOOD, TAB_OVERWORLD);

  private static final Block.Properties REDWOOD_LOG = builder(Material.WOOD, ToolType.AXE, SoundType.WOOD).hardnessAndResistance(2.0f).harvestLevel(-1);
  public static final EnumObject<RedwoodType, Block> redwood = BLOCKS.registerEnum("redwood", RedwoodType.values(), (type) -> new Block(REDWOOD_LOG), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties LEAVES = builder(Material.LEAVES, NO_TOOL, SoundType.PLANT).hardnessAndResistance(0.2F).tickRandomly().notSolid()
    .setAllowsSpawn((state, reader, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
    .setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false);
  public static final EnumObject<TreeType, OverworldLeavesBlock> leaves = BLOCKS.registerEnum(TreeType.values(), "leaves", (type) -> new OverworldLeavesBlock(type, LEAVES), DEFAULT_BLOCK_ITEM);

  public static final ItemObject<RedwoodLeavesBlock> redwood_leaves = BLOCKS.register("redwood_leaves", () -> new RedwoodLeavesBlock(LEAVES), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties SAPLING = builder(Material.PLANTS, NO_TOOL, SoundType.PLANT).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance();
  public static final EnumObject<TreeType, Block> sapling = BLOCKS.registerEnum(TreeType.values(), "sapling", (type) -> new SaplingBlock(new OverworldTree(type), SAPLING), DEFAULT_BLOCK_ITEM);

  public static final ItemObject<Block> redwood_sapling = BLOCKS.register("redwood_sapling", () -> new SaplingBlock(new RedwoodTree(), SAPLING), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties CROP = builder(Material.PLANTS, NO_TOOL, SoundType.CROP).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance();
  public static final ItemObject<Block> cotton_crop = BLOCKS.register("cotton_crop", () -> new CottonCropBlock(CROP), NAMED_TOOLTIP_BLOCK_ITEM);
  public static final ItemObject<Block> barley_crop = BLOCKS.register("barley_crop", () -> new BarleyCropBlock(CROP), NAMED_TOOLTIP_BLOCK_ITEM);

  public static final IntegerProperty EXTENDED_TREE_DISTANCE = IntegerProperty.create("distance_extended", 1, 10);
  public static final IntegerProperty REDWOOD_TREE_DISTANCE = IntegerProperty.create("distance_redwood", 1, 25);
}
