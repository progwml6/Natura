package com.progwml6.natura.common.data.loot;

import com.progwml6.natura.Natura;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.tconstruct.shared.TinkerCommons;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

public class NaturaBlockLootTables extends BlockLootTables {

  @Nonnull
  @Override
  protected Iterable<Block> getKnownBlocks() {
    return ForgeRegistries.BLOCKS.getValues().stream()
      .filter((block) -> Natura.modID.equals(block.getRegistryName().getNamespace()))
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
      this.registerDropSelfLootTable(NaturaWorld.logs.get(type));
      this.registerLootTable(NaturaWorld.leaves.get(type),
        (leaves) -> droppingWithChancesAndSticks(leaves, NaturaWorld.sapling.get(type), DEFAULT_SAPLING_DROP_RATES));
      this.registerDropSelfLootTable(NaturaWorld.sapling.get(type));
    }

    for (RedwoodType type : RedwoodType.values()) {
      this.registerDropSelfLootTable(NaturaWorld.redwood.get(type));
    }

    this.registerLootTable(NaturaWorld.redwood_leaves.get(),
      (leaves) -> droppingWithChancesAndSticks(leaves, NaturaWorld.redwood_sapling.get(), DEFAULT_SAPLING_DROP_RATES));
    this.registerDropSelfLootTable(NaturaWorld.redwood_sapling.get());
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
}
