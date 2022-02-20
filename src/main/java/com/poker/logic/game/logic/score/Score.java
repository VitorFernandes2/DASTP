package com.poker.logic.game.logic.score;

import com.poker.model.card.ICard;
import com.poker.model.filter.Log;

import java.util.*;

public class Score implements IScore {
    private static final Log LOG = Log.getInstance();
    private static List<ICard> cards;

    public Score(List<ICard> cards) {
        Score.cards = cards;
    }

    /**
     * Function to clear the cards list.
     */
    public void clearCards() {
        cards.clear();
    }

    /**
     * Function to set the cards list with a list of cards.
     *
     * @param deckCards {@link List} - List of cards.
     */
    public void setCards(List<ICard> deckCards) {
        cards = deckCards;
    }

    /**
     * Function to sort the table cards list to easier research sequence formations.
     */
    private void sortCards() {
        cards.sort((o1, o2) -> {
            if (o1.getCardValue() == 1) {
                return 1;
            } else if (o2.getCardValue() == 1) {
                return -1;
            }
            return o1.getCardValue().compareTo(o2.getCardValue());
        });
    }

    /**
     * Function to sort the table + player hand cards list to easier research sequence formations.
     *
     * @param newCards {@link List} - List of cards to sort.
     * @return List - List of cards in sorted state.
     */
    private List<ICard> sortCards(List<ICard> newCards) {
        newCards.sort((o1, o2) -> {
            if (o1.getCardValue() == 1) {
                return 1;
            } else if (o2.getCardValue() == 1) {
                return -1;
            }
            return o1.getCardValue().compareTo(o2.getCardValue());
        });

        return newCards;
    }

    /**
     * Function to verify if the lowest rule of Royal Straight Flush is present on the cards list.
     *
     * @param cardsList {@link List} - List of cards {@link ICard}.
     * @return boolean - Is <code>true</code> if the lowest rule of Royal Straight Flush is present, otherwise is <code>false</code>.
     */
    private boolean isLowRoyalStraightFlush(List<ICard> cardsList) {
        List<Integer> cardsValues = new ArrayList<>();

        for (ICard card : cardsList) {
            cardsValues.add(card.getCardValue());
        }

        return cardsValues.containsAll(Arrays.asList(5, 4, 3, 2, 1));
    }

    /**
     * Function to get the first list with the highest sequence of cards possible.
     *
     * @return List - List of cards {@link ICard} with the biggest sequence.
     */
    private List<ICard> getSequenceList() {
        int lastValue = 100;
        boolean onlyOne = true;
        List<ICard> highFirstSequence = new ArrayList<>();
        List<ICard> highSecondSequence = new ArrayList<>();

        if (!isLowRoyalStraightFlush(cards)) {
            for (ICard card : cards) {
                int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

                if (lastValue == 100) {
                    lastValue = cardValue;
                    highFirstSequence.add(card);
                    continue;
                }

                if ((cardValue - lastValue) == 1 && onlyOne) {
                    highFirstSequence.add(card);
                } else {
                    onlyOne = false;
                }

                if ((cardValue - lastValue) == 1 && !onlyOne) {
                    highSecondSequence.add(card);
                }

                lastValue = cardValue;
            }

            // Because the cards list is already sorted (descending order) even if the sequences are
            // of the same size, the first sequence will have the most valuable sequence of cards.
            return highFirstSequence.size() >= highSecondSequence.size() ? highFirstSequence : highSecondSequence;
        }

        for (ICard card : cards) {
            int cardValue = card.getCardValue();

            if (lastValue == 100) {
                lastValue = cardValue;
                highFirstSequence.add(card);
                continue;
            }

            if ((cardValue - lastValue) == 1 && onlyOne) {
                highFirstSequence.add(card);
            } else {
                onlyOne = false;
            }

            if ((cardValue - lastValue) == 1 && !onlyOne) {
                highSecondSequence.add(card);
            }

            lastValue = cardValue;
        }

        // Because the cards list is already sorted (descending order) even if the sequences are
        // of the same size, the first sequence will have the most valuable sequence of cards.
        return highFirstSequence.size() >= highSecondSequence.size() ? highFirstSequence : highSecondSequence;
    }

