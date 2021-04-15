package com.progwml6.natura.gadgets;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.gadgets.block.PunjiBlock;
import com.progwml6.natura.gadgets.data.GadgetRecipeProvider;
import com.progwml6.natura.shared.NaturaFood;
import net.minecraft.block.Block;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import slimeknights.mantle.item.EdibleItem;
import slimeknights.mantle.registration.object.ItemObject;

@SuppressWarnings("unused")
public class NaturaGadgets extends NaturaModule {

  /*
   * Blocks
   */
  public static final ItemObject<LadderBlock> stoneLadder = BLOCKS.register("stone_ladder", () -> new LadderBlock(builder(Material.MISCELLANEOUS, NO_TOOL, SoundType.STONE).hardnessAndResistance(0.1F).notSolid()) {}, GENERAL_BLOCK_ITEM);
  public static final ItemObject<PunjiBlock> punji = BLOCKS.register("punji", () -> new PunjiBlock(builder(Material.PLANTS, NO_TOOL, SoundType.PLANT).hardnessAndResistance(3.0F).notSolid()), GENERAL_BLOCK_ITEM);

  // torch
  private static final Block.Properties STONE_TORCH = builder(Material.MISCELLANEOUS, NO_TOOL, SoundType.STONE).doesNotBlockMovement().hardnessAndResistance(0.0F).setLightLevel(s -> 14);
  public static final RegistryObject<WallTorchBlock> wallStoneTorch = BLOCKS.registerNoItem("wall_stone_torch", () -> new WallTorchBlock(STONE_TORCH, ParticleTypes.FLAME) {});
  public static final ItemObject<TorchBlock> stoneTorch = BLOCKS.register("stone_torch",
    () -> new TorchBlock(STONE_TORCH, ParticleTypes.FLAME) {},
    (block) -> new WallOrFloorItem(block, wallStoneTorch.get(), GENERAL_PROPS));

  /*1
   * Items
   */
  public static final ItemObject<Item> stoneStick = ITEMS.register("stone_stick", GENERAL_PROPS);

  // jerkies
  public static final ItemObject<EdibleItem> monsterJerky = ITEMS.register("monster_jerky", () -> new EdibleItem(NaturaFood.MONSTER_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> beefJerky = ITEMS.register("beef_jerky", () -> new EdibleItem(NaturaFood.BEEF_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> chickenJerky = ITEMS.register("chicken_jerky", () -> new EdibleItem(NaturaFood.CHICKEN_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> porkJerky = ITEMS.register("pork_jerky", () -> new EdibleItem(NaturaFood.PORK_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> muttonJerky = ITEMS.register("mutton_jerky", () -> new EdibleItem(NaturaFood.MUTTON_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> rabbitJerky = ITEMS.register("rabbit_jerky", () -> new EdibleItem(NaturaFood.RABBIT_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> fishJerky = ITEMS.register("fish_jerky", () -> new EdibleItem(NaturaFood.FISH_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> salmonJerky = ITEMS.register("salmon_jerky", () -> new EdibleItem(NaturaFood.SALMON_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> clownfishJerky = ITEMS.register("clownfish_jerky", () -> new EdibleItem(NaturaFood.CLOWNFISH_JERKY, TAB_GENERAL));
  public static final ItemObject<EdibleItem> pufferfishJerky = ITEMS.register("pufferfish_jerky", () -> new EdibleItem(NaturaFood.PUFFERFISH_JERKY, TAB_GENERAL));

  @SubscribeEvent
  void gatherData(final GatherDataEvent event) {
    if (event.includeServer()) {
      DataGenerator datagenerator = event.getGenerator();
      datagenerator.addProvider(new GadgetRecipeProvider(datagenerator));
    }
  }
}
