package mods.natura.blocks;

import java.util.List;

import mods.natura.common.NaturaTab;
import mods.tinker.tconstruct.TConstruct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class NBlock extends Block
{
	public String[] textureNames;
	public Icon[] icons;
	public NBlock(int id, Material material, float hardness, String[] tex)
	{
		super(id, material);
		setHardness(hardness);
		this.setCreativeTab(NaturaTab.tab);
		textureNames = tex;
	}

	@Override
	public int damageDropped (int meta)
	{
		return meta;
	}
	
	public void registerIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]);
        }
    }
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata (int side, int meta)
	{
		return icons[meta];
	}

	@Override
	public void getSubBlocks (int id, CreativeTabs tab, List list)
	{
		for (int iter = 0; iter < icons.length; iter++)
		{
			list.add(new ItemStack(id, 1, iter));
		}
	}
}
