package com.progwml6.natura.shared.data;

import com.progwml6.natura.common.data.BaseRecipeProvider;
import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CommonRecipeProvider extends BaseRecipeProvider {

  public CommonRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName() {
    return "Natura Common Recipes";
  }

  @Override
  protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
    String folder = "common/seed_bags/wheat";

    ShapedRecipeBuilder.shapedRecipe(NaturaCommons.wheatSeedBag)
      .key('s', Items.WHEAT_SEEDS)
      .patternLine("sss")
      .patternLine("sss")
      .patternLine("sss")
      .addCriterion("has_item", hasItem(Items.WHEAT_SEEDS))
      .build(consumer, prefix(NaturaCommons.wheatSeedBag, folder));

    ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT_SEEDS, 9)
      .addIngredient(NaturaCommons.wheatSeedBag)
      .addCriterion("has_item", hasItem(NaturaCommons.wheatSeedBag))
      .build(consumer, prefix(Items.WHEAT_SEEDS, folder));

    // Carrots
    folder = "common/seed_bags/carrots";

    ShapedRecipeBuilder.shapedRecipe(NaturaCommons.carrotsSeedBag)
      .key('s', Tags.Items.CROPS_CARROT)
      .patternLine("sss")
      .patternLine("sss")
      .patternLine("sss")
      .addCriterion("has_item", hasItem(Tags.Items.CROPS_CARROT))
      .build(consumer, prefix(NaturaCommons.carrotsSeedBag, folder));

    ShapelessRecipeBuilder.shapelessRecipe(Items.CARROT, 9)
      .addIngredient(NaturaCommons.carrotsSeedBag)
      .addCriterion("has_item", hasItem(NaturaCommons.carrotsSeedBag))
      .build(consumer, prefix(Items.CARROT, folder));

    // Potatoes
    folder = "common/seed_bags/potatoes";

    ShapedRecipeBuilder.shapedRecipe(NaturaCommons.potatoesSeedBag)
      .key('s', Tags.Items.CROPS_POTATO)
      .patternLine("sss")
      .patternLine("sss")
      .patternLine("sss")
      .addCriterion("has_item", hasItem(Tags.Items.CROPS_POTATO))
      .build(consumer, prefix(NaturaCommons.potatoesSeedBag, folder));

    ShapelessRecipeBuilder.shapelessRecipe(Items.POTATO, 9)
      .addIngredient(NaturaCommons.potatoesSeedBag)
      .addCriterion("has_item", hasItem(NaturaCommons.potatoesSeedBag))
      .build(consumer, prefix(Items.POTATO, folder));

    // Nether Wart
    folder = "common/seed_bags/nether_wart";

    ShapedRecipeBuilder.shapedRecipe(NaturaCommons.netherWartSeedBag)
      .key('s', Tags.Items.CROPS_NETHER_WART)
      .patternLine("sss")
      .patternLine("sss")
      .patternLine("sss")
      .addCriterion("has_item", hasItem(Tags.Items.CROPS_NETHER_WART))
      .build(consumer, prefix(NaturaCommons.netherWartSeedBag, folder));

    ShapelessRecipeBuilder.shapelessRecipe(Items.NETHER_WART, 9)
      .addIngredient(NaturaCommons.netherWartSeedBag)
      .addCriterion("has_item", hasItem(NaturaCommons.netherWartSeedBag))
      .build(consumer, prefix(Items.NETHER_WART, folder));

    // Bone Meal
    folder = "common/seed_bags/bone_meal";

    ShapedRecipeBuilder.shapedRecipe(NaturaCommons.boneMealBag)
      .key('b', Blocks.BONE_BLOCK)
      .key('s', Items.STRING)
      .key('p', Items.PAPER)
      .patternLine("sps")
      .patternLine("sbs")
      .patternLine("sss")
      .addCriterion("has_item", hasItem(Items.STRING))
      .build(consumer, prefix(NaturaCommons.boneMealBag, folder));

    ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 9)
      .addIngredient(NaturaCommons.boneMealBag)
      .addCriterion("has_item", hasItem(NaturaCommons.boneMealBag))
      .build(consumer, prefix(Items.BONE_MEAL, folder));

    folder = "common/food/";

    ShapelessRecipeBuilder.shapelessRecipe(NaturaCommons.cactusJuice)
      .addIngredient(Blocks.CACTUS)
      .addCriterion("has_item", hasItem(Blocks.CACTUS))
      .build(consumer, prefix(NaturaCommons.cactusJuice, folder));

    folder = "common/";

    ShapedRecipeBuilder.shapedRecipe(Items.WATER_BUCKET)
      .key('c', NaturaCommons.cactusJuice)
      .key('b', Items.BUCKET)
      .patternLine("ccc")
      .patternLine("cbc")
      .patternLine("ccc")
      .addCriterion("has_item", hasItem(NaturaCommons.cactusJuice))
      .build(consumer, prefix(Items.WATER_BUCKET, folder));
  }
}
