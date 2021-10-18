package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.Game;
import com.webcheckers.service.game.GameService;
import com.webcheckers.service.game.NoMoveBufferedException;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private final GameService gameService;
    private final Gson gson;

    private static final String USERNAME_PARAM = "username";

    public PostSubmitTurnRoute(final GameService gameService, final Gson gson) {
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());

        // TODO handle error cases
        var game = gameService.getGameFromRequest(request).get();
        var username = session.getUsername().get();

        Message message;
        if (game.getCurrentTurn().equals(username)) {
            try {
                game.applyBufferedMove();
                message = Message.info("Move submitted successfully");

                if(
                    // A red single piece moves to the back rank (row 7)
                       (!game.getBufferMove().getStart().isKing()
                        && game.getBufferMove().getStart().isRed()
                        && game.getBufferMove().getStart().getRow() != 7
                        && game.getBufferMove().getEnd().getRow() == 7)
                    // A white single piece moves to the back rank (row 0)
                    || (!game.getBufferMove().getStart().isKing()
                        && game.getBufferMove().getStart().isWhite()
                        && game.getBufferMove().getStart().getRow() != 0
                        && game.getBufferMove().getEnd().getRow() == 0)
                ){
                    //Promote piece
                    game.promotePiece(game.getBufferMove().getEnd());
                }



            } catch (NoMoveBufferedException e) {
                message = Message.error("No move has been submitted for validation");
            }
        } else {
            message = Message.error("You can only submit a move if it's your turn");
        }

        return gson.toJson(message);
    }
}
