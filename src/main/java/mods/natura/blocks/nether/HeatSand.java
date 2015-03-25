package mods.natura.blocks.nether;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HeatSand extends BlockFalling
{
    public HeatSand()
    {
        super();
        this.setHardness(3f);
        this.setStepSound(Block.soundTypeSand);
        this.setCreativeTab(NaturaTab.tab);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        float f = 0.125F;
        return new AxisAlignedBB(pos, new BlockPos(pos.getX() + 1, pos.getY() + 1 - f, pos.getZ() + 1));
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityPlayer)
        {
            ItemStack stack = ((EntityPlayer) entityIn).inventory.getStackInSlot(36);
            if (stack == null)
            	entityIn.attackEntityFrom(DamageSource.inFire, 1);
        }
        else if (entityIn instanceof EntityLiving && !entityIn.isImmuneToFire())
        {
        	entityIn.attackEntityFrom(DamageSource.inFire, 1);
        }
        /*par5Entity.motionX *= 0.4D;
        par5Entity.motionZ *= 0.4D;*/
    }
}
