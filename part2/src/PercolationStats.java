/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] res;
    private int t;
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0))
            throw new IllegalArgumentException("N and T must be greater than zero");
        t = trials;
        res = new double[t];

        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int gen = StdRandom.uniformInt(n * n);
                int row = gen / n + 1;
                int col = gen % n + 1;

                if (!p.isOpen(row, col))
                    p.open(row, col);
            }
            double cnt = p.numberOfOpenSites();
            res[i] = cnt / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(res);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(res);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        return m - (CONFIDENCE_95 * stddev() / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        return m + (CONFIDENCE_95 * stddev() / Math.sqrt(t));
    }

    // test client
    public static void main(String[] args) {
        int inpN = Integer.parseInt(args[0]);
        int inpT = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(inpN, inpT);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", "
                        + ps.confidenceHi() + "]");
    }
}
