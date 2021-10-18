package com.webcheckers.service.game;

public class NoMoveBufferedException extends Exception {
    public NoMoveBufferedException() {
        super("Attempted to apply buffered move when no move has been buffered");
    }
}
