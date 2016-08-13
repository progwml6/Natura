package com.progwml6.natura.common;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.Util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameData;
import slimeknights.mantle.item.ItemBlockMeta;

public class ClientProxy extends CommonProxy
{
    protected ResourceLocation registerModel(Item item, String... customVariants)
    {
        return registerModel(item, 0, customVariants);
    }

    protected ResourceLocation registerModel(Item item, int meta, String... customVariants)
    {
        @SuppressWarnings("deprecation")
        Object o = GameData.getItemRegistry().getNameForObject(item);

        if (o == null)
        {
            Natura.log.error("Trying to register a model for an unregistered item: %s" + item.getUnlocalizedName());
            return null;
        }

        ResourceLocation location = (ResourceLocation) o;

        location = new ResourceLocation(location.getResourceDomain(), location.getResourcePath());

        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(location,
                        "inventory"));

        if (customVariants.length > 0)
        {
            ModelLoader.registerItemVariants(item, location);
        }

        for (String customVariant : customVariants)
        {
            String custom = location.getResourceDomain() + ":" + customVariant;
            ModelLoader.registerItemVariants(item, new ResourceLocation(custom));
        }

        return location;
    }

    protected void registerItemModel(ItemStack item, String name)
    {
        if (!name.contains(":"))
        {
            name = Util.resource(name);
        }

        ModelLoader.registerItemVariants(item.getItem(), new ResourceLocation(name));

        ModelLoader.setCustomModelResourceLocation(item.getItem(), item
                .getMetadata(), new ModelResourceLocation(name, "inventory"));
    }

    public ResourceLocation registerItemModel(Item item)
    {
        ResourceLocation itemLocation = getItemLocation(item);
        if (itemLocation == null)
        {
            return null;
        }

        return registerIt(item, itemLocation);
    }

    public ResourceLocation registerItemModel(Block block)
    {
        return registerItemModel(Item.getItemFromBlock(block));
    }

    public void registerItemModel(Item item, int meta, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private static ResourceLocation registerIt(Item item, final ResourceLocation location)
    {
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
        {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        ModelLoader.registerItemVariants(item, location);

        return location;
    }

    protected void registerItemBlockMeta(Block block)
    {
        if (block != null)
        {
            ((ItemBlockMeta) Item.getItemFromBlock(block)).registerItemModels();
        }
    }

    public static ResourceLocation getItemLocation(Item item)
    {
        return Util.getItemLocation(item);
    }
}
