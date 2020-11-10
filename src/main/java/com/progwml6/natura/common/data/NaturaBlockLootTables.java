package com.progwml6.natura.common.data;

import com.progwml6.natura.Natura;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.TreeType;
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
    this.addOverworld();
  }

  private void addOverworld() {
    for (TreeType type : TreeType.values()) {
      this.registerDropSelfLootTable(NaturaOverworld.logs.get(type));
      this.registerLootTable(NaturaOverworld.leaves.get(type), (leaves) -> droppingWithChancesAndSticks(leaves, NaturaOverworld.sapling.get(type), DEFAULT_SAPLING_DROP_RATES));
      this.registerDropSelfLootTable(NaturaOverworld.sapling.get(type));
    }
  }
}
