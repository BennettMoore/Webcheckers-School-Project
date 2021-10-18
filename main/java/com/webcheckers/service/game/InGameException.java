package com.webcheckers.service.game;

public class InGameException extends GameException {
    public InGameException(String player) {
        super(player + " is already in a game, both players must not be in a game to start a new one.");
    }
}
