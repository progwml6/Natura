package com.progwml6.natura.common.data;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.data.recipe.IRecipeHelper;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Shared logic for each module's recipe provider
 */
public abstract class BaseRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {
  public BaseRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

  @Override
  public abstract String getName();

  @Override
  public String getModId() {
    return Natura.MOD_ID;
  }
}
