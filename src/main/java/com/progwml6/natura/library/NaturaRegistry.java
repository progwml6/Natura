package com.progwml6.natura.library;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public class NaturaRegistry {

  private NaturaRegistry() {
  }

  /*---------------------------------------------------------------------------
    | CREATIVE TABS                                                             |
    ---------------------------------------------------------------------------*/

  public static CreativeTab tabGeneral = new CreativeTab("natura_general", new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK)));

  public static CreativeTab tabWorld = new CreativeTab("natura_world", new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK)));

  public static CreativeTab tabDecorative = new CreativeTab("natura_decorative", new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK)));

  /*---------------------------------------------------------------------------
  | MATERIALS                                                                 |
  ---------------------------------------------------------------------------*/
  public static final Material CLOUD = (new Material.Builder(MaterialColor.SNOW)).doesNotBlockMovement().notOpaque().notSolid().build();
}
