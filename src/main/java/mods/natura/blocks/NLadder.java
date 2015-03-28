package mods.natura.blocks;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockLadder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NLadder extends BlockLadder
{
    public String[] textureNames;

    public NLadder(String[] textures)
    {
        super();
        this.textureNames = textures;
        this.setHardness(0.4F);
        this.setStepSound(soundTypeLadder);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < textureNames.length; iter++)
        {
            list.add(new ItemStack(id, 1, iter * 4));
        }
    }
}
