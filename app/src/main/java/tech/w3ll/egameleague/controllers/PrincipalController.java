package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrincipalController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to eGameLeague!");
    }
}