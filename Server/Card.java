enum Suit {
    heart,
    diamond,
    club,
    spade
}

enum Rank {
    two,
    three,
    four,
    five,
    six,
    seven,
    eight,
    nine,
    ten,
    J,
    Q,
    K,
    A
}

class Card {
    public final Suit suit;
    public final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toImageStr() {
        return rank + "" + suit + ".png";
    }
}