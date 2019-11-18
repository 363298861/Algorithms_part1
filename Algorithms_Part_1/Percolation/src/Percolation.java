import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF grid;
    private Boolean[][] openGrid;
    private int numOfOpenSites;
    private int num;

    public Percolation(int n) {
        if(n <= 0)
            throw new IllegalArgumentException("n < 0");

        num = n;
        grid = new WeightedQuickUnionUF(n * n + 2);
        openGrid = new Boolean[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++)
                openGrid[i][j] = false;
        }

        for(int i = 1; i <= n; i++) {
            grid.union(0, i);
            grid.union(n * n + 1, n * n + 1 - i);
        }
    } // create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        isValid(row, col);
        if(!isOpen(row, col)) {

            openGrid[row - 1][col - 1] = true;
            numOfOpenSites++;

            if (row == 1) {
                if (isOpen(2, col))
                    grid.union(col, col + num);
            } else if (row == num) {
                if (isOpen(num - 1, col))
                    grid.union((row - 1) * num + col, (row - 2) * num + col);
            } else if (col == 1) {
                if (isOpen(row - 1, col))
                    grid.union((row - 2) * num + 1, (row - 1) * num + 1);
                if (isOpen(row + 1, col))
                    grid.union((row - 1) * num + 1, row * num + 1);
                if (isOpen(row, col + 1))
                    grid.union((row - 1) * num + 1, (row - 1) * num + 2);
            } else if (col == num) {
                if (isOpen(row - 1, col))
                    grid.union((row - 1) * num, row * num);
                if (isOpen(row + 1, col))
                    grid.union((row + 1) * num, row * num);
                if (isOpen(row, col - 1))
                    grid.union((row - 1) * num + col, (row - 1) * num + col - 1);
            } else {
                if (isOpen(row - 1, col))
                    grid.union((row - 2) * num + col, (row - 1) * num + col);
                if (isOpen(row + 1, col))
                    grid.union((row - 1) * num + col, row * num + col);
                if (isOpen(row, col + 1))
                    grid.union((row - 1) * num + col, (row - 1) * num + col + 1);
                if (isOpen(row, col - 1))
                    grid.union((row - 1) * num + col, (row - 1) * num + col - 1);
            }
        }

    }   // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return openGrid[row - 1][col - 1];
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        isValid(row, col);
        return grid.connected(0, (row - 1) * num + col ) && isOpen(row, col);
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }      // number of open sites

    public boolean percolates() {
        return grid.connected(0, num * num + 1);
    } // does the system percolate?

    private void isValid(int row, int col){
        if(row <= 0 || col <= 0 || row > num || col > num)
            throw new IllegalArgumentException("Illegal argument");
    }

}
