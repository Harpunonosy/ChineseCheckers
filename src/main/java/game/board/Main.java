package game.board;

import java.util.List;

import factories.GameFactory;
import factories.StandardGameFactory;
import game.Game;
import game.move.Move;
import game.board.*;


public class Main {
    public static void main(String[] args) {
        int numberOfPlayers = 6; 
        GameFactory factory = new StandardGameFactory();
        Game game = new Game(factory, numberOfPlayers);
        //DEMA
        game.displayBoard();
        // System.out.println(game.getGameRuleSet().isGameOver(game.getBoard(),1));
        // System.out.println(game.getGameRuleSet().isGameOver(game.getBoard(),2));


        // game.makeMove(new Move(9, 13, 9,11), 1);
        // game.displayBoard();
        // CellVertex vertex = game.getBoard().getVertexAt(9, 13);
        // List<CCEdge> edges = vertex.getEdges();

        // // Print each edge
        // for (CCEdge edge : edges) {
        //     System.out.println(edge.getDestVertex().getLocation());
        // }
    }
}