package mods.natura.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Glowshroom extends BlockMushroom
{
    Icon[] icons;
    String[] textureNames = { "green", "purple" };
    public Glowshroom(int par1)
    {
        super(par1, "");
        this.setStepSound(soundGrassFootstep);
        this.setCreativeTab(NaturaTab.tab);
    }
    
    public boolean fertilizeMushroom(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:mushroom_"+textureNames[i]);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return icons[meta];
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length; i++)
        par3List.add(new ItemStack(par1, 1, i));
    }
}
