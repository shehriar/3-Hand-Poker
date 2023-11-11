import java.util.*;

public class ThreeCardLogic {
    // High => Low rank: ace, king, queen, jack, 10, 9, 8, 7, 6, 5, 4, 3, 2
    // All suits shall be considered equal in rank
    // If both the dealer and the player have the same pair plus combo, sum the card ranks and compare.
    public ArrayList<String> dealers = new ArrayList<>(); // Stores the image name of dealer's cards
    public ArrayList<String> players = new ArrayList<>(); // Stores the image name of player's cards
    public boolean dealerHasQueenOrHigher;
    public int playerPairPlusMultiplier;
    public boolean playerWins;

    public ThreeCardLogic() {
        ArrayList<Card> deck = new ArrayList<>();
        // Fills the deck with all the cards
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        // Shuffle the deck
        Collections.shuffle(deck);
        ArrayList<Card> dealersCards = new ArrayList<>();
        ArrayList<Card> playersCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Card dc = deck.remove(0);
            dealersCards.add(dc);
            dealers.add(dc.toImageStr());
            Card pc = deck.remove(0);
            playersCards.add(pc);
            players.add(pc.toImageStr());
        }
        dealerHasQueenOrHigher = dealerHasQueenOrHigher(dealersCards);
        playerPairPlusMultiplier = evaluatePairPlus(playersCards);
        playerWins = checkPlayerWins(dealersCards, playersCards);
    }

    public static boolean dealerHasQueenOrHigher(ArrayList<Card> dealersCards) {
        for (Card card : dealersCards) {
            if (card.rank == Rank.Q || card.rank == Rank.K || card.rank == Rank.A) {
                return true;
            }
        }
        return false;
    }

    public static int evaluatePairPlus(ArrayList<Card> hand) {
        if (isStraightFlush(hand)) {
            return 40;
        } else if (isThreeOfAKind(hand)) {
            return 30;
        } else if (isStraight(hand)) {
            return 6;
        } else if (isFlush(hand)) {
            return 3;
        } else if (isPair(hand)) {
            return 1;
        }
        return 0;
    }

    public static boolean isStraightFlush(ArrayList<Card> hand) {
        // Sort hand by rank
        Collections.sort(hand, (c1, c2) -> c1.rank.ordinal() - c2.rank.ordinal());
        // Check if hand are in sequence and of the same suit
        Suit suit = hand.get(0).suit;
        for (int i = 1; i < 3; i++) {
            if (hand.get(i).suit != suit || hand.get(i).rank.ordinal() != hand.get(i-1).rank.ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isThreeOfAKind(ArrayList<Card> hand) {
        Rank rank = hand.get(0).rank;
        return hand.get(1).rank == rank && hand.get(2).rank == rank;
    }

    public static boolean isStraight(ArrayList<Card> hand) {
        // Sort hand by rank
        Collections.sort(hand, (c1, c2) -> c1.rank.ordinal() - c2.rank.ordinal());
        // Check if hand are in sequence
        for (int i = 1; i < 3; i++) {
            if (hand.get(i).rank.ordinal() != hand.get(i-1).rank.ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFlush(ArrayList<Card> hand) {
        Suit firstSuit = hand.get(0).suit;
        Suit secondSuit = hand.get(1).suit;
        Suit thirdSuit = hand.get(2).suit;
        return firstSuit == secondSuit && secondSuit == thirdSuit;
    }

    public static boolean isPair(ArrayList<Card> hand) {
        Rank firstRank = hand.get(0).rank;
        Rank secondRank = hand.get(1).rank;
        Rank thirdRank = hand.get(2).rank;
        return (firstRank == secondRank) || (firstRank == thirdRank) || (secondRank == thirdRank);
    }

    public static boolean checkPlayerWins(ArrayList<Card> dealersCards, ArrayList<Card> playersCards) {
        int dealerPairPlusMultiplier = evaluatePairPlus(dealersCards);
        int playerPairPlusMultiplier = evaluatePairPlus(playersCards);
        // The player wins with a greater pair plus combo.
        // Otherwise, if both the dealer and the player have the same pair plus combo,
        // the player wins with a greater sum of card ranks.
        if (playerPairPlusMultiplier > dealerPairPlusMultiplier) {
            return true;
        } else if (playerPairPlusMultiplier == dealerPairPlusMultiplier) {
            int playerRankSum = playersCards.get(0).rank.ordinal() + playersCards.get(1).rank.ordinal() + playersCards.get(2).rank.ordinal();
            int dealerRankSum = dealersCards.get(0).rank.ordinal() + dealersCards.get(1).rank.ordinal() + dealersCards.get(2).rank.ordinal();
            return playerRankSum > dealerRankSum;
        }
        return false;
    }

    public int calculateWinningsFold(int pp, int a) {
        int result = 0;
        result += pp * playerPairPlusMultiplier;
        return result - pp - a;
    }

    public int calculateWinningsPlay(int pp, int a) {
        int play = a;
        int result = 0;
        result += pp * playerPairPlusMultiplier;
        if (playerWins) {
            result += a * 2;
            result += play * 2;
        }
        return result - pp - a - play;
    }
}