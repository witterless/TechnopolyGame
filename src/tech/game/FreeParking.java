package tech.game;

public class FreeParking extends Square {

    private int reward;

    /**
     * @param name   from the square class
     * @param reward amount is set in the board class
     */
    public FreeParking(String name, int reward) {
        super(name);
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }


}
