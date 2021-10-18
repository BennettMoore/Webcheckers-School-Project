package com.webcheckers.ui;

import com.webcheckers.service.player.Username;
import spark.Session;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * PlayerSession wraps a pre-existing HTTP Session and attaches a signed-in
 * player username to that session. This is what allows players to remained signed
 * in.
 *
 * This is currently implemented as a pretty simple wrapper around session attributes.
 */
public class PlayerSession {
    private static final String SESSION_USERNAME_KEY = "username";
    private static final String SESSION_SPECTATOR_TURN_KEY = "spectator_turn_num";

    private final Session session;

    /**
     * Wrap the provided session to create a PlayerSession
     * @param sessionToWrap
     */
    public PlayerSession(Session sessionToWrap) {
        this.session = Objects.requireNonNull(sessionToWrap);
    }

    /**
     * Set the session's username attribute.
     * @param username
     */
    public void setUsername(Username username) {
        session.attribute(SESSION_USERNAME_KEY, Objects.requireNonNull(username));
    }

    /**
     * Unset the session's username attribute.
     * @return The previous username, if it existed.
     */
    public Optional<Username> removeUsername() {
        var currentUsername = getUsername();
        session.attribute(SESSION_USERNAME_KEY, null);
        return currentUsername;
    }

    /**
     * Get the session's username attribute
     * @return the current username, if it exists.
     */
    public Optional<Username> getUsername() {
        Username usernameNullable = session.attribute(SESSION_USERNAME_KEY);
        return Optional.ofNullable(usernameNullable);
    }

    public OptionalInt getSpectatorTurnNum() {
        Integer intNullable = session.attribute(SESSION_SPECTATOR_TURN_KEY);
        if (intNullable == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(intNullable);
        }
    }

    public void setSpectatorTurnNum(int num) {
        session.attribute(SESSION_SPECTATOR_TURN_KEY, num);
    }

    public OptionalInt removeSpectatorTurnNum() {
        var currentNum = getSpectatorTurnNum();
        session.attribute(SESSION_SPECTATOR_TURN_KEY, null);
        return currentNum;
    }
}
