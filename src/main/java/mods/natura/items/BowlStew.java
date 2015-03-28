package mods.natura.items;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BowlStew extends ItemFood
{
    public static String[] textureNames = new String[] { "mushroom", "glowshroom" };

    public BowlStew()
    {
        super(6, false);
    }

    @Override
    public ItemStack onItemUseFinish (ItemStack stack, World world, EntityPlayer player)
    {
        // TODO 1.7 check this
        player.getFoodStats().addStats(6, 0.6f);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);

        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
            if (stack.stackSize <= 0)
            {
                int stackDamage = stack.getItemDamage() % 14;
                if (stackDamage == 0)
                    return new ItemStack(Items.bowl);

                return new ItemStack(NContent.bowlEmpty, 1, stackDamage - 1);
            }

            ItemStack returnStack = new ItemStack(Items.bowl);
            int stackDamage = stack.getItemDamage() % 14;
            if (stackDamage != 0)
                returnStack = new ItemStack(NContent.bowlEmpty, 1, stackDamage - 1);

            player.inventory.addItemStackToInventory(returnStack);
        }

        return stack;
    }

    @Override
    protected void onFoodEaten (ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && stack.getItemDamage() / 14 == 1)
        {
            int duration = 0;
            PotionEffect potion;

            potion = player.getActivePotionEffect(Potion.nightVision);
            if (potion != null)
                duration = potion.getDuration();
            else
                duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, duration + 45 * 25, 0));

            potion = player.getActivePotionEffect(Potion.weakness);
            if (potion != null)
                duration = potion.getDuration();
            else
                duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, duration + 16 * 25, 0));

            potion = player.getActivePotionEffect(Potion.weakness);
            if (potion != null)
                duration = potion.getDuration();
            else
                duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, duration + 8 * 25, 0));
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        int arr = MathHelper.clamp_int(stack.getItemDamage() / 14, 0, textureNames.length);
        return "item.bowl." + textureNames[arr];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int iter = 1; iter < textureNames.length; iter++)
        {
            par3List.add(new ItemStack(par1, 1, iter * 14));
        }
    }
}
