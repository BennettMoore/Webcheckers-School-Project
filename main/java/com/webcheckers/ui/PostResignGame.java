package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.service.game.Game;
import com.webcheckers.service.player.Username;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

public class PostResignGame implements Route {
    private final GameService gameService;
    private final Gson gson;

    public PostResignGame(GameService gameService, Gson gson) {
        this.gameService = Objects.requireNonNull(gameService);
        this.gson = Objects.requireNonNull(gson);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var playerSession = new PlayerSession(request.session());

        var game = gameService.getGameFromRequest(request).get();
        var username = playerSession.getUsername().get();

        Message message;
        if (game.getPlayerOne().equals(username) || game.getPlayerTwo().equals(username)) {
            gameService.deleteGame(game.getGameId());
            message = Message.info("Game has been resigned");
        } else {
            message = Message.error("You are not in this game...");
        }

        return gson.toJson(message);
    }
}
