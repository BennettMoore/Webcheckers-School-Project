package com.webcheckers.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.Optional;
import java.util.ArrayList;

import com.webcheckers.service.player.Username;
import com.webcheckers.util.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.service.game.ChatLog;
import com.webcheckers.service.game.Game;
import com.webcheckers.service.game.GameId;
import com.webcheckers.service.game.GameService;
import com.webcheckers.service.player.PlayerService;
import com.webcheckers.service.player.Player;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:kmk8334@rit.edu'>Kevin Kulp</a>
 * @author <a href='mailto:msg4061@rit.edu'>Matthew Glanz</a>
 */
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  private final PlayerService playerService;
  private final GameService gameService;

  private static final String GAMEID_PARAM = "gameid";

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /game} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine, final PlayerService playerService, final GameService gameService) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerService = Objects.requireNonNull(playerService, "playerService is required");
    this.gameService = Objects.requireNonNull(gameService, "gameService is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    // Get a reference to the game using the player username
    var playerSession = new PlayerSession(request.session());
    LOG.fine(gameService.toString());
    String gameidParam = request.queryParams(GAMEID_PARAM);
    GameId gameId;
    if (gameidParam == null) {
      var gameIdOpt = playerSession.getUsername()
               .flatMap(gameService::getGameWithPlayer)
               .map(Game::getGameId);
      if (gameIdOpt.isPresent()) {
        gameId = gameIdOpt.get();
      } else {
        response.redirect(WebServer.HOME_URL);
        return null;
      }
    } else {
      gameId = new GameId(Integer.parseInt(gameidParam));
    }
    LOG.fine("GetGameRoute is invoked with GameId " + gameId.getGameNum());
    Optional<Game> gameOpt = gameService.getGameWithGameId(gameId);
    Game game;
    if (gameOpt.isPresent()) {
      game = gameOpt.get();
    } else {
      response.redirect(WebServer.HOME_URL);
      return null;
    }
    LOG.fine("PlayerSession: " + playerSession.toString());
    LOG.fine("Game: " + game.toString());
    // var oppoSession = new UserSession(session());
    LOG.finer("GetGameRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    ArrayList<String> messages = game.getGameMessages();
    String displayMessages = new String();
    for (int i = 0; i < messages.size(); i++) {
        displayMessages += messages.get(i);
        displayMessages += "</br></br>";
    }
    vm.put("messages", displayMessages);

    Color bottomColor;
    if (playerSession.getUsername().get().equals(game.getPlayerOne())) {
      bottomColor = Color.RED;
    } else {
      bottomColor = Color.WHITE;
    }

    var board = game.renderToBoardView(bottomColor);

    // ${gameID!'null'},
    //   "currentUser" : "${currentUser.name}",
    //   "viewMode" : "${viewMode}",
    //   "modeOptions" : ${modeOptionsAsJSON!'{}'},
    //   "redPlayer" : "${redPlayer.name}",
    //   "whitePlayer" : "${whitePlayer.name}",
    //   "activeColor" : "${activeColor}"

    // Set values for window.gameData
    vm.put("gameID", game.getGameId().toString());
    vm.put("currentUser", playerSession.getUsername().orElse(null).toString());
    // ENUM located: \src\main\resources\public\js\game\GameView.js
    String viewMode;
    String activeColor;
    if(playerSession.getUsername().orElse(null).equals(game.getPlayerOne())) {
      viewMode = "PLAY";
    } else if(playerSession.getUsername().orElse(null).equals(game.getPlayerTwo())) {
      viewMode = "PLAY";
    } else {
      viewMode = "SPECTATOR";
    }
    if (game.getCurrentTurnColor() == Color.RED) {
      activeColor = "RED";
    } else {
      activeColor = "WHITE";
    }
    // Determines if spectator or player is viewing the board
    vm.put("viewMode", viewMode);
    // Determines color at bottom of the board
    vm.put("activeColor", activeColor);
    // vm.put("modeOptions", );
    LOG.fine("=====Player One: " + playerService.getPlayerWithUsername(game.getPlayerOne()).orElse(null).toString());
    vm.put("redPlayer", game.getPlayerOne().toString());
    vm.put("whitePlayer", game.getPlayerTwo().toString());

    // display a user message in the Game page
    vm.put("message", WELCOME_MSG);

    vm.put("board", board);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}
