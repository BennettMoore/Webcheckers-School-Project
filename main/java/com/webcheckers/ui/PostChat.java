package com.webcheckers.ui;

import com.webcheckers.service.game.Game;
import com.webcheckers.service.game.GameService;
import com.webcheckers.service.player.Username;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostChat implements Route {
    private static final Logger LOG = Logger.getLogger(PostChat.class.getName());
    private final TemplateEngine templateEngine;
    private final GameService gameService;

    private static final String MESSAGE_PARAM = "message";
    private static final String GAMEID_PARAM = "gameid";

    public PostChat(final TemplateEngine templateEngine, final GameService gameService) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        Username name = session.getUsername().orElse(null);
        String messageParam = request.queryParams(MESSAGE_PARAM);
        String gameidParam = request.queryParams(GAMEID_PARAM);
        Username gameid = new Username(gameidParam);
        Game game = gameService.getGameWithPlayer(gameid).orElse(null);
        LOG.fine("PostChat is invoked with message " + messageParam);
        game.setGameMessages(name, messageParam);
        response.redirect("/game?gameid=" + game.getGameId().toString());
        return null;
    }
}
