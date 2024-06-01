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

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean ControllaFormatoDati(String nome, String cognome, String email, String username, LocalDate datanascita, String password, char sesso) {
        Pattern noNumber = Pattern.compile("^[A-Za-z]*$");
        if(nome.equals("") || cognome.equals("") || email.equals("") || username.equals("") || datanascita == null || )
        if(noNumber.matcher(nome).find() && noNumber.matcher(cognome).find()){

        } else {
            Utils.creaPannelloErrore("Formato del nome\n o del cognome errato");
        }
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
        if (username.isBlank() || password.isBlank()) {
            Utils.creaPannelloErrore("Campi lasciati vuoti");
        } else {
            if (username.equals("root") && password.equals("123")) {
                Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                    return new MainView(this);
                });
            } else {
                if (Main.sistema == 0) {
                    Utente u = DBMSView.queryControllaCredenziali(username, password);
                    if (u == null) {
                        Utils.creaPannelloErrore("Credenziali errate");
                    } else {
                        System.out.println(u);
                        Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                            return new MainView(this);
                        });
                    }
                } else if (Main.sistema == 1) {
                    Gestore g = DBMSView.queryControllaCredenzialiGest(username, password);
                    if (g == null) {
                        Utils.creaPannelloErrore("Credenziali errate");
                    } else {
                        System.out.println(g);
                        Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
                            return new AdminView(this);
                        });
                    }
                }
            }
        }
    }

    public int ControllaTipo() {
        return 0;
    }

    public void SendDati(String nome, String cognome, String email, String username, LocalDate datanascita, String password, String tipo, char sesso, float livello) {
        if(!DBMSView.queryDBMSCheckUsernameandMail(username, email)){
            Utils.creaPannelloErrore("Username o mail già\n registrate");
        } else {
            this.ControllaFormatoDati(nome,cognome,email, username, datanascita, password, sesso);
        }
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
