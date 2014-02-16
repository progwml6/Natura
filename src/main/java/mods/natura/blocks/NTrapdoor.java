package mods.natura.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class NTrapdoor extends BlockTrapDoor
{
    String textureName;

    public NTrapdoor(Material par2Material, String texture)
    {
        super(par2Material);
        textureName = texture;
        this.setCreativeTab(NaturaTab.tab);
        this.disableStats();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:" + textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return this.blockIcon;
    }

}
