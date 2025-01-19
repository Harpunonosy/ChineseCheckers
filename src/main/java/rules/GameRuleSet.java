package rules;

import game.board.Board;
import game.move.Move;

/**
 * Interface representing a set of game rules.
 */
public interface GameRuleSet {
    /**
     * Checks if a move is valid.
     * 
     * @param move The move to check.
     * @param playerId The ID of the player making the move.
     * @param board The game board.
     * @return True if the move is valid, false otherwise.
     */
    boolean isValidMove(Move move, int playerId, Board board);

    /**
     * Checks if the game is over for a player.
     * 
     * @param board The game board.
     * @param playerId The ID of the player.
     * @return True if the game is over for the player, false otherwise.
     */
    boolean isGameOver(Board board, int playerId);
}