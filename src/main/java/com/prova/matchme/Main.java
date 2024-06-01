package com.prova.matchme;

import com.mysql.cj.exceptions.NumberOutOfRange;
import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.LoginView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {

    public static Stage mainStage = null;
    public static int sistema;


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

    public static void risolviSistema(String args[]){
        try {
            if (args.length >= 1) {
                sistema = Integer.parseInt(args[0]);
                switch (sistema){
                    case 0:{
                        System.out.println("Il sistema è di tipo utente");
                        break;
                    }
                    case 1:{
                        System.out.println("Il sistema è di tipo gestore");
                        break;
                    }
                    default:{
                        throw new NumberOutOfRange("Numero sconosciuto");
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            exit(1);
        }

    }

    public static void main(String[] args) {
        //risolviSistema(args);
        sistema=0;
        launch();
    }
}