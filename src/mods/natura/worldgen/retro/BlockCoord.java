package mods.natura.worldgen.retro;

import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;

/**
 * Standardized implementation for representing and manipulating Block Coordinates. Provides standard Java Collection interaction.
 * from COFH lib
 * @author King Lemming
 * 
 */
public final class BlockCoord implements Comparable<BlockCoord>, Serializable {

    public int x;
    public int y;
    public int z;

    public BlockCoord(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockCoord(TileEntity tile) {

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
    }

    public void step(int dir) {

        x += BlockHelper.SIDE_COORD_MOD[dir][0];
        y += BlockHelper.SIDE_COORD_MOD[dir][1];
        z += BlockHelper.SIDE_COORD_MOD[dir][2];
    }

    public void step(int dir, int dist) {

        switch (dir) {
        case 0:
            this.y -= dist;
            break;
        case 1:
            this.y += dist;
            break;
        case 2:
            this.z -= dist;
            break;
        case 3:
            this.z += dist;
            break;
        case 4:
            this.x -= dist;
            break;
        case 5:
            this.x += dist;
            break;
        default:
        }
    }

    public BlockCoord copy() {

        return new BlockCoord(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof BlockCoord)) {
            return false;
        }
        BlockCoord other = (BlockCoord) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {

        int hash = this.x;
        hash *= 31 + this.y;
        hash *= 31 + this.z;
        return hash;
    }

    @Override
    public String toString() {

        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    /* Comparable */
    @Override
    public int compareTo(BlockCoord other) {

        return this.x == other.x ? this.y == other.y ? this.z - other.z : this.y - other.y : this.x - other.x;
    }

}