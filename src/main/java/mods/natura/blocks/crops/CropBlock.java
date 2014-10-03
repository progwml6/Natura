package mods.natura.blocks.crops;

import java.util.ArrayList;
import java.util.Random;

import mods.natura.Natura;
import mods.natura.client.CropRender;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropBlock extends BlockBush implements IGrowable
{
    public CropBlock()
    {
        super();
        this.setTickRandomly(true);
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.setCreativeTab(null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }
    
    public int getMaxGrowth(int meta)
    {
        return (meta < 4) ? 3 : 8;
    }
    
    public int getStartGrowth(int meta)
    {
        return (meta < 4) ? 0 : 4;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        this.checkAndDropBlock(world, x, y, z);

        int light = world.getBlockLightValue(x, y, z);
        if (light >= 8)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if (this.getMaxGrowth(meta) != meta)
            {
                float grow = this.getGrowthRate(world, x, y, z, meta, light);

                if (random.nextInt((int) (60.0F / grow) + 1) == 0)
                {
                    meta++;
                    world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                }
            }
        }
    }

    protected float getGrowthRate (World world, int x, int y, int z, int meta, int light)
    {
        float growth = 0.25f * (light - 7);
        Block soil = world.getBlock(x, y - 1, z);

        if (world.canBlockSeeTheSky(x, y, z) || !requiresSun(meta))
            growth += 2f;

        if (soil != null && soil.isFertile(world, x, y - 1, z))
            growth *= 2f;

        return 1f + growth;
    }

    boolean requiresSun (int meta)
    {
        return true;
    }

    protected boolean canThisPlantGrowOnThisBlock (Block par1)
    {
        return par1 == Blocks.farmland;
    }

    /* Left-click harvests berries */
    @Override
    public void onBlockClicked (World world, int x, int y, int z, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta == 8)
            {
                world.setBlock(x, y, z, this, 6, 3);
                EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NContent.plantItem, 1, 3));
                world.spawnEntityInWorld(entityitem);
                entityitem.onCollideWithPlayer(player);
            }
        }
    }

    /* Right-click harvests berries */
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        /*if (world.isRemote)
            return false;*/

        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 8)
        {
            if (world.isRemote)
                return true;

            world.setBlock(x, y, z, this, 6, 3);
            EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NContent.plantItem, 1, 3));
            world.spawnEntityInWorld(entityitem);
            entityitem.onCollideWithPlayer(player);
            return true;
        }
        return false;
    }

    @Override
    public float getBlockHardness (World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) > 3)
            return 0.5f;
        return this.blockHardness;
    }

    public IIcon[] icons;
    public String[] textureNames = new String[] { "barley_1", "barley_2", "barley_3", "barley_4", "cotton_1", "cotton_2", "cotton_3", "cotton_4", "cotton_5" };

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icons[meta];
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType ()
    {
        return CropRender.model;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune)
    {
        if (meta == 3 || meta == 8)
            return this.getCropItem(meta);
        return this.getSeedItem(meta);
    }

    public Item getCropItem (int meta)
    {
        return NContent.plantItem;
    }

    public Item getSeedItem (int meta)
    {
        return NContent.seeds;
    }

    @Override
    public int damageDropped (int meta)
    {
        if (meta < 4)
            return 0;
        return 3;
    }

    public int seedDamageDropped (int meta)
    {
        if (meta < 4)
            return 0;
        return 1;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance (World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
    }

    @Override
    public ArrayList<ItemStack> getDrops (World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata == 3 || metadata == 8)
        {
            int count = quantityDropped(metadata, fortune, world.rand);
            for (int i = 0; i < count; i++)
            {
                Item id = getItemDropped(metadata, world.rand, 0);
                if (id != null)
                {
                    ret.add(new ItemStack(id, 1, damageDropped(metadata)));
                }
            }
        }
        if (metadata >= 4)
        {
            ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
            if (metadata >= 5 && world.rand.nextBoolean())
                ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
            if (metadata >= 7 && world.rand.nextBoolean())
                ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
        }
        else
        {
            ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
            if (metadata >= 2 && world.rand.nextInt(3) == 0)
                ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
            if (metadata >= 3 && world.rand.nextInt(4) == 0)
                ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
        }

        return ret;
    }

    /*@Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	if (meta % 4 == 0)
    		return 1+random.nextInt(fortune+1);
    	return random.nextInt(meta/4);
    }*/

    /**
     * Returns the ID of the items to drop on destruction.
     */

    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem (World world, int x, int y, int z)
    {
        return this.getSeedItem(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int getDamageValue (World par1World, int par2, int par3, int par4)
    {
        return seedDamageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z); //Wild crops can stay
        if (meta == 3 || meta == 8)
            return world.getBlock(x, y - 1, z) != Blocks.air;

        return super.canBlockStay(world, x, y, z);
    }

    @Override
    public int getPlantMetadata (IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 4)
            return 0;
        else
            return 4;
    }

    // isNotFullyGrown
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote)
    {
        int meta = world.getBlockMetadata(x, y, z);

        return this.getMaxGrowth(meta) != meta;
    }

    // canUseBonemeal
    @Override
    public boolean func_149852_a(World world, Random random, int x, int y, int z)
    {
        return true;
    }

    // onUseBonemeal
    @Override
    public void func_149853_b(World world, Random random, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (this.getMaxGrowth(meta) != meta)
        {
            int maxGrowth = this.getMaxGrowth(meta);
            int growthSpan = maxGrowth - this.getStartGrowth(meta);
            int output = Natura.random.nextInt(growthSpan) + 1 + meta;
            
            if (output > maxGrowth)
                output = maxGrowth;
            
            if (output != meta)
                world.setBlockMetadataWithNotify(x, y, z, output, 3);
        }
    }
}
