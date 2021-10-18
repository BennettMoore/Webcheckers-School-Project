package com.webcheckers.service.game;

import com.webcheckers.service.player.*;
import spark.Request;

import java.util.*;
import java.util.logging.Logger;

public class GameService {
    private static final Logger LOG = Logger.getLogger(GameService.class.getName());
    private final HashMap<GameId, Game> games;
    private int gameId;

    public GameService() {
        games = new HashMap<>();
        gameId = 0;
    }

    public Game createGame(Username playerOne, Username playerTwo) throws SelfException, InGameException {
        if (playerOne.equals(playerTwo)) {
            throw new SelfException(playerOne);
        }
        for (Game game : games.values()) {
            if (playerOne.equals(game.getPlayerOne()) || playerOne.equals(game.getPlayerTwo())) {
                throw new InGameException(playerOne.toString());
            }
            if (playerTwo.equals(game.getPlayerOne()) || playerTwo.equals(game.getPlayerTwo())) {
                throw new InGameException(playerTwo.toString());
            }
        }
        gameId++;
        GameId newid = new GameId(gameId);
        var game = new Game(newid, playerOne, playerTwo);
        games.put(newid, game);
        LOG.fine("Game " + newid + " created");
        return game;
    }

    public void deleteGame(GameId name) {
        games.remove(name);
    }

    public Optional<Game> getGameWithGameId(GameId gameid) {
        for (Game game : games.values()) {
            if (game.getGameId().toString().equals(gameid.toString())) {
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    public Optional<Game> getGameWithPlayer(Username player) {
        LOG.fine(player.toString());

        for (Game game : games.values()) {
            if (player.equals(game.getPlayerOne()) || player.equals(game.getPlayerTwo())) {
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    /**
     * Get the game associated with the gameID query parameter in the supplied request
     * @param request the request to find the gameID query parameter in
     * @return the game, if it exists
     */
    public Optional<Game> getGameFromRequest(Request request) {
        String gameIDString = request.queryParams("gameID");
        if (gameIDString.equals("")) {
            return Optional.empty();
        }

        try {
            int gameInt = Integer.parseInt(gameIDString);
            GameId gameID = new GameId(gameInt);
            return getGameWithGameId(gameID);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public int getNumGames() {
        return games.size();
    }

    public Set<GameId> getCreatedGameIds() {
        return games.keySet();
    }
}
