package GUI;

import client.ClientInputHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class InGameClientController {

    @FXML
    private GridPane Board;

    @FXML
    private Circle field;

    private Integer startX, startY;
    private ClientInputHandler inputHandler;

    public void setInputHandler(ClientInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @FXML
    void OnClicked(MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        Integer column = GridPane.getColumnIndex(clickedCircle);
        Integer row = GridPane.getRowIndex(clickedCircle);
        System.out.println("Clicked on column: " + column + " row: " + row);

        if (startX == null || startY == null) {
            startX = column;
            startY = row;
        } else {
            String move = buildMoveString(startX, startY, column, row);
            System.out.println("Move: " + move);

            if (inputHandler != null) {
                inputHandler.sendMove(move);
            }

            startX = null;
            startY = null;
        }
    }

    private String buildMoveString(Integer startX, Integer startY, Integer endX, Integer endY) {
        return startX + "-" + startY + "-" + endX + "-" + endY;
    }
}