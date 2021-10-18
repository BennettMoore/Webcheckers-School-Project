package com.webcheckers.model.board;

import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.util.Color;
import com.webcheckers.util.Type;

import java.util.*;
import java.util.logging.Logger;

/**
 * Current state of the game board. Is immutable - functions that move pieces return a new game board object.
 */
public class Board {
    private static final Logger LOG = Logger.getLogger(Board.class.getName());

    private final ArrayList<Piece> pieces;
    private final Color currentTurn;
    private Set<Move> possibleMoves;

    /**
     * Initialize a new game board with the pieces in the default configuration
     */
    public Board() {
        pieces = new ArrayList<>();
        try {
            // these are done as separate loops to ensure the list remains sorted by
            // piece row/col
            for (int i = 0; i < 3*8; i++) {
                int rowRed = i / 8;
                int col = i % 8;
                if (Piece.isBlackSquare(rowRed, col)) {
                    pieces.add(new Piece(rowRed, col, Type.SINGLE, Color.RED));
                }
            }
            for (int i = 0; i < 3*8; i++) {
                int rowWhite = i / 8 + 5;
                int col = i % 8;
                if (Piece.isBlackSquare(rowWhite, col)) {
                    pieces.add(new Piece(rowWhite, col, Type.SINGLE, Color.WHITE));
                }
            }
        } catch (InvalidPositionException e) {
            // this indicates a bug in the position generation code and should be handled by
            // a programmer
            throw new Error(e);
        }

        currentTurn = Color.RED;
    }

    private Board(ArrayList<Piece> pieces, Color currentTurn) {
        this.pieces = pieces;
        this.currentTurn = currentTurn;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }

    /**
     * get the index in the given piece array that a piece at the given position should be. if said piece doesn't exist,
     * return where it should be inserted
     */
    private static int getIndexForPiece(List<Piece> pieces, int row, int col) throws InvalidPositionException {
        var searchPiece = new Piece(row, col, Type.SINGLE, Color.RED);
        return getIndexForPiece(pieces, searchPiece);
    }

    /**
     * get the index in the given piece array that a piece. if said piece doesn't exist,
     * return where it should be inserted
     */
    private static int getIndexForPiece(List<Piece> pieces, Piece piece) {
        // the type and color don't actually matter because PiecePositionComparator
        // only compares by row and column
        var pieceIndex = Collections.binarySearch(pieces, piece,
                new PiecePositionComparator());
        return pieceIndex >= 0 ? pieceIndex : -pieceIndex - 1;
    }

    /**
     * get the piece at the given position.
     * @param row the row of the piece
     * @param col the column of the piece
     * @return the piece. returns empty if there's no piece at that position
     */
    public Optional<Piece> getPieceAtPosition(int row, int col) {
        try {
            var pieceIndex = getIndexForPiece(pieces, row, col);
            var pieceAtIndex = pieces.get(pieceIndex);
            if (pieceAtIndex.getRow() == row && pieceAtIndex.getCol() == col) {
                return Optional.of(pieceAtIndex);
            } else {
                return Optional.empty();
            }
        } catch (InvalidPositionException | IndexOutOfBoundsException e) {
            //Catch ensures that nothing is returned if the user asks for either an invalid row and column
            //or if the user asks for the last space in the array and there isn't a piece there
            return Optional.empty();
        }
    }

    /**
     * get the potentially valid moves, given which player is taking a turn and the state of the board
     * @return the set of all possible moves
     */
    public Set<Move> getPossibleMoves() {
        if (possibleMoves != null) {
            return possibleMoves;
        }

        var moves = new ArrayList<Move>();

        for (var piece : pieces) {
            if (piece.getColor() != currentTurn) continue;

            for(var move : getPossibleJumps(piece)) {
              LOG.fine("JUMP MOVE: " + move.toString());
            }

            // single jumps
            moves.addAll(getPossibleJumps(piece));
        }

        //Only calculate moves if there are no jumps
        if(moves.size() == 0) {
            for (var piece : pieces) {
                if (piece.getColor() != currentTurn) continue;

                // single-space moves
                for (var newPiece : piece.getPossibleSingleMoves()) {
                    if (getPieceAtPosition(newPiece.getRow(), newPiece.getCol()).isEmpty()) {
                        try {
                            LOG.fine("SINGLE MOVE: " + new Move(piece, newPiece).toString());
                            moves.add(new Move(piece, newPiece));
                        } catch (InvalidMoveException e) {
                            // this should never happen, since this is the function that calculates what a valid move is.
                            // if this gets thrown there's almost certainly a bug.
                            throw new Error(e);
                        }
                    }
                }
            }
        }

        possibleMoves = Set.of(moves.toArray(new Move[0]));
        return possibleMoves;
    }

    public Set<Move> getPossibleJumps(Piece piece){
        var jumps = new ArrayList<Move>();

        for(Piece newPiece : piece.getPossibleSingleJumps()) {
            int midRow = (piece.getRow() + newPiece.getRow())/2;
            int midCol = (piece.getCol() + newPiece.getCol())/2;
            Piece midPiece = getPieceAtPosition(midRow, midCol).orElse(null);

            //Attempt to add a valid jump move to the list
            if (midPiece != null && getPieceAtPosition(newPiece.getRow(), newPiece.getCol()).isEmpty() &&
                    midPiece.getColor() != currentTurn) {
                try {
                    jumps.add(new Move(piece, newPiece, midPiece));
                } catch (InvalidMoveException e) {
                    // this should never happen, since this is the function that calculates what a valid move is.
                    // if this gets thrown there's almost certainly a bug.
                    throw new Error(e);
                }
            }
        }
        return Set.of(jumps.toArray(new Move[0]));
    }

    /**
     * take the provided move, and return a new Board object with that move applies. does not mutate this board.
     * @param move the move to apply
     * @return the board with the move applied
     * @throws InvalidMoveException if the move can't be applied to the current board
     */
    public Board applyMove(Move move) throws InvalidMoveException {
        if (!getPossibleMoves().contains(move)) {
            throw new InvalidMoveException("move can't be applied to current board configuration");
        }

        var startPiece = move.getStart();
        var endPiece = move.getEnd();
        var jumpPiece = move.getJump();
        var newList = new ArrayList<Piece>(pieces);
        newList.remove(getIndexForPiece(newList, startPiece));
        newList.add(getIndexForPiece(newList, endPiece), endPiece);

        if(jumpPiece != null){
            newList.remove(getIndexForPiece(newList, jumpPiece));
            if(getPossibleJumps(endPiece).size() > 0){
                return new Board(newList, currentTurn);
            }
        }

        var nextTurn = currentTurn == Color.RED ? Color.WHITE : Color.RED;
        return new Board(newList, nextTurn);
    }



    /**
     * implements row/column-based comparisons between pieces
     */
    private static class PiecePositionComparator implements Comparator<Piece> {
        @Override
        public int compare(Piece o1, Piece o2) {
            if (o1.getRow() != o2.getRow()) {
                return Integer.compare(o1.getRow(), o2.getRow());
            } else if (o1.getCol() != o2.getCol()) {
                return Integer.compare(o1.getCol(), o2.getCol());
            } else {
                return 0;
            }
        }
    }

    /**
     * Promotes an individual checkers piece to a king
     * @param piece
     * @return a new board object with the promoted piece
     */
    public Board promotePiece(Piece piece){
        var newList = new ArrayList<Piece>(pieces);
        newList.remove(getIndexForPiece(newList, piece));
        piece.promotePiece();
        newList.add(getIndexForPiece(newList, piece), piece);
        //Already updated turn from applymove
        return new Board(newList, currentTurn);


    }
}
