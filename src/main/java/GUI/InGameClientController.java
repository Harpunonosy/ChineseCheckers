package GUI;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class InGameClientController {

    @FXML
    private GridPane Board;

    @FXML
    private Circle field;

    @FXML
    void OnClicked(MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        Integer column = GridPane.getColumnIndex(clickedCircle);
        Integer row = GridPane.getRowIndex(clickedCircle);
        //TODO ROBISZ FUNKCJĘ UŻYWAJĄ STRING BUILDER TAK ŻEBY ZBUDOWAŁA RUCH: x1-y1-x2-y2
        //FUNKCJONALNOŚĆ
    }
}