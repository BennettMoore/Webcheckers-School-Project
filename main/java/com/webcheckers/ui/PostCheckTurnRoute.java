package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private final GameService gameService;
    private final Gson gson;

    private static final String USERNAME_PARAM = "username";

    public PostCheckTurnRoute(final GameService gameService, final Gson gson) {
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        var username = session.getUsername().get(); // TODO handle error case
        var game = gameService.getGameFromRequest(request).get(); // TODO handle error case
        
        var isPlayersTurn = Message.info(Boolean.toString(game.getCurrentTurn().equals(username)));

        if (!game.getPlayerOne().equals(username) && !game.getPlayerTwo().equals(username)) {
            isPlayersTurn = Message.info("true");
        }
        return gson.toJson(isPlayersTurn);
    }
}
