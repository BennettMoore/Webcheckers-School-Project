package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.logging.Logger;

public class GetSpectatorStopWatching implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectatorStopWatching.class.getName());

    public GetSpectatorStopWatching() {
        LOG.config("PostSpectatorStopWatching is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        session.removeSpectatorTurnNum();
        response.redirect(WebServer.HOME_URL);
        LOG.fine("Spectator stopped watching.");
        return null;
    }
}
