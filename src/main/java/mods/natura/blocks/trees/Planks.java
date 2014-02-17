package mods.natura.blocks.trees;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Planks extends Block
{
    public IIcon[] icons;
    public static String[] textureNames = new String[] { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
            "fusewood" };

    public Planks()
    {
        super(Material.wood);
        // TODO 1.7 Where the heck did this go? setBurnProperties(this, 5, 20);
        this.setHardness(2.0f);
        this.setCreativeTab(NaturaTab.tab);
        this.setStepSound(Block.soundTypeWood);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        if (meta >= textureNames.length )
            return Blocks.lava.getIcon(0, 0);
        return icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_planks");
        }
    }

    /*public boolean renderAsNormalBlock()
    {
        return false;
    }*/

    public int getFlammability (IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        if (metadata == 2 || metadata == 4 || metadata > 10)
            return 0;
        return this.getFlammability(world, x, y, z, face);
    }

    public int getFireSpreadSpeed (World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        if (metadata == 2 || metadata == 4 || metadata > 10)
            return 0;
        return this.getFireSpreadSpeed(world, x, y, z, face);
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < textureNames.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
