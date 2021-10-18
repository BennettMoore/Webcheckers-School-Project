package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

import java.util.ArrayList;
import java.util.Optional;

@Tag("Model-tier")
public class BoardTest {
    // verify that the board, when created, will setup the pieces on the board properly
    @Test
    public void testBoardInitialize() {
        var board = new Board();

        // verify that the white squares are empty
        assertEquals(Optional.empty(), board.getPieceAtPosition(0, 0));
        assertEquals(Optional.empty(), board.getPieceAtPosition(0, 2));
        assertEquals(Optional.empty(), board.getPieceAtPosition(0, 4));
        assertEquals(Optional.empty(), board.getPieceAtPosition(0, 6));
        assertEquals(Optional.empty(), board.getPieceAtPosition(1, 1));
        assertEquals(Optional.empty(), board.getPieceAtPosition(1, 3));
        assertEquals(Optional.empty(), board.getPieceAtPosition(1, 5));
        assertEquals(Optional.empty(), board.getPieceAtPosition(1, 7));
        assertEquals(Optional.empty(), board.getPieceAtPosition(2, 0));
        assertEquals(Optional.empty(), board.getPieceAtPosition(2, 2));
        assertEquals(Optional.empty(), board.getPieceAtPosition(2, 4));
        assertEquals(Optional.empty(), board.getPieceAtPosition(2, 6));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 1));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 3));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 5));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 7));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 0));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 2));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 4));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 6));
        assertEquals(Optional.empty(), board.getPieceAtPosition(5, 1));
        assertEquals(Optional.empty(), board.getPieceAtPosition(5, 3));
        assertEquals(Optional.empty(), board.getPieceAtPosition(5, 5));
        assertEquals(Optional.empty(), board.getPieceAtPosition(5, 7));
        assertEquals(Optional.empty(), board.getPieceAtPosition(6, 0));
        assertEquals(Optional.empty(), board.getPieceAtPosition(6, 2));
        assertEquals(Optional.empty(), board.getPieceAtPosition(6, 4));
        assertEquals(Optional.empty(), board.getPieceAtPosition(6, 6));
        assertEquals(Optional.empty(), board.getPieceAtPosition(7, 1));
        assertEquals(Optional.empty(), board.getPieceAtPosition(7, 3));
        assertEquals(Optional.empty(), board.getPieceAtPosition(7, 5));
        assertEquals(Optional.empty(), board.getPieceAtPosition(7, 7));

        // verify that the center black rows are empty
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 0));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 2));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 4));
        assertEquals(Optional.empty(), board.getPieceAtPosition(3, 6));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 1));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 3));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 5));
        assertEquals(Optional.empty(), board.getPieceAtPosition(4, 7));

        // verify that the red pieces are where they should be
        assertEquals(Optional.of(PieceTest.redSinglePiece(0, 1)), board.getPieceAtPosition(0, 1));
        assertEquals(Optional.of(PieceTest.redSinglePiece(0, 3)), board.getPieceAtPosition(0, 3));
        assertEquals(Optional.of(PieceTest.redSinglePiece(0, 5)), board.getPieceAtPosition(0, 5));
        assertEquals(Optional.of(PieceTest.redSinglePiece(0, 7)), board.getPieceAtPosition(0, 7));
        assertEquals(Optional.of(PieceTest.redSinglePiece(1, 0)), board.getPieceAtPosition(1, 0));
        assertEquals(Optional.of(PieceTest.redSinglePiece(1, 2)), board.getPieceAtPosition(1, 2));
        assertEquals(Optional.of(PieceTest.redSinglePiece(1, 4)), board.getPieceAtPosition(1, 4));
        assertEquals(Optional.of(PieceTest.redSinglePiece(1, 6)), board.getPieceAtPosition(1, 6));
        assertEquals(Optional.of(PieceTest.redSinglePiece(2, 1)), board.getPieceAtPosition(2, 1));
        assertEquals(Optional.of(PieceTest.redSinglePiece(2, 3)), board.getPieceAtPosition(2, 3));
        assertEquals(Optional.of(PieceTest.redSinglePiece(2, 5)), board.getPieceAtPosition(2, 5));
        assertEquals(Optional.of(PieceTest.redSinglePiece(2, 7)), board.getPieceAtPosition(2, 7));

        // verify that the white pieces are where they should be
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(5, 0)), board.getPieceAtPosition(5, 0));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(5, 2)), board.getPieceAtPosition(5, 2));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(5, 4)), board.getPieceAtPosition(5, 4));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(5, 6)), board.getPieceAtPosition(5, 6));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(6, 1)), board.getPieceAtPosition(6, 1));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(6, 3)), board.getPieceAtPosition(6, 3));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(6, 5)), board.getPieceAtPosition(6, 5));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(6, 7)), board.getPieceAtPosition(6, 7));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(7, 0)), board.getPieceAtPosition(7, 0));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(7, 2)), board.getPieceAtPosition(7, 2));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(7, 4)), board.getPieceAtPosition(7, 4));
        assertEquals(Optional.of(PieceTest.whiteSinglePiece(7, 6)), board.getPieceAtPosition(7, 6));

        assertEquals(Color.RED, board.getCurrentTurn());
    }

    // test the possible-move getter and move applicator
    @Test
    public void testPossibleMoves() {
        var board0 = new Board();

        var possibleMoves = board0.getPossibleMoves();
        assertEquals(7, board0.getPossibleMoves().size());
        assertEquals(Color.RED, board0.getCurrentTurn());
        for (var move : possibleMoves) {
            assertEquals(Color.RED, move.getStart().getColor());
            assertEquals(Color.RED, move.getEnd().getColor());
            assertEquals(2, move.getStart().getRow());
            assertEquals(3, move.getEnd().getRow());
        }

        assertThrows(InvalidMoveException.class, () -> {
            var move = new Move(PieceTest.redSinglePiece(1, 2), PieceTest.redSinglePiece(2, 3));
            board0.applyMove(move);
        });

        var board0Move = assertDoesNotThrow(() -> new Move(
                PieceTest.redSinglePiece(2, 1),
                PieceTest.redSinglePiece(3, 2)
        ));
        assertTrue(possibleMoves.contains(board0Move));

        var board1 = assertDoesNotThrow(() -> board0.applyMove(board0Move));
        possibleMoves = board1.getPossibleMoves();
        assertEquals(7, board1.getPossibleMoves().size());
        assertEquals(Color.WHITE, board1.getCurrentTurn());
        for (var move : possibleMoves) {
            assertEquals(Color.WHITE, move.getStart().getColor());
            assertEquals(Color.WHITE, move.getEnd().getColor());
            assertEquals(5, move.getStart().getRow());
            assertEquals(4, move.getEnd().getRow());
        }
    }

    // test whether jumps are correctly calculated when given a specific scenario
    @Test
    public void testPossibleJumps(){
        var board0 = new Board();
        Piece red23 = board0.getPieceAtPosition(2,3).orElse(null);
        assertNotNull(red23, "Red checker at (2,3) not found.");

        Piece white56 = board0.getPieceAtPosition(5,6).orElse(null);
        assertNotNull(white56, "White checker at (5,6) not found.");

        try {
            //Moving red(2,3) into position (3,4)
            board0 = board0.applyMove(new Move(red23,
                    new Piece(3,4, Type.SINGLE, Color.RED)));
            //Moving white(5,6) into position (4,5)
            board0 = board0.applyMove(new Move(white56,
                    new Piece(4,5, Type.SINGLE, Color.WHITE)));

            //Pieces are now capable of jumping one another
        }
        catch (Exception e){
            e.printStackTrace();
            fail("Exception triggered when setting up board.");
        }
        //Checking to make sure red's move was successful
        Piece red34 = board0.getPieceAtPosition(3,4).orElse(null);
        assertNotNull(red34, "Red checker at (3,4) not found. Either did not move or disappeared.");
        assertNull(board0.getPieceAtPosition(2,3).orElse(null));

        //Checking to make sure white's move was successful
        Piece white45 = board0.getPieceAtPosition(4,5).orElse(null);
        assertNotNull(white45, "White checker at (4,5) not found. Either did not move or disappeared.");
        assertNull(board0.getPieceAtPosition(5,6).orElse(null));


        try {
            //Checking how many possible jumps red has
            assertEquals(1,board0.getPossibleJumps(red34).size(),
                    "Red's number of possible jumps not equal to 1.");

            //Checking that making a non-jump move will result in an error
            Board finalBoard = board0;
            assertThrows(InvalidMoveException.class, () -> {
                finalBoard.applyMove(new Move(finalBoard.getPieceAtPosition(2,1).orElse(null),
                        new Piece(3,2, Type.SINGLE, Color.RED)));
            });

            //White takes red
            board0 = board0.applyMove(new Move(red34,
                    new Piece(5,6, Type.SINGLE, Color.RED), white45));

            //Checking to see if the jump actually succeeded
            assertNull(board0.getPieceAtPosition(3,4).orElse(null));
            assertNull(board0.getPieceAtPosition(4,5).orElse(null));
            assertNotNull(board0.getPieceAtPosition(5,6).orElse(null));

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception triggered when checking jumps.");
        }
    }
}
