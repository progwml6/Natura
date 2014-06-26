package mods.natura.blocks.nether;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HeatSand extends BlockFalling
{
    IIcon[] icons;

    public HeatSand()
    {
        super();
        this.setHardness(3f);
        this.setStepSound(Block.soundTypeSand);
        this.setCreativeTab(NaturaTab.tab);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:heatsand");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World par1World, int par2, int par3, int par4)
    {
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1 - f, par4 + 1);
    }

    @Override
    public void onEntityCollidedWithBlock (World par1World, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            ItemStack stack = ((EntityPlayer) entity).inventory.getStackInSlot(36);
            if (stack == null)
                entity.attackEntityFrom(DamageSource.inFire, 1);
        }
        else if (entity instanceof EntityLiving && !entity.isImmuneToFire())
        {
            entity.attackEntityFrom(DamageSource.inFire, 1);
        }
        /*par5Entity.motionX *= 0.4D;
        par5Entity.motionZ *= 0.4D;*/
    }
}
