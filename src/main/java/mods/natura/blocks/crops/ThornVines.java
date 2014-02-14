package mods.natura.blocks.crops;

import mods.natura.Natura;
import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockVine;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThornVines extends BlockVine
{

    public ThornVines()
    {
        super();
        this.setCreativeTab(NaturaTab.netherTab);
        setHardness(1.0F);
        setStepSound(soundTypeGrass);
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor ()
    {
        return 0xFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor (int par1)
    {
        return 0xFFFFFF;
    }

    public int colorMultiplier (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:thornvine");
    }

    public void onEntityCollidedWithBlock (World par1World, int x, int y, int z, Entity entity)
    {
        //entity.motionY *= 1.5D;
        if (!(entity instanceof EntityItem) && !(entity instanceof EntityGhast) && Natura.random.nextInt(30) == 0)
        {
            DamageSource source = Natura.random.nextBoolean() ? DamageSource.cactus : DamageSource.lava;
            entity.attackEntityFrom(source, 1);
        }
    }

    /*public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        return 15;
    }*/
}
