package com.webcheckers.service.player;

import java.util.*;
import java.util.logging.Logger;

public class PlayerService {
    private static final Logger LOG = Logger.getLogger(PlayerService.class.getName());
    private final HashMap<Username, Player> players;

    /**
     * Initialize a new PlayerService
     */
    public PlayerService() {
        players = new HashMap<>();
    }

    /**
     * Attempt to sign in with the given username.
     *
     * @param name the player's requested username
     * @return the signed-in Player object
     * @throws InvalidUsernameException if the requested username isn't formatted properly
     * @throws UsernameTakenException if the username is already taken
     */
    public Player signInWithUsername(String name) throws InvalidUsernameException, UsernameTakenException {
        var username = new Username(Objects.requireNonNull(name));
        if (players.containsKey(username)) {
            LOG.fine("Username " + username + " has already been taken :/");
            throw new UsernameTakenException(username);
        }
        var user = new Player(username);
        players.put(username, user);
        LOG.fine("User " + user.getUsername() + " created");
        return user;
    }

    /**
     * Sign out the player with the given username. Does nothing if that username isn't associated with an active user.
     * @param name the username to sign out
     */
    public void signOutWithUsername(Username name) {
        if (players.remove(name) != null) {
            LOG.fine("Username " + name + " logged out.");
        }
    }

    /**
     * Get the player that has the given username.
     * @param username The username to look up a player with
     * @return The player, if they exist
     */
    public Optional<Player> getPlayerWithUsername(Username username) {
        return Optional.ofNullable(players.get(username));
    }

    /**
     * @return the number of users that are currently signed in
     */
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * @return the set of usernames that are currently associated with a player
     */
    public Set<Username> getSignedInUsernames() {
        return players.keySet();
    }
}
