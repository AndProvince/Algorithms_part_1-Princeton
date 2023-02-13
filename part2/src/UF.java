/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */
public class UF {
    private int[] id;
    private int[] sz;

    public UF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]]; // change to root node
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (sz[i] < sz[j]) {
            sz[j] = i;
            sz[j] += sz[i];
        }
        else {
            sz[i] = j;
            sz[i] += sz[j];
        }
    }

    public static class Percolation {
        private int[][] map;
        private int[] id;
        private int[] sz;
        private int n;
        private int top;
        private int botom;

        // creates n-by-n grid, with all sites initially blocked
        public Percolation(int n) {
            if (n <= 0)
                throw new IllegalArgumentException("N must be greater than zero");

            this.n = n;
            top = this.n * this.n;
            botom = this.n * this.n + 1;
            map = new int[n][n];
            id = new int[n * n + 2];
            sz = new int[n * n + 2];

            for (int i = 0; i < n * n; i++) {
                id[i] = i;
                sz[i] = 1;
                map[i / n][i % n] = 0;
            }

            id[n * n] = top;
            id[n * n + 1] = botom;
            sz[n * n] = 1;
            sz[n * n + 1] = 1;
        }

        private void union(int p, int q) {
            int i = root(p);
            int j = root(q);
            if (sz[i] < sz[j]) {
                id[j] = i;
                sz[j] += sz[i];
            }
            else {
                id[i] = j;
                sz[i] += sz[j];
            }
        }

        private int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // change to root node
                i = id[i];
            }
            return i;
        }

        // opens the site (row, col) if it is not open already
        public void open(int row, int col) {
            if ((row <= 0) || (row > n) || (col <= 0) || (col > n))
                throw new IllegalArgumentException("Illegal row and col values");
            row -= 1;
            col -= 1;
            map[row][col] = 1;
            if ((row > 0) && isOpen(row, col + 1))
                union(row * n + col, (row - 1) * n + col);
            if ((row < n - 1) && isOpen(row + 2, col + 1))
                union(row * n + col, (row + 1) * n + col);
            if ((col > 0) && isOpen(row + 1, col))
                union(row * n + col, row * n + (col - 1));
            if ((col < n - 1) && isOpen(row + 1, col + 2))
                union(row * n + col, row * n + (col + 1));

            if (row == 0) union(col, top);
            if (row == n - 1) union(row * n + col, botom);
        }

        // is the site (row, col) open?
        public boolean isOpen(int row, int col) {
            if ((row <= 0) || (row > n) || (col <= 0) || (col > n))
                throw new IllegalArgumentException("Illegal row and col values");
            row -= 1;
            col -= 1;
            return map[row][col] == 1;
        }

        // is the site (row, col) full?
        public boolean isFull(int row, int col) {
            if ((row <= 0) || (row > n) || (col <= 0) || (col > n))
                throw new IllegalArgumentException("Illegal row and col values");
            row -= 1;
            col -= 1;
            int index = row * n + col;
            return root(index) == root(top);
        }

        // returns the number of open sites
        public int numberOfOpenSites() {
            int cnt = 0;
            for (int i = 0; i < n * n; i++) {
                cnt += map[i / n][i % n];
            }
            return cnt;
        }

        // does the system percolate?
        public boolean percolates() {
            return root(top) == root(botom);
        }

        public static void main(String[] args) {

        }
    }
}
