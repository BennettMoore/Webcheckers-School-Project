package com.webcheckers.model.board;

import java.util.Objects;

public class Move {
    private final Piece start;
    private final Piece end;
    private final Piece jump;

    /**
     * initialize a new move object
     * @param start the starting position of the piece to be moved
     * @param end the ending position of the move
     */
    public Move(Piece start, Piece end) throws InvalidMoveException {
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
        jump = null;

        if (start.getColor() != end.getColor()) {
            throw new InvalidMoveException("piece changed color during moves");
        }
    }

    /**
     * initialize a new move object
     * @param start the starting position of the piece to be moved
     * @param end the ending position of the move
     * @param jump the piece being jumped over
     */
    public Move(Piece start, Piece end, Piece jump) throws InvalidMoveException {
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
        this.jump = Objects.requireNonNull(jump);

        if (start.getColor() != end.getColor()) {
            throw new InvalidMoveException("piece changed color during moves");
        }
        if (start.getColor() == jump.getColor()){
            throw new InvalidMoveException("user attempted to jump over friendly piece");
        }
    }

    public Piece getStart() {
        return start;
    }

    public Piece getEnd() {
        return end;
    }

    public Piece getJump() {
        return jump;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return start.equals(move.start) &&
                end.equals(move.end) && Objects.equals(jump, move.jump);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, jump);
    }

    @Override
    public String toString() {
        // Start: [#, #], End: [#, #]
        var start = "[" + this.start.getRow() + ", " + this.start.getCol() + "]";
        var end = "[" + this.end.getRow() + ", " + this.end.getCol() + "]";
        return "Start: " + start + ", End: " + end;
    }
}
