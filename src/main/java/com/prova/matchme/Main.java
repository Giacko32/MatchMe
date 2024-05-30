package com.prova.matchme;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.application.Application;
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
        stage.setTitle("MatchMe");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        new AuthCtrl(stage);
    }


    public static void main(String[] args) {
        launch();
    }
}