package com.webcheckers.model.board;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;

import java.util.*;

/**
 * a board piece. implements basic movement rules (e.g. move forward-diagonally, backward-diagonally) and single/king
 * state, though it doesn't implement those features in a way that's aware of the existence of other pieces.
 *
 * movement direction is influenced by color - so, "move forwards" moves forwards relative to the red player if the
 * piece is red, and forwards relative to the white player if the piece is white. this is useful for implementing
 * higher-level algorithms, since they only have to be implemented in one way to be valid for all colors.
 *
 * Rows 0, 1, and 2 are the red starting rows, and red pieces move in a way that increments the row count. Rows 5, 6,
 * and 7 are the white starting rows, and white pieces move in a way that decrements the row count.
 */
public class Piece {

    //   0 1 2 3 4 5 6 7 < col
    // 0 wwBBwwBBwwBBwwBB red starting row V
    // 1 BBwwBBwwBBwwBBww red starting row V
    // 2 wwBBwwBBwwBBwwBB red starting row V
    // 3 BBwwBBwwBBwwBBww
    // 4 wwBBwwBBwwBBwwBB
    // 5 BBwwBBwwBBwwBBww white starting row ^
    // 6 wwBBwwBBwwBBwwBB white starting row ^
    // 7 BBwwBBwwBBwwBBww white starting row ^
    // ^ row
    private final int row;
    private final int col;
    private  Type type;
    private final Color color;

    public static boolean isBlackSquare(int row, int col) {
        return (row + col) % 2 == 1;
    }

    /**
     * initialize a new piece
     * @param row the row to place the piece
     * @param col the column to place the piece
     * @param type whether the piece should be single or a king
     * @param color the color of the piece (red or white)
     * @throws InvalidPositionException thrown if the piece is either not on a black square, or not within the bounds of
     *                                  the board
     */
    public Piece(int row, int col, Type type, Color color) throws InvalidPositionException {
        this.row = row;
        this.col = col;
        this.type = Objects.requireNonNull(type);
        this.color = Objects.requireNonNull(color);

        if (!isBlackSquare(row, col)) {
            throw new InvalidPositionException(row, col, "piece must be on a black square");
        }
        if (row < 0 || 7 < row || col < 0 || 7 < col) {
            throw new InvalidPositionException(row, col, "piece must be within bounds of board");
        }
    }

    /**
     * @return the piece's current row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the piece's current column
     */
    public int getCol() {
        return col;
    }

    /**
     * @return whether the piece is single or a king
     */
    public Type getType() {
        return type;
    }

    /**
     * @return whether the piece is red or white
     */
    public Color getColor() {
        return color;
    }

    /**
     * do a forward-left diagonal move
     * @return empty if the move results in a piece that's in an invalid position, or a piece moved to the new position
     */
    public Optional<Piece> moveForwardLeft() {
        switch (color) {
            case WHITE:
                return move(-1, -1);
            default:
            case RED:
                return move(+1, +1);
        }
    }

    /**
     * do a forward-right diagonal move
     * @return empty if the move results in a piece that's in an invalid position, or a piece moved to the new position
     */
    public Optional<Piece> moveForwardRight() {
        switch (color) {
            case WHITE:
                return move(-1, +1);
            case RED:
            default:
                return move(+1, -1);
        }
    }

    /**
     * do a backward-left diagonal move. only works if the piece is a king
     * @return empty if the piece isn't a king move results in a piece that's in an invalid position, or a piece moved
     *         to the new position
     */
    public Optional<Piece> moveBackwardLeft() {
        switch (color) {
            case WHITE:
                return move(+1, -1);
            default:
            case RED:
                return move(-1, +1);
        }
    }

    /**
     * do a backward-right diagonal move. only works if the piece is a king
     * @return empty if the piece isn't a king move results in a piece that's in an invalid position, or a piece moved
     *         to the new position
     */
    public Optional<Piece> moveBackwardRight() {
        switch (color) {
            case WHITE:
                return move(+1, +1);
            case RED:
            default:
                return move(-1, -1);
        }
    }
    /**
     * do a forward-left diagonal jump
     * @return empty if the move results in a piece that's in an invalid position, or a piece moved to the new position
     */
    public Optional<Piece> jumpForwardLeft() {
        switch (color) {
            case WHITE:
                return move(-2, -2);
            default:
            case RED:
                return move(+2, +2);
        }
    }