    /**
     * Function to get the first list with the highest sequence of cards possible.
     *
     * @param sequence {@link List} - Its the list of cards {@link ICard}.
     * @return List - List of cards {@link ICard} with the biggest sequence.
     */
    private List<ICard> getSequenceList(List<ICard> sequence) {
        int lastValue = 100;
        boolean onlyOne = true;
        List<ICard> highFirstSequence = new ArrayList<>();
        List<ICard> highSecondSequence = new ArrayList<>();

        if (!isLowRoyalStraightFlush(sequence)) {
            for (ICard card : sequence) {
                int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

                if (lastValue == 100) {
                    lastValue = cardValue;
                    highFirstSequence.add(card);
                    continue;
                }

                if ((cardValue - lastValue) == 1 && onlyOne) {
                    highFirstSequence.add(card);
                } else {
                    onlyOne = false;
                }

                if ((cardValue - lastValue) == 1 && !onlyOne) {
                    highSecondSequence.add(card);
                }

                lastValue = cardValue;
            }

            // Because the cards list is already sorted (descending order) even if the sequences are
            // of the same size, the first sequence will have the most valuable sequence of cards.
            return highFirstSequence.size() >= highSecondSequence.size() ? highFirstSequence : highSecondSequence;
        }

        for (ICard card : sequence) {
            int cardValue = card.getCardValue();

            if (lastValue == 100) {
                lastValue = cardValue;
                highFirstSequence.add(card);
                continue;
            }

            if ((cardValue - lastValue) == 1 && onlyOne) {
                highFirstSequence.add(card);
            } else {
                onlyOne = false;
            }

            if ((cardValue - lastValue) == 1 && !onlyOne) {
                highSecondSequence.add(card);
            }

            lastValue = cardValue;
        }

        // Because the cards list is already sorted (descending order) even if the sequences are
        // of the same size, the first sequence will have the most valuable sequence of cards.
        return highFirstSequence.size() >= highSecondSequence.size() ? highFirstSequence : highSecondSequence;
    }

    /**
     * Function to search for the highest set formation in the cards list. It returns the size of the highest set of cards.
     *
     * @return int - Size of the highest cards set.
     */
    private int setHighSetValue(Map<String, List<ICard>> sets) {
        int highSet = 0;
        for (String key : sets.keySet()) {
            int setCounter = sets.get(key).size();
            if (highSet == 0) {
                highSet = setCounter;
            } else if (highSet < setCounter) {
                highSet = setCounter;
            }

        }
        return highSet;
    }

    /**
     * Function to dismiss non-paired set formations. To be a paired set, it needs to have at least 2 cards of the same set.
     *
     * @param setMap {@link Map} - Map with all the cards organized by their respective set.
     * @return Map - Map without non-paired sets.
     */
    private Map<String, List<ICard>> uselessSet(Map<String, List<ICard>> setMap) {
        Map<String, List<ICard>> setMapCopy = new HashMap<>(setMap);
        for (String set : setMap.keySet()) {
            if (!(setMap.get(set).size() > 1)) {
                setMapCopy.remove(set);
            }
        }
        return setMapCopy;
    }

    /**
     * Function to create an organized Map Object with the table cards organized by sets.
     *
     * @return Map - Map Object with the table cards organized by sets.
     */
    private Map<String, List<ICard>> setMapCreator() {
        Map<String, List<ICard>> cardSet = new HashMap<>();
        cardSet.put("Diamonds", new ArrayList<>());
        cardSet.put("Spades", new ArrayList<>());
        cardSet.put("Clubs", new ArrayList<>());
        cardSet.put("Hearts", new ArrayList<>());

        // Getting the number of cards of the same set
        for (ICard card : cards) {
            if (card.getStringCardValue().contains("Diamonds")) {
                // Diamonds
                cardSet.get("Diamonds").add(card);
            } else if (card.getStringCardValue().contains("Spades")) {
                // Spades
                cardSet.get("Spades").add(card);
            } else if (card.getStringCardValue().contains("Clubs")) {
                // Clubs
                cardSet.get("Clubs").add(card);
            } else {
                // Hearts
                cardSet.get("Hearts").add(card);
            }
        }

        return uselessSet(cardSet);
    }

