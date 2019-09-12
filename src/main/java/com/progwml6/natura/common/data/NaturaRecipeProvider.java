package com.progwml6.natura.common.data;

import com.progwml6.natura.common.Tags;
import com.progwml6.natura.common.conditions.ConfigOptionCondition;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static com.progwml6.natura.shared.NaturaCommons.*;

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
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(barley_flour)
                    .addIngredient(Tags.Items.CROPS_BARLEY)
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

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.BREAD)
                    .patternLine("###")
                    .key('#', Tags.Items.CROPS_BARLEY)
                    .setGroup("")
                    .addCriterion("has_barley", this.hasItem(Tags.Items.CROPS_BARLEY))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/bread"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/bread")))
                            .withCriterion("has_barley", this.hasItem(Tags.Items.CROPS_BARLEY))))
            .build(consumer, new ResourceLocation("natura", "common/bread"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(cactus_juice)
                    .addIngredient(Blocks.CACTUS)
                    .setGroup("")
                    .addCriterion("has_cactus", this.hasItem(Blocks.CACTUS))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/cactus_juice"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/cactus_juice")))
                            .withCriterion("has_cactus", this.hasItem(Blocks.CACTUS))))
            .build(consumer, new ResourceLocation("natura", "common/cactus_juice"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.CAKE)
                    .patternLine("AAA").patternLine("BCB").patternLine(" D ")
                    .key('A', Items.MILK_BUCKET).key('B', Items.SUGAR).key('C', Items.EGG).key('D', Tags.Items.BARLEY)
                    .setGroup("")
                    .addCriterion("has_egg", this.hasItem(Items.EGG))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/cake"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/cake")))
                            .withCriterion("has_egg", this.hasItem(Items.EGG))))
            .build(consumer, new ResourceLocation("natura", "common/cake"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.CARROT, 9)
                    .addIngredient(carrots_seed_bag)
                    .setGroup("")
                    .addCriterion("has_carrot_seed_bag", this.hasItem(carrots_seed_bag))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/carrots"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/carrots")))
                            .withCriterion("has_carrot_seed_bag", this.hasItem(carrots_seed_bag))))
            .build(consumer, new ResourceLocation("natura", "common/carrots"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(carrots_seed_bag)
                    .patternLine("AAA").patternLine("AAA").patternLine("AAA")
                    .key('A', net.minecraftforge.common.Tags.Items.CROPS_CARROT)
                    .setGroup("")
                    .addCriterion("has_carrot", this.hasItem(net.minecraftforge.common.Tags.Items.CROPS_CARROT))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/carrots_bag"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/carrots_bag")))
                            .withCriterion("has_carrot", this.hasItem(net.minecraftforge.common.Tags.Items.CROPS_CARROT))))
            .build(consumer, new ResourceLocation("natura", "common/carrots_bag"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.CHARCOAL)
                    .patternLine("AAA").patternLine("AAA").patternLine("AAA")
                    .key('A', ash_cloud)
                    .setGroup("")
                    .addCriterion("has_ash_cloud", this.hasItem(ash_cloud))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/charcoal"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/charcoal")))
                            .withCriterion("has_ash_cloud", this.hasItem(ash_cloud))))
            .build(consumer, new ResourceLocation("natura", "common/charcoal"));

    ConditionalRecipe.builder()
            .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Blocks.DAYLIGHT_DETECTOR)
                    .patternLine("AAA").patternLine("BBB").patternLine("CCC")
                    .key('A', net.minecraftforge.common.Tags.Items.GLASS).key('B', ItemTags.WOODEN_SLABS).key('C', net.minecraftforge.common.Tags.Items.GEMS_QUARTZ)
                    .setGroup("")
                    .addCriterion("has_quartz", this.hasItem(net.minecraftforge.common.Tags.Items.GEMS_QUARTZ))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/daylight_detector"), ConditionalAdvancement.builder()
                    .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/daylight_detector")))
                            .withCriterion("has_quartz", this.hasItem(net.minecraftforge.common.Tags.Items.GEMS_QUARTZ))))
            .build(consumer, new ResourceLocation("natura", "common/daylight_detector"));

    ConditionalRecipe.builder()
            .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.GLASS_BOTTLE, 3)
                    .patternLine("A A").patternLine(" A ")
                    .key('A', net.minecraftforge.common.Tags.Items.GLASS)
                    .setGroup("")
                    .addCriterion("has_glass", this.hasItem(net.minecraftforge.common.Tags.Items.GLASS))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/glass_bottle"), ConditionalAdvancement.builder()
                    .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID))
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/glass_bottle")))
                            .withCriterion("has_glass", this.hasItem(net.minecraftforge.common.Tags.Items.GLASS))))
            .build(consumer, new ResourceLocation("natura", "common/glass_bottle"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.GUNPOWDER)
                    .patternLine("AA").patternLine("AA")
                    .key('A', Tags.Items.DUST_SULFUR)
                    .setGroup("")
                    .addCriterion("has_sulfur", this.hasItem(Tags.Items.DUST_SULFUR))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/gunpowder"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/gunpowder")))
                            .withCriterion("has_sulfur", this.hasItem(Tags.Items.DUST_SULFUR))))
            .build(consumer, new ResourceLocation("natura", "common/gunpowder"));

    ConditionalRecipe.builder()
            .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_ENTITIES_PULSE_ID))
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.LEATHER)
                    .patternLine("AA").patternLine("AA")
                    .key('A', imp_leather)
                    .setGroup("")
                    .addCriterion("has_sulfur", this.hasItem(imp_leather))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/leather"), ConditionalAdvancement.builder()
                    .addCondition(this.pulseLoaded(NaturaPulseIds.NATURA_ENTITIES_PULSE_ID))
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/leather")))
                            .withCriterion("has_sulfur", this.hasItem(imp_leather))))
            .build(consumer, new ResourceLocation("natura", "common/leather"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.NETHER_WART, 9)
                    .addIngredient(nether_wart_seed_bag)
                    .setGroup("")
                    .addCriterion("has_nether_wart_bag", this.hasItem(nether_wart_seed_bag))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/nether_wart"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/nether_wart")))
                            .withCriterion("has_nether_wart_bag", this.hasItem(nether_wart_seed_bag))))
            .build(consumer, new ResourceLocation("natura", "common/nether_wart"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(nether_wart_seed_bag)
                    .patternLine("BCB").patternLine("BAB").patternLine("BBB")
                    .key('A', Blocks.NETHER_WART_BLOCK).key('B', Items.STRING).key('C', Items.PAPER)
                    .setGroup("")
                    .addCriterion("has_nether_wart_block", this.hasItem(Blocks.NETHER_WART_BLOCK))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/nether_wart_bag"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/nether_wart_bag")))
                            .withCriterion("has_nether_wart_block", this.hasItem(Blocks.NETHER_WART_BLOCK))))
            .build(consumer, new ResourceLocation("natura", "common/nether_wart_bag"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.POTATO, 9)
                    .addIngredient(potatoes_seed_bag)
                    .setGroup("")
                    .addCriterion("has_potatoes_bag", this.hasItem(potatoes_seed_bag))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/potatoes"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/potatoes")))
                            .withCriterion("has_potatoes_bag", this.hasItem(potatoes_seed_bag))))
            .build(consumer, new ResourceLocation("natura", "common/potatoes"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(potatoes_seed_bag)
                    .patternLine("AAA").patternLine("AAA").patternLine("AAA")
                    .key('A', net.minecraftforge.common.Tags.Items.CROPS_POTATO)
                    .setGroup("")
                    .addCriterion("has_potato", this.hasItem(net.minecraftforge.common.Tags.Items.CROPS_POTATO))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/potatoes_bag"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/potatoes_bag")))
                            .withCriterion("has_potato", this.hasItem(net.minecraftforge.common.Tags.Items.CROPS_POTATO))))
            .build(consumer, new ResourceLocation("natura", "common/potatoes_bag"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.STRING)
                    .patternLine("AAA")
                    .key('A', Tags.Items.CROPS_COTTON)
                    .setGroup("")
                    .addCriterion("has_cotton", this.hasItem(Tags.Items.CROPS_COTTON))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/string"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/string")))
                            .withCriterion("has_cotton", this.hasItem(Tags.Items.CROPS_COTTON))))
            .build(consumer, new ResourceLocation("natura", "common/string"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(sulfur_powder)
                    .patternLine("AA").patternLine("AA")
                    .key('A', sulfur_cloud)
                    .setGroup("")
                    .addCriterion("has_sulfur_cloud", this.hasItem(sulfur_cloud))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/sulfur"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/sulfur")))
                            .withCriterion("has_sulfur_cloud", this.hasItem(sulfur_cloud))))
            .build(consumer, new ResourceLocation("natura", "common/sulfur"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.WATER_BUCKET)
                    .patternLine("AAA").patternLine("ABA").patternLine("AAA")
                    .key('A', cactus_juice).key('B', Items.BUCKET)
                    .setGroup("")
                    .addCriterion("has_cactus_juice", this.hasItem(cactus_juice))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/water_bucket"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/water_bucket")))
                            .withCriterion("has_cactus_juice", this.hasItem(cactus_juice))))
            .build(consumer, new ResourceLocation("natura", "common/water_bucket"));

    ConditionalRecipe.builder()
            .addCondition(this.configOptionEnabled("enableWheatRecipe"))
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(wheat_flour, 9)
                    .addIngredient(Items.WHEAT)
                    .setGroup("")
                    .addCriterion("has_wheat", this.hasItem(Items.WHEAT))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/wheat_flour"), ConditionalAdvancement.builder()
                    .addCondition(this.configOptionEnabled("enableWheatRecipe"))
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/wheat_flour")))
                            .withCriterion("has_wheat", this.hasItem(Items.WHEAT))))
            .build(consumer, new ResourceLocation("natura", "common/wheat_flour"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT_SEEDS, 9)
                    .addIngredient(wheat_seed_bag)
                    .setGroup("")
                    .addCriterion("has_wheat_bag", this.hasItem(wheat_seed_bag))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/wheat_seeds"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/wheat_seeds")))
                            .withCriterion("has_wheat_bag", this.hasItem(wheat_seed_bag))))
            .build(consumer, new ResourceLocation("natura", "common/wheat_seeds"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(wheat_seed_bag)
                    .patternLine("AAA").patternLine("AAA").patternLine("AAA")
                    .key('A', Items.WHEAT_SEEDS)
                    .setGroup("")
                    .addCriterion("has_wheat_seeds", this.hasItem(Items.WHEAT_SEEDS))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/wheat_seed_bag"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/wheat_seed_bag")))
                            .withCriterion("has_wheat_seeds", this.hasItem(Items.WHEAT_SEEDS))))
            .build(consumer, new ResourceLocation("natura", "common/wheat_seed_bag"));

    ConditionalRecipe.builder()
            .addCondition(this.TRUE())
            .addRecipe(ShapedRecipeBuilder.shapedRecipe(Items.WHITE_WOOL)
                    .patternLine("AAA").patternLine("AAA").patternLine("AAA")
                    .key('A', Tags.Items.CROPS_COTTON)
                    .setGroup("")
                    .addCriterion("has_cotton", this.hasItem(Tags.Items.CROPS_COTTON))
                    ::build)
            .setAdvancement(new ResourceLocation("natura", "common/wool"), ConditionalAdvancement.builder()
                    .addCondition(this.TRUE())
                    .addAdvancement(Advancement.Builder.builder().withParentId(new ResourceLocation("minecraft", "recipes/root"))
                            .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("natura", "common/wool")))
                            .withCriterion("has_cotton", this.hasItem(Tags.Items.CROPS_COTTON))))
            .build(consumer, new ResourceLocation("natura", "common/wool"));
  }

  private ICondition pulseLoaded(String pulseId) {
    return new PulseLoadedCondition(pulseId);
  }

  private ICondition configOptionEnabled(String configSetting) {
    return new ConfigOptionCondition(configSetting);
  }

}
