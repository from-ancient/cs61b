package game2048;

import javax.swing.border.Border;
import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }
    /* ?????? row ?????????????????????tile, ?????????????????? null*/
    public Tile findTileBelow(int row, int col) {
        // row range 3 2 1 not include 0
        for (int j = row - 1; j >= 0; j --)
            if (board.tile(col, j) != null)
                return  board.tile(col, j);
        return null;
    }
    /* ???????????? row, ????????????????????? changed*/
    public boolean dealWithOneRow(int col) {
        // Tile t = board.tile(col,  3, 2, 1);
        boolean isChanged = false;
        for (int row = board.size() - 1; row > 0; row--) {
            // ??????col????????????????????????tile
            Tile next = findTileBelow(row, col);
            if (next == null)
                break; // ???????????????????????????????????? ???col????????????

            Tile cur = board.tile(col, row);
            if (cur == null) {
                // 1. ???????????? cur ??? null??? ????????????(not null)??????next?????????????????????
                assert board.tile(col, row) == null;
                boolean ret = board.move(col, row, next);
                assert !ret;
                assert board.tile(col, row) != null;
                row ++; // ??????????????????
                isChanged = true;
            } else if (cur.value() == next.value()) {
                // 2. cur != null and next != null and cur.value == next.value
                score += 2 * next.value();
                boolean ret =  board.move(col, row, next);
                assert  ret;
                isChanged = true;
            } else if (cur.value() != next.value()) {
                // 3. cur != null and next != null and cur.value != next.value
                if (board.tile(col, row - 1) != next) {
                    boolean ret = board.move(col, row - 1, next);
                    assert !ret;
                    isChanged = true;
                }
            }
        }
        return isChanged;
    }
    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        // TODO: Modify this.board (and perhaps this.score) to account
        System.out.println("Begin Side is " + side);
        if (side != Side.NORTH)
            board.setViewingPerspective(side);
        for (int i = 0; i < board.size(); i++) {
            if (dealWithOneRow(i))
                changed = true;
        }
        if (side != Side.NORTH)
            board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        System.out.println("End Side is " + side);
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) != null && b.tile(i, j).value() == MAX_PIECE)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static  boolean checkAdjTile(int col1, int row1, int col2, int row2, Board b) {
        return b.tile(col1, row1).value() == b.tile(col2, row2).value();
    }
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        /* 1. There is at least one empty space on the board. */
        if (emptySpaceExists(b)) {
            return true;
        }
        /* 2.There are two adjacent tiles with the same value. */
        // 1) from bottom to top
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size() - 1; j++) {
                if (checkAdjTile(i, j, i, j + 1, b))
                    return true;
            }
        }
        // 2) from left to right
        for (int i = 0; i < b.size() - 1; i++) {
            for (int j = 0; j < b.size(); j++) {
                if (checkAdjTile(i, j, i + 1, j, b))
                    return true;
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model???s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
