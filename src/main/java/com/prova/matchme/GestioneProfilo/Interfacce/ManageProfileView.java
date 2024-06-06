package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ManageProfileView {


    private ProfiloCtrl profiloCtrl;
    private Utente u;
    private Gestore g;

    public ManageProfileView(ProfiloCtrl profiloCtrl, Utente u) {
        this.profiloCtrl = profiloCtrl;
        this.u = u;
    }

    public ManageProfileView(ProfiloCtrl profiloCtrl, Gestore g) {
        this.profiloCtrl = profiloCtrl;
        this.g = g;
    }

    @FXML
    private TextField nome;
    public TextField username;
    public Button tastoabb;

    @FXML
    public void initialize() {
        if (u != null) {
            if (u.getTipo().equals("nt")) {
                tastoabb.setDisable(false);
            }
        }
    }

    public void ClickVisualizzaAccount() {

    }

    public void ClickModifyAccount() {

    }

    public void ClickModifyPassword() {

    }

    public void ShowBnd() {

    }

}
