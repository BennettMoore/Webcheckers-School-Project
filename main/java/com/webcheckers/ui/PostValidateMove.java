package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.model.board.*;
import com.webcheckers.service.game.Game;
import com.webcheckers.service.game.GameId;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the move validation
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Matthew Glanz</a>

 */

public class PostValidateMove implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    private final GameService gameService;
    private final Gson gson;

    public PostValidateMove(GameService gameService, Gson gson) {
        this.gameService = gameService;
        this.gson = gson;
        LOG.config("PostValidateMove is initialized.");
    }

    /**
     * Handle a move validation request
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());

        //Take data from the request and turn into usable ints
        String actionData = request.queryParams("actionData");
        var actionJson = gson.fromJson(actionData, JsonObject.class);
        int sRow = actionJson.getAsJsonObject("start").get("row").getAsInt();
        int sCell = actionJson.getAsJsonObject("start").get("cell").getAsInt();
        int eRow = actionJson.getAsJsonObject("end").get("row").getAsInt();
        int eCell = actionJson.getAsJsonObject("end").get("cell").getAsInt();

        Optional<Game> gameOpt = gameService.getGameFromRequest(request);
        Message message;

        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            var isCurrentPlayersTurn = session.getUsername()
                    .map(username -> game.getCurrentTurn().equals(username)).orElse(false);
            if (isCurrentPlayersTurn) {
                //Get the piece object from the board
                Piece startPiece = game.getPieceAtPos(sRow, sCell);
                try {
                    Piece endPiece = new Piece(eRow, eCell, startPiece.getType(), startPiece.getColor());
                    Move move;
                    //Checking for jumps
                    if(Math.abs(sRow-eRow) > 1 || Math.abs(sCell-eCell) > 1){
                        Piece jumpPiece = game.getPieceAtPos((sRow+eRow)/2,(sCell+eCell)/2);
                        move = new Move(startPiece, endPiece, jumpPiece);
                    }
                    //No jump
                    else {
                        move = new Move(startPiece, endPiece);
                    }

                    try {
                        game.bufferMove(move);
                        message = Message.info("Valid Move");
                    }
                    //Catch for if the move is invalid
                    catch(InvalidMoveException e) {
                        message = Message.error("Piece cannot be placed here");
                    }
                }
                //Catch for if it is a white square
                catch(InvalidPositionException e){
                    message = Message.error("Piece must be placed on a black square");
                }
            } else {
                message = Message.error("It is not your turn...");
            }
        } else {
            message = Message.error("Game does not exist");
        }

        return gson.toJson(message);
    }
}
