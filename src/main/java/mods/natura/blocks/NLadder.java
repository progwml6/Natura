package mods.natura.blocks;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NLadder extends BlockLadder
{
    public String[] textureNames;
    public IIcon[] icons;

    public NLadder(String[] textures)
    {
        super();
        this.textureNames = textures;
        this.setHardness(0.4F);
        this.setStepSound(soundTypeLadder);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icons[meta / 4];
    }

    @Override
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < icons.length; iter++)
        {
            list.add(new ItemStack(id, 1, iter * 4));
        }
    }
}
