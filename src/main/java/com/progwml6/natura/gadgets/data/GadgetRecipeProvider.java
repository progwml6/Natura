package com.progwml6.natura.gadgets.data;

import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.common.data.BaseRecipeProvider;
import com.progwml6.natura.gadgets.NaturaGadgets;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class GadgetRecipeProvider extends BaseRecipeProvider {

  public GadgetRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName() {
    return "Natura Gadget Recipes";
  }

  @Override
  protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
    // stone
    String folder = "gadgets/stone/";
    ShapedRecipeBuilder.shapedRecipe(Blocks.JACK_O_LANTERN)
      .key('#', Blocks.CARVED_PUMPKIN)
      .key('X', NaturaGadgets.stoneTorch.get())
      .patternLine("#")
      .patternLine("X")
      .addCriterion("has_item", hasItem(Blocks.CARVED_PUMPKIN))
      .build(consumer, location(folder + "jack_o_lantern"));
    ShapedRecipeBuilder.shapedRecipe(NaturaGadgets.stoneLadder.get(), 3)
      .key('#', NaturaTags.Items.RODS_STONE)
      .patternLine("# #")
      .patternLine("###")
      .patternLine("# #")
      .addCriterion("has_item", hasItem(NaturaTags.Items.RODS_STONE))
      .build(consumer, prefix(NaturaGadgets.stoneLadder, folder));
    ShapedRecipeBuilder.shapedRecipe(NaturaGadgets.stoneStick.get(), 4)
      .key('#', Ingredient.fromItemListStream(Stream.of(
        new Ingredient.TagList(Tags.Items.STONE),
        new Ingredient.TagList(Tags.Items.COBBLESTONE))
      ))
      .patternLine("#")
      .patternLine("#")
      .addCriterion("has_item", hasItem(Tags.Items.STONE))
      .build(consumer, prefix(NaturaGadgets.stoneStick, folder));
    ShapedRecipeBuilder.shapedRecipe(NaturaGadgets.stoneTorch.get(), 4)
      .key('#', Ingredient.fromItemListStream(Stream.of(
        new Ingredient.SingleItemList(new ItemStack(Items.COAL)),
        new Ingredient.SingleItemList(new ItemStack(Items.CHARCOAL))
      )))
      .key('X', NaturaTags.Items.RODS_STONE)
      .patternLine("#")
      .patternLine("X")
      .addCriterion("has_item", hasItem(NaturaTags.Items.RODS_STONE))
      .build(consumer, prefix(NaturaGadgets.stoneTorch, folder));
    
    folder = "gadgets/";
    ShapedRecipeBuilder.shapedRecipe(NaturaGadgets.punji.get(), 3)
      .key('#', Items.SUGAR_CANE)
      .patternLine("# #")
      .patternLine(" # ")
      .patternLine("# #")
      .addCriterion("has_item", hasItem(Items.SUGAR_CANE))
      .build(consumer, prefix(NaturaGadgets.punji, folder));
  }
}
