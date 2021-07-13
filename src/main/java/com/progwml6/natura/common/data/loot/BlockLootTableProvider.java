package com.progwml6.natura.common.data.loot;

import com.progwml6.natura.Natura;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.block.crops.CottonCropBlock;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.FenceBuildingBlockObject;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;
import slimeknights.mantle.registration.object.WoodBlockObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockLootTableProvider extends BlockLootTables {

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
    this.registerDropSelfLootTable(NaturaGadgets.stoneLadder.get());

    this.registerDropSelfLootTable(NaturaGadgets.stoneTorch.get());

    this.registerDropping(NaturaGadgets.wallStoneTorch.get(), NaturaGadgets.stoneTorch.get());

    this.registerDropSelfLootTable(NaturaGadgets.punji.get());
  }

  private void addWorld() {
    for (TreeType type : TreeType.values()) {
      this.registerLootTable(NaturaWorld.leaves.get(type),
        (leaves) -> droppingWithChancesAndSticks(leaves, NaturaWorld.sapling.get(type), DEFAULT_SAPLING_DROP_RATES));
      this.registerDropSelfLootTable(NaturaWorld.sapling.get(type));
    }

    this.registerWoodLootTables(NaturaWorld.maple);
    this.registerWoodLootTables(NaturaWorld.silverbell);
    this.registerWoodLootTables(NaturaWorld.amaranth);
    this.registerWoodLootTables(NaturaWorld.tiger);
    this.registerWoodLootTables(NaturaWorld.willow);
    this.registerWoodLootTables(NaturaWorld.eucalyptus);
    this.registerWoodLootTables(NaturaWorld.hopseed);
    this.registerWoodLootTables(NaturaWorld.sakura);


    for (RedwoodType type : RedwoodType.values()) {
      this.registerDropSelfLootTable(NaturaWorld.redwood.get(type));
    }

    this.registerLootTable(NaturaWorld.redwood_leaves.get(),
      (leaves) -> droppingWithChancesAndSticks(leaves, NaturaWorld.redwood_sapling.get(), DEFAULT_SAPLING_DROP_RATES));
    this.registerDropSelfLootTable(NaturaWorld.redwood_sapling.get());

    this.registerLootTable(NaturaWorld.cotton_crop.get(), droppingAndBonusWhen(NaturaWorld.cotton_crop.get(), NaturaCommons.cotton.get(),
      NaturaWorld.cotton_crop.asItem(), BlockStateProperty.builder(NaturaWorld.cotton_crop.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CottonCropBlock.AGE, 4))));

    this.registerLootTable(NaturaWorld.barley_crop.get(), droppingAndBonusWhen(NaturaWorld.barley_crop.get(), NaturaCommons.barley.get(),
      NaturaWorld.barley_crop.asItem(), BlockStateProperty.builder(NaturaWorld.barley_crop.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CottonCropBlock.AGE, 3))));
  }

  /**
   * Registers all loot tables for a building block object
   *
   * @param object Object instance
   */
  private void registerBuildingLootTables(BuildingBlockObject object) {
    this.registerDropSelfLootTable(object.get());
    this.registerLootTable(object.getSlab(), BlockLootTables::droppingSlab);
    this.registerDropSelfLootTable(object.getStairs());
  }

  /**
   * Registers all loot tables for a wall building block object
   *
   * @param object Object instance
   */
  private void registerWallBuildingLootTables(WallBuildingBlockObject object) {
    registerBuildingLootTables(object);
    this.registerDropSelfLootTable(object.getWall());
  }

  /**
   * Registers all loot tables for a fence building block object
   *
   * @param object Object instance
   */
  private void registerFenceBuildingLootTables(FenceBuildingBlockObject object) {
    registerBuildingLootTables(object);
    this.registerDropSelfLootTable(object.getFence());
  }

  /**
   * Adds all loot tables relevant to the given wood object
   */
  private void registerWoodLootTables(WoodBlockObject object) {
    registerFenceBuildingLootTables(object);
    // basic
    this.registerDropSelfLootTable(object.getLog());
    this.registerDropSelfLootTable(object.getStrippedLog());
    this.registerDropSelfLootTable(object.getWood());
    this.registerDropSelfLootTable(object.getStrippedWood());
    // door
    this.registerDropSelfLootTable(object.getFenceGate());
    this.registerLootTable(object.getDoor(), BlockLootTables::registerDoor);
    this.registerDropSelfLootTable(object.getTrapdoor());
    // redstone
    this.registerDropSelfLootTable(object.getPressurePlate());
    this.registerDropSelfLootTable(object.getButton());
    // sign
    this.registerDropSelfLootTable(object.getSign());
  }
}
