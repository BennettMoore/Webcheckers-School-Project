package com.webcheckers.service.player;

import java.util.Objects;
import java.util.logging.Logger;

public class Username implements Comparable<Username> {
    private static final Logger LOG = Logger.getLogger(Username.class.getName());
    private static final String NAME_REGEX = "^[a-zA-Z0-9](?:\\w|[\\- ])+$";

    private final String displayName;
    // the comparisonName is normalized to lower-case to ensure that people don't take names that have already been
    // entered with a different capitalization
    private final String comparisonName;

    /**
     * Create a new Username object
     * @param name the string to wrap
     * @throws InvalidUsernameException if the given name string isn't formatted properly.
     */
    public Username(final String name) throws InvalidUsernameException {
        this.displayName = Objects.requireNonNull(name).trim();
        this.comparisonName = this.displayName.toLowerCase();

        var nameIsValid = this.displayName.matches(NAME_REGEX) && this.displayName.length() <= 32;
        if (!nameIsValid) {
            LOG.fine("Attempted to create invalid username " + this.displayName);
            throw new InvalidUsernameException(this.displayName);
        }
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Username) {
            return ((Username)o).comparisonName.equals(comparisonName);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.comparisonName.hashCode();
    }

    @Override
    public int compareTo(Username o) {
        return this.comparisonName.compareTo(o.comparisonName);
    }
}
