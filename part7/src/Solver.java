/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
        }

        public int compareTo(SearchNode that) {
            int thisMove = this.board.manhattan() + this.moves;
            int thatMove = that.board.manhattan() + that.moves;
            if (thisMove < thatMove) return -1;
            else if (thisMove > thatMove) return 1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<SearchNode> PQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        SearchNode Node = new SearchNode(initial);
        SearchNode twinNode = new SearchNode(initial.twin());
        PQ.insert(Node);
        twinPQ.insert(twinNode);


        while (!PQ.isEmpty() && !twinPQ.isEmpty()) {

            SearchNode min = PQ.delMin();
            SearchNode twinMin = twinPQ.delMin();

            if (min.board.isGoal()) {
                goal = min;
                break;
            }
            else goal = null;

            if (twinMin.board.isGoal()) {
                goal = null;
                break;
            }

            for (Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(
                        min.prev.board)) {   // check if move back this previous state
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.insert(n);
                }
            }
            for (Board b : twinMin.board.neighbors()) {
                if (twinMin.prev == null || !b.equals(twinMin.prev.board)) {
                    SearchNode n = new SearchNode(b);
                    n.moves = twinMin.moves + 1;
                    n.prev = twinMin;
                    twinPQ.insert(n);
                }
            }
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else return goal.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<>();
        for (SearchNode sn = goal; sn != null; sn = sn.prev) {
            stack.push(sn.board);
        }
        return stack;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
