package com.poker.model.enums;

/**
 * SETUP      Three cards down
 * THE_FLOP     Three cards up
 * THE_TURN      Four cards up
 * THE_RIVER     Five cards up
 * SHOWDOWN      Ending round
 */
public enum RoundState {
    SETUP,
    THE_FLOP,
    THE_TURN,
    THE_RIVER,
    SHOWDOWN
}
