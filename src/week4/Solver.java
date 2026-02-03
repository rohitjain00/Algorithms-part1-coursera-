package week4;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;


public class Solver {
    private SearchNode goal;

    private static class SearchNode {
        private int moves;
        private Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            this.moves = 0;
            this.prev = null;
            this.board = initial;
        }
    }

    public Solver(Board initial)  {
        if (initial == null) throw new IllegalArgumentException("Board cannot be null");
        PriorityOrder order = new PriorityOrder();

        MinPQ<SearchNode> pq = new MinPQ<>(order);
        SearchNode node = new SearchNode(initial);
        pq.insert(node);

        MinPQ<SearchNode> twinPQ = new MinPQ<>(order);
        SearchNode twinNode = new SearchNode(initial.twin());
        twinPQ.insert(twinNode);

        SearchNode min = pq.delMin();
        SearchNode twinMin = twinPQ.delMin();

        while (!min.board.isGoal() && !twinMin.board.isGoal()) {
            for (Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(min.prev.board)) {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    pq.insert(n);
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

            if (pq.isEmpty()) break;
            min = pq.delMin();

            if (twinPQ.isEmpty()) break;
            twinMin = twinPQ.delMin();
        }

        if (min.board.isGoal()) goal = min;
        else goal = null;

    }         // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return goal != null;
    }           // is the initial board solvable?

    public int moves() {
        if (!isSolvable()) return -1;
        else return goal.moves;
    }                    // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (!isSolvable())  return null;
        Stack<Board> s = new Stack<Board>();
        for (SearchNode n = goal; n != null; n = n.prev)
            s.push(n.board);
        return s;
    }

    private static class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.moves + a.board.manhattan();
            int pb = b.moves + b.board.manhattan();
            return Integer.compare(pa, pb);
        }
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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
    } // solve a slider puzzle (given below)
}