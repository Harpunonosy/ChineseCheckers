package game;

import factories.GameFactory;
import game.board.Board;
import game.move.Move;
import rules.GameRuleSet;

/**
 * Represents a game.
 */
public class Game {
    private Board board;
    private GameRuleSet ruleSet;
    private int numberOfPlayers;

    /**
     * Initializes a new game.
     * 
     * @param factory The game factory.
     * @param numberOfPlayers The number of players.
     */
    public Game(GameFactory factory, int numberOfPlayers) {
        this.board = factory.createBoard();
        this.ruleSet = factory.createRuleSet();
        this.numberOfPlayers = numberOfPlayers;
        board.setNumberOfPlayers(numberOfPlayers);
        board.initialize();
        board.setPlayersToRegions(numberOfPlayers);
    }

    /**
     * Gets the game board.
     * 
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the game rule set.
     * 
     * @return The game rule set.
     */
    public GameRuleSet getGameRuleSet() {
        return ruleSet;
    }

    /**
     * Makes a move in the game.
     * 
     * @param move The move to make.
     * @param playerId The ID of the player making the move.
     */
    public void makeMove(Move move, int playerId) {
        if (ruleSet.isValidMove(move, playerId, board)) {
            board.makeMove(move, playerId, ruleSet);
        }
    }
}