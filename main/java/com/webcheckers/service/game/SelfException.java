package com.webcheckers.service.game;
import com.webcheckers.service.player.Username;

public class SelfException extends GameException {
    public SelfException(Username player) {
        super("Players cannot start a game with themselves, please choose a player other than " + player + " to start a game.");
    }
}
