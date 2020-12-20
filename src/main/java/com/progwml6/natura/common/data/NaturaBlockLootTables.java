package com.progwml6.natura.common.data;

import com.progwml6.natura.Natura;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

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
    this.addWorld();
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
}
