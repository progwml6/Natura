package mods.natura.items.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaKama extends ItemSword
{
    String texture;

    public NaturaKama(ToolMaterial toolmaterial, String texture)
    {
        super(toolmaterial);
        this.texture = texture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:" + texture + "_kama");
    }

    /* Shears */
    public boolean onBlockDestroyed (ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        return true;
    }

    public boolean canHarvestBlock (Block par1Block)
    {
        return par1Block == Blocks.web || par1Block == Blocks.redstone_wire || par1Block == Blocks.tripwire;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock (ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != Blocks.web && par2Block != Blocks.leaves ? (par2Block == Blocks.wool ? 5.0F : super.func_150893_a/*getStrVsBlock*/(par1ItemStack, par2Block)) : 15.0F;
    }

    @Override
    public boolean itemInteractionForEntity (ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity.worldObj.isRemote)
        {
            return false;
        }
        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable) entity;
            if (target.isShearable(itemstack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ,
                        EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

                Random rand = new Random();
                for (ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                itemstack.damageItem(1, entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak (ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        if (player.worldObj.isRemote)
        {
            return false;
        }
        Block id = player.worldObj.getBlock(x, y, z);
        if (id instanceof IShearable)
        {
            IShearable target = (IShearable) id;
            if (target.isShearable(itemstack, player.worldObj, x, y, z))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                Random rand = new Random();

                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.worldObj, x + d, y + d1, z + d2, stack);
                    entityitem.delayBeforeCanPickup = 10;
                    player.worldObj.spawnEntityInWorld(entityitem);
                }

                itemstack.damageItem(1, player);
                player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(id)], 1);
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.kama"));
    }
}
