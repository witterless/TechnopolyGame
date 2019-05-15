package tech.game;

public class Square {

    private String name;

    /**
     * method to create the square
     *
     * @param name square name
     */
    public Square(String name) {
        this.name = name;
    }

    public Square() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
