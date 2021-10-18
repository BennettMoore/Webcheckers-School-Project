package com.webcheckers.service.game;

import com.webcheckers.service.player.Username;
import com.webcheckers.service.player.InvalidUsernameException;
import com.webcheckers.service.player.UsernameTakenException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Application-tier")
class GameTest {
    private GameService gameService;

    private Username testOne;
    private Username testTwo;
    private Username testThree;
    private Username testFour;
    private Game game;
    final static String ERROR = "error";

    @BeforeEach
    public void setUp() throws InvalidUsernameException, GameException {
        gameService = new GameService();
        testOne = new Username("testone");
        testTwo = new Username("testtwo");
        testThree = new Username("testthree");
        testFour = new Username("testfour");
        game = gameService.createGame(testOne, testTwo);
    }

    @Test
    void testGame() {
        GameId gameTwoId = new GameId(2);
        Game gameTwo = new Game(gameTwoId, testOne, testTwo);
        assertEquals(gameTwoId.toString(), gameTwo.getGameId().toString(), ERROR);
        assertSame(testOne, gameTwo.getPlayerOne(), ERROR);
        assertSame(testTwo, gameTwo.getPlayerTwo(), ERROR);
    }

    @Test
    void testEquals() throws GameException {
        Game gameOne = game;
        assertEquals(game, game, ERROR);
        Game gameTwo = gameService.createGame(testThree, testFour);
        assertNotEquals(gameTwo, game, ERROR);
    }

    @Test
    void getGameId() {
        String gameId = game.getGameId().toString();

        assertEquals("1", gameId, ERROR);
    }

    @Test
    void getPlayerOne() throws InvalidUsernameException, UsernameTakenException {
        Username pOne = game.getPlayerOne();
        assertEquals(testOne.toString(), pOne.toString(), ERROR);
    }

    @Test
    void getPlayerTwo() throws InvalidUsernameException, UsernameTakenException {
        Username pOne = game.getPlayerTwo();
        assertEquals(testTwo.toString(), pOne.toString(), ERROR);
    }
}