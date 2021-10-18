package com.webcheckers.service.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Application-tier")
class GameIdTest {

    private GameId gameIdUnderTest;

    @BeforeEach
    void setUp() {
        gameIdUnderTest = new GameId(0);
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = gameIdUnderTest.toString();

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    void testCompareTo1() {
        // Setup
        final GameId o = new GameId(0);

        // Run the test
        final int result = gameIdUnderTest.compareTo(gameIdUnderTest);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testEquals1() {
        // Setup

        // Run the test
        final boolean result = gameIdUnderTest.equals(gameIdUnderTest);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testHashCode1() {
        // Setup

        // Run the test
        final int result = gameIdUnderTest.hashCode();

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testEquals() {
        // Setup

        // Run the test
        final boolean result = gameIdUnderTest.equals(gameIdUnderTest);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testHashCode() {
        // Setup

        // Run the test
        final int result = gameIdUnderTest.hashCode();

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testCompareTo() {
        // Setup
        final GameId o = new GameId(0);

        // Run the test
        final int result = gameIdUnderTest.compareTo(gameIdUnderTest);

        // Verify the results
        assertEquals(0, result);
    }
}
