package com.poker.logic.score;

import com.poker.model.card.ICard;

import java.util.*;

public class Score implements IScore {
    private static List<ICard> cards;
    private static Score scoreInstance = null;

    private Score() {
        if (cards == null) {
            cards = new ArrayList<>();
        }
    }

    public static Score getInstance() {
        if (scoreInstance == null)
            scoreInstance = new Score();

        return scoreInstance;
    }

    /**
     * Function to sort the table cards list to easier research sequence formations.
     */
    private void sortCards() {
        Collections.sort(cards, (o1, o2) -> {
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
        Collections.sort(newCards, (o1, o2) -> {
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

        return (oneSequence >= twoSequence ? oneSequence : twoSequence);
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

        return (oneSequence >= twoSequence ? oneSequence : twoSequence);
    }

    /**
     * Function to research for set formations in the cards list. It returns the size of the highest set of cards.
     *
     * @return int - Size of the highest cards set.
     */
    private int setValue(Map<String, List<ICard>> sets) {
        int highSet = 0;
        for (String key : sets.keySet()) {
            int setCounter = sets.get(key).size();
            if (highSet == 0){
                highSet = setCounter;
            } else if (highSet < setCounter) {
                highSet = setCounter;
            }

        }
        return highSet;
    }

    /**
     * Function to research for pair formations in the cards list. It returns the size of the highest pair of cards.
     *
     * @return int - Size of the highest cards pair.
     */
    private int pairValue() {
        int pairCount = 0, lastValue = 100;
        for (ICard card : cards) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if (cardValue == lastValue) {
                pairCount++;
            }

            lastValue = cardValue;
        }
        return pairCount;
    }

    /**
     * Function to research for pair formations in the cards list. It returns the size of the highest pair of cards.
     *
     * @param pair {@link List} - Table + player hand cards list.
     * @return int - Size of the highest cards pair.
     */
    private int pairValue(List<ICard> pair) {
        int pairCount = 0, lastValue = 100;
        for (ICard card : pair) {
            int cardValue = (card.getCardValue() == 1 ? 14 : card.getCardValue());

            if (lastValue == 100) {
                lastValue = cardValue;
                continue;
            }

            if (cardValue == lastValue) {
                pairCount++;
            }

            lastValue = cardValue;
        }
        return pairCount;
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
        int highSet = setValue(cardSet);

        // FIXME: Cut this part out. It's only for easier debugging visualization.
        for (ICard card : cards) {
            System.out.println("Table: " + card.getStringCardValue());
        }

        // FIXME: Cut this part out. It's only for easier debugging visualization.
        for (String set : cardSet.keySet()) {
            System.out.println("\n" + set);
            for (ICard card : cardSet.get(set)) {
                System.out.println("Before useless set cutter: " + card.getCardValue());
            }
            System.out.println("\n");
        }

        cardSet = uselessSet(cardSet);

        // FIXME: Cut this part out.
        for (String set : cardSet.keySet()) {
            System.out.println("\n" + set);
            for (ICard card : cardSet.get(set)) {
                System.out.println(card.getCardValue());
            }
            System.out.println("\n");
        }

        // FIXME: Cut this part out. It's only for easier debugging visualization.
        System.out.println("Number of sequences on the table: " + sequenceValue());
        System.out.println("Number of pairs on the table: " + pairValue());


        // Rule: Royal Straight Flush
        // -100 score because it's the 1st highest score possible.
        // FIXME: Verify cards list set. It needs to be all the same.
        if (sequenceValue() == 4 && cards.get(cards.size() - 1).getCardValue() == 1) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }
            return total - 100;
        }

        // Rule: Straight Flush
        // -200 score because it's the 2nd highest score possible.
        // FIXME: Verify cards list set. It needs to be all the same.
        if (sequenceValue() == 4 && cards.get(cards.size() - 1).getCardValue() != 1) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }
            return total - 200;
        }

        // Rule: Four of a Kind
        // -300 score because it's the 3th highest score possible.
        if (pairValue() == 4) {
            int total = 0;
            for (String key : cardSet.keySet()) {
                for (ICard card : cardSet.get(key)) {
                    total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
                }
            }
            return total - 300;
        }

        // TODO: other rules.

        // Rule: Straight
        // -500 score because it's the 5th highest score possible.
        if (sequenceValue() == 0 && pairValue() == 0 && highSet == 5) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }
            return total - 500;
        }

        // Rule: Straight
        // -600 score because it's the 6th highest score possible.
        if (sequenceValue() == 4) {
            int total = 0;
            for (ICard card : cards) {
                total += (card.getCardValue() == 1 ? 14 : card.getCardValue());
            }
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
            return total - 900;
        }

        // Rule: High Card
        // -1000 score because it's the 10th highest score possible.
        // FIXME: In the end this will be the last rule and the last return of score needed by this function.
        if (pairValue() == 0 && sequenceValue() == 0) {
            int highCard = cards.get(cards.size() - 1).getCardValue();
            return (highCard == 1 ? 14 : highCard) - 1000;
        }

        return 9999;
    }

    // TODO: Work in progress. It will implement all the logic from function calculateTableScore().
    @Override
    public int calculateWithHandScore(List<ICard> playerHand) {
        List<ICard> cardsCopy = new ArrayList<>(cards);
        cardsCopy.addAll(playerHand);
        cardsCopy = sortCards(cardsCopy);

        // FIXME: Cut this part out.
        for (ICard card : cardsCopy) {
            System.out.println(card.getStringCardValue());
        }

        return 0;
    }

    @Override
    public void addTableCard(ICard singleCard) {
        if (cards.size() < 5)
            cards.add(singleCard);
    }

    @Override
    public void addTableCard(List<ICard> multipleCards) {
        if (cards.size() + multipleCards.size() < 4)
            cards.addAll(multipleCards);
    }
}
