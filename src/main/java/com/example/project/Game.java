package com.example.project;
import java.util.ArrayList;


public class Game{
    //static method to determine the winner in a poker game with players p1 and p2, with hands of p1Hand and p2Hand, and community cards
    // represented by the ArrayList communityCards
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards){
        int numWin = getOneOrTwoGreater(Utility.getHandRanking(p1Hand), Utility.getHandRanking(p2Hand));
        if (numWin == 1) {
            return "Player 1 wins!";
        } else if (numWin == 2) {
            return "Player 2 wins!";
        } else {
            if (Utility.getHandRanking(p1Hand) == 1) {
                return greaterHighCard(p1, p2);
            } else if (Utility.getHandRanking(p1Hand) == 2) {
                return greaterHighCard(p1, p2);
            } else {
                return greaterMultiple(p1, p2, communityCards);
            }
        }
    }

    //static utility method to determine the highest ranking between two players p1 and p2 with the communityCards ArrayList
    //this logic solves for highest pair, twopair, three of a kind, or full house
    public static String greaterMultiple(Player p1, Player p2, ArrayList<Card> communityCards) {
        String type = "";
        int count = 0;
        int pairs = 0;
        String p1Pair = "";
        String p1Pair2 = "";
        String p1ThreeOfAKind = "";
        for (int i = 0; i < p1.getAllCards().size(); i++) {
            for (int j = 0; j < p1.getAllCards().size(); j++) {
                if (Utility.getRankValue((p1.getAllCards().get(i).getRank())) == Utility.getRankValue((p1.getAllCards().get(j).getRank()))) {
                    count++;
                }
                if (count == 2) {
                    if (pairs == 1) {
                        type = "twopair";
                        p1Pair2 = p1.getAllCards().get(i).getRank();
                    }
                    p1Pair = p1.getAllCards().get(i).getRank();
                    pairs++;
                }
                if (count == 3) {
                    p1ThreeOfAKind = p1.getAllCards().get(i).getRank();
                }
            }
        }
        if (!p1Pair.equals("")) {
            type = "pair";
        }
        if (!p1ThreeOfAKind.equals("")) {
            type = "three";
        }
        if (!p1Pair.equals("") && !p1ThreeOfAKind.equals("")) {
            type = "fullhouse";
        }
        count = 0;
        pairs = 0;
        String p2Pair = "";
        String p2Pair2 = "";
        String p2ThreeOfAKind = "";
        for (int i = 0; i < p2.getAllCards().size(); i++) {
            for (int j = 0; j < p2.getAllCards().size(); j++) {
                if (Utility.getRankValue((p2.getAllCards().get(i).getRank())) == Utility.getRankValue((p2.getAllCards().get(j).getRank()))) {
                    count++;
                }
                if (count == 2) {
                    if (pairs == 1) {
                        p2Pair2 = p2.getAllCards().get(i).getRank();
                    }
                    p2Pair = p2.getAllCards().get(i).getRank();
                    pairs++;
                }
                if (count == 3) {
                    p2ThreeOfAKind = p2.getAllCards().get(i).getRank();
                }
            }
        }
        int p1HighPairRank = Utility.getRankValue(p1Pair);
        int p2HighPairRank = Utility.getRankValue(p2Pair);
        int p1HighThreeRank = Utility.getRankValue(p1ThreeOfAKind);
        int p2HighThreeRank = Utility.getRankValue(p2ThreeOfAKind);
        if (type.equals("pair")) {
            if (getOneOrTwoGreater(p1HighPairRank, p2HighPairRank) == 1) {
                return "Player 1 wins!";
            } else if (getOneOrTwoGreater(p1HighPairRank, p2HighPairRank) == 2) {
                return "Player 2 wins!";
            } else {
                return greaterHighCard(p1, p2);
            }
        } else if (type.equals("three") || type.equals("fullhouse")) {
            if (getOneOrTwoGreater(p1HighThreeRank, p2HighThreeRank) == 1) {
                return "Player 1 wins!";
            } else if (getOneOrTwoGreater(p1HighThreeRank, p2HighThreeRank) == 2) {
                return "Player 2 wins!";
            } else {
                return greaterHighCard(p1, p2);
            }
        } else {
            int p1Max = getGreater(Utility.getRankValue(p1Pair), Utility.getRankValue(p1Pair2));
            int p2Max = getGreater(Utility.getRankValue(p2Pair), Utility.getRankValue(p2Pair2));
            if (getOneOrTwoGreater(p1Max, p2Max) == 1) {
                return "Player 1 wins!";
            } else if (getOneOrTwoGreater(p1Max, p2Max) == 2) {
                return "Player 2 wins!";
            } else {
                return greaterHighCard(p1, p2);
            }
        }
    }

    //logic to determine the winner between two players p1 and p2 based only on which player
    //has the highest high card in hand
    public static String greaterHighCard(Player p1, Player p2) {
        int p1HighCard = getGreater(Utility.getRankValue(p1.getHand().get(0).getRank()), Utility.getRankValue(p1.getHand().get(1).getRank()));
        int p2HighCard = getGreater(Utility.getRankValue(p2.getHand().get(0).getRank()), Utility.getRankValue(p2.getHand().get(1).getRank()));
        if (p1HighCard == p2HighCard) {
            return "Tie!";
        } else if (p1HighCard > p2HighCard) {
            return "Player 1 wins!";
        } else {
            return "Player 2 wins!";
        }
    }

    //utility method for determining the greater number between two int parameters num1 and num2
    //returns a placeholder value for cases where neither of the inputted values should be returned
    public static int getOneOrTwoGreater(int num1, int num2) {
        if (num1 > num2) {
            return 1;
        } else if (num2 > num1) {
            return 2;
        } else {
            return -1;
        }
    }

    //utility method for determining the greater number between two int parameters num1 and num2
    //returns the greater of the two, if both are equal it returns that value
    public static int getGreater(int num1, int num2) {
        int result = getOneOrTwoGreater(num1, num2);
        if (result == -1) {
            return num1;
        } else if (result == 1) {
            return num1;
        }  else {
            return num2;
        }
    }

    public static void play(){ //simulate card playing
    
    }
        
}       

