package com.progwml6.natura.shared.data;

import com.progwml6.natura.common.data.BaseRecipeProvider;
import com.progwml6.natura.library.data.recipe.ICommonRecipeHelper;
import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CommonRecipeProvider extends BaseRecipeProvider implements ICommonRecipeHelper {

  public CommonRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName() {
    return "Natura Common Recipes";
  }

  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
    String folder = "common/seed_bags/wheat";

    ShapedRecipeBuilder.shaped(NaturaCommons.wheatSeedBag)
      .define('s', Items.WHEAT_SEEDS)
      .pattern("sss")
      .pattern("sss")
      .pattern("sss")
      .unlockedBy("has_item", has(Items.WHEAT_SEEDS))
      .save(consumer, prefix(NaturaCommons.wheatSeedBag, folder));

    ShapelessRecipeBuilder.shapeless(Items.WHEAT_SEEDS, 9)
      .requires(NaturaCommons.wheatSeedBag)
      .unlockedBy("has_item", has(NaturaCommons.wheatSeedBag))
      .save(consumer, prefix(Items.WHEAT_SEEDS, folder));

    // Carrots
    folder = "common/seed_bags/carrots";

    ShapedRecipeBuilder.shaped(NaturaCommons.carrotsSeedBag)
      .define('s', Tags.Items.CROPS_CARROT)
      .pattern("sss")
      .pattern("sss")
      .pattern("sss")
      .unlockedBy("has_item", has(Tags.Items.CROPS_CARROT))
      .save(consumer, prefix(NaturaCommons.carrotsSeedBag, folder));

    ShapelessRecipeBuilder.shapeless(Items.CARROT, 9)
      .requires(NaturaCommons.carrotsSeedBag)
      .unlockedBy("has_item", has(NaturaCommons.carrotsSeedBag))
      .save(consumer, prefix(Items.CARROT, folder));

    // Potatoes
    folder = "common/seed_bags/potatoes";

    ShapedRecipeBuilder.shaped(NaturaCommons.potatoesSeedBag)
      .define('s', Tags.Items.CROPS_POTATO)
      .pattern("sss")
      .pattern("sss")
      .pattern("sss")
      .unlockedBy("has_item", has(Tags.Items.CROPS_POTATO))
      .save(consumer, prefix(NaturaCommons.potatoesSeedBag, folder));

    ShapelessRecipeBuilder.shapeless(Items.POTATO, 9)
      .requires(NaturaCommons.potatoesSeedBag)
      .unlockedBy("has_item", has(NaturaCommons.potatoesSeedBag))
      .save(consumer, prefix(Items.POTATO, folder));

    // Nether Wart
    folder = "common/seed_bags/nether_wart";

    ShapedRecipeBuilder.shaped(NaturaCommons.netherWartSeedBag)
      .define('s', Tags.Items.CROPS_NETHER_WART)
      .pattern("sss")
      .pattern("sss")
      .pattern("sss")
      .unlockedBy("has_item", has(Tags.Items.CROPS_NETHER_WART))
      .save(consumer, prefix(NaturaCommons.netherWartSeedBag, folder));

    ShapelessRecipeBuilder.shapeless(Items.NETHER_WART, 9)
      .requires(NaturaCommons.netherWartSeedBag)
      .unlockedBy("has_item", has(NaturaCommons.netherWartSeedBag))
      .save(consumer, prefix(Items.NETHER_WART, folder));

    // Bone Meal
    folder = "common/seed_bags/bone_meal";

    ShapedRecipeBuilder.shaped(NaturaCommons.boneMealBag)
      .define('b', Blocks.BONE_BLOCK)
      .define('s', Items.STRING)
      .define('p', Items.PAPER)
      .pattern("sps")
      .pattern("sbs")
      .pattern("sss")
      .unlockedBy("has_item", has(Items.STRING))
      .save(consumer, prefix(NaturaCommons.boneMealBag, folder));

    ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL, 9)
      .requires(NaturaCommons.boneMealBag)
      .unlockedBy("has_item", has(NaturaCommons.boneMealBag))
      .save(consumer, prefix(Items.BONE_MEAL, folder));

    folder = "common/food/";

    ShapelessRecipeBuilder.shapeless(NaturaCommons.cactusJuice)
      .requires(Blocks.CACTUS)
      .unlockedBy("has_item", has(Blocks.CACTUS))
      .save(consumer, prefix(NaturaCommons.cactusJuice, folder));

    folder = "common/";

    ShapedRecipeBuilder.shaped(Items.WATER_BUCKET)
      .define('c', NaturaCommons.cactusJuice)
      .define('b', Items.BUCKET)
      .pattern("ccc")
      .pattern("cbc")
      .pattern("ccc")
      .unlockedBy("has_item", has(NaturaCommons.cactusJuice))
      .save(consumer, prefix(Items.WATER_BUCKET, folder));
  }
}
