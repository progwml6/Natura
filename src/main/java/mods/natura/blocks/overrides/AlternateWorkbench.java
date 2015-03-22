package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.gui.NGuiHandler;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlternateWorkbench extends BlockWorkbench
{
    IIcon[] topIcons;
    IIcon[] sideIcons;
    IIcon[] faceIcons;

    public AlternateWorkbench()
    {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        if (side == 0)
            return NContent.planks.getIcon(side, metadata);
        if (side == 1)
            return topIcons[metadata];
        if (side == 2 || side == 4)
            return faceIcons[metadata];

        return sideIcons[metadata];
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.topIcons = new IIcon[NContent.woodTextureNames.length];
        this.sideIcons = new IIcon[NContent.woodTextureNames.length];
        this.faceIcons = new IIcon[NContent.woodTextureNames.length];

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
    public void getSubBlocks (Item par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < topIcons.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }

    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            player.openGui(Natura.instance, NGuiHandler.craftingGui, world, x, y, z);
            //player.displayGUIWorkbench(par2, par3, par4);
            return true;
        }
    }
}
