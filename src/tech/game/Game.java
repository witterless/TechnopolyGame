package tech.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {

    //starting money for player and the pass go reward
    private static final int STARTING_MONEY = 1000;
    private static final int PASS_GO_REWARD = 100;

    // tracking number of properties owned by player
    private int numberOfProperties = 0;

    private Scanner scanner = new Scanner(System.in);

    // Get board and dice
    private Board board = new Board();
    private Dice dice = new Dice();

    /**
     * Start method which will be called in Main Class to start the game.
     */
    public void start() {

        welcomeMessage();
        // Get number of players
        int numberOfPlayers = getNumberOfPlayers();

        // Get players

        List<Player> players = getPlayers(numberOfPlayers);

        int currentPlayer = 0;

        //loop over players
        while (true) {
            // Get player
            Player player = players.get(currentPlayer);
            System.out.println("\nCurrent player is : " + player.getPlayerName());

            // Take turn
            boolean finishedTurn = takeTurn(player);

            // Finish turn
            if (finishedTurn) {
                currentPlayer = (currentPlayer + 1) % players.size();
            } else {
                finishGame(players);
                break;
            }


        }
    }

    /**
     * method to ask user if they would like to take turn. If they say Y then they will proceed to roll dice
     * and move around board.
     * If player says N then false will be returned and the getLeaderboard method will be called.
     *
     * @param player current player
     * @return true or false
     */
    private boolean takeTurn(Player player) {
        //Ask player if they want to take turn
        if (!answeredYes("Would you like to take your turn - Y or N?")) {
            return false;

        }

        rollAndMove(player);

        //tell player where they landed
        Square square = board.getSquare(player.getPosition());
        System.out.println("\nYou have £" + player.getMoney());
        System.out.println("You have landed on square : " + square.getName());


        // if square type is property then cast to property
        if (square instanceof Property) {
            Property prop = (Property) square;

            //check if square is owned
            if (prop.getOwner() == null) {
                //if square is available then ask player if they can afford and then if they want to buy
                System.out.println("This property has no owner.");
                if (player.canAfford(prop.getBuyCost())) {
                    if (answeredYes("\nWould you like to buy this property for £" + prop.getBuyCost() + " Y or N?")) {
                        player.decreaseMoney(prop.getBuyCost());
                        prop.setOwner(player);

                        // adding on to number of properties
                        numberOfProperties++;
                        System.out.println("You bought this property and now have £" + player.getMoney());

                        // check if player has at least 3 properties and upgrade the rental price for property
                        upgradeProperty(numberOfProperties, prop);
                    }
                }
            } else if (prop.getOwner().equals(player)) {
                System.out.println("This is your property. Stay rent free!");
            } else {
                //player will have to pay rent
                System.out.println("This property is owned by " + prop.getOwner().getPlayerName() + ". The rent is £" + prop.getRentCost());

                // if square is owned then check if player can afford rent
                int canAfford = Math.min(player.getMoney(), prop.getRentCost());

                // move rent payment to the property owner
                player.decreaseMoney(canAfford);
                prop.getOwner().increaseMoney(canAfford);

                if (canAfford < prop.getRentCost()) {
                    //if can't afford rent then player bankrupt and game ends
                    System.out.println("You could only afford to pay £" + canAfford + "! You have ran out of money!");
                    return false;
                } else {
                    System.out.println(player.getPlayerName() + " you paid the rent in full and now have £" + player.getMoney());
                    if (player.getMoney() == 0) {
                        System.out.println("You are bankrupt! The game will now end.");
                        return false;
                    }
                }
            }
            //if player lands on free parking they will receive reward
        } else if (square instanceof FreeParking) {
            FreeParking freeParking = (FreeParking) square;
            player.increaseMoney(freeParking.getReward());
            System.out.println("You collected the reward of £" + freeParking.getReward());
        }


        System.out.println(player.getPlayerName() + " you have finished your turn.");
        //go to next player
        return true;
    }

    /**
     * Method takes two parameters once player reaches owning 3 properties they will be asked would they like to
     * upgrade their property to a Corporation and their rent will be increased by 100
     *
     * @param numberOfProperties number of properties owned by player
     * @param property           current property they are buying
     */
    private void upgradeProperty(int numberOfProperties, Property property) {
        if (numberOfProperties % 3 == 0) {
            if (answeredYes("Do you want to upgrade your 3 properties to a Corporation?")) {
                property.addCorpTaxToRent();
                System.out.println("Congratulations! You now own a Corporation!");
            }

        }
    }

    /**
     * method to move player's around board. Will also allow players to collect Go reward once on or passed Go
     *
     * @param player current player
     */
    private void rollAndMove(Player player) {
        //player rolls and moves around board
        int rollTotal = dice.rollTotal();

        System.out.println(rollTotal);

        int total = player.getPosition() + rollTotal;

        if (total >= board.getBoardSize()) {
            player.setMoney(player.getMoney() + PASS_GO_REWARD);
            System.out.println("You passed go and collected £" + PASS_GO_REWARD);
        }

        int newPosition = total % board.getBoardSize();
        player.setPosition(newPosition);
    }

    /**
     * Once the game has ended because player says N when asked to take turn, a leaderboard will show and declare the
     * winner of the game
     *
     * @param playerList list of players
     */
    private void finishGame(List<Player> playerList) {
        System.out.println("The game has ended. This is the leaderboard");

        Collections.sort(playerList);

        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            System.out.println("Position " + (i + 1) + ": " + player.getPlayerName() + " finished with £" + player.getMoney());
        }
    }

    /**
     * Will ask how many players for the game and only continue if between 2-4 is picked
     *
     * @return numOfPlayers
     */
    private int getNumberOfPlayers() {

        int numOfPlayers;

        System.out.println("How many Players? (Must be between 2-4) : ");

        while (true) {
            if (scanner.hasNextInt()) {
                numOfPlayers = scanner.nextInt();
                if (numOfPlayers >= 2 && numOfPlayers <= 4) {
                    return numOfPlayers;
                }
            } else {
                System.out.println("Must be a number between 2 - 4. Try again.");
                scanner.next();
            }
        }
    }

    /**
     * get players method will ask players to input their names and the player object in an arraylist played playerList
     * playerList will be returned
     *
     * @param numberOfPlayers number of players gained from users
     * @return playerList playerList generated
     */
    private List<Player> getPlayers(int numberOfPlayers) {
        List<Player> playerList = new ArrayList<>();
        String name;

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Name of player " + (i + 1));
            name = scanner.next();

            //Validate names
            boolean valid = true;
            for (int j = i - 1; j >= 0; j--) {
                if (name.equalsIgnoreCase(playerList.get(j).getPlayerName())) {
                    valid = false;
                }
            }

            if (valid) {
                playerList.add(new Player(name, STARTING_MONEY, 0));
            } else {
                System.out.println("Name \"" + name + "\" has already been entered, please try again.");
                i--;
            }
        }

        return playerList;
    }

    /**
     * Scanner method to call when user is being asked question
     *
     * @param question question asked to user
     * @return true or false
     */
    private boolean answeredYes(String question) {
        String answer;
        while (true) {
            System.out.println(question);
            answer = scanner.next();
            if (answer.equalsIgnoreCase("n")) {
                return false;
            } else if (answer.equalsIgnoreCase("y")) {
                return true;
            }
        }
    }


    /**
     * To show Welcome message when game starts
     */
    private void welcomeMessage() {
        System.out.println(
                "                                  -+ooo:       ++//++                                    \n" +
                        "                               -sy+-`dMN-     :+y++so                                    \n" +
                        "                             -hN:    -NMd`    +:y.                                       \n" +
                        "                            oNMMN:   .sNdy`   .y:y:                                      \n" +
                        "                           +mMMMMN+++-`-mMh.-h+`o++s`                                    \n" +
                        "                           +s+NMMhs. `.-+ydmm-   -s:s:                                   \n" +
                        "                   -:       :homshmysy+/:-`.ss`   `/o+s`                                 \n" +
                        "                  osh        :dsdso+:o+`    `:y     `s+sos+-                             \n" +
                        "              ./syh`h`       .d++y-  - `    :/o/      oy+s:/o`                           \n" +
                        "           .+ys:.`o :mo+/.   h/sh//   -s  `/  -y`.   `y-so` :+                           \n" +
                        "          oso++   ` +osMMNh+`d:Ny:.      `.do+/ds+    s`o:o/`o                           \n" +
                        "         .y-/o++oooy+yMMMMMMNddm:   `  -o/``  .-y-   :yh+yysyy+                          \n" +
                        "          `soo/-`` `:dMMMMMMMMMNhy+o`so++  `o/:+s-   .md+hss/.yyy.                       \n" +
                        "                    `./smMMMMMMMNdMd:++//os/.y/    :mMMNooh`  /yy+                       \n" +
                        "                         -yMMMMMMMMMhs+-.`.+mdo++smMMMMMMNo    `oys-                     \n" +
                        "                           -dMMMMMMMd ++++:ohMMMMMMMMMMMMh.      -yy+`                   \n" +
                        "                            `dMMMMMMM/sssosodMMMMMMMMMMm/         `+ys-                  \n" +
                        "                             :MMMMMMMN/`  `:MMMMMMMMMd+`            .s:                  \n" +
                        "                             /MNhdhdMM/    sMMMMMNho-                                    \n" +
                        "   Y8b Y8b Y888P 888'Y88 888       e88'Y88   e88 88e       e   e     888'Y88             \n" +
                        "    Y8b Y8b Y8P  888 ,'Y 888      d888  'Y  d888 888b     d8b d8b    888 ,'Y             \n" +
                        "     Y8b Y8b Y   888C8   888     C8888     C8888 8888D   e Y8b Y8b   888C8               \n" +
                        "      Y8b Y8b    888 \",d 888  ,d  Y888  ,d  Y888 888P   d8b Y8b Y8b  888 \",d           \n" +
                        "       Y8P Y     888,d88 888,d88   \"88,d88   \"88 88\"   d888b Y8b Y8b 888,d88          \n" +
                        "                                                                                        \n");
    }


}
