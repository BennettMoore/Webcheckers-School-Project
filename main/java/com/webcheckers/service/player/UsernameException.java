package com.webcheckers.service.player;

public abstract class UsernameException extends Exception {
    /**
     * thrown if there's an issue with the player's requested username
     * @param message the issue with the player's username. This will be displayed to the player in the web UI.
     */
    public UsernameException(String message) {
        super(message);
    }
}
