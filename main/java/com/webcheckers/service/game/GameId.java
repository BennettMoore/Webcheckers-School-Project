package com.webcheckers.service.game;

import java.util.Objects;
import java.util.logging.Logger;

public class GameId {
    private static final Logger LOG = Logger.getLogger(GameId.class.getName());

    private final int gameNum;

    public GameId(int gameId) {
        this.gameNum = Objects.requireNonNull(gameId);

    }

    @Override
    public String toString() {
        return String.valueOf(this.gameNum);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GameId) {
            return ((GameId)o).getGameNum() == this.getGameNum();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getGameNum();
    }

    public int getGameNum() {
        return gameNum;
    }

    public int compareTo(GameId o) {
       return this.toString().compareTo(o.toString());
    }
}
