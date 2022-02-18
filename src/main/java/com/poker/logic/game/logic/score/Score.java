package com.poker.logic.game.logic.score;

import com.poker.model.card.ICard;
import com.poker.model.filter.Log;

import java.util.*;
import java.util.stream.Collectors;

public class Score implements IScore {
    private static List<ICard> cards;
    private static final Log LOG = Log.getInstance();

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
     * Function to research for sequence formations in the table cards. It returns the size of the highest sequence of cards.
     *
     * @return int - Size of the highest cards sequence.
     */
    private int sequenceValue() {
        int oneSequence = 0, twoSequence = 0, lastValue = 100;
        boolean onlyOne = true;

        for (ICard card : cards) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if ((cardValue - lastValue) == 1 && onlyOne) {
                oneSequence++;
            } else {
                onlyOne = false;
            }

            if ((cardValue - lastValue) == 1 && !onlyOne) {
                twoSequence++;
            }

            lastValue = cardValue;
        }

        return Math.max(oneSequence, twoSequence);
    }

    /**
     * Function to research for sequence formations in table + player hand cards list. It returns the size of the highest sequence of cards.
     *
     * @param sequence {@link List} - Table + player hand cards list.
     * @return int - Size of the highest cards sequence.
     */
    private int sequenceValue(List<ICard> sequence) {
        int oneSequence = 0, twoSequence = 0, lastValue = 100;
        boolean onlyOne = true;

        for (ICard card : sequence) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if ((cardValue - lastValue) == 1 && onlyOne) {
                oneSequence++;
            } else {
                onlyOne = false;
            }

            if ((cardValue - lastValue) == 1 && !onlyOne) {
                twoSequence++;
            }

            lastValue = cardValue;
        }

        return Math.max(oneSequence, twoSequence);
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
     * Function to search for the lowest set formations in the cards list. It returns the size of the lowest set of cards.
     *
     * @return int - Size of the lowest cards set.
     */
    private int setLowSetValue(Map<String, List<ICard>> sets) {
        int lowSet = 0;
        for (String key : sets.keySet()) {
            int setCounter = sets.get(key).size();
            if (lowSet == 0) {
                lowSet = setCounter;
            } else if (lowSet > setCounter) {
                lowSet = setCounter;
            }

        }
        return lowSet;
    }

    /**
     * Function to research for pair formations in the cards list. It returns the size of the highest pair of cards.
     *
     * @return int - Size of the highest cards pair.
     */
    private int pairValue() {
        int pairTransformCount = 0, pairNormalCount = 0, lastValue = 100;
        for (ICard card : cards) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if (cardValue == lastValue) {
                pairTransformCount++;
            }

            lastValue = cardValue;
        }

        lastValue = 100;

        for (ICard card : cards) {
            if (lastValue == 100) {
                lastValue = card.getCardValue();
                continue;
            }

            if (card.getCardValue() == lastValue) {
                pairNormalCount++;
            }

            lastValue = card.getCardValue();
        }
        return Math.max(pairTransformCount, pairNormalCount);
    }

    /**
     * Function to research for pair formations in the cards list. It returns the size of the highest pair of cards.
     *
     * @param pair {@link List} - Table + player hand cards list.
     * @return int - Size of the highest cards pair.
     */
    private int pairValue(List<ICard> pair) {
        int pairTransformCount = 0, pairNormalCount = 0, lastValue = 100;
        for (ICard card : pair) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if (cardValue == lastValue) {
                pairTransformCount++;
            }

            lastValue = cardValue;
        }

        lastValue = 100;

        for (ICard card : pair) {
            if (lastValue == 100) {
                lastValue = card.getCardValue();
                continue;
            }

            if (card.getCardValue() == lastValue) {
                pairNormalCount++;
            }

            lastValue = card.getCardValue();
        }
        return Math.max(pairTransformCount, pairNormalCount);
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

        return cardSet;
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

        return cardSet;
    }

    /**
     * Function to create an organized List with the card's appearance value organized by the times they appear.
     *
     * @return List - List of Integer Objects with the table card's value organized by the times they appear.
     */
    private List<Integer> getPairCounter() {
        Map<Integer, Integer> pairCounter = new HashMap<>();

        // Getting the number of cards of the same value
        for (ICard card : cards) {
            if (!pairCounter.containsKey(card.getCardValue())) {
                pairCounter.put(card.getCardValue(), 1);
            } else {
                pairCounter.replace(card.getCardValue(), pairCounter.get(card.getCardValue()) + 1);
            }
        }

        return pairCounter.values().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    /**
     * Function to create an organized List with the card's appearance value organized by the times they appear.
     *
     * @return List - List of Integer Objects with the table card's value organized by the times they appear.
     */
    private List<Integer> getPairCounter(List<ICard> cardsCopy) {
        Map<Integer, Integer> pairCounter = new HashMap<>();

        // Getting the number of cards of the same value
        for (ICard card : cardsCopy) {
            if (!pairCounter.containsKey(card.getCardValue())) {
                pairCounter.put(card.getCardValue(), 1);
            } else {
                pairCounter.replace(card.getCardValue(), pairCounter.get(card.getCardValue()) + 1);
            }
        }

        return pairCounter.values().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    /**
     * Function to verify if the all the cards are from the same set.
     *
     * @param cardSet {@link Map} - Map with all the cards organized by their respective set.
     * @return boolean - If all the cards are from the same set.
     */
    public boolean isSameSet(Map<String, List<ICard>> cardSet) {
        for (String set : cardSet.keySet()) {
            if (cardSet.get(set).size() == 5) {
                return true;
            }
        }

        return false;
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
        Map<String, List<ICard>> cardSet = setMapCreator();
        int highSet = setHighSetValue(cardSet);

        cardSet = uselessSet(cardSet);
        List<Integer> pairsCounter = getPairCounter();

        // Rule: Royal Straight Flush
        // -100 score because it's the 1st highest score possible.
        if (sequenceValue() == 4 && cards.get(cards.size() - 1).getCardValue() == 1 && isSameSet(cardSet)) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            LOG.addAndShowLog("\nRoyal Straight Flush detected!\n");

            return total - 100;
        }

        // Rule: Straight Flush
        // -200 score because it's the 2nd highest score possible.
        if (sequenceValue() == 4 && cards.get(cards.size() - 1).getCardValue() != 1 && isSameSet(cardSet)) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            LOG.addAndShowLog("\nStraight Flush detected!\n");

            return total - 200;
        }

        // Rule: Four of a Kind
        // -300 score because it's the 3rd highest score possible.
        if (pairValue() == 4) {
            int total = 0;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            LOG.addAndShowLog("\nFour of a Kind detected!\n");

            return total - 300;
        }

        // Rule: Full House
        // -400 score because it's the 4th highest score possible.
        if (pairValue() == 3 && pairsCounter.get(1) == 2) {
            int total = 0;
            for (int i = 0, k = 0; i < pairsCounter.size(); i++) {
                if (pairsCounter.get(i) >= 2 && total == 0) {
                    for (int j = 0; j < pairsCounter.get(i); j++) {
                        if (total == 0) {
                            total = cards.get(j + k).getCardValue();
                        } else {
                            total += cards.get(j + k).getCardValue();
                        }
                    }
                } else if (pairsCounter.get(i) >= 2) {
                    for (int j = 0; j < pairsCounter.get(i); j++) {
                        if (total == 0) {
                            total = cards.get(j + k).getCardValue();
                        } else {
                            total += cards.get(j + k).getCardValue();
                        }
                    }
                }

                k += pairsCounter.get(i);
            }

            LOG.addAndShowLog("\nFull House detected!\n");

            return total - 400;
        }


        // Rule: Flush
        // -500 score because it's the 5th highest score possible.
        if (sequenceValue() == 0 && pairValue() == 0 && highSet == 5) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            LOG.addAndShowLog("\nFlush detected!\n");

            return total - 500;
        }

        // Rule: Straight
        // -600 score because it's the 6th highest score possible.
        if (sequenceValue() == 4) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            LOG.addAndShowLog("\nStraight detected!\n");

            return total - 600;
        }

        // Rule: Three of a Kind
        // -700 score because it's the 7th highest score possible.
        if (pairValue() == 3) {
            int total = 0;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            LOG.addAndShowLog("\nThree of a Kind detected!\n");

            return total - 700;
        }

        // Rule: Two Pairs
        // -800 score because it's the 8th highest score possible.
        if (pairValue() == 2) {
            int total = 0;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            LOG.addAndShowLog("\nTwo Pairs detected!\n");

            return total - 800;
        }

        // Rule: One Pair
        // -900 score because it's the 9th highest score possible.
        if (pairValue() == 1) {
            int total = 0;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            LOG.addAndShowLog("\nOne Pairs detected!\n");

            return total - 900;
        }

        // Rule: High Card
        // -1000 score because it's the 10th highest score possible.
        if (pairValue() == 0) {
            int highCard = cards.get(cards.size() - 1).getCardValue();

            LOG.addAndShowLog("\nHigh Card detected!\n");

            return (highCard == 1 ? 14 : highCard) - 1000;
        }

        LOG.addAndShowLog("\nERROR - Nothing detected!\n");
        return 0;
    }

    @Override
    public String[] calculateWithHandScore(List<ICard> playerHand) {
        String[] output = new String[2];
        if (cards.isEmpty()) {
            return output;
        }
        List<ICard> cardsCopy = new ArrayList<>(cards);
        cardsCopy.addAll(playerHand);
        sortCards(cardsCopy);

        Map<String, List<ICard>> cardSet = setMapCreator(cardsCopy);
        int highSet = setHighSetValue(cardSet);

        cardSet = uselessSet(cardSet);
        List<Integer> pairsCounter = getPairCounter(cardsCopy);

        // Rule: Royal Straight Flush
        // -100 score because it's the 1st highest score possible.
        if (sequenceValue(cardsCopy) == 4 && cardsCopy.get(cardsCopy.size() - 1).getCardValue() == 1 && isSameSet(cardSet)) {
            int total = 0, score;
            for (ICard card : cardsCopy) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            score = total - 100;

            // LOG.addAndShowLog("Royal Straight Flush detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Royal Straight Flush";

            return output;
        }

        // Rule: Straight Flush
        // -200 score because it's the 2nd highest score possible.
        if (sequenceValue(cardsCopy) == 4 && cardsCopy.get(cardsCopy.size() - 1).getCardValue() != 1 && isSameSet(cardSet)) {
            int total = 0, score;
            for (ICard card : cardsCopy) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            score = total - 200;

            // LOG.addAndShowLog("Straight Flush detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Straight Flush";

            return output;
        }

        // Rule: Four of a Kind
        // -300 score because it's the 3rd highest score possible.
        if (pairValue(cardsCopy) == 4) {
            int total = 0, score;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            score = total - 300;

            // LOG.addAndShowLog("Four of a Kind detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Four of a Kind";

            return output;
        }

        // Rule: Full House
        // -400 score because it's the 4th highest score possible.
        if (pairValue(cardsCopy) == 3 && pairsCounter.get(1) == 2) {
            int total = 0, score;
            for (int i = 0, k = 0; i < pairsCounter.size(); i++) {
                if (pairsCounter.get(i) >= 2 && total == 0) {
                    for (int j = 0; j < pairsCounter.get(i); j++) {
                        if (total == 0) {
                            total = cardsCopy.get(j + k).getCardValue();
                        } else {
                            total += cardsCopy.get(j + k).getCardValue();
                        }
                    }
                } else if (pairsCounter.get(i) >= 2) {
                    for (int j = 0; j < pairsCounter.get(i); j++) {
                        if (total == 0) {
                            total = cardsCopy.get(j + k).getCardValue();
                        } else {
                            total += cardsCopy.get(j + k).getCardValue();
                        }
                    }
                }

                k += pairsCounter.get(i);
            }

            score = total - 400;

            // LOG.addAndShowLog("Full House detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Full House";

            return output;
        }


        // Rule: Flush
        // -500 score because it's the 5th highest score possible.
        if (sequenceValue(cardsCopy) == 0 && pairValue(cardsCopy) == 0 && highSet == 5) {
            int total = 0, score;
            for (ICard card : cardsCopy) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            score = total - 500;

            // LOG.addAndShowLog("Flush detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Flush";

            return output;
        }

        // Rule: Straight
        // -600 score because it's the 6th highest score possible.
        if (sequenceValue(cardsCopy) == 4) {
            int total = 0, score;
            for (ICard card : cardsCopy) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }

            score = total - 600;

            // LOG.addAndShowLog("Straight detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Straight";

            return output;
        }

        // Rule: Three of a Kind
        // -700 score because it's the 7th highest score possible.
        if (pairValue(cardsCopy) == 3) {
            int total = 0, score;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            score = total - 700;

            // LOG.addAndShowLog("Three of a Kind detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Three of a Kind";

            return output;
        }

        // Rule: Two Pairs
        // -800 score because it's the 8th highest score possible.
        if (pairValue(cardsCopy) == 2) {
            int total = 0, score;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            score = total - 800;

            // LOG.addAndShowLog("Two Pairs detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "Two Pairs";

            return output;
        }

        // Rule: One Pair
        // -900 score because it's the 9th highest score possible.
        if (pairValue(cardsCopy) == 1) {
            int total = 0, score;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }

            score = total - 900;

            // LOG.addAndShowLog("One Pairs detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "One Pairs";

            return output;
        }

        // Rule: High Card
        // -1000 score because it's the 10th highest score possible.
        if (pairValue() == 0) {
            int highCard = cardsCopy.get(cardsCopy.size() - 1).getCardValue();
            int score = (highCard == 1 ? 14 : highCard) - 1000;

            // LOG.addAndShowLog("High Card detected!\t\t-\tScore: " + score);

            output[0] = Integer.toString(score);
            output[1] = "High Card";

            return output;
        }

        // LOG.addAndShowLog("ERROR SCORING - Nothing was detected!");
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

    /**
     * Function to add multiple cards to the card list.
     *
     * @param multipleCards {@link ICard} - List of cards.
     */
    @Override
    public void addTableCard(List<ICard> multipleCards) {
        if (cards.size() + multipleCards.size() < 4)
            cards.addAll(multipleCards);
    }
}
