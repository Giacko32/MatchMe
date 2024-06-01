package com.prova.matchme.Autenticazione.Controller;

import com.prova.matchme.Autenticazione.Interfacce.*;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.Main;
import com.prova.matchme.Utils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AuthCtrl {
    Stage s;

    public AuthCtrl(Stage stage) {
        s = stage;

    }

    private String username_inserito;

    private String password_inserita;

    private String password;

    private int tipo_utente;

    private String codice;

    private String mail;

    private String codice_inserito;

    public boolean ControllaFormatoDati() {
        return false;
    }

    public void CloseWarningView() {

    }

    public void toRegistra() {
        Utils.cambiaInterfaccia("FXML/Register-view.fxml", s, c -> {
            return new RegisterView(this);
        });
    }

    public void toLogin() {
        Utils.cambiaInterfaccia("FXML/login-view.fxml", s, c -> {
            return new LoginView(s);
        });
    }

    public void toRecovery() {
        Utils.cambiaInterfaccia("FXML/RecuperaPassword.fxml", new CustomStage("Recupero password"), c -> {
            return new RecoveryView(this);
        }, 450, 200);
    }

    public void controllaCredenziali(String username, String password) {
        if (username.equals("root") && password.equals("123")) {
            Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                return new MainView(this);
            });
        } else {
            if (Main.sistema == 0) {
                Utente u = DBMSView.queryControllaCredenziali(username, password);
                if (u == null) {
                } else {
                    System.out.println(u);
                    Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                        return new MainView(this);
                    });
                }
            }else if(Main.sistema==1){
                Gestore g = DBMSView.queryControllaCredenzialiGest(username, password);
                if (g == null) {
                } else {
                    System.out.println(g);
                    Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
                        return new AdminView(this);
                    });
                }
            }
        }

    }

    public int ControllaTipo() {
        return 0;
    }

    public void SendDati() {

    }

    public boolean ControllaUsername() {
        return false;
    }

    public void PassMail() {

    }

    public String GeneraCodice() {
        return null;
    }

    public void InviaMail() {

    }

    public void PassCode() {

    }

    public boolean ControllaCodice() {
        return false;
    }

    public boolean ControllaMail() {
        return false;
    }

    public void PassPassword() {

    }

    public boolean ControllaPassword() {
        return false;
    }

    public void ConfermaCliccata() {

    }

}
