package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BoneBag extends Item
{
    String textureName;

    public BoneBag(String texture)
    {
        super();
        textureName = texture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
            return false;

        boolean planted = false;
        for (int posX = x - 1; posX <= x + 1; posX++)
        {
            for (int posZ = z - 1; posZ <= z + 1; posZ++)
            {
                if (player.canPlayerEdit(posX, y, posZ, side, stack) && player.canPlayerEdit(posX, y + 1, posZ, side, stack))
                {
                    if (applyBonemeal(stack, world, posX, y, posZ, player))
                    {
                        planted = true;
                        if (!world.isRemote)
                        {
                            world.playAuxSFX(2005, posX, y, posZ, 0);
                        }
                    }
                }
            }
        }
        if (planted)
        {
            if (!player.capabilities.isCreativeMode)
                stack.stackSize--;
            if (stack.stackSize < 1)
                player.destroyCurrentEquippedItem();
        }
        return planted;
    }

    public static boolean applyBonemeal (ItemStack par0ItemStack, World par1World, int par2, int par3, int par4, EntityPlayer player)
    {
        Block l = par1World.getBlock(par2, par3, par4);

        BonemealEvent event = new BonemealEvent(player, par1World, l, par2, par3, par4);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return false;
        }

        if (event.getResult() == event.getResult().ALLOW)
        {
            /*if (!par1World.isRemote)
            {
                par0ItemStack.stackSize--;
            }*/
            return true;
        }

        if (l == Blocks.sapling)
        {
            if (!par1World.isRemote)
            {
                if ((double) par1World.rand.nextFloat() < 0.45D)
                {
                    ((BlockSapling) Blocks.sapling).markOrGrowMarked(par1World, par2, par3, par4, par1World.rand);
                }

                //--par0ItemStack.stackSize;
            }

            return true;
        }
        else if (l != Blocks.brown_mushroom && l != Blocks.red_mushroom)
        {
            if (l != Blocks.melon_stem && l != Blocks.pumpkin_stem)
            {
                if (l != null && l instanceof BlockCrops)
                {
                    if (par1World.getBlockMetadata(par2, par3, par4) == 7)
                    {
                        return false;
                    }
                    else
                    {
                        if (!par1World.isRemote)
                        {
                            ((BlockCrops) l).fertilize(par1World, par2, par3, par4);
                            //--par0ItemStack.stackSize;
                        }

                        return true;
                    }
                }
                else
                {
                    int i1;
                    int j1;
                    int k1;

                    if (l == Blocks.cocoa)
                    {
                        i1 = par1World.getBlockMetadata(par2, par3, par4);
                        j1 = BlockDirectional.getDirection(i1);
                        k1 = BlockCocoa.func_72219_c(i1);

                        if (k1 >= 2)
                        {
                            return false;
                        }
                        else
                        {
                            if (!par1World.isRemote)
                            {
                                ++k1;
                                par1World.setBlockMetadataWithNotify(par2, par3, par4, k1 << 2 | j1, 2);
                                //--par0ItemStack.stackSize;
                            }

                            return true;
                        }
                    }
                    else if (l != Blocks.grass)
                    {
                        return false;
                    }
                    else
                    {
                        if (!par1World.isRemote)
                        {
                            --par0ItemStack.stackSize;
                            label102:

                            for (i1 = 0; i1 < 128; ++i1)
                            {
                                j1 = par2;
                                k1 = par3 + 1;
                                int l1 = par4;

                                for (int i2 = 0; i2 < i1 / 16; ++i2)
                                {
                                    j1 += itemRand.nextInt(3) - 1;
                                    k1 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
                                    l1 += itemRand.nextInt(3) - 1;

                                    if (par1World.getBlock(j1, k1 - 1, l1) != Blocks.grass || par1World.getBlock(j1, k1, l1).isNormalCube())
                                    {
                                        continue label102;
                                    }
                                }

                                if (par1World.getBlock(j1, k1, l1) == Blocks.air)
                                {
                                    if (itemRand.nextInt(10) != 0)
                                    {
                                        if (Blocks.tallgrass.canBlockStay(par1World, j1, k1, l1))
                                        {
                                            par1World.setBlock(j1, k1, l1, Blocks.tallgrass, 1, 3);
                                        }
                                    }
                                    else
                                    {
                                        ForgeHooks.plantGrass(par1World, j1, k1, l1);
                                    }
                                }
                            }
                        }

                        return true;
                    }
                }
            }
            else if (par1World.getBlockMetadata(par2, par3, par4) == 7)
            {
                return false;
            }
            else
            {
                if (!par1World.isRemote)
                {
                    ((BlockStem) l).fertilizeStem(par1World, par2, par3, par4);
                    //--par0ItemStack.stackSize;
                }

                return true;
            }
        }
        else
        {
            if (!par1World.isRemote)
            {
                if ((double) par1World.rand.nextFloat() < 0.4D)
                {
                    ((BlockMushroom) l).fertilizeMushroom(par1World, par2, par3, par4, par1World.rand);
                }

                //--par0ItemStack.stackSize;
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:seedbag_" + textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.bonebag"));
    }
}
