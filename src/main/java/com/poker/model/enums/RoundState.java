package com.poker.model.enums;

/**
 * FIRST_STATE      Three cards down
 * SECOND_STATE     Three cards up
 * THIRD_STATE      Four cards up
 * FOURTH_STATE     Five cards up
 * FIFTH_STATE      Ending round
 */
public enum RoundState {
    // FIXME: names to be improved
    FIRST_STATE,  // Three cards down
    SECOND_STATE, // Three cards up
    THIRD_STATE,  // Four cards up
    FOURTH_STATE, // Five cards up
    FIFTH_STATE   // Ending round //TODO: maybe will be removed
}
