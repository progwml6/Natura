package com.progwml6.natura.common;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.item.ItemBlockMeta;

/** This class basically is what you want when you got models to register */
@SideOnly(Side.CLIENT)
public final class ModelRegisterUtil
{

    public static final String VARIANT_INVENTORY = "inventory";

    // Regular ITEM MODELS //

    /** Registers the item-meta combo in the itemstack with the given location for the inventory-variant */
    public static void registerItemModel(ItemStack itemStack, ResourceLocation name)
    {
        if (itemStack != ItemStack.EMPTY && name != null)
        {
            // tell the loader to load the model
            ModelLoader.registerItemVariants(itemStack.getItem(), name);
            // tell the game which model to use for this item-meta combination
            ModelLoader.setCustomModelResourceLocation(itemStack.getItem(), itemStack.getMetadata(), new ModelResourceLocation(name, VARIANT_INVENTORY));
        }
    }

    /** Registers the given item with its registry name for all metadata values for the inventory variant */
    public static ResourceLocation registerItemModel(Item item)
    {
        ResourceLocation itemLocation = null;
        if (item != null)
        {
            itemLocation = item.getRegistryName();
        }
        if (itemLocation != null)
        {
            itemLocation = registerIt(item, itemLocation);
        }

        return itemLocation;
    }

    /** Registers the item of the given block with its registry name for all metadata values for the inventory variant */
    public static ResourceLocation registerItemModel(Block block)
    {
        return registerItemModel(Item.getItemFromBlock(block));
    }

    /** Registers an itemblockmeta model for the blocks inventory variant. */
    public static void registerItemBlockMeta(Block block)
    {
        if (block != null)
        {
            Item item = Item.getItemFromBlock(block);
            if (item instanceof ItemBlockMeta)
            {
                ((ItemBlockMeta) item).registerItemModels();
            }
            else
            {
                Natura.log.error("Trying to register an ItemBlockMeta model for a non itemblockmeta block: " + block.getRegistryName());
            }
        }
    }

    /** Registers the item with the given metadata and its registry name for the inventory variant */
    public static void registerItemModel(Item item, int meta)
    {
        registerItemModel(item, meta, VARIANT_INVENTORY);
    }

    /** Registers the given item with the given meta and its registry name for the given variant */
    public static void registerItemModel(Item item, int meta, String variant)
    {
        if (item != null)
        {
            registerItemModel(item, meta, item.getRegistryName(), variant);
        }
    }

    /** Registers the given item/meta combination with the model at the given location, and the given variant */
    public static void registerItemModel(Item item, int meta, ResourceLocation location, String variant)
    {
        if (item != null && !StringUtils.isNullOrEmpty(variant))
        {
            //ModelLoader.registerItemVariants(item, location);
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
        }
    }

    private static ResourceLocation registerIt(Item item, final ResourceLocation location)
    {
        // plop it in.
        // This here is needed for the model to be found ingame when the game looks for a model to render an Itemstack
        // we use an ItemMeshDefinition because it allows us to do it no matter what metadata we use
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
        {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                return new ModelResourceLocation(location, VARIANT_INVENTORY);
            }
        });

        // We have to readd the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelLoader.registerItemVariants(item, location);

        return location;
    }

    private ModelRegisterUtil()
    {
    }
}
