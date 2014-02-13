package mods.natura.blocks.crops;

import java.util.List;
import java.util.Random;

import mods.natura.client.BerryRender;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherBerryBush extends BlockLeavesBase implements IPlantable
{
    Random random;
    public IIcon[] fastIcons;
    public IIcon[] fancyIcons;
    public static String[] textureNames = new String[] { "blightberry", "duskberry", "skyberry", "stingberry", "blightberry_ripe", "duskberry_ripe", "skyberry_ripe", "stingberry_ripe" };

    public NetherBerryBush()
    {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        random = new Random();
        this.setHardness(0.3F);
        this.setStepSound(Block.soundGrassFootstep);
        this.setUnlocalizedName("berrybush");
        this.setCreativeTab(NaturaTab.tab);
    }

    /* Berries show up at meta 12-15 */

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.fastIcons = new IIcon[textureNames.length];
        this.fancyIcons = new IIcon[textureNames.length];

        for (int i = 0; i < this.fastIcons.length; i++)
        {
            this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_fast");
            this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_fancy");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        if (graphicsLevel)
        {
            if (metadata < 12)
            {
                return fancyIcons[metadata % 4];
            }
            else
            {
                return fancyIcons[metadata % 4 + 4];
            }
        }
        else
        {
            if (metadata < 12)
            {
                return fastIcons[metadata % 4];
            }
            else
            {
                return fastIcons[metadata % 4 + 4];
            }
        }
    }

    /* Bushes are stored by size then type */
    @Override
    public int damageDropped (int metadata)
    {
        return metadata % 4;
    }

    /* The following methods define a berry bush's size depending on metadata */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        if (l < 4)
        {
            return AxisAlignedBB.getBoundingBox((double) x + 0.25D, y, (double) z + 0.25D, (double) x + 0.75D, (double) y + 0.5D, (double) z + 0.75D);
        }
        else if (l < 8)
        {
            return AxisAlignedBB.getBoundingBox((double) x + 0.125D, y, (double) z + 0.125D, (double) x + 0.875D, (double) y + 0.75D, (double) z + 0.875D);
        }
        else
        {
            return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0D, (double) y + 1.0D, (double) z + 1.0D);
        }
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        if (l < 4)
        {
            return AxisAlignedBB.getBoundingBox((double) x + 0.25D, y, (double) z + 0.25D, (double) x + 0.75D, (double) y + 0.5D, (double) z + 0.75D);
        }
        else if (l < 8)
        {
            return AxisAlignedBB.getBoundingBox((double) x + 0.125D, y, (double) z + 0.125D, (double) x + 0.875D, (double) y + 0.75D, (double) z + 0.875D);
        }
        else
        {
            return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0D, (double) y + 1.0D, (double) z + 1.0D);
        }
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess iblockaccess, int x, int y, int z)
    {
        int md = iblockaccess.getBlockMetadata(x, y, z);

        float minX;
        float minY = 0F;
        float minZ;
        float maxX;
        float maxY;
        float maxZ;

        if (md < 4)
        {
            minX = minZ = 0.25F;
            maxX = maxZ = 0.75F;
            maxY = 0.5F;
        }
        else

        if (md < 8)
        {
            minX = minZ = 0.125F;
            maxX = maxZ = 0.875F;
            maxY = 0.75F;
        }

        else
        {
            minX = minZ = 0.0F;
            maxX = maxZ = 1.0F;
            maxY = 1.0F;
        }
        setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /* Left-click harvests berries */
    @Override
    public void onBlockClicked (World world, int x, int y, int z, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta >= 12)
            {
                world.setBlock(x, y, z, blockID, meta - 4, 3);
                EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NContent.netherBerryItem.itemID, 1, meta - 12));
                world.spawnEntityInWorld(entityitem);
                entityitem.onCollideWithPlayer(player);
            }
        }
    }

    /* Right-click harvests berries */
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
            return false;

        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 12)
        {
            if (world.isRemote)
                return true;

            world.setBlock(x, y, z, blockID, meta - 4, 3);
            EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NContent.netherBerryItem.itemID, 1, meta - 12));
            world.spawnEntityInWorld(entityitem);
            entityitem.onCollideWithPlayer(player);
            return true;
        }
        return false;
    }

    /* Render logic */

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    public void setGraphicsLevel (boolean flag)
    {
        graphicsLevel = flag;
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    public int getRenderType ()
    {
        return BerryRender.berryModel;
    }

    public boolean shouldSideBeRendered (IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if (l > 7 || graphicsLevel)
        {
            return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
        }
        else
        {
            return true;
        }
    }

    /* Bush growth */

    @Override
    public void updateTick (World world, int x, int y, int z, Random random1)
    {
        if (world.isRemote)
        {
            return;
        }

        int height;

        for (height = 1; world.getBlockId(x, y - height, z) == this.blockID; ++height)
        {
            ;
        }

        if (random1.nextInt(75) == 0)
        {
            int md = world.getBlockMetadata(x, y, z);
            if (md < 12)
            {
                world.setBlock(x, y, z, this, md + 4, 3);
            }
            if (random1.nextInt(3) == 0 && height < 3 && world.getBlock(x, y + 1, z) == Blocks.air && md >= 8)
            {
                world.setBlock(x, y + 1, z,  this, md % 4, 3);
            }
        }
    }

    /* Resistance to fire */

    @Override
    public int getFlammability (IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 0;
    }

    @Override
    public boolean isFlammable (IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return false;
    }

    @Override
    public int getFireSpreadSpeed (World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 0;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 12; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public EnumPlantType getPlantType (World world, int x, int y, int z)
    {
        return EnumPlantType.Nether;
    }

    @Override
    public int getPlantID (World world, int x, int y, int z)
    {
        return this;
    }

    @Override
    public int getPlantMetadata (World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) - 4;
    }

    public boolean boneFertilize (World world, int x, int y, int z, Random random)
    {

        int meta = world.getBlockMetadata(x, y, z);
        if (meta / 4 < 2)
        {
            if (random.nextBoolean())
            {
                int setMeta = random.nextInt(2) + 1 + meta / 4;
                if (setMeta > 2)
                    setMeta = 2;
                world.setBlockMetadataWithNotify(x, y, z, meta % 4 + setMeta * 4, 4);
            }
            return true;
        }

        Block block = world.getBlock(x, y + 1, z);
        if (block == null || world.isAirBlock(x, y + 1, z))
        {
            if (random.nextBoolean())
            {
                if (random.nextInt(3) == 0)
                    world.setBlock(x, y + 1, z, this, meta % 4, 3);
            }

            return true;
        }

        return false;
    }
}