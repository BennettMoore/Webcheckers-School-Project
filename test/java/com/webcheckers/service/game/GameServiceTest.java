package com.webcheckers.service.game;

import com.webcheckers.service.player.Username;
import com.webcheckers.service.game.GameService;
import com.webcheckers.service.player.InvalidUsernameException;
import com.webcheckers.service.player.UsernameTakenException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import java.util.Set;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Application-tier")
class GameServiceTest {

    private GameService gameService;

    private Username testOne;
    private Username testTwo;
    private Username testThree;
    private Username testFour;
    private GameId one;
    private GameId two;
    private Game game;
    private Game gameTwo;
    final static String ERROR = "error";

    @BeforeEach
    public void setUp() throws InvalidUsernameException, InGameException, SelfException {
        gameService = new GameService();
        testOne = new Username("testone");
        testTwo = new Username("testtwo");
        testThree = new Username("testfour");
        testFour = new Username("testthree");
        one = new GameId(1);
        two = new GameId(2);
        game = gameService.createGame(testOne, testTwo);
        gameTwo = gameService.createGame(testThree, testFour);
    }

    @Test
    void testGameService() {
        assertNotNull(gameService);
    }

    @Test
    void createGame() {
        assertEquals(two.toString(), gameTwo.getGameId().toString(), ERROR);
        assertSame(testThree, gameTwo.getPlayerOne(), ERROR);
        assertSame(testFour, gameTwo.getPlayerTwo(), ERROR);
    }

    @Test
    void deleteGame() {
        gameService.deleteGame(one);
        gameService.deleteGame(two);
        var testGame = gameService.getGameWithGameId(one);
        assertEquals(Optional.empty(), testGame, ERROR);
        var testGameTwo = gameService.getGameWithGameId(two);
        assertEquals(Optional.empty(), testGameTwo, ERROR);
    }

    @Test
    void getGameWithGameId() {
        var testGame = gameService.getGameWithGameId(one).orElseThrow();
        assertSame(testGame, game, ERROR);
        var testGameTwo = gameService.getGameWithGameId(two).orElseThrow();
        assertSame(testGameTwo, gameTwo, ERROR);
    }

    @Test
    void getGameWithPlayer() {
        Game oneTestGame = gameService.getGameWithPlayer(testOne).orElseThrow();
        assertSame(oneTestGame, game, ERROR);
        Game testGameOne = gameService.getGameWithPlayer(testTwo).orElseThrow();
        assertSame(testGameOne, game, ERROR);
        Game twoTestGame = gameService.getGameWithPlayer(testThree).orElseThrow();
        assertSame(twoTestGame, gameTwo, ERROR);
        Game testGameTwo = gameService.getGameWithPlayer(testFour).orElseThrow();
        assertSame(testGameTwo, gameTwo, ERROR);
    }

    @Test
    void getNumGames() {
        int numGames = 2;
        int testNumGames = gameService.getNumGames();
        assertEquals(numGames, testNumGames, ERROR);
    }

    @Test
    void getCreatedGameIds() {
        Set<GameId> games = gameService.getCreatedGameIds();
        assertTrue(games.contains(one), ERROR);
    }
}