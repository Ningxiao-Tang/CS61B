package byog.lab5;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    private static class Position {
        private int x;
        private int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }

    /**
     * Computes relative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }

    public static Position topRightNeighbor(Position p, int s){
        int x = p.x + hexRowWidth(s,s) + hexRowOffset(s,s);
        int y = p.y+s;
        Position topRightN = new Position(x, y);
        return topRightN;
    }
    public static Position bottomRightNeighbor(Position p, int s){
        int x = p.x + s + Math.abs(hexRowOffset(s,s));
        int y = p.y - s;
        Position btmRightN = new Position(x, y);
        return btmRightN;
    }
    public static void drawRandomVerticalHexes(TETile[][] world, Position p, int n, int s){
        int x = p.x;
        int y = p.y;
        Position startP = new Position(x,y);
        for(int i = 0; i < n; i++, startP.y+=2*s){
            addHexagon(world,startP,s,randomTile());

        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] Hex = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Hex[x][y] = Tileset.NOTHING;
            }
        }

        Position btmLeft = new Position(7,7);
        drawRandomVerticalHexes(Hex,btmLeft,3,3);

        Position btm2 = bottomRightNeighbor(btmLeft,3);
        drawRandomVerticalHexes(Hex,btm2,4,3);

        Position btm3 = bottomRightNeighbor(btm2,3);
        drawRandomVerticalHexes(Hex,btm3,5,3);

        Position btm4 = topRightNeighbor(btm3,3);
        drawRandomVerticalHexes(Hex,btm4,4,3);

        Position btmRight = topRightNeighbor(btm4,3);
        drawRandomVerticalHexes(Hex,btmRight,3,3);

        // draws the world to the screen
        ter.renderFrame(Hex);

    }

    /**
     * author: Ningxiao Tang
     * date: 2019.12.12
     * finished drawHexgon implementation
    public static void addHexagon(TETile[][] tiles, int size){


        int mid = size + 2*(size-1);
        int off = size-1;
        int i = size;
        for(int y = 0; y < 2*size; y++){
            if(y < size){
                //System.out.printf("y<size, off, i :%d %d\n",off,i);
                for(int x = off; x< mid; x++){
                    if (x < off+i)
                        tiles[x][y] = Tileset.TREE;
                }
                off--; i+=2;

            }
            else{
                off ++; i-=2;
                //System.out.printf("size<= y <= mid, off, i: %d %d\n",off,i);
                for(int x = off; x >=0; x++){
                    if (x < off+i)
                        tiles[x][y] = Tileset.TREE;
                }

            }
        }
    }

    */
    /**
     * test drawHexgon algorithm
    private static void printHex(int n){
        for(int i = 0; i < n; i++)
            System.out.print("a");
    }
    private static void offset(int x){
        while(x != 0){
            System.out.print(" ");
            x--;
        }
    }
    private static void printRow(int i, int off){
        offset(off);
        printHex(i);
        offset(off);
        System.out.println();
    }*/

}
