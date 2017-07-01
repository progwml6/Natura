package com.progwml6.natura.common;

import java.util.Locale;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.BlockGrassStairs;
import com.progwml6.natura.common.block.base.BlockNaturaStairsBase;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
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

    protected static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = Util.prefix(name);
        block.setUnlocalizedName(prefixedName);

        register(registry, block, name);
        return block;
    }

    protected static <E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> BlockNaturaStairsBase registerBlockStairsFrom(IForgeRegistry<Block> registry, EnumBlock<E> block, E value, String name)
    {
        return registerBlock(registry, new BlockNaturaStairsBase(block.getDefaultState().withProperty(block.prop, value)), name);
    }

    protected static <E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> BlockGrassStairs registerBlockGrassStairsFrom(IForgeRegistry<Block> registry, EnumBlock<E> block, E value, String name)
    {
        return registerBlock(registry, new BlockGrassStairs(block.getDefaultState().withProperty(block.prop, value)), name);
    }

    protected static <T extends Block> T registerItemBlock(IForgeRegistry<Item> registry, T block, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        ItemBlock itemBlock = new ItemBlockMeta(block);

        String prefixedName = Util.prefix(name);
        itemBlock.setUnlocalizedName(prefixedName);

        register(registry, itemBlock, name);
        return block;
    }

    protected static <T extends EnumBlock<?>> T registerEnumItemBlock(IForgeRegistry<Item> registry, T block, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        ItemBlock itemBlock = new ItemBlockMeta(block);

        String prefixedName = Util.prefix(name);
        itemBlock.setUnlocalizedName(prefixedName);

        register(registry, itemBlock, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    @SuppressWarnings("unchecked")
    protected static <T extends Block> T registerItemBlockProp(IForgeRegistry<Item> registry, ItemBlock itemBlock, String name, IProperty<?> property)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = Util.prefix(name);
        itemBlock.setUnlocalizedName(prefixedName);

        register(registry, itemBlock, name);
        ItemBlockMeta.setMappingProperty(itemBlock.getBlock(), property);
        return (T) itemBlock.getBlock();
    }

    protected static <T extends EnumBlockSlab<?>> T registerEnumItemBlockSlab(IForgeRegistry<Item> registry, T block, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        ItemBlock itemBlock = new ItemBlockSlab(block);

        String prefixedName = Util.prefix(name);
        itemBlock.setUnlocalizedName(prefixedName);

        register(registry, itemBlock, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    protected static <T extends Item> T registerItem(IForgeRegistry<Item> registry, T item, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", name));
        }

        item.setUnlocalizedName(Util.prefix(name));
        item.setRegistryName(Util.getResource(name));
        registry.register(item);
        return item;
    }

    protected static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T thing, String name)
    {
        thing.setRegistryName(Util.getResource(name));
        registry.register(thing);
        return thing;
    }

    /**
     * Used to register TileEntitys with the GameRegistry.
     * 
     * @param teClazz
     * @param name
     * 
     */
    protected static void registerTE(Class<? extends TileEntity> teClazz, String name)
    {
        if (!name.equals(name.toLowerCase(Locale.US)))
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! TE: %s", name));
        }

        GameRegistry.registerTileEntity(teClazz, Util.prefix(name));
    }
}
