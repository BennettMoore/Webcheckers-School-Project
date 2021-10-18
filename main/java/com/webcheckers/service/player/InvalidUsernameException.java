package com.webcheckers.service.player;

public class InvalidUsernameException extends UsernameException {
    /**
     * Initialize an InvalidUsernameException. This is thrown if the username is formatted incorrectly.
     * @param name the attempted username
     */
    public InvalidUsernameException(String name) {
        super("Invalid username \"" + name + "\": Username must start with an alphanumeric character, can contain " +
                "any mixture of letters, numbers, spaces, -, or _, and must be less than 32 characters long");
    }
}
