package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());
    private final TemplateEngine templateEngine;

    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebCheckers sign-in page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        var playerSession = new PlayerSession(request.session());
        if (playerSession.getUsername().isPresent()) {
            LOG.finer("Attempted to invoke GetSigninRoute while already signed in.");
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        LOG.finer("GetSigninRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign In");

        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}
