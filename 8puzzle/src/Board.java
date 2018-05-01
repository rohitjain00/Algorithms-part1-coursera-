import java.util.*;

public class Board {
    private int[][] board;
    private int boardSize;
    private int moves = 0;
    private int outOfPlace;
    private ArrayList<Board> neighbourBoards;
    private int xyTo1D(int x, int y) {
        return (x * boardSize) + y + 1;
    }

    public Board(int[][] blocks) {
        boardSize = blocks.length;
        board = new int[this.boardSize][this.boardSize];
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = blocks[i][j];
            }
        }

    }          // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return boardSize;
    }                 // board dimension N
    public int hamming() {
        outOfPlace = 0;
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != xyTo1D(i,j)) {
                    outOfPlace += 1;
                }
            }
        }
        return outOfPlace+moves;
    }                  // number of blocks out of place
    public int manhattan() {
        int totalManhattanDistances = 0;
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(board[i][j] != xyTo1D(i,j)){
                    int x = Math.round(board[i][j]/boardSize);
                    int y = board[i][j] % boardSize;
                    totalManhattanDistances += x + y;
                }
            }
        }
        return totalManhattanDistances + moves;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return outOfPlace == 0;
    }               // is this board the goal board?

    public Board twin() {

        int[][] newBoardBlocks = new int[this.boardSize][this.boardSize];
        int x = 0,y = 0;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j] == 0) {
                    x = i;
                    y = j;
                }
                newBoardBlocks[i][j] = this.board[i][j];
            }
        }

        boolean moveMade = false;
        while (!moveMade) { // move any direction that is possible
            Random rn = new Random();
            int i = (rn.nextInt() % 4) + 1;

            if (i == 1) { // MOVE LEFT
                if (x-1 >= 0) {
                    int temp = newBoardBlocks[x - 1][y];
                    newBoardBlocks[x - 1][y] = 0;
                    newBoardBlocks[x][y] = temp;
                    moveMade = true;
                    moves ++;
                }
            } else if(i == 2){ // MOVE UP
                if (y-1 >= 0) {
                    int temp = newBoardBlocks[x - 1][y];
                    newBoardBlocks[x - 1][y] = 0;
                    newBoardBlocks[x][y] = temp;
                    moveMade = true;
                    moves ++;
                }
            } else if (i == 3) { // MOVE RIGHT
                if (x + 1 < this.boardSize) {
                    int temp = newBoardBlocks[x + 1][y];
                    newBoardBlocks[x + 1][y] = 0;
                    newBoardBlocks[x][y] = temp;
                    moveMade = true;
                    moves ++;
                }
            } else if (i == 4){
                if (y+1 < this.boardSize) {
                    int temp = newBoardBlocks[x - 1][y];
                    newBoardBlocks[x - 1][y] = 0;
                    newBoardBlocks[x][y] = temp;
                    moveMade = true;
                    moves ++;
                }
            }
        }
        Board latestBoard = new Board(newBoardBlocks);
        neighbourBoards.add(latestBoard);
        return latestBoard;


    }
    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        return (this == y);
    }       // does this board equal y?


    public Iterable<Board> neighbors() {
        return () -> neighbourBoards.iterator();

    }     // all neighboring boards
    public String toString() {
        StringBuilder toReturn = new StringBuilder(Integer.toString(this.boardSize) + "\n");
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                toReturn.append(this.board[i][j]);
            }
            toReturn.append("\n");
        }
        return toReturn.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args){

    } // unit tests (not graded)
}