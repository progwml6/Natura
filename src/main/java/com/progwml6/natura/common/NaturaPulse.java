package com.progwml6.natura.common;

import java.util.Locale;

import com.progwml6.natura.Natura;
import com.progwml6.natura.entities.NaturaEntities;
import com.progwml6.natura.library.Util;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import slimeknights.mantle.block.BlockStairsBase;
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

    protected static <T extends EnumBlockSlab<?>> T registerEnumBlockSlab(T block, String name)
    {
        registerBlock(block, new ItemBlockSlab(block), name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    protected static <E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> BlockStairsBase registerBlockStairsFrom(EnumBlock<E> block, E value, String name)
    {
        return registerBlock(new BlockStairsBase(block.getDefaultState().withProperty(block.prop, value)), name);
    }

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
}
