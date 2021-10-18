package com.webcheckers.model.board;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@Tag("Model-tier")
public class PieceTest {
    public static Piece whiteSinglePiece(int row, int col) {
        return assertDoesNotThrow(() -> new Piece(row, col, Type.SINGLE, Color.WHITE));
    }

    public static Piece whiteKingPiece(int row, int col) {
        return assertDoesNotThrow(() -> new Piece(row, col, Type.KING, Color.WHITE));
    }

    public static Piece redSinglePiece(int row, int col) {
        return assertDoesNotThrow(() -> new Piece(row, col, Type.SINGLE, Color.RED));
    }

    // verify that the white single-moves work
    @Test
    void whiteSingleMove() {
        {
            var whitePieceLeft = whiteSinglePiece(5, 0);
            assertEquals(Optional.empty(), whitePieceLeft.moveForwardLeft());
            assertEquals(Optional.of(whiteSinglePiece(4, 1)), whitePieceLeft.moveForwardRight());
            assertEquals(Optional.empty(), whitePieceLeft.moveBackwardLeft());
            assertEquals(Optional.empty(), whitePieceLeft.moveBackwardRight());
        }

        {
            var whitePieceRight = whiteSinglePiece(6, 7);
            assertEquals(Optional.of(whiteSinglePiece(5, 6)), whitePieceRight.moveForwardLeft());
            assertEquals(Optional.empty(), whitePieceRight.moveForwardRight());
            assertEquals(Optional.empty(), whitePieceRight.moveBackwardLeft());
            assertEquals(Optional.empty(), whitePieceRight.moveBackwardRight());
        }

        {
            var whitePieceEnd = whiteSinglePiece(0, 3);
            assertEquals(Optional.empty(), whitePieceEnd.moveForwardLeft());
            assertEquals(Optional.empty(), whitePieceEnd.moveForwardRight());
            assertEquals(Optional.empty(), whitePieceEnd.moveBackwardLeft());
            assertEquals(Optional.empty(), whitePieceEnd.moveBackwardRight());
        }

        {
            var whitePieceCenter = whiteSinglePiece(4, 3);
            assertEquals(Optional.of(whiteSinglePiece(3, 2)), whitePieceCenter.moveForwardLeft());
            assertEquals(Optional.of(whiteSinglePiece(3, 4)), whitePieceCenter.moveForwardRight());
            assertEquals(Optional.empty(), whitePieceCenter.moveBackwardLeft());
            assertEquals(Optional.empty(), whitePieceCenter.moveBackwardRight());
        }
    }

    // verify that the red single-moves work
    @Test
    void redSingleMove() {
        {
            var redPieceLeft = redSinglePiece(2, 7);
            assertEquals(Optional.empty(), redPieceLeft.moveForwardLeft());
            assertEquals(Optional.of(redSinglePiece(3, 6)), redPieceLeft.moveForwardRight());
            assertEquals(Optional.empty(), redPieceLeft.moveBackwardLeft());
            assertEquals(Optional.empty(), redPieceLeft.moveBackwardRight());
        }

        {
            var redPieceRight = redSinglePiece(1, 0);
            assertEquals(Optional.of(redSinglePiece(2, 1)), redPieceRight.moveForwardLeft());
            assertEquals(Optional.empty(), redPieceRight.moveForwardRight());
            assertEquals(Optional.empty(), redPieceRight.moveBackwardLeft());
            assertEquals(Optional.empty(), redPieceRight.moveBackwardRight());
        }

        {
            var redPieceEnd = redSinglePiece(7, 4);
            assertEquals(Optional.empty(), redPieceEnd.moveForwardLeft());
            assertEquals(Optional.empty(), redPieceEnd.moveForwardRight());
            assertEquals(Optional.empty(), redPieceEnd.moveBackwardLeft());
            assertEquals(Optional.empty(), redPieceEnd.moveBackwardRight());
        }

        {
            var redPieceCenter = redSinglePiece(3, 4);
            assertEquals(Optional.of(redSinglePiece(4, 5)), redPieceCenter.moveForwardLeft());
            assertEquals(Optional.of(redSinglePiece(4, 3)), redPieceCenter.moveForwardRight());
            assertEquals(Optional.empty(), redPieceCenter.moveBackwardLeft());
            assertEquals(Optional.empty(), redPieceCenter.moveBackwardRight());
        }
    }

    // verify that the white single jumps work
    @Test
    void whiteSingleJump() {
        {
            var whitePieceLeftJump = whiteSinglePiece(5, 0);
            assertEquals(Optional.empty(), whitePieceLeftJump.jumpForwardLeft());
            assertEquals(Optional.of(whiteSinglePiece(3, 2)), whitePieceLeftJump.jumpForwardRight());
            assertEquals(Optional.empty(), whitePieceLeftJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), whitePieceLeftJump.jumpBackwardRight());
        }

