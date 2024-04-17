package com.prova.matchme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MatchMeController {

    @FXML
    Button registerbutton;

    @FXML
    protected void registrazione() throws IOException {
        Stage stage = (Stage) registerbutton.getScene().getWindow();
        if(getClass().getResource("auth/Register-view.fxml") != null) {
            Parent root = FXMLLoader.load(getClass().getResource("auth/Register-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}