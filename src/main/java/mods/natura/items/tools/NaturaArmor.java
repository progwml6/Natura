package mods.natura.items.tools;

import java.util.List;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaArmor extends ItemArmor
{
    String itemTexture;
    String armorTexture;

    public NaturaArmor(ArmorMaterial material, int renderIndex, int slotType, String itemTexture, String armorTexture)
    {
        super(material, renderIndex, slotType);
        this.itemTexture = itemTexture;
        this.armorTexture = armorTexture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type)
    {
        if (slot == 2)
            return "natura:textures/armor/" + armorTexture + "_2.png";
        else
            return "natura:textures/armor/" + armorTexture + "_1.png";
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        switch (armorType)
        {
        case 0:
            list.add(NContent.impHelmetStack.copy());
            break;
        case 1:
            list.add(NContent.impJerkinStack.copy());
            break;
        case 2:
            list.add(NContent.impLeggingsStack.copy());
            break;
        case 3:
            list.add(NContent.impBootsStack.copy());
            break;
        }
    }
}
