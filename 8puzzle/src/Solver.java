import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.Stack;


public class Solver {
    private SearchNode goal;

    private class SearchNode {
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
        // A* algorithm
        //// A* Search Algorithm
        //1.  Initialize the open list
        //2.  Initialize the closed list
        //    put the starting node on the open
        //    list (you can leave its f at zero)
        //
        //3.  while the open list is not empty
        //    a) find the node with the least f on
        //       the open list, call it "q"
        //
        //    b) pop q off the open list
        //
        //    c) generate q's 8 successors and set their
        //       parents to q
        //
        //    d) for each successor
        //        i) if successor is the goal, stop search
        //          successor.g = q.g + distance between
        //                              successor and q
        //          successor.h = distance from goal to
        //          successor (This can be done using many
        //          ways, we will discuss three heuristics-
        //          Manhattan, Diagonal and Euclidean
        //          Heuristics)
        //
        //          successor.f = successor.g + successor.h
        //
        //        ii) if a node with the same position as
        //            successor is in the OPEN list which has a
        //           lower f than successor, skip this successor
        //
        //        iii) if a node with the same position as
        //            successor  is in the CLOSED list which has
        //            a lower f than successor, skip this successor
        //            otherwise, add  the node to the open list
        //     end (for loop)
        //
        //    e) push q on the closed list
        //    end (while loop)
        // 1st step
        PriorityOrder order = new PriorityOrder();

        MinPQ<SearchNode> PQ = new MinPQ<>(order);
        SearchNode Node = new SearchNode(initial);
        PQ.insert(Node);

        MinPQ<SearchNode> twinOfPQ = new MinPQ<>(order);
        SearchNode twinNode = new SearchNode(initial);
        twinOfPQ.insert(twinNode);

        SearchNode min = PQ.delMin();
        SearchNode twinMin = twinOfPQ.delMin();

        while (!min.board.isGoal() && !twinMin.board.isGoal()) {
            for(Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(min.prev.board)) {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.insert(n);
                }
            }
            for(Board b : twinMin.board.neighbors()) {
                if (twinMin.prev == null || !b.equals(twinMin.prev.board)){
                    SearchNode n = new SearchNode(b);
                    n.moves = twinMin.moves + 1;
                    n.prev = twinMin;
                    twinOfPQ.insert(n);
                }
            }
            if (!PQ.isEmpty()) {
                min = PQ.delMin();
            }
            if (!twinOfPQ.isEmpty()) {
                twinMin = twinOfPQ.delMin();
            }
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
    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.board.manhattan();
            int pb = b.board.manhattan();
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