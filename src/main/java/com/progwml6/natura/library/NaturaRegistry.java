package com.progwml6.natura.library;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import slimeknights.mantle.client.CreativeTab;

public final class NaturaRegistry {

  private NaturaRegistry() {
  }

  /*---------------------------------------------------------------------------
  | ITEM GROUPS                                                               |
  ---------------------------------------------------------------------------*/

  public static CreativeTab generalItemGroup = new CreativeTab("NaturaGeneral", new ItemStack(Items.SLIME_BALL));
  public static CreativeTab worldItemGroup = new CreativeTab("NaturaWorld", new ItemStack(Items.SLIME_BALL));
  public static CreativeTab decorativeItemGroup = new CreativeTab("NaturaDecorative", new ItemStack(Items.SLIME_BALL));

  /*---------------------------------------------------------------------------
  | MATERIALS                                                                 |
  ---------------------------------------------------------------------------*/
  public static Material CLOUD = (new Material.Builder(MaterialColor.SNOW)).doesNotBlockMovement().notSolid().notOpaque().build();

}
