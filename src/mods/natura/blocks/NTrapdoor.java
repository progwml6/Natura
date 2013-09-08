package mods.natura.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class NTrapdoor extends BlockTrapDoor
{
    String textureName;

    public NTrapdoor(int par1, Material par2Material, String texture)
    {
        super(par1, par2Material);
        textureName = texture;
        this.setCreativeTab(NaturaTab.tab);
        this.disableStats();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:"+textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return this.blockIcon;
    }

}
