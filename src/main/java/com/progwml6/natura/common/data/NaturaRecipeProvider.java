package com.progwml6.natura.common.data;

import com.progwml6.natura.common.Tags;
import com.progwml6.natura.common.conditions.PulseLoadedCondition;
import com.progwml6.natura.library.NaturaPulseIds;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static com.progwml6.natura.shared.NaturaCommons.barley_flour;
import static com.progwml6.natura.shared.NaturaCommons.bone_meal_bag;
import static com.progwml6.natura.shared.NaturaCommons.ghostwood_fletching;

public class NaturaRecipeProvider extends RecipeProvider implements IConditionBuilder {

  public NaturaRecipeProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }

  @Override
  protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
    this.addCommon(consumer);
  }

  private void addCommon(Consumer<IFinishedRecipe> consumer) {
    ConditionalRecipe.builder()
            .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.ARROW, 4)
                    .patternLine(" # ").patternLine("XYX").patternLine(" X ")
                    .key('#', Items.FLINT).key('X', ghostwood_fletching).key('Y', net.minecraftforge.common.Tags.Items.RODS_WOODEN)
                    .setGroup("")
                    .addCriterion("has_ghostwood_fletching", this.hasItem(ghostwood_fletching))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/arrows"), ConditionalAdvancement.builder()
                    .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/arrows")))
                            .withCriterion("has_ghostwood_fletching", this.hasItem(ghostwood_fletching))))
            .build(consumer, new ResourceLocation("natura", "common/arrows"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(barley_flour).addIngredient(Tags.Items.CROPS_BARLEY)
                    .setGroup("")
                    .addCriterion("has_barley", this.hasItem(Tags.Items.CROPS_BARLEY))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/barley_flour"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/barley_flour")))
                            .withCriterion("has_barley", this.hasItem(Tags.Items.CROPS_BARLEY))))
            .build(consumer, new ResourceLocation("natura", "common/barley_flour"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 9)
                    .addIngredient(bone_meal_bag)
                    .setGroup("")
                    .addCriterion("has_bone_meal_bag", this.hasItem(bone_meal_bag))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/bone_meal"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/bone_meal")))
                            .withCriterion("has_bone_meal_bag", this.hasItem(bone_meal_bag))))
            .build(consumer, new ResourceLocation("natura", "common/bone_meal"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(bone_meal_bag, 4)
                    .patternLine("#X#").patternLine("#Y#").patternLine("###")
                    .key('#', Items.STRING).key('X', Items.PAPER).key('Y', Blocks.BONE_BLOCK)
                    .setGroup("")
                    .addCriterion("has_string", this.hasItem(Items.STRING))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/bone_meal_bag"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/bone_meal_bag")))
                            .withCriterion("has_string", this.hasItem(Items.STRING))))
            .build(consumer, new ResourceLocation("natura", "common/bone_meal_bag"));
  }

  private ICondition pulseLoaded(String pulseId) {
    return new PulseLoadedCondition(pulseId);
  }

}
