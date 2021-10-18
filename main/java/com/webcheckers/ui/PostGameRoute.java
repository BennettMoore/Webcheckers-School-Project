package com.webcheckers.ui;

import com.webcheckers.service.game.InGameException;
import com.webcheckers.service.game.SelfException;
import com.webcheckers.service.player.Username;
import com.webcheckers.service.game.GameService;
import com.webcheckers.util.Message;
import spark.*;
import java.net.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameService gameService;

    private static final String USERNAME_PARAM = "username";

    public PostGameRoute(final TemplateEngine templateEngine, final GameService gameService) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gameService = Objects.requireNonNull(gameService, "gameService is required");
        LOG.config("PostGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        var usernameParam = request.queryParams(USERNAME_PARAM);
        LOG.fine("+++++++++++++++++++++++++++=PostGameRoute is invoked with username " + usernameParam);
        Username playerTwo = new Username(usernameParam);

        try {
            var username = session.getUsername();
            if (username.isPresent()) {
                var game = gameService.createGame(username.get(), playerTwo);
                response.redirect(WebServer.GAME_URL);
            } else {
                response.redirect(WebServer.HOME_URL);
            }
            return null;
        } catch (SelfException | InGameException e) {
            var errorMessage = Message.error(e.getMessage());
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Sign In");
            vm.put("message", errorMessage);
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        }
    }
}
