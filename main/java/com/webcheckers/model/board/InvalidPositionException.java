package com.webcheckers.model.board;

public class InvalidPositionException extends Exception {
    public InvalidPositionException(int row, int col, String message) {
        super(row + " x " + col + " is not a valid position for a piece: " + message);
    }
}
