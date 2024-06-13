package com.prova.matchme.Autenticazione.Controller;

import com.prova.matchme.*;
import com.prova.matchme.Autenticazione.Interfacce.*;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.shared.ConfirmView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class AuthCtrl {
    Stage s;
    private String codice;

    public AuthCtrl(Stage stage) {
        s = stage;
    }

    private String email_inserita;


    public String ControllaFormatoDati(String nome, String cognome, String email, String username, LocalDate datanascita, String password, char sesso) {
        Pattern noNumber = Pattern.compile("^[A-Za-z]*$");
        Pattern mail = Pattern.compile("^[a-z0-9.]+@[a-z]+[.][a-z]+$");
        Pattern passlenght = Pattern.compile("^.{8,}$");
        if (nome.equals("") || cognome.equals("") || email.equals("") || username.equals("") || datanascita == null || password.equals("") || sesso == 'V') {
            return "Ci sono campi vuoti";
        } else {
            if (noNumber.matcher(nome).find() && noNumber.matcher(cognome).find()) {
                if (mail.matcher(email).find()) {
                    if (datanascita.isBefore(LocalDate.now())) {
                        if (passlenght.matcher(password).find()) {
                            return "true";
                        } else {
                            return "La password deve essere\n lunga almeno 8 caratteri";
                        }
                    } else {
                        return "Data di nascita errata";
                    }
                } else {
                    return "Formato della mail errato";
                }
            } else {
                return "Formato del nome\n o del cognome errato";
            }
        }
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
        CustomStage s = new CustomStage("Recupero password");
        Utils.cambiaInterfaccia("FXML/RecuperaPassword.fxml", s, c -> {
            return new RecoveryView(this, s);
        }, 450, 200);
    }

    public void controllaCredenziali(String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            Utils.creaPannelloErrore("Campi lasciati vuoti");
        } else {
            if (username.equals("root") && password.equals("123")) {
                Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                    return new MainView(this, null, s);
                });
            } else {
                String ritorno = DBMSView.queryControllaCredenziali(username, password);
                if (ritorno.equals("ut")) {
                    Utente u = DBMSView.queryControllaCredenzialiUtente(username, password);
                    if (u.getTipo().equals("al")) {
                        Utils.cambiaInterfaccia("FXML/Allena-view.fxml", s, c -> {
                            return new AllenaView(this, u, s);
                        });
                    } else {
                        Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                            return new MainView(this, u, s);
                        });
                    }
                } else if (ritorno.equals("g")) {
                    Gestore g = DBMSView.queryControllaCredenzialiGest(username, password);
                    Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
                        return new AdminView(this, g, s);
                    });
                } else {
                    Utils.creaPannelloErrore("Le credenziali inserite\n sono errate");
                }
            }
        }
    }

    public void SendDati(String nome, String cognome, String email, String username, LocalDate datanascita, String password, String tipo, char sesso, float livello) {
        if (!DBMSView.queryDBMSCheckUsernameandMail(username, email)) {
            Utils.creaPannelloErrore("Username o mail già\n registrate");
        } else {
            String esito = this.ControllaFormatoDati(nome, cognome, email, username, datanascita, password, sesso);
            if (esito.equals("true")) {
                int eta = Period.between(datanascita, LocalDate.now()).getYears();
                if (DBMSView.registraUtente(nome, cognome, email, username, eta, password, tipo, sesso, livello)) {
                    this.toLogin();
                }
            } else {
                Utils.creaPannelloErrore(esito);
            }
        }
    }

    public boolean passMail(String email) {
        Pattern mail = Pattern.compile("^[a-z0-9.]+@[a-z]+[.][a-z]+$");
        if (mail.matcher(email).find()) {
            if (DBMSView.queryDBMSCheckMail(email)) {
                email_inserita = email;
                this.codice = GeneraCodice();
                InviaMail(email);
                return true;
            } else {
                Utils.creaPannelloErrore("Email inserita non associata \n ad un account");
                return false;
            }
        } else {
            Utils.creaPannelloErrore("Email inserita non valida");
            return false;
        }
    }

    public String GeneraCodice() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    public void InviaMail(String email) {
        EmailSender.sendEmail(email, codice);
    }

    public boolean passcode(String codice) {
        if (codice.equals(this.codice)) {
            CustomStage s = new CustomStage("Recupera Credenziali");
            Utils.cambiaInterfaccia("FXML/RecuperaPassword1.fxml", s, c -> {
                return new ChangePasswordView(this, s);
            }, 450, 200);
            return false;
        } else {
            Utils.creaPannelloErrore("Il codice inserito non è\ncorretto");
            return false;
        }
    }

    public void passPassword(String password, String conferma, Stage s) {
        if (ControllaPassword(password, conferma) && password.length() > 7) {
            DBMSView.queryDBMSChangePassword(email_inserita, password);
        } else {
            Utils.creaPannelloErrore("Errore nell'inserimento della password");
        }
        s.close();
    }

    public boolean ControllaPassword(String password, String conferma) {
        if (password.equals(conferma)) {
            return true;
        } else {
            return false;
        }
    }

    public void toConfirm() {
        CustomStage s = new CustomStage("ATTENZIONE");
        Utils.cambiaInterfaccia("FXML/DialogConferma.fxml", s, c -> {
            return new ConfirmView(this, s);
        }, 350, 200);
    }


}
