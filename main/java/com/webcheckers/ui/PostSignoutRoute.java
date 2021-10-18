package com.webcheckers.ui;

import com.webcheckers.service.player.PlayerService;
import spark.*;

import java.util.Objects;

public class PostSignoutRoute implements Route {
    private final PlayerService playerService;

    public PostSignoutRoute(PlayerService playerService) {
        this.playerService = Objects.requireNonNull(playerService);
    }

    /**
     * Handle a signout request
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var playerSession = new PlayerSession(request.session());

        var currentUsername = playerSession.removeUsername();
        currentUsername.ifPresent(playerService::signOutWithUsername);

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
