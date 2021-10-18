package com.webcheckers.service.player;

import java.util.Objects;

public class Player {

    // object ID
    private final Username username;


    /**
     * Initialize a player with the given username
     * @param username the player's username
     */
    public Player(Username username) {
        this.username = Objects.requireNonNull(username);
    }

    /**
     * @return the player's username
     */
    public Username getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Player) {
            return ((Player)o).username.equals(username);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