    /**
     * Function to create an organized Map Object with the table cards organized by sets.
     *
     * @return Map - Map Object with the table cards organized by sets.
     */
    private Map<String, List<ICard>> setMapCreator(List<ICard> cardsCopy) {
        Map<String, List<ICard>> cardSet = new HashMap<>();
        cardSet.put("Diamonds", new ArrayList<>());
        cardSet.put("Spades", new ArrayList<>());
        cardSet.put("Clubs", new ArrayList<>());
        cardSet.put("Hearts", new ArrayList<>());

        // Getting the number of cards of the same set
        for (ICard card : cardsCopy) {
            if (card.getStringCardValue().contains("Diamonds")) {
                // Diamonds
                cardSet.get("Diamonds").add(card);
            } else if (card.getStringCardValue().contains("Spades")) {
                // Spades
                cardSet.get("Spades").add(card);
            } else if (card.getStringCardValue().contains("Clubs")) {
                // Clubs
                cardSet.get("Clubs").add(card);
            } else {
                // Hearts
                cardSet.get("Hearts").add(card);
            }
        }

        return uselessSet(cardSet);
    }

    /**
     * Function to create an organized List with the card's appearance value organized by the times they appear.
     *
     * @return List - List of Integer Objects with the table card's value organized by the times they appear.
     */
    private List<Integer> getPairCounter() {
        Map<Integer, Integer> sequenceCounter = new HashMap<>();

        for (ICard card : cards) {
            int cardValue = card.getCardValue();
            if (!sequenceCounter.containsKey(cardValue)) {
                sequenceCounter.put(cardValue, 1);
            } else {
                int counterAux = sequenceCounter.get(cardValue);
                sequenceCounter.replace(cardValue, counterAux + 1);
            }
        }

        return new ArrayList<>(sequenceCounter.values());
    }

    /**
     * Function to create an organized List with the card's appearance value organized by the times they appear.
     *
     * @return List - List of Integer Objects with the table card's value organized by the times they appear.
     */
    private List<Integer> getPairCounter(List<ICard> cardsCopy) {
        Map<Integer, Integer> sequenceCounter = new HashMap<>();

        for (ICard card : cardsCopy) {
            int cardValue = card.getCardValue();
            if (!sequenceCounter.containsKey(cardValue)) {
                sequenceCounter.put(cardValue, 1);
            } else {
                int counterAux = sequenceCounter.get(cardValue);
                sequenceCounter.replace(cardValue, counterAux + 1);
            }
        }

        return new ArrayList<>(sequenceCounter.values());
    }

    /**
     * Function to calculate the possible final score of the table that can be achievable.
     *
     * @return int - Final score achievable.
     */
    private int totalScore() {
        int total = 0;

        for (ICard card : cards) {
            total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
        }

        return total;
    }

    /**
     * Function to calculate the possible final score that can be achievable.
     *
     * @param cards {@link List} - List of cards {@link ICard}.
     * @return int - Final score achievable.
     */
    private int totalScore(List<ICard> cards) {
        int total = 0;

        for (ICard card : cards) {
            total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
        }

        return total;
    }

    /**
     * Function to calculate the current table cards list score.
     *
     * @return int - Current table cards list score.
     */
    @Override
    public int calculateTableScore() {
        if (cards.isEmpty()) {
            return 0;
        }
        sortCards();
        Map<String, List<ICard>> cardSet = setMapCreator();    // Organizing the cards by their sets.
        List<ICard> sequenceOfCards = getSequenceList();       // Use it to get the biggest cards sequence.

        int highSet = setHighSetValue(cardSet);                // Getting the highest set counter value.
        List<Integer> pairsCounter = getPairCounter();         // Array of cards value pairs.
        int total = totalScore();                              // Total cards score. (Draws are settled after by a comparator.)

        // Rule: Royal Straight Flush
        // -100 score because it's the 1st highest score possible.
        if (sequenceOfCards.get(0).getCardValue() == 1 && highSet >= 5) {
            return total - 100;
        }

        // Rule: Straight Flush
        // -200 score because it's the 2nd highest score possible.
        if (sequenceOfCards.get(0).getCardValue() != 1 && highSet >= 5) {
            return total - 200;
        }

        // Rule: Four of a Kind
        // -300 score because it's the 3rd highest score possible.
        if (pairsCounter.contains(4)) {
            return total - 300;
        }

        // Rule: Full House
        // -400 score because it's the 4th highest score possible.
        if (pairsCounter.contains(3) && pairsCounter.contains(2)) {
            return total - 400;
        }


        // Rule: Flush
        // -500 score because it's the 5th highest score possible.
        if (highSet >= 5) {
            return total - 500;
        }

        // Rule: Straight
        // -600 score because it's the 6th highest score possible.
        if (sequenceOfCards.size() >= 5) {
            return total - 600;
        }

        // Rule: Three of a Kind
        // -700 score because it's the 7th highest score possible.
        if (pairsCounter.contains(3)) {
            return total - 700;
        }

        if (pairsCounter.contains(2)) {
            // Rule: Two Pairs
            // -800 score because it's the 8th highest score possible.
            if (pairsCounter.indexOf(2) != pairsCounter.lastIndexOf(2)) {
                return total - 800;
            }

            // Rule: One Pair
            // -900 score because it's the 9th highest score possible.
            return total - 900;
        }

        // Rule: High Card
        // -1000 score because it's the 10th highest score possible.
        int highCard = cards.get(cards.size() - 1).getCardValue();
        return (highCard == 1 ? 14 : highCard) - 1000;
    }