    /**
     * do a forward-right diagonal jump
     * @return empty if the move results in a piece that's in an invalid position, or a piece moved to the new position
     */
    public Optional<Piece> jumpForwardRight() {
        switch (color) {
            case WHITE:
                return move(-2, +2);
            case RED:
            default:
                return move(+2, -2);
        }
    }

    /**
     * do a backward-left diagonal jump. only works if the piece is a king
     * @return empty if the piece isn't a king move results in a piece that's in an invalid position, or a piece moved
     *         to the new position
     */
    public Optional<Piece> jumpBackwardLeft() {
        switch (color) {
            case WHITE:
                return move(+2, -2);
            default:
            case RED:
                return move(-2, +2);
        }
    }

    /**
     * do a backward-right diagonal jump. only works if the piece is a king
     * @return empty if the piece isn't a king move results in a piece that's in an invalid position, or a piece moved
     *         to the new position
     */
    public Optional<Piece> jumpBackwardRight() {
        switch (color) {
            case WHITE:
                return move(+2, +2);
            case RED:
            default:
                return move(-2, -2);
        }
    }

    /**
     * @return the possible single-jump moves that this piece can make
     */
    public Iterable<Piece> getPossibleSingleMoves() {
        var moves = new ArrayList<Piece>();
        moveForwardLeft().ifPresent(moves::add);
        moveForwardRight().ifPresent(moves::add);
        moveBackwardLeft().ifPresent(moves::add);
        moveBackwardRight().ifPresent(moves::add);
        return moves;
    }

    /**
     * @return the possible single-jump moves that this piece can make
     */
    public Iterable<Piece> getPossibleSingleJumps() {
        var jumps = new ArrayList<Piece>();

        jumpForwardLeft().ifPresent(jumps::add);
        jumpForwardRight().ifPresent(jumps::add);
        jumpBackwardLeft().ifPresent(jumps::add);
        jumpBackwardRight().ifPresent(jumps::add);
        return jumps;
    }

    /**
     * move the piece to a new position, if the new position is valid
     * @param deltaRow row delta to be added to the current row
     * @param deltaCol column delta to be added to the current column
     * @return the new position
     */
    private Optional<Piece> move(int deltaRow, int deltaCol) {
        var newRow = row + deltaRow;
        var newCol = col + deltaCol;
        // make sure moves stay on the board
        if (newRow < 0 || 7 < newRow || newCol < 0 || 7 < newCol) {
            return Optional.empty();
        }
        // make sure only kings can move backwards
        // ...is that social commentary? probably not since the game is literally thousands of years old
        if (isSingle() && ((isWhite() && deltaRow > 0) || (isRed() && deltaRow < 0))) {
            return Optional.empty();
        }
        try {
            return Optional.of(new Piece(newRow, newCol, type, color));
        } catch (InvalidPositionException e) {
            // this is a private method and if this error gets thrown it indicates a bug in how the method
            // is being called internally.
            throw new Error(e);
        }
    }

    /**
     * @return whether the piece is single
     */
    public boolean isSingle() {
        return this.type == Type.SINGLE;
    }

    /**
     * @return whether the piece is a king
     */
    public boolean isKing() {
        return this.type == Type.KING;
    }

    /**
     * @return if the piece is colored red
     */
    public boolean isRed() {
        return this.color == Color.RED;
    }

    /**
     * @return if the piece is colored white
     */
    public boolean isWhite() {
        return this.color == Color.WHITE;
    }

    @Override
    public boolean equals(Object other) {
        Piece o;
        if (other instanceof Piece) {
            o = (Piece) other;
        } else {
            return false;
        }
        return row == o.row && col == o.col && color == o.color && type == o.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, type, color);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "row=" + row +
                ", col=" + col +
                ", type=" + type +
                ", color=" + color +
                '}';
    }

    /**
     * Promotes a single piece to a king
     */
    public void promotePiece(){
        this.type = Type.KING;
    }
}

