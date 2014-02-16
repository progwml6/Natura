package mods.natura.util;

import mods.natura.items.SpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class DispenserBehaviorSpawnEgg extends BehaviorDefaultDispenseItem
{
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    @Override
	public ItemStack dispenseStack (IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
        double d0 = par1IBlockSource.getX() + enumfacing.getFrontOffsetX();
        double d1 = par1IBlockSource.getYInt() + 0.2F;
        double d2 = par1IBlockSource.getZ() + enumfacing.getFrontOffsetZ();
        EntityLiving entity = SpawnEgg.activateSpawnEgg(par2ItemStack, par1IBlockSource.getWorld(), d0, d1, d2, 0);

        if (par2ItemStack.hasDisplayName())
        {
            entity.setCustomNameTag(par2ItemStack.getDisplayName());
        }

        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}
