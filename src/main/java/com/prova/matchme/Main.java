package com.prova.matchme;


import com.prova.matchme.Autenticazione.Interfacce.LoginView;
import com.prova.matchme.shared.DBMSView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class  Main extends Application {

    public static Stage mainStage = null;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle("MatchMe");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        Utils.cambiaInterfaccia("FXML/login-view.fxml", mainStage, c -> {
            return new LoginView(mainStage);
        });

        DBMSView.connectDBMS();
    }
    public static void main(String[] args) {
        launch();
    }
}