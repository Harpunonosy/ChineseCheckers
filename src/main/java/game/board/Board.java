package game.board;

import java.util.Map;

import game.move.Move;
import rules.GameRuleSet;

/**
 * Interface representing a game board.
 */
public interface Board {
    void initialize();
    void makeMove(Move move, int playerId, GameRuleSet ruleSet);
    CellVertex getVertexAt(int x, int y);
    void setPlayersToRegions(int numberOfPlayers);
    CellVertex[][] getMatrix();
    Map<Integer, Integer> getPlayersTargetRegions();
    int[][] getRegion(int region);
    int getNumberOfPlayers();
    void setNumberOfPlayers(int numberOfPlayers);
}