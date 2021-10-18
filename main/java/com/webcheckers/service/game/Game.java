package com.webcheckers.service.game;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.InvalidMoveException;
import com.webcheckers.model.board.Move;
import com.webcheckers.model.board.Piece;
import com.webcheckers.service.player.*;
import com.webcheckers.util.*;
import java.util.logging.Logger;

import java.util.*;

/**
 * @author <a href='mailto:bdbvse@rit.edu'>Matthew Glanz</a>
 */

public class Game {

    // object ID
    private static final Logger LOG = Logger.getLogger(Game.class.getName());
    private final GameId gameid;
    private final Username playerOne;
    private final Username playerTwo;
    private Board board;
    private Optional<Board> boardWithMoveApplied;
    private ChatLog chatLog;
    private Move move;
    private int turnNum;

    public Game(GameId gameid, Username playerOne, Username playerTwo) {
        this.gameid = Objects.requireNonNull(gameid);
        this.playerOne = Objects.requireNonNull(playerOne);
        this.playerTwo = Objects.requireNonNull(playerTwo);
        this.board = new Board();
        this.boardWithMoveApplied = Optional.empty();
        this.chatLog = new ChatLog();
        this.turnNum = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Game) {
            return ((Game)o).gameid.equals(gameid);
        } else {
            return false;
        }
    }

    public GameId getGameId() {
        return gameid;
    }

    public Username getPlayerOne() {
        return playerOne;
    }

    public Username getPlayerTwo() {
        return playerTwo;
    }

    public void setGameMessages(Username name, String message) {
        chatLog.createMessage(name, message);
        LOG.fine("==============================" + name + message);
    }

    public ArrayList<String> getGameMessages() {
        return chatLog.getMessages();
    }

    @Override
    public int hashCode() {
        return this.gameid.hashCode();
    }


    /**
     * Gets the piece at a specific coordinate position
     * @param row the row of the piece
     * @param cell the cell of the piece
     * @return the piece object itself
     */
    public Piece getPieceAtPos(int row, int cell){
        return board.getPieceAtPosition(row, cell).get();
    }

    /**
     * Get the username associated with the user whose turn is currently active.
     */
    public Username getCurrentTurn() {
        switch (board.getCurrentTurn()) {
            case WHITE: return getPlayerTwo();
            default:
            case RED: return getPlayerOne();
        }
    }

    public Color getCurrentTurnColor() {
        return board.getCurrentTurn();
    }
  
    /**
     * Check the given move's validity and cache it so that it can be applied if the user decides to submit the move
     * @param move the move to check and cache
     * @throws InvalidMoveException if the move is not valid
     */
    public void bufferMove(Move move) throws InvalidMoveException {
        this.move = move;
        boardWithMoveApplied = Optional.of(board.applyMove(move));
    }

    public Move getBufferMove(){
        return this.move;
    }

    /**
     * Clear the cached move applied by bufferMove.
     */
    public void clearBufferedMove() {
        boardWithMoveApplied = Optional.empty();
    }

    /**
     * Apply the buffered move to the board.
     * @throws NoMoveBufferedException if there is no move buffered.
     */
    public void applyBufferedMove() throws NoMoveBufferedException {
        if (boardWithMoveApplied.isPresent()) {
            this.board = boardWithMoveApplied.get();
            boardWithMoveApplied = Optional.empty();
            this.turnNum++;
        } else {
            throw new NoMoveBufferedException();
        }
    }

    public int getTurnNum() {
        return turnNum;
    }

    /**
     * Render the current board to a templatable BoardView
     * @param bottomColor the color to be rendered on the lower half of the board
     * @return the BoardView that's rendered to
     */
    public BoardView renderToBoardView(Color bottomColor) {
        var flip = bottomColor == Color.RED;
        var rows = new Row[8];
        for (int row = 0; row < 8; row++) {
            var spaces = new Space[8];
            for (int col = 0; col < 8; col++) {
                var piece = board.getPieceAtPosition(row, col)
                        .map(p -> new PieceView(p.getType(), p.getColor()))
                        .orElse(null);
                spaces[col] = new Space(col, piece);
            }
            rows[row] = new Row(row, spaces, flip);
        }
        return new BoardView(rows, flip);
    }

    /**
     * Promotes a single piece to a king and replaces the game's board to the returned board
     * @param piece
     */
    public void promotePiece(Piece piece){
        board = board.promotePiece(piece);
    }

    /**
     * Returns the board saved in the game class
     * @return the board object
     */
    public Board getBoard() {
        return board;
    }
}
