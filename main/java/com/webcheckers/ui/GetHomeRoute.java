package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.service.player.PlayerService;
import com.webcheckers.service.game.GameService;
import com.webcheckers.service.player.Username;
import com.webcheckers.service.game.GameId;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  private final PlayerService playerService;
  private final GameService gameService;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, final PlayerService playerService, final GameService gameService) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerService = Objects.requireNonNull(playerService, "userService is required");
    this.gameService = Objects.requireNonNull(gameService, "gameService is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    var playerSession = new PlayerSession(request.session());
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    vm.put("currentUser", playerSession.getUsername().orElse(null));

    var currentGame = playerSession.getUsername().flatMap(gameService::getGameWithPlayer);

    if (playerSession.getUsername().isEmpty()) {
      vm.put("userCount", playerService.getNumPlayers());
      vm.put("gameCount", gameService.getNumGames());
    } else if (currentGame.isPresent()) {
      // redirect the user to a game if they're in a game
      response.redirect(WebServer.GAME_URL);
    } else {
      var currentUsernames = new ArrayList<Username>(playerService.getSignedInUsernames());
      Collections.sort(currentUsernames);
      vm.put("signedInUsers", currentUsernames);
      var currentGames = new ArrayList<GameId>(gameService.getCreatedGameIds());
      vm.put("createdGames", currentGames);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
