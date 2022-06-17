package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.registration.RedwoodBlockObject;
import com.progwml6.natura.library.item.BlockNamedTooltipItem;
import com.progwml6.natura.library.utils.Util;
import com.progwml6.natura.world.block.OverworldLeavesBlock;
import com.progwml6.natura.world.block.RedwoodLeavesBlock;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.data.WorldRecipeProvider;
import com.progwml6.natura.world.worldgen.trees.growers.OverworldTreeGrower;
import com.progwml6.natura.world.worldgen.trees.growers.RedwoodTreeGrower;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
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

  public static final RedwoodBlockObject redwood = BLOCKS.registerRedwood("redwood", createWood(), true, TAB_OVERWORLD);

  private static final Block.Properties LEAVES = builder(Material.LEAVES, SoundType.GRASS).strength(0.2F).randomTicks().noOcclusion()
    .isValidSpawn((state, reader, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
    .isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false);
  public static final EnumObject<TreeType, OverworldLeavesBlock> leaves = BLOCKS.registerEnum(TreeType.values(), "leaves", (type) -> new OverworldLeavesBlock(type, LEAVES), DEFAULT_BLOCK_ITEM);
  public static final ItemObject<RedwoodLeavesBlock> redwood_leaves = BLOCKS.register("redwood_leaves", () -> new RedwoodLeavesBlock(LEAVES), DEFAULT_BLOCK_ITEM);

  private static final Block.Properties SAPLING = builder(Material.PLANT, SoundType.GRASS).noCollission().randomTicks().instabreak();
  public static final EnumObject<TreeType, Block> sapling = BLOCKS.registerEnum(TreeType.values(), "sapling", (type) -> new SaplingBlock(new OverworldTreeGrower(type), SAPLING), DEFAULT_BLOCK_ITEM);
  public static final ItemObject<Block> redwood_sapling = BLOCKS.register("redwood_sapling", () -> new SaplingBlock(new RedwoodTreeGrower(), SAPLING), DEFAULT_BLOCK_ITEM);

  public static final IntegerProperty EXTENDED_TREE_DISTANCE = IntegerProperty.create("distance_extended", 1, 10);
  public static final IntegerProperty REDWOOD_TREE_DISTANCE = IntegerProperty.create("distance_redwood", 1, 25);

  @SubscribeEvent
  void gatherData(final GatherDataEvent event) {
    if (event.includeServer()) {
      DataGenerator datagenerator = event.getGenerator();
      datagenerator.addProvider(new WorldRecipeProvider(datagenerator));
    }
  }
}
