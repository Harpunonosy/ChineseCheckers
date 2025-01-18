package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.logging.Logger;

public class GameOverController {
    private static final Logger logger = Logger.getLogger(GameOverController.class.getName());

    @FXML
    private Label gameOverLabel;

    @FXML
    private Label winnerLabel;

    public void setWinner(String winner) {
        logger.info("Setting winner: " + winner);
        winnerLabel.setText(winner);
    }
}