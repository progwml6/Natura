package mods.natura.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class FlintAndBlaze extends Item
{
    public FlintAndBlaze()
    {
        super();
        this.maxStackSize = 1;
        this.setMaxDamage(256);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /*public ItemStack onItemRightClick (ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Item.fireballCharge.itemID))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    public void onPlayerStoppedUsing (ItemStack stack, World world, EntityPlayer player, int ticksRemaining)
    {
        if (!world.isRemote)
        {
            int time = this.getMaxItemUseDuration(stack) - ticksRemaining;
            if (time > 8)
            {
                int amount = time / 8;
                if (amount > 1)
                    amount = 1;
                for (int i = 0; i < amount; i++)
                {
                    double x = player.posX;
                    double y = player.posY;
                    double z = player.posZ;
                    float f1 = player.rotationYawHead;
                    EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, player, x, y, z);
                    entitysmallfireball.posY = player.posY + 1.62D;
                    world.spawnEntityInWorld(entitysmallfireball);
                    world.playAuxSFXAtEntity(player, 1009, (int) x, (int) y, (int) z, 0);
                }
            }
        }
    }

    public int getMaxItemUseDuration (ItemStack par1ItemStack)
    {
        return 72000;
    }

    public EnumAction getItemUseAction (ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }*/

    //Right-click on blocks
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        pos = pos.offset(side);

        if (!playerIn.canPlayerEdit(pos, side, stack))
        {
            return false;
        }
        else
        {
            if (worldIn.isAirBlock(pos))
            {
                worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
            }

            stack.damageItem(1, playerIn);
            return true;
        }
    }
}
