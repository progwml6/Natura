package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HeatSand extends BlockSand
{
    Icon[] icons;
    public HeatSand(int par1)
    {
        super(par1);
        this.setHardness(5f);
        this.setStepSound(Block.soundSandFootstep);
        this.setCreativeTab(NaturaTab.tab);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        //this.icons = new Icon[1];

        //this.icons[0] = iconRegister.registerIcon("natura:heatsand");
        this.blockIcon = iconRegister.registerIcon("natura:heatsand");
    }
    
   /* @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return icons[0];
    }*/

}