    @Override
    public String[] calculateWithHandScore(List<ICard> playerHand) {
        String[] output = new String[2];
        List<ICard> cardsCopy;
        if (cards.isEmpty()) {
            cardsCopy = new ArrayList<>(playerHand);
        } else {
            cardsCopy = new ArrayList<>(cards);
            cardsCopy.addAll(playerHand);
            sortCards(cardsCopy);
        }

        List<ICard> sequenceOfCards = getSequenceList(cardsCopy);       // Use it to get the biggest cards sequence.
        List<Integer> pairsCounter = getPairCounter(cardsCopy);         // Array of cards value pairs.
        int total = totalScore(cardsCopy);                              // Total cards score. (Draws are settled after by a comparator.)

        Map<String, List<ICard>> cardSet = setMapCreator(cardsCopy);    // Organizing the cards by their sets.
        int highSet = setHighSetValue(cardSet);                         // Getting the highest set counter value.

        if (highSet >= 5 && sequenceOfCards.size() >= 5) {
            // Rule: Royal Straight Flush
            // -100 score because it's the 1st highest score possible.
            if (sequenceOfCards.get(sequenceOfCards.size() - 1).getCardValue() == 1) {
                output[0] = Integer.toString(total - 100);
                output[1] = "Royal Straight Flush";

                return output;
            }

            // Rule: Straight Flush
            // -200 score because it's the 2nd highest score possible.
            if (sequenceOfCards.get(sequenceOfCards.size() - 1).getCardValue() != 1) {
                output[0] = Integer.toString(total - 200);
                output[1] = "Straight Flush";

                return output;
            }
        }

        // Rule: Four of a Kind
        // -300 score because it's the 3rd highest score possible.
        if (pairsCounter.contains(4)) {
            output[0] = Integer.toString(total - 300);
            output[1] = "Four of a Kind";

            return output;
        }

        // Rule: Full House
        // -400 score because it's the 4th highest score possible.
        if (pairsCounter.contains(3) && pairsCounter.contains(2)) {
            output[0] = Integer.toString(total - 400);
            output[1] = "Full House";

            return output;
        }


        // Rule: Flush
        // -500 score because it's the 5th highest score possible.
        if (highSet >= 5) {
            output[0] = Integer.toString(total - 500);
            output[1] = "Flush";

            return output;
        }

        // Rule: Straight
        // -600 score because it's the 6th highest score possible.
        if (sequenceOfCards.size() >= 5) {
            output[0] = Integer.toString(total - 600);
            output[1] = "Straight";

            return output;
        }

        // Rule: Three of a Kind
        // -700 score because it's the 7th highest score possible.
        if (pairsCounter.contains(3)) {
            output[0] = Integer.toString(total - 700);
            output[1] = "Three of a Kind";

            return output;
        }

        if (pairsCounter.contains(2)) {
            // Rule: Two Pairs
            // -800 score because it's the 8th highest score possible.
            if (pairsCounter.indexOf(2) != pairsCounter.lastIndexOf(2)) {
                output[0] = Integer.toString(total - 800);
                output[1] = "Two Pairs";

                return output;
            }

            // Rule: One Pair
            // -900 score because it's the 9th highest score possible.
            output[0] = Integer.toString(total - 900);
            output[1] = "One Pairs";

            return output;
        }

        // Rule: High Card
        // -1000 score because it's the 10th highest score possible.
        int highCard = cardsCopy.get(cardsCopy.size() - 1).getCardValue();
        int score = (highCard == 1 ? 14 : highCard) - 1000;
        output[0] = Integer.toString(score);
        output[1] = "High Card";

        return output;
    }

    /**
     * Function to add a single card to the card list.
     *
     * @param singleCard {@link ICard} - Single card.
     */
    @Override
    public void addTableCard(ICard singleCard) {
        if (cards.size() < 5)
            cards.add(singleCard);
    }
}
