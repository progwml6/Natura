package com.progwml6.natura.common;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.BlockGrassStairs;
import com.progwml6.natura.common.block.base.BlockButtonBase;
import com.progwml6.natura.common.block.base.BlockFenceBase;
import com.progwml6.natura.common.block.base.BlockFenceGateBase;
import com.progwml6.natura.common.block.base.BlockNaturaStairsBase;
import com.progwml6.natura.common.block.base.BlockPressurePlateBase;
import com.progwml6.natura.common.block.base.BlockTrapDoorBase;
import com.progwml6.natura.decorative.NaturaDecorative;
import com.progwml6.natura.entities.NaturaEntities;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.NaturaWorld;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import slimeknights.mantle.block.EnumBlock;
import slimeknights.mantle.block.EnumBlockSlab;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.item.ItemBlockSlab;

/**
 * Just a small helper class that provides some function for cleaner Pulses.
 *
 * Items should be registered during PreInit
 *
 * Models should be registered during Init
 */
// THANKS TINKERS
public abstract class NaturaPulse
{
    protected static boolean isEntitiesLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaEntities.PulseId);
    }

    protected static boolean isWorldLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaWorld.PulseId);
    }

    protected static boolean isOverworldLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId);
    }

    protected static boolean isNetherLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaNether.PulseId);
    }

    protected static boolean isDecorativeLoaded()
    {
        return Natura.pulseManager.isPulseLoaded(NaturaDecorative.PulseId);
    }

    /**
     * Sets the correct unlocalized name and registers the item.
     */
    protected static <T extends Item> T registerItem(T item, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", name));
        }

        item.setUnlocalizedName(Util.prefix(name));
        item.setRegistryName(Util.getResource(name));
        GameRegistry.register(item);
        return item;
    }

    protected static <T extends Block> T registerBlock(T block, String name)
    {
        ItemBlock itemBlock = new ItemBlockMeta(block);
        registerBlock(block, itemBlock, name);
        return block;
    }

    protected static <T extends EnumBlock<?>> T registerEnumBlock(T block, String name)
    {
        registerBlock(block, new ItemBlockMeta(block), name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static <T extends EnumBlockSlab<?>> T registerEnumBlockSlab(T block, String name)
    {
        registerBlock(block, new ItemBlockSlab(block), name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    protected static <E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> BlockNaturaStairsBase registerBlockStairsFrom(EnumBlock<E> block, E value, String name)
    {
        return registerBlock(new BlockNaturaStairsBase(block.getDefaultState().withProperty(block.prop, value)), name);
    }

    protected static <E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> BlockGrassStairs registerBlockGrassStairsFrom(EnumBlock<E> block, E value, String name)
    {
        return registerBlock(new BlockGrassStairs(block.getDefaultState().withProperty(block.prop, value)), name);
    }

    // Buttons, Trap Doors, Fences, Fence Gates, Pressure Plates START
    protected static BlockButtonBase registerBlockButton(String name)
    {
        return registerBlock(new BlockButtonBase(), name);
    }

    protected static BlockPressurePlateBase registerBlockPressurePlate(String name)
    {
        return registerBlock(new BlockPressurePlateBase(), name);
    }

    protected static BlockTrapDoorBase registerBlockTrapDoor(String name)
    {
        return registerBlock(new BlockTrapDoorBase(), name);
    }

    protected static BlockFenceBase registerBlockFence(String name)
    {
        return registerBlock(new BlockFenceBase(), name);
    }

    protected static BlockFenceGateBase registerBlockFenceGate(String name)
    {
        return registerBlock(new BlockFenceGateBase(), name);
    }
    // Buttons, Pressure Plates, Trap Doors Fences, Fence Gates END

    @SuppressWarnings("unchecked")
    protected static <T extends Block> T registerBlock(ItemBlock itemBlock, String name)
    {
        Block block = itemBlock.getBlock();
        return (T) registerBlock(block, itemBlock, name);
    }

    protected static <T extends Block> T registerBlock(T block, String name, IProperty<?> property)
    {
        ItemBlockMeta itemBlock = new ItemBlockMeta(block);
        registerBlock(block, itemBlock, name);
        ItemBlockMeta.setMappingProperty(block, property);
        return block;
    }

    protected static <T extends Block> T registerBlock(T block, ItemBlock itemBlock, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = Util.prefix(name);
        block.setUnlocalizedName(prefixedName);
        itemBlock.setUnlocalizedName(prefixedName);

        register(block, name);
        register(itemBlock, name);
        return block;
    }

    protected static <T extends Block> T registerBlockNoItem(T block, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = Util.prefix(name);
        block.setUnlocalizedName(prefixedName);

        register(block, name);
        return block;
    }

    protected static <T extends IForgeRegistryEntry<?>> T register(T thing, String name)
    {
        thing.setRegistryName(Util.getResource(name));
        GameRegistry.register(thing);
        return thing;
    }

    protected static void registerTE(Class<? extends TileEntity> teClazz, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! TE: %s", name));
        }

        GameRegistry.registerTileEntity(teClazz, Util.prefix(name));
    }

    protected void addShapedRecipe(ItemStack stack, Object... recipeComponents)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (recipeComponents[i] instanceof String[])
        {
            String[] astring = ((String[]) recipeComponents[i++]);

            for (String s2 : astring)
            {
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        else
        {
            while (recipeComponents[i] instanceof String)
            {
                String s1 = (String) recipeComponents[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }

        Map<Character, ItemStack> map;

        for (map = Maps.<Character, ItemStack> newHashMap(); i < recipeComponents.length; i += 2)
        {
            Character character = (Character) recipeComponents[i];
            ItemStack itemstack = ItemStack.EMPTY;

            if (recipeComponents[i + 1] instanceof Item)
            {
                itemstack = new ItemStack((Item) recipeComponents[i + 1]);
            }
            else if (recipeComponents[i + 1] instanceof Block)
            {
                itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
            }
            else if (recipeComponents[i + 1] instanceof ItemStack)
            {
                itemstack = (ItemStack) recipeComponents[i + 1];
            }

            map.put(character, itemstack);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int l = 0; l < j * k; ++l)
        {
            char c0 = s.charAt(l);

            if (map.containsKey(Character.valueOf(c0)))
            {
                aitemstack[l] = map.get(Character.valueOf(c0)).copy();
            }
            else
            {
                aitemstack[l] = ItemStack.EMPTY;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, stack);

        CraftingManager.getInstance().getRecipeList().add(shapedrecipes);
    }

    protected void addShapelessRecipe(ItemStack stack, Object... recipeComponents)
    {
        List<ItemStack> list = Lists.<ItemStack> newArrayList();

        for (Object object : recipeComponents)
        {
            if (object instanceof ItemStack)
            {
                list.add(((ItemStack) object).copy());
            }
            else if (object instanceof Item)
            {
                list.add(new ItemStack((Item) object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                list.add(new ItemStack((Block) object));
            }
        }

        CraftingManager.getInstance().getRecipeList().add(new ShapelessRecipes(stack, list));
    }

    protected static void addSlabRecipe(ItemStack slab, ItemStack input)
    {
        GameRegistry.addShapedRecipe(new ItemStack(slab.getItem(), 6, slab.getItemDamage()), "BBB", 'B', input);
    }

    protected static void addSlabRecipe(Block slab, int slabMeta, ItemStack input)
    {
        GameRegistry.addShapedRecipe(new ItemStack(slab, 6, slabMeta), "BBB", 'B', input);
    }

    protected static void addStairRecipe(Block stairs, ItemStack input)
    {
        GameRegistry.addShapedRecipe(new ItemStack(stairs, 4, 0), "B  ", "BB ", "BBB", 'B', input);
    }

}
