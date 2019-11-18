import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;

/******************************************************************************
 *  Author:  Huang Zhiyuan
 *  Date:    27th Dec, 2018
 *
 *  What do I learn after I finish: Learn what is A* algorithm, and how to implement it
 *  using priority queue data structure. And know how to use priority queue based on
 *  the comparator. Know the importance of comparator in Java!
 ******************************************************************************/

public class Solver {

    private Stack<Board> solution;
    private boolean isSolvable;
    private int move;

    private static class SearchNode {
        Board board;
        int move;
        SearchNode predecessor;

        SearchNode(Board board, int move, SearchNode predecessor) {
            this.board = board;
            this.move = move;
            this.predecessor = predecessor;
        }
    }


    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Invalid input");

        solution = new Stack<>();
        MinPQ<SearchNode> gameTree = new MinPQ<>(new ManNO());
        MinPQ<SearchNode> twinTree = new MinPQ<>(new ManNO());
        MinPQ<SearchNode> gameTem = new MinPQ<>(new ManNO());
        MinPQ<SearchNode> twinTem = new MinPQ<>(new ManNO());


        if (initial.isGoal()) {
            isSolvable = true;
            move = 0;
            solution.push(initial);
            return;
        } else if (initial.twin().isGoal())
            isSolvable = false;
        else {
            SearchNode init = new SearchNode(initial, 0, null);
            for (Board b : initial.neighbors())
                gameTree.insert(new SearchNode(b, 1, init));
            for (Board c : initial.twin().neighbors())
                twinTree.insert(new SearchNode(c, 1, init));
        }

        while (!gameTree.min().board.isGoal() && !twinTree.min().board.isGoal()) {
            SearchNode t;

            do {
                t = gameTree.delMin();
                gameTem.insert(t);
            }
            while (!gameTree.isEmpty() && t.board.manhattan() + t.move == gameTree.min().move + gameTree.min().board.manhattan());

            for (SearchNode s : gameTem) {
                for (Board q : s.board.neighbors()) {
                    if (!q.equals(s.predecessor.board))
                        gameTree.insert(new SearchNode(q, s.move + 1, s));
                }
            }
            while (!gameTem.isEmpty())
                gameTem.delMin();

            //do the same thing to the twin of the initial blocks
            do {
                t = twinTree.delMin();
                twinTem.insert(t);
            }
            while (!twinTree.isEmpty() && t.board.manhattan() + t.move == twinTree.min().move + twinTree.min().board.manhattan());

            for (SearchNode s : twinTem) {
                for (Board q : s.board.neighbors()) {
                    if (!q.equals(s.predecessor.board))
                        twinTree.insert(new SearchNode(q, s.move + 1, s));
                }
            }

            while (!twinTem.isEmpty())
                twinTem.delMin();
        }

        if (gameTree.min().board.isGoal()) {
            SearchNode sequence = gameTree.min();
            solution.push(sequence.board);
            while (sequence.predecessor != null) {
                solution.push(sequence.predecessor.board);
                sequence = sequence.predecessor;
            }

            isSolvable = true;
            move = gameTree.min().move;
        } else {
            solution = null;
            move = -1;
            isSolvable = false;
        }


    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return isSolvable;
    } // is the initial board solvable?

    public int moves() {
        return move;
    } // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        return solution;
    } // sequence of boards in a shortest solution; null if unsolvable

    private static final class ManNO implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int com = (o1.board.manhattan() + o1.move) - (o2.board.manhattan() + o2.move);
            if (com != 0) return com;
            else return o1.board.manhattan() - o2.board.manhattan();
        }
    }

    public static void main(String[] args) {
        int[][] blocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board initial1 = new Board(blocks);
        //System.out.println(a.twin());
        int[][] blocks3 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board initial2 = new Board(blocks3);
        //System.out.println(a.neighbors());
        int[][] blocks1 = {{8, 6, 7}, {2, 5, 4}, {3, 0, 1}};
        Board initial3 = new Board(blocks1);
        int[][] blocks2 = {{1, 0, 2}, {7, 5, 4}, {8, 6, 3}};
        Board initial4 = new Board(blocks2);
        int[][] blocks5 = {{5, 7, 4}, {3, 0, 8}, {1, 6, 2}};
        Board initial5 = new Board(blocks5);
        int[][] blocks6 = {{2, 3, 0, 8}, {15, 12, 6, 7}, {13, 1, 4, 9}, {14, 11, 10, 5}};
        Board initial = new Board(blocks6);
        MinPQ<SearchNode> list = new MinPQ<>(new ManNO());
        MinPQ<SearchNode> tem = new MinPQ<>(new ManNO());
        Stack<Board> solu = new Stack<>();
        int n = 1;

        SearchNode init = new SearchNode(initial, 0, null);
        for (Board b : initial.neighbors())
            list.insert(new SearchNode(b, 1, init));

        while (!list.min().board.isGoal()) {
            SearchNode t;

            do {
                t = list.delMin();
                tem.insert(t);
            }
            while (!list.isEmpty() && t.board.manhattan() + t.move == list.min().move + list.min().board.manhattan());

            for (SearchNode s : tem) {
                for (Board q : s.board.neighbors()) {
                    if (!q.equals(s.predecessor.board))
                        list.insert(new SearchNode(q, s.move + 1, s));
                }
            }

            while (!tem.isEmpty())
                tem.delMin();

            n++;
        }

        SearchNode sequence = list.min();
        solu.push(sequence.board);
        while (sequence.predecessor != null) {
            solu.push(sequence.predecessor.board);
            sequence = sequence.predecessor;
        }
        //System.out.println(solu);
        //System.out.println(list.min().board);
        //System.out.println(list.min().move);

        Solver solver = new Solver(initial);

        System.out.println(solver.solution);
        System.out.println(solver.move);
    }

}
