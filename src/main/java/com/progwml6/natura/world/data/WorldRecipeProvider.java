package com.progwml6.natura.world.data;

import com.progwml6.natura.common.data.BaseRecipeProvider;
import com.progwml6.natura.library.data.recipe.ICommonRecipeHelper;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class WorldRecipeProvider extends BaseRecipeProvider implements ICommonRecipeHelper {

  public WorldRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName() {
    return "Natura World Recipes";
  }

  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

    // wood
    String woodFolder = "world/wood/";
    woodCrafting(consumer, NaturaWorld.maple, woodFolder + "maple/");
    woodCrafting(consumer, NaturaWorld.silverbell, woodFolder + "silverbell/");
    woodCrafting(consumer, NaturaWorld.amaranth, woodFolder + "amaranth/");
    woodCrafting(consumer, NaturaWorld.tiger, woodFolder + "tiger/");
    woodCrafting(consumer, NaturaWorld.willow, woodFolder + "willow/");
    woodCrafting(consumer, NaturaWorld.eucalyptus, woodFolder + "eucalyptus/");
    woodCrafting(consumer, NaturaWorld.hopseed, woodFolder + "hopseed/");
    woodCrafting(consumer, NaturaWorld.sakura, woodFolder + "sakura/");
    woodCrafting(consumer, NaturaWorld.redwood, woodFolder + "redwood/");
  }
}
