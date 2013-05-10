package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.gui.NGuiHandler;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlternateWorkbench extends BlockWorkbench
{
    Icon[] topIcons;
    Icon[] sideIcons;
    Icon[] faceIcons;

    public AlternateWorkbench(int par1)
    {
        super(par1);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int metadata)
    {
        if (side == 0)
            return NContent.planks.getIcon(side, metadata);
        if (side == 1)
            return topIcons[metadata];
        if (side == 2 || side == 4)
            return faceIcons[metadata];

        return sideIcons[metadata];
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.topIcons = new Icon[NContent.woodTextureNames.length];
        this.sideIcons = new Icon[NContent.woodTextureNames.length];
        this.faceIcons = new Icon[NContent.woodTextureNames.length];

        for (int i = 0; i < this.topIcons.length; ++i)
        {
            this.topIcons[i] = iconRegister.registerIcon("natura:" + NContent.woodTextureNames[i] + "_workbench_top");
            this.sideIcons[i] = iconRegister.registerIcon("natura:" + NContent.woodTextureNames[i] + "_workbench_side");
            this.faceIcons[i] = iconRegister.registerIcon("natura:" + NContent.woodTextureNames[i] + "_workbench_face");
        }
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (int par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < topIcons.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            System.out.println("Workbench");
            player.openGui(Natura.instance, NGuiHandler.craftingGui, world, x, y, z);
            //player.displayGUIWorkbench(par2, par3, par4);
            return true;
        }
    }
}
