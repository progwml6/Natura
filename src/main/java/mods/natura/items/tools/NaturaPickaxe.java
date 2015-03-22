package mods.natura.items.tools;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaPickaxe extends ItemPickaxe
{
    String texture;

    public NaturaPickaxe(ToolMaterial toolmaterial, String texture)
    {
        super(toolmaterial);
        this.texture = texture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:" + texture + "_pickaxe");
    }
}
