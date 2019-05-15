package tech.game;

import java.util.List;
import java.util.ArrayList;

public class Board {

    private List<Square> board = new ArrayList<>();

    /**
     * The setup of the technopoly board
     */
    public Board() {
        board.add(new Square("Go"));
        board.add(new Property("Jennifer & Co", 150, 250, Field.IT_SUPPORT));
        board.add(new Property("StartUp Inc", 150, 250, Field.IT_SUPPORT));
        board.add(new Property("Function Corp", 200, 300, Field.CYBER_SECURITY));
        board.add(new Property("Blue Pen", 200, 300, Field.CYBER_SECURITY));
        board.add(new Property("Water Web", 200, 300, Field.CYBER_SECURITY));
        board.add(new FreeParking("Free Parking", 50));
        board.add(new Property("Diamond Group", 300, 400, Field.FINTECH));
        board.add(new Property("HP", 300, 400, Field.FINTECH));
        board.add(new Property("DTS", 300, 400, Field.FINTECH));
        board.add(new Property("Group 2", 400, 500, Field.ARTIFICIAL_INTELLIGENCE));
        board.add(new Property("Microsoft", 400, 500, Field.ARTIFICIAL_INTELLIGENCE));
    }

    public int getBoardSize() {
        return board.size();
    }

    public Square getSquare(int position) {
       return board.get(position);

    }

}
