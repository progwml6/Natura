package mods.natura.blocks.nether;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HeatSand extends BlockSand
{
    IIcon[] icons;

    public HeatSand()
    {
        super();
        this.setHardness(3f);
        this.setStepSound(Block.soundTypeSand);
        this.setCreativeTab(NaturaTab.tab);
        MinecraftForge.setBlockHarvestLevel(this, "shovel", 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:heatsand");
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool (World par1World, int par2, int par3, int par4)
    {
        float f = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double) par2, (double) par3, (double) par4, (double) (par2 + 1), (double) ((float) (par3 + 1) - f), (double) (par4 + 1));
    }

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
