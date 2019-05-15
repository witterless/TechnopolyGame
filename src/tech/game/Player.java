package tech.game;

public class Player implements Comparable {

    private String playerName;
    private int money;
    private int position;


    /**
     * Will create the player object
     *
     * @param name     user input
     * @param money    set amount of 1000
     * @param position will be 0
     */
    public Player(String name, int money, int position) {
        this.playerName = name;
        this.money = money;
        this.position = position;

    }

    public Player() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * called in the movePlayer method in game class
     *
     * @param player player on turn
     */
    public void passGoReward(Player player) {
        player.money += 50;
    }

    /**
     * Called in the movePlayer method in game class before a player is asked if they want to buy property
     * canAffordValue will by the properties buyCost
     *
     * @param canAffordValue canAffordValue will by the properties buyCost
     * @return amount player can afford
     */
    public boolean canAfford(int canAffordValue) {
        return money > canAffordValue;
    }

    public void increaseMoney(int increase) {
        this.money += increase;
    }

    public void decreaseMoney(int decrease) {
        this.money -= decrease;
    }

    /**
     * @param compareAgainst implemented from Comparable
     * @return returns descending order of player's money
     */
    public int compareTo(Object compareAgainst) {
        int compareMoney = ((Player) compareAgainst).getMoney();
        return compareMoney - this.money;
    }

}
