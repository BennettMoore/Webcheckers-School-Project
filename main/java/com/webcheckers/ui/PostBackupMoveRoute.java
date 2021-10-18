package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private final GameService gameService;
    private final Gson gson;

    private static final String USERNAME_PARAM = "username";

    public PostBackupMoveRoute(final GameService gameService, final Gson gson) {
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        // TODO ERROR HANDLE CASE
        var game = gameService.getGameFromRequest(request).get();
        var username = session.getUsername().get();

        Message message;
        if (game.getCurrentTurn().equals(username)) {
            game.clearBufferedMove();
            message = Message.info("Move cleared successfully");
        } else {
            message = Message.info("It is not your turn...");
        }

        return gson.toJson(message);
    }
}
