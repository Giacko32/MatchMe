package com.prova.matchme;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage mainStage = null;
    public static int sistema, idFarmacia;
    public static String nomeFarmacia;
    public static boolean debug;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle("MatchMe");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        new AuthCtrl(stage);
        DBMSView.connectDBMS();
    }


    public static void main(String[] args) {
        launch();
    }
}