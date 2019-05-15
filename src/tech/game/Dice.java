package tech.game;

import java.util.*;

public class Dice {

    Random rand = new Random();

    private int diceOne;
    private int diceTwo;

    private final int MAX_ROLL = 6;


    public int getDiceOne() {
        return diceOne;
    }

    public int getDiceTwo() {
        return diceTwo;
    }


    /**
     * method to roll the two dice
     */
    public void roll() {
        System.out.println("Rolling dice...");
        diceOne = rand.nextInt(MAX_ROLL) + 1;
        diceTwo = rand.nextInt(MAX_ROLL) + 1;

    }

    /**
     * method will call the roll method and return sum
     *
     * @return the sum of diceOne and diceTwo
     */
    public int rollTotal() {
        roll();
        System.out.printf("%d + %d = ", diceOne, diceTwo);
        return diceOne + diceTwo;
    }

}
