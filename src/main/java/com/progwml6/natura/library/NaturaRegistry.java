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

  public static CreativeTab tabGeneral = new CreativeTab("natura_general", new ItemStack(Items.SLIME_BALL));
  public static CreativeTab tabWorld = new CreativeTab("natura_world", new ItemStack(Items.SLIME_BALL));
  public static CreativeTab tabDecorative = new CreativeTab("natura_decorative", new ItemStack(Items.SLIME_BALL));

  /*---------------------------------------------------------------------------
  | MATERIALS                                                                 |
  ---------------------------------------------------------------------------*/
  public static Material CLOUD = (new Material.Builder(MaterialColor.SNOW)).doesNotBlockMovement().notSolid().notOpaque().build();

}
