package com.webcheckers.ui;

import com.webcheckers.service.player.PlayerService;
import com.webcheckers.service.player.UsernameException;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerService playerService;

    private static final String USERNAME_PARAM = "username";

    public PostSigninRoute(final TemplateEngine templateEngine, final PlayerService playerService) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerService = Objects.requireNonNull(playerService, "userService is required");
        //
        LOG.config("PostSigninRoute is initialized.");
    }

    /**
     * Handle a signin request.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var session = new PlayerSession(request.session());
        var usernameParam = request.queryParams(USERNAME_PARAM);
        LOG.finer("PostSigninRoute is invoked with username " + usernameParam);

        try {
            var user = playerService.signInWithUsername(usernameParam);
            session.setUsername(user.getUsername());
            response.redirect(WebServer.HOME_URL);
            return null;
        } catch (UsernameException e) {
            var errorMessage = Message.error(e.getMessage());
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Sign In");
            vm.put("message", errorMessage);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
