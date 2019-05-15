package tech.game;

public class Property extends Square {

    private int rentCost;
    private int buyCost;
    private Field field;
    private Player owner;

    /**
     * To create the property object
     *
     * @param name     extended from square
     * @param rentCost rent of property
     * @param buyCost  cost of property to buy
     * @param field   field of square
     */
    public Property(String name, int rentCost, int buyCost, Field field) {
        super(name);
        this.rentCost = rentCost;
        this.buyCost = buyCost;
        this.field = field;
    }

    public int getRentCost() {
        return rentCost;
    }

    public void setRentCost(int rentCost) {
        this.rentCost = rentCost;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void addCorpTaxToRent() {
        this.rentCost = this.rentCost + 100;

    }

