package mods.natura.blocks.tech;

import java.util.Random;

import mods.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RespawnObelisk extends Block
{
    public RespawnObelisk(int id, Material material)
    {
        super(id, material);
    }

    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player.isSneaking())
            return false;
        player.setSpawnChunk(new ChunkCoordinates(x, y, z), false, world.provider.dimensionId);

        if (!world.isRemote)
            player.addChatMessage("Spawn point set!");
        world.setBlockMetadataWithNotify(x, y, z, 1, 3);
        world.playSound((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "portal.portal", 1.0F, Natura.random.nextFloat() * 0.4F + 0.8F, false);

        return true;
    }

    @Override
    public boolean isBed (World world, int x, int y, int z, EntityLivingBase player)
    {
        return world.getBlockMetadata(x, y, z) != 0;
    }

    @Override
    public int getLightValue (IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 0)
            return 7;
        return lightValue[blockID];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick (World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 0)
        {
            for (int i = 0; i < 2; i++)
            {
                world.spawnParticle("portal", x + random.nextFloat() * 3 - 1, y + random.nextFloat() * 2, z + random.nextFloat() * 3 - 1, 0, 0, 0);
                world.spawnParticle("witchMagic", x + random.nextFloat() * 3 - 1, y + random.nextFloat() * 2, z + random.nextFloat() * 3 - 1, 0, 0, 0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    Icon activeTexture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:obelisk");
        this.activeTexture = iconRegister.registerIcon("natura:obelisk_active");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return meta == 0 ? this.blockIcon : this.activeTexture;
    }
}
