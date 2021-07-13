package com.progwml6.natura.library.item;

import net.minecraft.block.Block;
import slimeknights.mantle.item.BlockTooltipItem;

public class BlockNamedTooltipItem extends BlockTooltipItem {

  public BlockNamedTooltipItem(Block blockIn, Properties builder) {
    super(blockIn, builder);
  }

  /**
   * Returns the unlocalized name of this item.
   */
  @Override
  public String getTranslationKey() {
    return this.getDefaultTranslationKey();
  }
}
