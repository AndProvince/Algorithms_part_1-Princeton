/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int n;
    private int i0, j0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = copyArray(tiles);
    }

    private int[][] copyArray(int[][] a) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                copy[i][j] = a[i][j];
                if (a[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        return copy;
    }

    // string representation of this board
    public String toString() {
        String ans = "";
        ans += Integer.toString(n) + "\n";
        for (int i = 0; i < n; i++) {
            String line = "";
            for (int j = 0; j < n; j++)
                line += " " + Integer.toString(tiles[i][j]) + " ";
            ans += line + "\n";
        }
        return ans;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int ans = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if ((tiles[i][j] != i * n + j + 1) && (tiles[i][j] != 0))
                    ans++;
        return ans;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int ans = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if ((tiles[i][j] != i * n + j + 1) && (tiles[i][j] != 0)) {
                    int gi = (tiles[i][j] - 1) / n;
                    int gj = (tiles[i][j] - 1) % n;
                    ans += Math.abs(i - gi) + Math.abs(j - gj);
                }
        return ans;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if ((tiles[i][j] != i * n + j + 1) && (tiles[i][j] != 0))
                    return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        if (this.n != that.n) return false;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neibrs = new LinkedList<>();
        if (i0 > 0) {
            int[][] neibrTiles = copyArray(tiles);
            neibrTiles[i0 - 1][j0] = 0;
            neibrTiles[i0][j0] = tiles[i0 - 1][j0];
            neibrs.add(new Board(neibrTiles));
        }
        if (i0 < n - 1) {
            int[][] neibrTiles = copyArray(tiles);
            int dd = tiles[i0 + 1][j0];
            neibrTiles[i0 + 1][j0] = 0;
            neibrTiles[i0][j0] = dd;
            neibrs.add(new Board(neibrTiles));
        }
        if (j0 > 0) {
            int[][] neibrTiles = copyArray(tiles);
            neibrTiles[i0][j0 - 1] = 0;
            neibrTiles[i0][j0] = tiles[i0][j0 - 1];
            neibrs.add(new Board(neibrTiles));
        }
        if (j0 < n - 1) {
            int[][] neibrTiles = copyArray(tiles);
            neibrTiles[i0][j0 + 1] = 0;
            neibrTiles[i0][j0] = tiles[i0][j0 + 1];
            neibrs.add(new Board(neibrTiles));
        }

        return neibrs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyArray(tiles);
        if ((twinTiles[0][0] != 0) && (twinTiles[n - 1][n - 1] != 0)) {
            int swap = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[n - 1][n - 1];
            twinTiles[n - 1][n - 1] = swap;
        }
        else {
            int swap = twinTiles[0][n - 1];
            twinTiles[0][n - 1] = twinTiles[n - 1][0];
            twinTiles[n - 1][0] = swap;
        }
        return new Board(twinTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[][] t = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                t[i][j] = i * n + j;
            }
        // int[][] tt = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board b = new Board(t);
        StdOut.println(b.toString());

        StdOut.println("Haming: " + b.hamming());
        StdOut.println("Manhattan: " + b.manhattan());

        for (Board ne : b.neighbors())
            StdOut.println(ne.toString());

    }
}
