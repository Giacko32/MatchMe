package com.prova.matchme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

public class MatchMeController{


    @FXML
    public Button registerbutton;

    @FXML
    public void toRegister() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/Register-view.fxml"));
        Stage stage = (Stage) registerbutton.getScene().getWindow();
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}