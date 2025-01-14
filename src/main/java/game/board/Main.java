package game.board;

import game.move.Move;

public class Main {
    public static void main(String[] args) {
        StandardBoard board = new StandardBoard();
        board.initialize();
        board.display();
    }
}