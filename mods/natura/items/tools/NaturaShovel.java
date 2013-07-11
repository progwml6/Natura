package mods.natura.items.tools;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NaturaShovel extends ItemSpade
{
    String texture;

    public NaturaShovel(int id, EnumToolMaterial toolmaterial, String texture)
    {
        super(id, toolmaterial);
        this.texture = texture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:" + texture + "_shovel");
    }
}
