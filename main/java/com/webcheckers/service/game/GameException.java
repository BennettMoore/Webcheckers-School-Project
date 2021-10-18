package com.webcheckers.service.game;

public abstract class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}
