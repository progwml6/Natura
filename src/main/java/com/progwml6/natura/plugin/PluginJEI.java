package com.progwml6.natura.plugin;

import com.progwml6.natura.Natura;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class PluginJEI extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IIngredientBlacklist ingredientBlacklist = jeiHelpers.getIngredientBlacklist();

        if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
        {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.eucalyptusDoor, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.hopseedDoor, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.sakuraDoor, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.redwoodDoor, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.redwoodBarkDoor, 1, OreDictionary.WILDCARD_VALUE));

            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.barleyCrop, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaOverworld.cottonCrop, 1, OreDictionary.WILDCARD_VALUE));
        }

        if (Natura.pulseManager.isPulseLoaded(NaturaNether.PulseId))
        {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaNether.ghostwoodDoor, 1, OreDictionary.WILDCARD_VALUE));
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(NaturaNether.bloodwoodDoor, 1, OreDictionary.WILDCARD_VALUE));
        }
    }
}
