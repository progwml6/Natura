package mods.natura.blocks.crops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlowerBlock extends BlockFlower
{

    public FlowerBlock(int i)
    {
        super(i);
        setCreativeTab(CreativeTabs.tabDecorations);
        this.setStepSound(soundTypeGrass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:flower_bluebells");
    }
}
