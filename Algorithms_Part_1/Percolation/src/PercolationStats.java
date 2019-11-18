import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {

    private Percolation[] percolations;
    private double[] thresholds;
    private double T;

    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        percolations = new Percolation[trials];
        thresholds = new double[trials];
        T = Math.sqrt(trials);

        for(int i = 0; i < trials; i++) {
            percolations[i] = new Percolation(n);

            while(!percolations[i].percolates()){
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolations[i].open(row, col);
            }
            thresholds[i] = ((double) percolations[i].numberOfOpenSites()) / ((double) (n * n));
        }
    }// perform trials independent experiments on an n-by-n grid

    public double mean() {
        return StdStats.mean(thresholds);
    }// sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(thresholds);
    }// sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - (1.96 / T) ;
    }// low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + (1.96 / T) ;
    }// high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(m, n);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [ " +
                percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() +" ]" );
    }
}
