package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){ //Player constructor to construct a hand ArrayList
        hand = new ArrayList<>();
    }
    //getter method for the player's hand
    public ArrayList<Card> getHand(){return hand;}
    //getter method for the player's hand and the community cards
    public ArrayList<Card> getAllCards(){return allCards;}
    //void method to add a card to the player's hand
    public void addCard(Card c){
        hand.add(c);
    }
    //method to simulate playing a poker hand with an ArrayList parameter communityCards
    public String playHand(ArrayList<Card> communityCards){      
        allCards = new ArrayList<Card>();
        for (int i = 0; i < communityCards.size(); i++) {
            allCards.add(communityCards.get(i));
        }
        for (int j = 0; j < hand.size(); j++) {
            allCards.add(hand.get(j));
        }
        if (checkFlushStraight(allCards) != "Nothing") {
            return checkFlushStraight(allCards);
        } else if (checkMultiples(allCards) != "Nothing") {
            return checkMultiples(allCards);
        } else if (checkHighCard(communityCards, hand) != "Nothing") {
            return checkHighCard(communityCards, hand);
        } else {
            return "Nothing";
        }
    }

    //method to check if a player has a higher rank card in hand than the highest in the communityCards card pool
    public String checkHighCard(ArrayList<Card> communityCards, ArrayList<Card> handCards) {
        int comMax = 0;
        int handMax = 0;
        for (Card card : communityCards) {
            if (Utility.getRankValue(card.getRank()) > comMax) {
                comMax = Utility.getRankValue(card.getRank());
            }
        }
        for (Card card : handCards) {
            if (Utility.getRankValue(card.getRank()) > handMax) {
                handMax = Utility.getRankValue(card.getRank());
            }
        }
        if (handMax > comMax) {
            return "High Card";
        }
        return "Nothing";
    }

    //method to check if a player has a flush, straight, straight flush, or royal flush
    public String checkFlushStraight(ArrayList<Card> cards) {
        boolean flush = false;
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (int j = 0; j < cards.size(); j++) {
                if (cards.get(j).getSuit() == suits[i]) {
                    count++;
                }
            }
            if (count == 5) {
                flush = true;
            }
        }
        boolean straight = false;
        boolean royal = false;
        ArrayList<Integer> ranksList = new ArrayList<Integer>();
        for (int q = 0; q < cards.size(); q++) {
            ranksList.add(Utility.getRankValue(cards.get(q).getRank()));
        }
        for (int x = 2; x < 11; x++) {
            if (ranksList.indexOf(x) > -1 && ranksList.indexOf(x + 1) > -1 && ranksList.indexOf(x + 2) > -1
             && ranksList.indexOf(x + 3) > -1 && ranksList.indexOf(x + 4) > -1) {
                if (x == 10) {
                    royal = true;
                }
                straight = true;
            }
        }
        if (flush && royal) {
            return "Royal Flush";
        } else if (flush && straight) {
            return "Straight Flush";
        } else if (flush) {
            return "Flush";
        } else if (straight) {
            return "Straight";
        } else {
            return "Nothing";
        }
    }

    //method to check if a player has a pair, twopair, three of a kind, or a full house
    public String checkMultiples(ArrayList<Card> cards) {
        int alt = 0;
        String used = "";
        for (int i = 0; i < cards.size(); i++) {
            int count = 0;
            for (int j = 0; j < cards.size(); j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank() && cards.get(i).getRank() != used) {
                    count++;
                }
            }
            used = cards.get(i).getRank();
            if (count >= 2) {
                if (count == 4) {
                    return "Four of a Kind";
                }
                if (count + alt == 4) {
                    return "Two Pair";
                }
                if (count + alt == 5) {
                    return "Full House";
                }
                if (count >= 2) {
                    alt = count;
                }
            }
            if (i == cards.size() - 1) {
                if (alt == 3) {
                    return "Three of a Kind";
                }
                if (alt == 2) {
                    return "A Pair";
                }
            }
        }
        return "Nothing";
    }

    //method to sort cards by rank value order
    public void sortAllCards() {
        for (int i = 1; i < allCards.size(); i++) {
            Card key = allCards.get(i);
            int keyRank = Utility.getRankValue(key.getRank());
            int j = i - 1;
            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > keyRank) {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }
            allCards.set(j + 1, key);
        }
    }

    //method to find the frequency of each rank, returns an ArrayList with the frequencies in order
    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> freq = new ArrayList<Integer>(); 
        for (int i = 0; i < 13; i++) {
            int count = 0;
            for (int j = 0; j <allCards.size(); j++) {
                if (allCards.get(j).getRank() == ranks[i]) {
                    count++;
                }
            }
            freq.add(count);
        }
        return freq;
    }

    //method to find the frequency of each suit, returns an ArrayList with the suits in order
    public ArrayList<Integer> findSuitFrequency(){ // spades, hearts, clubs, diamonds
        ArrayList<Integer> freq = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (int j = 0; j < allCards.size(); j++) {
                if (allCards.get(j).getSuit() == suits[i]) {
                    count++;
                }
            }
            freq.add(count);
        }
        return freq;
    }

   //toString method
    @Override
    public String toString(){
        return hand.toString();
    }




}
