package com.prova.matchme.GestioneProfilo.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.PartitaStorico;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ConfirmView;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    public ProfiloCtrl(Stage s,Utente u){
        ArrayList<PartitaStorico> lista=DBMSView.queryPartiteGiocate(u.getId());
        Utils.cambiaInterfaccia("FXML/Visualizza Partite Giocate.fxml", s, c -> {
            return new StoricoView(this, s,lista);
        });
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

    public void Passcode(String codice) {
        int id=DBMSView.queryGetIdCode(codice);
        if(CheckId(id)){
            DBMSView.querySetAbbonamento(u.getId());
            u.setTipo("t");
            CustomStage s=new CustomStage("SUCCESSO");
            Utils.cambiaInterfaccia("FXML/Dialog cod abb corretto.fxml", s, c -> {
                return new ConfirmView(this,s);
            }, 400, 300);
        }else{
            Utils.cambiaInterfaccia("FXML/Dialog cod abb errato.fxml", s, c -> {
                return new ConfirmView(this,s);
            }, 400, 300);
        }
    }

    public boolean CheckId(int id) {
        if(id==u.getId()){
            return true;
        }
        return false;
    }

    public void CloseConfirmeView() {
        CustomStage s=new CustomStage("Gestione Profilo");
        Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml",s , c -> {
            return new ManageProfileView(this, u, s);
        }, 400, 300);
    }

    public void AttivaAbbonamento() {
        CustomStage stage=new CustomStage("Inserisci il tuo codice");
        Utils.cambiaInterfaccia("FXML/Dialog Attiva Abbonamento.fxml", stage, c -> {
            return new CodiceAbbonamentoView(this,stage);
        }, 400, 300);
    }

}
