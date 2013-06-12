package mods.natura.items;

import java.util.List;
import java.util.ArrayList;
import mods.natura.common.NContent;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BowlStew extends ItemFood
{   
    World world = entityPlayer.worldObj;
    Icon[] icons;
    public static String[] textureNames = new String[] { "mushroom", "glowshroom" };

    public BowlStew(int par1)
    {
        super(par1, 6, false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses ()
    {
        return true;
    }

    @Override
    public Icon getIcon (ItemStack stack, int renderPass)
    {
        if (renderPass == 0)
        {
            int stackDamage = stack.getItemDamage() % 14;
            if (stackDamage == 0)
                return Item.bowlEmpty.getIconFromDamage(0);
            return NContent.bowlEmpty.getIconFromDamage(stack.getItemDamage() % 14 - 1);
        }
        else
            return icons[stack.getItemDamage() / 14];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];
        for (int i = 0; i < this.textureNames.length; ++i)
        {
            if (!textureNames[i].equals(""))
                this.icons[i] = iconRegister.registerIcon("natura:stew_" + textureNames[i]);
        }
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        player.getFoodStats().addStats(this);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);
        
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
            if (stack.stackSize <= 0)
            {
                int stackDamage = stack.getItemDamage() % 14;
                if (stackDamage == 0)
                    return new ItemStack(Item.bowlEmpty);
                
                return new ItemStack(NContent.bowlEmpty, 1, stackDamage - 1);
            }
            
            ItemStack returnStack = new ItemStack(Item.bowlEmpty);
            int stackDamage = stack.getItemDamage() % 14;
            if (stackDamage != 0)
                returnStack = new ItemStack(NContent.bowlEmpty, 1, stackDamage - 1);
            
            player.inventory.addItemStackToInventory(returnStack);
        }

        return stack;
    }
    
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && stack.getItemDamage() / 14 == 1)
        {
            cureAllPotions(world, entityPlayer);
            
            int duration = 0;
            PotionEffect potion;
            
            potion = player.getActivePotionEffect(Potion.nightVision);
            if (potion != null)
                duration = potion.duration;
            else
                duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, duration + 45*25, 0));
            
            potion = player.getActivePotionEffect(Potion.weakness);
            if (potion != null)
                duration = potion.duration;
            else
                duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, duration + 16*25, 0));
            
            potion = player.getActivePotionEffect(Potion.weakness);
            if (potion != null)
                duration = potion.duration;
            else
            duration = 0;
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, duration + 8*25, 0));
        }
    }
    
    public String getUnlocalizedName(ItemStack stack)
    {
        int arr = MathHelper.clamp_int(stack.getItemDamage()/14, 0, textureNames.length);
        return "item.bowl." +textureNames[arr];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int iter = 1; iter < textureNames.length; iter++)
        {
            par3List.add(new ItemStack(par1, 1, iter * 14));
        }
    }
 
    public static void cureAllPotions(World world, EntityPlayer entityPlayer) {
        List<PotionEffect> activePotions = new ArrayList<PotionEffect>(entityPlayer.getActivePotionEffects());
        for (PotionEffect potionEffect : activePotions) {
            entityPlayer.removePotionEffect(potionEffect.getPotionID());
        }
    }
}
