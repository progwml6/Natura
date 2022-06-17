package com.progwml6.natura.gadgets.data;

import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.common.data.BaseRecipeProvider;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.library.data.recipe.ICommonRecipeHelper;
import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class GadgetRecipeProvider extends BaseRecipeProvider implements ICommonRecipeHelper {

  public GadgetRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName() {
    return "Natura Gadget Recipes";
  }

  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
    // stone
    String folder = "gadgets/stone/";

    ShapedRecipeBuilder.shaped(Blocks.JACK_O_LANTERN)
      .define('#', Blocks.CARVED_PUMPKIN)
      .define('X', NaturaGadgets.stoneTorch.get())
      .pattern("#")
      .pattern("X")
      .unlockedBy("has_item", has(Blocks.CARVED_PUMPKIN))
      .save(consumer, modResource(folder + "jack_o_lantern"));

    ShapedRecipeBuilder.shaped(NaturaGadgets.stoneLadder.get(), 3)
      .define('#', NaturaTags.Items.RODS_STONE)
      .pattern("# #")
      .pattern("###")
      .pattern("# #")
      .unlockedBy("has_item", has(NaturaTags.Items.RODS_STONE))
      .save(consumer, prefix(NaturaGadgets.stoneLadder, folder));

    ShapedRecipeBuilder.shaped(NaturaGadgets.stoneStick.get(), 4)
      .define('#', Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(Tags.Items.STONE),
        new Ingredient.TagValue(Tags.Items.COBBLESTONE))
      ))
      .pattern("#")
      .pattern("#")
      .unlockedBy("has_item", has(Tags.Items.STONE))
      .save(consumer, prefix(NaturaGadgets.stoneStick, folder));

    ShapedRecipeBuilder.shaped(NaturaGadgets.stoneTorch.get(), 4)
      .define('#', Ingredient.fromValues(Stream.of(
        new Ingredient.ItemValue(new ItemStack(Items.COAL)),
        new Ingredient.ItemValue(new ItemStack(Items.CHARCOAL))
      )))
      .define('X', NaturaTags.Items.RODS_STONE)
      .pattern("#")
      .pattern("X")
      .unlockedBy("has_item", has(NaturaTags.Items.RODS_STONE))
      .save(consumer, prefix(NaturaGadgets.stoneTorch, folder));

    folder = "gadgets/";

    ShapedRecipeBuilder.shaped(NaturaGadgets.punji.get(), 3)
      .define('#', Items.SUGAR_CANE)
      .pattern("# #")
      .pattern(" # ")
      .pattern("# #")
      .unlockedBy("has_item", has(Items.SUGAR_CANE))
      .save(consumer, prefix(NaturaGadgets.punji, folder));

    // dried clay
    folder = "gadgets/building/";

    ShapedRecipeBuilder.shaped(NaturaCommons.driedClayBricks)
      .define('b', NaturaCommons.driedBrick)
      .pattern("bb")
      .pattern("bb")
      .unlockedBy("has_item", has(NaturaCommons.driedClay))
      .save(consumer, prefix(NaturaCommons.driedClayBricks, folder));
    slabStairsCrafting(consumer, NaturaCommons.driedClay, folder, true);
    slabStairsCrafting(consumer, NaturaCommons.driedClayBricks, folder, true);
  }
}