        {
            var whitePieceRightJump = whiteSinglePiece(6, 7);
            assertEquals(Optional.of(whiteSinglePiece(4, 5)), whitePieceRightJump.jumpForwardLeft());
            assertEquals(Optional.empty(), whitePieceRightJump.jumpForwardRight());
            assertEquals(Optional.empty(), whitePieceRightJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), whitePieceRightJump.jumpBackwardRight());
        }

        {
            var whitePieceEndJump = whiteSinglePiece(0, 3);
            assertEquals(Optional.empty(), whitePieceEndJump.jumpForwardLeft());
            assertEquals(Optional.empty(), whitePieceEndJump.jumpForwardRight());
            assertEquals(Optional.empty(), whitePieceEndJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), whitePieceEndJump.jumpBackwardRight());
        }

        {
            var whitePieceCenterJump = whiteSinglePiece(4, 3);
            assertEquals(Optional.of(whiteSinglePiece(2, 1)), whitePieceCenterJump.jumpForwardLeft());
            assertEquals(Optional.of(whiteSinglePiece(2, 5)), whitePieceCenterJump.jumpForwardRight());
            assertEquals(Optional.empty(), whitePieceCenterJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), whitePieceCenterJump.jumpBackwardRight());
        }
    }

    // verify that the red single jumps work
    @Test
    void redSingleJump() {
        {
            var redPieceLeftJump = redSinglePiece(2, 7);
            assertEquals(Optional.empty(), redPieceLeftJump.jumpForwardLeft());
            assertEquals(Optional.of(redSinglePiece(4, 5)), redPieceLeftJump.jumpForwardRight());
            assertEquals(Optional.empty(), redPieceLeftJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), redPieceLeftJump.jumpBackwardRight());
        }

        {
            var redPieceRightJump = redSinglePiece(1, 0);
            assertEquals(Optional.of(redSinglePiece(3, 2)), redPieceRightJump.jumpForwardLeft());
            assertEquals(Optional.empty(), redPieceRightJump.jumpForwardRight());
            assertEquals(Optional.empty(), redPieceRightJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), redPieceRightJump.jumpBackwardRight());
        }

        {
            var redPieceEndJump = redSinglePiece(7, 4);
            assertEquals(Optional.empty(), redPieceEndJump.jumpForwardLeft());
            assertEquals(Optional.empty(), redPieceEndJump.jumpForwardRight());
            assertEquals(Optional.empty(), redPieceEndJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), redPieceEndJump.jumpBackwardRight());
        }

        {
            var redPieceCenterJump = redSinglePiece(3, 4);
            assertEquals(Optional.of(redSinglePiece(5, 6)), redPieceCenterJump.jumpForwardLeft());
            assertEquals(Optional.of(redSinglePiece(5, 2)), redPieceCenterJump.jumpForwardRight());
            assertEquals(Optional.empty(), redPieceCenterJump.jumpBackwardLeft());
            assertEquals(Optional.empty(), redPieceCenterJump.jumpBackwardRight());
        }
    }
    // verify that the piece creator error cases work
    @Test
    void pieceValidation() {
        // check that an exception is thrown if the piece is on a white square
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(0, 0, Type.SINGLE, Color.RED);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(2, 2, Type.SINGLE, Color.RED);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(4, 2, Type.SINGLE, Color.WHITE);
        });

        assertThrows(InvalidPositionException.class, () -> {
            new Piece(-1, -1, Type.SINGLE, Color.RED);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(4, -1, Type.SINGLE, Color.WHITE);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(9, -1, Type.SINGLE, Color.WHITE);
        });

        assertThrows(InvalidPositionException.class, () -> {
            new Piece(-1, -1, Type.SINGLE, Color.RED);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(-1, 4, Type.SINGLE, Color.WHITE);
        });
        assertThrows(InvalidPositionException.class, () -> {
            new Piece(-1, 9, Type.SINGLE, Color.WHITE);
        });

        assertThrows(InvalidPositionException.class, () -> {
            new Piece(9, 9, Type.SINGLE, Color.WHITE);
        });

        assertDoesNotThrow(() -> {
            new Piece(2, 3, Type.SINGLE, Color.RED);
        });
    }

    // verify that the getters work
    @Test
    void getters() {
        var redSinglePiece = redSinglePiece(3, 4);
        assertEquals(Color.RED, redSinglePiece.getColor());
        assertTrue(redSinglePiece.isRed());
        assertFalse(redSinglePiece.isWhite());
        assertEquals(3, redSinglePiece.getRow());
        assertEquals(4, redSinglePiece.getCol());
        assertTrue(redSinglePiece.isSingle());
        assertFalse(redSinglePiece.isKing());

        var whiteKingPiece = whiteKingPiece(5, 6);
        assertEquals(Color.WHITE, whiteKingPiece.getColor());
        assertTrue(whiteKingPiece.isWhite());
        assertFalse(whiteKingPiece.isRed());
        assertEquals(5, whiteKingPiece.getRow());
        assertEquals(6, whiteKingPiece.getCol());
        assertFalse(whiteKingPiece.isSingle());
        assertTrue(whiteKingPiece.isKing());
    }

    // verify that the piece equality tester works
    @Test
    void equals() {
        assertEquals(
                whiteSinglePiece(2, 3),
                whiteSinglePiece(2, 3)
        );
        assertEquals(
                redSinglePiece(2, 3),
                redSinglePiece(2, 3)
        );
        assertNotEquals(
                redSinglePiece(2, 5),
                redSinglePiece(2, 3)
        );
        assertNotEquals(
                whiteSinglePiece(2, 3),
                redSinglePiece(2, 3)
        );
    }
}
