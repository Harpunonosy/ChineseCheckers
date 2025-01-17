package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientStartController {

    @FXML
    private Label clientIdLabel;

    @FXML
    private Label infoLabel;

    public void setClientId(int clientId) {
        clientIdLabel.setText("Client ID: " + clientId);
    }

    public void setInfo(String info) {
        infoLabel.setText(info);
    }

    public Label getClientIdLabel() {
        return clientIdLabel;
    }
}