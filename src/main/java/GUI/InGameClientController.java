package GUI;

import client.ClientInputHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;

public class InGameClientController {

    @FXML
    private GridPane Board;

    @FXML
    private Circle field;

    @FXML
    private Label turnState;

    @FXML
    private Label playerIdColor;

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
            highlightCircle(clickedCircle, Color.YELLOW); // Kolorowanie kółka na żółto
        } else {
            String move = buildMoveString(startX, startY, column, row);
            System.out.println("Move: " + move);

            if (inputHandler != null) {
                inputHandler.sendMove(move);
                unhighlightCircle(getCircleAtPosition(startX, startY)); // Odkolorowanie kółka startowego
                highlightCircle(clickedCircle, Color.GREEN); // Kolorowanie kółka końcowego na zielono
            }

            startX = null;
            startY = null;
        }
    }

    private String buildMoveString(Integer startX, Integer startY, Integer endX, Integer endY) {
        return startX + "-" + startY + "-" + endX + "-" + endY;
    }

    public void highlightCircle(Circle circle, Color color) {
        circle.setFill(color);
    }

    public void unhighlightCircle(Circle circle) {
        circle.setFill(Color.BLUE); // Przywrócenie domyślnego koloru
    }

    public Circle getCircleAtPosition(int column, int row) {
        for (javafx.scene.Node node : Board.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                return (Circle) node;
            }
        }
        return null;
    }

    public void clearBoard() {
        for (javafx.scene.Node node : Board.getChildren()) {
            if (node instanceof Circle) {
                ((Circle) node).setFill(Color.WHITE); // Ustaw domyślny kolor
            }
        }
    }
    public void setTurnState(String state) {
        turnState.setText(state);
    }

    public void setPlayerIdColor(int playerId, Color color) {
        playerIdColor.setText("Player ID: " + playerId);
        playerIdColor.setTextFill(color);
    }

}