package com.prova.matchme;

import com.almasb.fxgl.dsl.FXGL;
import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMatchMe extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("MatchMe");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        AuthCtrl authCtrl=new AuthCtrl(stage);
    }


    public static void main(String[] args) {
        launch();
    }
}