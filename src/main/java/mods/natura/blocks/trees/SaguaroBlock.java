package mods.natura.blocks.trees;

import java.util.Random;

import mods.natura.client.SaguaroRenderer;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.worldgen.SaguaroGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SaguaroBlock extends Block implements IPlantable
{
    public SaguaroBlock()
    {
        super(Material.cactus);
        this.setCreativeTab(NaturaTab.tab);
        setStepSound(soundTypeCloth);
        this.setHardness(0.3f);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0 && world.getWorldInfo().isRaining() && random.nextInt(20) == 0 && world.getBlock(x, y + 1, z) == Blocks.air)
        {
            switch (random.nextInt(4))
            {
            case 0:
                if (world.getBlock(x + 1, y, z) == Blocks.air)
                    world.setBlock(x + 1, y, z, this, 5, 3);
                break;
            case 1:
                if (world.getBlock(x, y, z + 1) == Blocks.air)
                    world.setBlock(x, y, z + 1, this, 6, 3);
                break;
            case 2:
                if (world.getBlock(x - 1, y, z) == Blocks.air)
                    world.setBlock(x - 1, y, z, this, 3, 3);
                break;
            case 3:
                if (world.getBlock(x, y, z - 1) == Blocks.air)
                    world.setBlock(x, y, z - 1, this, 4, 3);
                break;
            }
        }
        else if (meta == 2 && random.nextInt(200) == 0)
        {
            SaguaroGen gen = new SaguaroGen(NContent.saguaro, 0, true);
            gen.generate(world, random, x, y, z);
        }
        else if (meta == 1 && random.nextInt(200) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 3);
        }

        //Fruit shouldn't do a thing
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0)
        {
            float offset = 0.125F;
            return AxisAlignedBB.getBoundingBox(x + offset, y, z + offset, x + 1 - offset, y + 1 - offset, z + 1 - offset);
        }
        else if (meta == 1 || meta == 2)
        {
            float offset = 0.325F;
            return AxisAlignedBB.getBoundingBox(x + offset, y, z + offset, x + 1 - offset, y + 1 - offset, z + 1 - offset);
        }
        else if (meta == 3)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + 0.625f, y + 0.1875, z + offset, x + 1.125f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 4)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + offset, y + 0.1875, z + 0.625f, x + 1 - offset, y + 0.75, z + 1.125f);
        }
        else if (meta == 5)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x - 0.125f, y + 0.1875, z + offset, x + 0.375f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 6)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + offset, y + 0.1875, z - 0.125f, x + 1 - offset, y + 0.75, z + 0.375f);
        }
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0)
        {
            float offset = 0.125F;
            float height = 0.125F;
            float base = 0F;
            if (world.getBlock(x, y + 1, z) == this)
                height = 0F;

            Block block = world.getBlock(x, y - 1, z);
            if (block != null && !block.isOpaqueCube())
                base = 0.125F;

            return AxisAlignedBB.getBoundingBox(x + offset, y, z + offset, x + 1 - offset, y + 1 - height, z + 1 - offset);
        }
        else if (meta == 1 || meta == 2)
        {
            float offset = 0.325F;
            return AxisAlignedBB.getBoundingBox(x + offset, y, z + offset, x + 1 - offset, y + 0.5, z + 1 - offset);
        }
        else if (meta == 3)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + 0.625f, y + 0.1875, z + offset, x + 1.125f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 4)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + offset, y + 0.1875, z + 0.625f, x + 1 - offset, y + 0.75, z + 1.125f);
        }
        else if (meta == 5)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x - 0.125f, y + 0.1875, z + offset, x + 0.375f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 6)
        {
            float offset = 0.25F;
            return AxisAlignedBB.getBoundingBox(x + offset, y + 0.1875, z - 0.125f, x + 1 - offset, y + 0.75, z + 0.375f);
        }
        return null;
    }

    @Override
    public int getRenderType ()
    {
        return SaguaroRenderer.model;
    }

    public static int func_72219_c (int par0)
    {
        return (par0 & 12) >> 2;
    }

    public static int getRotation (int meta)
    {
        return meta - 3;
    }

    @Override
    public Item getItemDropped (int meta, Random random, int fortune)
    {
        if (meta == 0)
            return Item.getItemFromBlock(this);
        else
            return NContent.seedFood;
    }

    public IIcon[] icons;
    public String[] textureNames = new String[] { "saguaro_bottom", "saguaro_top", "saguaro_side", "saguaro_fruit" };

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
        if (meta == 0)
        {
            if (side < 2)
                return icons[side];
            else
                return icons[2];
        }
        else if (meta == 1 || meta == 2)
        {
            return icons[1];
        }
        else
        {
            return icons[3];
        }
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt (World world, int i, int j, int k)
    {
        return super.canPlaceBlockAt(world, i, j, k) && canBlockStay(world, i, j, k);
    }

    public void onNeighborBlockChange (World world, int i, int j, int k, int l)
    {
        if (!canBlockStay(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        Block blockID = world.getBlock(x, y - 1, z);
        return blockID == this || blockID == Blocks.sand || blockID == null;

    }

    @Override
    public void onEntityCollidedWithBlock (World world, int i, int j, int k, Entity entity)
    {
        if (!(entity instanceof EntityItem))
            entity.attackEntityFrom(DamageSource.cactus, 1);
    }

    public boolean canConnectSuguaroTo (IBlockAccess world, int x, int y, int z)
    {
        return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 0;

    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Desert;
    }

    @Override
    public Block getPlant (IBlockAccess world, int x, int y, int z)
    {
        return this;
    }

    @Override
    public int getPlantMetadata (IBlockAccess world, int x, int y, int z)
    {
        return 1;
    }

    /*public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List list)
    {
    	for (int i = 0; i < 7; i++)
    		list.add(new ItemStack(par1, 1, i));
    }*/
}
