import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

/******************************************************************************
 *  Author:  Huang Zhiyuan
 *  Date:    27th Dec, 2018
 *
 *  What do I learn after I finish: Learn what is iterable and how to use it.
 ******************************************************************************/


public class Board{

    private final int[] board;
    private final int dimension;

    public Board(int[][] blocks) {
        if(blocks == null)
            throw new IllegalArgumentException("No input");

        dimension = blocks.length;
        board = new int[dimension * dimension];

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                board[(i * dimension) + j] = blocks[i][j];
            }
        }
    } // construct a board from an n-by-n array of blocks
      // (where blocks[i][j] = block in row i, column j)

    public int dimension() {
        return dimension;
    } // board dimension n

    public int hamming() {
        int n = 0;
        for(int i = 0; i < board.length - 1; i++){
            if(board[i] != i + 1)
                n++;
        }
        return n;
    } // number of blocks out of place

    public int manhattan() {
        int n = 0;
        for(int i = 0; i < board.length; i++){
            if(board[i] != i + 1 && board[i] != 0){
                n += Math.abs((i / dimension) - (board[i] - 1) / dimension) + Math.abs((i % dimension) - (board[i] - 1) % dimension);
            }
        }
        return n;
    } // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        return hamming() == 0;
    } // is this board the goal board?

    public Board twin() {

        int[] board2 = new int[board.length];

        for(int i = 0; i < board.length; i++)
            board2[i] = board[i];

        for(int j = 0; j < board2.length - 1; j++){
            if(board2[j] !=0){
                if(board2[j + 1] != 0){
                    int e = board2[j];
                    board2[j] = board2[j + 1];
                    board2[j + 1] = e;
                }else{
                    int t = board2[j];
                    board2[j] = board2[j + 2];
                    board2[j + 2] = t;
                } break;
            }else{
                int o = board2[j + 1];
                board2[j + 1] = board2[j + 2];
                board2[j + 2] = o;
                break;
            }
        }
        return new Board(get2dArray(board2));
    } // a board that is obtained by exchanging any pair of blocks

    private int[][] get2dArray(int[] array){
        int[][] res = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                res[i][j] = array[i * dimension + j];
            }
        }
        return res;
    }

    public boolean equals(Object y) {// this may look wired, but this is java features.
        if(this == y) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.equals(this.board, that.board);
    } // does this board equal y?

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int n = 0;

        for(int i = 0; i < board.length; i++){
            if(board[i] == 0)
                n = i;
        }
        int[] board2 = new int[board.length];

        if(n - dimension >= 0){

            for(int i = 0; i < board.length; i++)
                board2[i] = board[i];

            board2[n] = board2[n - dimension];
            board2[n - dimension] = 0;
            neighbors.push(new Board(get2dArray(board2)));
        }

        if(n + dimension < board.length){

            for(int i = 0; i < board.length; i++)
                board2[i] = board[i];

            board2[n] = board2[n + dimension];
            board2[n + dimension] = 0;
            neighbors.push(new Board(get2dArray(board2)));
        }

        if(n % dimension != 0){

            for(int i = 0; i < board.length; i++)
                board2[i] = board[i];

            board2[n] = board2[n - 1];
            board2[n - 1] = 0;
            neighbors.push(new Board(get2dArray(board2)));
        }

        if((n + 1) % dimension != 0){

            for(int i = 0; i < board.length; i++)
                board2[i] = board[i];

            board2[n] = board2[n + 1];
            board2[n + 1] = 0;
            neighbors.push(new Board(get2dArray(board2)));
        }
        return neighbors;
    } // all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        //s.append(dimension + "\n");
        for (int i = 0; i < board.length; i++) {
            s.append(String.format("%2d ", board[i]));
        }
        s.append("\n");
        return s.toString();
    } // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    } // unit tests (not graded)

}
