package mods.natura.blocks.crops;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockVine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ThornVines extends BlockVine
{

    public ThornVines(int id)
    {
        super(id);
        this.setCreativeTab(NaturaTab.tab);
        setHardness(1.0F);
        setStepSound(soundGrassFootstep);
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 0xFFFFFF;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1)
    {
        return 0xFFFFFF;
    }
    
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 0xFFFFFF;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:thornvine");
    }
    
    public void onEntityCollidedWithBlock (World par1World, int x, int y, int z, Entity entity)
    {
        //entity.motionY *= 1.5D;
        if (!(entity instanceof EntityItem) && Natura.random.nextInt(10) == 0)
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
