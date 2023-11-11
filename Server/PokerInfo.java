import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    int ante;
    int pairPlus;
    ArrayList<String> dealersCardsImg;
    ArrayList<String> playersCardsImg;
    boolean dealerHasQueenOrHigher;
    boolean playerWins;
    int winningsFold;
    int winningsPlay;
    String ppCombo;

    PokerInfo() {
    }
}