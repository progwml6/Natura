package com.progwml6.natura.library.item;

import net.minecraft.world.level.block.Block;
import slimeknights.mantle.item.BlockTooltipItem;

public class BlockNamedTooltipItem extends BlockTooltipItem {

  public BlockNamedTooltipItem(Block blockIn, Properties builder) {
    super(blockIn, builder);
  }

  /**
   * Returns the unlocalized name of this item.
   */
  public String getDescriptionId() {
    return this.getOrCreateDescriptionId();
  }
}
