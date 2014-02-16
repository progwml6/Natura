package mods.natura.items;

import java.util.List;

import mods.natura.Natura;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

public class ImpMeat extends NSpecialFood
{
    public ImpMeat()
    {
        super(new int[] { 3, 8 }, new float[] { 0.2f, 0.6f }, new String[] { "raw", "cooked" }, new String[] { "impmeat_raw", "impmeat_cooked" });
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.impmeat1"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.impmeat2"));
            break;
        }
    }

    @Override
	protected void onFoodEaten (ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int duration = 0;
            PotionEffect potion;
            switch (stack.getItemDamage())
            {
            case 0:
                potion = player.getActivePotionEffect(Potion.hunger);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, duration + 8 * 20, 0));
                player.setFire(10);

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.poison);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration= 0;
                    player.addPotionEffect(new PotionEffect(Potion.poison.id, duration + 5 * 20, 0));
                }
                break;

            case 1:
                potion = player.getActivePotionEffect(Potion.fireResistance);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, duration + 15 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.poison);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.poison.id, duration + 5 * 20, 0));
                }
                break;
            }
        }
    }
}
