package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DarkTreeBlock extends BlockLog
{
    public Icon[] icons;
    public String[] textureNames = new String[] { "darkwood_bark", "darkwood_heart", "fusewood_bark", "fusewood_heart" };
    
    public DarkTreeBlock(int id)
    {
        super(id);
        this.setHardness(5.0F);
        this.setResistance(40F);
        this.setStepSound(Block.soundWoodFootstep);
        //setBurnProperties(this.blockID, 5, 20);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
        int tex = (metadata % 4)*2;
        int orientation = metadata / 4;
        
        switch (orientation) //Ends of logs
        {
        case 0:
            if (side == 0 || side == 1)
                return icons[tex + 1];
            break;
        case 1:
            if (side == 4 || side == 5)
                return icons[tex + 1];
            break;
        case 2:
            if (side == 2 || side == 3)
                return icons[tex + 1];
            break;
        }
        
        return icons[tex];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]);
        }
    }
    
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }

    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) 
    {
        world.createExplosion(null, x, y, z, 2f, false);
        //System.out.println("Block ID: "+world.getBlockId(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length/2; i++)
        par3List.add(new ItemStack(par1, 1, i));
    }
}
