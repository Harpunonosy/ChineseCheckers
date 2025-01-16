package game;

import factories.GameFactory;
import game.board.Board;
import game.move.Move;
import rules.GameRuleSet;

public class Game {
    private Board board;
    private GameRuleSet ruleSet;
    private int numberOfPlayers;

    public Game(GameFactory factory, int numberOfPlayers) {
        this.board = factory.createBoard();
        this.ruleSet = factory.createRuleSet();
        this.numberOfPlayers = numberOfPlayers;
        board.initialize();
        board.setPlayersToRegions(numberOfPlayers);
    }

    public Board getBoard(){
      return board;
    }

    public GameRuleSet getGameRuleSet(){
      return ruleSet;
    }

    public void displayBoard() {
        board.display();
    }

    public void makeMove(Move move, int playerId){
      if(ruleSet.isValidMove(move, playerId, board)){
        board.makeMove(move, playerId, ruleSet);
      }
    }

}