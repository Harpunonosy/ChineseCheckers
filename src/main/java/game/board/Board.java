package game.board;

import java.util.Map;

import game.move.Move;
import rules.GameRuleSet;

public interface Board {
    void initialize();
    void display();
    void makeMove(Move move, int playerId, GameRuleSet ruleSet);
    CellVertex getVertexAt(int x, int y);
    void setPlayersToRegions(int numberOfPlayers);
    CellVertex[][] getMatrix();
    Map<Integer, Integer> getPlayersTargetRegions();
    int[][] getRegion(int region);
}