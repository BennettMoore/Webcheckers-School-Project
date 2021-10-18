package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.logging.Logger;

public class PostSpectatorCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSpectatorCheckTurnRoute.class.getName());
    private final GameService gameService;
    private final Gson gson;

    private static final String USERNAME_PARAM = "username";

    public PostSpectatorCheckTurnRoute(final GameService gameService, final Gson gson) {
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostSpectatorCheckTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        var game = gameService.getGameFromRequest(request).get(); // TODO handle error case
        var turnNum = game.getTurnNum();
        var spectatorTurnNum = session.getSpectatorTurnNum();

        var newTurnExists = !OptionalInt.of(turnNum).equals(spectatorTurnNum);
        session.setSpectatorTurnNum(turnNum);

        var message = Message.info(Boolean.toString(newTurnExists));
        return gson.toJson(message);
    }
}
