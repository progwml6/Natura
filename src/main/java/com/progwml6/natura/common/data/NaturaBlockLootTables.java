package com.progwml6.natura.common.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.progwml6.natura.shared.NaturaCommons.ash_cloud;
import static com.progwml6.natura.shared.NaturaCommons.dark_cloud;
import static com.progwml6.natura.shared.NaturaCommons.sulfur_cloud;
import static com.progwml6.natura.shared.NaturaCommons.white_cloud;

public class NaturaBlockLootTables extends BlockLootTables {

  private final Map<ResourceLocation, LootTable.Builder> loot_tables = Maps.newHashMap();

  private Set<Block> knownBlocks = new HashSet<>();

  private void addCommon() {
    this.func_218492_c(white_cloud);
    this.func_218492_c(dark_cloud);
    this.func_218492_c(ash_cloud);
    this.func_218492_c(sulfur_cloud);
  }

  @Override
  public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
    this.addCommon();

    Set<ResourceLocation> visited = Sets.newHashSet();

    for (Block block : this.knownBlocks) {
      ResourceLocation lootTable = block.getLootTable();
      if (lootTable != LootTables.EMPTY && visited.add(lootTable)) {
        LootTable.Builder builder = this.field_218581_i.remove(lootTable);
        if (builder == null) {
          throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", lootTable, block.getRegistryName()));
        }

        consumer.accept(lootTable, builder);
      }
    }

    if (!this.field_218581_i.isEmpty()) {
      throw new IllegalStateException("Created block loot tables for non-blocks: " + this.field_218581_i.keySet());
    }
  }

  @Override
  public void func_218564_a(Block blockIn, Block droppedBlockIn) {
    this.knownBlocks.add(blockIn);
    super.func_218564_a(blockIn, droppedBlockIn);
  }

  @Override
  public void func_218492_c(Block block) {
    this.knownBlocks.add(block);
    super.func_218492_c(block);
  }

  @Override
  public void registerLootTable(Block blockIn, Function<Block, LootTable.Builder> builderFunction) {
    this.knownBlocks.add(blockIn);
    super.registerLootTable(blockIn, builderFunction);
  }

  @Override
  public void registerLootTable(Block blockIn, LootTable.Builder builder) {
    this.knownBlocks.add(blockIn);
    super.registerLootTable(blockIn, builder);
  }

}
