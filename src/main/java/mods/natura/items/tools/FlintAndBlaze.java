package mods.natura.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }

        if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else
        {
            if (world.isAirBlock(x, y, z))
            {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, Blocks.fire);
            }

            stack.damageItem(1, player);
            return true;
        }
    }
}
