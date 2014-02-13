package mods.natura.worldgen.retro;


import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Contains various helper functions to assist with {@link Block} and Block-related manipulation and interaction.
 * from COFH LIB
 * @author King Lemming
 * 
 */
public final class BlockHelper {

        private BlockHelper() {

        }

        public static byte[] rotateType = new byte[4096];
        public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
        public static float[][] SIDE_COORD_AABB = { { 1, -2, 1 }, { 1, 2, 1 }, { 1, 1, 1 }, { 1, 1, 2 }, { 1, 1, 1 }, { 2, 1, 1 } };
        public static final byte[] SIDE_LEFT = { 4, 5, 5, 4, 2, 3 };
        public static final byte[] SIDE_RIGHT = { 5, 4, 4, 5, 3, 2 };
        public static final byte[] SIDE_OPPOSITE = { 1, 0, 3, 2, 5, 4 };
        public static final byte[] SIDE_ABOVE = { 3, 2, 1, 1, 1, 1 };
        public static final byte[] SIDE_BELOW = { 2, 3, 0, 0, 0, 0 };

        // These assume facing is towards negative - looking AT side 1, 3, or 5.
        public static final byte[] ROTATE_CLOCK_Y = { 0, 1, 4, 5, 3, 2 };
        public static final byte[] ROTATE_CLOCK_Z = { 5, 4, 2, 3, 0, 1 };
        public static final byte[] ROTATE_CLOCK_X = { 2, 3, 1, 0, 4, 5 };

        public static final byte[] ROTATE_COUNTER_Y = { 0, 1, 5, 4, 2, 3 };
        public static final byte[] ROTATE_COUNTER_Z = { 4, 5, 2, 3, 1, 0 };
        public static final byte[] ROTATE_COUNTER_X = { 3, 2, 0, 1, 4, 5 };

        public static final byte[] INVERT_AROUND_Y = { 0, 1, 3, 2, 5, 4 };
        public static final byte[] INVERT_AROUND_Z = { 1, 0, 2, 3, 5, 4 };
        public static final byte[] INVERT_AROUND_X = { 1, 0, 3, 2, 4, 5 };

        public static final class RotationType {

                public static final int PREVENT = -1;
                public static final int FOUR_WAY = 1;
                public static final int SIX_WAY = 2;
                public static final int RAIL = 3;
                public static final int PUMPKIN = 4;
                public static final int STAIRS = 5;
                public static final int REDSTONE = 6;
                public static final int LOG = 7;
                public static final int SLAB = 8;
                public static final int CHEST = 9;
                public static final int LEVER = 10;
                public static final int SIGN = 11;
        }



        public static int determineXZPlaceFacing(EntityLivingBase living) {

                int quadrant = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

                switch (quadrant) {
                case 0:
                        return 2;
                case 1:
                        return 5;
                case 2:
                        return 3;
                case 3:
                        return 4;
                }
                return 3;
        }

        public static TileEntity getAdjacentTileEntity(World world, int x, int y, int z, ForgeDirection dir) {

                return world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
        }

        public static TileEntity getAdjacentTileEntity(World world, int x, int y, int z, int side) {

                return getAdjacentTileEntity(world, x, y, z, ForgeDirection.values()[side]);
        }

        public static TileEntity getAdjacentTileEntity(TileEntity refTile, ForgeDirection dir) {

                return getAdjacentTileEntity(refTile.worldObj, refTile.xCoord, refTile.yCoord, refTile.zCoord, dir);
        }

        public static TileEntity getAdjacentTileEntity(TileEntity refTile, int side) {

                return getAdjacentTileEntity(refTile.worldObj, refTile.xCoord, refTile.yCoord, refTile.zCoord, ForgeDirection.values()[side]);
        }

        /* COORDINATE TRANSFORM */
        public static int[] getAdjacentCoordinatesForSide(int x, int y, int z, int side) {

                return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2] };
        }

        public static int[] getAdjacentCoordinatesForSide(TileEntity tile, int side) {

                return new int[] { tile.xCoord + SIDE_COORD_MOD[side][0], tile.yCoord + SIDE_COORD_MOD[side][1], tile.zCoord + SIDE_COORD_MOD[side][2] };
        }

        public static AxisAlignedBB getAdjacentAABBForSide(int x, int y, int z, int side) {

                return AxisAlignedBB.getAABBPool().getAABB(x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2],
                                x + SIDE_COORD_AABB[side][0], y + SIDE_COORD_AABB[side][1], z + SIDE_COORD_AABB[side][2]);
        }

        public static int getLeftSide(int side) {

                return SIDE_LEFT[side];
        }

        public static int getRightSide(int side) {

                return SIDE_RIGHT[side];
        }

        public static int getOppositeSide(int side) {

                return SIDE_OPPOSITE[side];
        }

        public static int getAboveSide(int side) {

                return SIDE_ABOVE[side];
        }

        public static int getBelowSide(int side) {

                return SIDE_BELOW[side];
        }

        /* BLOCK ROTATION */
        public static boolean canRotate(int blockId) {

                return rotateType[blockId] != 0;
        }

        public static int rotateVanillaBlock(World world, int bId, int bMeta, int x, int y, int z) {

                switch (rotateType[bId]) {
                case RotationType.FOUR_WAY:
                        return SIDE_LEFT[bMeta];
                case RotationType.SIX_WAY:
                        if (bMeta < 6) {
                                return ++bMeta % 6;
                        }
                        return bMeta;
                case RotationType.RAIL:
                        if (bMeta < 2) {
                                return ++bMeta % 2;
                        }
                        return bMeta;
                case RotationType.PUMPKIN:
                        return ++bMeta % 4;
                case RotationType.STAIRS:
                        return ++bMeta % 8;
                case RotationType.REDSTONE:
                        int upper = bMeta & 0xC;
                        int lower = bMeta & 0x3;
                        return upper + ++lower % 4;
                case RotationType.LOG:
                        return (bMeta + 4) % 12;
                case RotationType.SLAB:
                        return (bMeta + 8) % 16;
                case RotationType.CHEST:
                        int coords[] = new int[3];
                        for (int i = 2; i < 6; i++) {
                                coords = getAdjacentCoordinatesForSide(x, y, z, i);
                                if (world.getBlockId(coords[0], coords[1], coords[2]) == Block.chest.blockID) {
                                        world.setBlockMetadataWithNotify(coords[0], coords[1], coords[2], SIDE_OPPOSITE[bMeta], 1);
                                        return SIDE_OPPOSITE[bMeta];
                                }
                        }
                        return SIDE_LEFT[bMeta];
                case RotationType.LEVER:
                        int shift = 0;
                        if (bMeta > 7) {
                                bMeta -= 8;
                                shift = 8;
                        }
                        if (bMeta == 5) {
                                return 6 + shift;
                        } else if (bMeta == 6) {
                                return 5 + shift;
                        } else if (bMeta == 7) {
                                return 0 + shift;
                        } else if (bMeta == 0) {
                                return 7 + shift;
                        }
                        return bMeta + shift;
                case RotationType.SIGN:
                        return ++bMeta % 16;
                case RotationType.PREVENT:
                default:
                        return bMeta;
                }
        }

}