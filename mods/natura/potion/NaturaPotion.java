package mods.natura.potion;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class NaturaPotion extends Potion
{

    public static final Potion naturaPoison = new NaturaPotion(30, true, 0).setPotionName("natura.potion.poison");

    protected NaturaPotion(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
    }

    public Potion setIconIndex (int par1, int par2)
    {
        super.setIconIndex(par1, par2);
        return this;
    }

    @Override
    public void performEffect (EntityLiving par1EntityLiving, int par2)
    {
        if (this.id == naturaPoison.id)
        {
            par1EntityLiving.attackEntityFrom(DamageSource.magic, 1);
        }
    }

    @Override
    public boolean isReady (int par1, int par2)
    {
        if (this.id == naturaPoison.id)
        {
            return par1 % 20 == 0;
        }
        return false;
    }
}
