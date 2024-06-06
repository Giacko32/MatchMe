package com.prova.matchme.GestioneProfilo.Controller;


import com.prova.matchme.Autenticazione.Interfacce.RegisterView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Interfacce.ManageProfileView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class ProfiloCtrl {

    private Gestore g;
    private Stage s;
    private Utente u;

    public ProfiloCtrl(Stage s, Utente u, Gestore g) {
        this.s = s;
        this.u = u;
        this.g = g;
        if (u != null) {
            Utils.cambiaInterfaccia("FXML/Gestione Profilo.fxml", s, c -> {
                return new ManageProfileView(this,u);
            });
        }else if(g!=null){
            Utils.cambiaInterfaccia("FXML/GestioneProfiloGestore.fxml", s, c -> {
                return new ManageProfileView(this,g);
            });
        }
    }

    public void VisualizzaAccount() {

    }

    public void ModificaDati() {

    }

    public Object PassData() {
        return null;
    }

    public void ModificaPassword() {

    }

    public boolean ControllaPassword() {
        return false;
    }

    public void PassPassword() {

    }

    public boolean ControllaFormatoPassword() {
        return false;
    }

    public void CloseWarningView() {

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
