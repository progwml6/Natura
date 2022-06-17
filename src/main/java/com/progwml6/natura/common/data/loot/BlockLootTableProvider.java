package com.progwml6.natura.common.data.loot;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.registration.RedwoodBlockObject;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.FenceBuildingBlockObject;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;
import slimeknights.mantle.registration.object.WoodBlockObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockLootTableProvider extends BlockLoot {

  @Nonnull
  @Override
  protected Iterable<Block> getKnownBlocks() {
    return ForgeRegistries.BLOCKS.getValues().stream()
      .filter((block) -> Natura.MOD_ID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace()))
      .collect(Collectors.toList());
  }

  @Override
  protected void addTables() {
    this.addDecorative();
    this.addGadgets();
    this.addWorld();
  }

  private void addDecorative() {
    this.registerBuildingLootTables(NaturaCommons.driedClay);
    this.registerBuildingLootTables(NaturaCommons.driedClayBricks);
  }

  private void addGadgets() {
    this.dropSelf(NaturaGadgets.stoneLadder.get());

    this.dropSelf(NaturaGadgets.stoneTorch.get());

    this.dropOther(NaturaGadgets.wallStoneTorch.get(), NaturaGadgets.stoneTorch.get());

    this.dropSelf(NaturaGadgets.punji.get());
  }

  private void addWorld() {
    for (TreeType type : TreeType.values()) {
      this.add(NaturaWorld.leaves.get(type), (leaves) -> createLeavesDrops(leaves, NaturaWorld.sapling.get(type), NORMAL_LEAVES_SAPLING_CHANCES));
      this.dropSelf(NaturaWorld.sapling.get(type));
    }

    this.registerWoodLootTables(NaturaWorld.maple);
    this.registerWoodLootTables(NaturaWorld.silverbell);
    this.registerWoodLootTables(NaturaWorld.amaranth);
    this.registerWoodLootTables(NaturaWorld.tiger);
    this.registerWoodLootTables(NaturaWorld.willow);
    this.registerWoodLootTables(NaturaWorld.eucalyptus);
    this.registerWoodLootTables(NaturaWorld.hopseed);
    this.registerWoodLootTables(NaturaWorld.sakura);

    this.registerWoodLootTables(NaturaWorld.redwood);

//    this.registerLootTable(NaturaWorld.redwood_leaves.get(), (leaves) -> createLeavesDrops(leaves, NaturaWorld.redwood_sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
//    this.dropSelf(NaturaWorld.redwood_sapling.get());
//
//    this.registerLootTable(NaturaWorld.cotton_crop.get(), droppingAndBonusWhen(NaturaWorld.cotton_crop.get(), NaturaCommons.cotton.get(),
//      NaturaWorld.cotton_crop.asItem(), BlockStateProperty.builder(NaturaWorld.cotton_crop.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CottonCropBlock.AGE, 4))));
//
//    this.registerLootTable(NaturaWorld.barley_crop.get(), droppingAndBonusWhen(NaturaWorld.barley_crop.get(), NaturaCommons.barley.get(),
//      NaturaWorld.barley_crop.asItem(), BlockStateProperty.builder(NaturaWorld.barley_crop.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CottonCropBlock.AGE, 3))));
  }

  /**
   * Registers all loot tables for a building block object
   *
   * @param object Object instance
   */
  private void registerBuildingLootTables(BuildingBlockObject object) {
    this.dropSelf(object.get());
    this.add(object.getSlab(), BlockLoot::createSlabItemTable);
    this.dropSelf(object.getStairs());
  }

  /**
   * Registers all loot tables for a wall building block object
   *
   * @param object Object instance
   */
  private void registerWallBuildingLootTables(WallBuildingBlockObject object) {
    registerBuildingLootTables(object);
    this.dropSelf(object.getWall());
  }

  /**
   * Registers all loot tables for a fence building block object
   *
   * @param object Object instance
   */
  private void registerFenceBuildingLootTables(FenceBuildingBlockObject object) {
    registerBuildingLootTables(object);
    this.dropSelf(object.getFence());
  }

  /**
   * Adds all loot tables relevant to the given wood object
   */
  private void registerWoodLootTables(WoodBlockObject object) {
    registerFenceBuildingLootTables(object);
    // basic
    this.dropSelf(object.getLog());
    this.dropSelf(object.getStrippedLog());
    this.dropSelf(object.getWood());
    this.dropSelf(object.getStrippedWood());
    // door
    this.dropSelf(object.getFenceGate());
    this.add(object.getDoor(), BlockLoot::createDoorTable);
    this.dropSelf(object.getTrapdoor());
    // redstone
    this.dropSelf(object.getPressurePlate());
    this.dropSelf(object.getButton());
    // sign
    this.dropSelf(object.getSign());
  }

  private void registerWoodLootTables(RedwoodBlockObject object) {
    registerFenceBuildingLootTables(object);
    // basic
    this.dropSelf(object.getBark());
    this.dropSelf(object.getHeart());
    this.dropSelf(object.getRoot());
    // door
    this.dropSelf(object.getFenceGate());
    this.add(object.getDoor(), BlockLoot::createDoorTable);
    this.add(object.getBarkDoor(), BlockLoot::createDoorTable);
    this.dropSelf(object.getTrapdoor());
    // redstone
    this.dropSelf(object.getPressurePlate());
    this.dropSelf(object.getButton());
    // sign
    this.dropSelf(object.getSign());
  }
}
