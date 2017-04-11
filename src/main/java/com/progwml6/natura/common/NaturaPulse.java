package com.progwml6.natura.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.BlockGrassStairs;
import com.progwml6.natura.common.block.base.BlockButtonBase;
import com.progwml6.natura.common.block.base.BlockFenceBase;
import com.progwml6.natura.common.block.base.BlockFenceGateBase;
import com.progwml6.natura.common.block.base.BlockNaturaStairsBase;
import com.progwml6.natura.common.block.base.BlockPressurePlateBase;
import com.progwml6.natura.common.block.base.BlockTrapDoorBase;
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
import net.minecraft.item.crafting.IRecipe;
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

    protected void addShapedRecipeFirst(List<IRecipe> recipeList, ItemStack itemstack, Object... objArray)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (objArray[var4] instanceof String[])
        {
            String[] var7 = ((String[]) objArray[var4++]);

            for (String var9 : var7)
            {
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        }
        else
        {
            while (objArray[var4] instanceof String)
            {
                String var11 = (String) objArray[var4++];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }

        HashMap<Character, ItemStack> var12;

        for (var12 = new HashMap<>(); var4 < objArray.length; var4 += 2)
        {
            Character var13 = (Character) objArray[var4];
            ItemStack var14 = null;

            if (objArray[var4 + 1] instanceof Item)
            {
                var14 = new ItemStack((Item) objArray[var4 + 1]);
            }
            else if (objArray[var4 + 1] instanceof Block)
            {
                var14 = new ItemStack((Block) objArray[var4 + 1], 1, Short.MAX_VALUE);
            }
            else if (objArray[var4 + 1] instanceof ItemStack)
            {
                var14 = (ItemStack) objArray[var4 + 1];
            }

            var12.put(var13, var14);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (int var16 = 0; var16 < var5 * var6; ++var16)
        {
            char var10 = var3.charAt(var16);

            if (var12.containsKey(Character.valueOf(var10)))
            {
                var15[var16] = var12.get(Character.valueOf(var10)).copy();
            }
            else
            {
                var15[var16] = null;
            }
        }

        ShapedRecipes var17 = new ShapedRecipes(var5, var6, var15, itemstack);
        recipeList.add(0, var17);
    }

    protected void addShapelessRecipeFirst(List<IRecipe> recipeList, ItemStack par1ItemStack, Object... par2ArrayOfObj)
    {
        ArrayList<ItemStack> arraylist = new ArrayList<>();
        Object[] aobject = par2ArrayOfObj;
        int i = par2ArrayOfObj.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object1));
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipe!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        recipeList.add(0, new ShapelessRecipes(par1ItemStack, arraylist));
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
