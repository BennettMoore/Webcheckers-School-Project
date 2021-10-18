package com.webcheckers.service.player;

public class UsernameTakenException extends UsernameException {
    /**
     * thrown if the username is already in use by another player
     * @param username the username that's already in use
     */
    public UsernameTakenException(Username username) {
        super("Username \"" + username + "\" is already in use");
    }
}
