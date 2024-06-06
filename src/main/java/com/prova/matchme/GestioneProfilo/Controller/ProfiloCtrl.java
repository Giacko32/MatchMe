package com.prova.matchme.GestioneProfilo.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Interfacce.ManageProfileView;
import com.prova.matchme.GestioneProfilo.Interfacce.ModifyPasswordView;
import com.prova.matchme.GestioneProfilo.Interfacce.ModifyView;
import com.prova.matchme.GestioneProfilo.Interfacce.ProfileView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class ProfiloCtrl {

    private Gestore g;
    private Stage s;
    private Utente u;

    public ProfiloCtrl(Stage s, Utente u, Gestore g) {
        this.s = s;
        this.u = u;
        this.g = g;
        CustomStage stage = new CustomStage("Gestione Profilo");
        if (u != null) {
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, u, stage);
            }, 400, 300);
        } else if (g != null) {
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, g, stage);
            }, 400, 250);
        }
    }

    public void toMain() {
        if (u != null) {
            if (u.getTipo().equals("al")) {
                Utils.cambiaInterfaccia("FXML/Allena-view.fxml", s, c -> {
                    return new AllenaView(new AuthCtrl(s), u, s);
                });
            } else {
                Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                    return new MainView(new AuthCtrl(s), u, s);
                });
            }
        }
        if (g != null) {
            Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
                return new AdminView(new AuthCtrl(s), g, s);
            });
        }
    }

    public void VisualizzaAccount() {
        if (u != null) {
            Utils.cambiaInterfaccia("FXML/Visualizza Account.fxml", s, c -> {
                return new ProfileView(this, u);
            });
        } else if (g != null) {
            Utils.cambiaInterfaccia("FXML/Visualizza AccountGestore.fxml", s, c -> {
                return new ProfileView(this, g);
            });
        }
    }

    public void ModificaDati() {
        if (u != null) {
            Utils.cambiaInterfaccia("FXML/Modifica Credenziali.fxml", s, c -> {
                return new ModifyView(this, u);
            });
        } else if (g != null) {
            Utils.cambiaInterfaccia("FXML/Modifica Credenziali Gestore.fxml", s, c -> {
                return new ModifyView(this, g);
            });
        }

    }

    public void PassData(String username, String email, String nome, String cognome) {
        CustomStage stage = new CustomStage("Gestione Profilo");
        if (u != null) {
            u.setdati(nome, cognome, email, username);
            DBMSView.queryDBMSUpdateData(u, null);
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, u, stage);
            }, 400, 300);
        }
        if (g != null) {
            g.setdati(nome, cognome, email, username);
            DBMSView.queryDBMSUpdateData(null, g);
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, g, stage);
            }, 400, 300);
        }
    }

    public void ModificaPassword() {
        CustomStage stage = new CustomStage("Cambia Password");
        Utils.cambiaInterfaccia("FXML/Dialog Modifica Password.fxml", stage, c -> {
            return new ModifyPasswordView(this, stage);
        }, 450, 200);
    }

    public boolean ControllaPassword(String password) {
        if (u != null) {
            if (u.getPassword().equals(password)) {
                return true;
            }
            return false;
        }
        if (g != null) {
            if (g.getPassword().equals(password)) {
                return true;
            }
            return false;
        }
        return false;
    }
    public void PassPassword(String old, String newpass) {
        CustomStage stage = new CustomStage("Gestione Profilo");
        if (u != null) {
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, u, stage);
            }, 400, 300);
        }
        if (g != null) {
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", stage, c -> {
                return new ManageProfileView(this, g, stage);
            }, 400, 300);
        }
        if (ControllaFormatoPassword(newpass)) {
            if (ControllaPassword(old)) {
                if (u != null) {
                    DBMSView.queryDBMSUpdatePassword(u, null, newpass);
                    u.setPassword(newpass);
                }
                if (g != null) {
                    DBMSView.queryDBMSUpdatePassword(null, g, newpass);
                    g.setPassword(newpass);
                }
            } else {
                Utils.creaPannelloErrore("La vecchia password\nnon coincide");
            }
        } else {
            Utils.creaPannelloErrore("La nuova password \nnon è formattata \ncorrettamente");
        }
    }

    public boolean ControllaFormatoPassword(String password) {
        Pattern passlenght = Pattern.compile("^.{8,}$");
        if (passlenght.matcher(password).find()) {
            return true;
        }
        return false;
    }

    public void Passcode() {

    }

    public boolean CheckId() {
        return false;
    }

    public void CloseConfirmeView() {

    }

    public void AttivaAbbonamento() {

    }

}
